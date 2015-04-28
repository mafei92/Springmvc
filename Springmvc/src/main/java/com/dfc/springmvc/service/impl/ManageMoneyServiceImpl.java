package com.dfc.springmvc.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.dao.ManageMoneyDao;
import com.dfc.springmvc.dao.UserInfoDao;
import com.dfc.springmvc.pojo.ManageMoney;
import com.dfc.springmvc.service.ManageMoneyService;

@Service("manageMoneyService")
public class ManageMoneyServiceImpl implements ManageMoneyService {

	@Autowired
	private ManageMoneyDao manageMoneyDao;

	@Autowired
	private UserInfoDao userdao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<ManageMoney> queryManageMoney(PageInfo<ManageMoney> pageInfo, Map<String, String> map) {
		if (null != pageInfo) {
			pageInfo.setTotalRecords(manageMoneyDao.getTotalRows(map));
		}
		// 设置从第几条开始获取记录和每页显示条数
		RowBounds rowBounds = new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize());
		return manageMoneyDao.queryManageMoney(rowBounds, map);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<ManageMoney> queryAllManageMoney(Map<String, Object> map) {
		return manageMoneyDao.queryAllManageMoney(map);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BigDecimal getSum(Map<String, Object> map) {
		return manageMoneyDao.getSum(map);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ManageMoney getManageMoneyById(String manageId) {
		return manageMoneyDao.getManageMoneyById(manageId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveManageMoney(ManageMoney manageMoney) {
		manageMoneyDao.saveManageMoney(manageMoney);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateManageMoney(ManageMoney manageMoney) {
		manageMoneyDao.updateManageMoney(manageMoney);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteManageMoney(String[] ids) {
		manageMoneyDao.deleteManageMoney(ids);
	}

}
