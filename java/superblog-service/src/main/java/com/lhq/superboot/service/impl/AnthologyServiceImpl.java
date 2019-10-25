package com.lhq.superboot.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.Anthology;
import com.lhq.superboot.domain.AnthologyExample;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.AnthologyMapper;
import com.lhq.superboot.service.AnthologyService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.StringUtils;

@Service
public class AnthologyServiceImpl implements AnthologyService {
	
	private static final String TOURIST = "tourist";
	
	@Autowired
	private AnthologyMapper anthologyMapper;
	
	@Autowired
	private UserService userService;

	@Override
	public void create(Anthology anthology) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			userId = TOURIST;
		}

		Anthology param = new Anthology().toBuilder().name(anthology.getName()).createUser(userId).build();
		anthologyMapper.insertSelective(param);
	}

	@Override
	public void update(Anthology anthology) {
		Date time = new Date();
		anthology.setUpdateTime(time);
		
		anthologyMapper.updateByPrimaryKey(anthology);
	}
	
	@Override
	public void delete(Anthology anthology) {
		int anthologyId = anthology.getId();
		String userId = userService.getCurrentUserId();
		String createUserId =  findById(anthologyId).getCreateUser();
		if (StringUtils.isEmpty(createUserId) || TOURIST.equals(createUserId) || userId.equals(createUserId)) {
			anthologyMapper.deleteByPrimaryKey(anthologyId);
		} else {
			throw new SuperBootException("该文章您无权限删除");
		}
	}
	
	@Override
	public Anthology findById(int id) {
		return anthologyMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<Anthology> findPage(int page, int size) {
		PageHelper.startPage(page, size);

		AnthologyExample anthologyExample = new AnthologyExample();
		// AnthologyExample.Criteria criteria = anthologyExample.createCriteria();
		anthologyExample.setOrderByClause("create_time desc");
		Page<Anthology> anthologyePage = (Page<Anthology>) anthologyMapper.selectByExample(anthologyExample);

		return anthologyePage;
	}

}
