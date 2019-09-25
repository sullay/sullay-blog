package com.sullay.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.sullay.model.Anthology;
import com.sullay.repository.AnthologyRepository;

@Service
public class AnthologyService {
	@Autowired
	AnthologyRepository anthologyRepository;
	public void create(Anthology anthology) {
		System.out.println(anthology.toString());
		anthology.setUpdateTime(new Date());
		if(anthology.getId()== null) {
			anthology.setCreateTime(new Date());
		}
		anthologyRepository.save(anthology);
	  }
	  public void delete(Anthology anthology) {
		  anthologyRepository.delete(anthology);
	  }
	  public Page<Anthology> findPage(int page,int size){
		  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "createTime")));
		  return anthologyRepository.findAll(pageable);
	  }
}
