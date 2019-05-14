<#-- 部门名 -->
<#macro deptName id> 
	<@deptTag id="${id}">
		<span>${deptinfo.deptName!}</span>
	</@deptTag> 
</#macro> 

<#macro deptName ids> 
	<#list ids?split(",") as id>
		<@deptTag id="${id}">
		<span>${deptinfo.deptName!}</span>
		</@deptTag> 
		<#if id_has_next>、</#if>
	</#list>
</#macro> 








