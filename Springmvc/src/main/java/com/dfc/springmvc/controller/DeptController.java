package com.dfc.springmvc.controller;

import java.util.ArrayList;
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

import com.dfc.springmvc.common.TreeData;
import com.dfc.springmvc.pojo.Dept;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.service.DeptService;
import com.dfc.springmvc.service.UserService;
import com.dfc.springmvc.util.StringUtil;

/**
 * ʵ�ֲ�����Ϣ��ع���
 * 
 * @author dongdong
 * 
 */

@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private DeptService deptService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/turnToDeptTree")
	public String turnToDeptTree() {
		return "/dept/dept";
	}

	/**
	 * ��֯������
	 * 
	 * @param superId
	 * @return
	 */
	@RequestMapping(value = "/getDeptTree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeData> getDeptTree(String superId) {
		if (StringUtil.isEmpty(superId)) {
			superId = "0";
		}
		log.info("��ѯ����" + superId + "�����в���");
		List<Dept> rootList = deptService.queryDeptBySuperId(superId);
		List<TreeData> treeData = new ArrayList<TreeData>();
		for (Dept dept : rootList) {
			TreeData rootData = new TreeData();
			rootData.setId(dept.getDeptId());
			rootData.setText(dept.getDeptName());
			rootData.setChecked(false);
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("deptNo", dept.getDeptNo());
			attributes.put("ifLeaf", dept.getIfLeaf());
			rootData.setAttributes(attributes);
			List<Dept> childList = deptService.queryDeptBySuperId(dept.getDeptId());
			if (childList.size() == 0 || childList == null) {
				rootData.setState("open");
			} else {
				rootData.setState("closed");
			}
			/*
			 * for (Dept child : childList) { TreeData childData = new
			 * TreeData(); childData.setId(child.getDeptId());
			 * childData.setText(child.getDeptName());
			 * childData.setChecked(false); if (rootData.getChildren() == null)
			 * rootData.setChildren(new ArrayList<TreeData>());
			 * rootData.getChildren().add(childData); }
			 */
			treeData.add(rootData);
		}
		return treeData;
	}

	/**
	 * ���ݲ���id��ȡ������Ϣ
	 * 
	 * @param model
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/getDeptByDeptId", method = RequestMethod.GET)
	public String getDeptBydeptId(Model model, String deptId) {
		Dept dept = null;
		if (StringUtil.isNotEmpty(deptId)) {
			dept = deptService.getDeptByDeptId(deptId);
		}
		Dept superDept = new Dept();
		if (StringUtil.isNotEmpty(dept.getSuperId()) && !dept.getSuperId().equals("0")) {
			superDept = deptService.getDeptByDeptId(dept.getSuperId());
		}
		UserInfo user = new UserInfo();
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			user = userService.getUser(dept.getDeptManager());
		}
		if (user != null) {
			model.addAttribute("trueName", user.getTrueName());
		}
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		return "/dept/view";
	}

	/**
	 * ��ת���ҳ��
	 * 
	 * @param model
	 * @param superId
	 * @return
	 */
	@RequestMapping(value = "/addDept", method = RequestMethod.GET)
	public String addDept(Model model, String superId) {
		Dept dept = new Dept();
		String deptId = deptService.getMaxPrimaryKey();
		dept.setDeptId(deptId);
		String deptNo = deptService.getMaxDeptNo(superId);
		dept.setDeptNo(deptNo);
		Dept superDept = new Dept();
		if (StringUtil.isNotEmpty(superId) && !superId.equals("0")) {
			dept.setSuperId(superId);
			superDept = deptService.getDeptByDeptId(superId);
		}
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		model.addAttribute("userList", userList);
		return "dept/add";
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/saveDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> saveDept(Dept dept) {
		int result = deptService.insertDept(dept);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * ���ݲ���id�༭������Ϣ
	 * 
	 * @param model
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/editDept", method = RequestMethod.GET)
	public String editDept(Model model, String deptId) {
		Dept dept = null;
		if (StringUtil.isNotEmpty(deptId)) {
			dept = deptService.getDeptByDeptId(deptId);
		}
		Dept superDept = new Dept();
		if (StringUtil.isNotEmpty(dept.getSuperId()) && !dept.getSuperId().equals("0")) {
			superDept = deptService.getDeptByDeptId(dept.getSuperId());
		}
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		model.addAttribute("userList", userList);
		return "/dept/update";
	}

	/**
	 * ���²�����Ϣ
	 * 
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/updateDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> updateDept(Dept dept) {
		int result = deptService.updateDept(dept);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/deleteDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteDept(String deptId) {
		int result = deptService.deleteDept(deptId);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * ����첽�ύ�����������Ͳ��Զ�ת������ ����ͨ��ע���������ԣ��Զ����ת�������pojo��
	 * 
	 * @param binder
	 * @param request
	 */
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		CustomDateEditor customDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, customDateEditor);
	}*/

}
