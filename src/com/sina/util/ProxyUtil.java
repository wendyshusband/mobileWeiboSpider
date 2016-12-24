package com.sina.util;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.httpclient.HostConfiguration;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.httpclient.cookie.CookiePolicy;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ProxyUtil {
//	public static Map<String, Map<String, Boolean>> proxyMap = new HashMap<String, Map<String, Boolean>>();
//	public static String IS_DHB_USING = "using";
//	public static String IS_DHB_USEFUL = "useful";
	public static int DHB_ACCESS_TIMEOUT = 10000;
	private FileUtil fu=new FileUtil();
	private String path="./proxy.txt";
	private boolean isFirst=true;
	
	public ProxyUtil() {
		
	}

	//从proxy360获取代理
	public void getProxyFromProxy360()
	{
		try {
			 StringBuffer sb = new StringBuffer();
			 //创建HttpClient实例
			 HttpClient client = new DefaultHttpClient();
			 //创建httpGet
			 HttpGet httpGet = new HttpGet("http://www.proxy360.cn/Region/China");
				httpGet
				.setHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			 //执行

			  HttpResponse response = client.execute(httpGet);
			  
			  HttpEntity entry = response.getEntity();
			  
			  if(entry != null)
			  {
			   InputStreamReader is = new InputStreamReader(entry.getContent());
			   BufferedReader br = new BufferedReader(is);
			   String str = null;
			   while((str = br.readLine()) != null)
			   {
			    sb.append(str.trim());
			   }
			   br.close();
			  }
			  

			//String content = HttpClientUtil.HTTPRequest(url);
			Document doc = Jsoup.parse(sb.toString());
			Elements proxys = doc.select("div.proxylistitem");
			Map<String, String> proxyMap = new HashMap<String, String>();
			for (Element element : proxys) {
				Elements spans = element.select("span");
				String ip=spans.get(0).text();
				String  port=spans.get(1).text();
				boolean isUseful = isProxyUseful2WB(ip, port);
				if (isUseful) {
					String content=ip+" "+port+"\n";
					if(isFirst){
						fu.writeFile(content, path);
						isFirst=false;
					}
					else{
						fu.writeFileAppend(content, path);
					}
				}
			}
			
			for (String ip : proxyMap.keySet()) {
				System.out.println(ip+" " +proxyMap.get(ip));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void getProxyFromPaChong(){
		try {
			
			 StringBuffer sb = new StringBuffer();
			 //创建HttpClient实例
			 HttpClient client = new DefaultHttpClient();
			 //创建httpGet
			 HttpGet httpGet = new HttpGet("http://pachong.org/area/city/name/广东省.html");
				httpGet
				.setHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			 //执行
			 try {
			  HttpResponse response = client.execute(httpGet);
			  
			  HttpEntity entry = response.getEntity();
			  
			  if(entry != null)
			  {
			   InputStreamReader is = new InputStreamReader(entry.getContent());
			   BufferedReader br = new BufferedReader(is);
			   String str = null;
			   while((str = br.readLine()) != null)
			   {
			    sb.append(str.trim());
			   }
			   br.close();
			  }
			  
			 } catch (ClientProtocolException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			 } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			 }
			//String content = HttpClientUtil.HTTPRequest(url);
			Document doc = Jsoup.parse(sb.toString());
			
			//Map<String, String> proxyMap = new HashMap<String, String>();
			String script=doc.select("script").get(2).html().replace("var ", "");//解析script生成端口号
			String vars[] = script.split(";");
			Map<String, Integer> varsMap = new HashMap<String, Integer>();
			for (String var : vars) {
				String[] arr = var.split("=");
				String[] ab = arr[1].split("\\+");
				if (ab[1].contains("^")) {
					int a = Integer.parseInt(ab[0]);
					int b = Integer.parseInt(ab[1].split("\\^")[0]);
					String cString = ab[1].split("\\^")[1];
					int c = varsMap.get(cString);
					varsMap.put(arr[0],a+b^c);
				}else {
					int a = Integer.parseInt(ab[0]);
					int b = Integer.parseInt(ab[1]);
					varsMap.put(arr[0],a+b);
				}
			}

//			int cat=2264+5848;
//			int cock=8112+7211^cat;
//			int duck=3625+5721^cock;
//			int calf=8414+8262^duck;
//			int hen=7771+2079^calf;
			
			Elements proxys = doc.select("table.tb").select("tr");
			//System.out.println(proxys);
			String content="";
			
			for (int i = 1; i < proxys.size(); i++) {
				Element element = proxys.get(i);
				Elements tds = element.select("td");
				String tmp = tds.get(2).select("script").html().replace("document.write((", "").replace(");", "").replace(")", "");
				String abc[] = tmp.split("\\+"); 
				String ab[] = abc[0].split("\\^");
				int a = Integer.parseInt(ab[0]);
				int b = varsMap.get(ab[1]);
				int c = Integer.parseInt(abc[1]);
				String port = ((a^b)+c)+"";
				String ip = tds.get(1).text();
				content+=ip+" "+port+"\n";
				//System.out.println(ip+"   "+port+" "+a+"^"+b+"+"+c);
//				boolean isUseful = isProxyUseful2WB(ip, port);
//				if (isUseful) {
//					content=ip+" "+port+"\n";
//					fu.writeFileAppend(content, path);
//				}
			}
			fu.writeFileAppend(content, path);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	
//	public void getProxyFromxici(){
//		try {
//			HttpClient client = new HttpClient();
//			HttpMethod method = new GetMethod("http://www.proxy.com.ru/gaoni/");
//			client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
//			method.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT)");
//			client.executeMethod(method);
//			InputStreamReader isr = new InputStreamReader(method.getResponseBodyAsStream());
//			BufferedReader br = new BufferedReader(isr);
//			String sCurrentLine = "";
//			StringBuffer sTotalString = new StringBuffer();
//			while ((sCurrentLine = br.readLine()) != null) {
//				sTotalString.append(sCurrentLine + "\n");
//			}
//			//String content = HttpClientUtil.HTTPRequest(url);
//			Document doc = Jsoup.parse(sTotalString.toString());
//			Elements trs = doc.select("table").get(7).select("tr");
//			for (Element tr : trs) {
//				try {
//					Elements tds = tr.select("td");
//					String ip = tds.get(1).text();
//					String port = tds.get(2).text();
//					boolean isUseful = isProxyUseful2WB(ip, port);
//					if (isUseful){
//						String content=ip+" "+port+"\n";
//						if(isFirst){
//							fu.writeFile(content, path);
//							isFirst=false;
//						}
//						else{
//							fu.writeFileAppend(content, path);
//						}
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//	
//		} catch (Exception ex) {
//			System.out.println(ex.toString());
//		}
//	}


	public boolean isProxyUseful2WB(String ip,String port) {
		
    	try {
    	    HttpClient client = new DefaultHttpClient(); 
    		String proxyHost = ip;
			int proxyPort = Integer.valueOf(port);
			HttpHost proxy = new HttpHost(proxyHost,proxyPort);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			
			StringBuffer sb = new StringBuffer();
			 //创建httpGet
			 HttpGet httpGet = new HttpGet("http://d.weibo.com/");
			 httpGet
				.setHeader(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
			 httpGet.setHeader("Host", "d.weibo.com");
			 httpGet.setHeader("Connection", "Keep-Alive");
			 httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			 httpGet.setHeader("Accept-Encoding","gzip, deflate");
			 httpGet.setHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			 httpGet.setHeader("Referer","http://d.weibo.com/");
			 httpGet.setHeader("Cookie","ULV=1425527685104:13:4:4:7855582540943.719.1425527684715:1425435377648; SUHB=0oU4QxKbkSQtvA; SINAGLOBAL=6241086707285.725.1422584921923; UOR=,,www.baidu.com; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WhPGG.h2f_y6hn5a-mIgh1v; myuid=2562669191; un=ue_1@sina.com; SUB=_2AkMjquQ6f8NhqwJRmPkdzGnhaI1yygrEiebDAHzsJxJjHm8x7C-H4TDgYdcs7UbPVNoRpLDkqdE0; YF-Page-G0=00acf392ca0910c1098d285f7eb74a11; _s_tentry=-; Apache=7855582540943.719.1425527684715");
			 //执行
			  HttpResponse response = client.execute(httpGet);
			  
			  HttpEntity entry = response.getEntity();
			  if(entry != null)
			  {
			   InputStreamReader is = new InputStreamReader(entry.getContent(),"UTF-8");
			   BufferedReader br = new BufferedReader(is);
			   String str = null;
			   while((str = br.readLine()) != null)
			   {
			    sb.append(str.trim());
			   }
			   br.close();
			  }
			  
			 System.out.println(sb.toString());
			 String source=sb.toString();
			if (source.indexOf("发现－热门微博") != -1) {
				System.out.println("验证ip能否访问微博："+ip+"  "+ port + "   ture!");
				return true;
			} else{
				System.out.println("验证ip能否访问微博："+ip+"  "+ port + "   false!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("验证ip能否访问微博："+ip+"  "+ port + "   超时false!");
		} 
    	return false;
    	
	}

	public static void main(String[] args) {
		ProxyUtil g = new ProxyUtil();
//		g.getProxyFromProxy360();
		g.getProxyFromPaChong();
				
	}
}
