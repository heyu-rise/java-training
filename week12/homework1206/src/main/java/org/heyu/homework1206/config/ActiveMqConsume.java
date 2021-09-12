package org.heyu.homework1206.config;

import lombok.extern.slf4j.Slf4j;
import org.heyu.homework1206.model.User;
import org.heyu.homework1206.utils.JsonUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2020/5/25
 */
@Slf4j
@Component
public class ActiveMqConsume {

	@JmsListener(destination = "heyu-test-string")
	public void stringListener(String message) {
		log.info(message);
	}

	@JmsListener(destination = "heyu-test-object")
	public void objectListener(User user) {
		log.info(JsonUtil.obj2json(user));
	}

	@JmsListener(destination = "heyu-topic-string", containerFactory = "topicFactory")
	public void topicString1(String user) {
		log.info(user);
	}

	@JmsListener(destination = "heyu-topic-object", containerFactory = "topicFactory")
	public void topicObject1(User user) {
		log.info(JsonUtil.obj2json(user));
	}

	@JmsListener(destination = "heyu-topic-string", containerFactory = "topicFactory")
	public void topicString2(String user) {
		log.info(user);
	}

	@JmsListener(destination = "heyu-topic-object", containerFactory = "topicFactory")
	public void topicObject2(User user) {
		log.info(JsonUtil.obj2json(user));
	}
}
