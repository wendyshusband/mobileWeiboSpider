package com.sina.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
/**
 * 数据转换
 * @author tzl
 *
 */
public class Converter {
/**
 * 清洗字符串为标准html
 * @param html
 * @return
 */
	public static String standardHtml(String html){
		return html.replaceAll("\\\\n","")
                       .replaceAll("\\\\t", "")
                       .replaceAll("\\\\r", "")
                       .replaceAll("\\\\", "")
		               .replaceAll("<!-.*?->", "");
		
	}
	
	/**
	 * 通过10进制转62进制方式加密wid，每四个字符为一组，作为一个62进制数。
	 * 主要用于通过wid得到该微博url
	 * @param wid
	 * @return
	 */
	public static String encodeWid(String wid){
	    String result="";
	    String tmp="";
	    int codeLength=4;
	    int max=14776335; //4为62进制所能表示的最大数
	    int end=wid.length();
	    int begin=Math.max(0,end-7);
	    while(end>0){
//	        System.out.println(begin+"--"+end);
	    	tmp=wid.substring(begin, end);
	       if(Integer.parseInt(tmp)>max){
	           begin++;
	           tmp=wid.substring(begin, end);
	    	  }
	       	       
//	       System.out.println(tmp);
	       String code=decimalTo62(Integer.parseInt(tmp));
//	       System.out.println("code length:"+code.length());
	       if(code.length()<codeLength&&begin!=0){ //不是最后一组且长度小于4，在前面补0
	    	   StringBuilder sb=new StringBuilder(code);
	    	   for(int i=codeLength-code.length();i>0;i--){
	    		   sb.insert(0,"0");
	    	   }
	    	   code=sb.toString();
	       }
//	       System.out.println(code);
	       result=code+result;
	       end=begin;
	       begin=Math.max(0,end-7);
	    }
		return result;
	}

	
	private static char[] charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();    
    
    /**
     * 查找set中c的下标
     * @param set
     * @param c
     * @return
     */
	public static int indexOf(char[]set,char c){
		int index=-1;
		for(int i=0;i<set.length;i++){
			if(set[i]==c){
				index=i;
				break;
			}
		}
       return index;
	}
	
	/** 
     * 将10进制转化为62进制  
     * @param number  
     * @return 
     */  
    public static String decimalTo62(int number){  
         int rest=number; 
         Stack<Character> stack=new Stack<Character>();  
         StringBuilder result=new StringBuilder();  
         while(rest!=0){  
             stack.add(charSet[new Long((rest-(rest/62)*62)).intValue()]);  
             rest=rest/62;  
         }  
         for(;!stack.isEmpty();){  
             result.append(stack.pop());  
         }  
         
         return result.toString();
  
    }  
      /**
       * 将62进制转换10进制
       * @param ident62
       * @return
       */
    private static String uncodeDecimalTo62( String ident62 ) {  
        
        char[]set=ident62.toCharArray();
        double sum=0;
        int m=0;
    	for(int i=set.length-1;i>=0;i--){
         int index=indexOf(charSet,set[i]);
         sum+=index*Math.pow(62,m++);
        }
    	return String.valueOf((int)sum);
    }
    
    /**
     * string型转map型
     * @param str
     * @return
     */
    public static Map<String,String> StringToMap(String str){
    	Map<String,String> map=new HashMap<String,String>();
    	String[]sub=str.split("&");
    	for(int i=0;i<sub.length;i++){
    		String[] keyValue=sub[i].split("=");
    		if(keyValue.length>=2)
    		   map.put(keyValue[0], keyValue[1]);
    	}
    	return map;
    }
    
/**
 * 将诸如刚刚、5秒前、10分钟前等格式的 日期转换成yyyy-MM-dd HH:mm格式
 * @param date 日期格式
 * @param rnd 对应当前的时间
 * @return 标准yyyy-MM-dd HH:mm格式时间
 */
	public static String  dateFormat(String date,long rnd){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date result=new Date();
		
		int year=new Date().getYear();
		if(date.contains("刚刚")){
			result.setTime(rnd);
			return sdf.format(result);
		}
		else if(date.contains("秒前")) {
			int s=Integer.valueOf(parseUtil.getFirstNumber(date));
			rnd=rnd-(s*1000);
			result.setTime(rnd);
			return sdf.format(result);
		}
		else if(date.contains("分钟前")){
			int m=Integer.valueOf(parseUtil.getFirstNumber(date));
			rnd=rnd-(m*1000*60);
			result.setTime(rnd);
			return sdf.format(result);
		}
		else if(date.contains("今天")){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date now=new Date();
			now.setTime(rnd);
			String today=sdf1.format(now);
			date=date.replaceFirst("今天",today);
			return date;
			
		}
		else if(date.contains("月")){
			SimpleDateFormat sdf1=new SimpleDateFormat("MM月dd日 HH:mm");
			try {
				result=sdf1.parse(date);
				result.setYear(year);
				return sdf.format(result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
	
	
    public static void main(String[]args){
    	
    	long start=System.currentTimeMillis();   //获取开始时间   


    	String date="刚刚";
    	String date1="50秒前";
		String date2="10分钟前";
		String date3="今天 08:09";
		String date4="2月3日 18:30";
		String date5="2014-12-3 12:23";
		
		long rnd=new Date().getTime();
		System.out.println(date+"---->"+dateFormat(date, rnd));
		System.out.println(date1+"---->"+dateFormat(date1, rnd));
		System.out.println(date2+"---->"+dateFormat(date2, rnd));
		System.out.println(date3+"---->"+dateFormat(date3, rnd));
		System.out.println(date4+"---->"+dateFormat(date4, rnd));
		System.out.println(date5+"---->"+dateFormat(date4, rnd));

//    	String wid="3801798265700546";
//    	String code="C0KMqnUYi";
////    	char[]s="1B".toCharArray();
//    	System.out.println(encodeWid("3698458718017732"));
    	long end=System.currentTimeMillis(); //获取结束时间   

    	System.out.println("程序运行时间： "+(end-start)+"ms");
    }
}
