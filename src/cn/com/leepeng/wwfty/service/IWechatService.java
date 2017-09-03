/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service;

import java.util.List;

import cn.com.leepeng.wwfty.exception.WechatException;
import cn.com.leepeng.wwfty.schema.wechatsechma.ArticlesData;
import cn.com.leepeng.wwfty.schema.wechatsechma.CustomServiceData;
import net.sf.json.JSONObject;

/**
 * 推送帖子到微博，微信，Facebook，Twitter YouTube服务基类
 * 
 * @author LEEPENG
 * @since 1.0
 * @version 1.0
 * @date 27th Aug,2017
 * 
 */
public interface IWechatService {

	/**
	 * 
	 * 发送消息-客服信息
	 * 
	 * 当用户和公众号产生特定动作的交互时（具体动作列表请见下方说明），微信将会把消息数据推送给开发者，开发者可以在一段时间内（目前修改为48小时）调用客服接口，通过POST一个JSON数据包来发送消息给普通用户。此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
	 * 目前允许的动作列表如下（公众平台会根据运营情况更新该列表，不同动作触发后，允许的客服接口下发消息条数不同，下发条数达到上限后，会遇到错误返回码，具体请见返回码说明页）：
	 */
	public JSONObject addCustomServiceAccount(CustomServiceData customServiceData) throws WechatException;

	/**
	 * 
	 * @param articlesDatas
	 * @param appID
	 * @param appSecret
	 * @return
	 * @throws WechatException
	 */
	public JSONObject sendGroupMessage(List<ArticlesData> articlesDatas, String appID, String appSecret)
			throws WechatException;

	/**
	 * 新增永久素材
	 * 
	 * 对于常用的素材，开发者可通过本接口上传到微信服务器，永久使用。新增的永久素材也可以在公众平台官网素材管理模块中查询管理。 请注意：
	 * 1、最近更新：永久图片素材新增后，将带有URL返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。
	 * 2、公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为5000，其他类型为1000。 3、素材的格式大小等要求与公众平台官网一致：
	 * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
	 * 语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式 视频（video）：10MB，支持MP4格式
	 * 缩略图（thumb）：64KB，支持JPG格式
	 * 4、图文消息的具体内容中，微信后台将过滤外部的图片链接，图片url需通过"上传图文消息内的图片获取URL"接口上传图片获取。
	 * 5、"上传图文消息内的图片获取URL"接口所上传的图片，不占用公众号的素材库中图片数量的5000个的限制，图片仅支持jpg/png格式，大小必须在1MB以下。
	 * 6、图文消息支持正文中插入自己帐号和其他公众号已群发文章链接的能力。
	 * 
	 * @return
	 */
	public JSONObject addPermanentTextMaterial(List<ArticlesData> articlesDatas,String token);
}
