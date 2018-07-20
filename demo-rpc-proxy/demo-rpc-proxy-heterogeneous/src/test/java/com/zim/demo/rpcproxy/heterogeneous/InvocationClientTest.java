package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import com.zim.demo.rpcproxy.common.HttpClientFactory;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class InvocationClientTest {

    private static final String REQUEST_URL = "http://localhost:8080/sidecar";
    private InvocationClient invocationClient;

    @Before
    public void initialize() {
        OkHttpClient client = HttpClientFactory.createClient();
        invocationClient = new InvocationClient(REQUEST_URL, client);
    }

    @Test
    public void testRegister() {
        DefaultServiceInfo serviceInfo = new DefaultServiceInfo();
        serviceInfo.setName("com.zim.demo.rpcproxy.heterogeneous.DemoService");
        serviceInfo.setVersion("1.0.0");
        serviceInfo.setGroup("arch");
        invocationClient.register(serviceInfo);
    }
}
