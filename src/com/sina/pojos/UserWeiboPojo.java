package com.sina.pojos;

public class UserWeiboPojo {
      
	private String uid;
	private String wid;   //微博id
	private String content;//微博内容
	private String mediaSrc;//媒体如图片、视频等的地址
	private String time;//微博发表时间
	private String commentCount;//评论数
	private String repostCount;//转发数
	private String likeCount;//点赞数
	private boolean isOriginal; //是否为原创微博
	private String source; //微博来源
	
	//下面变量仅当isOriginal=false时存在
	private String owid;   //原微博id
	private String ouid;   //原微博id
	private String rcmt;	//转发评论
	
	
	//以下三个参数放这里是因为只能从微薄页面而非用户信息页面获取，这些要存进用户信息文件中
	private String followercount;
	private String friendcount;
	private String weibocount;
	
	
	public UserWeiboPojo(){
//		wid="";
//		url="";
//		content="";
//		mediaSrc="";
//		time="";
		commentCount="0";
		repostCount="0";
		likeCount="0";
		isOriginal=true;
//		owid="";
//		ouid="";
//		originalSrc="";
	}
	

	public String toString()
	{
		String str = "";
		str+="uid="+uid +";";
		str+="wid="+wid +";";
//		str+="url="+url+";";
		str+="time="+time +";";
		str+="repostCount="+repostCount  +";";
		str+="commentCount="+commentCount  +";";
		str+="likeCount="+likeCount  +";";
		str+="isOriginal="+isOriginal+";";
		str+="owid="+owid  +";";
		str+="ouid="+ouid  +";";
		str+="source="+source  +";";
		str+="content="+content+";";
		str+="mediaSrc="+mediaSrc +";";
		return str;
	}
	
	//微博数、粉丝数和关注数转换为json（因为跟上面的数据获取页面不同）
		public String toCountJson(){
			String str = "{";
			str+="'followercount':'"+followercount +"',";
			str+="'friendcount':'"+friendcount +"',";
			str+="'weibocount':'"+weibocount +"'}";
			return str;
		}

	public String toJson()
	{
		String str = "";
		str+="{";
		str+="'uid':'"+uid +"',";
		str+="'wid':'"+wid +"',";
//		str+="'url':'"+url+"',";
		str+="'time':'"+time +"',";
		str+="'repostCount':'"+repostCount  +"',";
		str+="'commentCount':'"+commentCount  +"',";
		str+="'likeCount':'"+likeCount  +"',";
		str+="'isOriginal':'"+isOriginal+"',";
		str+="'owid':'"+owid  +"',";
		str+="'ouid':'"+ouid  +"',";
		str+="'source':'"+source  +"',";
		str+="'content':'"+content+"',";
		str+="'mediaSrc':'"+mediaSrc +"'";
		str+="}";
		return str;
	}
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid=uid;
	}
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid=wid;
	}
	
//	public String getUrl() {
//		return url;
//	}
//	public void setUrl(String url) {
//		this.url=url;
//	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}
	
	public String getMediaSrc() {
		return mediaSrc;
	}
	public void setMediaSrc(String mediaSrc) {
		this.mediaSrc=mediaSrc;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time=time;
	}
	
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount=commentCount;
	}
	
	public String getRepostCount() {
		return repostCount;
	}
	public void setRepostCount(String repostCount) {
		this.repostCount=repostCount;
	}
	
	
	public String getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(String likeCount ) {
		this.likeCount=likeCount;
	}
	
	public boolean getIsOriginal(){
		return isOriginal;
	}
	public void setIsOriginal(boolean isOriginal){
	    this.isOriginal=isOriginal;
	}
	
	public String getOwid() {
		return owid;
	}
	public void setOwid(String owid) {
		this.owid=owid;
	}
	
	public String getOuid() {
		return ouid;
	}
	public void setOuid(String ouid) {
		this.ouid=ouid;
	}
	public String getRcmt() {
		return rcmt;
	}
	public void setRcmt(String rcmt) {
		this.rcmt = rcmt;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}
	public String getFollowercount() {
		return followercount;
	}


	public void setFollowercount(String followercount) {
		this.followercount = followercount;
	}


	public String getFriendcount() {
		return friendcount;
	}


	public void setFriendcount(String friendcount) {
		this.friendcount = friendcount;
	}


	public String getWeibocount() {
		return weibocount;
	}


	public void setWeibocount(String weibocount) {
		this.weibocount = weibocount;
	}


	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}

}
