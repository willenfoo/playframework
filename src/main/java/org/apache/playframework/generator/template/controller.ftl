<#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${table.entityName}')!}"/>
package ${packageConfig.parent}.web.${StringUtils.replacePattern('${packageConfig.controller}', '/', '.')!};

import ${packageConfig.parent}.vo.${table.entityName}Vo;
import ${packageConfig.parent}.model.${table.entityName};
import ${packageConfig.parent}.service.${table.entityName}Service;

import org.apache.playframework.domain.EasyuiClientMessage;
import org.apache.playframework.web.controller.BaseAdminController;
import org.apache.playframework.mybatisplus.mapper.EntityWrapperBind;
import org.apache.playframework.mybatisplus.plugins.PageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author willenfoo
 */
@Controller
@RequestMapping("${modelNameVariable}/")
public class ${table.entityName}Controller extends BaseAdminController {

	private static String VIEWS_PATH = "${packageConfig.functionModuleName}/${modelNameVariable}/";

	@Autowired
	private ${table.entityName}Service ${modelNameVariable}Service; // 服务层

	/**
	 * 跳转到查询页面
	 * 
	 * @return
	 */
	@GetMapping("list")
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_list')")
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
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_add')")
	public String add(ModelMap modelMap) {
		modelMap.put("${modelNameVariable}", new ${table.entityName}Vo());
		return VIEWS_PATH + "edit";
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("update")
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_update')")
	public String update(@RequestParam(value = "id") Long id, ModelMap modelMap) {
		${table.entityName}Vo ${modelNameVariable}Vo = (${table.entityName}Vo)${modelNameVariable}Service.selectById(id);
		modelMap.put("${modelNameVariable}", ${modelNameVariable}Vo);
		return VIEWS_PATH + "edit";
	}

	/**
	 * 查询数据
	 * @param role
	 * @return
	 */
	@PostMapping
	@ResponseBody
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_list')")
	public Object list(${table.entityName}Vo ${modelNameVariable}Vo) {
		Page<${table.entityName}> page = getEasyuiPage();
        EntityWrapper<${table.entityName}> wrapper = EntityWrapperBind.bind(${table.entityName}.class, ${modelNameVariable}Vo);
		return success(${modelNameVariable}Service.selectPage(page, wrapper), ${table.entityName}Vo.class);
	}

	/**
	 * 添加
	 * 
	 * @param ${modelNameVariable}
	 * @return
	 */
	@PostMapping
	@ResponseBody
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_add')")
	public Object add(${table.entityName} ${modelNameVariable}Vo, BindingResult br) {
		// 数据验证
		if (validate(${modelNameVariable}Vo, br)) {
			return getResult(${modelNameVariable}Service.insert(${modelNameVariable}Vo));
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
	@PostMapping("update")
	@ResponseBody
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_update')")
	public Object update(${table.entityName}Vo ${modelNameVariable}Vo, BindingResult br) {
		// 数据验证
		if (validate(${modelNameVariable}Vo, br)) {
			return getResult(${modelNameVariable}Service.updateById(${modelNameVariable}Vo));
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
	@PostMapping("delete")
	@ResponseBody
	@PreAuthorize("hasPermission('${modelNameVariable}', '${modelNameVariable}_delete')")
	public Object delete(@RequestParam(value = "id") Long id) {
		${table.entityName}Vo ${modelNameVariable}Vo = new ${table.entityName}Vo();
		${modelNameVariable}Vo.setId(id);
		${modelNameVariable}Vo.setDeleteFlag("Y");
		return getResult(${modelNameVariable}Service.updateById(${modelNameVariable}Vo));
	}

}