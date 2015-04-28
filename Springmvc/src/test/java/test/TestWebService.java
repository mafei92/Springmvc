package test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestWebService {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/Springmvc/services/webSer/userInfo/getUserInfo";
		String param = "?userId=23ddbf9a116911e4b54e1205e26038f6";
		HttpGet httpget = new HttpGet(url + param);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpget, responseHandler);
		responseBody = java.net.URLDecoder.decode(responseBody, "utf-8");
		httpclient.getConnectionManager().shutdown();
		System.out.println(responseBody);
	}
}
