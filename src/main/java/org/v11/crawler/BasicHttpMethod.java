package org.v11.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpMethod {
	private String RefererString = "www.baidu.com";
	private static Map<Class, Logger> loggers = new HashMap<Class, Logger>();
	
	protected synchronized Logger getLogger(){
		if(!BasicHttpMethod.loggers.containsKey(this.getClass())){
			Logger log = LoggerFactory.getLogger(this.getClass());
			BasicHttpMethod.loggers.put(this.getClass(), log);
		}
		return BasicHttpMethod.loggers.get(this.getClass());
		
	}
	/**
	 * 释放资源
	 * @param res
	 */
	protected void release(HttpResponse res){
		try {
			EntityUtils.consume(res.getEntity());
		} catch (IOException e) {
			getLogger().error(e.toString());
		}
	}
	/**
	 * 为HttpGet添加报文头信息
	 * @param url
	 * @return 
	 */
	protected HttpGet addHttpGetWithHeader(String url){
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Host", "yszsjs2.cas.cn");
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:32.0) Gecko/20100101 Firefox/32.0");
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpGet.setHeader("Accept-Encoding", "");
		httpGet.setHeader("Cookie", "Hm_lvt_4066ff3539577ca0df4e65f3937c5caf=1413898318,1413901520; JSESSIONID=787AF60E088FD9392331AB64DF053C04.164tc1; 3455=app2; Hm_lpvt_4066ff3539577ca0df4e65f3937c5caf=1413902946");
		RefererString = url ;
		return httpGet;
	}
	/**
	 * 添加post请求报文头信息
	 * @param url
	 * @return
	 */
	protected HttpPost addHttpPostWithHeader(String url){
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
		httpPost.setHeader("Referer", RefererString);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		RefererString = url ;
		return httpPost;
	}
	protected void printResponse(HttpResponse res){
		getHeaders(res);
		getResponseBody(res);
	}
	/**
	 * 打印出response的页面信息
	 * @param res
	 */
	protected String getResponseBody(HttpResponse res){
		HttpEntity entity = res.getEntity();
		if(entity != null){
			String content;
			try {
				content = EntityUtils.toString(entity,"UTF-8");
				getLogger().info(content);
				return content;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 打印response出报文头信息
	 * @param response
	 */
	protected String getHeaders(HttpResponse response){
		getLogger().debug("-----------Header---------------");
		Header[] headers = response.getAllHeaders();
		String res = "";
    	for(Header header:headers){
    		res += "key; "+header.getName()
    				+" value:"+header.getValue();
    	}
    	getLogger().info(res);
    	return res;
	}
}
