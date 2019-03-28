package cn.com.leepeng.wwfty.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP请求工具类。HTTPS忽略证书请求 httpclient 4.3
 * 
 * @author LEEPENG
 * @date 21st Sep,2017
 * @mail lp@zving.com/leepeng@leepeng.com.cn
 *
 */
public class CommonHttpProtocolRequestUtil {
	public static final int http_ok = 200;// 返回状态码正常

	public static final int CONNECTION_TIMEOUT = 5000;// 连接超时

	public static final int READDATA_TIMEOUT = 10000;// 数据读取等待超时

	public static final int DEFAULT_HTTP_PORT = 80;// http端口

	public static final int DEFAULT_HTTPS_PORT = 443;// https端口

	private static String EMPTY_STR = "";
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 设置代理服务器，访问facebook，twitter等需要配置代理服务器IP和端口，如果是走专线此处不用设置
		// configBuilder.setProxy(new HttpHost("127.0.0.1",50814));
		requestConfig = configBuilder.build();

	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		// return
		// HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr)
		// .setDefaultRequestConfig(requestConfig).build();

		return createSSLConnSocketFactory();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String requestWithGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	public static String requestWithGet(String url, Map<String, Object> params) {
		URIBuilder ub = null;
		try {
			ub = new URIBuilder(url);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(ub.build());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return getResult(httpGet);
	}

	public static String requestWithGet(String url, Map<String, Object> headers, Map<String, Object> params) {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(ub.build());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String requestWithPost(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				// return null;
				throw new Exception(response.toString());
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static CloseableHttpClient createSSLConnSocketFactory() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 默认信任所有证书
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();
			// AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
			SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslcsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	
	private static List<Header> defaultHeader() {
	    ArrayList<Header> headers = new ArrayList<Header>();
	    Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
	    headers.add(header);
	    return headers;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) {
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClient client = HttpClientBuilder.create().build();
		try {
			HttpResponse response = client.execute(request);
			// response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// long len = entity.getContentLength();// -1 表示长度未知
				return EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (request != null) {
				request.releaseConnection();
			}
		}

		return EMPTY_STR;
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}
		return pairs;
	}

	public static String postFile(String url, String serverFieldName, String localFilePath,
			Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String respStr = null;
		HttpEntity resEntity = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody binFileBody = new FileBody(new File(localFilePath));
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			// add the file params
			multipartEntityBuilder.addPart(serverFieldName, binFileBody);
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			response = httpclient.execute(httppost);
			System.out.println(response.getStatusLine());
			resEntity = response.getEntity();
			respStr = getRespString(resEntity);
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpclient != null) {
					httpclient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return respStr;

	}
	public static String postFile(String url, String serverFieldName, List<String> localFilePath,
			Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String respStr = null;
		HttpEntity resEntity = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httppost = new HttpPost(url);
			
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			for (String string : localFilePath) {
				FileBody binFileBody = new FileBody(new File(string));
				multipartEntityBuilder.addPart(serverFieldName, binFileBody);
				
			}
			httppost.addHeader("Authorization", "Bearer Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlY2hpc2FuIiwic3ViIjoie1wiY2xpZW50XCI6XCJ3ZWJcIixcInR5cGVcIjpcInVzZXJcIixcIm5hbWVcIjpcImFkbWluXCIsXCJzaWduYXR1cmVcIjpudWxsfSIsImlhdCI6MTU1Mzc2MDk5NywiZXhwIjoxNTUzNzc4OTk3fQ.XASkykHI5yjwY8lyGwcCiRuFuLaqBVY6w6xwzXDWhwVlgIiK9kjv3mcw9sv49Ih-q0mI5MK2y3zfsLICLkieVw");
			// add the file params
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);
			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);
			response = httpclient.execute(httppost);
			System.out.println(response.getStatusLine());
			resEntity = response.getEntity();
			respStr = getRespString(resEntity);
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpclient != null) {
					httpclient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return respStr;

	}

	/**
	 * 设置上传文件时所附带的其他参数
	 * 
	 * @param multipartEntityBuilder
	 * @param params
	 */
	private static void setUploadParams(MultipartEntityBuilder multipartEntityBuilder, Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				multipartEntityBuilder.addPart(key, new StringBody(params.get(key), ContentType.TEXT_PLAIN));
			}
		}
	}

	/**
	 * 将返回结果转化为String
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private static String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}

	/**
	 * 无需本地证书keyStore的SSL https带参数请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @param encoding
	 * @return
	 */
	public static String postSSLUrlWithParams(String url, Map<String, String> reqMap, String encoding) {
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		// 添加参数
		List<NameValuePair> params = new ArrayList<>();
		if (reqMap != null && reqMap.keySet().size() > 0) {
			Iterator<Map.Entry<String, String>> iter = reqMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entity = iter.next();
				params.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
			}
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			// 设置客户端请求的头参数getParams已经过时,现在用requestConfig对象替换
			//httpClient.getParams().setParameter("Content-Type","application/x-www-form-urlencoded");
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
					.setSocketTimeout(READDATA_TIMEOUT).build();
			post.setConfig(requestConfig);
			// 设置编码格式
			post.setEntity(new UrlEncodedFormEntity(params, encoding));
			HttpResponse response = httpClient.execute(post);
			HttpEntity httpEntity = response.getEntity();
			br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encoding));
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + encoding);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException("读取流文件异常", e);
		} catch (Exception e) {
			throw new RuntimeException("通讯未知系统异常", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		List<String> fiept = new ArrayList<>();
		fiept.add("C:\\Users\\Alex.Lee\\Pictures\\photo-1498932042873-e35fb6535a02.jpg");
		fiept.add("C:\\Users\\Alex.Lee\\Pictures\\new.jpg");
		Map<String, String> params = new HashMap<>();
		params.put("content", "11122223112");
		params.put("title", "XXXXXXXXXX");
		params.put("catalogID", "15162");
		params.put("hasFile", "true");
		String postFile = postFile("http://47.94.198.40/zcms/api/articles/replace", "attachments", fiept, params);
		System.out.println(postFile);
	}

}
