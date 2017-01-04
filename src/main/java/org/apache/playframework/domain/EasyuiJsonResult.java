package org.apache.playframework.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.baomidou.mybatisplus.plugins.Page;

public class EasyuiJsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 841128335515870839L;

	protected final static String MESSAGE = "msg"; //返回页面的提示信息

	protected final static String CODE = "code"; //返回的的状态码
	
	protected final static String TOTAL = "total"; //整数

	protected final static String ROWS = "rows"; //返回json数据的或者list数据的key名称
	
	protected final static String HTTP_OK = "200"; //请求成功码
	
	protected final static String HTTP_NO = "201"; //请求错误码
	
	protected final static String SUCCESS_TEXT = "操作成功!";
	
	protected final static String FAILURE_TEXT = "操作失败!";
	
	protected final static String SYSTEM_ERROR_TEXT = "系统异常!";
	
	protected final static String DATA_ILLEGAL = "数据不合法!";
	
	protected final static String WARNING = "warning";
	
	protected final static String ERROR = "error";
	
	
	/**
	 * 返回失败的标识方法
	 * @return
	 */
	public static Map<String, Object> getFailureResult() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_NO);
		map.put(MESSAGE, FAILURE_TEXT);
		return map;
	}  
	
	/**
	 * 返回失败的标识方法
	 * @return
	 */
	public static Map<String, Object> getSystemErrorResult() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_NO);
		map.put(MESSAGE, FAILURE_TEXT);
		return map;
	}
	
	/**
	 * 返回失败的标识方法
	 * @return
	 */
	public static Map<String, Object> getFailureResult(BindingResult br) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_NO);
		
		List<FieldError> list = br.getFieldErrors();
		for (FieldError objectError : list) {
			map.put(objectError.getField(),  objectError.getDefaultMessage());
		}
		return map;
	}
	
	/**
	 * 返回失败的标识方法
	 * @return
	 */
	public static Map<String, Object> getFailureResult(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_NO);
		map.put(MESSAGE, msg);
		return map;
	}
	
	/**
	 * 返回失败的标识方法
	 * @return
	 */
	public static Map<String, Object> getFailureResult(String msg, String showType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_NO);
		map.put(MESSAGE, msg);
		map.put("showType", showType);
		return map;
	}
	
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK); 
		map.put(MESSAGE, SUCCESS_TEXT);
		return map;
	}
	
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK);  
		map.put(MESSAGE, msg);
		return map;
	}
		
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult(List<?> resultList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK); 
		map.put(MESSAGE, SUCCESS_TEXT);
		if (resultList == null) {
			resultList = new ArrayList<>();
		}
		map.put(ROWS, resultList);
		return map;
	}
	
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult(int total, List<?> resultList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK); 
		map.put(MESSAGE, SUCCESS_TEXT);
		if (resultList == null) {
			resultList = new ArrayList<>();
		}
		map.put(TOTAL, total);
		map.put(ROWS, resultList);
		return map;
	}
	
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult(Page<?> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK); 
		map.put(MESSAGE, SUCCESS_TEXT);
		if (page == null) {
			page = new Page<>();
		}
		map.put(TOTAL, page.getTotal());
		map.put(ROWS, page.getRecords());
		return map;
	}
	
	
	
	/**
	 * 返回成功的标识方法
	 * @return
	 */
	public static Map<String, Object> getSuccessResult(String msg, List<?> resultList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, HTTP_OK); 
		map.put(MESSAGE, msg);
		if (resultList == null) {
			resultList = new ArrayList<>();
		}
		map.put(ROWS, resultList);
		return map;
	}
	
	public static void main(String[] args) {
		
	}
}
