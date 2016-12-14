package org.apache.playframework.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.playframework.util.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.framework.controller.SuperController;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 所有Controller都应该继承该类，但是要看具体需求
 * 
 * @author willenfoo
 */
public class BaseController extends SuperController {

	/**
	 * 得到HttpServletRequest对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	protected HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}


	/**
	 * <p>
	 * 获取 easyui 分页对象
	 * </p>
	 * @param size 每页显示数量
	 * @return
	 */
	protected <T> Page<T> getEasyuiPage() {
		int pageSize = 10, offset = 1;
		if (request.getParameter("rows") != null) {
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		if (request.getParameter("page") != null) {
			offset = Integer.parseInt(request.getParameter("page"));
		}
		return new Page<T>(offset, pageSize);
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
			return String.format("attachment; filename=\"%s\"", fileName + "." + suffixName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // name.getBytes("UTF-8")处理safari的乱码问题
		return "";
	}
}
