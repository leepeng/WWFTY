package cn.com.leepeng.wwfty.exception;

public class WechatException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 896428065140163499L;
	private String message;
	public WechatException(String message) {
		super(message);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
