package com.dfc.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dfc.springmvc.pojo.UserInfo;

/**
 * ʵ�ֹ���ģ����ع���
 * 
 * @author dongdong
 * 
 */

@Controller
public class BaseController {

	@Autowired
	private HttpSession session;

	/**
	 * �û���Ϣ����session
	 * 
	 * @param userInfo
	 */
	public void setUserInfo(UserInfo userInfo) {
		if (null != userInfo) {
			session.setAttribute("userInfo", userInfo);
			session.setMaxInactiveInterval(1800);// ����session��ʱʱ��
		}
	}

	/**
	 * ��session�л�ȡ�û���Ϣ
	 * 
	 * @return
	 */
	public UserInfo getUserInfo() {
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		return (userInfo != null ? userInfo : null);
	}

	/**
	 * userLevel�б�
	 * 
	 * @return
	 */
	public Map<String, String> userLevelMap() {
		Map<String, String> userLevels = new HashMap<String, String>();
		userLevels.put("0", "ϵͳ����Ա");
		userLevels.put("1", "��ͨ�û�");
		return userLevels;
	}

	/**
	 * menuType�б�
	 * 
	 * @return
	 */
	public Map<String, String> menuTypeMap() {
		Map<String, String> menuTypes = new HashMap<String, String>();
		menuTypes.put("system", "ϵͳ�˵�");
		menuTypes.put("web", "��վ����");
		return menuTypes;
	}

}
