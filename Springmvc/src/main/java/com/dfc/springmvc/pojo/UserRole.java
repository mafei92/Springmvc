package com.dfc.springmvc.pojo;

import java.io.Serializable;

/**
 * �û���ɫ�м��ģ��
 * 
 * @author dongdong
 */

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6437484369214269004L;
	private String userRoleId;
	private String userId;
	private String roleId;

	public UserRole() {
		super();
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}