<#assign  modelNameVariable="${StringUtils.lowerCaseFirst('${table.entityName}')!}"/>
var ${modelNameVariable} = {
    function formatterAction(value, row, index) {
		var deleteAuth = $("#deleteAuth").val();
		if ("Y" == deleteAuth) {
			return "<a href='javascript:void(0);' onclick='delById(\"${modelNameVariable}/delete\","+row.id+");'>删除</a>";
		}
		return "";
	},
	initList : function initList() {
	    getGrid().datagrid({  
	        url:'getPsNewConsultList.action',  
	        fit:true,
            striped: true,  
            singleSelect : true,  
            pagination: true,  
            rownumbers: true,    
            toolbar:"#toolbar", 
            columns:[[  
                <#list table.fields as column>
			    <#if column.columnType.type == "Date">
			        <#if !column_has_next>
			        {field:'${column.propertyName}',title: '${column.comment}',align: 'center', fit:true, formatter:dateFormatByEasyui},
			        <#else>
			         {field:'${column.propertyName}',title: '${column.comment}',align: 'center', fit:true, formatter:dateFormatByEasyui},  
			        </#if>
			    <#else>
			    <#if !column_has_next>
			        {field:'${column.propertyName}',title: '${column.comment}',align: 'center', fit:true},
			        <#else>
			         {field:'${column.propertyName}',title: '${column.comment}',align: 'center', fit:true},  
			        </#if>
			    </#if>
				</#list>
				{field:'_action',title: '操作',align: 'center', fit:true, formatter:${modelNameVariable}.formatterAction}
            ]]  
        });
	},
	initUpdate : function initUpdate() {
	   
	}
}