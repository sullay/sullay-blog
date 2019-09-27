package com.sullay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class Article {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  @Column(nullable=false)
	  private String name;
	  private Date createTime;
	  private Date updateTime;
	  @Lob
	  private String context;
	  
	  @ManyToOne
	  @JoinColumn(name="aid")
	  private Anthology anthology;
}
