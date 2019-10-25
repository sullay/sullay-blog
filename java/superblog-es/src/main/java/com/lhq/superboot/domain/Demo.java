package com.lhq.superboot.domain;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Description: es 测试demo实体类
 * 
 * @author: lihaoqi
 * @date: 2019年4月15日
 * @version: 1.0.0
 */
@Document(indexName = "lhq_es_demo_info", type = "doc")
public class Demo {
	
	private String id;

	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
