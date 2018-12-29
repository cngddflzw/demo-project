package com.zim.demo.instrument.client;

import java.lang.instrument.Instrumentation;

/**
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public interface InstrumentTask {

	void run(Instrumentation inst);
}
