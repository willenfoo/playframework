package org.apache.playframework.mybatisplus.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * 支持用id分页，支持字段(like, LIKE, GT, LT, GTE, LTE,IN)按规则查询
 * http://blog.csdn.net/liu4071325/article/details/52248374
 * 
 * @author fuwei
 *
 */
public class EntityWrapperBind {

	@SuppressWarnings("unchecked")
	public static <T> EntityWrapper<T> bind(Class<T> entity, Object entityVo) {
		EntityWrapper<T> ew = new EntityWrapper<T>((T)entityVo);
		return ew;
	}
	
	public static <T> EntityWrapper<T> bind(Class<T> entity) {
		return bind(entity, null);
	}
	

}
