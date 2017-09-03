package cn.com.leepeng.wwfty.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import net.sf.json.JSONObject;

public class WechatTokenUtil {
	public static String getAccessToken(String appID,String appSecret){
		String tokenUrl = ConfigurationPropertiesUtil.getConfigProperties("Wechat.Accesstoken.Url");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grant_type", "client_credential");
		params.put("appid", appID);
		params.put("secret", appSecret);
		String requestWithGet = CommonHttpProtocolRequestUtil.requestWithGet(tokenUrl, params);
		JSONObject fromString = JSONObject.fromString(requestWithGet);
		return fromString.getString("access_token");
		
	}
	
	public static void main(String[] args) {
		getAccessToken("wxdd404791419bd48d","ececc64f0769034b2c2a8422343b11d1");
	}
	
	 
}
