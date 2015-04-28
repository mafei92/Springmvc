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
 * ���ݴ������ʵ��
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
		// ����ڵ�ǰ���޸ĸ����ڵ�Ϊ��Ҷ�ӽڵ�
		Dept father = deptDao.getDeptByDeptId(dept.getSuperId());
		if (father.getIfLeaf().equals("1")) {
			father.setIfLeaf("0");
			deptDao.updateDept(father);
		}
		// �������Ϊ���ž���ͬʱ�޸��û������ڲ���
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
		// �������Ϊ���ž���ͬʱ�޸��û������ڲ���
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
		// ���ýڵ��Ƿ����ӽڵ�
		List<Dept> list = this.queryDeptBySuperId(deptId);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			Dept dept = deptDao.getDeptByDeptId(deptId);
			Dept father = deptDao.getDeptByDeptId(dept.getSuperId());
			result = deptDao.deleteDept(deptId);
			// ɾ���󣬼�⸸���ڵ��Ƿ����ӽڵ�
			List<Dept> child = this.queryDeptBySuperId(father.getDeptId());
			if (child == null || child.size() == 0) {
				// ���û���ӽڵ㣬������ΪҶ�ӽڵ�
				father.setIfLeaf("1");
				deptDao.updateDept(father);
			}
			// ͬʱɾ���û������ڲ���
			userInfoDao.deleteDeptId(deptId);
		}
		return result;
	}

	/**
	 * ����ʱ����deptId
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
	 * ����ʱ����deptNo
	 * 
	 * @param superId
	 * @return deptNo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getMaxDeptNo(String superId) {
		Dept superDept = this.getDeptByDeptId(superId);
		StringBuffer deptNo = new StringBuffer(superDept.getDeptNo());
		// ����ϼ��ڵ��Ƿ����ӽڵ�
		List<Dept> list = this.queryDeptBySuperId(superId);
		if (list != null && list.size() > 0) {
			String MaxDeptNo = deptDao.getMaxDeptNo(superId);
			String num = MaxDeptNo.substring(superDept.getDeptNo().length());
			int newNum = Integer.parseInt(num) + 1;
			deptNo.append(newNum);
		} else {
			deptNo.append("01");
			// �ӽڵ����99��
		}
		return deptNo.toString();
	}

	@Override
	public List<Dept> getDeptByDeptManager(String userId) {
		return deptDao.getDeptByDeptManager(userId);
	}

}
