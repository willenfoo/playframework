<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %> <#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${table.entityName}')!}"/>
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
    <sec:accesscontrollist hasPermission="${modelNameVariable}_delete" domainObject="${modelNameVariable}">
         <input type="hidden" id="deleteAuth" value="Y"/>
    </sec:accesscontrollist>
    
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
						<td align="center" colspan="2">
							<sec:accesscontrollist hasPermission="${modelNameVariable}_delete" domainObject="${modelNameVariable}">
							  <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchData();">查询</a> &nbsp;&nbsp;&nbsp;&nbsp; 
							  <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="resetSearchForm();">清空</a>
							</sec:accesscontrollist>
						</td>
					</tr>
				</table>
			</form>
			<sec:accesscontrollist hasPermission="${modelNameVariable}_delete" domainObject="${modelNameVariable}">
				<a href="javascript:void(0);" onclick="add('${modelNameVariable}/toAdd','添加',400,420);" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="添加">添加</a>
			</sec:accesscontrollist>
			<sec:accesscontrollist hasPermission="${modelNameVariable}_delete" domainObject="${modelNameVariable}">
				<a href="javascript:void(0);" onclick="update('${modelNameVariable}/toUpdate','修改',400,420);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="修改">修改</a>
			</sec:accesscontrollist>
		</div>
	</div>
	
	<table class="easyui-datagrid" id="${r'${namespace }'}Grid" >
	</table>
	 
	<script type="text/javascript">
		$(document).ready(function() {
		    setNamespace("${r'${namespace }'}");
		    ${modelNameVariable}.initList();
		});
	</script>
</body>
</html>