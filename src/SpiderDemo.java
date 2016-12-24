import com.sina.util.getPageUtil;

import java.io.IOException;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

public class SpiderDemo {
	String preloginUrl = "http://login.sina.com.cn/sso/prelogin.php?entry=account&callback=sinaSSOController.preloginCallBack&su=MjczNTUzODM0JTQwcXEuY29t&rsakt=mod&client=ssologin.js(v1.4.15)&_=1451131784116";
	String servertime;
	String nonce;

	public static void main(String args[]) throws Exception {

		// 创建默认的客户端实例
		HttpClient httpCLient = new DefaultHttpClient();
		// 创建get请求实例
		HttpGet httpget = new HttpGet("http://d.weibo.com");
		httpget.setHeader("cookie", "YF-Page-G0=04608cddd2bbca9a376ef2efa085a43b; login_sid_t=93cb871ae91c923e116ed5a8ba67257f; _s_tentry=passport.weibo.com; Apache=2996011807209.8584.1451353404514; SINAGLOBAL=2996011807209.8584.1451353404514; ULV=1451353404517:1:1:1:2996011807209.8584.1451353404514:; SUS=SID-2507591934-1451353425-GZ-9crh2-4bb5e41a4b69a932a84c3acbfb5a1d50; SUE=es%3D0a6b073d41c15bd429b8360446e07b02%26ev%3Dv1%26es2%3Da44503d8c6a429caac80f7f8649cac11%26rs0%3DxX8nOYjR8doCbb79lgVjHqBWqvc%252BtnEK1nFyu06fsFVI6C8SaRzJWT10psd951qnLpTGr3yojLtVCA8f%252BcGu%252F3xptFOqNRjtuvPc5e0A7Wke9qEV%252B59nhEJoRrZVbFyz9lKmqr5z%252BgtR0IuxVz7YKYNplE3VSBXk74GNyCCRMZ0%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1451353425%26et%3D1451439825%26d%3Dc909%26i%3D1d50%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D2507591934%26name%3D273553834%2540qq.com%26nick%3D%25E6%259C%25B1%25E6%2596%258C%25E4%25BF%258Ahp%26fmp%3D%26lcp%3D2014-12-01%252012%253A32%253A41; SUB=_2A257hZUBDeRxGeRL61UU-S_FyDiIHXVY8oHJrDV8PUNbuNBeLU_skW-TmzJ9FaxOTXHPbQoD7qxlDZcyHg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5W1N-VC6.fnLMaHD.BCewh5JpX5K2t; SUHB=0y0oIsu6o1GBSx; ALF=1482889425; SSOLoginState=1451353425; un=273553834@qq.com; wvr=6");
		System.out.println("executing request " + httpget.getURI());
		try {
			// 客户端执行get请求 返回响应实体
			HttpResponse response = httpCLient.execute(httpget);
			// 服务器响应状态行
			System.out.println(response.getStatusLine());
			Header[] heads = response.getAllHeaders();
			// 打印所有响应头
			for (Header h : heads) {
				System.out.println(h.getName() + ":" + h.getValue());
			}

			// 获取响应消息实体
			HttpEntity entity = response.getEntity();

			System.out.println("------------------------------------");

			if (entity != null) {
				// 响应内容
				System.out.println(EntityUtils.toString(entity,"gbk"));
				System.out.println("----------------------------------------");
				// 响应内容长度
				System.out.println("响应内容长度：" + entity.getContentLength());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpCLient.getConnectionManager().shutdown();
		}
	}

	// 预登陆preloginUrl，获得servertime，nonce两个变量
	public void prelogin() {

	}

}