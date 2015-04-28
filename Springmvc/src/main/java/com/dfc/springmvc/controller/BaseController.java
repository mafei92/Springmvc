package com.dfc.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dfc.springmvc.pojo.UserInfo;

/**
 * 实现公共模块相关功能
 * 
 * @author dongdong
 * 
 */

@Controller
public class BaseController {

	@Autowired
	private HttpSession session;

	/**
	 * 用户信息加入session
	 * 
	 * @param userInfo
	 */
	public void setUserInfo(UserInfo userInfo) {
		if (null != userInfo) {
			session.setAttribute("userInfo", userInfo);
			session.setMaxInactiveInterval(1800);// 设置session超时时间
		}
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @return
	 */
	public UserInfo getUserInfo() {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		return (userInfo != null ? userInfo : null);
	}

	/**
	 * userLevel列表
	 * 
	 * @return
	 */
	public Map<String, String> userLevelMap() {
		Map<String, String> userLevels = new HashMap<String, String>();
		userLevels.put("0", "系统管理员");
		userLevels.put("1", "普通用户");
		return userLevels;
	}

	/**
	 * menuType列表
	 * 
	 * @return
	 */
	public Map<String, String> menuTypeMap() {
		Map<String, String> menuTypes = new HashMap<String, String>();
		menuTypes.put("system", "系统菜单");
		menuTypes.put("web", "网站链接");
		return menuTypes;
	}

}
