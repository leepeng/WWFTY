package cn.com.leepeng.wwfty.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.com.leepeng.wwfty.exception.WechatException;
import cn.com.leepeng.wwfty.schema.wechatsechma.ArticlesData;
import cn.com.leepeng.wwfty.schema.wechatsechma.CustomServiceData;
import cn.com.leepeng.wwfty.schema.wechatsechma.UploadMaterialResult;
import cn.com.leepeng.wwfty.schema.wechatsechma.UploadMaterialType;
import cn.com.leepeng.wwfty.service.IWechatService;
import cn.com.leepeng.wwfty.util.AnnotationAnalysis;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;
import cn.com.leepeng.wwfty.util.ConfigurationPropertiesUtil;
import cn.com.leepeng.wwfty.util.WechatTokenUtil;
import cn.com.leepeng.wwfty.util.WechatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WechatServiceImpl implements IWechatService {
	public static final String MEDIA = "media";

	@Override
	public void publish() {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject addCustomServiceAccount(CustomServiceData customServiceData) {
		String addUrl = ConfigurationPropertiesUtil.getConfigProperties("Wechat.CustomService.AddCustomeAccountUrl");
		Map<String, Object> requestParametersMap = new AnnotationAnalysis<CustomServiceData>()
				.getRequestParametersMap(CustomServiceData.class, customServiceData);
		String result = CommonHttpProtocolRequestUtil.requestWithPost(addUrl, requestParametersMap);
		return JSONObject.fromString(result);
	}

	// public static void main(String[] args) throws WechatException {
	// CustomServiceData customServiceData = new CustomServiceData();
	// customServiceData.setAccessToken("Vf-jalaAFexYf41Z2a2S5qLUsRugKNKOMnFgpcvW2Vzx4rxnw5ucIo3UWlvvEJA2hg7fxNUOjlQw6bYgrsK_MkzDS4Uat5ugEIEIwVAwqG8JEXjAIACXQ");
	// customServiceData.setPassword("1234");
	// JSONObject addCustomServiceAccount = new
	// WechatServiceImpl().addCustomServiceAccount(customServiceData);
	// List<ArticlesData> customServiceData = new ArrayList<>();
	// ArticlesData articlesData = new ArticlesData();
	// articlesData.setAuthor("xxxx");
	// customServiceData.add(articlesData);
	// JSONObject addCustomServiceAccount = new
	// WechatServiceImpl().sendGroupMessage(customServiceData,"wxdd404791419bd48d","ececc64f0769034b2c2a8422343b11d1");
	// JSONObject addPermanentTextMaterial = new
	// WechatServiceImpl().addPermanentTextMaterial(customServiceData,
	// WechatTokenUtil.getAccessToken("wxdd404791419bd48d",
	// "ececc64f0769034b2c2a8422343b11d1"));
	// System.out.println(addPermanentTextMaterial);
	// String accessToken = WechatTokenUtil.getAccessToken("wxb9b37c5e268617f5",
	// "5d0fda82e91a0f346511dcc0e60b23d3");
	// System.out.println(accessToken);
	// }

	@Override
	public JSONObject sendGroupMessage(List<ArticlesData> articlesDatas, String appID, String appSecret)
			throws WechatException {
		String url = ConfigurationPropertiesUtil.getConfigProperties("Wechat.SendGroup.UploadNewsUrl");
		JSONObject finalJson = new JSONObject();

		List<JSONObject> jsonObjects = new LinkedList<JSONObject>();
		for (ArticlesData articlesData : articlesDatas) {
			JSONObject object = JSONObject.fromBean(articlesData);
			jsonObjects.add(object);
		}
		Map<String, Object> requestParametersMap = new HashMap<String, Object>();
		// finalJson.put("articles", JSONArray.fromCollection(jsonObjects));
		requestParametersMap.put("article", finalJson);
		requestParametersMap.put("access_token", WechatTokenUtil.getAccessToken(appID, appSecret));
		String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(url, requestParametersMap);
		return JSONObject.fromString(requestWithPost);
	}

	@Override
	public JSONObject addPermanentTextMaterial(List<ArticlesData> articlesDatas, String token) {
		String addGraphicMaterialUrl = ConfigurationPropertiesUtil
				.getConfigProperties("Wechat.SourceMaterial.AddGraphicMaterial");
		Map<String, Object> map = new HashMap<>();
		JSONObject params = new JSONObject();
		List<JSONObject> jsonObjects = new ArrayList<>();
		for (ArticlesData articlesData : articlesDatas) {
			Map<String, Object> requestParametersMap = new AnnotationAnalysis<ArticlesData>()
					.getRequestParametersMap(ArticlesData.class, articlesData);
			System.out.println(requestParametersMap);
		}
		map.put("articles", jsonObjects.toString());
		map.put("access_token", token);
		System.out.println(params);
		String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(addGraphicMaterialUrl, map);
		System.out.println(requestWithPost);
		return JSONObject.fromString(requestWithPost);
	}

	@Override
	public UploadMaterialResult addOtherMaterial(String localFilePath, Map<String, String> params, String type,
			String token) {
		String addOtherMaterialUrl = null;
		UploadMaterialResult materialResult = null;
		try {
			addOtherMaterialUrl = ConfigurationPropertiesUtil
					.getConfigProperties("Wechat.SourceMaterial.AddOtherMaterial");
			addOtherMaterialUrl = addOtherMaterialUrl + "?access_token=" + token + "&type=" + type;
			String postFile = CommonHttpProtocolRequestUtil.postFile(addOtherMaterialUrl, MEDIA, localFilePath, params);
			JSONObject jsonObject = JSONObject.fromString(postFile);
			materialResult = (UploadMaterialResult) JSONObject.toBean(jsonObject, UploadMaterialResult.class);
			WechatUtil.checkHasException(materialResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return materialResult;
	}

	public static void main(String[] args) {
		WechatServiceImpl impl = new WechatServiceImpl();
		String accessToken = WechatTokenUtil.getAccessToken("wxb9b37c5e268617f5", "5d0fda82e91a0f346511dcc0e60b23d3");
		 UploadMaterialResult addOtherMaterial =
//		 impl.addOtherMaterial("C:\\Users\\Mr.Lee\\Desktop\\music2.mp3", null,UploadMaterialType.VOICE, accessToken);
//		 impl.addOtherMaterial("C:\\Users\\Mr.Lee\\Desktop\\2.jpg", null,UploadMaterialType.IMAGE, accessToken);
		 impl.addOtherMaterial("http://blog.leepeng.com.cn/wp-content/uploads/2017/09/24493956_1376530423419.jpg", null,UploadMaterialType.IMAGE, accessToken);
		 // System.out.println(addOtherMaterial.getMessage());
		 System.out.println("====" + addOtherMaterial.getErrcode());
		 System.out.println("====" + addOtherMaterial.getErrmsg());
		 System.out.println("====" + addOtherMaterial.getMedia_id());
		 System.out.println("====" + addOtherMaterial.getUrl());
//		List<ArticlesData> allArticlesData = impl.getAllArticlesData("mAB1BDp5w0L1QXr_Sh7OTYn-pdDZc7YWXAezHaMg4N8",
//				accessToken);
//		List<ArticlesData> allArticlesData = impl.getAllArticlesData(addOtherMaterial.getMedia_id(),
//				accessToken);
//		for (ArticlesData articlesData : allArticlesData) {
//			System.out.println(articlesData.toString());
//		}
	}

	@Override
	public List<ArticlesData> getAllArticlesData(String mediaId, String token) {
		List<ArticlesData> articlesDatas = null;
		String permanentMaterialURL = null;
		try {
			articlesDatas = new LinkedList<ArticlesData>();
			permanentMaterialURL = ConfigurationPropertiesUtil
					.getConfigProperties("Wechat.SourceMaterial.GetPermanentMaterial");
			permanentMaterialURL += ("?access_token=" + token);
			Map<String, Object> params = new HashMap<String, Object>();
			String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(permanentMaterialURL, params);
			JSONObject result = JSONObject.fromString(requestWithPost);
			UploadMaterialResult materialResult = (UploadMaterialResult) JSONObject.toBean(result,
					UploadMaterialResult.class);
			WechatUtil.checkHasException(materialResult);
			JSONArray jsonArray = result.getJSONArray("news_item");
			int i = 0;
			for (; i < jsonArray.length();) {
				JSONObject articleObject = jsonArray.getJSONObject(i);
				ArticlesData articlesData = new ArticlesData();
				articlesData.setTitle(articleObject.getString("title"));
				articlesData.setContent(articleObject.getString("content"));
				articlesData.setContentSourceUrl(articleObject.getString("content_source_url"));
				articlesData.setDigest(articleObject.getString("digest"));
				articlesData.setShowCoverPic(articleObject.getString("show_cover_pic"));
				articlesData.setThumbMediaID(articleObject.getString("thumb_media_id"));
				articlesData.setAuthor(articleObject.getString("author"));
				articlesData.setUrl(articleObject.getString("url"));
				articlesDatas.add(articlesData);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlesDatas;
	}

}
