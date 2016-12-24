package com.mweibo.parsePage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.pojos.WeiboCommentPojo;
import com.sina.util.FileUtil;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.parseUtil;

public class parseWeiboCommentPage {
	RadisUtil ru = new RadisUtil();//radis连接对象
	parseUtil pu = new parseUtil();//解析页面
	public void parsePageAndStore(){
		while( !ru.isQueEmpty("mws.page.weiboCommentQue") ){
			String page = null;
	//page="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>评论列表</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009\" class=\"nl\">首页</a>|<a href=\"http://weibo.cn/msg/?tf=5_010\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012\" class=\"nl\">搜索</a>|<a href=\"/comment/DrN7x9Zcs?rl=0&amp;mp=141&amp;page=12&amp;rand=2632&amp;p=r\" class=\"nl\">刷新</a></div><div class=\"c\"><a href=\"/cctvxinwen?rand=126262\">返回央视新闻的微博</a></div><div class=\"s\"></div><div class=\"c\"><div>    <a href=\"/comment/DrN7x9Zcs?\">央视新闻:【92岁阿昌族老兵...</a></div></div><div class=\"c\"></div><div><span>&nbsp;<a href=\"/repost/DrN7x9Zcs?uid=2656274875&amp;#rt\">转发[3781]</a>&nbsp;</span><span class=\"pms\">&nbsp;评论[1405]&nbsp;</span><span >&nbsp;<a href=\"/attitude/DrN7x9Zcs?#attitude\">赞[9893]</a>&nbsp;</span><br/></div><div class=\"pms\" id=\"cmtfrm\"><form action=\"/comments/addcomment?st=4d5f0e\" method=\"post\"><div>    评论只显示前140字:<br/>    <input type=\"hidden\" name=\"srcuid\" value=\"2656274875\" />    <input type=\"hidden\" name=\"id\" value=\"DrN7x9Zcs\" />        <input type=\"hidden\" name=\"rl\" value=\"1\" />    <textarea name=\"content\" rows=\"2\" cols=\"20\"></textarea><br/>        <input type=\"submit\" value=\"评论\" />&nbsp;<input type=\"submit\" value=\"评论并转发\" name=\"rt\" /></div></form></div><div class=\"c\" id=\"C_3966317705219841\">        <a href=\"/u/2647807023\">时空记</a>        :<span class=\"ctt\">英雄楷模，保家卫国，人民永记，一路走好。</span>    &nbsp;<a href=\"/spam/?cid=3966317705219841&amp;fuid=2647807023&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPe2lTUZ/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966317705219841?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:41&nbsp;来自小米M4    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966317700322261\">        <a href=\"/u/2887542654\">汴味坊</a>    <img src=\"http://u1.sinaimg.cn/upload/h5/img/hyzs/donate_btn_s.png\" alt=\"M\"/>    :<span class=\"ctt\">有这样的国人，心里暖暖的</span>    &nbsp;<a href=\"/spam/?cid=3966317700322261&amp;fuid=2887542654&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPe21lPL/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966317700322261?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:41&nbsp;来自iPhone 6 Plus(土豪金)    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966317650353592\">        <a href=\"/u/5045059926\">乔妞妞妞妞</a>        :<span class=\"ctt\">不得不说中国对于老兵的扶持政策只是空谈，不管不问不尊重，对于真正对国家奉献贡献的老兵持此态度，那也难怪中国那么多精英人才投身海外，主要原因没有受到国家的尊重和重用</span>    &nbsp;<a href=\"/spam/?cid=3966317650353592&amp;fuid=5045059926&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPdX1tZ6/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966317650353592?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:41&nbsp;来自iPhone 6s Plus    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966317613199400\">        <a href=\"/u/5726462836\">韦陀祺</a>        :<span class=\"ctt\">央视一天闲的蛋疼，你不怕打脸吗？</span>    &nbsp;<a href=\"/spam/?cid=3966317613199400&amp;fuid=5726462836&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPdTdqje/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966317613199400?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:41&nbsp;来自华为Ascend手机    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966317130335358\">        <a href=\"/u/5726462836\">韦陀祺</a>        :<span class=\"ctt\">央视派人去吊唁了吗？</span>    &nbsp;<a href=\"/spam/?cid=3966317130335358&amp;fuid=5726462836&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPd71pf0/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966317130335358?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:39&nbsp;来自华为Ascend手机    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966316598093574\">        <a href=\"/u/1764406727\">黄国栋A</a>        :<span class=\"ctt\">转发微博</span>    &nbsp;<a href=\"/spam/?cid=3966316598093574&amp;fuid=1764406727&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPcfxXvw/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966316598093574?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:37&nbsp;来自荣耀畅玩4X·快科技    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966316522507323\">        <a href=\"/u/1703795635\">蔺凯L</a>        :<span class=\"ctt\">丢人！！！！！！！！！！还有脸说？！！！！！！！！！！？</span>    &nbsp;<a href=\"/spam/?cid=3966316522507323&amp;fuid=1703795635&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPc8awgH/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966316522507323?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:36&nbsp;来自魅蓝 note2    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966316182545580\">        <a href=\"/u/5653669592\">K先生和J女士</a>        :<span class=\"ctt\">[爱心][爱心][爱心][爱心][爱心][爱心]</span>    &nbsp;<a href=\"/spam/?cid=3966316182545580&amp;fuid=5653669592&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPbAaGdK/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966316182545580?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:35&nbsp;来自乐1    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966315884937151\">        <a href=\"/u/5404501621\">要命的小雨</a>        :<span class=\"ctt\">国家对不起的何止一个</span>    &nbsp;<a href=\"/spam/?cid=3966315884937151&amp;fuid=5404501621&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPb6kInt/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[1]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966315884937151?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:34&nbsp;来自荣耀7 世界有点不同    </span></div>    <div class=\"s\"></div>            <div class=\"c\" id=\"C_3966315872466864\">        <a href=\"/u/1088068780\">急性短暂间歇性精神病人</a>        :<span class=\"ctt\">说到中国远征军就想起了我的团长我的团， 致敬老兵，一路走好！！</span>    &nbsp;<a href=\"/spam/?cid=3966315872466864&amp;fuid=1088068780&amp;type=2&amp;rl=1\">举报</a>    &nbsp;    <span class=\"cc\">    <a href=\"/attitude/DrPb5alK8/update?object_type=comment&amp;uid=5871393971&amp;rl=1&amp;st=4d5f0e\">赞[0]</a></span>        &nbsp;<span class=\"cc\"><a href=\"/comments/reply/DrN7x9Zcs/3966315872466864?rl=1&amp;st=4d5f0e\">回复</a></span>        &nbsp;    <span class=\"ct\">今天 12:34&nbsp;来自iPhone 6s    </span></div>        <div class=\"s\"></div><div class=\"pa\" id=\"pagelist\"><form action=\"/comment/DrN7x9Zcs?rl=0\" method=\"post\"><div><a href=\"/comment/DrN7x9Zcs?rl=0&amp;page=13\">下页</a>&nbsp;<a href=\"/comment/DrN7x9Zcs?rl=0&amp;page=11\">上页</a>&nbsp;<a href=\"/comment/DrN7x9Zcs?rl=0\">首页</a>&nbsp;<input name=\"mp\" type=\"hidden\" value=\"141\" /><input type=\"text\" name=\"page\" size=\"2\" style='-wap-input-format: \"*N\"' /><input type=\"submit\" value=\"跳页\" />&nbsp;12/141页</div></form></div><div class=\"s\"></div><div class=\"c\"><a href=\"/cctvxinwen?rand=126262\">返回央视新闻的微博</a></div></body></html>";
			try {
				page = ru.popWeiboCommentPageFromPageQue();
				ArrayList<WeiboCommentPojo> wcp = parsePage(page);
				if(wcp == null)
					continue;
				storePojo(wcp);
				break;
			} catch (Exception e) {
//				if(page != null)	//解析出现问题直接忽略，不放回
//					ru.addUserInfoPage(page);
				e.printStackTrace();
			}
		}
	}
	Pattern pt_uid = Pattern.compile("uid=(\\d+)");
	Pattern pt_wid = Pattern.compile("/repost/(\\w+)?");
	Pattern pt_cid = Pattern.compile("cid=(\\d+)");
	Pattern pt_fuid = Pattern.compile("fuid=(\\d+)");
	private ArrayList<WeiboCommentPojo> parsePage(String page) {
		if(StringUtil.isBlank(page))
			return null;
		Element body = JsoupUtil.getBody(page);
		Elements idUrl = body.select("div").select("span").select("a");
		//1.被评论者id,微博id
		String id ="";
		String wid ="";
		for(int j=0;j<idUrl.size();j++){
			if(pu.isMatch(idUrl.get(j).text(), "转发\\[")){
				id = pu.matchStringGroup1(idUrl.get(j).attr("href"), pt_uid);
				wid = pu.matchStringGroup1(idUrl.get(j).attr("href"), pt_wid);
				break;
			}
		}
		//System.out.println("1"+id);
		//System.out.println("2"+wid);
		//2.具体的评论数据 
		ArrayList<WeiboCommentPojo> wcp_arr = new ArrayList<WeiboCommentPojo>();
		if(wid!=null && id!=null){//被评论的用户ID和微博ID必须有
			Elements commentDiv = body.select("div.c");
			for(Element div:commentDiv){
				
				if(div == commentDiv.get(0) || div == commentDiv.get(1) ||div.text().hashCode() =="".hashCode()){//去除”返回**的微博“以及首页的微博信息
					continue;
				}
				WeiboCommentPojo wcp = new WeiboCommentPojo();
				Elements cid_a = div.select("a[href*=cid=]");
				String cid = parseUtil.matchStringGroup1(cid_a.attr("href"),pt_cid );
				Elements fuid_a = div.select("a[href*=fuid=]");
				String fuid = parseUtil.matchStringGroup1(fuid_a.attr("href"),pt_fuid );
				Elements content_a = div.select("span.ctt");
				String content =content_a.text();
				//System.out.println(cid);
				//System.out.println(fuid);
				//System.out.println(content);
				Elements time_a = div.select("span.ct");
				//System.out.println("c="+content);
				if( time_a!=null ){
					String timeStr = time_a.text();
					String time = "";
	//				System.out.println("timeStr"+timeStr);
					if(pu.isMatch(timeStr, "分钟前")){//1： x分钟前格式时间戳
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.add(Calendar.MINUTE, +1);//+1今天的时间加一天		
						Date date = calendar.getTime();		
						time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
						//System.out.println("分钟前"+timeStr);
					}
					else if(pu.isMatch(timeStr, "刚刚")){//2： “刚刚” 格式时间戳
						time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
						//System.out.println("刚刚"+timeStr);
					}
					else //3: XXXX年XX月XX日 或者 XX月XX日 或者 今天 XX:XX 
					{
						if(pu.isMatch(timeStr, "今天")){
							time = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
							//System.out.println("今天"+timeStr);
						}
						else if(pu.isMatch(timeStr, "\\d{4}-\\d{2}-\\d{2}")){
							time += pu.matchStringGroup1(timeStr, "(\\d+)-")+"-";
							time += pu.matchStringGroup1(timeStr, "-(\\d\\d)-")+"-";
							time += pu.matchStringGroup1(timeStr, "-(\\d\\d) ")+" ";
							//System.out.println("年份"+timeStr);
						}
						else if(pu.isMatch(timeStr, "\\d{2}月\\d{2}日")){
							time += new SimpleDateFormat("yyyy-").format(new Date());
							time += pu.matchStringGroup1(timeStr, "(\\d\\d)月")+"-";
							time += pu.matchStringGroup1(timeStr, "(\\d\\d)日")+" ";
							//System.out.println("几月几日"+timeStr);
						}
						else
							time = "";
						//获取时间
						time += pu.matchStringGroup1(timeStr, "(\\d\\d:\\d\\d)");
					}
					String wbSource = pu.matchStringGroup1(timeStr, "来自(.+)");
					wcp.setTime(time);
					wcp.setSource(wbSource);
				}//time
				wcp.setContent(content);
				wcp.setUid(fuid);
				wcp.setCid(cid);
				wcp.setWid(wid);
				wcp.setId(id);
				wcp_arr.add(wcp);
				System.out.println("解析到评论信息："+wcp);
			}//for
		}//if id!=0
		return wcp_arr;
	}
	
	private void storePojo(ArrayList<WeiboCommentPojo> wcp) throws IOException {
		if(wcp !=null && wcp.size()!=0){
			FileUtil fu = new FileUtil();
			for(int i=0;i<wcp.size();i++){
				if(wcp.get(i).getId() !=null && wcp.get(i).getId().hashCode() != "".hashCode()){
					fu.writeFileAppend(wcp.get(i).toJson(),"./weiboUser/"+wcp.get(i).getId()+"/weibo/comment.json");
				}else{
					System.err.println("用户ID为空，舍弃当前评论！");
				}
			}
			System.out.println("存储一页评论成功！");
		}else{
			System.out.println("存储一页评论0个！");
		}
	}
	
	public static void main(String[] args) {
		parseWeiboCommentPage pu =new parseWeiboCommentPage();
		pu.parsePageAndStore();
	}
}
