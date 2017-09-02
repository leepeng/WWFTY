/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.service;

import cn.com.leepeng.wwfty.exception.WechatException;
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
	
	/*
	 * 发送消息-客服信息
	 * 
	 * 当用户和公众号产生特定动作的交互时（具体动作列表请见下方说明），微信将会把消息数据推送给开发者，开发者可以在一段时间内（目前修改为48小时）调用客服接口，通过POST一个JSON数据包来发送消息给普通用户。此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
目前允许的动作列表如下（公众平台会根据运营情况更新该列表，不同动作触发后，允许的客服接口下发消息条数不同，下发条数达到上限后，会遇到错误返回码，具体请见返回码说明页）：
	 */
	public JSONObject addCustomServiceAccount(CustomServiceData customServiceData) throws WechatException;
	public JSONObject  sendGroupMessage(CustomServiceData customServiceData) throws WechatException;
}
