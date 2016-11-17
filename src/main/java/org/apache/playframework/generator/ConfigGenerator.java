package org.apache.playframework.generator;

public class ConfigGenerator extends  org.apache.playframework.generator.mybatisplus.ConfigGenerator {

	protected String dtoPackage;
	
	protected String dtoName = "%sDto";
	
	protected boolean jspGenerator;

	protected String dubboRegistryId;
	
	public String getDtoPackage() {
		return dtoPackage;
	}

	public void setDtoPackage(String dtoPackage) {
		this.dtoPackage = dtoPackage;
	}

	public String getDtoName() {
		return dtoName;
	}

	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}

	public boolean isJspGenerator() {
		return jspGenerator;
	}

	public void setJspGenerator(boolean jspGenerator) {
		this.jspGenerator = jspGenerator;
	}

	public String getDubboRegistryId() {
		return dubboRegistryId;
	}

	public void setDubboRegistryId(String dubboRegistryId) {
		this.dubboRegistryId = dubboRegistryId;
	}
	
}
