package cn.com.leepeng.wwfty.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.com.leepeng.wwfty.exception.WeiboException;
import cn.com.leepeng.wwfty.schema.weibo.AOauth2Token;
import cn.com.leepeng.wwfty.schema.weibo.AOauth2Token.OAuthResult;
import cn.com.leepeng.wwfty.schema.weibo.WeiboInvokeAPIResult;
import net.sf.json.JSONObject;

/**
 * 微博获取Accesstoken工具类
 * 
 * @author LEEPENG
 * @date 24th Sep,2017
 * @mail lp@zving.com/leepeng@leepeng.com.cn
 *
 */
public class WeiboTokenUtil {
	public static final String SUCCESS_CODE = "0";

	/**
	 * 检测是否存在异常
	 * 
	 * @param apiResult
	 *            异常返回队形
	 * @throws WeiboException
	 *             抛出微博异常
	 */
	public static void checkHasException(WeiboInvokeAPIResult apiResult) throws WeiboException {
		if (StringUtils.isNotEmpty(apiResult.getError_code()) && !SUCCESS_CODE.equals(apiResult.getError_code())) {
			throw new WeiboException(apiResult.toString());
		}
	}

	/**
	 * 获取token工具类
	 * 
	 * @param oauth2Token
	 * @return 返回token
	 */
	public static String getAccessToken(AOauth2Token oauth2Token) {
		String[] readOrWriteTokenTempFile = readOrWriteTokenTempFile(null, false);
		if (readOrWriteTokenTempFile != null && readOrWriteTokenTempFile.length == 3) {
			long time = System.currentTimeMillis() / 1000;
			if ((time - Long.valueOf(readOrWriteTokenTempFile[2])) < Long.valueOf(readOrWriteTokenTempFile[0])) {
				return readOrWriteTokenTempFile[1];
			}
		}
		String authorizeUrl = null;
		Map<String, Object> requestParametersMap = null;
		AOauth2Token.OAuthResult authResult = null;
		WeiboInvokeAPIResult apiResult = null;
		try {
			authorizeUrl = ConfigurationPropertiesUtil.getConfigProperties("weibo.api.oauth2.authorize.token");
			requestParametersMap = new AnnotationAnalysis<AOauth2Token>().getRequestParametersMap(AOauth2Token.class,
					oauth2Token);
			authorizeUrl = endUrlAppendParams(authorizeUrl, requestParametersMap);
			String result = CommonHttpProtocolRequestUtil.requestWithPost(authorizeUrl, new HashMap<>());
			if (StringUtils.isNotEmpty(result)) {
				JSONObject object = JSONObject.fromString(result);
				try {
					authResult = (AOauth2Token.OAuthResult) JSONObject.toBean(object, AOauth2Token.OAuthResult.class);
					if (authResult != null) {
						readOrWriteTokenTempFile(authResult, true);
						return authResult.getAccess_token();
					}
				} catch (Exception e) {
					apiResult = (WeiboInvokeAPIResult) JSONObject.toBean(object, WeiboInvokeAPIResult.class);
					checkHasException(apiResult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将获取到的token写入临时文件
	 * 
	 * @param authResult
	 *            授权返回对象
	 * @param isWrite
	 *            是否写入文件
	 * @return 返回过期时间，accesstoken即过期的时间
	 */
	private static String[] readOrWriteTokenTempFile(OAuthResult authResult, boolean isWrite) {
		if (authResult == null && isWrite) {
			return null;
		}
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;
		try {
			if (isWrite) {
				bufferedWriter = new BufferedWriter(new FileWriter(new File("temp.dat")));
				bufferedWriter.write(authResult.getExpires_in());
				bufferedWriter.newLine();
				bufferedWriter.write(authResult.getAccess_token());
				bufferedWriter.newLine();
				String timeOut = String
						.valueOf(System.currentTimeMillis() / 1000 + Long.valueOf(authResult.getExpires_in()));
				bufferedWriter.write(timeOut);
				bufferedWriter.flush();
				return new String[] { authResult.getExpires_in(), authResult.getAccess_token(), timeOut };
			} else {
				bufferedReader = new BufferedReader(new FileReader(new File("temp.dat")));
				String expires_in = bufferedReader.readLine();
				String access_token = bufferedReader.readLine();
				String timeOut = bufferedReader.readLine();
				if (StringUtils.isEmpty(timeOut)) {
					return new String[] { expires_in, access_token };
				}
				return new String[] { expires_in, access_token, timeOut };
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
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

	public static void main(String[] args) {
		AOauth2Token oauth2 = new AOauth2Token();
		oauth2.setClientId("432508261");
		oauth2.setClientSecret("eb8800338a28a6a96031bce4a23dafdd");
		oauth2.setCode("b0fc3d6fc067af8a4db58bb64298cc71");
		oauth2.setRedirectUri("http://www.leepeng.com.cn");
		oauth2.setGrantType("authorization_code");

		String accessToken = getAccessToken(oauth2);
		System.out.println("==========================" + accessToken);
	}
}
