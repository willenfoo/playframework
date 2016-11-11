package ${targetPackage};

import java.util.Date;
import java.io.Serializable;
<#assign  modelName="${dtoName}"/>
<#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${dtoName}')!}"/>
public class ${modelName}  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -${StringUtils.random(12)!}L;
<#list columns as column>
   /**
     * ${column.remarks}
     */
    private ${column.className} ${column.propertyName};
    
</#list>

<#list columns as column>
    public ${column.className} get${StringUtils.upperCaseFirst('${column.propertyName}')!}() {
        return ${column.propertyName};
    }

    public void set${StringUtils.upperCaseFirst('${column.propertyName}')!}(${column.className} ${column.propertyName}) {
        <#if column.className == "String">
        this.${column.propertyName} = ${column.propertyName} == null ? null : ${column.propertyName}.trim();       
        <#else>
        this.${column.propertyName} = ${column.propertyName};
        </#if>
    }
    
</#list>
    
	/**
     * 数据库一些默认值初始化，方便批量插入用
     * @return
     */
    public static ${modelName} initDefaultValue() {
		${modelName} ${modelNameVariable} = new ${modelName}();
		return ${modelNameVariable};
	}
}