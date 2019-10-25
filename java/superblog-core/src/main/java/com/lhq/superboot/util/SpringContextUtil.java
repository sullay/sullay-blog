package com.lhq.superboot.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.lhq.superboot.enums.EnvProfile;

/**
 * @Description: 获取bean的工具类，可用于在线程里面获取bean
 *
 * @author: lihaoqi 
 * @date: 2019年8月27日 下午3:46:06 
 * @version: v1.0.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    
    private static ApplicationContext context = null;
    
    /* 
     * @Description: spring获取bean工具类
     * 
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }
 
    /**
     * @Description: 传入线程中
     *
     * @param <T>
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }
    
    /**
     * @Description: 获取某个类型下的所有请求方法
     *
     * @param <T>
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return (T) context.getBean("requestMappingHandlerMapping", requiredType);
    }
 
    /**
     * @Description: 国际化使用
     *
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
 
    /**
     * @Description: 获取当前环境
     *
     * @return
     */
    public static String getActiveProfile() {
        String activePro = null;
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            for (EnvProfile profile : EnvProfile.values()) {
                if (activeProfile.equals(profile.getCode())) {
                    activePro = profile.getCode();
                }
            }
        }
        return activePro == null ? EnvProfile.PROFILE_DEV.getCode() : activePro;
    }
}
