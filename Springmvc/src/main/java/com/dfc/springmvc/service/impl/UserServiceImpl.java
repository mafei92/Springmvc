package com.dfc.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.dao.DeptDao;
import com.dfc.springmvc.dao.UserInfoDao;
import com.dfc.springmvc.dao.UserRoleDao;
import com.dfc.springmvc.pojo.Dept;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.pojo.UserRole;
import com.dfc.springmvc.service.UserService;
import com.dfc.springmvc.util.ParameterUtil;
import com.dfc.springmvc.util.StringUtil;

/**
 * ���ݴ������ʵ��
 * 
 * @author dongdong
 * 
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserRoleDao userRoleDaol;
	@Autowired
	private DeptDao deptDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserInfo login(String userName, String password) {
		log.info("��֤�û�");
		return userInfoDao.login(userName, password);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserInfo> queryUsers(PageInfo<UserInfo> pageInfo, Map<String, String> map) {
		if (pageInfo != null) {
			log.info("���÷�ҳ");
			pageInfo.setTotalRecords(userInfoDao.getTotalRows(map));
		}
		log.info("��ȡ��¼");
		return userInfoDao.queryUsers(new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize()), map);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<UserInfo> queryForJson() {
		log.info("��ȡ��¼");
		return userInfoDao.queryUsers();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserInfo getUser(String userId) {
		return userInfoDao.getUser(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveUser(UserInfo userInfo) {
		userInfoDao.saveUser(userInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateUser(UserInfo userInfo) {
		userInfoDao.updateUser(userInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUser(String[] idItem) {
		List<Dept> depts = null;
		for (String userId : idItem) {
			log.info("ɾ���û�ǰ��ɾ���û����еĽ�ɫ");
			this.deleteUserRoleByUserId(userId);
			log.info("����Ƿ��ǲ��ž�������ǣ����������ڲ��Ų��ž���Ϊ��");
			depts = deptDao.getDeptByDeptManager(userId);
			if (depts != null && depts.size() > 0) {
				for (Dept dept : depts) {
					dept.setDeptManager("");
					deptDao.updateDept(dept);
				}
			}
		}
		userInfoDao.deleteUser(idItem);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int checkUserName(String userName) {
		return userInfoDao.checkUserName(userName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<UserInfo> getAllUsers() {
		return userInfoDao.getAllUsers();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserInfo downloadFile(String userId) {
		return userInfoDao.downloadFile(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteFile(String userId) {
		return userInfoDao.deleteFile(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveUserRole(String userId_role, String roleIds) {
		try {
			log.info("����Ȩ��");
			if (StringUtil.isNotEmpty(userId_role) && StringUtil.isNotEmpty(roleIds)) {
				userRoleDaol.deleteUserRoleByUserId(userId_role);
				List<UserRole> list = new ArrayList<UserRole>();
				String[] roleId = roleIds.split(",");
				for (String temp : roleId) {
					UserRole userRole = new UserRole();
					userRole.setUserId(userId_role);
					userRole.setRoleId(temp);
					list.add(userRole);
				}
				userRoleDaol.saveUserRole(list);
			} else if (StringUtil.isNotEmpty(userId_role) && StringUtil.isEmpty(roleIds)) {
				userRoleDaol.deleteUserRoleByUserId(userId_role);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("Ȩ�޷���ʧ��");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUserRoleByUserId(String userId) {
		userRoleDaol.deleteUserRoleByUserId(userId);
	}

}
