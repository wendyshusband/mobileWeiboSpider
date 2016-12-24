package com.sina.pojos;

public class proxyPojo {
    private String ip;
    private String port;
    
    
    public void proxyPojo(){
    	ip="";
    	port="";
    }
    public  String toString(){
       return ip+" "+port;
    }
    
    public String  getIp(){
    	return ip;
    }
    
    public void setIp(String ip){
    	this.ip=ip;
    }
    
    public String getPort(){
    	return port;
    }
    
    
    public void setPort(String port){
    	this.port=port;
    }
    
    
}
