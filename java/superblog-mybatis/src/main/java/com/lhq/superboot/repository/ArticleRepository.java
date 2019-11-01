package com.lhq.superboot.repository;

import java.util.List;

import com.lhq.superboot.domain.Article;

/**
 * @Description: 文章接口
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public interface ArticleRepository {

	/**
	 * @Description: 资源权限查询
	 * 
	 * @param id
	 * @param size
	 * @return
	 */
	public List<Article> selectByIdAndSize(int id, int size);


}
