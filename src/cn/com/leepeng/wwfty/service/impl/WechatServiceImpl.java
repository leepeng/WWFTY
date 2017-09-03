package cn.com.leepeng.wwfty.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.com.leepeng.wwfty.exception.WechatException;
import cn.com.leepeng.wwfty.schema.wechatsechma.ArticlesData;
import cn.com.leepeng.wwfty.schema.wechatsechma.CustomServiceData;
import cn.com.leepeng.wwfty.service.IService;
import cn.com.leepeng.wwfty.service.IWechatService;
import cn.com.leepeng.wwfty.util.AnnotationAnalysis;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;
import cn.com.leepeng.wwfty.util.ConfigurationPropertiesUtil;
import cn.com.leepeng.wwfty.util.WechatTokenUtil;
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
	
	public static void main(String[] args) throws WechatException {
//		CustomServiceData customServiceData = new CustomServiceData();
//		customServiceData.setAccessToken("Vf-jalaAFexYf41Z2a2S5qLUsRugKNKOMnFgpcvW2Vzx4rxnw5ucIo3UWlvvEJA2hg7fxNUOjlQw6bYgrsK_MkzDS4Uat5ugEIEIwVAwqG8JEXjAIACXQ");
//		customServiceData.setPassword("1234");
//		JSONObject addCustomServiceAccount = new WechatServiceImpl().addCustomServiceAccount(customServiceData);
		List<ArticlesData> customServiceData = new ArrayList<>();
		ArticlesData articlesData = new ArticlesData();
		articlesData.setAuthor("xxxx");
		customServiceData.add(articlesData);
		JSONObject addCustomServiceAccount = new WechatServiceImpl().sendGroupMessage(customServiceData,"wxdd404791419bd48d","ececc64f0769034b2c2a8422343b11d1");
		System.out.println(addCustomServiceAccount);
	}


	@Override
	public JSONObject sendGroupMessage(List<ArticlesData> articlesDatas,String appID,String appSecret) throws WechatException {
		String url = ConfigurationPropertiesUtil.getConfigProperties("Wechat.SendGroup.UploadNewsUrl");
		JSONObject finalJson = new JSONObject();
		
		List<JSONObject> jsonObjects = new LinkedList<JSONObject>();
		for (ArticlesData articlesData : articlesDatas) {
			JSONObject object = JSONObject.fromBean(articlesData);
			jsonObjects.add(object);
		}
		Map<String, Object> requestParametersMap = new HashMap<String, Object>();
		//finalJson.put("articles", JSONArray.fromCollection(jsonObjects));
		requestParametersMap.put("article", finalJson);
		requestParametersMap.put("access_token", WechatTokenUtil.getAccessToken(appID, appSecret));
		String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(url, requestParametersMap);
		return JSONObject.fromString(requestWithPost);
	}


	@Override
	public JSONObject addPermanentTextMaterial(List<ArticlesData> articlesDatas, String token) {
		
		return null;
	}

}
