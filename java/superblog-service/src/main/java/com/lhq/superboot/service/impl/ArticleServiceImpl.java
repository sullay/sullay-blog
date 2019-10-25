package com.lhq.superboot.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.Article;
import com.lhq.superboot.domain.ArticleExample;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.ArticleMapper;
import com.lhq.superboot.service.ArticleService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.StringUtils;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final String TOURIST = "tourist";

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private UserService userService;

	@Override
	public void create(Article article) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			userId = TOURIST;
		}

		Article param = new Article().toBuilder().aid(article.getAid()).id(article.getId())
				.context(article.getContext()).name(article.getName()).createUser(userId).build();
		articleMapper.insertSelective(param);
	}

	@Override
	public void update(Article article) {
		Date time = new Date();
		article.setUpdateTime(time);

		articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	public void delete(Article article) {
		int articleId = article.getId();
		String userId = userService.getCurrentUserId();
		String createUserId = findById(articleId).getCreateUser();
		if (StringUtils.isEmpty(createUserId) || TOURIST.equals(createUserId) || userId.equals(createUserId)) {
			articleMapper.deleteByPrimaryKey(articleId);
		} else {
			throw new SuperBootException("该文章您无权限修改");
		}
	}

	@Override
	public Article findById(int id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<Article> findPage(Integer anthologyId, int page, int size) {
		PageHelper.startPage(page, size);

		ArticleExample articleExample = new ArticleExample();
		ArticleExample.Criteria criteria = articleExample.createCriteria();
		if (null != anthologyId) {
			criteria.andAidEqualTo(anthologyId);
		}
		articleExample.setOrderByClause("create_time desc");
		Page<Article> articlePage = (Page<Article>) articleMapper.selectByExample(articleExample);

		return articlePage;
	}

}
