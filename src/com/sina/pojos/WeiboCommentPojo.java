package com.sina.pojos;

public class WeiboCommentPojo {
     
	private String id;//被评论者的用户ID
	private String wid;//微博ID
	private String uid;  //评论者id
	private String cid;//评论id
	private String time;//评论时间
	private String content;//评论内容
	private String source;//微博来源
	public WeiboCommentPojo(){
		
//		uid="";
//		time="";
//		content="";
	}
	public String toString()
	{
		String str = "";
		str+="id="+id +";";
		str+="wid="+wid +";";
		str+="uid="+uid +";";
		str+="cid="+cid +";";
		str+="time="+time+";";
		str+="source="+source+";";
		str+="content="+content +";";
		return str;
	}
	
	public String toJson()
	{
		String str = "{";
		str+="'id':'"+id +"',";
		str+="'wid':'"+wid +"',";
		str+="'uid':'"+uid +"',";
		str+="'cid':'"+cid +"',";
		str+="'time':'"+time+"',";
		str+="'source':'"+source+"',";
		str+="'content':'"+content +"'}";
		return str;
	}
	
	public String getId() {
		return id;
}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time=time;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	

}
