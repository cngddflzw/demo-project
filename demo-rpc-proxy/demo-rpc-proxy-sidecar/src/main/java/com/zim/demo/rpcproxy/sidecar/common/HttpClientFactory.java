package com.zim.demo.rpcproxy.sidecar.common;

import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class HttpClientFactory {

    public static HttpClient createClient() {

        // TODO 这里需要考虑将 dubbo 的一些超时配置对接到 httpclient
        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
        phccm.setDefaultMaxPerRoute(1000);
        phccm.setMaxTotal(1000);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000) // 连接超时
                .setConnectionRequestTimeout(1000) // 从连接池获取连接的超时时间
                .setSocketTimeout(10000) // 请求超时
                .setContentCompressionEnabled(false) // 内网请求不需要压缩
                .build();

        return HttpClients.custom()
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .setConnectionManager(phccm)
                .setDefaultRequestConfig(requestConfig)
                .build();

    }
}
