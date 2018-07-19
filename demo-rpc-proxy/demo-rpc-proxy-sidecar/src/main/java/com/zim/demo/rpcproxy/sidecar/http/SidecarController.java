package com.zim.demo.rpcproxy.sidecar.http;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.core.SidecarService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@RestController
@RequestMapping("/sidecar")
public class SidecarController {

    @Resource
    private SidecarService sidecarService;

    @RequestMapping("/register")
    public void register(ServiceInfo serviceInfo) {
        sidecarService.register(serviceInfo);
    }

    @RequestMapping("/unregister")
    public void unregister(ServiceInfo serviceInfo) {
        sidecarService.unregister(serviceInfo);
    }

    @RequestMapping("/refer")
    public void refer(ServiceInfo serviceInfo) {
        sidecarService.refer(serviceInfo);
    }

    @RequestMapping("/invoke")
    public Object invoke(Invocation invocation) {
        return sidecarService.invoke(invocation);
    }
}
