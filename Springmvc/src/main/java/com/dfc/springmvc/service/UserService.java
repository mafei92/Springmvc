package com.dfc.springmvc.service;

import java.util.List;
import java.util.Map;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.UserInfo;

/**
 * 数据处理接口
 * 
 * @author dongdong
 * 
 */

public interface UserService {

	public UserInfo login(String userName, String password);

	public List<UserInfo> queryUsers(PageInfo<UserInfo> pageInfo, Map<String, String> map);

	public List<UserInfo> queryForJson();

	public UserInfo getUser(String userId);

	public void saveUser(UserInfo userInfo);

	public void updateUser(UserInfo userInfo);

	public void deleteUser(String[] idItem);

	public int checkUserName(String userName);

	public List<UserInfo> getAllUsers();

	public UserInfo downloadFile(String userId);

	public int deleteFile(String userId);

	public int saveUserRole(String userId_role, String roleIds);
	
	public void deleteUserRoleByUserId(String userId);

}
