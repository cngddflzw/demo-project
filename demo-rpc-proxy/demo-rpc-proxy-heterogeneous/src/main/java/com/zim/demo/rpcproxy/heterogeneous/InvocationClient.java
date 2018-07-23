package com.zim.demo.rpcproxy.heterogeneous;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.Invocation;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 异构语言调用 java 服务
 *
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class InvocationClient {

    private static final String INVOKE_PATH = "/invoke";

    private String requestUrl;
    private OkHttpClient httpClient;

    public InvocationClient(String requestUrl, OkHttpClient httpClient) {
        this.requestUrl = requestUrl;
        this.httpClient = httpClient;
    }

    public Object invoke(Invocation invocation) {
        return request(INVOKE_PATH, invocation);
    }

    private Object request(String path, Object data) {
        Request request = new Builder()
                .url(requestUrl + path)
                .post(RequestBody.create(MediaType.parse("application/json"),
                        JSON.toJSONString(data)))
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (body == null) {
                throw new RuntimeException("request proxy error, body is null");
            }
            String bodyStr = body.string();
            return JSON.parse(bodyStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
