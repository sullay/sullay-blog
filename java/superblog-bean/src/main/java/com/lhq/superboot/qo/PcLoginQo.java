package com.lhq.superboot.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: pc登录qo
 *
 * @author: lihaoqi
 * @version: 1.0.0
 * @date: 2019年4月24日
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PcLoginQo {
	
	private String password;
	
	private String userName;
	
	private String channelFlg;

}
