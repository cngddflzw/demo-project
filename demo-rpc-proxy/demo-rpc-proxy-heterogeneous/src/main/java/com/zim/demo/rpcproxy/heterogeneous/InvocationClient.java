package com.zim.demo.rpcproxy.heterogeneous;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationService;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
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
public class InvocationClient implements RegistryService, InvocationService {

    private static final String REFER_PATH = "/refer";
    private static final String INVOKE_PATH = "/invoke";
    private static final String REGISTER_PATH = "/register";
    private static final String UNREGISTER_PATH = "/unregister";

    private String requestUrl;
    private OkHttpClient httpClient;

    public InvocationClient(String requestUrl, OkHttpClient httpClient) {
        this.requestUrl = requestUrl;
        this.httpClient = httpClient;
    }

    @Override
    public void refer(ServiceInfo serviceInfo) {
        request(REFER_PATH, serviceInfo);
    }

    @Override
    public Object invoke(Invocation invocation) {
        return request(INVOKE_PATH, invocation);
    }

    @Override
    public void register(ServiceInfo serviceInfo) {
        request(REGISTER_PATH, serviceInfo);
    }

    @Override
    public void unregister(ServiceInfo serviceInfo) {
        request(UNREGISTER_PATH, serviceInfo);
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
