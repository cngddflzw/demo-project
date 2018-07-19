package com.zim.demo.rpcproxy.sidecar.config;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class RegisterConfig {

    private Application application;
    private Registry registry;
    private Protocol protocol;

    public Application getApplication() {
        return application;
    }

    public RegisterConfig setApplication(Application application) {
        this.application = application;
        return this;
    }

    public Registry getRegistry() {
        return registry;
    }

    public RegisterConfig setRegistry(Registry registry) {
        this.registry = registry;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public RegisterConfig setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }
}
