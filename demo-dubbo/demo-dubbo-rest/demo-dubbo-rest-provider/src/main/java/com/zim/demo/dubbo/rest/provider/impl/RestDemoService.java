package com.zim.demo.dubbo.rest.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zim.demo.dubbo.api.DemoService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author zimliu
 */
@Path("demo")
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class RestDemoService implements DemoService {

    @GET
    @Path("hello")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "Hello Rest Dubbo Service";
    }
}
