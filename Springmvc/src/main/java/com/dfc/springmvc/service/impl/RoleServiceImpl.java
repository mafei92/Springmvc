package com.dfc.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.dao.RoleDao;
import com.dfc.springmvc.dao.RoleMenuDao;
import com.dfc.springmvc.dao.UserRoleDao;
import com.dfc.springmvc.pojo.Role;
import com.dfc.springmvc.pojo.RoleMenu;
import com.dfc.springmvc.service.RoleService;
import com.dfc.springmvc.util.ParameterUtil;
import com.dfc.springmvc.util.StringUtil;

/**
 * 数据处理具体实现
 * 
 * @author dongdong
 *
 */

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Role> queryRoles(PageInfo<Role> pageInfo, Role role) {
		if (pageInfo != null) {
			pageInfo.setTotalRecords(roleDao.getTotalRows(role));
		}
		RowBounds rowBounds = new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize());
		return roleDao.queryRoles(rowBounds, role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveRole(Role role, String menuIds) {
		try {
			log.info("保存角色");
			roleDao.saveRole(role);
			if (StringUtil.isNotEmpty(role.getRoleId()) && StringUtil.isNotEmpty(menuIds)) {
				log.info("保存角色菜单关系");
				List<RoleMenu> rmList = new ArrayList<RoleMenu>();
				if (StringUtil.isNotEmpty(menuIds)) {
					String[] menuId = menuIds.split(",");
					for (String temp : menuId) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setRoleId(role.getRoleId());
						roleMenu.setMenuId(temp);
						rmList.add(roleMenu);
					}
				}
				roleMenuDao.saveRoleMenu(rmList);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色保存失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Role getRoleByRoleId(String roleId) {
		return roleDao.getRoleByRoleId(roleId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateRole(Role role, String menuIds) {
		try {
			log.info("更新角色");
			roleDao.updateRole(role);
			roleMenuDao.deleteRoleMenuByRoleId(role.getRoleId());
			if (StringUtil.isNotEmpty(role.getRoleId()) && StringUtil.isNotEmpty(menuIds)) {
				log.info("保存角色菜单关系");
				List<RoleMenu> rmList = new ArrayList<RoleMenu>();
				if (StringUtil.isNotEmpty(menuIds)) {
					String[] menuId = menuIds.split(",");
					for (String temp : menuId) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setRoleId(role.getRoleId());
						roleMenu.setMenuId(temp);
						rmList.add(roleMenu);
					}
				}
				roleMenuDao.saveRoleMenu(rmList);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色更新失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteRole(String roleId) {
		try {
			log.info("删除用户角色关联关系");
			userRoleDao.deleteUserRoleByRoleId(roleId);
			log.info("删除角色菜单关联关系");
			roleMenuDao.deleteRoleMenuByRoleId(roleId);
			log.info("删除角色");
			roleDao.deleteRole(roleId);
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色删除失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Role> getRolesByUserId(String userId) {
		return roleDao.getRolesByUserId(userId);
	}

}
