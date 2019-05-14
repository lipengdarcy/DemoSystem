$(function() {
	//行政区域单选
	$('#chooseArea').selectCity({});
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : home_url + '/system/param/area/areaData',
		datatype : "json",
		colModel : [ {
			label : '编号',
			name : 'id'
		}, {
			label : '名称',
			name : 'name'
		}, {
			label : '省',
			name : 'provinceName'
		}, {
			label : '市',
			name : 'cityName'
		}, {
			label : '区',
			name : 'areaName'
		}, {
			label : '镇',
			name : 'streetName'
		} ],
		viewrecords : true,// 是否要显示总记录数
		autowidth : true,// 宽度自适应
		height : 'auto',// 高度自适应
		autoheight : true,// 高度自适应
		rownumbers : true,// 显示序号
		rownumWidth : 60,// 序号列宽度
		// 每页显示记录
		rowNum : 10,
		rowList : [ 10, 20, 30, 40 ],
		pager : "#jqGridPager",
	});

}

function searchForm() {
	var provinceId = $("#chooseArea").find("select:eq(0)").val();
	var cityId = $("#chooseArea").find("select:eq(1)").val();
	var areaId = $("#chooseArea").find("select:eq(2)").val();
	$("#searchForm input[name=provinceId").val(provinceId);
	$("#searchForm input[name=cityId").val(cityId);
	$("#searchForm input[name=areaId").val(areaId);

	// 传入查询条件参数
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1, // 每次提出新的查询都转到第一页
		postData : $('#searchForm').serializeObject(),
	}).trigger("reloadGrid");
}