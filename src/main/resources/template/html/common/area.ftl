<#macro name id> 
	<@areaTag id="${id}">
		<a href="javascript:;">${areainfo.name}${(areainfo.isValid)?string('','(已删除)')?html}</a>
	</@areaTag> 
</#macro> 

<#macro names ids> 
	<#list ids?split(",") as id>
		<@areaTag id="${id}">
		<a href="javascript:;">${areainfo.name}${(areainfo.isValid)?string('','(已删除)')?html}</a>
		</@areaTag> 
		<#if id_has_next>,</#if>
	</#list>
</#macro>
<#macro fullname id,splitStr>
	<#assign splitStr_temp="">
	<#if splitStr??>
		<#assign splitStr_temp="${splitStr}">
	</#if>
	<@areaTag id="${id}">
		<#if areainfo.provinceName??>
			${(areainfo.provinceName)!}${splitStr_temp}${(areainfo.cityName)!}${splitStr_temp}${(areainfo.areaName)!}${splitStr_temp}${(areainfo.streetName)!}
		<#else>
			${(areainfo.shortName)!}${splitStr_temp}${(areainfo.name)!}
		</#if>
	</@areaTag>
</#macro>

<#macro view_citynames ids> 
	<#list ids?split(",") as id>
		<@areaTag id="${id}">
		<a href="javascript:;">${areainfo.getProvinceCityName()}${(areainfo.isValid)?string('','(已删除)')?html}</a>
		</@areaTag> 
		<#if id_has_next>,</#if>
	</#list>
</#macro>







