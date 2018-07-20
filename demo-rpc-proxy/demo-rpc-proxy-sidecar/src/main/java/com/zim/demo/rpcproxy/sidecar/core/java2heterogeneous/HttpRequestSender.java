package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class HttpRequestSender implements RequestSender<InvocationResult> {

    private final OkHttpClient httpClient;
    private final String requestUrl;
    private ResponseParser<String, InvocationResult> responseParser;

    public HttpRequestSender(OkHttpClient httpClient, String requestUrl,
            ResponseParser<String, InvocationResult> responseParser) {
        this.httpClient = httpClient;
        this.requestUrl = requestUrl;
        this.responseParser = responseParser;
    }

    @Override
    public InvocationResult send(Object postData) {
        Request request = new Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.parse("application/json"), postData.toString()))
                .build();

        try {
            ResponseBody body = httpClient.newCall(request).execute().body();
            if (body != null) {
                return responseParser.parse(body.string());
            } else {
                throw new Java2HeterogeneousException(
                        String.format("request %s delegate return empty data", postData));
            }
        } catch (IOException e) {
            throw new Java2HeterogeneousException(e);
        }
    }
}
