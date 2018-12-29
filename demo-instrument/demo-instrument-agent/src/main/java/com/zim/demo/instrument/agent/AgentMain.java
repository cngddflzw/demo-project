package com.zim.demo.instrument.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author zhenwei.liu
 * @since 2018-12-21
 */
public class AgentMain {

	private static Instrumentation inst;
	/** 命令行启动 */
	public static void premain(String agentArgs, Instrumentation instrumentation) {
		inst = instrumentation;
	}

	/** 类加载调用 */
	public static void agentmain(String agentArgs, Instrumentation instrumentation) {
		inst = instrumentation;
	}

	public static Instrumentation instrumentation() {
		return inst;
	}
}
