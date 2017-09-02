package cn.com.leepeng.wwfty.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigurationPropertiesUtil {
	private static Properties properties = new Properties();
	private static List<Properties> propertiesLists = new ArrayList<>();
	static{
		try {
			File file = new File("bin");
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				if(file2.getName().endsWith(".properties")){
					InputStream stream = new BufferedInputStream (new FileInputStream("bin"+File.separator+file2.getName()));
					properties.load(stream);
					propertiesLists.add(properties);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getConfigProperties(String key){
		for (Properties properties : propertiesLists) {
			if(StringUtils.isEmpty(properties.getProperty(key))){
				continue;
			}
		}
		return properties.getProperty(key);
	}
	
	public static void main(String[] args) {
		String properties2 = getConfigProperties("Facebook.Video.PublishVideoUrl");
		properties2 = getConfigProperties("Wechat.CustomService.AddCustomeAccountUrl");
		System.out.println(properties2);
	}
}
