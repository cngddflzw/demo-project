package com.zim.demo.rpcproxy.sidecar.core.http;

import com.google.common.base.Suppliers;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocationResult;
import com.zim.demo.rpcproxy.common.GsonHolder;
import com.zim.demo.rpcproxy.sidecar.common.RequestParser;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.core.AbstractRequestSender;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
//@Service
public class HttpRequestSender extends AbstractRequestSender<Request, Response> {

    @Resource
    private OkHttpClient httpClient;

    @Value("${heterogeneous.request.url}")
    private String requestUrl;

    private Supplier<RequestParser<Invocation, Request>> requestParserSupplier = Suppliers.memoize(
            Suppliers.ofInstance(new RequestParser<Invocation, Request>() {
                @Override
                public Request parse(Invocation inv) {
                    return new Builder()
                            .url(requestUrl)
                            .post(RequestBody.create(MediaType.parse("application/json"),
                                    GsonHolder.gson().toJson(inv)))
                            .build();
                }
            })
    );

    private Supplier<ResponseParser<Response, InvocationResult>> responseParserSupplier = Suppliers
            .memoize(
                    Suppliers.ofInstance(new ResponseParser<Response, InvocationResult>() {
                        @Override
                        public InvocationResult parse(Response resp) {
                            try {
                                ResponseBody body = resp.body();
                                assert body != null;
                                return GsonHolder.gson().fromJson(body.string(),
                                        DefaultInvocationResult.class);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
            );

    @Override
    protected Response doSend(Request request) throws IOException {
        Response response = httpClient.newCall(request).execute();
        int code = response.code();
        String message = response.message();
        ResponseBody body = response.body();
        if (code != 200) {
            throw new Java2HeterogeneousException(
                    String.format(
                            "request heterogeneous service error code %s message %s",
                            code, message));
        } else if (body != null) {
            return response;
        } else {
            throw new Java2HeterogeneousException(
                    "request heterogeneous service return empty result, post data");
        }
    }

    @Override
    protected RequestParser<Invocation, Request> getRequestParser() {
        return requestParserSupplier.get();
    }

    @Override
    protected ResponseParser<Response, InvocationResult> getResponseParser() {
        return responseParserSupplier.get();
    }
}
