package com.zim.demo.kafka.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhenwei.liu
 * @since 2019-08-12
 */
public class ConsumerDemo2 {

	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(new ConsumerTask());
		Thread.sleep(Long.MAX_VALUE);
	}
}
