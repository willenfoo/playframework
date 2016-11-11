package org.apache.playframework.service;

import java.util.Map;

public interface FieldFillService {
	public Map<String, Object> getInsertData();

	public Map<String, Object> getUpdateData();
}
