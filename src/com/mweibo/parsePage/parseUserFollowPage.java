package com.mweibo.parsePage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.pojos.UserFollowPojo;
import com.sina.util.CommonUtil;
import com.sina.util.FileUtil;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.parseUtil;

public class parseUserFollowPage {
	RadisUtil ru = new RadisUtil();//radis连接对象
	public void parsePageAndStore(){
		while( !ru.isQueEmpty("mws.page.userFollowQue") ){
			String page = null;
	//		page ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>头条新闻关注的人</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009\" class=\"nl\">首页</a>|<a href=\"http://weibo.cn/msg/?tf=5_010\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012\" class=\"nl\">搜索</a>|<a href=\"/1618051664/follow?mp=20&amp;page=5&amp;rand=4868&amp;p=r\" class=\"nl\">刷新</a></div><div class=\"u\"><div class=\"ut\">头条新闻关注的人&nbsp;<a href=\"/attention/add?uid=1618051664&amp;rl=1&amp;st=4d5f0e\">加关注</a></div><div class=\"tip2\"><a href=\"/1618051664/profile\">微博[114208]</a>&nbsp;<span class=\"tc\">关注[460]</span>&nbsp;<a href=\"/1618051664/fans\">粉丝[50507588]</a>&nbsp;<a href=\"/attgroup/opening?uid=1618051664\">分组[2]</a>&nbsp;<a href=\"/at/weibo?uid=1618051664\">@她的</a></div></div><div class=\"ps\">系统提示：为了避免骚扰，微博智能反垃圾系统已过滤掉部分广告用户。</div> <table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/yzdsb\"><img src=\"http://tp3.sinaimg.cn/1738004582/50/22908647449/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/yzdsb\">燕赵都市报</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝1625909人<br/><a href=\"http://weibo.cn/attention/add?uid=1738004582&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/99du\"><img src=\"http://tp1.sinaimg.cn/1805685140/50/5710058216/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/99du\">安徽网</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝806674人<br/><a href=\"http://weibo.cn/attention/add?uid=1805685140&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/jxfbt\"><img src=\"http://tp4.sinaimg.cn/3687019147/50/40058818135/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/jxfbt\">江西发布</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝665593人<br/><a href=\"http://weibo.cn/attention/add?uid=3687019147&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/zgsybks\"><img src=\"http://tp1.sinaimg.cn/2359441912/50/5728705845/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/zgsybks\">中国水运报</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝374227人<br/><a href=\"http://weibo.cn/attention/add?uid=2359441912&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/hubeiribao\"><img src=\"http://tp1.sinaimg.cn/2827102952/50/40094662957/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/hubeiribao\">湖北日报</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝3013138人<br/><a href=\"http://weibo.cn/attention/add?uid=2827102952&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/zghjxw\"><img src=\"http://tp2.sinaimg.cn/5608027665/50/5727750318/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/zghjxw\">中国环境新闻</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝134752人<br/><a href=\"http://weibo.cn/attention/add?uid=5608027665&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/1818hjy\"><img src=\"http://tp3.sinaimg.cn/2334162530/50/40094664425/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/1818hjy\">1818黄金眼</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝1155247人<br/><a href=\"http://weibo.cn/attention/add?uid=2334162530&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/3840710893\"><img src=\"http://tp2.sinaimg.cn/3840710893/50/22857152460/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/3840710893\">太原中院</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝4702人<br/><a href=\"http://weibo.cn/attention/add?uid=3840710893&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/gdga\"><img src=\"http://tp3.sinaimg.cn/1701367442/50/5735026010/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/gdga\">平安南粤</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5337.gif\" alt=\"V\"/><br/>粉丝5967632人<br/><a href=\"http://weibo.cn/attention/add?uid=1701367442&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table><div class=\"c\"><form action=\"http://weibo.cn/attention/batchadd?st=4d5f0e\" method=\"post\"><div><input type=\"hidden\" name=\"rl\" value=\"1\" /><input type=\"hidden\" name=\"f\" value=\"\" /><input type=\"hidden\" name=\"uidList\" value=\"1738004582,1805685140,3687019147,2359441912,2827102952,5608027665,2334162530,3840710893,1701367442\" /><input type=\"submit\" value=\"关注以上这些人\" /></div></form></div><div class=\"s\"></div><div class=\"pa\" id=\"pagelist\"><form action=\"/1618051664/follow\" method=\"post\"><div><a href=\"/1618051664/follow?page=6\">下页</a>&nbsp;<a href=\"/1618051664/follow?page=4\">上页</a>&nbsp;<a href=\"/1618051664/follow\">首页</a>&nbsp;<input name=\"mp\" type=\"hidden\" value=\"20\" /><input type=\"text\" name=\"page\" size=\"2\" style='-wap-input-format: \"*N\"' /><input type=\"submit\" value=\"跳页\" />&nbsp;5/20页</div></form></div><div class=\"cd\"><a href=\"#top\"><img src=\"http://u1.sinaimg.cn/3g/image/upload/0/62/203/18979/5e990ec2.gif\" alt=\"TOP\"/></a></div><div class=\"pms\"> <a href=\"http://weibo.cn\">首页</a>.<a href=\"http://weibo.cn/topic/240489\">反馈</a>.<a href=\"http://weibo.cn/page/91\">帮助</a>.<a  href=\"http://down.sina.cn/weibo/default/index/soft_id/1/mid/0\"  >客户端</a>.<a href=\"http://weibo.cn/spam/?rl=1&amp;type=3&amp;fuid=1618051664\" class=\"kt\">举报</a>.<a href=\"http://passport.sina.cn/sso/logout?r=http%3A%2F%2Fweibo.cn%2Fpub%2F%3Fvt%3D&amp;entry=mweibo\">退出</a></div><div class=\"c\">设置:<a href=\"http://weibo.cn/account/customize/skin?tf=7_005&amp;st=4d5f0e\">皮肤</a>.<a href=\"http://weibo.cn/account/customize/pic?tf=7_006&amp;st=4d5f0e\">图片</a>.<a href=\"http://weibo.cn/account/customize/pagesize?tf=7_007&amp;st=4d5f0e\">条数</a>.<a href=\"http://weibo.cn/account/privacy/?tf=7_008&amp;st=4d5f0e\">隐私</a></div><div class=\"c\">彩版|<a href=\"http://m.weibo.cn/?tf=7_010\">触屏</a>|<a href=\"http://weibo.cn/page/521?tf=7_011\">语音</a></div><div class=\"b\">weibo.cn[04-20 13:56]</div></body></html>";
			try {
				page = ru.popUserFollowPageFromPageQue();
				UserFollowPojo ufop = parsePage(page);
				if(ufop == null)
					continue;
				storePojo(ufop);
				break;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	Pattern pt_uid = Pattern.compile("/(\\d+?)/follow");
	Pattern pt_followUid = Pattern.compile("uid=(\\d+)");
	
	public UserFollowPojo parsePage(String page){
		if(StringUtil.isBlank(page))
			return null;
		UserFollowPojo ufop = new UserFollowPojo();	
		Element body = JsoupUtil.getBody(page);
		//1.解析uid，同时判断是否异常页面
		Element eUid = body.select("div.n").first();
		eUid = eUid.select("a[href*=/follow]").first();
		String uid = parseUtil.matchStringGroup1(eUid.attr("href"), pt_uid);
		if(StringUtil.isBlank(uid))
			return null;
		//2.获取关注的人的uid集合
		List<String> followUidList = new ArrayList<String>();
		Elements foUids = body.select("table").select("tr");
		for(int i = 0;i<foUids.size();i++){
		    Elements tds = foUids.get(i).select("td");
		    for(int j = 0;j<tds.size();j++){		            	
		    	String fUid = parseUtil.matchStringGroup1(tds.toString(), pt_followUid);
		    	followUidList.add(fUid);
		    }
		}
		followUidList = CommonUtil.removeEmptyList(followUidList);//去空
		followUidList = CommonUtil.removeSameList(followUidList);//去重      
		ufop.setUid(uid);
		ufop.setFollowUidList(followUidList);
		System.out.println("解析到"+ufop.getUid()+"的关注信息");
		//System.out.println(ufop.getFollowUidList());
		//System.out.println(ufop);
		//System.exit(0);
		return ufop;
	}

	private void storePojo(UserFollowPojo ufop) throws IOException {
		if(ufop !=null && ufop.getFollowUidList().size()!=0 ){
			List<String> followList = ufop.getFollowUidList();
			String temp_id = ufop.getUid();
			FileUtil fu = new FileUtil();
		//	fu.writeFile(temp_id+":", "./weiboUser/"+temp_id+"/focus.json");
			for(String followUid:followList){
				ru.addUid(followUid); //存储到radis
				String temp_fansid = followUid+",";
				if(temp_id !=null && temp_id.hashCode() != "".hashCode()){
					fu.writeFileAppend(temp_fansid, "./weiboUser/"+temp_id+"/focus.json" );
				}else{
					System.err.println("用户ID为空，故舍弃其该关注的人的信息");
				}
			}
			System.out.println("存储关注的人成功！");
		}else{
			System.err.println("存储关注的人0个！");
		}
	}
	
	public static void main(String[] args) {
		parseUserFollowPage p = new parseUserFollowPage();
		p.parsePageAndStore();
	}
}
