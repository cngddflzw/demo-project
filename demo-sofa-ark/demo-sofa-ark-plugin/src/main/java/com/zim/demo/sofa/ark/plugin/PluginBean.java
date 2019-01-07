package com.zim.demo.sofa.ark.plugin;

/**
 * @author zhenwei.liu
 * @since 2019-01-07
 */
public class PluginBean {

	// no public setter for jackson test

	private String name;
	private int age;

	public PluginBean(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}
