package com.lhq.superboot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.Resource;
import com.lhq.superboot.domain.ResourceExample;
import com.lhq.superboot.domain.RoleResource;
import com.lhq.superboot.domain.RoleResourceExample;
import com.lhq.superboot.enums.ConstEnumUtils.IS_DELETE;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.enums.LoginSource;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.ResourceMapper;
import com.lhq.superboot.mapper.RoleResourceMapper;
import com.lhq.superboot.repository.UserRepository;
import com.lhq.superboot.service.ResourceService;
import com.lhq.superboot.service.ShiroService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.shiro.LocalRealm;
import com.lhq.superboot.util.StringUtils;

/**
 * @Description: 资源实现
 * 
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private ResourceMapper resMapper;

	@Autowired
	private RoleResourceMapper roleResMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocalRealm localRealm;

	@Autowired
	private ShiroService shiroService;

	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	@Override
	public Page<Resource> selectResourceByPage(String resName, String resUrl, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		ResourceExample resExample = new ResourceExample();
		ResourceExample.Criteria criteria = resExample.createCriteria();
		if (null != resName) {
			criteria.andResNameLike("%" + resName + "%");
		}
		if (null != resUrl) {
			criteria.andResUrlLike("%" + resUrl + "%");
		}
		criteria.andResChannelNotEqualTo(LoginSource.XCX.name()).andIsEnabledEqualTo(IS_ENABLED.YES.value()).andIsDeletedEqualTo(IS_DELETE.NO.value());
		resExample.setOrderByClause("`create_time` DESC");
		Page<Resource> resPage = (Page<Resource>) resMapper.selectByExample(resExample);

		return resPage;
	}

	@Override
	@Transactional
	public void createResource(Resource res) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}
		
		Date date = new Date();
		String resName = res.getResName();
		String resCode = res.getResCode();
		String resUrl = res.getResUrl();

		Resource resParam = new Resource().toBuilder().resCode(resCode).resName(resName).resUrl(resUrl).createTime(date)
				.createUser(userId).modifyTime(date).modifyUser(userId).isDeleted(IS_DELETE.NO.value())
				.isEnabled(IS_ENABLED.YES.value()).build();
		resMapper.insertSelective(resParam);
		shiroService.updatePermission(shiroFilterFactoryBean);
	}

	@Override
	@Transactional
	public void updateResource(Resource res) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}
		
		Resource resParam = new Resource().toBuilder().resId(res.getResId()).modifyTime(new Date()).modifyUser(userId)
				.build();
		if (StringUtils.isNotEmpty(res.getResName())) {
			resParam.setResName(res.getResName());
		}
		if (StringUtils.isNotEmpty(res.getResCode())) {
			resParam.setResCode(res.getResCode());
		}
		if (StringUtils.isNotEmpty(res.getResUrl())) {
			resParam.setResUrl(res.getResUrl());
		}
		resMapper.updateByPrimaryKeySelective(resParam);
		shiroService.updatePermission(shiroFilterFactoryBean);
	}

	@Override
	@Transactional
	public void disableResource(List<String> resIdList, Integer isEnabled) {
		Date date = new Date();
		
		ResourceExample resExample = new ResourceExample();
		resExample.createCriteria().andResIdIn(resIdList);
		Resource resource = new Resource().toBuilder().isEnabled(isEnabled).modifyTime(date).build();
		resMapper.updateByExampleSelective(resource, resExample);
		shiroService.updatePermission(shiroFilterFactoryBean);
	}

	@Override
	@Transactional
	public void deleteResource(List<String> resIdList) {
		Date date = new Date();
		
		ResourceExample resExample = new ResourceExample();
		resExample.createCriteria().andResIdIn(resIdList);
		Resource resource = new Resource().toBuilder().isDeleted(IS_DELETE.YES.value()).modifyTime(date).build();
		resMapper.updateByExampleSelective(resource, resExample);
		shiroService.updatePermission(shiroFilterFactoryBean);
	}

	@Override
	public Page<Resource> selectRoleRes(String roleId, int pageNum, int pageSize) {
		RoleResourceExample roleResExample = new RoleResourceExample();
		roleResExample.createCriteria().andRoleIdEqualTo(roleId).andIsEnabledEqualTo(IS_ENABLED.YES.value());
		PageHelper.startPage(pageNum, pageSize);
		List<RoleResource> roleResList = roleResMapper.selectByExample(roleResExample);

		if (roleResList.isEmpty()) {
			return null;
		}

		ResourceExample resExample = new ResourceExample();
		resExample.createCriteria()
				.andResIdIn(roleResList.stream().map(roleRes -> roleRes.getResId()).collect(Collectors.toList()))
				.andIsDeletedEqualTo(IS_DELETE.NO.value()).andIsEnabledEqualTo(IS_ENABLED.YES.value());
		PageHelper.startPage(pageNum, pageSize);
		Page<Resource> resPage = (Page<Resource>) resMapper.selectByExample(resExample);
		return resPage;
	}

	@Override
	@Transactional
	public void createRoleRes(String roleId, List<String> resIdList) {
		Date date = new Date();
		List<String> roleResIdList = new ArrayList<String>(resIdList.size());

		for (String resId : resIdList) {
			RoleResource roleResParam = new RoleResource().toBuilder().resId(resId).roleId(roleId).createTime(date)
					.modifyTime(date).isEnabled(IS_ENABLED.YES.value()).build();
			roleResMapper.insertSelective(roleResParam);
			roleResIdList.add(roleResParam.getRoleResId());
		}
		clearAuthorization(roleResIdList);
	}

	@Override
	@Transactional
	public void disableRoleRes(List<String> roleResIdList, Integer isEnabled) {
		RoleResourceExample roleReseExample = new RoleResourceExample();
		roleReseExample.createCriteria().andRoleResIdIn(roleResIdList);
		RoleResource roleRes = new RoleResource().toBuilder().isEnabled(isEnabled).build();
		roleResMapper.updateByExampleSelective(roleRes, roleReseExample);
		clearAuthorization(roleResIdList);
	}

	@Override
	@Transactional
	public void deleteRoleRes(List<String> roleResIdList) {
		RoleResourceExample roleReseExample = new RoleResourceExample();
		roleReseExample.createCriteria().andRoleResIdIn(roleResIdList);
		// 这里注意先清除缓存再删除，否则删除后没有索引找到缓存记录
		clearAuthorization(roleResIdList);
		roleResMapper.deleteByExample(roleReseExample);
	}
	
	@Override
	@Transactional
	public void deleteRoleRes(String roleId, List<String> resIdList) {
		RoleResourceExample roleResExample = new RoleResourceExample();
		roleResExample.createCriteria().andRoleIdEqualTo(roleId).andResIdIn(resIdList);
		List<RoleResource> roleResList = roleResMapper.selectByExample(roleResExample);
		if (roleResList.isEmpty()) {
			return;
		}
		// 获取需要删除的roleResId列表
		List<String> roleResIdList = roleResList.stream().map(roleRes -> roleRes.getRoleResId()).collect(Collectors.toList());
		deleteRoleRes(roleResIdList);
	}

	/**
	 * 清除资源权限对应的用户的redis数据权限
	 */
	private void clearAuthorization(List<String> roleResIdList) {
		logger.debug("[ResourceService] -> 清除权限{}", roleResIdList);
		Set<String> userIdList = new HashSet<String>(128);
		for (String roleResId : roleResIdList) {
			userIdList.addAll(userRepository.selectUserByRoleResId(roleResId));
		}
		logger.debug("[ResourceService] -> 拥有权限:{}的用户有:{}", roleResIdList, userIdList);
		if (userIdList.isEmpty()) {
			return;
		}
		// 清除缓存权限数据
		Cache<Object, AuthorizationInfo> authorizationCache = localRealm.getAuthorizationCache();
		if (authorizationCache != null) {
			for (String userId : userIdList) {
				authorizationCache.remove(userId);
			}
		}
	}

}
