package cn.com.leepeng.wwfty.schema.weibo;

/**
 * 
 * @author LEEPENG
 *
 */
public class Display {
	/**
	 * 默认的授权页面，适用于web浏览器。
	 */
	public static final String DEFAULT = "default";
	/**
	 * 移动终端的授权页面，适用于支持html5的手机。注：使用此版授权页请用
	 * https://open.weibo.cn/oauth2/authorize 授权接口
	 */
	public static final String MOBILE = "mobile";
	/**
	 * wap版授权页面，适用于非智能手机。
	 */
	public static final String WAP = "wap";
	/**
	 * 客户端版本授权页面，适用于PC桌面应用。
	 */
	public static final String CLIENT = "client";
	/**
	 * 默认的站内应用授权页，授权后不返回access_token，只刷新站内应用父框架。
	 */
	public static final String APPONWEIBO = "apponweibo";
}
