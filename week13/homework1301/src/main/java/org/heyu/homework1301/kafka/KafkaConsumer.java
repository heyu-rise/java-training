package org.heyu.homework1301.kafka;

import org.heyu.homework1301.model.User;
import org.heyu.homework1301.utils.JsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2020/5/27
 */
@Slf4j
@Component
public class KafkaConsumer {

	@KafkaListener(topics = "heyu-string", groupId = "heyu1")
	public void string(String message) {
		log.info(message);
	}

	@KafkaListener(topics = "heyu-string", groupId = "heyu2")
	public void string2(String message) {
		log.info(message);
	}

	@KafkaListener(topics = "heyu-object", groupId = "heyu-object1")
	public void object(String message) {
		User user = JsonUtil.json2obj(message, User.class);
		log.info(user.toString());
	}

	@KafkaListener(topics = "heyu-object", groupId = "heyu-object2")
	public void object2(String message) {
		User user = JsonUtil.json2obj(message, User.class);
		log.info(user.toString());
	}

}
