package cn.com.leepeng.wwfty.schema.wechat;

public class UploadMaterialResult {
	private String media_id;
	private String url;
	private String errcode;
	private String errmsg;
	
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public UploadMaterialResult(String media_id, String url) {
		super();
		this.media_id = media_id;
		this.url = url;
	}

	public UploadMaterialResult() {
		super();
		// TODO Auto-generated constructor stub
	}

}
