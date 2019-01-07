package com.zim.demo.sofa.ark.plugin;

/**
 * @author zhenwei.liu
 * @since 2019-01-07
 */
public class PluginService {

	public String hello(PluginBean bean) {
		return "hello " + bean.getName() + " " + bean.getAge();
	}
}
