package com.dfc.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfc.springmvc.pojo.Menu;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.service.MenuService;
import com.dfc.springmvc.service.UserService;
import com.dfc.springmvc.util.StringUtil;

/**
 * 实现公共模块相关功能
 * 
 * @author dongdong
 * 
 */

@Controller
@RequestMapping("/common")
// @SessionAttributes("user")
public class CommonController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	/**
	 * 登录
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(/* @ModelAttribute("user") */UserInfo userInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo user = userService.login(userInfo.getUserName(), userInfo.getPassword());
		if (null != user) {
			log.info("登录成功");
			super.setUserInfo(user);
			map.put("result", "success");
		} else {
			log.info("登录失败");
			map.put("result", "failed");
		}
		return map;
	}

	/**
	 * 进入主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMain", method = RequestMethod.GET)
	public String toMain(Model model) {
		UserInfo userInfo = super.getUserInfo();
		String userId = userInfo.getUserId();
		List<String> menuTypes = menuService.getMenuTypes();
		List<Menu> webMenus = menuService.getAllWebMenus();
		List<Menu> systemMenus = null;
		if (userInfo.getUserName().equals("system")) {
			systemMenus = menuService.getAllSystemMenus();
		} else if (StringUtil.isNotEmpty(userId)) {
			systemMenus = menuService.getUserMenus(userId);
		}
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("menuTypes", menuTypes);
		model.addAttribute("systemMenus", systemMenus);
		model.addAttribute("webMenus", webMenus);
		return "/main";
	}

}
