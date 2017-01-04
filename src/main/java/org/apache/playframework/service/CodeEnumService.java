package org.apache.playframework.service;

import java.util.Map;

public interface CodeEnumService {

	public String getKey();

	public Map<String, String> getData();

	public String getText(String code);

	public boolean isExist(String code);

}
