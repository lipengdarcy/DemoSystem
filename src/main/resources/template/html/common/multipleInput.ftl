<#-- 多个Input输入框 -->
<#macro ul_input keys, fieldInput> 
	<div class="input-group chomen weituo_list">
		<ul class='editcode'>
			<#if keys??  && keys != "">
			<#list keys?split(",") as key>
			<li>
				<span>${key!}</span>
				<a href="javascript:;" onclick="$(this).li_remove();">
					<input type="hidden" name="${fieldInput}" value="${key!}">
					<i class="icon-remove white"></i>
				</a>
			</li>
			</#list>
			</#if>
		</ul>
		<div class='position-re fl'>
			<input type="text" class='fl' style="width: 120px;" name="${fieldInput}" placeholder="请输入">
			<div class='clear'></div>
			<a href="javascript:;">
				<i class="icon-ok bigger-110 cyan position-ab" style="bottom:8px;right:-18px;" onclick='men_redio(this, "${fieldInput}")'></i>
			</a>
		</div>
	</div>
</#macro> 










