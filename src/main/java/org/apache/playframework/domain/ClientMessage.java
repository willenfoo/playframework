package org.apache.playframework.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.playframework.util.JacksonUtils;

/**
 * 客户端报文
 * 
 * @author tangxiaojun
 *
 */
public class ClientMessage {

	// 错误描述信息
	private String msg = "操作成功";
	 
	// 对象信息
	private Object data = CollectionUtils.EMPTY_COLLECTION;

	/**
	 * 0000 : 访问正常, 10001：当前接口弃用需要客户端强制升级, 1002：维护中  1003：当前访问的接口有新版本可使用 1004： jsession失效,1005：非法请求 ,1006：接口异常或错误
	 */
	private String code = "0000";
	
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

	public ClientMessage() {
		
	}
	
	public ClientMessage(Object data) {
		this.data = data;
	}
	
	public ClientMessage(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public ClientMessage(Object data, String msg) {
		this.msg = msg;
		this.data = data;
	}

	public static final ClientMessage success() {
		return new ClientMessage();
	}

	public static final ClientMessage success(Object data) {
		return new ClientMessage(data);
	}

	public static final ClientMessage success(Object data, String msg) {
		return new ClientMessage(data, msg);
	}

	public static final ClientMessage failed(String msg) {
		return new ClientMessage(1005, msg);
	}

	public static final ClientMessage failed(String code, String msg) {
		return new ClientMessage(code, msg);
	}
 
	
	public static void main(String[] args) {
		ClientMessage cm = success();
		System.out.println(JacksonUtils.writeValueAsString(cm));
	}
}
