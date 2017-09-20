package cn.com.leepeng.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
//		String token = "EAAbjjhlOHqMBAMBuMDrhQBOnccZAjpYPmVlCzV0v4DruET7aaxHr6EL2U6zMEljvPMm6I91Jhk3d59JYo3si0pFZCvCKHzxNzJ7MO8el8Q3QW84kQhwSdltx4Rb1uquPIKQZB526uf4TjNqI2kPtP4kthNraVngUYP459bQzZCLv6ELCNvda";
//		String str = "https://graph-video.facebook.com/v2.8/1854162644912074/videos";
//		
//		
//		IFacebookService facebookService = new FacebookServiceImpl();
//		FacebookIndividualVideo individualVideo = new FacebookIndividualVideo();
//		individualVideo.setUploadphase("start");
//		individualVideo.setFileSize(122344);
//		
//		FacebookData facebookData = new FacebookData();
//		facebookData.setFacebookIndividualVideo(individualVideo);
//		facebookData.setId("1854162644912074");
//		facebookData.setAccessToken(token);
//		
//		facebookService.publishVideo(facebookData);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("D:"+File.separator+"demo.txt")));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while((line = bufferedReader.readLine())!=null){
			buffer.append(line);
		}
		
		String token = "";
		System.out.println(buffer.toString());
		System.out.println(buffer.toString().replace("&quot;", "\""));
	}
}
