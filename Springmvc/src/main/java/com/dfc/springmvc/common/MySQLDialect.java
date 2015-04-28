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
public class MySQLDialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.chinatelecom.cpt.interceptor.Dialect#getLimitString(java.lang
	 * .String, int, int)
	 */
	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		// sql = sql.replaceAll("select", "SELECT");
		// sql = sql.replaceAll("from", "FROM");
		// sql = sql.replaceFirst("SELECT", "SELECT (@rowNum:=@rowNum+1) AS rowNo, ");
		// sql = sql.replaceFirst("FROM", "FROM (SELECT (@rowNum :=0)) rowNum, ");
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset >= 0) {
			pagingSelect.append(sql).append(" limit ").append(offset).append(", ").append(limit);
		} else {
			pagingSelect.append(sql).append(" limit ").append(0).append(", ").append(limit);
		}
		return pagingSelect.toString();
	}

}
