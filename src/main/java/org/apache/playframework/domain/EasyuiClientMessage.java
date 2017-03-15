package org.apache.playframework.domain;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.playframework.util.BeanCopierUtils;
import org.apache.playframework.util.JacksonUtils;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 客户端报文
 * 
 * @author tangxiaojun
 *
 */
public class EasyuiClientMessage {

	// 错误描述信息
	private String msg = "操作成功";
	 
	// 对象信息
	private Collection<?> rows = CollectionUtils.EMPTY_COLLECTION;

	private Integer total;
	
	/**
	 * 1000 : 访问正常 10001：当前接口弃用需要客户端强制升级 1002：维护中 (提示消息放入errorMsg字段中)
	 * 1003：当前访问的接口有新版本可使用 1004： jsession失效 1005：接口异常或错误
	 */
	private String code = "200";
	
	// 对象信息
	private Object data = CollectionUtils.EMPTY_COLLECTION;
		
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

	public Collection<?> getRows() {
		return rows;
	}

	public void setRows(Collection<?> rows) {
		this.rows = rows;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public EasyuiClientMessage() {
		
	}
	
	public EasyuiClientMessage(Collection<?> rows) {
		this.rows = rows;
	}
	
	public EasyuiClientMessage(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public EasyuiClientMessage(Collection<?> rows, String msg) {
		this.msg = msg;
		this.rows = rows;
	}
	
	public EasyuiClientMessage(Integer total, Collection<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public static final EasyuiClientMessage success() {
		return new EasyuiClientMessage();
	}

	public static final EasyuiClientMessage success(Collection<?> rows) {
		return new EasyuiClientMessage(rows);
	}
	
	public static final <T> EasyuiClientMessage success(Page<?> page,Class<T> resultClass) {
		return new EasyuiClientMessage(page.getTotal(), BeanCopierUtils.copyToList(page.getRecords(), resultClass));
	}
	
	
	public static final EasyuiClientMessage success(Collection<?> rows, String msg) {
		return new EasyuiClientMessage(rows, msg);
	}

	public static final EasyuiClientMessage failed(String msg) {
		return new EasyuiClientMessage("1005", msg);
	}

	public static final EasyuiClientMessage failed(String code, String msg) {
		return new EasyuiClientMessage(code, msg);
	}
 
	
	public static void main(String[] args) {
		EasyuiClientMessage cm = success();
		System.out.println(JacksonUtils.writeValueAsString(cm));
	}
}
