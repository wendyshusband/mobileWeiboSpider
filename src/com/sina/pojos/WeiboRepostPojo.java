package com.sina.pojos;

public class WeiboRepostPojo {

	private String id; //被转发的用户ID
	private String wid;
	private String uid;  //转发者id
	private String time;//转发时间
	private String source; //转发来源
	private String comment;//转发时的评论
	
	public WeiboRepostPojo(){
		
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
		str+="time="+time+";";
		str+="source="+source+";";
		str+="content="+comment +";";
		return str;
	}
	
	public String toJson()
	{
		String str = "{";
		str+="'id':'"+id +"',";
		str+="'wid':'"+wid +"',";
		str+="'uid':'"+uid +"',";
		str+="'time':'"+time+"',";
		str+="'source':'"+source+"',";
		str+="'content':'"+comment +"'}";
		return str;
	}
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
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
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment=comment;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
