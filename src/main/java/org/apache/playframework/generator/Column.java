package org.apache.playframework.generator;

 

/**
 * 数据库列的 javabean
 * @author Administrator
 *
 */
public class Column {

	private String name; //名称
	
	private String remarks; //备注
	
	private String className; //对应的java类name
	
	private String isPrimaryKey;
	
	private String propertyName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	 
	
}
