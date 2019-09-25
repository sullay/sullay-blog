package com.sullay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sullay.model.Anthology;

public interface AnthologyRepository extends JpaRepository<Anthology,Integer>, JpaSpecificationExecutor<Anthology> {
	
}