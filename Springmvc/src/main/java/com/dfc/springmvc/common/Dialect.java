/**
 * 
 */
package com.dfc.springmvc.common;

/**
 * MyBatis实现物理分页
 * 
 * @author dongdong
 * 
 */
public abstract class Dialect {
	public static enum Type {
		MYSQL, ORACLE
	}

	public abstract String getLimitString(String sql, int skipResults,
			int maxResults);
}
