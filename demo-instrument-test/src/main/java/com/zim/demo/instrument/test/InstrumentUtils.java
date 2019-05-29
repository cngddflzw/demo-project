package com.zim.demo.instrument.test;

import com.google.common.base.Splitter;
import com.sun.tools.attach.VirtualMachine;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

/**
 * @author zhenwei.liu
 * @since 2019-02-21
 */
public class InstrumentUtils {

	public static Instrumentation getInst() throws Exception {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = Splitter.on('@').split(name).iterator().next();
		VirtualMachine vm = VirtualMachine.attach(pid);
		vm.loadAgent(ThreadInstrumentTest.class.getClassLoader().getResource("instrument-agent.jar").getPath());

		Class<?> agentClazz = Class.forName("com.zim.demo.instrument.agent.AgentMain");
		Field instField = agentClazz.getDeclaredField("inst");
		instField.setAccessible(true);
		Instrumentation inst = (Instrumentation) instField.get(agentClazz);
		return inst;
	}
}
