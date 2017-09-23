package cn.com.leepeng.wwfty.schema.weibo;

public class WeiboInvokeAPIResult {
	private String error;
	private String error_code;
	private String request;

	public WeiboInvokeAPIResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeiboInvokeAPIResult(String error, String error_code, String request) {
		super();
		this.error = error;
		this.error_code = error_code;
		this.request = request;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "{\"error\":\"" + this.error + "\",\"error_code\":" + this.error_code + ",\"request\":\"" + this.request
				+ "\"}";
	}

}
