package com.zim.demo.rpcproxy.sidecar.bootstrap;

import com.zim.demo.rpcproxy.common.HttpClientFactory;
import com.zim.demo.rpcproxy.sidecar.config.Application;
import com.zim.demo.rpcproxy.sidecar.config.ExportConfig;
import com.zim.demo.rpcproxy.sidecar.config.Protocol;
import com.zim.demo.rpcproxy.sidecar.config.Registry;
import java.util.Objects;
import javax.annotation.Resource;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@Configuration
@PropertySource("classpath:system.properties")
public class SidecarConfiguration {

    private static final String APPLICATION_NAME = "application.name";
    private static final String APPLICATION_ORGANIZATION = "application.organization";

    private static final String PROTOCOL_NAME = "protocol.name";
    private static final String PROTOCOL_PORT = "protocol.port";

    private static final String REGISTRY_PROTOCOL = "registry.protocol";
    private static final String REGISTRY_ADDRESS = "registry.address";
    private static final String REGISTRY_PORT = "registry.port";

    @Resource
    private Environment env;

    @Bean
    public OkHttpClient httpClient() {
        return HttpClientFactory.createClient();
    }

    @Bean
    public ExportConfig registerConfig() {

        ExportConfig exportConfig = new ExportConfig();

        Application application = new Application();
        application.setName(env.getProperty(APPLICATION_NAME));
        application.setOrganization(env.getProperty(APPLICATION_ORGANIZATION));

        Protocol protocol = new Protocol();
        protocol.setName(env.getProperty(PROTOCOL_NAME));
        protocol.setPort(Integer.valueOf(Objects.requireNonNull(env.getProperty(PROTOCOL_PORT))));

        Registry registry = new Registry();
        registry.setProtocol(env.getProperty(REGISTRY_PROTOCOL));
        registry.setAddress(env.getProperty(REGISTRY_ADDRESS));
        registry.setPort(Integer.valueOf(Objects.requireNonNull(env.getProperty(REGISTRY_PORT))));

        exportConfig.setApplication(application);
        exportConfig.setProtocol(protocol);
        exportConfig.setRegistry(registry);

        return exportConfig;
    }
}
