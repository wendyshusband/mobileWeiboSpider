package com.sina.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * 解析工具
 * 
 * @author tzl
 *
 */
public class parseUtil {

	public static Logger logger = Logger.getLogger(parseUtil.class.toString());

	/**
	 * 根据正则表达式提取匹配的内容
	 * 
	 * @param regex
	 * @param content
	 * @return 以列表的形式返回
	 */
	public List<String> parserToList(String regex, String content) {
		List<String> ls = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			ls.add(m.group());
		}
		return ls;
	}

	/**
	 * 根据正则表达式提取匹配的内容
	 * 
	 * @param regex
	 * @param content
	 * @return 以字符串形式返回
	 */
	public String parserToString(String regex, String content) {
		String s = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			s += m.group();
		}
		return s;
	}

	/**
	 * 根据正则表达式提取第一个匹配的内容
	 * 
	 * @param regex
	 * @param content
	 * @return
	 */
	public String parserFirst(String regex, String content) {
		String s = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			s += m.group();
		}
		return s;
	}

	/**
	 * 获取script中的html
	 * 
	 * @param script
	 * @return
	 */
	public static String getHtmlFromScript(String script) {
		String begin = "<script>.*?\"html\":\"";
		String end = "\"}\\)</script>";
		String content = getContent(begin, end, script);

		String html = content.replaceAll("\\\\n", "").replaceAll("\\\\t", "").replaceAll("\\\\r", "").replaceAll("\\\\",
				"");
		return html;
	}

	/**
	 * 获取块中内容
	 * 
	 * @param begin
	 *            块头
	 * @param end
	 *            块尾
	 * @param block
	 * @return
	 */
	public static String getContent(String begin, String end, String block) {
		return block.replaceFirst(begin, "").replaceAll(end, "");
	}

	/**
	 * 提取字符串中的数字
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> getNumber(String content) {
		List<String> ls = new ArrayList<String>();
		Pattern p = Pattern.compile("[0-9][0-9]*");
		Matcher m = p.matcher(content);
		while (m.find()) {
			ls.add(m.group());
		}
		return ls;
	}

	/**
	 * 提取字符串中的第一个数字
	 * 
	 * @param content
	 * @return
	 */
	public static String getFirstNumber(String content) {
		String number = "0";
		Pattern p = Pattern.compile("[0-9][0-9]*");
		Matcher m = p.matcher(content);
		if (m.find()) {
			number = m.group();
		}
		return number;
	}

	/**
	 * 获取html中的title
	 * 
	 * @param content
	 * @return
	 */
	public String getTitle(String content) {
		String title = "";
		String regex = "<title>[\\s\\S]*?</title>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			title += m.group();
		}
		return title;
	}

	/**
	 * 获取用户的domain
	 * 
	 * @param content
	 *            用户页面源码
	 * @return
	 */
	public String getDomain(String content) {
		String domain = "";
		String start = "\\$CONFIG\\['domain'\\]='";
		String end = "';";
		String regex = start + ".*?" + end;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			domain = m.group().replaceAll(start, "").replaceAll(end, "");
		}
		return domain;
	}

	/**
	 * 清洗掉内容中的html元素
	 * 
	 * @param content
	 * @return
	 */
	public String filterHtmlOfContent(String content) {
		String html = "";
		Tag tag = Tag.valueOf("html");
		Element e = new Element(tag, "");

		String regex = "<img.*?/>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			html += m.group();
			// String title=e.select("img").attr("title");
			// content=content.replaceAll("<img.*?"+title+".*?/>",title);
			// m=p.matcher(content);
		}

		regex = "<a.*?</a>";
		p = Pattern.compile(regex);
		m = p.matcher(content);
		while (m.find()) {
			html += m.group();
		}
		//至此html中已经包含了所有html信息，content中还是原信息，开始对html的每个项进行解析，逐步替换掉content的内容
		//String replaceAll(regex, replacement)函数 ,  由于第一个参数支持正则表达式，replacement中出现“$”,会按照$1$2的分组模式进行匹配，
		//当编译器发现“$”后跟的不是整数的时候，就会抛出“非法的组引用”的异常。 微博中有$text$标识符会导致异常
		html = html.replace("$", "\\$");
		
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a");
		Elements imgs = doc.select("img[title]");
		for (Element link : links) {
			String text = link.text();
			String href = link.attr("href");
			if (text.contains("@") || text.contains("#") || text.contains("$")) {
				try {
					content = content.replaceFirst("<a.*?" + text + ".*?</a>", text);
				} catch (Exception e1) {
					e1.printStackTrace();
					content = content.replaceFirst("<a.*?</a>", text);
				}
			}

			else {
				try {
					content = content.replaceFirst("<a.*?</a>", "<[" + text + "]" + href + ">");
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					content = content.replaceFirst("<a.*?</a>", text);
					logger.info("filterHtmlOfContent error");
					logger.info("content:"+content);
					logger.info("action:"+"replaceFirst");
					logger.info("regex:"+"<a.*?</a>");
					logger.info("replaceContent:"+"<[" + text + "]" + href + ">");
				}
			}
		}
		for (Element img : imgs) {
			String title = img.attr("title");
			try {
				content = content.replaceFirst("<img.*?" + title + ".*?/>", title);
			} catch (Exception e2) {
				// TODO: handle exception

			}
		}
		return content;
	}

	/**
	 * 
	 * @param strs
	 *            待匹配字符串,返回group（0），则正则表达式必须加括号
	 * @param regex
	 *            正则表达式
	 * @return 默认返回group（1）
	 */
	public static String matchStringGroup1(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String res = matcher.group(1);
			if (res != null && res != "")
				return res;
			else
				continue;
		}
		return null;
	}
	/**使用 已经编译的 pattern 作
	 *
	 */
	public static String matchStringGroup1(String str,Pattern pattern) {
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String res = matcher.group(1);
			if (res != null && res != "")
				return res;
			else
				continue;
		}
		return null;
	}
	public static boolean isMatch(String str,String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) 
			return true;
		else
			return false;
	}
	public static boolean isMatch(String str,Pattern pattern){
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) 
			return true;
		else
			return false;
	}
	

	
 	public static void main(String[] args) throws IOException {


		parseUtil pu = new parseUtil();
		FileUtil fu = new FileUtil();
		String content = "dsfd 99 dfa0 afafa 9 dca080";
		// List<String>ls=pu.getNumber(content);
		// for(int i=0;i<ls.size();i++){
		// System.out.println(ls.get(i));
		// }
		// for(int i=0;i<2;i++){
		// System.out.println(pu.getFirstNumber(content));
		// }

		content = fu.readFile("./test.txt");
		content = pu.filterHtmlOfContent(content);
		System.out.println(content);

	}
}
