package com.zim.demo.sofa.ark.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.zim.demo.sofa.ark.plugin.PluginBean;
import com.zim.demo.sofa.ark.plugin.PluginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSofaArkBizApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoSofaArkBizApplication.class, args);

		PluginBean bean = new PluginBean("zhenwei", 18);
		PluginService pluginService = new PluginService();
		System.out.println("### Bean CL: " + bean.getClass().getClassLoader());
		System.out.println("### Service CL: " + pluginService.getClass().getClassLoader());
		System.out.println(pluginService.hello(bean));

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(new AfterburnerModule());
		String s = objectMapper.writeValueAsString(bean);
		System.out.println("Json string: " + s);
	}

}

