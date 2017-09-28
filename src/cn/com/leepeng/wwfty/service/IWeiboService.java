/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service;

import java.util.List;

import cn.com.leepeng.wwfty.schema.weibo.MediaData;

/**
 * 微博服务基类
 * 27th Sep,2017
 * @author LEEPENG
 * @since 1.0
 * @version 1.0
 */
public interface IWeiboService extends IService {
	
	/**
	 * 上传多张图片
	 * @param imagePath 待上传的本地图片路径集合
	 * @param accessToken 调用口令
	 */
	public void uploadMultipleImages(List<String> imagePath,String accessToken);
	
	/**
	 * 
	 */
	public void uploadMultimedia(MediaData mediaData);
}
