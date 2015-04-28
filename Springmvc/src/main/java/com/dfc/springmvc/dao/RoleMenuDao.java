package com.dfc.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.RoleMenu;

/**
 * RoleMenu持久层相关接口
 * 
 * @author dongdong
 * 
 */

@Repository
public interface RoleMenuDao {

	int saveRoleMenu(List<RoleMenu> list);

	int deleteRoleMenuByMenuId(String menuId);

	int deleteRoleMenuByRoleId(String roleId);

}