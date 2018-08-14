package com.zim.demo.rpcproxy.sidecar.core;

import com.google.gson.reflect.TypeToken;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import com.zim.demo.rpcproxy.common.GsonHolder;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import org.apache.curator.shaded.com.google.common.base.Charsets;
import org.apache.curator.shaded.com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 本地文件读取配置并发布和引用服务
 *
 * @author zhenwei.liu
 * @since 2018-07-23
 */
@Service
public class LocalFileServiceInitializer extends ServiceInitializer {

    private static final Type TR = new TypeToken<List<DefaultServiceInfo>>() {
    }.getType();

    @Value("${service.config.file.path}")
    private String serviceConfigPath;

    @Value("${reference.config.file.path}")
    private String referenceConfigPath;

    @Override
    protected List<DefaultServiceInfo> loadServiceConfig() {
        return listServiceInfo(serviceConfigPath);
    }

    @Override
    protected List<DefaultServiceInfo> loadReferenceConfig() {
        return listServiceInfo(referenceConfigPath);
    }

    private List<DefaultServiceInfo> listServiceInfo(String file) {
        try {
            return GsonHolder.gson().fromJson(Files.toString(new File(Objects.requireNonNull(
                    getClass().getClassLoader().getResource(file)).getFile()),
                    Charsets.UTF_8), TR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
