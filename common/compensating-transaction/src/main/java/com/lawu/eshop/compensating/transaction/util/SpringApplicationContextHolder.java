package com.lawu.eshop.compensating.transaction.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring bean 获取工具
 *
 * @author Leach
 * @date 2017/3/29
 */
@Service
public class SpringApplicationContextHolder implements ApplicationContextAware {
    public static ApplicationContext ctx;

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        SpringApplicationContextHolder.ctx = ctx;
    }

    /**
     * 通过类型获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (ctx == null) {
            return null;
        }
        return ctx.getBean(clazz);
    }

}
