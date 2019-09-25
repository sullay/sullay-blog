package com.sullay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
/**
 * 公告管理
 * 
 * 分中心创建上传资源
 *
 */
@Entity
@Data
public class Anthology {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  @Column(unique=true,nullable=false)
	  private String name;
	  private Date createTime;
	  private Date updateTime;
}
