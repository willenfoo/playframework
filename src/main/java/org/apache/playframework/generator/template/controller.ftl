<#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${table.entityName}')!}"/>
package ${packageConfig.parent}.web.${StringUtils.replacePattern('${packageConfig.controller}', '/', '.')!};

import ${packageConfig.parent}.dto.${table.entityName}Dto;
import ${packageConfig.parent}.model.${table.entityName};
import ${packageConfig.parent}.service.${table.entityName}Service;

import org.apache.playframework.domain.EasyuiClientMessage;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * @author willenfoo
 */
@Controller
@RequestMapping("${modelNameVariable}s/")
public class ${table.entityName}Controller extends BaseController {

	private static String VIEWS_PATH = "${packageConfig.functionModuleName}/${modelNameVariable}/";

	@Autowired
	private ${table.entityName}Service ${modelNameVariable}Service; // 服务层

	/**
	 * 跳转到查询页面
	 * 
	 * @return
	 */
	@GetMapping("list")
	@Permission("${modelNameVariable}")
	public String list() {
		return VIEWS_PATH + "list";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("add")
	@Permission("${modelNameVariable}_add")
	public String add(ModelMap modelMap) {
		modelMap.put("${modelNameVariable}", new ${table.entityName}Dto());
		return VIEWS_PATH + "edit";
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("{id}/update")
	@Permission("${modelNameVariable}_update")
	public String update(@PathVariable(value = "id") Long id, ModelMap modelMap) {
		${table.entityName}Dto ${modelNameVariable}Dto = (${table.entityName}Dto)${modelNameVariable}Service.selectById(id);
		modelMap.put("${modelNameVariable}", ${modelNameVariable}Dto);
		return VIEWS_PATH + "edit";
	}

	/**
	 * 查询数据
	 * 
	 * @param role
	 * @return
	 */
	@GetMapping
	@ResponseBody
	@Permission("${modelNameVariable}_find")
	public Object find(${table.entityName}Dto ${modelNameVariable}Dto) {
		EntityWrapper<${table.entityName}> wrapper = new EntityWrapper<${table.entityName}>(${modelNameVariable}Dto);
		Page<${table.entityName}> page = getEasyuiPage();
		return EasyuiClientMessage.success(${modelNameVariable}Service.selectPage(page, wrapper), ${table.entityName}Dto.class);
	}

	/**
	 * 添加
	 * 
	 * @param ${modelNameVariable}
	 * @return
	 */
	@PostMapping
	@ResponseBody
	@Permission("${modelNameVariable}_add")
	public Object add(${table.entityName} ${modelNameVariable}Dto, BindingResult br) {
		// 数据验证
		if (validate(${modelNameVariable}Dto, br)) {
			return getResult(${modelNameVariable}Service.insert(${modelNameVariable}Dto));
		} else {
			return getFailResult(br.getFieldError().getDefaultMessage());
		}
	}

	/**
	 * 更新
	 * 
	 * @param ${modelNameVariable}
	 * @return
	 */
	@PutMapping("{id}")
	@ResponseBody
	@Permission("${modelNameVariable}_update")
	public Object update(@PathVariable(value = "id") Long id, ${table.entityName}Dto ${modelNameVariable}Dto, BindingResult br) {
		// 数据验证
		if (validate(${modelNameVariable}Dto, br)) {
			return getResult(${modelNameVariable}Service.updateById(${modelNameVariable}Dto));
		} else {
			return getFailResult(br.getFieldError().getDefaultMessage());
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	@Permission("${modelNameVariable}_delete")
	public Object delete(@PathVariable(value = "id") Long id) {
		${table.entityName}Dto ${modelNameVariable}Dto = new ${table.entityName}Dto();
		${modelNameVariable}Dto.setId(id);
		${modelNameVariable}Dto.setDeleteFlag("Y");
		return getResult(${modelNameVariable}Service.updateById(${modelNameVariable}Dto));
	}

}