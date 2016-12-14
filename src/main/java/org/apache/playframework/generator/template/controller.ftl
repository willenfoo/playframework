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
	
	/**
	 *  查询数据
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	public Object find(${modelName} ${modelNameVariable}) {
		EntityWrapper<${modelName}> wrapper = new EntityWrapper<${modelName}>(${modelNameVariable});
		Page<${modelName}> page = getEasyuiPage();
		page = ${serviceNameVariable}.selectPage(page , wrapper);
		return EasyuiJsonResult.getSuccessResult(page.getTotal(), page.getRecords());
	}

	/**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	public String toFind() {
		return VIEWS_PATH+"find";
	}
	
    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
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
	public Object delete(@RequestParam("id") Long id) {
		Map<String, Object> resultMap;
		if (${serviceNameVariable}.deleteById(id)) {
		    resultMap = EasyuiJsonResult.getSuccessResult();
		} else {
		    resultMap = EasyuiJsonResult.getFailureResult();
		}
		return resultMap;
	} 
	
	<#if !dubboRegistryId??>
	@Resource
	private ${serviceName} ${serviceNameVariable}; //服务层
	</#if>  
	<#if dubboRegistryId??>
	@Reference(registry="${dubboRegistryId}")
	private ${serviceName} ${serviceNameVariable}; //服务层
	</#if> 

	private static String VIEWS_PATH = "${modelNameVariable}/";
	 
}