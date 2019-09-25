package com.sullay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sullay.model.Article;
import com.sullay.model.Msg;
import com.sullay.service.ArticleService;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	  ArticleService articleService;
	  @PostMapping("/create")
	  public Msg create(@RequestBody Article article) {
		  try {
			  articleService.create(article);
			  return Msg.success();
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "创建或更新文章失败").add("errInfo", e.toString());
		  }
	  }
	  @PostMapping("/delete")
	  public Msg delete(@RequestBody Article article) {
		  try {
			  articleService.delete(article);
			  return Msg.success();
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "删除文章失败").add("errInfo", e.toString());
		  }
	  }
	  @GetMapping("/findById")
	  public Msg findPage(@RequestParam(value="id") int id){
		  try {
			  Article article = articleService.findById(id);
			  return Msg.success().add("data", article);
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "查询文章失败").add("errInfo", e.toString());
		  }
	  }
	  @GetMapping("/findPage")
	  public Msg findPage(@RequestParam(value="AnthologyId",required = false) Integer AnthologyId,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="10") int size){
		  try {
			  System.out.println(AnthologyId);
			  Page<Article> articlePage = articleService.findPage(AnthologyId, page, size);
			  return Msg.success().add("data", articlePage);
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "查询文章列表失败").add("errInfo", e.toString());
		  }
	  }
}
