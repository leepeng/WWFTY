package cn.com.leepeng.wwfty.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
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
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
		//设置代理服务器，访问facebook，twitter等需要配置代理服务器IP和端口，如果是走专线此处不用设置
		// configBuilder.setProxy(new HttpHost("127.0.0.1",50814));
		requestConfig = configBuilder.build();

	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).build();
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
				return null;
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
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
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
}
