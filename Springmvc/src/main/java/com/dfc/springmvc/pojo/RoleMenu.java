package com.dfc.springmvc.pojo;

import java.io.Serializable;

/**
 * 角色菜单中间表模型
 * 
 * @author dongdong
 */

public class RoleMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 450960236138742302L;

	private String id;

	private String roleId;

	private String menuId;

	private Menu menu;

	public RoleMenu() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}