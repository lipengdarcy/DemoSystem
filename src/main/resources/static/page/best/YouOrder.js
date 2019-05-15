$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/best/YouOrderData',
		styleUI : 'Bootstrap',
		datatype : "json",
		colModel : [ {
			label : 'id',
			name : 'id',
			hidden : true
		}, {
			label : '公司名称',
			name : 'domainName'
		}, {
			label : '使用时间(天)',
			name : 'useDayCount'
		}, {
			label : '总活跃量',
			name : 'activeCount'
		}, {
			label : '销售单量(总)',
			name : 'saleCountTolal'
		}, {
			label : '销售单量(小程序)',
			name : 'saleCountProgram'
		}, {
			label : '销售单总金额(元)',
			name : 'saleValue'
		}, {
			label : '采购单总金额(元)',
			name : 'purchaseValue'
		}, {
			label : '运单数量',
			name : 'orderCount'
		} ],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		autowidth : true,// 宽度自适应
		rownumbers : true,// 显示序号
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : "#jqGridPager",
	});

}

// 搜索
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}
