package org.heyu.homework1306.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author heyu
 * @date 2021/9/21
 */
@Data
@NoArgsConstructor
public class Kmq {

	private String topic;

	private AtomicLong pushOffset;

	private AtomicLong pullOffset;

	private List<Object> messageList;

	public Kmq(String topic) {
		this.topic = topic;
		this.pushOffset = new AtomicLong(0);
		this.pullOffset = new AtomicLong(0);
		this.messageList = new ArrayList<Object>(100);
	}

	public void pull(Object object) {
		if (object == null) {
			return;
		}
		messageList.add((int) pushOffset.getAndDecrement(), object);
	}

	public Object pull() {
		Object object = null;
		try {
			object = messageList.get((int) pullOffset.getAndIncrement());
		} catch (Exception e) {
			e.printStackTrace();
			pullOffset.getAndDecrement();
		}
		return object;
	}

}
