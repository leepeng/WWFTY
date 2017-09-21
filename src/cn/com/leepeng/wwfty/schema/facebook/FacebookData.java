package cn.com.leepeng.wwfty.schema.facebook;

public class FacebookData {
	
	private String id;
	private FacebookIndividualVideo facebookIndividualVideo;
	private String accessToken;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FacebookIndividualVideo getFacebookIndividualVideo() {
		return facebookIndividualVideo;
	}
	public void setFacebookIndividualVideo(FacebookIndividualVideo facebookIndividualVideo) {
		this.facebookIndividualVideo = facebookIndividualVideo;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
