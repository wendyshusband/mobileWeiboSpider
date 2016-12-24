package com.mweibo.parsePage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.pojos.UserFansPojo;
import com.sina.pojos.UserInfoPojo;
import com.sina.util.CommonUtil;
import com.sina.util.FileUtil;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.parseUtil;

public class parseUserFansPage {
	RadisUtil ru = new RadisUtil();//radis连接对象
	//持续爬取页面
	public void parsePageAndStore(){
		while( !ru.isQueEmpty("mws.page.userFansQue") ){
			String page = null;
			//page="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>姜妍二胖的粉丝</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009\" class=\"nl\">首页</a>|<a href=\"http://weibo.cn/msg/?tf=5_010\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012\" class=\"nl\">搜索</a>|<a href=\"/1861203461/fans?rand=1537&amp;p=r\" class=\"nl\">刷新</a></div><div class=\"u\"><div class=\"ut\">姜妍二胖的粉丝&nbsp;<a href=\"/attention/add?uid=1861203461&amp;rl=1&amp;st=4d5f0e\">加关注</a></div><div class=\"tip2\"><a href=\"/1861203461/profile\">微博[1158]</a>&nbsp;<a href=\"/1861203461/follow\">关注[299]</a>&nbsp;<span class=\"tc\">粉丝[2725252]</span>&nbsp;<a href=\"/attgroup/opening?uid=1861203461\">分组[1]</a>&nbsp;<a href=\"/at/weibo?uid=1861203461\">@她的</a></div></div><div class=\"c\"> <table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/2177122821\"><img src=\"http://tp2.sinaimg.cn/2177122821/50/5752816859/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/2177122821\">小酒窝长睫毛双下巴</a><br/>粉丝328人<br/><a href=\"http://weibo.cn/attention/add?uid=2177122821&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/536318345\"><img src=\"http://tp4.sinaimg.cn/1688979803/50/22867519033/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/536318345\">Davy_Young</a><br/>粉丝261人<br/><a href=\"http://weibo.cn/attention/add?uid=1688979803&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/yydayao\"><img src=\"http://tp4.sinaimg.cn/2869522035/50/5748504574/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/yydayao\">watermelon-瑶</a><img src=\"http://u1.sinaimg.cn/upload/2011/08/16/5547.gif\" alt=\"达人\"/><br/>粉丝229人<br/><a href=\"http://weibo.cn/attention/add?uid=2869522035&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/szq1198672565\"><img src=\"http://tp1.sinaimg.cn/3267651792/50/5755260233/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/szq1198672565\">大大大大大越纸</a><br/>粉丝101人<br/><a href=\"http://weibo.cn/attention/add?uid=3267651792&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/338682227\"><img src=\"http://tp4.sinaimg.cn/2608245923/50/5752984313/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/338682227\">TKA27</a><img src=\"http://u1.sinaimg.cn/upload/2011/07/28/5338.gif\" alt=\"V\"/><br/>粉丝954人<br/><a href=\"http://weibo.cn/attention/add?uid=2608245923&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/2182837134\"><img src=\"http://tp3.sinaimg.cn/2182837134/50/22831028383/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/2182837134\">红薯family</a><br/>粉丝29人<br/><a href=\"http://weibo.cn/attention/add?uid=2182837134&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/kevinsfantasy\"><img src=\"http://tp3.sinaimg.cn/2104319990/50/5628513611/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/kevinsfantasy\">大摇大摆北极熊</a><img src=\"http://u1.sinaimg.cn/upload/2011/08/16/5547.gif\" alt=\"达人\"/><br/>粉丝290人<br/><a href=\"http://weibo.cn/attention/add?uid=2104319990&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/2267249943\"><img src=\"http://tp4.sinaimg.cn/2267249943/50/5755994759/1\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/2267249943\">小鬼--大人</a><br/>粉丝85人<br/><a href=\"http://weibo.cn/attention/add?uid=2267249943&amp;rl=1&amp;st=4d5f0e\">关注他</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/3742735557\"><img src=\"http://tp2.sinaimg.cn/3742735557/50/5721572215/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/3742735557\">Ling羽化微飘</a><br/>粉丝34人<br/><a href=\"http://weibo.cn/attention/add?uid=3742735557&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table> <div class=\"s\"></div><table><tr><td valign=\"top\" style=\"width: 52px\"><a href=\"http://weibo.cn/u/5837693433\"><img src=\"http://tp2.sinaimg.cn/5837693433/50/5751450423/0\" alt=\"pic\" /></a></td><td valign=\"top\"><a href=\"http://weibo.cn/u/5837693433\">周末199810</a><br/>粉丝21人<br/><a href=\"http://weibo.cn/attention/add?uid=5837693433&amp;rl=1&amp;st=4d5f0e\">关注她</a></td></tr></table><div class=\"c\"><form action=\"http://weibo.cn/attention/batchadd?st=4d5f0e\" method=\"post\"><div><input type=\"hidden\" name=\"rl\" value=\"1\" /><input type=\"hidden\" name=\"f\" value=\"\" /><input type=\"hidden\" name=\"uidList\" value=\"2177122821,1688979803,2869522035,3267651792,2608245923,2182837134,2104319990,2267249943,3742735557,5837693433\" /><input type=\"submit\" value=\"关注以上这些人\" /></div></form></div><div class=\"s\"></div><div class=\"pa\" id=\"pagelist\"><form action=\"/1861203461/fans\" method=\"post\"><div><a href=\"/1861203461/fans?page=2\">下页</a>&nbsp;<input name=\"mp\" type=\"hidden\" value=\"20\" /><input type=\"text\" name=\"page\" size=\"2\" style='-wap-input-format: \"*N\"' /><input type=\"submit\" value=\"跳页\" />&nbsp;1/20页</div></form></div></div><div class=\"cd\"><a href=\"#top\"><img src=\"http://u1.sinaimg.cn/3g/image/upload/0/62/203/18979/5e990ec2.gif\" alt=\"TOP\"/></a></div><div class=\"pms\"> <a href=\"http://weibo.cn\">首页</a>.<a href=\"http://weibo.cn/topic/240489\">反馈</a>.<a href=\"http://weibo.cn/page/91\">帮助</a>.<a  href=\"http://down.sina.cn/weibo/default/index/soft_id/1/mid/0\"  >客户端</a>.<a href=\"http://weibo.cn/spam/?rl=1&amp;type=3&amp;fuid=1861203461\" class=\"kt\">举报</a>.<a href=\"http://passport.sina.cn/sso/logout?r=http%3A%2F%2Fweibo.cn%2Fpub%2F%3Fvt%3D&amp;entry=mweibo\">退出</a></div><div class=\"c\">设置:<a href=\"http://weibo.cn/account/customize/skin?tf=7_005&amp;st=4d5f0e\">皮肤</a>.<a href=\"http://weibo.cn/account/customize/pic?tf=7_006&amp;st=4d5f0e\">图片</a>.<a href=\"http://weibo.cn/account/customize/pagesize?tf=7_007&amp;st=4d5f0e\">条数</a>.<a href=\"http://weibo.cn/account/privacy/?tf=7_008&amp;st=4d5f0e\">隐私</a></div><div class=\"c\">彩版|<a href=\"http://m.weibo.cn/?tf=7_010\">触屏</a>|<a href=\"http://weibo.cn/page/521?tf=7_011\">语音</a></div><div class=\"b\">weibo.cn[04-20 13:54]</div></body></html>";
			try {
				page = ru.popUserFansPageFromPageQue();
				UserFansPojo ufp = parsePage(page);
				if(ufp == null)
					continue;
				storePojo(ufp);
				break;
			} catch (Exception e) {
//				if(page != null)	//解析出现问题直接忽略，不放回
//					ru.addUserInfoPage(page);
				e.printStackTrace();
			}
		}
	}

	//预编译正则表达式，解析更快速，调试也方便
	Pattern pt_uid = Pattern.compile("/(\\d+?)/fans");
	Pattern pt_fansUid = Pattern.compile("uid=(\\d+)");
	private UserFansPojo parsePage(String page){
		if(StringUtil.isBlank(page))
			return null;
		UserFansPojo ufp = new UserFansPojo();	
		Element body = JsoupUtil.getBody(page);
		//1.解析uid，同时判断是否异常页面
		Element eUid = body.select("div.n").first();
		eUid = eUid.select("a[href*=/fans]").first();
		String uid = parseUtil.matchStringGroup1(eUid.attr("href"), pt_uid);
		if(StringUtil.isBlank(uid))
			return null;

		//2.解析粉丝的Uid并构成集合
		List<String> fansUidList = new ArrayList<String>();
		Elements fUids = body.select("table").select("tr");
        for(int i = 0;i<fUids.size();i++){
            Elements tds = fUids.get(i).select("td");
            for(int j = 0;j<tds.size();j++){
            	String fUid = parseUtil.matchStringGroup1(tds.toString(), pt_fansUid);
            	fansUidList.add(fUid);
            }
        }
        fansUidList = CommonUtil.removeEmptyList(fansUidList);//去空
        fansUidList = CommonUtil.removeSameList(fansUidList);//去重
		
        ufp.setUid(uid);
		ufp.setFansUidList(fansUidList);
		System.out.println("解析到"+ufp.getUid()+"的粉丝信息");
		return ufp;
	}
	private void storePojo(UserFansPojo ufp) throws IOException{
		if(ufp !=null && ufp.getFansUidList().size()!=0){
			List<String> fansList = ufp.getFansUidList();
			String temp_id = ufp.getUid();
			FileUtil fu = new FileUtil();
			//fu.writeFile(temp_id+":", "./weiboUser/"+temp_id+"/fans.json");
			for(String fansUid:fansList){
				ru.addUid(fansUid); //存储到radis
				String temp_fansid = fansUid+",";
				if(temp_id !=null && temp_id.hashCode() != "".hashCode()){
					fu.writeFileAppend(temp_fansid, "./weiboUser/"+temp_id+"/fans.json" );
				}else{
					System.err.println("用户ID为空，故舍弃其该粉丝信息");
				}
			}
			System.out.println("存储粉丝成功！");
		}else{
			System.err.println("存储粉丝0个！");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseUserFansPage p = new parseUserFansPage();
		p.parsePageAndStore();
	}

}
