package com.zim.demo.rpcproxy.common;

import java.io.File;

/**
 * @author zhenwei.liu
 * @since 2018-08-15
 */
public class PathUtils {

    public static boolean isInJar() {
        String path = PathUtils.class.getResource("PathUtils.class").toString();
        return path.startsWith("jar:") || path.startsWith("rsrc:");
    }

    public static File getJarDir() {
        // file:/root/zim-test/rpc-proxy-test/rpc-proxy.jar!/BOOT-INF/classes!/
        String sourceCodeDir = PathUtils.class.getProtectionDomain().getCodeSource().getLocation()
                .getPath();

        int jarIndex = sourceCodeDir.indexOf(".jar!");
        if (jarIndex == -1) {
            return null;
        }
        String s1 = sourceCodeDir.substring(0, jarIndex);
        String jarDir = sourceCodeDir.substring(0, s1.lastIndexOf(File.separator));

        if (jarDir.startsWith("file:")) {
            jarDir = jarDir.replace("file:", "");
        }

        return new File(jarDir);
    }
}
