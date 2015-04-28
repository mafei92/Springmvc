package com.dfc.springmvc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.Dept;
import com.dfc.springmvc.pojo.Role;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.service.DeptService;
import com.dfc.springmvc.service.RoleService;
import com.dfc.springmvc.service.UserService;
import com.dfc.springmvc.util.DownloadUtil;
import com.dfc.springmvc.util.ParameterUtil;
import com.dfc.springmvc.util.StringUtil;

/**
 * ʵ���û���Ϣ��ع���
 * 
 * @author dongdong
 * 
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private RoleService roleService;

	/**
	 * ��ѯ�û���Ϣ
	 * 
	 * @param model
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping(value = "/queryUser")
	public String queryUser(Model model, @ModelAttribute(value = "pageInfo") PageInfo<UserInfo> pageInfo,
			@ModelAttribute(value = "user") UserInfo userInfo, @ModelAttribute(value = "dept") Dept dept) {
		log.info("��ѯ�û��б�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("trueName", userInfo.getTrueName());
		map.put("mail", userInfo.getMail());
		map.put("deptNo", dept.getDeptNo());
		map.put("userLevel", userInfo.getUserLevel());
		List<UserInfo> list = userService.queryUsers(pageInfo, map);
		model.addAttribute("list", list);
		model.addAttribute("userLevel", super.getUserInfo().getUserLevel());
		model.addAttribute("userLevelMap", super.userLevelMap());
		return "/user/query";
	}

	/**
	 * ��ѯ�û���Ϣ��json��ʽ��
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryForJson", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryForJson(String page, String rows) {
		log.info("��ѯ�û��б�");
		// ��ǰҳ
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// ÿҳ��ʾ����
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		Map<String, String> map = new HashMap<String, String>();
		List<UserInfo> list = userService.queryUsers(pageInfo, map);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("rows", list);// rows�� ���ÿҳ��¼ list
		jsonMap.put("total", pageInfo.getTotalRecords());// �ܼ�¼��
		return jsonMap;
	}

	/**
	 * ��ת����û�ҳ��
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String add(Model model) {
		log.info("�������ҳ��");
		model.addAttribute("user", new UserInfo());
		model.addAttribute("userLevelMap", super.userLevelMap());
		return "/user/add";
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param userInfo
	 * @param fileData
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String save(UserInfo userInfo, @RequestParam("fileData") MultipartFile fileData) {
		try {
			if (null != userInfo) {
				if (fileData.getSize() != 0) {
					String fileName = fileData.getOriginalFilename();
					byte[] fileByte = fileData.getBytes();
					userInfo.setFileName(fileName);
					userInfo.setFileByte(fileByte);
				}
				userInfo.setCreateTime(new Date());
				userInfo.setUpdateTime(new Date());
				userService.saveUser(userInfo);
				log.info("�����û���Ϣ�ɹ�");
			}
		} catch (Exception e) {
			log.info("�����û���Ϣʧ��");
			log.info(e.getCause());
			// throw new RuntimeException();
		}
		return "redirect:queryUser.do";
	}

	/**
	 * ��ת�޸��û�ҳ��
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/editUser")
	public String editUser(Model model, String userId) {
		log.info("����༭ҳ��");
		UserInfo userInfo = userService.getUser(userId);
		String deptId = userInfo.getDeptId();
		if (StringUtil.isNotEmpty(deptId)) {
			Dept dept = deptService.getDeptByDeptId(deptId);
			model.addAttribute("deptName", dept.getDeptName());
		}
		model.addAttribute("user", userInfo);
		model.addAttribute("userLevelMap", super.userLevelMap());
		return "/user/update";
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param userInfo
	 * @param fileData
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(UserInfo userInfo, @RequestParam("fileData") MultipartFile fileData) {
		try {
			if (StringUtil.isNotEmpty((userInfo.getUserId()))) {
				if (null != fileData && fileData.getSize() != 0) {
					String fileName = fileData.getOriginalFilename();
					byte[] fileByte = fileData.getBytes();
					userInfo.setFileName(fileName);
					userInfo.setFileByte(fileByte);
				}
				userInfo.setUpdateTime(new Date());
				userService.updateUser(userInfo);
				log.info("�����û���Ϣ�ɹ�");
			}
		} catch (Exception e) {
			log.info("�����û���Ϣʧ��");
			log.info(e.getCause());
		}
		return "redirect:queryUser.do";
	}

	/**
	 * ɾ���û�
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(Model model, String userId) {
		if (StringUtil.isNotEmpty(userId)) {
			String[] idItem = userId.split(",");
			log.info("ɾ���û�");
			userService.deleteUser(idItem);
		}
		return "redirect:queryUser.do";
	}

	/**
	 * ����û����Ƿ��ظ�
	 * 
	 * @param userName
	 * @return map
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> checkUserName(String userName) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (StringUtil.isNotEmpty(userName)) {
			int count = userService.checkUserName(userName);
			map.put("count", count);
		}
		return map;
	}

	/**
	 * �ļ�����
	 * 
	 * @param userId
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(String userId, final HttpServletResponse response) {
		UserInfo userFile = userService.downloadFile(userId);
		String fileName = userFile.getFileName();
		byte[] fileByte = userFile.getFileByte();
		DownloadUtil.downloadFile(response, fileName, fileByte);
	}

	/**
	 * �ļ�ɾ��
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteFile(String userId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int result = userService.deleteFile(userId);
		map.put("result", result);
		return map;
	}

	/**
	 * Ȩ�޷���
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/allotUserRole", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> allotUserRole(String userId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		UserInfo userInfo = userService.getUser(userId);
		List<Role> roles = roleService.getAllRoles();
		List<Role> haves = roleService.getRolesByUserId(userId);
		for (Role role : roles) {
			if (haves != null && haves.size() > 0) {
				for (Role have : haves) {
					if (role.getRoleId().equals(have.getRoleId())) {
						role.setUserHave(1);
					}
				}
			}
		}
		jsonMap.put("userInfo", userInfo);
		jsonMap.put("roles", roles);
		return jsonMap;
	}

	/**
	 * ����Ȩ�޷���
	 * 
	 * @param userId_role
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> saveUserRole(String userId_role, String roleIds) {
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		int result = userService.saveUserRole(userId_role, roleIds);
		jsonMap.put(ParameterUtil.RESULT, result);
		return jsonMap;
	}

}
