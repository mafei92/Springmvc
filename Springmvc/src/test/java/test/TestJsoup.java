package test;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.dfc.springmvc.common.Fetion;

public class TestJsoup {

	public static void main(String[] args) {
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
			Fetion.sendMsg("15210777936", sb.toString());
			Fetion.sendMsg("18341123330", sb.toString());
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
