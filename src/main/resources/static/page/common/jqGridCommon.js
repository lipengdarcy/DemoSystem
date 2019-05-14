/**
 * jqGrid 的通用方法，如搜索、编辑、查看、删除等
 */

var jqTool = {};

// 搜索
jqTool.query = function() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}

function reloadJqgrid() {
	searchForm();
}

function optFormatter(cellvalue, options, rowObject) {
	var viewBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='查看' onclick='view("
			+ rowObject.id + ")'><i class='ion-eye'></i></a></div>";
	var editBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='编辑' onclick='edit("
			+ rowObject.id + ")'><i class='ion-edit'></i></a></div>";
	var delBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='删除' onclick='del("
			+ rowObject.id + ")'><i class='ion-trash-a'></i></a></div>";

	return viewBtn + editBtn + delBtn;
}




