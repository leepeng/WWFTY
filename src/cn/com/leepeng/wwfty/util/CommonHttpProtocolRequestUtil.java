package cn.com.leepeng.wwfty.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * 
 * @author Mr.Lee
 *
 */
@SuppressWarnings("deprecation")
public class CommonHttpProtocolRequestUtil {
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
         // 在提交请求之前 测试连接是否可用  
         configBuilder.setStaleConnectionCheckEnabled(true);
         //configBuilder.setProxy(new HttpHost("127.0.0.1",50814));
         requestConfig = configBuilder.build(); 
         
     }  
     
     public static String requestWithPost(String apiUrl, Map<String, Object> params) {  
         CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
         HttpPost httpPost = new HttpPost(apiUrl);  
         CloseableHttpResponse response = null;  
         String httpStr = null;  
   
         try {  
             httpPost.setConfig(requestConfig);  
             List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
             for (Map.Entry<String, Object> entry : params.entrySet()) {  
                 NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                         .getValue().toString());  
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
}
