package com.dfc.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.UserInfo;

/**
 * user持久层相关接口
 * 
 * @author dongdong
 *
 */

@Repository
public interface UserInfoDao {

	public UserInfo login(@Param("userName") String userName, @Param("password") String password);

	public List<UserInfo> queryUsers(RowBounds rowBounds, Map<String, String> map);

	public List<UserInfo> queryUsers();

	public int getTotalRows(Map<String, String> map);

	public UserInfo getUser(String userId);

	public void saveUser(UserInfo userInfo);

	public void updateUser(UserInfo userInfo);

	public void deleteUser(String[] idItem);

	public int checkUserName(String userName);

	public List<UserInfo> getAllUsers();

	public UserInfo downloadFile(String userId);

	public int deleteFile(String userId);

	public int deleteDeptId(String deptId);

}