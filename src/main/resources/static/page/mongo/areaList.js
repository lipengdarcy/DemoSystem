$(function() {
	var grid_selector = "#jqGrid";
	var pager_selector = "#jqGridPager";

	$(grid_selector).jqGrid({
		url : '/mongo/areaData',
		styleUI : 'Bootstrap',
		datatype : "json",
		colNames : [ '编号', '上级编号', '区域名称', '省', '市', '区','级别' ],
		colModel : [ {
			name : 'id'
		}, {
			name : "pid"
		}, {
			name : "name"
		}, {
			name : "provinceName"
		}, {
			name : "cityName"
		}, {
			name : "areaName"
		}, {
			name : "level"
		} ],

		viewrecords : true,
		autowidth : true, // 宽度自适应
		height : 'auto', // 高度自适应
		rownumbers : true, // 显示序号
		rownumWidth : 60,// 序号列宽度
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		//caption: "行政区域列表",
		pager : pager_selector
	});

});

// 查询
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}