package com.zim.demo.rpcproxy.sidecar.core;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import com.zim.demo.rpcproxy.common.GsonHolder;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
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

    @Value("${service.config.file}")
    private String serviceConfigFile;

    @Value("${reference.config.file}")
    private String referenceConfigFile;

    @Override
    protected List<DefaultServiceInfo> loadServiceConfig() {
        return listServiceInfo(serviceConfigFile);
    }

    @Override
    protected List<DefaultServiceInfo> loadReferenceConfig() {
        return listServiceInfo(referenceConfigFile);
    }

    private List<DefaultServiceInfo> listServiceInfo(String fileName) {
        try {
            return GsonHolder.gson()
                    .fromJson(Files.toString(loadConfigFile(fileName), Charsets.UTF_8), TR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File loadConfigFile(String fileName) {
        String path = this.getClass().getResource("LocalFileServiceInitializer.class").toString();
        boolean isInJar = path.startsWith("jar:") || path.startsWith("rsrc:");
        try {
            if (isInJar) {
                // jar 运行从 jar 所在文件夹读取文件
                File jarDir = new File(
                        this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
                                .getPath()).getParentFile();
                return new File(jarDir, fileName);
            } else {
                // 从项目 resources 文件夹读取
                URL resource = Preconditions
                        .checkNotNull(this.getClass().getClassLoader().getResource(fileName),
                                "config file not found %s", fileName);
                return new File(resource.toURI());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
