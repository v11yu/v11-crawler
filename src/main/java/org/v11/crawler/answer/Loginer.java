package org.v11.crawler.answer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.v11.crawler.BasicHttpMethod;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Loginer extends BasicHttpMethod {
	private final String LOGIN_URL = "http://yszsjs2.cas.cn/login.action";
	private HttpClient client;
	private final String username = "wuwangyu@software.ict.ac.cn";
	private final String pwd = "fjndwy";
	private final String successUrl = "http://yszsjs2.cas.cn/question.action?qno=2";
	private final String cookieString = "Hm_lvt_4066ff3539577ca0df4e65f3937c5caf=1413898318,1413901520; JSESSIONID=787AF60E088FD9392331AB64DF053C04.164tc1; 3455=app2; Hm_lpvt_4066ff3539577ca0df4e65f3937c5caf=1413907260";

	public boolean login() {
		try {
			WebClient client = new WebClient();
			client.addRequestHeader("Host", "yszsjs2.cas.cn");
			client.addRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; rv:32.0) Gecko/20100101 Firefox/32.0");
			client.addRequestHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			client.addRequestHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			client.addRequestHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			client.addRequestHeader("Accept-Encoding", "");
			addCookie(client, cookieString);

			HtmlPage res = client.getPage(successUrl);
			getLogger().info(res.asText());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void addCookie(WebClient client, String str) {
		CookieStore cookieStore = new BasicCookieStore();
		String ls[] = str.split(";");
		for (String tmp : ls) {
			String v[] = tmp.split("=");
			Cookie cookie = new Cookie("http://yszsjs2.cas.cn/",v[0], v[1]);
			client.getCookieManager().addCookie(cookie);
		}
		return;
	}

}
