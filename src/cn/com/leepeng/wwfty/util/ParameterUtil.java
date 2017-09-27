package cn.com.leepeng.wwfty.util;

import java.util.Map;
/**
 * 
 * @author LEEPENG
 *
 */
public class ParameterUtil {
	/**
	 * URL后拼接参数，获取token时，请求体中的参数必须为空
	 * 
	 * @param url
	 *            传入的URL
	 * @param requestParametersMap
	 *            所有参数集合
	 * @return 返回拼接好的URL
	 */
	public static String endUrlAppendParams(String url, Map<String, Object> requestParametersMap) {
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
}
