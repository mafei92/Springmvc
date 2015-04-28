/**
 * 
 */
package com.dfc.springmvc.common;

/**
 * MyBatisʵ�������ҳ
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
