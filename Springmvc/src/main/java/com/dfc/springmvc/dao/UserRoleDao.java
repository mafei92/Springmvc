package com.dfc.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.UserRole;

/**
 * UserRole�־ò���ؽӿ�
 * 
 * @author dongdong
 * 
 */

@Repository
public interface UserRoleDao {

	public void saveUserRole(List<UserRole> list);

	public void deleteUserRoleByUserId(String userId);
	
	public void deleteUserRoleByRoleId(String roleId);

}