package org.heyu.week11.lock;

import java.lang.annotation.*;

/**
 * @author heyu
 * @date 2021/9/5
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {

    /**
     * 锁key
     */
    String key();

}
