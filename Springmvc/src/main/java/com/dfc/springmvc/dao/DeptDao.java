package com.dfc.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.Dept;

/**
 * dept持久层相关接口
 * 
 * @author dongdong
 * 
 */

@Repository
public interface DeptDao {

	public List<Dept> queryDeptBySuperId(String superId);

	public Dept getDeptByDeptId(String deptId);

	public int insertDept(Dept dept);

	public int updateDept(Dept dept);

	public int deleteDept(String deptId);

	public String getMaxPrimaryKey();

	public String getMaxDeptNo(String superId);

	public List<Dept> getDeptByDeptManager(String userId);

}