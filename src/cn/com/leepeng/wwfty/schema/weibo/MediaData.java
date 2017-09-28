package cn.com.leepeng.wwfty.schema.weibo;

import net.sf.json.JSONObject;

/**
 * 
 * @author LEEPENG
 *
 */
public class MediaData extends WeiboBaseData implements Cloneable {
	private Integer length;
	private String check;
	private String name;
	private String client;
	private String type;
	private String status_text;
	private String status_visible;
	private String callback;
	private String mediaprops;

	public MediaData() {
		super();
	}

	public MediaData(Integer length, String check, String name, String client) {
		super();
		this.length = length;
		this.check = check;
		this.name = name;
		this.client = client;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public String getStatus_visible() {
		return status_visible;
	}

	public void setStatus_visible(String status_visible) {
		this.status_visible = status_visible;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getMediaprops() {
		return mediaprops;
	}

	public void setMediaprops(String mediaprops) {
		this.mediaprops = mediaprops;
	}

	@Override
	public String toString() {
		return JSONObject.fromBean(this).toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	

}
