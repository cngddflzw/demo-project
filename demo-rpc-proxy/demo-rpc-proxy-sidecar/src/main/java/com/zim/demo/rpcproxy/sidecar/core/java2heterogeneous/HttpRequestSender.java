package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;
import java.io.IOException;
import javax.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@Service
public class HttpRequestSender implements RequestSender<InvocationResult> {

    @Resource
    private OkHttpClient httpClient;

    @Value("${heterogeneous.request.url}")
    private String requestUrl;

    @Resource(name = "json2InvocationResultParser")
    private ResponseParser<String, InvocationResult> responseParser;

    @Override
    public InvocationResult send(Object postData) {
        Request request = new Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.parse("application/json"), postData.toString()))
                .build();

        try {
            Response execute = httpClient.newCall(request).execute();
            int code = execute.code();
            String message = execute.message();
            ResponseBody body = execute.body();
            if (code != 200) {
                throw new Java2HeterogeneousException(
                        String.format(
                                "request heterogeneous service error code %s message %s, post data: %s",
                                code, message, postData));
            } else if (body != null) {
                return responseParser.parse(body.string());
            } else {
                throw new Java2HeterogeneousException(String.format(
                        "request heterogeneous service return empty result, post data: %s",
                        postData));
            }
        } catch (IOException e) {
            throw new Java2HeterogeneousException(e);
        }
    }
}
