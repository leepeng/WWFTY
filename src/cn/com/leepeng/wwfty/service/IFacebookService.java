/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service;

import cn.com.leepeng.wwfty.schema.facebook.FacebookData;

/**
 * 推送帖子到微博，微信，Facebook，Twitter YouTube服务基类
 * 
 * @author LEEPENG
 * @since 1.0
 * @version 1.0
 * @date 27th Aug,2017
 * 
 */
public interface IFacebookService extends IService{
	
	/**
	 * Represents an individual video on Facebook.
	 * 
	 * Permissions:
	 * 
	 * Any valid access token can read videos on a public Page. A page access
	 * token can read all videos posted to or posted by that Page. A user access
	 * token can read any video your application created on behalf of that user.
	 * A user's videos can be read if the owner has granted the user_videosor
	 * user_posts permission. A user access token may read a video that user is
	 * tagged in if they user granted the user_videosor user_posts permission.
	 * However, in some cases the video's owner's privacy settings may not allow
	 * your application to access it.
	 * 
	 * 如果您上传具有多部分HTTP请求的视频或通过向视频提供URL，则视频的大小不能超过1GB，持续时间为20分钟。
	 * 可录像视频上传支持上传长达1.75GB和45分钟的视频。
	 */
	public void publishVideo(FacebookData facebookData);
}
