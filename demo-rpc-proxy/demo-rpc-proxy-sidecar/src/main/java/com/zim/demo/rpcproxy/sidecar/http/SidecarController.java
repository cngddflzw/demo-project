package com.zim.demo.rpcproxy.sidecar.http;

import com.zim.demo.rpcproxy.api.InvocationService;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
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
    private InvocationService invocationService;

    @PostMapping("/invoke")
    public Object invoke(@Valid @RequestBody DefaultInvocation invocation) {
        return invocationService.invoke(invocation);
    }
}
