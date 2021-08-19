package at.smartshop.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class WebService extends Factory {

	public void apiReportPostRequest(String url, String json) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(Constants.DATA, json));
			request.setEntity(new UrlEncodedFormEntity(params));
			httpClient.execute(request);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void apiPutRequest(String url, String id, String json) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPut httpPut = new HttpPut(url + id);
			httpPut.setHeader("Content-type", "application/json");
			StringEntity stringEntity = new StringEntity(json);
			httpPut.setEntity(stringEntity);
			httpClient.execute(httpPut);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
