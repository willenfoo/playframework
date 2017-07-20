package org.apache.playframework.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.playframework.service.BaseService;
import org.apache.playframework.service.FieldFillService;
import org.apache.playframework.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T> implements BaseService<T> {

	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired(required = false)
	private FieldFillService fieldFillService;

	public Map<String, Object> loadInsertData() {
		if (fieldFillService == null) {
			fieldFillService = new FieldFillServiceDefaultImpl();
		}
		return fieldFillService.getInsertData();
	}
	
	public Map<String, Object> loadUpdateData() {
		if (fieldFillService == null) {
			fieldFillService = new FieldFillServiceDefaultImpl();
		}
		return fieldFillService.getUpdateData();
	}
	
	private void setInsertData(T entity) {
		Map<String, Object> insertData = loadInsertData();
		for (Map.Entry<String, Object> entry : insertData.entrySet()) {
			ReflectUtils.setProperty(entity, entry.getKey(), entry.getValue());
		}
	}

	private void setInsertData(List<T> list) {
		Map<String, Object> insertData = loadInsertData();
		for (T t : list) {
			for (Map.Entry<String, Object> entry : insertData.entrySet()) {
				ReflectUtils.setProperty(t, entry.getKey(), entry.getValue());
			}
		}
	}

	private void setUpdateData(T entity) {
		Map<String, Object> updateData = loadUpdateData();
		for (Map.Entry<String, Object> entry : updateData.entrySet()) {
			ReflectUtils.setProperty(entity, entry.getKey(), entry.getValue());
		}
	}

	private void setUpdateData(List<T> list) {
		Map<String, Object> updateData = loadUpdateData();
		for (T t : list) {
			for (Map.Entry<String, Object> entry : updateData.entrySet()) {
				ReflectUtils.setProperty(t, entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public boolean insert(T entity) {
		setInsertData(entity);
		return super.insert(entity);
	}

	@Override
	public boolean insertBatch(List<T> entityList) {
		setInsertData(entityList);
		return super.insertBatch(entityList);
	}

	@Override
	public boolean insertBatch(List<T> entityList, int batchSize) {
		setInsertData(entityList);
		return super.insertBatch(entityList, batchSize);
	}

	@Override
	public boolean updateById(T entity) {
		setUpdateData(entity);
		return super.updateById(entity);
	}

	@Override
	public boolean update(T entity, Wrapper<T> wrapper) {
		setUpdateData(entity);
		return super.update(entity, wrapper);
	}

	@Override
	public boolean updateBatchById(List<T> entityList) {
		setUpdateData(entityList);
		return super.updateBatchById(entityList);
	}

	@Override
	public T selectOne(T entity) {
		return selectOne(new EntityWrapper<T>(entity));
	}

}
