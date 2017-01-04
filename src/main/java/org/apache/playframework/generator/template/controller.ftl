package ${package};

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.playframework.web.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.kisso.annotation.Permission;

<#assign  modelName="${beanName}"/>
<#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${beanName}')!}"/>
<#assign  serviceNameVariable="${StringUtils.lowerCaseFirst('${serviceName}')!}"/>

import ${modelPackage}.${beanName};
import ${servicePackage}.${serviceName};

<#if dubboRegistryId??>
import com.alibaba.dubbo.config.annotation.Reference;
</#if>
	
import org.apache.playframework.domain.EasyuiJsonResult;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("${modelNameVariable}/")
public class ${modelName}Controller extends BaseController {
	
	private static String VIEWS_PATH = "${modelNameVariable}/";
	
	<#if !dubboRegistryId??>
	@Resource
	private ${serviceName} ${serviceNameVariable}; //服务层
	</#if>  
	<#if dubboRegistryId??>
	@Reference(registry="${dubboRegistryId}")
	private ${serviceName} ${serviceNameVariable}; //服务层
	</#if> 

    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("${modelNameVariable}")
	public String toFind() {
		return VIEWS_PATH+"find";
	}
	
	/**
	 *  查询数据
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	@Permission("${modelNameVariable}_find")
	public Object find(${modelName} ${modelNameVariable}) {
		EntityWrapper<${modelName}> wrapper = new EntityWrapper<${modelName}>(${modelNameVariable});
		Page<${modelName}> page = getEasyuiPage();
		page = ${serviceNameVariable}.selectPage(page , wrapper);
		return EasyuiJsonResult.getSuccessResult(page.getTotal(), page.getRecords());
	}

    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("${modelNameVariable}_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("${modelNameVariable}", new ${modelName}());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("${modelNameVariable}_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    ${modelName} ${modelNameVariable} = ${serviceNameVariable}.selectById(id);
	    modelMap.put("${modelNameVariable}", ${modelNameVariable});
		return VIEWS_PATH+"edit";
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("${modelNameVariable}_add")
	public Object add(${modelName} ${modelNameVariable}) {
		Map<String, Object> resultMap;
		if (${serviceNameVariable}.insert(${modelNameVariable})) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("${modelNameVariable}_update")
	public Object update(${modelName} ${modelNameVariable}) {
		Map<String, Object> resultMap;
		if (${serviceNameVariable}.updateById(${modelNameVariable})) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("${modelNameVariable}_delete")
	public Object delete(@RequestParam("id") Long id) {
	    ${modelName} ${modelNameVariable} = new ${modelName}();
	    ${modelNameVariable}.setId(id);
	    ${modelNameVariable}.setIsDelete("Y");
		Map<String, Object> resultMap;
		if (${serviceNameVariable}.updateById(${modelNameVariable})) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	} 
	 
}