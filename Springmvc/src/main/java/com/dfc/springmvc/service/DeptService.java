package com.dfc.springmvc.service;

import java.util.List;

import com.dfc.springmvc.pojo.Dept;

/**
 * 数据处理接口
 * 
 * @author dongdong
 * 
 */

public interface DeptService {

	public List<Dept> queryDeptBySuperId(String superId);

	public Dept getDeptByDeptId(String deptId);

	public int insertDept(Dept dept);

	public int updateDept(Dept dept);

	public int deleteDept(String deptId);

	public String getMaxPrimaryKey();

	public String getMaxDeptNo(String superId);

	public List<Dept> getDeptByDeptManager(String userId);

}
