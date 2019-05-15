$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/best/BestProjectData',
		styleUI : 'Bootstrap',
		datatype : "json",
		colModel : [ {
			label : 'id',
			name : 'id',
			hidden : true
		}, {
			label : '分公司',
			name : 'subCompany'
		}, {
			label : '项目名称',
			name : 'projectName'
		}, {
			label : '开始时间',
			name : 'beginTime'
		}, {
			label : '结束时间',
			name : 'endTime'
		}, {
			label : '销售经理',
			name : 'manager'
		},  {
			label : '会员费',
			name : 'memberFee'
		},  {
			label : '实际打款',
			name : 'actualFee'
		}, {
			label : '合同编码',
			name : 'contractNo'
		}, {
			label : '合同邮寄地址',
			name : 'mailAddress'
		}],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		autowidth : true,// 宽度自适应
		rownumbers : true,// 显示序号
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : "#jqGridPager",
	});

}

//搜索
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}
