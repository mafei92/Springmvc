package com.dfc.springmvc.pojo;

import java.io.Serializable;

/**
 * ²Ëµ¥Ä£ÐÍ
 * 
 * @author dongdong
 */

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String menuId;
	private String menuName;
	private String menuUri;
	private int menuOrder;
	private String menuDesc;
	private String menuType;

	/**
	 * 
	 */
	public Menu() {
		super();
	}

	/**
	 * 
	 * @param menuName
	 * @param menuUri
	 * @param menuOrder
	 * @param menuDesc
	 * @param userId
	 */
	public Menu(String menuName, String menuUri, int menuOrder, String menuDesc) {
		super();
		this.menuName = menuName;
		this.menuUri = menuUri;
		this.menuOrder = menuOrder;
		this.menuDesc = menuDesc;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

}