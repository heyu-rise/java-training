package org.heyu.homework0702.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 */
@Slf4j
public class MyRejectExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("task reject" + executor.toString());
    }
}
