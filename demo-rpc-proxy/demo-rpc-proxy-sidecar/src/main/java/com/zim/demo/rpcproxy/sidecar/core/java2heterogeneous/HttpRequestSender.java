package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.zim.demo.rpcproxy.sidecar.exception.DelegateInvokeException;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class HttpRequestSender implements RequestSender<String, Object> {

    private final HttpClient client;
    private final String requestUrl;

    public HttpRequestSender(HttpClient client, String requestUrl) {
        this.client = client;
        this.requestUrl = requestUrl;
    }

    @Override
    public Object send(String postData) {
        HttpPost post = new HttpPost(requestUrl);
        StringEntity entity = new StringEntity(postData,
                ContentType.create("application/json", "UTF-8"));
        post.setEntity(entity);
        try {
            HttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new DelegateInvokeException(e);
        }
    }
}
