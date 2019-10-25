package com.lhq.superboot.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 手机校验码qo
 * 
 * @author: lihaoqi
 * @date: 2019年4月29日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCodeQo {

	private String phone;
	
	private String code;
}
