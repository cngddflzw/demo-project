package com.zim.demo.rpcproxy.heterogeneous;

import com.google.common.collect.Lists;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
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
    private static final String HETEROGENEOUS_SERVICE_NAME = "com.zim.demo.rpcproxy.heterogeneous.DemoHeterogeneousService";
    private static final String REFER_SERVICE_NAME = "com.zim.demo.rpcproxy.java.DemoJavaService";

    private InvocationClient invocationClient;

    @Before
    public void initialize() {
        OkHttpClient client = HttpClientFactory.createClient();
        invocationClient = new InvocationClient(REQUEST_URL, client);
    }

    @Test
    public void testReferAndInvokeService() {
        DefaultInvocation invocation = new DefaultInvocation();
        invocation.setInterfaceName(REFER_SERVICE_NAME);
        invocation.setGroup("arch");
        invocation.setVersion("1.0.0");
        invocation.setMethodName("sayHello");
        invocation.setParamTypes(Lists.newArrayList("java.lang.String"));
        invocation.setParamVals(Lists.newArrayList("zim"));
        Object result = invocationClient.invoke(invocation);
        System.out.println(result);
    }
}
