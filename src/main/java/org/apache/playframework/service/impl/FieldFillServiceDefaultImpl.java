package org.apache.playframework.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.playframework.service.FieldFillService;

public class FieldFillServiceDefaultImpl implements  FieldFillService {

	@Override
	public Map<String, Object> getInsertData() {
		Map<String, Object> insertData = new HashMap<String, Object>();
		insertData.put("createDate", new Date());
		return insertData;
	}

	@Override
	public Map<String, Object> getUpdateData() {
		Map<String, Object> updateData = new HashMap<String, Object>();
		updateData.put("updateDate", new Date());
		return updateData;
	}

}
