package com.zim.demo.rpcproxy.common;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class HttpClientFactory {

    public static OkHttpClient createClient() {
        ConnectionPool cp = new ConnectionPool(10, 5, TimeUnit.SECONDS);

        return new Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .followRedirects(false)
                .connectionPool(cp)
                .build();
    }
}
