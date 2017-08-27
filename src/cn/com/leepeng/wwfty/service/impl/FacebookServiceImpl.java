/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.com.leepeng.wwfty.schema.FacebookData;
import cn.com.leepeng.wwfty.schema.FacebookIndividualVideo;
import cn.com.leepeng.wwfty.service.IFacebookService;
import cn.com.leepeng.wwfty.service.IService;
import cn.com.leepeng.wwfty.util.HttpUtil;
import cn.com.leepeng.wwfty.util.PropertiesUtil;
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
public class FacebookServiceImpl implements IService, IFacebookService {

	
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
		String accessUrl = PropertiesUtil.getProperties("Facebook.Video.PublishVideoUrl");
		String graphApiVersion = PropertiesUtil.getProperties("Facebook.GraphAPI.Version");
		accessUrl = accessUrl + "/" + graphApiVersion + "/" + facebookData.getId()+ "/videos";
		//开始阶段。通过启动会话开始可续传的上传。要发出 start 请求并创建视频上传会话，
		//向 /{page_id || user_id || event_id || group_id}/videos 连线发出带有参数 
		Map<String, Object> params = new HashMap<>();
		params.put("upload_phase", facebookIndividualVideo.getUploadphase());
		params.put("file_size", facebookIndividualVideo.getFileSize());
		params.put("access_token", facebookData.getAccessToken());
		String doPostSSL = HttpUtil.doPostSSL(accessUrl, params);
		JSONObject result = JSONObject.fromString(doPostSSL);
		//获取上传会话的编号
		//int int1 = result.getInt("upload_session_id");
		//int int1 = result.getInt("video_id");
		//int int1 = result.getInt("start_offset");
		//int int1 = result.getInt("end_offset");
		
	}

}
