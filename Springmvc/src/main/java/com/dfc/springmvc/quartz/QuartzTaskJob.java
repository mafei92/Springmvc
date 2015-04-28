package com.dfc.springmvc.quartz;

import java.io.Serializable;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.dfc.springmvc.common.Fetion;

@Component
public class QuartzTaskJob extends QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 566252153887644041L;
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * ÿСʱִ��һ��
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("===Quartz��ʱ����===");
		//this.sendWeatherByXml();
	}

	public void sendWeatherByXml() {
		try {
			StringBuffer sb = new StringBuffer("�װ���ϱ�������ã������ǹ�Ԫ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
			sb.append(sdf.format(System.currentTimeMillis()));
			sb.append("������Ϊ���㱨�������������");
			Document doc = Jsoup.connect("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0").get();
			String city = doc.getElementsByTag("city").html();
			sb.append(city);
			sb.append("��");
			String status1 = doc.getElementsByTag("status1").html();
			sb.append(status1);
			sb.append("��");
			String direction1 = doc.getElementsByTag("direction1").html();
			sb.append(direction1);
			sb.append("��");
			String power1 = doc.getElementsByTag("power1").html();
			sb.append(power1);
			sb.append("�������£�");
			String low = doc.getElementsByTag("temperature2").html();
			sb.append(low);
			sb.append("~");
			String high = doc.getElementsByTag("temperature1").html();
			sb.append(high);
			sb.append("������������");
			String pollution = doc.getElementsByTag("pollution_l").html();
			sb.append(pollution);
			sb.append("��Ⱦ��");
			String zwx = doc.getElementsByTag("zwx_s").html();
			sb.append(zwx);
			String chy_shuoming = doc.getElementsByTag("chy_shuoming").html();
			sb.append("������˵����");
			sb.append(chy_shuoming);
			sb.append("��");
			String gm = doc.getElementsByTag("gm").html();
			// String gm_l = doc.getElementsByTag("gm_l").html();
			String gm_s = doc.getElementsByTag("gm_s").html();
			sb.append("��ðָ����");
			sb.append(gm);
			sb.append("��");
			sb.append(gm_s);
			String yd = doc.getElementsByTag("yd").html();
			// String yd_l = doc.getElementsByTag("yd_l").html();
			String yd_s = doc.getElementsByTag("yd_s").html();
			sb.append("�˶�ָ����");
			sb.append(yd);
			sb.append("��");
			sb.append(yd_s);
			Fetion.sendMsg("18341123330", sb.toString());
		} catch (Exception e) {
			log.info(e.getMessage());
			try {
				Fetion.sendMsg("18341123330", e.getMessage().toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void sendWeatherForecast() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httget = new HttpGet("http://www.weather.com.cn/data/cityinfo/101010100.html");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httget, responseHandler);
			responseBody = URLDecoder.decode(responseBody, "utf-8");
			httpclient.getConnectionManager().shutdown();
			JSONObject jsonObject = JSONObject.fromObject(responseBody);
			JSONObject weatherInfo = JSONObject.fromObject(jsonObject.get("weatherinfo"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			StringBuffer sb = new StringBuffer("���ã�������");
			sb.append(sdf.format(new Date()));
			sb.append("��");
			sb.append(weatherInfo.get("city"));
			sb.append("����������");
			sb.append(weatherInfo.get("weather"));
			sb.append("���������");
			sb.append(weatherInfo.get("temp1"));
			sb.append("�ȣ��������");
			sb.append(weatherInfo.get("temp2"));
			sb.append("�ȣ�����ʱ�䣺����");
			sb.append(weatherInfo.get("ptime"));
			Fetion.sendMsg("18341123330", sb.toString());
		} catch (Exception e) {
			log.info(e.getCause());
			try {
				Fetion.sendMsg("18341123330", e.getCause().toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
