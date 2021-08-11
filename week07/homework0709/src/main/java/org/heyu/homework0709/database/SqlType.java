package org.heyu.homework0709.database;

import java.lang.annotation.*;

/**
 * @author heyu
 * @date 2021/8/10
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SqlType {

    SqlTypeEnum value() default SqlTypeEnum.READ_ONLY;
}
