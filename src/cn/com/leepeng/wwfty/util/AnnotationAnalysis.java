/*
 * Copyright (c) 2016, 2017, LEEPENG and/or its affiliates. All rights reserved.
 * LEEPENG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.leepeng.wwfty.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.com.leepeng.wwfty.annotation.wechat.Data;
import cn.com.leepeng.wwfty.annotation.wechat.DataParam;
import cn.com.leepeng.wwfty.schema.facebook.FacebookData;
import cn.com.leepeng.wwfty.schema.facebook.FacebookIndividualVideo;
import cn.com.leepeng.wwfty.schema.wechat.ArticlesData;
import cn.com.leepeng.wwfty.schema.wechat.CustomServiceData;

/**
 * 参数封装数据类注解解析器
 * 
 * @author 李鹏(LEEPENG)
 * @date 3rd Sep,2017
 * @version 1.0
 * @param <T>
 */
public class AnnotationAnalysis<T> {
	private static final String GET = "get";

	/**
	 * 注解解析方法
	 * 
	 * @param clazz
	 *            待设置的数据类
	 * @param data
	 *            设置值后的数据
	 * @return 根据注解返回封装好的参数
	 */
	public Map<String, Object> getRequestParametersMap(Class<?> clazz, T data) {
		Map<String, Object> _paramsMap = null;
		try {
			_paramsMap = new LinkedHashMap<String, Object>();
			if (clazz.isAnnotationPresent(Data.class)) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					Annotation[] annotations = field.getDeclaredAnnotations();
					String _target_method = GET + field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					for (Annotation annotation : annotations) {
						if (annotation instanceof DataParam) {
							DataParam dataParam = (DataParam) annotation;
							Object value = null;
							if (data instanceof ArticlesData) {
								ArticlesData articlesData = (ArticlesData) data;
								value = clazz.getDeclaredMethod(_target_method).invoke(articlesData.clone());
							} else if (data instanceof CustomServiceData) {
								CustomServiceData customServiceData = (CustomServiceData) data;
								value = clazz.getDeclaredMethod(_target_method).invoke(customServiceData.clone());
							} else if (data instanceof FacebookIndividualVideo) {
								FacebookIndividualVideo facebookIndividualVideo = (FacebookIndividualVideo) data;
								value = clazz.getDeclaredMethod(_target_method).invoke(facebookIndividualVideo.clone());
							} else if (data instanceof FacebookData) {
								FacebookData facebookData = (FacebookData) data;
								/// value =
								// clazz.getDeclaredMethod(_target_method).invoke(facebookData.clone());
							}
							_paramsMap.put(dataParam.value(), (!"".equals(value) && value != null) ? value : "");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _paramsMap;
	}
}
