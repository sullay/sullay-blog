package com.lhq.superboot.enums;

/**
 * @Description: 环境
 *
 * @author: lihaoqi 
 * @date: 2019年8月27日 下午4:48:15 
 * @version: v1.0.0
 */
public enum EnvProfile {
    
    // 测试环境
    PROFILE_TEST("test"),
    
    // 正式环境
    PROFILE_PROD("prod"),
    
    // 开发环境
    PROFILE_DEV("dev");

    private String code;
  
    EnvProfile(String code) {
       this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
