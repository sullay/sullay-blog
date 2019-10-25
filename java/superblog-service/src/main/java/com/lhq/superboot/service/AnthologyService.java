package com.lhq.superboot.service;


import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Anthology;

/**
 * @Description: 
 *
 * @author lihaoqi
 *
 */
public interface AnthologyService {

	/**
	 * @Description: 
	 *
	 * @param anthology
	 */
	public void create(Anthology anthology);
	
	/**
	 * @Description: 
	 *
	 * @param anthology
	 */
	public void update(Anthology anthology);

	/**
	 * @Description: 
	 *
	 * @param anthology
	 */
	public void delete(Anthology anthology);

	/**
	 * @Description: 
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Anthology> findPage(int page, int size);

	/**
	 * @Description: 
	 *
	 * @param id
	 * @return
	 */
	public Anthology findById(int id);

}
