package com.dfc.springmvc.controller;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.Menu;
import com.dfc.springmvc.service.MenuService;
import com.dfc.springmvc.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单相关功能
 * 
 * @author dongdong
 * 
 */

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/queryMenu")
	public String queryMenu(Model model, PageInfo<Menu> pageInfo, Menu menu) {
		log.info("查询菜单记录");
		List<Menu> menuList = menuService.queryAllMenus(pageInfo, menu);
		model.addAttribute("menuList", menuList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("menu", menu);
		return "/menu/query";
	}

	/**
	 * 跳转添加菜单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	public String add(Model model) {
		log.info("进入添加页面");
		Menu menu = new Menu();
		menu.setMenuOrder(menuService.getMaxMenuOrder() + 1);
		model.addAttribute("menu", menu);
		model.addAttribute("menuTypes", super.menuTypeMap());
		return "/menu/add";
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> saveMenu(Menu menu) {
		int result = menuService.saveMenu(menu);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 跳转修改菜单页面
	 * 
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/editMenu", method = RequestMethod.POST)
	public String editMenu(Model model, String menuId) {
		log.info("进入编辑页面");
		Menu menu = menuService.getMenu(menuId);
		if (null != menu) {
			model.addAttribute("menu", menu);
		}
		model.addAttribute("menuTypes", super.menuTypeMap());
		return "/menu/update";
	}

	/**
	 * 更新菜单信息
	 *
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> updateMenu(Menu menu) {
		int result = menuService.updateMenu(menu);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 删除菜单
	 * 
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteMenu(Model model, String menuId) {
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		if (StringUtil.isNotEmpty(menuId)) {
			menuService.deleteRoleMenuByMenuId(menuId);// 删除菜单前，删除所有拥有此菜单的角色菜单关联
			int result = menuService.deleteMenu(menuId);
			jsonMap.put("result", result);
			log.info("删除菜单成功");
		}
		return jsonMap;
	}

	/**
	 * 获取所有菜单
	 * 
	 * @param page
	 * @param rows
	 * @param menuName
	 * @return
	 */
	@RequestMapping(value = "/getAllMenus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> allotMenu(String page, String rows, String menuName) {
		log.info("查询菜单");
		// 当前页
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		PageInfo<Menu> pageInfo = new PageInfo<Menu>();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		Menu menu = new Menu();
		if (StringUtil.isNotEmpty(menuName)) {
			menu.setMenuName(menuName);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Menu> menuList = menuService.querySystemMenus(pageInfo, menu);
		jsonMap.put("rows", menuList);
		jsonMap.put("total", pageInfo.getTotalRecords());
		return jsonMap;
	}

}
