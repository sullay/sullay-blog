package com.lhq.superboot.vo;

import java.util.Date;

import com.lhq.superboot.domain.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 资源信息dto
 *
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResMsgVo {

	private String resId;
	private String resName;
	private String resCode;
	private String resUrl;
	private String resChannel;
	private String modifyUser;
	private Date modifyTime;

	public static ResMsgVo convert(Resource resource) {
		ResMsgVo resMsg = new ResMsgVo().toBuilder().resId(resource.getResId()).resName(resource.getResName())
				.resCode(resource.getResCode()).resUrl(resource.getResUrl()).resChannel(resource.getResChannel())
				.modifyTime(resource.getModifyTime()).modifyUser(resource.getModifyUser()).build();
		return resMsg;
	}

}
