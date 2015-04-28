package com.dfc.springmvc.service;

import java.util.List;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.Menu;

/**
 * 数据处理接口
 * 
 * @author dongdong
 *
 */

public interface MenuService {

	public List<Menu> getUserMenus(String userId);

	public List<Menu> queryAllMenus(PageInfo<Menu> pageInfo, Menu menu);

	public List<Menu> querySystemMenus(PageInfo<Menu> pageInfo, Menu menu);

	public Menu getMenu(String menuId);

	public List<Menu> getAllMenus();

	public List<Menu> getAllSystemMenus();

	public List<Menu> getAllWebMenus();

	public int saveMenu(Menu menu);

	public int updateMenu(Menu menu);

	public int deleteMenu(String menuId);

	public int getMaxMenuOrder();

	public int deleteRoleMenuByMenuId(String menuId);

	public List<String> getMenuTypes();

}
