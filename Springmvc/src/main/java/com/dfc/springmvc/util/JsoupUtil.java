package com.dfc.springmvc.util;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
//import org.w3cm.Node;
import org.w3c.dom.NodeList;

public class JsoupUtil {
	
	public static NodeList getNodeList(String url, String exp) throws ParserConfigurationException, XPathExpressionException {

		// String url = "http://blog.jobbole.com/category/career/";
		// String exp = "//div[@id='archive']/div[@class='post floated-thumb']/div[@class='post-thumb']/a/img/@src";

		String html = null;
		try {
			Connection connect = Jsoup.connect(url);
			// connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			// connect.header("Accept-Encoding", "gzip,deflate,sdch");
			// connect.header("Accept-Language", "zh-CN,zh;q=0.8");
			// connect.header("Cache-Contro", "max-age=0");
			// connect.header("Connection", "keep-alive");
			// connect.header("Host", "www.cb.com.cn");
			// connect.header("Referer", "http://www.cb.com.cn/economy/2014_1024/1090616.html");
			// connect.header("User-Agent", "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
			html = connect.get().body().html();
			// System.out.println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HtmlCleaner hc = new HtmlCleaner();
		TagNode tn = hc.clean(html);
		Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
		XPath xPath = XPathFactory.newInstance().newXPath();
		Object result;
		result = xPath.evaluate(exp, dom, XPathConstants.NODESET);
		if (result instanceof NodeList) {
			NodeList nodeList = (NodeList) result;
//			System.out.println(nodeList.getLength());
//			for (int i = 0; i < nodeList.getLength(); i++) {
//				Node node = nodeList.item(i);
//				System.out.println(node.getNodeValue() == null ? node
//						.getTextContent() : node.getNodeValue());
//			}
			return nodeList;
		} else {
			return null;
		}
	}
}
