package cn.com.leepeng.wwfty.util;

import org.apache.commons.lang.StringUtils;

import cn.com.leepeng.wwfty.exception.WechatException;
import cn.com.leepeng.wwfty.schema.wechat.UploadMaterialResult;

public class WechatUtil {
	public static final String SUCCESS_CODE = "0";
	public static void checkHasException(UploadMaterialResult materialResult) throws WechatException{
		if(StringUtils.isNotEmpty(materialResult.getErrcode()) && !SUCCESS_CODE.equals(materialResult.getErrcode())){
			throw new WechatException("{\"errcode\":"+materialResult.getErrcode()+",\"errmsg\":\""+materialResult.getErrmsg()+"\"}");
		}
	}
}
