package com.dfc.springmvc.common;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * API2.1����:f.php?phone=xxxxxx&pwd=xxx&to=xxxx&msg=xxxx&type=0 ���Ͻӿڲ�����ϸ˵�� VIP
 * API 1.phone:�ֻ��� 2.pwd:�������� 3.to:���͸�˭(�ֻ��Ż���ź�) 4.msg:�������� 5.type:���� 0(��)���Ͷ���
 * 1������ 2��Ӻ��� 6.u:���ò���:����������Ϊ����ʱ ��������&u=1
 */
public class Fetion {

	private static Logger log = Logger.getLogger(Fetion.class);
	private static final String PHONE = "15010183134";
	private static final String PWD = "";

	public static void sendMsg(String _to, String _msg) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://3.ibtf.sinaapp.com/f.php");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// ��ͷ�ļ�������ת��

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