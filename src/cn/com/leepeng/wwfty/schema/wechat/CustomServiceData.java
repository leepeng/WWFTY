package cn.com.leepeng.wwfty.schema.wechat;

import cn.com.leepeng.wwfty.annotation.Data;
import cn.com.leepeng.wwfty.annotation.DataParam;
/**
 * // 调用接口凭证
 * @author Mr.Lee
 *
 */
@Data("CustomServiceData")
public class CustomServiceData implements Cloneable{
	@DataParam(value = "access_token")
	private String accessToken;
	@DataParam(value = "kf_account")
	private String customServiceAccount;
	@DataParam(value = "kf_nick")
	private String customServiceNick;
	@DataParam(value = "kf_id")
	private String customServiceID;
	@DataParam(value = "nickname")
	private String nickName;
	@DataParam(value = "password")
	private String password;
	@DataParam(value = "media")
	private String media;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getCustomServiceAccount() {
		return customServiceAccount;
	}
	public void setCustomServiceAccount(String customServiceAccount) {
		this.customServiceAccount = customServiceAccount;
	}
	public String getCustomServiceNick() {
		return customServiceNick;
	}
	public void setCustomServiceNick(String customServiceNick) {
		this.customServiceNick = customServiceNick;
	}
	public String getCustomServiceID() {
		return customServiceID;
	}
	public void setCustomServiceID(String customServiceID) {
		this.customServiceID = customServiceID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	

}
