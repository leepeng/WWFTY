package cn.com.leepeng.wwfty.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import cn.com.leepeng.wwfty.schema.weibo.MediaData;
import cn.com.leepeng.wwfty.schema.weibo.ToutiaoArticleData;
import cn.com.leepeng.wwfty.service.IWeiboService;
import cn.com.leepeng.wwfty.util.AnnotationAnalysis;
import cn.com.leepeng.wwfty.util.CommonHttpProtocolRequestUtil;
import cn.com.leepeng.wwfty.util.ConfigurationPropertiesUtil;
import cn.com.leepeng.wwfty.util.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiboServiceImpl implements IWeiboService {

	@Override
	public void publish() {
		// TODO Auto-generated method stub
		
	}

	public static List<String> UploadPic(List<String> picPath,String accessToken){
		List<String>list = new ArrayList<>();
		//https://api.weibo.com/2/statuses/upload_pic.json
		String url = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.upload.pic");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", accessToken);
		for (String p : picPath) {
			params.put("pic", p);
			String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(url, params);
			JSONObject fromString = JSONObject.fromString(requestWithPost);
			System.out.println("===UploadPic=="+fromString);
			String pic_id = fromString.getString("pic_id");
			System.out.println("========pic_id:"+pic_id);
			list.add(pic_id);
		}
		return list;
	}
	public static List<String> UploadPicMult(List<String> picPath,String accessToken) throws InterruptedException{
		List<String>list = new ArrayList<>();
		//--https://api.weibo.com/2/statuses/upload_pic.json;
		String url = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.upload.pic");
		Map<String, String> params = new HashMap<>();
		//params.put("access_token", accessToken);
		for (String p : picPath) {
			String postFile = CommonHttpProtocolRequestUtil.postFile(url, "pic", p, params);
			JSONObject fromString = JSONObject.fromString(postFile);
			System.out.println("===="+fromString);
			String string = fromString.getString("pic_id");
			list.add(string);
			Thread.sleep(200);
		}
		return list;
	}
	
	public static List<String> upload(String accessToken,String status,List<String> imgPath){
		List<String> list = new ArrayList<String>();
		String url = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.upload");
		Map<String, String> params = new HashMap<>();
		params.put("access_token", accessToken);
		params.put("status", status);
		for (String path : imgPath) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String postFile = CommonHttpProtocolRequestUtil.postFile(url, "pic", path, params);
			JSONObject fromString = JSONObject.fromString(postFile);
			System.out.println("+++++++++"+fromString);
			//String path2 = fromString.getString("bmiddle_pic");
			JSONArray jsonArray = fromString.getJSONArray("pic_urls");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String string = jsonObject.getString("thumbnail_pic");
				System.out.println("===="+string);
				list.add(string);
			}
		}
		return list;
		
	}
	
	public static JSONObject uploadPics(List<String> picturl,String accessToken,String status){
		String str = "";
		String url = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.upload.pics");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		url = endUrlAppendParams(url, map);
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", accessToken);
		for (int i = 0; i < picturl.size(); i++) {
			if(i!=picturl.size()-1){
				str = str + picturl.get(i)+",";
			}else{
				str = str + picturl.get(i);
			}
		}
		params.put("pic_id", str);
		String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(url, params);
		System.out.println(requestWithPost);
		return JSONObject.fromString(requestWithPost);
		
	}
	
	/**
	 * URL后拼接参数，获取token时，请求体中的参数必须为空
	 * 
	 * @param url
	 *            传入的URL
	 * @param requestParametersMap
	 *            所有参数集合
	 * @return 返回拼接好的URL
	 */
	private static String endUrlAppendParams(String url, Map<String, Object> requestParametersMap) {
		if (!url.endsWith("?")) {
			url += "?";
		}
		StringBuffer urlbuf = new StringBuffer(url);
		for (Map.Entry<String, Object> param : requestParametersMap.entrySet()) {
			urlbuf.append(param.getKey() + "=" + param.getValue());
			urlbuf.append("&");
		}
		return urlbuf.substring(0, urlbuf.length() - 1);
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		String token = "2.00Z5guWB0T7Rp6a497e4a9e3lTB1lB";
		token = "2.00tY1sZG0T7Rp66e15582780iw3PzB";
		token = "2.00i4A3uB0T7Rp6600870bca2_LnTKE";
//		List<String> list = new ArrayList<>();
//		list.add("C:\\Users\\Mr.Lee\\Pictures\\33195909430_d122cd3114_k.jpg");
//		list.add("C:\\Users\\Mr.Lee\\Pictures\\stock-photo-96786473.jpg");
//		list.add("C:\\Users\\Mr.Lee\\Pictures\\11195709_xl.jpg");
//		list.add("C:\\Users\\Mr.Lee\\Pictures\\14049218229_b6f7c79d16_k.jpg");
		
//		list = new ArrayList<>();
//		list.add("006zSkyTly1fjxav7rltej31kw11zh1z");
//		list.add("006zSkyTly1fjxav8yzu5j31hc0rhal8");
//		list.add("006zSkyTly1fjxav9llvwj31kw1204hg");
//		list.add("006zSkyTly1fjxavah84uj31kw11o7wh");
		
		
		//List<String> paths = upload(token, "测试微博多图", list);
		//uploadPics(list, token);
		//List<String> uploadPicMult = UploadPicMult(list, token);
		//List<String> uploadPic = UploadPic(paths, token);
		//List<String> uploadPic = UploadPicMult(list, token);
		//JSONObject uploadPics = uploadPics(list, token,"llllll");
		//System.out.println(uploadPic);
		
		//System.out.println(uploadPics);
		
	//JSONObject uploadPics = uploadPics(uploadPic, token,"分享图片");
	//	System.out.println(uploadPics);
	//  String dd = "/nasdata/inews/publish_cms/demopath/channel/xmtImage/175/101002017092550000034/102012017092550000130.jpeg";
    //	String substring = dd.substring(dd.indexOf("/channel"), dd.length());
//		System.out.println(substring);
		
		//String parseFileToMD5String = MD5Util.parseFileToMD5String("C:\\Users\\Mr.Lee\\Pictures\\14049218229_b6f7c79d16_k.jpg");
		//System.out.println(parseFileToMD5String);
		
		ToutiaoArticleData articleData = new ToutiaoArticleData();
		articleData.setTitle("这是一篇测试头条文章");
		articleData.setContent("这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章这是一篇测试头条文章");
		articleData.setCover("http://wx3.sinaimg.cn/mw690/654b47daly1fjy5opbcdgj20mk0kv1ky.jpg");
		articleData.setText("与其绑定短微博内容，限制1900个中英文字符内");
		//articleData.setAccess_token(token);
		//System.out.println(articleData);
		//new WeiboServiceImpl().publishToutiaoArticle(articleData);
		String str = "C:\\Users\\LEEPENG\\Desktop\\video.MP4";
		String parseFileToMD5String2 = MD5Util.parseFileToMD5String(str);
		MediaData data = new MediaData();
		
		File file = new File(str);
		FileInputStream inputStream = new FileInputStream(file);
		int available = inputStream.available();
		data.setLength(available);
		data.setCheck(parseFileToMD5String2);
		data.setName(file.getName());
		data.setAccess_token("2.00tY1sZG0T7Rp66e15582780iw3PzB");
		new WeiboServiceImpl().uploadMultimedia(data);
	}

//	@Override
//	public void publishToutiaoArticle(ToutiaoArticleData articleData) {
//		String publishToutiaoUrl = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.publish.toutiaoArticle");
//		Map<String, Object> requestParametersMap = new AnnotationAnalysis<ToutiaoArticleData>().getRequestParametersMap(ToutiaoArticleData.class, articleData);
//		String requestWithPost = CommonHttpProtocolRequestUtil.requestWithPost(publishToutiaoUrl, requestParametersMap);
//		System.out.println(requestWithPost);
//	}

	@Override
	public void uploadMultipleImages(List<String> imagePath, String accessToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadMultimedia(MediaData data) {
		String uploadMultimediaUrl = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.upload.advanced.multimedia");
		Map<String, String> requestParametersMap = new AnnotationAnalysis<MediaData>().getRequestParametersMap(MediaData.class, data);
		System.out.println(requestParametersMap);
		String requestWithPost = CommonHttpProtocolRequestUtil.postSSLUrlWithParams(uploadMultimediaUrl, requestParametersMap,"UTF-8");
		System.out.println(requestWithPost);
	}
	
	
	
}
