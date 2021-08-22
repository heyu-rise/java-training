package io.kimmking.rpcfx.demo.consumer.aop;

import java.lang.annotation.*;

/**
 *
 * @author heyu
 * @date 2021/8/22
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RpcAnno {

    String url();



}
