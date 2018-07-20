package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.common.HttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Configuration
public class ApplicationInitializer {

    private static final String REQUEST_URL = "http://localhost:8080/sidecar";

    @Bean
    public InvocationClient invocationClient() {
        return new InvocationClient(REQUEST_URL, HttpClientFactory.createClient());
    }
}
