package com.dfc.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.dao.DeptDao;
import com.dfc.springmvc.dao.UserInfoDao;
import com.dfc.springmvc.pojo.Dept;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.service.DeptService;
import com.dfc.springmvc.util.StringUtil;

/**
 * 数据处理具体实现
 * 
 * @author dongdong
 * 
 */

@Service("deptService")
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDao deptDao;
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Dept> queryDeptBySuperId(String superId) {
		return deptDao.queryDeptBySuperId(superId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Dept getDeptByDeptId(String deptId) {
		return deptDao.getDeptByDeptId(deptId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int insertDept(Dept dept) {
		// 插入节点前，修改父级节点为非叶子节点
		Dept father = deptDao.getDeptByDeptId(dept.getSuperId());
		if (father.getIfLeaf().equals("1")) {
			father.setIfLeaf("0");
			deptDao.updateDept(father);
		}
		// 如果设置为部门经理，同时修改用户的所在部门
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			UserInfo userInfo = userInfoDao.getUser(dept.getDeptManager());
			userInfo.setDeptId(dept.getDeptId());
			userInfoDao.updateUser(userInfo);
		}
		return deptDao.insertDept(dept);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateDept(Dept dept) {
		// 如果设置为部门经理，同时修改用户的所在部门
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			UserInfo userInfo = userInfoDao.getUser(dept.getDeptManager());
			userInfo.setDeptId(dept.getDeptId());
			userInfoDao.updateUser(userInfo);
		}
		return deptDao.updateDept(dept);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteDept(String deptId) {
		int result = 0;
		// 检测该节点是否有子节点
		List<Dept> list = this.queryDeptBySuperId(deptId);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			Dept dept = deptDao.getDeptByDeptId(deptId);
			Dept father = deptDao.getDeptByDeptId(dept.getSuperId());
			result = deptDao.deleteDept(deptId);
			// 删除后，检测父级节点是否还有子节点
			List<Dept> child = this.queryDeptBySuperId(father.getDeptId());
			if (child == null || child.size() == 0) {
				// 如果没有子节点，则设置为叶子节点
				father.setIfLeaf("1");
				deptDao.updateDept(father);
			}
			// 同时删除用户的所在部门
			userInfoDao.deleteDeptId(deptId);
		}
		return result;
	}

	/**
	 * 新增时生成deptId
	 * 
	 * @return deptId
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getMaxPrimaryKey() {
		String MaxId = deptDao.getMaxPrimaryKey();
		int num = Integer.parseInt(MaxId.replaceFirst("d", ""));
		int newNum = num + 1;
		StringBuffer deptId = new StringBuffer("d");
		deptId.append(newNum);
		return deptId.toString();
	}

	/**
	 * 新增时生成deptNo
	 * 
	 * @param superId
	 * @return deptNo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getMaxDeptNo(String superId) {
		Dept superDept = this.getDeptByDeptId(superId);
		StringBuffer deptNo = new StringBuffer(superDept.getDeptNo());
		// 检测上级节点是否有子节点
		List<Dept> list = this.queryDeptBySuperId(superId);
		if (list != null && list.size() > 0) {
			String MaxDeptNo = deptDao.getMaxDeptNo(superId);
			String num = MaxDeptNo.substring(superDept.getDeptNo().length());
			int newNum = Integer.parseInt(num) + 1;
			deptNo.append(newNum);
		} else {
			deptNo.append("01");
			// 子节点最多99个
		}
		return deptNo.toString();
	}

	@Override
	public List<Dept> getDeptByDeptManager(String userId) {
		return deptDao.getDeptByDeptManager(userId);
	}

}
