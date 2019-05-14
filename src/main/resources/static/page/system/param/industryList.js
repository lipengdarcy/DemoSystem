$(function() {
	jqgridInit();	
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		treeGrid : true,// 启用树型Grid功能
		treeGridModel : "adjacency",// 表示返回数据的读取类型，分为两种：nested和adjacency,默认值：nested
		ExpandColumn : "record.id",// 树型结构在哪列显示
		ExpandColClick: false,
		pager : "false", //不分页
		//loadonce : true,
		jsonReader : {
			root : "rows",
			total : "total",
			repeatitems : true
		},
		treeReader : {
			parent_id_field : "record.pid",
			level_field : "record.level",
			leaf_field : "isLeaf",
			expanded_field : "expanded",
			loaded : "loaded",
			//icon_field : "icon1"
		},

		url : home_url + '/system/param/industry/industryData',
		datatype : "json",
		//datatype : 'jsonstring',
		//datastr : data,
		//treedatatype : "local",
		colModel : [ {
			label : '分类编号',
			name : 'record.id',
			formatter : function(cellvalue, options, rowObject) {
				return cellvalue + "";
			}
		}, {
			label : '上级编号',
			name : 'record.pid',
			formatter : function(cellvalue, options, rowObject) {
				return cellvalue + "";
			}
		}, {
			label : '分类名称',
			name : 'record.name'
		}, {
			label : '分类等级',
			name : 'record.level'
		} ],
		autowidth : true,// 宽度自适应
		height : 'auto',// 高度自适应
		//rownumbers : true,// 显示序号
		rowNum : "-1", // 显示全部记录
		shrinkToFit : false
	// 控制水平滚动条
	});

}

function searchForm() {
	// 传入查询条件参数
	$("#jqGrid").jqGrid("setGridParam", {
		url : home_url + '/system/param/industry/industryData',
		datatype : "json",
		search : true,
		page : 1, // 每次提出新的查询都转到第一页
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}