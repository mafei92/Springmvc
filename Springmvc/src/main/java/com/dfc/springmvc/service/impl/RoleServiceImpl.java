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
 * ���ݴ������ʵ��
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
			log.info("�����ɫ");
			roleDao.saveRole(role);
			if (StringUtil.isNotEmpty(role.getRoleId()) && StringUtil.isNotEmpty(menuIds)) {
				log.info("�����ɫ�˵���ϵ");
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
			log.info("��ɫ����ʧ��");
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
			log.info("���½�ɫ");
			roleDao.updateRole(role);
			roleMenuDao.deleteRoleMenuByRoleId(role.getRoleId());
			if (StringUtil.isNotEmpty(role.getRoleId()) && StringUtil.isNotEmpty(menuIds)) {
				log.info("�����ɫ�˵���ϵ");
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
			log.info("��ɫ����ʧ��");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteRole(String roleId) {
		try {
			log.info("ɾ���û���ɫ������ϵ");
			userRoleDao.deleteUserRoleByRoleId(roleId);
			log.info("ɾ����ɫ�˵�������ϵ");
			roleMenuDao.deleteRoleMenuByRoleId(roleId);
			log.info("ɾ����ɫ");
			roleDao.deleteRole(roleId);
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("��ɫɾ��ʧ��");
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
