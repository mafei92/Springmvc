package com.dfc.springmvc.common;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * API2.1调用:f.php?phone=xxxxxx&pwd=xxx&to=xxxx&msg=xxxx&type=0 以上接口参数详细说明 VIP
 * API 1.phone:手机号 2.pwd:飞信密码 3.to:发送给谁(手机号或飞信号) 4.msg:飞信内容 5.type:操作 0(空)发送短信
 * 1检查好友 2添加好友 6.u:备用参数:当发送内容为乱码时 在最后加上&u=1
 */
public class Fetion {

	private static Logger log = Logger.getLogger(Fetion.class);
	private static final String PHONE = "15010183134";
	private static final String PWD = "";

	public static void sendMsg(String _to, String _msg) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://3.ibtf.sinaapp.com/f.php");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码

		NameValuePair[] data = { new NameValuePair("phone", PHONE), new NameValuePair("pwd", PWD),
				new NameValuePair("to", _to), new NameValuePair("msg", _msg), new NameValuePair("type", "0") };
		post.setRequestBody(data);
		client.executeMethod(post);
		int statusCode = post.getStatusCode();
		log.info("statusCode:" + statusCode);
		// Header[] headers = post.getResponseHeaders();
		// for (Header h : headers) {
		// log.info(h.toString());
		// }
		// String result = new
		// String(post.getResponseBodyAsString().getBytes("utf-8"));
		// log.info("result:" + result);
		post.releaseConnection();
	}
}