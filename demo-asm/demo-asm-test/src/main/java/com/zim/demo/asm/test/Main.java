package com.zim.demo.asm.test;

import com.zim.demo.asm.instrument.Instruments;

/**
 * 为了让 agent jar 生效, 需要达成可执行 jar
 * 否则无法获取 jar 路径, 因为使用的是源码编译出来的 class
 *
 * @author zhenwei.liu
 * @since 2018-12-22
 */
public class Main {

	public static void main(String[] args) {
		new TestTimed().test();
		Instruments.start();
		new TestTimed().test();
	}
}
