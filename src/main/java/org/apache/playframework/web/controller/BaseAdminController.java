package org.apache.playframework.web.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.playframework.domain.EasyuiClientMessage;
import org.apache.playframework.domain.EasyuiJsonResult;

import java.util.Map;

/**
 * 所有Controller都应该继承该类，但是要看具体需求
 * 
 * @author willenfoo
 */
public class BaseAdminController extends SuperController {

	protected final static String ROWS = "rows"; //返回json数据的或者list数据的key名称

	/**
	 * <p>
	 * 获取 easyui 分页对象
	 * </p>
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

	public Map<String, Object> getResult(boolean flag) {
		Map<String, Object> resultMap;
		if (flag) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	}
	
	public Map<String, Object> success(boolean flag) {
		Map<String, Object> resultMap;
		if (flag) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	}
	
	public EasyuiClientMessage success(Page<?> page) {
		return EasyuiClientMessage.success(page);
	}
	
	public EasyuiClientMessage success(Page<?> page, Class<?> resultClass) {
		return EasyuiClientMessage.success(page, resultClass);
	}
	
	public Map<String, Object> getResult(boolean flag, String msg) {
		Map<String, Object> resultMap;
		if (flag) {
		    resultMap = EasyuiJsonResult.getSuccessResult(msg);
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult(msg);
		}
		return resultMap;
	}
	
	public Map<String, Object> getFailResult(String msg) {
		return EasyuiJsonResult.getFailureResult(msg);
	}

}
