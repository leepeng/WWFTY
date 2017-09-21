/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.com.leepeng.wwfty.schema.facebook.FacebookData;
import cn.com.leepeng.wwfty.schema.facebook.FacebookIndividualVideo;
import cn.com.leepeng.wwfty.service.IFacebookService;
import cn.com.leepeng.wwfty.util.AnnotationAnalysis;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;
import cn.com.leepeng.wwfty.util.ConfigurationPropertiesUtil;
import net.sf.json.JSONObject;

/**
 * 推送帖子Facebook服务基类
 * 
 * @author LEEPENG
 * @since 1.0
 * @version 1.0
 * @date 27th Aug,2017
 * 
 */
public class FacebookServiceImpl implements IFacebookService {

	
	/**
	 * 
	 * /{user-id}/videos 
	 * /{event-id}/videos 
	 * /{page-id}/videos 
	 * /{group-id}/videos
	 * 
	 */

	@Override
	public void publish() {
		
	}


	@Override
	public void publishVideo(FacebookData facebookData) {
		FacebookIndividualVideo facebookIndividualVideo = facebookData.getFacebookIndividualVideo();
		String accessUrl = ConfigurationPropertiesUtil.getConfigProperties("Facebook.Video.PublishVideoUrl");
		String graphApiVersion = ConfigurationPropertiesUtil.getConfigProperties("Facebook.GraphAPI.Version");
		accessUrl = accessUrl + "/" + graphApiVersion + "/" + facebookData.getId()+ "/videos";
		//开始阶段。通过启动会话开始可续传的上传。要发出 start 请求并创建视频上传会话，
		//向 /{page_id || user_id || event_id || group_id}/videos 连线发出带有参数 
		Map<String, Object> requestParametersMap = new AnnotationAnalysis<FacebookData>().getRequestParametersMap(FacebookData.class, facebookData);
		
		//String doPostSSL = CommonHttpProtocolRequestUtil.requestWithPost(accessUrl, params);
		//JSONObject result = JSONObject.fromString(doPostSSL);
		//获取上传会话的编号
		//int int1 = result.getInt("upload_session_id");
		//int int1 = result.getInt("video_id");
		//int int1 = result.getInt("start_offset");
		//int int1 = result.getInt("end_offset");
		
	}

}
