package com.dfc.springmvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.Menu;

/**
 * menu持久层相关接口
 * 
 * @author dongdong
 * 
 */

@Repository
public interface MenuDao {

	public List<Menu> getUserMenus(String userId);

	public List<Menu> queryAllMenus(RowBounds rb, Menu menu);

	public int getTotalRecords(Menu menu);

	public List<Menu> querySystemMenus(RowBounds rb, Menu menu);

	public int getSystemRecords(Menu menu);

	public Menu getMenu(String menuId);

	public List<Menu> getMenusByMenuType(@Param("menuType") String menuType);

	public int saveMenu(Menu menu);

	public int updateMenu(Menu menu);

	public int deleteMenu(String menuId);

	public int getMaxMenuOrder();

	public List<String> getMenuTypes();

}