<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %> <#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${beanName}')!}"/>
<%
request.setAttribute("namespace", "${modelNameVariable}");
%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/common/common.jsp" %>
</head>
<body>
    <kisso:hasPermission name="${modelNameVariable}_delete">
         <input type="hidden" id="deleteAuth" value="Y"/>
    </kisso:hasPermission>
    
    <!-- 数据展示列表查询区 -->
	<div id="toolbar" style="padding: 1px; height: auto">
		<div style="margin-bottom: 1px;" id="${r'${namespace }'}SearchDiv"
			class="tab">
			<form action="#" name="searchForm" id="searchForm"
				style="display: inline;">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					style="border: 1px solid #52B64C;">
					<tr>
						<td width="13%" class="bule">名称：</td>
						<td>
						<input style="width: 120px" name="name"  class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']"/>
						</td>
						<td align="center" colspan="2"><a href="javascript:void(0);"
							class="easyui-linkbutton" data-options="iconCls:'icon-search'"
							onclick="searchData();">查询</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-reload'"
							onclick="$('#${r'${namespace }'}SearchDiv').find('#searchForm').form('reset');">清空</a>
						</td>
					</tr>
				</table>
			</form>
			<kisso:hasPermission name="${modelNameVariable}_add">
				<a href="javascript:void(0);" onclick="add('${modelNameVariable}/toAdd','添加',400,420);" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="添加">添加</a>
			</kisso:hasPermission>
			<kisso:hasPermission name="${modelNameVariable}_update">
				<a href="javascript:void(0);" onclick="update('${modelNameVariable}/toUpdate','修改',400,420);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="修改">修改</a>
			</kisso:hasPermission>
		</div>
	</div>
	
	<table class="easyui-datagrid" fit="true" <kisso:hasPermission name="${modelNameVariable}_find"> url="<c:url value="/${modelNameVariable}/find"> </c:url>" </kisso:hasPermission> id="${r'${namespace }'}Grid"  title="数据列表" 
	     data-options="<kisso:hasPermission name="${modelNameVariable}_update">onDblClickCell: function(index,field,value){update('${modelNameVariable}/toUpdate','修改',400,420);}</kisso:hasPermission>" 
	     singleSelect="true" rownumbers="true" pagination="true" toolbar="#toolbar">
		<thead>
			<tr>
			    <#list columns as column>
			    <#if column.className == "Date">
			    <th data-options="field:'${column.propertyName}',fit:true" formatter=dateFormatByEasyui>${column.remarks}</th>
			    <#else>
			    <th data-options="field:'${column.propertyName}',fit:true">${column.remarks}</th>
			    </#if>
				</#list>
				<th data-options="field:'action',fit:true" formatter="formatterAction">操作</th>
			</tr>
		</thead>
	</table>
	 
	<script type="text/javascript">
	    setNamespace("${r'${namespace }'}");
		function formatterAction(value, row, index) {
			var deleteAuth = $("#deleteAuth").val();
			if ("Y" == deleteAuth) {
				return "<a href='javascript:void(0);' onclick='delById(\"${modelNameVariable}/delete\","+row.id+");'>删除</a>";
			}
			return "";
		}
	</script>
</body>
</html>