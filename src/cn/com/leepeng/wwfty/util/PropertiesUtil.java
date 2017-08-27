package cn.com.leepeng.wwfty.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties properties = new Properties();
	static{
		try {
			InputStream stream = new BufferedInputStream (new FileInputStream("bin/FBConfig.properties"));
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperties(String key){
		return properties.getProperty(key);
	}
	
	public static void main(String[] args) {
		String properties2 = getProperties("Facebook.Video.PublishVideoUrl");
		System.out.println(properties2);
	}
}
