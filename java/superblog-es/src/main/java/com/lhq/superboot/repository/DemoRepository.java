package com.lhq.superboot.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.lhq.superboot.domain.Demo;

/**
 * @Description: es dao层接口
 *
 * @author: lihaoqi
 * @date: 2019年4月15日
 * @version: 1.0.0
 */
@NoRepositoryBean
public interface DemoRepository extends ElasticsearchRepository<Demo, String> {

}
