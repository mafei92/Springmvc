package com.dfc.springmvc.webservice.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dfc.springmvc.dao.UserInfoDao;
import com.dfc.springmvc.pojo.UserInfo;
import com.dfc.springmvc.webservice.CXFWebService;

@Service
public class CXFWebServiceImpl implements CXFWebService {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public String getArticleInfo(String userId) {
		log.info("=====" + userId + "=====");
		UserInfo userInfo = userInfoDao.getUser(userId);
		String result = "";
		if (null != userInfo) {
			try {
				result = URLEncoder.encode(JSONObject.fromObject(userInfo).toString(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
		}
		return result;
	}
}
