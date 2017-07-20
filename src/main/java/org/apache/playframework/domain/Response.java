package org.apache.playframework.domain;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233172608097378104L;

	public static String SUCCESS = "0000";

	public static String SUCCESS_TEXT = "操作成功!";

	public static String FAILURE = "0001";

	public static String FAILURE_TEXT = "操作失败!";

	public static String SKIP = "0002";

	public static String SKIP_TEXT = "忽略记录!";

	public static String SYSTEM_ERROR = "系统出错!";

	private String code = SUCCESS;

	private String msg = SUCCESS_TEXT;

	private String number; //业务逻辑编号
	
	private List<?> list;

	public Response() {

	}

	public Response(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public static final Response success() {
		return new Response();
	}
	
	public static final Response failed() {
		return new Response(FAILURE, FAILURE_TEXT);
	}

	public static final Response failed(String msg) {
		return new Response(FAILURE, msg);
	}

	public static final Response failed(String code, String msg) {
		return new Response(code, msg);
	}
}
