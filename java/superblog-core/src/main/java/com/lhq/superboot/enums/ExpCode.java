package com.lhq.superboot.enums;

/**
 * @Description: 异常编码
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public enum ExpCode {

   // 正常调用
   GOBALEXCEPTION("Gobal"),
   
   AOPEXCEPTION("Aop");

 
   private String code;
 
   ExpCode(String code) {
      this.code = code;
   }
   
   public String getCode() {
       return code;
   }
   
}
