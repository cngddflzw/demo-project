package com.zim.demo.motan.sample.consumer.web;

import com.ecwid.consul.v1.ConsistencyMode;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.zim.demo.motan.api.DemoService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenwei.liu
 * @since 2018-07-11
 */
@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("/demo/{name}")
    public String demo(@PathVariable String name) {
        return demoService.hello(name);
    }

    public static void main(String[] args) {

        String serviceName = "motanrpc_default_rpc";
        boolean onlyPass = true;
        QueryParams params = QueryParams.Builder.builder()
                .setConsistencyMode(ConsistencyMode.DEFAULT)
                .setWaitTime(600)
                .setIndex(180)
                .build();

        ConsulClient client = new ConsulClient();
        Response<List<HealthService>> response = client.getHealthServices(serviceName, onlyPass, params);

        System.out.println(response.getValue());
    }
}
