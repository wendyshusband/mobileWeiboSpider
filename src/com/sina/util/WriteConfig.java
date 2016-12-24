package com.sina.util;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class WriteConfig {
	private Properties config = null;

	public void setValue(String key, String value, String configFilePath) throws IOException {
		//判断是否存在，不存在文件则创建文件
		File file = new File(configFilePath);
		if(file.exists()==false){
			System.out.println("config file <"+configFilePath+"> not exist! Auto create this config file!");
			file.createNewFile();
		}

		config = new Properties();
		// FileOutputStream fos = new
		// FileOutputStream("./resources/"+configFilePath, true);//true表示追加打开
		FileOutputStream fos = new FileOutputStream("./resources/" + configFilePath, false);// false:直接修改
																							// 不追加打开
		config.setProperty(key, value);
		config.store(fos, null);
		fos.close();

	}

	public static void main(String args[]) throws IOException {
		WriteConfig wc = new WriteConfig();
		wc.setValue("cookie", "32", "heheda.properties");
	}
}
