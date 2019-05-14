<#macro input value> 
	<#if (_pageedit?? && (_pageedit=="1" || _pageedit==1)) || (RequestParameters._pageedit?? && (RequestParameters._pageedit=="1" || RequestParameters._pageedit==1))>
		<#nested>
	<#else>
	${value}
	</#if> 
</#macro>

<#macro input3 name,value,placeholder,class,type, id, other>
</#macro>
<#macro input2 name,value,type, other>
	<#if (_pageedit?? && (_pageedit=="1" || _pageedit==1)) || (RequestParameters._pageedit?? && (RequestParameters._pageedit=="1" || RequestParameters._pageedit==1))>
		<#assign value_temp="">
		<#if value?? && value!="">
			<#assign value_temp="value='${value!}'">
		</#if>
		<input name="${name}" type="${type!'text'}" ${value_temp} ${other} />
	<#else>
	${value}
	</#if> 
</#macro>

<#macro select name,value,opts,class> 
	<#if (_pageedit?? && (_pageedit=="1" || _pageedit==1)) || (RequestParameters._pageedit?? && (RequestParameters._pageedit=="1" || RequestParameters._pageedit==1))>
		<select class="${class}" name="${name}" id="${id}">
			<#list opts?split(",") as opt>
				<#assign valueField="${opt?split(':')[0]}">
				<#assign disField="${opt?split(':')[1]}">
				<option <#if value==valueField>selected="selected"</#if> value="${valueField}">${disField}</option>
			</#list>
		</select>
	<#else>
			<#assign div_text="">
			<#list opts?split(",") as opt>
				<#assign valueField="${opt?split(':')[0]}">
				<#assign disField="${opt?split(':')[1]}">
				<#if value==valueField>
					<#assign div_text="${disField}">
				</#if>
			</#list>
			${div_text}
	</#if> 
</#macro> 

