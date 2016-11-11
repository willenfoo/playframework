package ${package};

<#if !dubboRegistryId??>
import org.springframework.stereotype.Service;
</#if>
<#if dubboRegistryId??>
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
</#if>

import com.baomidou.framework.service.impl.ServiceImpl;
import ${servicePackage}.${serviceName};
import ${modelPackage}.${beanName};
import ${mapperPackage}.${mapperName};
/**
 *
 * ${beanName} 表数据服务层接口实现类
 *
 */
<#if !dubboRegistryId??>
@Service
</#if>
<#if dubboRegistryId??>
@Component  
@Service
</#if>
public class ${serviceImplName} extends ServiceImpl<${mapperName}, ${beanName}> implements ${serviceName} {


}


