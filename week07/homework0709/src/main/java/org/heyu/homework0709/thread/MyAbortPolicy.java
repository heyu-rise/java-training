package org.heyu.homework0709.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 */
@Slf4j
public class MyAbortPolicy implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		throw new RuntimeException("任务繁忙，请稍后重试！");
	}
}
