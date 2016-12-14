package org.apache.playframework.service;

import com.baomidou.mybatisplus.service.IService;

/**
 * 顶级 Service
 * @author willenfoo
 * @param <T>
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * <p>
	 * 根据  T ，查询一条记录
	 * </p>
	 * @param entity 实体对象
	 * @return T
	 */
	T selectOne(T entity);
}
