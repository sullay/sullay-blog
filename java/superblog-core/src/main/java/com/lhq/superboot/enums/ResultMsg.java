package com.lhq.superboot.enums;

/**
 * @Description: 响应码枚举，返回信息
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public enum ResultMsg {

   // 正常调用
   SUCCESS("调用成功"),
   
   FAIL("服务器异常");

 
   private String code;
 
   ResultMsg(String code) {
      this.code = code;
   }
   
   public String getCode() {
       return code;
   }
   
}
