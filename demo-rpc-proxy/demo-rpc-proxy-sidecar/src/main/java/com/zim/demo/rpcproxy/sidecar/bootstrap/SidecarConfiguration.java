package com.zim.demo.rpcproxy.sidecar.bootstrap;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.sidecar.common.HttpClientFactory;
import com.zim.demo.rpcproxy.sidecar.common.JsonResponseParser;
import com.zim.demo.rpcproxy.sidecar.common.JsonSerializer;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.common.Serializer;
import com.zim.demo.rpcproxy.sidecar.config.Application;
import com.zim.demo.rpcproxy.sidecar.config.Protocol;
import com.zim.demo.rpcproxy.sidecar.config.RegisterConfig;
import com.zim.demo.rpcproxy.sidecar.config.Registry;
import com.zim.demo.rpcproxy.sidecar.core.DubboSidecarService;
import com.zim.demo.rpcproxy.sidecar.core.SidecarService;
import com.zim.demo.rpcproxy.sidecar.core.heterogeneous2java.Heterogeneous2DubboService;
import com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous.Dubbo2HeterogeneousService;
import com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous.DubboRegistryService;
import com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous.HttpRequestSender;
import com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous.RequestSender;
import java.io.IOException;
import java.util.Properties;
import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@Configuration
public class SidecarConfiguration {

    private static final String SYS_PROPS_FILE = "system.properties";

    private static final String HETEROGENEOUS_REQUEST_URL = "heterogeneous.request.url";

    private static final String APPLICATION_NAME = "application.name";
    private static final String APPLICATION_ORGANIZATION = "application.organization";

    private static final String PROTOCOL_NAME = "protocol.name";
    private static final String PROTOCOL_PORT = "protocol.port";

    private static final String REGISTRY_PROTOCOL = "registry.protocol";
    private static final String REGISTRY_ADDRESS = "registry.address";
    private static final String REGISTRY_PORT = "registry.port";

    private static final Properties CONF;

    static {
        try {
            CONF = PropertiesLoaderUtils.loadAllProperties(SYS_PROPS_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Serializer<String> serializer() {
        return new JsonSerializer();
    }

    @Bean
    public ResponseParser<Object> responseParser() {
        return new JsonResponseParser();
    }

    @Bean
    public RequestSender<String, Object> requestSender(HttpClient httpClient) {
        return new HttpRequestSender(httpClient, CONF.getProperty(HETEROGENEOUS_REQUEST_URL));
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientFactory.createClient();
    }

    @Bean
    public GenericService genericService(
            Serializer<String> serializer,
            ResponseParser<Object> responseParser,
            RequestSender<String, Object> requestSender
    ) {
        return new Dubbo2HeterogeneousService(serializer, responseParser, requestSender);
    }

    @Bean
    public RegisterConfig registerConfig() {

        RegisterConfig registerConfig = new RegisterConfig();

        Application application = new Application();
        application.setName(CONF.getProperty(APPLICATION_NAME));
        application.setOrganization(CONF.getProperty(APPLICATION_ORGANIZATION));

        Protocol protocol = new Protocol();
        protocol.setName(CONF.getProperty(PROTOCOL_NAME));
        protocol.setPort(Integer.valueOf(CONF.getProperty(PROTOCOL_PORT)));

        Registry registry = new Registry();
        registry.setProtocol(CONF.getProperty(REGISTRY_PROTOCOL));
        registry.setAddress(CONF.getProperty(REGISTRY_ADDRESS));
        registry.setPort(Integer.valueOf(CONF.getProperty(REGISTRY_PORT)));

        registerConfig.setApplication(application);
        registerConfig.setProtocol(protocol);
        registerConfig.setRegistry(registry);

        return registerConfig;
    }

    @Bean
    public RegistryService registryService(RegisterConfig registerConfig,
            GenericService genericService) {
        return new DubboRegistryService(registerConfig, genericService);
    }

    @Bean
    public Heterogeneous2DubboService heterogeneous2DubboService(RegisterConfig registerConfig,
            ResponseParser<Object> responseParser) {
        return new Heterogeneous2DubboService(registerConfig, responseParser);
    }

    @Bean
    public SidecarService sidecarService(RegistryService registryService,
            Heterogeneous2DubboService heterogeneous2DubboService) {
        return new DubboSidecarService(registryService, heterogeneous2DubboService);
    }
}
