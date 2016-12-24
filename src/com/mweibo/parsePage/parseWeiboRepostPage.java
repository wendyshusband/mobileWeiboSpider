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

import com.sina.pojos.WeiboRepostPojo;
import com.sina.util.FileUtil;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.parseUtil;

public class parseWeiboRepostPage {
	RadisUtil ru = new RadisUtil();//radis连接对象
	parseUtil pu = new parseUtil();
	public void parseAndStorePage(){
		while(!ru.isQueEmpty("mws.page.weiboRepostQue") ){
			String page = null;
	//		page="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>转发微博</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009\" class=\"nl\">首页</a>|<a href=\"http://weibo.cn/msg/?tf=5_010\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012\" class=\"nl\">搜索</a>|<a href=\"/repost/DrN7x9Zcs?uid=2656274875&amp;mp=380&amp;page=23&amp;rand=3621&amp;p=r\" class=\"nl\">刷新</a></div><div class=\"c\"><a href=\"/cctvxinwen?rand=809078\">返回央视新闻的微博</a></div><div class=\"s\"></div><div class=\"c\" id=\"M_\"><div>    <a href=\"/cctvxinwen\">央视新闻</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><img src=\"http://u1.sinaimg.cn/upload/h5/img/hyzs/donate_btn_s.png\" alt=\"M\"/>    <span class=\"ctt\">:【92岁阿昌族老兵离世 转发送送老人！[心]】18日，被称为“中国远征军阿昌族最后一个抗战老兵”的廖兴根，在云南云龙县的家中安然离世，终年91岁。廖老曾渡怒江，入缅甸，参与滇西抗战，1951年退伍回乡。2015年，他获得了中国人民抗日战争胜利70周年纪念章。转发送送老兵！（央视记者陈政 图自段国庆）</span>            &nbsp;[<a href=\"/mblog/picAll/DrN7x9Zcs?rl=1\">组图共9张</a>]    &nbsp;</div><div><a href=\"/mblog/pic/DrN7x9Zcs?rl=1\"><img src=\"http://ww1.sinaimg.cn/wap180/9e5389bbjw1f32didlesmj20gb0avgnx.jpg\" alt=\"图片\" class=\"ib\" /></a>&nbsp;<a href=\"/mblog/oripic?id=DrN7x9Zcs&amp;u=9e5389bbjw1f32didlesmj20gb0avgnx&amp;rl=1\">原图</a>                <!-- 是否进行翻译 -->        &nbsp;    <span class=\"ct\">今天 07:20    </span>        &nbsp;<a href=\"/spam/?mid=DrN7x9Zcs&amp;fuid=2656274875&amp;type=1&amp;rl=1\">举报</a>&nbsp;<a href=\"/fav/addFav/DrN7x9Zcs?rl=1&amp;st=4d5f0e\">收藏</a>&nbsp;<a href=\"/mblog/operation/DrN7x9Zcs?uid=2656274875&amp;rl=1\" >操作</a>    </div></div><div class=\"c\"></div><div><span class=\"pms\" id=\"rt\">转发&nbsp;</span><span>&nbsp;<a href=\"/comment/DrN7x9Zcs?&amp;uid=2656274875&amp;#cmtfrm\" >评论</a>&nbsp;</span><span >&nbsp;<a href=\"/attitude/DrN7x9Zcs?#attitude\">赞[0]</a>&nbsp;</span><br/></div><div class=\"pms\">转发理由只显示前140字<form action=\"/repost/dort/DrN7x9Zcs?st=4d5f0e\" method=\"post\" id=\"mblogform\"><div>    <input type=\"hidden\" name=\"act\" value=\"dort\" />            <input type=\"hidden\" name=\"rl\" value=\"1\" />    <input type=\"hidden\" name=\"id\" value=\"DrN7x9Zcs\" />    <textarea name=\"content\" rows=\"2\" cols=\"20\" id=\"content\"></textarea><br/>            <input type=\"submit\" value=\"转发\" />&nbsp;<input type=\"submit\" name=\"rtmsg\" value=\"私信转发\" /> <br/>    <input type=\"checkbox\" name=\"rtrootcomment\"  />同时作为给央视新闻<img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><img src=\"http://u1.sinaimg.cn/upload/h5/img/hyzs/donate_btn_s.png\" alt=\"M\"/>的评论发布.      </div></form></div><div class=\"s\"></div><div class=\"c\"><a href=\"/cctvxinwen?rand=809078\">返回央视新闻的微博</a></div></body></html>";
			try {
				page = ru.popWeiboRepostPageFromPageQue();
				ArrayList<WeiboRepostPojo> sw = parsePage(page);
				if(sw == null)
					continue;
				storePojo(sw);
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	Pattern pt_uid = Pattern.compile("uid=(\\d+)");
	Pattern pt_wid = Pattern.compile("/comment/(\\w+)?");
	Pattern pt_ruid = Pattern.compile("/u/(\\d+)");
	Pattern pt_comment = Pattern.compile("</a>[\\s]*(.+)[\\s]*<span class=\"cc\">");
	private ArrayList<WeiboRepostPojo> parsePage(String page) {
		if(StringUtil.isBlank(page))
			return null;
		Element body = JsoupUtil.getBody(page);
		//1.被转发者id,微博id
		Elements idUrl = body.select("div").select("span").select("a");
		//System.out.println("idUrl="+idUrl);
		String id ="";
		String wid ="";
		for(int j=0;j<idUrl.size();j++){
			if(pu.isMatch(idUrl.get(j).text(), "评论")){
				//System.out.println("sssssssssssssssss"+idUrl.get(j));
				id = pu.matchStringGroup1(idUrl.get(j).attr("href"), pt_uid);
				wid = pu.matchStringGroup1(idUrl.get(j).attr("href"), pt_wid);
				break;
			}
		}
		//System.exit(0);
		//2.具体的转发数据
		ArrayList<WeiboRepostPojo> sw = new ArrayList<WeiboRepostPojo>();
		if(wid!=null && id!=null){//被评论的用户ID和微博ID必须有
			Elements repostDiv = body.select("div.c");
			for(Element div:repostDiv){
				if(div == repostDiv.get(0) || div == repostDiv.get(1) ||div.text().hashCode() =="".hashCode()){//去除”返回**的微博“以及首页的微博信息
					continue;
				}
				WeiboRepostPojo wrp = new WeiboRepostPojo();
				//转发者ID
				String uid = pu.matchStringGroup1(div.select("a").attr("href"), pt_ruid);
				if(uid ==null || uid.hashCode() =="".hashCode()){
					continue;
				}
				//转发时评论
				String comment = pu.matchStringGroup1(div.toString(), pt_comment); 
				//comment="s";
				//转发时间和来源
				Element childNode = div.select("span.ct").first();
				if( childNode!=null ){
					String timeStr = childNode.text();
					String wbTime = "";
					if(pu.isMatch(timeStr, "分钟前")){//1： x分钟前格式时间戳
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.add(Calendar.MINUTE, +1);//+1今天的时间加一天		
						Date date = calendar.getTime();		
						wbTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
						//System.out.println("分钟前"+timeStr);
					}
					else if(pu.isMatch(timeStr, "刚刚")){//2： “刚刚” 格式时间戳
						wbTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
						//System.out.println("刚刚"+timeStr);
					}
					else //3: XXXX年XX月XX日 或者 XX月XX日 或者 今天 XX:XX 
					{
						if(pu.isMatch(timeStr, "今天")){
							wbTime = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
						//	System.out.println("今天"+timeStr);
						}
						else if(pu.isMatch(timeStr, "\\d{4}-\\d{2}-\\d{2}")){
							wbTime += pu.matchStringGroup1(timeStr, "(\\d+)-")+"-";
							wbTime += pu.matchStringGroup1(timeStr, "-(\\d\\d)-")+"-";
							wbTime += pu.matchStringGroup1(timeStr, "-(\\d\\d) ")+" ";
						//	System.out.println("年份"+timeStr);
						}
						else if(pu.isMatch(timeStr, "\\d{2}月\\d{2}日")){
							wbTime += new SimpleDateFormat("yyyy-").format(new Date());
							wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d)月")+"-";
							wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d)日")+" ";
						//	System.out.println("几月几日"+timeStr);
						}
						else
							wbTime = "";
						//获取时间
						wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d:\\d\\d)");
					} 
					String wbSource = pu.matchStringGroup1(timeStr, "来自(.+)");
				//	System.out.println("zuizhong="+wbTime);
				//	System.out.println("wbSource="+wbSource);
					wrp.setTime(wbTime);
					wrp.setSource(wbSource);
				}//if child
				//System.out.println("comment="+comment);
			//	System.out.println("uid="+uid);
				wrp.setComment(comment);
				wrp.setId(id);
				wrp.setWid(wid);
				wrp.setUid(uid);
				System.out.println("解析到转发信息："+wrp);
				sw.add(wrp);
			}//for
		}//if id!=0
		return sw;
	}
	
	private void storePojo(ArrayList<WeiboRepostPojo> sw) throws IOException {
		if(sw !=null && sw.size()!=0){
			FileUtil fu = new FileUtil();
			for(int i=0;i<sw.size();i++){
				if(sw.get(i).getId() !=null && sw.get(i).getId().hashCode() != "".hashCode()){
					fu.writeFileAppend(sw.get(i).toJson(), "./weiboUser/"+sw.get(i).getId()+"/weibo/repost.json");
				}else{
					System.err.println("用户ID为空，舍弃当前转发信息！");
				}
			}
			System.out.println("储存转发信息成功！");
		}else{
			System.out.println("储存转发信息0个！");
		}
	}
	
	public static void main(String[] args) {
		parseWeiboRepostPage r = new parseWeiboRepostPage();
		r.parseAndStorePage();
	}

}
