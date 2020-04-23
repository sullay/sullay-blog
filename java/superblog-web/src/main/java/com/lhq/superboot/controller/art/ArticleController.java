package com.lhq.superboot.controller.art;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lhq.superboot.domain.Article;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.service.ArticleService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.StringUtils;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public Object create(@RequestBody Article article) {
		if (StringUtils.isEmpty(article.getAid()) ) {
			throw new SuperBootException("aid不能为空");
		}
		articleService.create(article);
		return "创建成功";
	}
	
	@PostMapping("/update")
	public Object update(@RequestBody Article article) {
		if (StringUtils.isEmpty(article.getId()) ) {
			throw new SuperBootException("id不能为空");
		}
		
		articleService.update(article);
		return "更新成功";
	}

	@PostMapping("/delete")
	public Object delete(@RequestBody Article article) {
		if (StringUtils.isEmpty(article.getId()) ) {
			throw new SuperBootException("id不能为空");
		}
		articleService.delete(article);
		return "删除成功";
	}

	@GetMapping("/findById")
	public Object findPage(@RequestParam(value = "id") int id) {
		return articleService.findById(id);
	}

	@GetMapping("/findPage")
	public Object findPage(@RequestParam(value = "AnthologyId", required = false) Integer anthologyId, PageInfo<Article> page) {
		if (StringUtils.isEmpty(anthologyId) ) {
			throw new SuperBootException("aid不能为空");
		}
		
		Page<Article> articlePage = articleService.findPage(anthologyId, page.getPageNum(), page.getPageSize());
		page.setList(articlePage.getResult());
        page.setTotal(articlePage.getTotal());
		return page;
	}
	
	@GetMapping("/findAllPage")
	public Object findPage(PageInfo<Article> page) {
		Page<Article> articlePage = articleService.findPage(null, page.getPageNum(), page.getPageSize());
		page.setList(articlePage.getResult());
        page.setTotal(articlePage.getTotal());
		return page;
	}

	@GetMapping("/findPageByUser")
	public Object findMyAllPage(@RequestParam(value = "AnthologyId", required = false) Integer anthologyId, PageInfo<Article> page) {
		Page<Article> articlePage = articleService.findPageByUser(anthologyId, page.getPageNum(), page.getPageSize());
		page.setList(articlePage.getResult());
		page.setTotal(articlePage.getTotal());
		return page;
	}
	
	@GetMapping("/findMyPage")
	public Object findMyPage(Integer id, Integer pageSize) {
		if (StringUtils.isEmpty(id) ) {
			throw new SuperBootException("id不能为空");
		}
		if (StringUtils.isEmpty(pageSize) ) {
			throw new SuperBootException("页面数量不能为空");
		}
		return articleService.findMyPage(id, pageSize);
	}
}
