package com.sullay.model.temp;

import com.sullay.model.Article;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
public class ArticleTemp extends Article {
	  private String passWord;
}
