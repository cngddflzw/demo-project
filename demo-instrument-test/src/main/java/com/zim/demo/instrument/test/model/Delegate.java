package com.zim.demo.instrument.test.model;

/**
 * @author zhenwei.liu
 * @since 2019-01-29
 */
public class Delegate implements Model {

	@Override
	public String hello(String name) {
		return "hello " + name;
	}
}
