package org.apache.playframework.mybatisplus.mapper;

import org.apache.playframework.mybatisplus.plugins.PageId;

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
	public static <T> EntityWrapper<T> bind(Class<T> entity, Object entityVo, PageId<T> pageId) {
		EntityWrapper<T> ew = new EntityWrapper<T>((T)entityVo);
		ew.isWhere(false);
		if (pageId != null && pageId.getIndexId() != null) {
        	if (pageId.getOffset() >= 0) {
            	ew.gt("id", pageId.getIndexId());
            } else {
            	ew.lt("id", pageId.getIndexId());
            }
        }
		return ew;
	}
	
	public static <T> EntityWrapper<T> bind(Class<T> entity) {
		return bind(entity, null, null);
	}
	
	public static <T> EntityWrapper<T> bind(Class<T> entity, Object entityVo) {
		return bind(entity, entityVo, null);
	}
}
