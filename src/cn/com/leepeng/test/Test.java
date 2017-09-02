package cn.com.leepeng.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.leepeng.wwfty.enums.UploadPhaseType;
import cn.com.leepeng.wwfty.schema.FacebookData;
import cn.com.leepeng.wwfty.schema.FacebookIndividualVideo;
import cn.com.leepeng.wwfty.service.IFacebookService;
import cn.com.leepeng.wwfty.service.impl.FacebookServiceImpl;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;

public class Test {
	public static void main(String[] args) {
		String token = "EAAbjjhlOHqMBAMBuMDrhQBOnccZAjpYPmVlCzV0v4DruET7aaxHr6EL2U6zMEljvPMm6I91Jhk3d59JYo3si0pFZCvCKHzxNzJ7MO8el8Q3QW84kQhwSdltx4Rb1uquPIKQZB526uf4TjNqI2kPtP4kthNraVngUYP459bQzZCLv6ELCNvda";
		String str = "https://graph-video.facebook.com/v2.8/1854162644912074/videos";
		
		
		IFacebookService facebookService = new FacebookServiceImpl();
		FacebookIndividualVideo individualVideo = new FacebookIndividualVideo();
		individualVideo.setUploadphase("start");
		individualVideo.setFileSize(122344);
		
		FacebookData facebookData = new FacebookData();
		facebookData.setFacebookIndividualVideo(individualVideo);
		facebookData.setId("1854162644912074");
		facebookData.setAccessToken(token);
		
		facebookService.publishVideo(facebookData);
	}
}
