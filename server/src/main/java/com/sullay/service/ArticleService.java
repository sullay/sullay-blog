package com.sullay.service;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sullay.model.Article;
import com.sullay.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;
	public void create(Article article) {
		article.setUpdateTime(new Date());
		if(article.getId()== null||article.getCreateTime()==null) {
			article.setCreateTime(new Date());
		}
		articleRepository.save(article);
	  }
	  public void delete(Article article) {
		  articleRepository.delete(article);
	  }
	  public Article findById(int id) {
		  return articleRepository.findById(id);
	  }
	  public Page<Article> findPage(Integer AnthologyId, int page, int size){
		  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "createTime")));
		  Specification<Article> specification = new Specification<Article>() {
	          /**
			 * 
			 */
			private static final long serialVersionUID = 6374675777314905474L;

			@Override
	          public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	              Predicate predicate = cb.conjunction();
	              if(AnthologyId!=null) {
	            	  predicate.getExpressions().add(cb.equal(root.get("anthology").get("id"), AnthologyId));
	              }
	              return predicate;
	          }
	      };
		  return articleRepository.findAll(specification, pageable);
	  }
}
