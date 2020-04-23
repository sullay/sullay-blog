package com.lhq.superboot.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Article;

/**
 * @Description: 
 *
 * @author: lihaoqi 
 * @date: 2019年10月23日 下午3:12:13 
 * @version: v1.0.0
 */
public interface ArticleService {

	/**
	 * @Description: 
	 *
	 * @param article
	 */
	public void create(Article article);
	
	/**
	 * @Description: 
	 *
	 * @param article
	 */
	public void update(Article article);

	/**
	 * @Description: 
	 *
	 * @param article
	 */
	public void delete(Article article);

	/**
	 * @Description: 
	 *
	 * @param id
	 * @return
	 */
	public Article findById(int id);

	/**
	 * @Description: 
	 *
	 * @param AnthologyId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Article> findPage(Integer AnthologyId, int page, int size);

	/**
	 * @Description:
	 *
	 * @param AnthologyId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Article> findPageByUser(Integer AnthologyId, int page, int size);


	/**
	 * @Description: 
	 *
	 * @param id
	 * @param size
	 * @return
	 */
	public List<Article> findMyPage(int id, int size);


}
