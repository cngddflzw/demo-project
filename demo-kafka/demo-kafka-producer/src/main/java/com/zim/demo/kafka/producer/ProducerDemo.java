package com.zim.demo.kafka.producer;

import com.google.common.util.concurrent.RateLimiter;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhenwei.liu
 * @since 2019-08-09
 */
public class ProducerDemo {

	private static final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);

	public static void main(String[] args) {
		logger.info("process id: {}", ManagementFactory.getRuntimeMXBean().getName());
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
			RateLimiter rateLimiter = RateLimiter.create(1);
			int i = 0;
			String topic = "zhenwei-topic";
			String key = "test-key";
			while (true) {
				rateLimiter.acquire();
				String val = "test-val " + i;
				int partition = i++ % 4;
				producer.send(new ProducerRecord<>(topic, partition, key, val),
						(metadata, exception) -> {
							if (exception != null) {
								logger.error("send message error partition {} topic {} key {} value {}", partition,
										topic, key, val, exception);
							} else {
								logger.info("send message successfully partition {} topic {} key {} value {}",
										partition, topic, key, val);
							}
						});
//				producer.send(new ProducerRecord<>("zhenwei-topic", "test-key", "test-val " + i));
			}
		}

	}
}
