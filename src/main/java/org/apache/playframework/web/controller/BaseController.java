package org.apache.playframework.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.playframework.util.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/** 
 * 所有Controller都应该继承该类，但是要看具体需求
 * @author willenfoo
 */    
public class BaseController {

	protected final static String REDIRECT = "redirect:"; //重定向页面
	
	/**
	 * 得到HttpServletRequest对象
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected Integer getOffset() {
		String offset = getRequest().getParameter("page");
		if (NumberUtils.isNumber(offset)) {
			return (Integer.valueOf(offset));
		} 
		return 0;
	}
	
	protected Integer getPageSize() {
		String pageSize = getRequest().getParameter("rows");
		if (NumberUtils.isNumber(pageSize)) {
			return Integer.valueOf(pageSize);
		}
		return 10;
	}
	
	
	/**
	 * 重定向URL
	 * @param address 地址
	 * @return
	 */
	public String redirect(String address) {
		return REDIRECT + address;
	}

	
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);

		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	public String initDownloadFileName(String fileName, String suffixName) {
		if (StringUtils.isEmpty(fileName)) {
			return "";
		}
        String userAgent = getRequest().getHeader("User-Agent");  
        byte[] bytes;
		try {
			bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
			fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码  
	        return String.format("attachment; filename=\"%s\"", fileName+"."+suffixName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // name.getBytes("UTF-8")处理safari的乱码问题  
		return "";
	}
}
