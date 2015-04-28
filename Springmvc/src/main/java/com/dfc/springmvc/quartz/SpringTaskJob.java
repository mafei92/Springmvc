package com.dfc.springmvc.quartz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.dfc.springmvc.common.Fetion;
import com.dfc.springmvc.util.JsoupUtil;

@Component
public class SpringTaskJob implements Serializable {

	private static final long serialVersionUID = 1288956798787274307L;
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 每天6:50发送天气预报
	 */
	@Scheduled(cron = "0 50 06 * * ?")
	public void sendWeatherForecast() {
		this.sendWeatherByXml();
	}

	public void sendWeatherByXml() {
		try {
			StringBuffer sb = new StringBuffer("亲爱的媳妇，您好：今天是公元");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			sb.append(sdf.format(System.currentTimeMillis()));
			sb.append("，下面为您汇报今日天气情况，");
			Document doc = Jsoup.connect("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0").get();
			String city = doc.getElementsByTag("city").html();
			sb.append(city);
			sb.append("，");
			String status1 = doc.getElementsByTag("status1").html();
			sb.append(status1);
			sb.append("，");
			String direction1 = doc.getElementsByTag("direction1").html();
			sb.append(direction1);
			sb.append("：");
			String power1 = doc.getElementsByTag("power1").html();
			sb.append(power1);
			sb.append("级，气温：");
			String low = doc.getElementsByTag("temperature2").html();
			sb.append(low);
			sb.append("~");
			String high = doc.getElementsByTag("temperature1").html();
			sb.append(high);
			sb.append("，空气质量：");
			String pollution = doc.getElementsByTag("pollution_l").html();
			sb.append(pollution);
			sb.append("污染，");
			String zwx = doc.getElementsByTag("zwx_s").html();
			sb.append(zwx);
			String chy_shuoming = doc.getElementsByTag("chy_shuoming").html();
			sb.append("，穿衣说明：");
			sb.append(chy_shuoming);
			sb.append("；");
			String gm = doc.getElementsByTag("gm").html();
			// String gm_l = doc.getElementsByTag("gm_l").html();
			String gm_s = doc.getElementsByTag("gm_s").html();
			sb.append("感冒指数：");
			sb.append(gm);
			sb.append("，");
			sb.append(gm_s);
			String yd = doc.getElementsByTag("yd").html();
			// String yd_l = doc.getElementsByTag("yd_l").html();
			String yd_s = doc.getElementsByTag("yd_s").html();
			sb.append("运动指数：");
			sb.append(yd);
			sb.append("，");
			sb.append(yd_s);
			Fetion.sendMsg("15210777936", sb.toString());
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

	public void sendWeatherByWeb() {
		try {
			log.info("===执行定时任务===");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
			StringBuffer sb = new StringBuffer("您好，今天是");
			sb.append(sdf.format(new Date()));
			sb.append("，北京今日天气：");
			String url = "http://weather.com.cn/html/weather/101010100.shtml";
			String weatherExp = "//div[@id='7d']/ul[@class='t clearfix']/li[@class='dn on']/p[@class='wea']/text()";
			Node weather = JsoupUtil.getNodeList(url, weatherExp).item(0);
			sb.append(weather.getNodeValue());
			sb.append("，最高气温");
			String highTemperatureExp = "//div[@id='7d']/ul[@class='t clearfix']/li[@class='dn on']/p[@class='tem tem1']/span/text()";
			Node highTemperature = JsoupUtil.getNodeList(url, highTemperatureExp).item(0);
			sb.append(highTemperature.getNodeValue());
			sb.append("度，最低气温");
			String lowTemperatureExp = "//div[@id='7d']/ul[@class='t clearfix']/li[@class='dn on']/p[@class='tem tem2']/span/text()";
			Node lowTemperature = JsoupUtil.getNodeList(url, lowTemperatureExp).item(0);
			sb.append(lowTemperature.getNodeValue());
			sb.append("度，");
			String windExp = "//div[@id='7d']/ul[@class='t clearfix']/li[@class='dn on']/p[@class='win']/i/text()";
			Node wind = JsoupUtil.getNodeList(url, windExp).item(0);
			sb.append("风力：");
			sb.append(wind.getNodeValue());
			Fetion.sendMsg("15210777936", sb.toString());
			Fetion.sendMsg("18341123330", sb.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
