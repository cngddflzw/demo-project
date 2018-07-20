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
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.MINUTES)
                .followRedirects(false)
                .connectionPool(cp)
                .build();
    }
}
