package cn.com.leepeng.wwfty.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.com.leepeng.wwfty.annotation.wechat.CustomServiceParam;
import cn.com.leepeng.wwfty.exception.WechatException;
import cn.com.leepeng.wwfty.schema.FacebookData;
import cn.com.leepeng.wwfty.schema.FacebookIndividualVideo;
import cn.com.leepeng.wwfty.schema.wechatsechma.CustomServiceData;
import cn.com.leepeng.wwfty.service.IService;
import cn.com.leepeng.wwfty.service.IWechatService;
import cn.com.leepeng.wwfty.util.AnnotationAnalysis;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;
import cn.com.leepeng.wwfty.util.ConfigurationPropertiesUtil;
import net.sf.json.JSONObject;

public class WechatServiceImpl implements IService,IWechatService {


	@Override
	public void publish() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public JSONObject addCustomServiceAccount(CustomServiceData customServiceData) {
		String addUrl = ConfigurationPropertiesUtil.getConfigProperties("Wechat.CustomService.AddCustomeAccountUrl");
		Map<String, Object> requestParametersMap = new AnnotationAnalysis<CustomServiceData>().getRequestParametersMap(CustomServiceData.class,customServiceData);
		String result = CommonHttpProtocolRequestUtil.requestWithPost(addUrl, requestParametersMap);
		return JSONObject.fromString(result);
	}
	
	public static void main(String[] args) {
		CustomServiceData customServiceData = new CustomServiceData();
		customServiceData.setAccessToken("Vf-jalaAFexYf41Z2a2S5qLUsRugKNKOMnFgpcvW2Vzx4rxnw5ucIo3UWlvvEJA2hg7fxNUOjlQw6bYgrsK_MkzDS4Uat5ugEIEIwVAwqG8JEXjAIACXQ");
		customServiceData.setPassword("1234");
		JSONObject addCustomServiceAccount = new WechatServiceImpl().addCustomServiceAccount(customServiceData);
		System.out.println(addCustomServiceAccount);
	}


	@Override
	public JSONObject sendGroupMessage(CustomServiceData customServiceData) throws WechatException {
		// TODO Auto-generated method stub
		return null;
	}

}
