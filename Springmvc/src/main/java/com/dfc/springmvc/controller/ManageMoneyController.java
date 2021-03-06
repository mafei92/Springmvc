package com.dfc.springmvc.controller;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.ManageMoney;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.service.ManageMoneyService;
import com.dfc.springmvc.service.UserService;
import com.dfc.springmvc.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manageMoney")
public class ManageMoneyController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ManageMoneyService manageMoneyService;

	@Autowired
	private UserService userService;

	/**
	 * 查询理财记录
	 * 
	 * @param model
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("/queryManageMoney")
	public String queryManageMoney(Model model, PageInfo<ManageMoney> pageInfo) {
		log.info("查询理财信息");
		Map<String, String> map = new HashMap<String, String>();
		map.put("userLevel", super.getUserInfo().getUserLevel());
		map.put("takeId", super.getUserInfo().getUserId());
		List<ManageMoney> manageList = manageMoneyService.queryManageMoney(pageInfo, map);
		model.addAttribute("manageList", manageList);
		return "/manage/query";
	}

	/**
	 * 理财报表
	 * 
	 * @param model
     * @param manageMoney
	 * @param beginTime
     * @param endTime
	 * @return
	 */
	@RequestMapping("/manageMoneyReport")
	public String queryAllManageMoney(Model model, ManageMoney manageMoney, String beginTime, String endTime) {
		log.info("查询理财信息");
		UserInfo user = super.getUserInfo();
		List<UserInfo> userList = userService.getAllUsers();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(manageMoney.getTakeId())) {
			map.put("takeId", manageMoney.getTakeId());
		} else {
			if (!user.getUserLevel().equals("0")) {
				map.put("takeId", user.getUserId());
			}
		}
		if (StringUtil.isNotEmpty(manageMoney.getInOrOut() + "")) {
			map.put("inOrOut", manageMoney.getInOrOut());
		}
		if (StringUtil.isNotEmpty(manageMoney.getIfTake() + "")) {
			map.put("ifTake", manageMoney.getIfTake());
		}
		if (StringUtil.isNotEmpty(beginTime)) {
			beginTime += " 00:00:00";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			beginTime = sdf.format(new Date(System.currentTimeMillis()));
			beginTime += "-01 00:00:00";
		}
		map.put("beginTime", beginTime);
		if (StringUtil.isNotEmpty(endTime)) {
			endTime += " 23:59:59";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endTime = sdf.format(new Date(System.currentTimeMillis()));
			endTime += " 23:59:59";
		}
		map.put("endTime", endTime);
		List<ManageMoney> manageList = manageMoneyService.queryAllManageMoney(map);
		map.put("inOrOut", 1);
		BigDecimal inSum = manageMoneyService.getSum(map);
		if (null == inSum) {
			inSum = BigDecimal.valueOf(0);
		}
		map.put("inOrOut", 2);
		BigDecimal outSum = manageMoneyService.getSum(map);
		if (null == outSum) {
			outSum = BigDecimal.valueOf(0);
		}
		model.addAttribute("manageMoney", manageMoney);
		model.addAttribute("beginTime", beginTime.substring(0, 10));
		model.addAttribute("endTime", endTime.substring(0, 10));
		model.addAttribute("manageList", manageList);
		model.addAttribute("inSum", inSum);
		model.addAttribute("outSum", outSum);
		model.addAttribute("sum", inSum.subtract(outSum));// 收入-支出
		model.addAttribute("user", user);
		model.addAttribute("userList", userList);
		return "/manage/report";
	}

	/**
	 * 跳转添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/addManageMoney")
	public String addManageMoney(Model model) {
		UserInfo user = super.getUserInfo();
		ManageMoney manageMoney = new ManageMoney();
		manageMoney.setInOrOut(2);
		manageMoney.setTakeId(user.getUserId());
		manageMoney.setTakeTime(new Date(System.currentTimeMillis()));
		manageMoney.setIfTake(0);
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("manageMoney", manageMoney);
		model.addAttribute("user", user);
		model.addAttribute("userList", userList);
		return "/manage/add";
	}

	/**
	 * 保存理财记录
	 * 
	 * @param manageMoney
	 * @return
	 */
	@RequestMapping("/saveManageMoney")
	public String saveManageMoney(ManageMoney manageMoney) {
		log.info("保存理财信息");
		manageMoney.setCreateTime(new Date(System.currentTimeMillis()));
		manageMoney.setUpdateTime(new Date(System.currentTimeMillis()));
		manageMoney.setCreateId(super.getUserInfo().getUserId());
		manageMoneyService.saveManageMoney(manageMoney);
		if (StringUtil.isNotEmpty(manageMoney.getTakeId())) {
			UserInfo takeUserInfo = userService.getUser(manageMoney.getTakeId());
			if (StringUtil.isNotEmpty(takeUserInfo.getPhone())) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String msg = sdf.format(new Date()) + "理财管理中添加了关于您的记录，请登录系统查看详细信息";
//				try {
//					Fetion.sendMsg(takeUserInfo.getPhone(), msg);
//				} catch (HttpException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		return "redirect:queryManageMoney.do";
	}

	/**
	 * 跳转修改页面
	 * 
	 * @param model
	 * @param manageId
	 * @return
	 */
	@RequestMapping("/editManageMoney")
	public String editManageMoney(Model model, String manageId) {
		ManageMoney manageMoney = manageMoneyService.getManageMoneyById(manageId);
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("manageMoney", manageMoney);
		model.addAttribute("userList", userList);
		model.addAttribute("user", super.getUserInfo());
		model.addAttribute("userName", userService.getUser(manageMoney.getTakeId()).getTrueName());
		return "/manage/update";
	}

	/**
	 * 更新理财记录
	 * 
	 * @param manageMoney
	 * @return
	 */
	@RequestMapping("/updateManageMoney")
	public String updateManageMoney(ManageMoney manageMoney) {
		log.info("更新理财信息");
		manageMoney.setUpdateTime(new Date(System.currentTimeMillis()));
		manageMoneyService.updateManageMoney(manageMoney);
		if (StringUtil.isNotEmpty(manageMoney.getTakeId())) {
			UserInfo takeUserInfo = userService.getUser(manageMoney.getTakeId());
			if (StringUtil.isNotEmpty(takeUserInfo.getPhone())) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String msg = sdf.format(new Date()) + "理财管理中更新了关于您的记录，请登录系统查看详细信息";
//				try {
//					Fetion.sendMsg(takeUserInfo.getPhone(), msg);
//				} catch (HttpException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		return "redirect:queryManageMoney.do";
	}

	/**
	 * 删除理财记录
	 * 
	 * @param manageId
	 * @return
	 */
	@RequestMapping("/deleteManageMoney")
	public String deleteManageMoney(String manageId) {
		log.info("删除理财信息");
		if (StringUtil.isNotEmpty(manageId)) {
			String[] ids = manageId.replaceFirst(",", "").split(",");
			manageMoneyService.deleteManageMoney(ids);
		}
		return "redirect:queryManageMoney.do";
	}

}
