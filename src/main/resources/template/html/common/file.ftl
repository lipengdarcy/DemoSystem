<#-- 附件名 -->
<#macro a_fileName id> 
	<@fileTag id="${id}">
		<a href="${file.fullpath!}">${file.fileName!}</a>
	</@fileTag> 
</#macro> 

<#macro a_fileNames ids> 
	<#list ids?split(",") as id>
		<@fileTag id="${id}">
		<a href="${file.fullpath!}">${file.fileName!}</a>
		</@fileTag> 
		<#if id_has_next>,</#if>
	</#list>
</#macro> 

<#macro li_file ids,fieldInput> 
	<#list ids?split(",") as id>
		<@fileTag id="${id}">
		<li class="fileItem" filecodeid="${file.id!c}">
			<div class="imgShow">
				<img src="${file.fullpath!}"> 
			</div> 
			<a class="fileName" href="${file.fullpath!}" target="_blank">${file.fileName!}</a>
			<div class="clearfix"></div> 
			<div class="status" onclick="$(this).li_remove();"><i class="icon-remove-sign"></i>	</div> 
			<input name="${fieldInput!}" type="hidden" value="${file.id!c}">
		</li>
		</@fileTag> 
		<#if id_has_next></#if>
	</#list>
</#macro> 

<#macro li_file_view ids> 
	<#list ids?split(",") as id>
		<@fileTag id="${id}">
		<li class="fileItem" filecodeid="${file.id!c}">
			<div class="imgShow">
				<img src="${file.fullpath!}"> 
			</div> 
			<a class="fileName" href="${file.fullpath!}" target="_blank">${file.fileName!}</a>
			<div class="clearfix"></div> 
		</li>
		</@fileTag> 
		<#if id_has_next></#if>
	</#list>
</#macro> 










