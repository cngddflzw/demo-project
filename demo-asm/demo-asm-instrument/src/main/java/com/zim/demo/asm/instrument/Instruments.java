package com.zim.demo.asm.instrument;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;
import com.zim.demo.asm.agent.AgentMain;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import sun.tools.attach.BsdVirtualMachine;
import sun.tools.attach.LinuxVirtualMachine;
import sun.tools.attach.SolarisVirtualMachine;
import sun.tools.attach.WindowsVirtualMachine;

/**
 * @author zhenwei.liu
 * @since 2018-12-21
 */
public class Instruments {

	private static AtomicBoolean started = new AtomicBoolean(false);
	private static Splitter atSp = Splitter.on("@");

	private static final AttachProvider attachProvider = new AttachProvider() {
		@Override
		public String name() {
			return null;
		}

		@Override
		public String type() {
			return null;
		}

		@Override
		public VirtualMachine attachVirtualMachine(String id) {
			return null;
		}

		@Override
		public List<VirtualMachineDescriptor> listVirtualMachines() {
			return null;
		}
	};

	public static void start() {
		if (started.compareAndSet(false, true)) {
			try {
				VirtualMachine vm = getVirtualMachine();
				vm.loadAgent(getAgentPath(), null);
				Instrumentation inst = instrumentation();

				intercept(inst, TimingInterceptor.class);

				vm.detach();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static VirtualMachine getVirtualMachine()
			throws IOException, AttachNotSupportedException {
		VirtualMachine vm;

		if (AttachProvider.providers().isEmpty()) {
			String vmName = System.getProperty("java.vm.name");

			if (vmName.contains("HotSpot")) {
				vm = getVirtualMachineImplementationFromEmbeddedOnes();
			} else {
				String helpMessage = getHelpMessageForNonHotSpotVM(vmName);
				throw new IllegalStateException(helpMessage);
			}
		} else {
			vm = VirtualMachine.attach(getPid());
		}

		return vm;
	}

	private static String getHelpMessageForNonHotSpotVM(String vmName) {
		String helpMessage = "To run on " + vmName;

		if (vmName.contains("J9")) {
			helpMessage += ", add <IBM SDK>/lib/tools.jar to the runtime classpath (before jmockit), or";
		}

		return helpMessage + " use -javaagent:" + getAgentPath();
	}

	private static String getPid() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = atSp.split(name).iterator().next();
		Preconditions.checkArgument(Long.valueOf(pid) != 0, "获取 pid 失败");
		return pid;
	}

	private static String getAgentPath() {
		ProtectionDomain domain = AgentMain.class.getProtectionDomain();
		CodeSource source = domain.getCodeSource();
		return new File(source.getLocation().getPath()).getAbsolutePath();
	}

	private static Instrumentation instrumentation() {
		ClassLoader mainAppLoader = ClassLoader.getSystemClassLoader();
		try {
			final Class<?> javaAgentClass = mainAppLoader.loadClass(AgentMain.class.getCanonicalName());
			final Method method = javaAgentClass.getDeclaredMethod("instrumentation", new Class[0]);
			return (Instrumentation) method.invoke(null, new Object[0]);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private static void intercept(Instrumentation inst, Class<?> interceptor) {
		new AgentBuilder.Default()
				.type(ElementMatchers.nameEndsWith("Timed"))
				.transform((builder, typeDescription, classLoader, javaModule) ->
						builder.method(ElementMatchers.any())
				.intercept(MethodDelegation.to(interceptor)))
				.installOn(inst);
	}

	private static VirtualMachine getVirtualMachineImplementationFromEmbeddedOnes() {
		Class<? extends VirtualMachine> vmClass = findVirtualMachineClassAccordingToOS();
		Class<?>[] parameterTypes = {AttachProvider.class, String.class};
		String pid = getPid();

		try {
			// This is only done with Reflection to avoid the JVM pre-loading all the XyzVirtualMachine classes.
			Constructor<? extends VirtualMachine> vmConstructor = vmClass.getConstructor(parameterTypes);
			return vmConstructor.newInstance(attachProvider, pid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<? extends VirtualMachine> findVirtualMachineClassAccordingToOS() {
		if (File.separatorChar == '\\') {
			return WindowsVirtualMachine.class;
		}

		String osName = System.getProperty("os.name");

		if (osName.startsWith("Linux") || osName.startsWith("LINUX")) {
			return LinuxVirtualMachine.class;
		} else if (osName.startsWith("Mac OS X")) {
			return BsdVirtualMachine.class;
		} else if (osName.startsWith("Solaris")) {
			return SolarisVirtualMachine.class;
		}

		throw new IllegalStateException("Cannot use Attach API on unknown OS: " + osName);
	}

}
