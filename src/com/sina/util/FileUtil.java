package com.sina.util;

import java.io.*;
/**
 * 文件操作工具，主要是文件的读写
 * @author tzl
 *
 */
public class FileUtil {

	/**
	 * 读入文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String  readFile(String path) throws IOException{
//		File inf=new File(path);
//	    FileInputStream fis = new FileInputStream(inf);
//			byte[] b=new byte[1024];
//			StringBuffer sb=new StringBuffer();
////			ByteArrayOutputStream bos=new ByteArrayOutputStream();
//			int len;
//			while((len=fis.read(b))>=0){
////                bos.write(b,0,len);
//				sb.append(new String(b));
//				b=new byte[1024];
//     	  }
////			System.out.println(sb.toString());
////			System.out.println(bos.toString());
//			fis.close();
//			return sb.toString();
		File f=new File(path);
		FileReader fr=new FileReader(f);
		char c[]=new char[1];
		String s="";
		while(fr.read(c)!=-1){
	       for(int i=0;i<c.length;i++){
	    	   s+=c[i];
	       }
		}
//		System.out.println(s);
		fr.close();
		return s;
		
		/*这个方式读文件太low。。。。。。*/
	}
	/**
	 * 自动创建父目录，以覆盖的方式，保存content到文件中
	 * @param content
	 * @param path
	 * @throws IOException
	 */
	public static void writeFile(String content,String path) throws IOException{
		File outf=new File(path);
		if(!outf.exists()){  
			try {
				String temp = null;
				if ((temp = outf.getParent()) != null) {
					new File(temp).mkdirs(); //创建父目录
				}
				outf.createNewFile();//创建文件
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	    
	    FileOutputStream fos=new FileOutputStream(outf);
	    fos.write(content.getBytes());
	    fos.close();
	}
	
	/**
	 * 以追加的方式，保存content到文件中
	 * @param content
	 * @param path
	 * @throws IOException
	 */
	public void writeFileAppend(String content,String path) throws IOException{
		File outf=new File(path);
		if(!outf.exists()){
			try {
				String temp = null;
				if ((temp = outf.getParent()) != null) {
					new File(temp).mkdirs();
				}
				outf.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	    
	    FileOutputStream fos=new FileOutputStream(outf,true);
	    fos.write(content.getBytes());
	    fos.close();
	}
	
	/**
	 * 创建root_path的所在文件夹的所有路径
	 * @param root_path
	 * @return
	 */
	public static boolean createRootDir(String root_path) {
		File f = new File(root_path);
		if (f.exists() && f.isDirectory()) {
			return true;
		} else {
			try {
				if (f.mkdirs()) {
					return true;
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public static long length(String path){
		return new File(path).length();
	}
	
	public static void main(String[]args) throws IOException{
		FileUtil fu=new FileUtil();
//		String path="./output/user/12345/info";
//		String content="hello\t";
////		createRootDir(path);
//		fu.writeFile(content, path);
//		content="word \n";
//		fu.writeFileAppend(content, path);
		System.out.println(fu.readFile("./cookie"));
		
	}
}
