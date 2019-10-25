package com.lhq.superboot.qo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户授权qo
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserGrantQo {
	
	private List<String> users;
	
	private String roleId;

}
