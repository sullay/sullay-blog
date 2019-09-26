package com.sullay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class User {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  @Column(unique=true,nullable=false)
	  private String userName;
	  @Column(unique=true,nullable=false)
	  private String passWord;
	  private Date createTime;
}
