package com.mweibo.parsePage;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.pojos.UserInfoPojo;
import com.sina.util.FileUtil;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.parseUtil;

public class parseUserInfoPage {
	RadisUtil ru = new RadisUtil();//radis连接对象
	//持续爬取页面
	public void parsePageAndStore(){
		while( !ru.isQueEmpty("mws.page.userInfoQue") ){
			String page = null;
	//		page ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>伍嘉成5十X的资料</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009&amp;vt=4\" class=\"nl\">首页<span class=\"tk\">!</span></a>|<a href=\"http://weibo.cn/msg/?tf=5_010&amp;vt=4\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn?vt=4\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012&amp;vt=4\" class=\"nl\">搜索</a>|<a href=\"/1639421885/info?rand=4918&amp;p=r&amp;vt=4\" class=\"nl\">刷新</a></div><div class=\"c\"><img src=\"http://tp2.sinaimg.cn/1639421885/180/5751303146/1\" alt=\"头像\" /></div><div class=\"c\">会员等级：3级&nbsp;<a href=\"/member/present/comfirmTime?uid=1639421885&amp;vt=4\">送Ta会员</a><br/><img src=\"http://img.t.sinajs.cn/t4/style/images/medal/1230_s.gif?version=\" alt=\"星海音乐学院\" />&nbsp;<img src=\"http://img.t.sinajs.cn/t4/style/images/medal/1163_s.gif?version=\" alt=\"扬V耀5\" />&nbsp;<img src=\"http://img.t.sinajs.cn/t4/style/images/medal/108_s.gif?version=\" alt=\"天下无双\" />&nbsp;<img src=\"http://img.t.sinajs.cn/t4/style/images/medal/312_s.gif?version=\" alt=\"一举成名\" />&nbsp;<img src=\"http://img.t.sinajs.cn/t4/style/images/medal/539_s.gif?version=\" alt=\"电视迷\" />&nbsp;<a href=\"/medal/owned?uid=1639421885&amp;vt=4\">更多勋章</a></div><div class=\"tip\">基本信息</div><div class=\"c\">昵称:伍嘉成5十X<br/>认证:《燃烧吧少年！》人气少年<br/>达人:美食 音乐 时尚 <br/>性别:男<br/>地区:广东 广州<br/>生日:巨蟹座<br/>认证信息：《燃烧吧少年！》人气少年<br/>简介:大家好，我是破音小网址。 工作联系：rsbsn@sina.com<br/>标签:<a href=\"/search/?keyword=%E5%B7%A8%E8%9F%B9%E7%94%B7&amp;stag=1&amp;vt=4\">巨蟹男</a>&nbsp;<a href=\"/search/?keyword=%E4%B9%90%E8%B6%A3&amp;stag=1&amp;vt=4\">乐趣</a>&nbsp;<a href=\"/search/?keyword=%E8%87%AA%E7%94%B1&amp;stag=1&amp;vt=4\">自由</a>&nbsp;<a href=\"/account/privacy/tags/?uid=1639421885&amp;vt=4&amp;st=f48dba\">更多&gt;&gt;</a><br/></div><div class=\"tip\">学习经历</div><div class=\"c\">·台师高级中学&nbsp;09级<br/></div><div class=\"tip\">工作经历</div><div class=\"c\">·星海音乐学院&nbsp;<br/></div><div class=\"tip\">其他信息</div><div class=\"c\">互联网:http://weibo.com/u/1639421885<br/>手机版:http://weibo.cn/u/1639421885<br/><a href=\"/album/albumlist?fuid=1639421885&amp;vt=4\">他的相册&gt;&gt;</a></div><div class=\"cd\"><a href=\"#top\"><img src=\"http://u1.sinaimg.cn/3g/image/upload/0/62/203/18979/5e990ec2.gif\" alt=\"TOP\"/></a></div><div class=\"pms\"> <a href=\"http://weibo.cn?vt=4\">首页<span class=\"tk\">!</span></a>.<a href=\"http://weibo.cn/topic/240489?vt=4\">反馈</a>.<a href=\"http://weibo.cn/page/91?vt=4\">帮助</a>.<a  href=\"http://down.sina.cn/weibo/default/index/soft_id/1/mid/0?vt=4\"  >客户端</a>.<a href=\"http://weibo.cn/spam/?rl=1&amp;type=3&amp;fuid=1639421885&amp;vt=4\" class=\"kt\">举报</a>.<a href=\"http://passport.sina.cn/sso/logout?r=http%3A%2F%2Fweibo.cn%2Fpub%2F%3Fvt%3D4&amp;entry=mweibo&amp;vt=4\">退出</a></div><div class=\"c\">设置:<a href=\"http://weibo.cn/account/customize/skin?tf=7_005&amp;vt=4&amp;st=f48dba\">皮肤</a>.<a href=\"http://weibo.cn/account/customize/pic?tf=7_006&amp;vt=4&amp;st=f48dba\">图片</a>.<a href=\"http://weibo.cn/account/customize/pagesize?tf=7_007&amp;vt=4&amp;st=f48dba\">条数</a>.<a href=\"http://weibo.cn/account/privacy/?tf=7_008&amp;vt=4&amp;st=f48dba\">隐私</a></div><div class=\"c\">彩版|<a href=\"http://m.weibo.cn/?tf=7_010&amp;vt=4\">触屏</a>|<a href=\"http://weibo.cn/page/521?tf=7_011&amp;vt=4\">语音</a></div><div class=\"b\">weibo.cn[04-12 18:51]</div></body></html>";
	//		page="<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta http-equiv=\"Cache-Control\" content=\"no-cache\"/><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=2.0\" /><link rel=\"icon\" sizes=\"any\" mask href=\"http://h5.sinaimg.cn/upload/2015/05/15/28/WeiboLogoCh.svg\" color=\"black\"><meta name=\"MobileOptimized\" content=\"240\"/><title>央视新闻的资料</title><style type=\"text/css\" id=\"internalStyle\">html,body,p,form,div,table,textarea,input,span,select{font-size:12px;word-wrap:break-word;}body{background:#F8F9F9;color:#000;padding:1px;margin:1px;}table,tr,td{border-width:0px;margin:0px;padding:0px;}form{margin:0px;padding:0px;border:0px;}textarea{border:1px solid #96c1e6}textarea{width:95%;}a,.tl{color:#2a5492;text-decoration:underline;}/*a:link {color:#023298}*/.k{color:#2a5492;text-decoration:underline;}.kt{color:#F00;}.ib{border:1px solid #C1C1C1;}.pm,.pmy{clear:both;background:#ffffff;color:#676566;border:1px solid #b1cee7;padding:3px;margin:2px 1px;overflow:hidden;}.pms{clear:both;background:#c8d9f3;color:#666666;padding:3px;margin:0 1px;overflow:hidden;}.pmst{margin-top: 5px;}.pmsl{clear:both;padding:3px;margin:0 1px;overflow:hidden;}.pmy{background:#DADADA;border:1px solid #F8F8F8;}.t{padding:0px;margin:0px;height:35px;}.b{background:#e3efff;text-align:center;color:#2a5492;clear:both;padding:4px;}.bl{color:#2a5492;}.n{clear:both;background:#436193;color:#FFF;padding:4px; margin: 1px;}.nt{color:#b9e7ff;}.nl{color:#FFF;text-decoration:none;}.nfw{clear:both;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.s{border-bottom:1px dotted #666666;margin:3px;clear:both;}.tip{clear:both; background:#c8d9f3;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tip2{color:#000000;padding:2px 3px;clear:both;}.ps{clear:both;background:#FFF;color:#676566;border:1px solid #BACDEB;padding:3px;margin:2px 1px;}.tm{background:#feffe5;border:1px solid #e6de8d;padding:4px;}.tm a{color:#ba8300;}.tmn{color:#f00}.tk{color:#ffffff}.tc{color:#63676A;}.c{padding:2px 5px;}.c div a img{border:1px solid #C1C1C1;}.ct{color:#9d9d9d;font-style:italic;}.cmt{color:#9d9d9d;}.ctt{color:#000;}.cc{color:#2a5492;}.nk{color:#2a5492;}.por {border: 1px solid #CCCCCC;height:50px;width:50px;}.me{color:#000000;background:#FEDFDF;padding:2px 5px;}.pa{padding:2px 4px;}.nm{margin:10px 5px;padding:2px;}.hm{padding:5px;background:#FFF;color:#63676A;}.u{margin:2px 1px;background:#ffffff;border:1px solid #b1cee7;}.ut{padding:2px 3px;}.cd{text-align:center;}.r{color:#F00;}.g{color:#0F0;}.bn{background: transparent;border: 0 none;text-align: left;padding-left: 0;}</style><script>if(top != self){top.location = self.location;}</script></head><body><div class=\"n\" style=\"padding: 6px 4px;\"><a href=\"http://weibo.cn/?tf=5_009\" class=\"nl\">首页</a>|<a href=\"http://weibo.cn/msg/?tf=5_010\" class=\"nl\">消息</a>|<a href=\"http://huati.weibo.cn\" class=\"nl\">话题</a>|<a href=\"http://weibo.cn/search/?tf=5_012\" class=\"nl\">搜索</a>|<a href=\"/2656274875/info?rand=9564&amp;p=r\" class=\"nl\">刷新</a></div><div class=\"c\"><img src=\"http://tp4.sinaimg.cn/2656274875/180/5744017446/1\" alt=\"头像\" /></div><div class=\"c\">会员等级：5级&nbsp;<a href=\"/member/present/comfirmTime?uid=2656274875\">送Ta会员</a><br/>用户勋章处于隐私状态</div><div class=\"tip\">基本信息</div><div class=\"c\">昵称:央视新闻<br/>认证:中央电视台新闻中心官方微博<br/>性别:男<br/>地区:北京<br/>生日:2012-11-01<br/>认证信息：中央电视台新闻中心官方微博<br/>简介:“央视新闻”微博是中央电视台新闻中心官方微博，是央视重大新闻、突发事件、重点报道的首发平台。<br/></div><div class=\"tip\">其他信息</div><div class=\"c\">互联网:http://weibo.com/cctvxinwen<br/>手机版:http://weibo.cn/cctvxinwen<br/><a href=\"/album/albumlist?fuid=2656274875\">他的相册&gt;&gt;</a></div><div class=\"cd\"><a href=\"#top\"><img src=\"http://u1.sinaimg.cn/3g/image/upload/0/62/203/18979/5e990ec2.gif\" alt=\"TOP\"/></a></div><div class=\"pms\"> <a href=\"http://weibo.cn\">首页</a>.<a href=\"http://weibo.cn/topic/240489\">反馈</a>.<a href=\"http://weibo.cn/page/91\">帮助</a>.<a  href=\"http://down.sina.cn/weibo/default/index/soft_id/1/mid/0\"  >客户端</a>.<a href=\"http://weibo.cn/spam/?rl=1&amp;type=3&amp;fuid=2656274875\" class=\"kt\">举报</a>.<a href=\"http://passport.sina.cn/sso/logout?r=http%3A%2F%2Fweibo.cn%2Fpub%2F%3Fvt%3D&amp;entry=mweibo\">退出</a></div><div class=\"c\">设置:<a href=\"http://weibo.cn/account/customize/skin?tf=7_005&amp;st=4d5f0e\">皮肤</a>.<a href=\"http://weibo.cn/account/customize/pic?tf=7_006&amp;st=4d5f0e\">图片</a>.<a href=\"http://weibo.cn/account/customize/pagesize?tf=7_007&amp;st=4d5f0e\">条数</a>.<a href=\"http://weibo.cn/account/privacy/?tf=7_008&amp;st=4d5f0e\">隐私</a></div><div class=\"c\">彩版|<a href=\"http://m.weibo.cn/?tf=7_010\">触屏</a>|<a href=\"http://weibo.cn/page/521?tf=7_011\">语音</a></div><div class=\"b\">weibo.cn[04-20 14:01]</div></body></html>";
			try {
				page = ru.popUserInfoPageFromPageQue();
				UserInfoPojo uip = parsePage(page);
				if(uip == null)
					continue;
				storePojo(uip);
				break;
			} catch (Exception e) {
//				if(page != null)	//解析出现问题直接忽略，不放回
//					ru.addUserInfoPage(page);
				e.printStackTrace();
			}
		}
	}

	//预编译正则表达式，解析更快速，调试也方便
	Pattern pt_uid = Pattern.compile("/(\\d+?)/info");
	Pattern pt_nickname = Pattern.compile(">昵称:(.+?)<");
	Pattern pt_authentication = Pattern.compile(">认证:(.+?)<");
	Pattern pt_gender = Pattern.compile("性别:(.+?)<");
	Pattern pt_location = Pattern.compile("地区:(.+?)<");
	Pattern pt_birthday = Pattern.compile("生日:(.+?)<");
	Pattern pt_slogan = Pattern.compile("简介:(.+?)<");
	Pattern pt_tag = Pattern.compile("标签:(.+?)更多");
	Pattern pt_checkUserInfoPage = Pattern.compile("昵称.+?性别.+?地区.+?生日");
	
	private UserInfoPojo parsePage(String page){
		if(StringUtil.isBlank(page))
			return null;
		UserInfoPojo uip = new UserInfoPojo();	
		Element body = JsoupUtil.getBody(page);
		//1.解析uid，同时判断是否异常页面
		Element eUid = body.select("div.n").first();
		eUid = eUid.select("a[href*=/info]").first();
		//System.out.println(eUid);
		String uid = parseUtil.matchStringGroup1(eUid.attr("href"), pt_uid);
		if(StringUtil.isBlank(uid))
			return null;
		//2.解析其他参数
		String nickname = parseUtil.matchStringGroup1(page,pt_nickname );
		String authentication = parseUtil.matchStringGroup1(page,pt_authentication );
		String gender = parseUtil.matchStringGroup1(page,pt_gender );
		String location = parseUtil.matchStringGroup1(page,pt_location );
		String birthday = parseUtil.matchStringGroup1(page,pt_birthday );
		String slogan = parseUtil.matchStringGroup1(page,pt_slogan );
		
		//3.头像地址
		String head = "";
		Elements alt = body.select("div.c").select("img");
		if(alt.attr("alt").hashCode() == "头像".hashCode()){
			head = body.select("div.c").select("img").attr("src");
		}
		//4.tag
		String tags ="";
		for(int ei=0;ei<body.childNodeSize();ei++){
			Element child = body.child(ei);
			if(child.className().equals("tip")){
				if(child.text().contains("基本信息") ){
					Elements esTagLink = body.child(ei+1).select("a[href]");
					for(Element eTagLink:esTagLink){
						if(!eTagLink.text().contains("更多>>"))
							tags += eTagLink.text()+";";
					}
					
				}
			}
		}
		
		//5.education，work and other
		String education ="";
		String work ="";
		String other ="";
		for(int ei=0;ei<body.childNodeSize();ei++){
			Element child = body.child(ei);
			if(child.className().equals("tip")){
				if(child.text().contains("学习经历") ){
					education = body.child(ei+1).text();	
				}
			}
		}
		for(int ei=0;ei<body.childNodeSize();ei++){
			Element child = body.child(ei);
			if(child.className().equals("tip")){
				if(child.text().contains("工作经历") ){
					work = body.child(ei+1).text();	
				}
			}
		}
		for(int ei=0;ei<body.childNodeSize();ei++){
			Element child = body.child(ei);
			if(child.className().equals("tip")){
				if(child.text().contains("其他信息") ){
					other = body.child(ei+1).text();	
				}
			}
		}
//		System.out.println(uid);
//		System.out.println(nickname);
//		System.out.println(authentication);
//		System.out.println(gender);
//		System.out.println(location);
//		System.out.println(birthday);
//		System.out.println(slogan);
//		System.out.println(head);
//		System.out.println(tags);
//		System.out.println(education);
//		System.out.println(work);
//		System.out.println(other);
		uip.setUid(uid);
		uip.setNickname(nickname);
		uip.setAuthentication(authentication);
		uip.setGender(gender);
		uip.setLocation(location);
		uip.setBirthday(birthday);
		uip.setSlogan(slogan);
		uip.setImgSrc(head);
		uip.setEducation(education);
		uip.setCareer(work);
		uip.setTag(tags);
		uip.setOther(other);
		System.out.println("解析到用户信息："+uip.toString());
		//System.exit(0);
		return uip;
	}
	private void storePojo(UserInfoPojo uip) throws IOException{
		if(uip !=null){
			FileUtil fu = new FileUtil();
			if(uip.getUid() !=null && uip.getUid().hashCode() != "".hashCode()){
				fu.writeFile(uip.toJson(),"./weiboUser/"+uip.getUid()+"/basicInfo.json" );
				System.out.println("储存用户信息成功");
			}else{
				System.err.println("用户ID为空，舍弃其用户信息");
			}
		}else{
			System.err.println("储存用户信息失败");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseUserInfoPage p = new parseUserInfoPage();
		p.parsePageAndStore();
	}

}
