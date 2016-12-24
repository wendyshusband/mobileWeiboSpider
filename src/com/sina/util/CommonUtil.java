package com.sina.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Richard
 * @project mobileWeiboSpider
 * @package com.sina.util
 * 一些公用方法
 */
public class CommonUtil {
	
	//去除空值
    public static List removeEmptyList(List list) { 
    	//System.out.println("remove="+list);
    	//System.exit(0);
        List list1 = new ArrayList();  
        if(list==null||list.size()<=0)  
            return null;  
        //循环第一层  
        for(int i=0;i<list.size();i++) {  
            //进入每一个list  
            Object listi = (Object) list.get(i);  
            if(listi!=null)  
                list1.add(listi);  
            //System.out.println(list1.size());  
        }  
        return list1;  
    }
    
    //去除重复值
    public static List removeSameList(List list)
    {
        for (int i = 0; i < list.size() - 1; i++){
            for (int j = i + 1; j < list.size(); j++){
                if (list.get(i).equals(list.get(j))){
                    list.remove(j);
                    j--;
                }
            }
        }
        return list;
    }
    
}
