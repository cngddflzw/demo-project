package com.zim.demo.asm.instrument;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * @author zhenwei.liu
 * @since 2018-12-21
 */
public class TimingInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) {

		long start = System.currentTimeMillis();
		try {
			return callable.call();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			System.out.println(method + " took " + (System.currentTimeMillis() - start));
		}
	}

}
