package com.zim.demo.rpcproxy.sidecar.core;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.api.ServiceManager;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;

/**
 * 负责读取服务配置初始化服务
 *
 * @author zhenwei.liu
 * @since 2018-07-23
 */
public abstract class ServiceInitializer<T extends ServiceInfo> implements InitializingBean {

    @Resource
    private ServiceManager<GenericService> serviceManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        initServices();
        initReferences();
    }

    private void initServices() {
        loadServiceConfig().forEach(service -> serviceManager.export(service));
    }

    private void initReferences() {
        loadReferenceConfig().forEach(reference -> serviceManager.refer(reference));
    }

    protected abstract List<T> loadServiceConfig();

    protected abstract List<T> loadReferenceConfig();

}
