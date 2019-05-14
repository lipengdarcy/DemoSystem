$(window).resize(function() {
	initLayout();
});

$(function() {
	jqgridInit();
});

/**
 * 在窗口缩放的时候，我们为jqGrid重新绘制宽度，使其自适应于bootstrap的响应式布局。
 * 使用的方法就是jqGrid的setGridWidth方法。
 * */
function initLayout() {
	$("table[rel=jqgridForm]").each(function() {
		var rel = $(this).attr("rel");
		if (rel) {
			var $form = $("#" + rel);
			var tableWidth = $form.width();
			$(this).setGridWidth(tableWidth, true);
		}
	});
}

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/system/role/listData',
		datatype : "json",
		colModel : [ {
			label : 'id',
			name : 'id',
			hidden : true
		}, {
			label : '名称',
			name : 'roleName'
		}, {
			label : '编码',
			name : 'roleCode',
		}, {
			label : '描述',
			name : 'roleDesc'
		}, {
			label : '操作',
			name : 'option',
			formatter : optFormatter
		} ],
		viewrecords : true,// 是否要显示总记录数
		autowidth : true,// 宽度自适应
		height : 'auto',// 高度自适应
		autoheight : true,// 高度自适应
		rownumbers : true,// 显示序号
		rownumWidth : 40,// 序号列宽度
		// 每页显示记录
		rowNum : 10,
		rowList : [ 10, 20, 30, 40 ],
		pager : "#jqGridPager",
	});

}

function optFormatter(cellvalue, options, rowObject) {
	var params = new Array(3);
	params[0] = "&quot" + rowObject.roleName + "&quot";
	params[1] = rowObject.isSys;
	params[2] = "&quot" + rowObject.desc + "&quot";
	var viewBtn = "<a href='javascript:;' class='blueT border IconjQ' title='查看' onclick='view("
			+ params[0] + "," + params[1] + "," + params[2] + ")'>查看</a>";

	if (!rowObject.isSys) {
		var editBtn = "<a class='pl10 cyan border IconjQ' title='编辑' onclick='edit("
				+ rowObject.id + ")'>编辑</a>";
		var delBtn = "<a class='red border IconjQ' title='删除' onclick='del("
				+ rowObject.id + ")' >删除</a>";
		return viewBtn + editBtn + delBtn;
	}

	return viewBtn;
}

function view(roleName, isSys, desc) {
	$("#roleName").text(roleName);
	$("#desc").text(desc);
	if (isSys) {
		$("#editBtn").hide();
	}
	var myDialog = dialog({
		title : '角色查看',
		width : '520',
		height : '150',
		content : $("#viewPage"),
		lock : true

	});
	myDialog.showModal();
}

function searchForm() {
	// 传入查询条件参数
	$("#jqGrid").jqGrid("setGridParam", {
		page : 1, // 每次提出新的查询都转到第一页
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");

}

function reloadJqgrid() {
	searchForm();
}