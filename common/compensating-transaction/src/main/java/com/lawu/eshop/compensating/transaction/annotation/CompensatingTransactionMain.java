package com.lawu.eshop.compensating.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 补偿性事务主逻辑注解
 * @author Leach
 * @date 2017/3/28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompensatingTransactionMain {

    byte value();

    int seconds() default 60;

    String topic();

    String tags() default "";
}
