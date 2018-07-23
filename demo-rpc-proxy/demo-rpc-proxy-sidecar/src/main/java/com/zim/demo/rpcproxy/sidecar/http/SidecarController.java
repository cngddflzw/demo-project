package com.zim.demo.rpcproxy.sidecar.http;

import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import com.zim.demo.rpcproxy.sidecar.core.SidecarService;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@RestController
@RequestMapping(path = "/sidecar",
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json")
public class SidecarController {

    @Resource
    private SidecarService sidecarService;

    @PostMapping("/export")
    public Object export(@Valid @RequestBody DefaultServiceInfo serviceInfo) {
        sidecarService.export(serviceInfo);
        return InvocationUtils.createSuccessfulResult(null);
    }

    @PostMapping("/unexport")
    public Object unexport(@Valid @RequestBody DefaultServiceInfo serviceInfo) {
        sidecarService.unexport(serviceInfo);
        return InvocationUtils.createSuccessfulResult(null);
    }

    @PostMapping("/refer")
    public Object refer(@Valid @RequestBody DefaultServiceInfo serviceInfo) {
        sidecarService.refer(serviceInfo);
        return InvocationUtils.createSuccessfulResult(null);
    }

    @PostMapping("/invoke")
    public Object invoke(@Valid @RequestBody DefaultInvocation invocation) {
        return sidecarService.invoke(invocation);
    }
}
