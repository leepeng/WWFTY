package cn.com.leepeng.wwfty.schema.weibo;

import cn.com.leepeng.wwfty.annotation.wechat.Data;
import cn.com.leepeng.wwfty.annotation.wechat.DataParam;

/**
 * 新浪微博Oauth2身份验证
 * 
 * @author LEEPENG
 *
 */
@Data(value = "AOauth2")
public class AOauth2Token implements Cloneable {
	@DataParam(value = "client_id")
	private String clientId;
	@DataParam(value = "client_secret")
	private String clientSecret;
	@DataParam(value = "grant_type")
	private String grantType;
	@DataParam(value = "code")
	private String code;
	@DataParam(value = "redirect_uri")
	private String redirectUri;

	public AOauth2Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AOauth2Token(String clientId, String clientSecret, String grantType, String code, String redirectUri) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.grantType = grantType;
		this.code = code;
		this.redirectUri = redirectUri;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public class OAuthResult {
		private String access_token;
		private String expires_in;
		private String remind_in;
		private String uid;
		private String isRealName;

		public OAuthResult() {
			super();
			// TODO Auto-generated constructor stub
		}

		public OAuthResult(String access_token, String expires_in, String remind_in, String uid, String isRealName) {
			super();
			this.access_token = access_token;
			this.expires_in = expires_in;
			this.remind_in = remind_in;
			this.uid = uid;
			this.isRealName = isRealName;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}

		public String getRemind_in() {
			return remind_in;
		}

		public void setRemind_in(String remind_in) {
			this.remind_in = remind_in;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getIsRealName() {
			return isRealName;
		}

		public void setIsRealName(String isRealName) {
			this.isRealName = isRealName;
		}
	}
}
