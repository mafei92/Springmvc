package com.dfc.springmvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * String工具类
 * 
 * @author dongdong
 * 
 */

public class StringUtil {

	private static Logger log = Logger.getLogger(StringUtil.class);

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (null != str && !"".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * String转Date
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (isNotEmpty(str)) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				log.info(e.getCause());
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * String转DateTime
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDateTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (isNotEmpty(str)) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				log.info(e.getCause());
				e.printStackTrace();
			}
		}
		return date;
	}

}
