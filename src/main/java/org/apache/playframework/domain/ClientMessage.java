package org.apache.playframework.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.playframework.util.JacksonUtils;

/**
 * 客户端报文
 * 
 * @author tangxiaojun
 *
 */
public class ClientMessage  {

	// 错误描述信息
	private String msg = "操作成功";
	 
	// 对象信息
	private Object data = MapUtils.EMPTY_MAP;

	/**
	 * 0000 : 访问正常, 1000：用户未登录  , 1001： sessionId过期, 1002：IP地址发生变化 ,  1003：当前接口弃用需要客户端强制升级, 
	 * 1004：维护中  ,1005：非法请求 ,1006：接口异常或错误
	 */
	private String code = SUCCESS_CODE;
	
	private static String SUCCESS_CODE = "0000";
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private ClientMessage() {
		
	}
	
	private ClientMessage(Object data) {
		this.data = data;
	}
	
	private ClientMessage(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}
	
	private ClientMessage(String code, String msg, Object data) {
		this.msg = msg;
		this.code = code;
		this.data = data;
	}

	private ClientMessage(Object data, String msg) {
		this.msg = msg;
		this.data = data;
	}

	public static final ClientMessage success() {
		return new ClientMessage();
	}

	public static final ClientMessage success(String msg) {
		return new ClientMessage(SUCCESS_CODE, msg);
	}
	
	public static final ClientMessage success(Object data) {
		if (data instanceof List) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("list", data);
			return new ClientMessage(resultMap);
		}
		return new ClientMessage(data);
	}

	public static final ClientMessage success(Object data, String msg) {
		return new ClientMessage(data, msg);
	}

	public static final ClientMessage failed(String msg) {
		return new ClientMessage("0001", msg);
	}

	public static final ClientMessage failed(String code, String msg) {
		return new ClientMessage(code, msg);
	}
 
	public static final ClientMessage failed(Object data, String msg) {
		return new ClientMessage("0001", msg ,data);
	}
	
	public static final ClientMessage failed(Object data) {
		return new ClientMessage("0001", "" , data);
	}
	
	public static void main(String[] args) {
		ClientMessage cm = success();
		System.out.println(JacksonUtils.writeValueAsString(cm));
	}
}
