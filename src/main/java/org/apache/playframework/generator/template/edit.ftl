<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %><#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${beanName}')!}"/>
<div style="padding: 10px 0 10px 0px">  

    <%-- 设置 form 的action 地址，id为空，为添加的地址，否则为修改的地址 --%>
    <c:choose>
        <c:when test="${r'${empty'} ${modelNameVariable }.${r'id}' }"><c:set var="action" value="${modelNameVariable }/add"></c:set></c:when>
        <c:otherwise><c:set var="action" value="${modelNameVariable }/update"></c:set></c:otherwise>
    </c:choose>
	<form:form id="form" method="post" action="${r'${action }'}" modelAttribute="${modelNameVariable }" style="display: inline;" class="tab">
	    <form:hidden path="id"/>
		<table>
		<#list columns as column>
        <#if column.className == "String">
		   <tr>
				<td class="bule" align="right" width="35%"><span class="requiredField">*</span>${column.remarks}:</td>
				<td align="left">
				   <form:input path="${column.propertyName}" class="easyui-validatebox"  data-options="required:true,validType:['length[0,20]']"/>
				</td>
			</tr>
        <#elseif column.className == "Integer">
            <tr>
				<td class="bule" align="right" width="35%">${column.remarks}:</td>
				<td align="left">
				  <form:input path="${column.propertyName}" class="easyui-numberbox"  max="10000"   data-options="required:false"/> 
				</td>
			</tr>
        <#elseif column.className == "Long">
            <tr>
				<td class="bule" align="right" width="35%">${column.remarks}:</td>
				<td align="left">
				  <form:input path="${column.propertyName}" class="easyui-numberbox"  max="10000"   data-options="required:false"/> 
				</td>
			</tr>
        </#if>
		</#list>
		</table>
	</form:form>
	
	<div
		style="position: absolute; bottom: 0px; right: 0px; background-color: #F4F4F4; height: 40px; width: 100%; text-align: center;">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'" style="margin-top: 10px;" onclick="submitForm();">保存</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload'" style="margin-top: 10px;" onclick="resetForm();">重置</a>
	</div>
</div>