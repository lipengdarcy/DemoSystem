<#--测试宏 1-->
<#macro demo1 color,name> 
<font size="+2" color="${color}">Hello ${name}</font> 
</#macro> 
 
 
<#-- 用户姓名 -->
<#macro realName id> 
	<@userTag id="${id}">
		<a href="/base/user/${userinfo.id!c}">${userinfo.realName!}<#if (userinfo.isValid)??><#else>(已删除)</#if></a>
	</@userTag> 
</#macro> 

<#macro realNames ids> 
	<#list ids?split(",") as id>
		<@userTag id="${id}">
		<a href="/base/user/${userinfo.id!c}">${userinfo.realName!}<#if (userinfo.isValid)??><#else>(已删除)</#if></a>
		</@userTag> 
		<#if id_has_next>,</#if>
	</#list>
</#macro> 

<#--作用域变量-->
<#assign mail = "i@itboy.net">










