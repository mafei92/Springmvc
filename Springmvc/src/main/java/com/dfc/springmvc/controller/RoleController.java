package com.dfc.springmvc.controller;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.Role;
import com.dfc.springmvc.service.MenuService;
import com.dfc.springmvc.service.RoleService;
import com.dfc.springmvc.util.ParameterUtil;
import com.dfc.springmvc.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ɫ��ع���
 * 
 * @author dongdong
 * 
 */

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/toQueryRoles")
	public String toQueryRoles(Model model) {
		model.addAttribute("role", new Role());
		return "/role/query";
	}

	/**
	 * ��ѯ��ɫ��Ϣ
	 * 
	 * @param model
	 * @param role
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/queryRoles", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryRoles(Model model, Role role, String page, String rows) {
		log.info("��ɫ��ѯ");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// ��ǰҳ
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// ÿҳ��ʾ����
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		PageInfo<Role> pageInfo = new PageInfo<Role>();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		List<Role> roleList = roleService.queryRoles(pageInfo, role);
		jsonMap.put("rows", roleList);// rows�� ���ÿҳ��¼ list
		jsonMap.put("total", pageInfo.getTotalRecords());// �ܼ�¼��
		return jsonMap;
	}

	@RequestMapping(value = "/toAddRole", method = RequestMethod.GET)
	public String toAddRole(Model model) {
		model.addAttribute("role", new Role());
		return "/role/add";
	}

	/**
	 * �����ɫ��Ϣ
	 * 
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> saveRoles(Role role, String menuIds) {
		log.info("�����ɫ��Ϣ");
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		role.setCreateTime(new Date());
		role.setCreator(super.getUserInfo().getUserId());
		int roleResult = roleService.saveRole(role, menuIds);
		jsonMap.put(ParameterUtil.RESULT, roleResult);
		return jsonMap;
	}

	/**
	 * ����roleId��ȡrole
	 * 
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/toEditRole", method = RequestMethod.GET)
	public String toEditRole(Model model, String roleId) {
		Role role = null;
		if (StringUtil.isNotEmpty(roleId)) {
			role = roleService.getRoleByRoleId(roleId);
			model.addAttribute("role", role);
		}
		return "/role/edit";
	}

	/**
	 * ���½�ɫ��Ϣ
	 * 
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateRole(Role role, String menuIds) {
		log.info("���½�ɫ��Ϣ");
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		int roleResult = roleService.updateRole(role, menuIds);
		jsonMap.put(ParameterUtil.RESULT, roleResult);
		return jsonMap;
	}

	/**
	 * ɾ����ɫ��Ϣ
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateRole(String roleId) {
		log.info("ɾ����ɫ��Ϣ");
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		int roleResult = roleService.deleteRole(roleId);
		jsonMap.put(ParameterUtil.RESULT, roleResult);
		return jsonMap;
	}

}
