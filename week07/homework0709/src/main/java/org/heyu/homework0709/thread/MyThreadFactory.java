package org.heyu.homework0709.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 */
@Slf4j
public class MyThreadFactory implements ThreadFactory {

    private final String namePrefix;

    private final AtomicLong nextId = new AtomicLong(1);

    public MyThreadFactory(String name) {
        this.namePrefix = name + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = namePrefix + nextId.getAndIncrement();
        return new Thread(null, r, name, 0);
    }
}
