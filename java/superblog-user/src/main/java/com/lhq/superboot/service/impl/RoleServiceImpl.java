package com.lhq.superboot.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.Role;
import com.lhq.superboot.domain.RoleExample;
import com.lhq.superboot.enums.ConstEnumUtils.IS_DELETE;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.RoleMapper;
import com.lhq.superboot.service.RoleService;
import com.lhq.superboot.service.UserService;

/**
 * @Description: 权限相关接口实现
 * 
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	/** pc默认权限id **/
	private static final String DEFAULT_PC_ROLE_ID = "1";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Page<Role> selectRoleByPage(String roleName, boolean isContainPC, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		RoleExample roleExample = new RoleExample();
		RoleExample.Criteria criteria = roleExample.createCriteria();
		if (null != roleName) {
			criteria.andRoleNameLike("%" + roleName + "%");
		}
		if (!isContainPC) {
			criteria.andRoleIdNotEqualTo(DEFAULT_PC_ROLE_ID).andIsEnabledEqualTo(IS_ENABLED.YES.value()).andIsDeletedEqualTo(IS_DELETE.NO.value());
		} else {
			criteria.andIsEnabledEqualTo(IS_ENABLED.YES.value()).andIsDeletedEqualTo(IS_DELETE.NO.value());
			
		}
		Page<Role> rolePage = (Page<Role>) roleMapper.selectByExample(roleExample);

		return rolePage;
	}

	@Override
	public void createRole(String roleName, String roleKey, Integer roleSort, Integer dataScope) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}

		Date date = new Date();
		Role roleParam = new Role().toBuilder().roleKey(roleKey).roleName(roleName).roleSort(roleSort)
				.dataScope(dataScope).createTime(date).createUser(userId).modifyTime(date).modifyUser(userId)
				.isDeleted(IS_DELETE.NO.value()).isEnabled(IS_ENABLED.YES.value()).build();
		roleMapper.insertSelective(roleParam);
	}

	@Override
	public void updateRole(Role role) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}
		
		Role roleParam = new Role().toBuilder().roleId(role.getRoleId()).modifyTime(new Date()).modifyUser(userId)
				.build();
		if (!StringUtils.isEmpty(role.getRoleName())) {
			roleParam.setRoleName(role.getRoleName());
		}
		if (!StringUtils.isEmpty(role.getRoleKey())) {
			roleParam.setRoleKey(role.getRoleKey());
		}
		if (null != role.getRoleSort()) {
			roleParam.setRoleSort(role.getRoleSort());
		}
		if (null != role.getDataScope()) {
			roleParam.setDataScope(role.getDataScope());
		}
		roleMapper.updateByPrimaryKeySelective(roleParam);
	}

	@Override
	public void disableRole(List<String> roleIdList, Integer isEnabled) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andRoleIdIn(roleIdList);
		Role role = new Role().toBuilder().isEnabled(isEnabled).build();
		roleMapper.updateByExampleSelective(role, roleExample);
	}

	@Override
	public void deleteRole(List<String> roleIdList) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andRoleIdIn(roleIdList);
		Role role = new Role().toBuilder().isDeleted(IS_DELETE.YES.value()).build();
		roleMapper.updateByExampleSelective(role, roleExample);
	}

}
