$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : home_url + '/system/param/listData',
		datatype : "json",
		colModel : [ {
			label : '参数名称',
			name : 'typeValue'
		}, {
			label : '参数编码',
			name : 'typeCode'
		}, {
			label : '操作',
			name : 'id',
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
	var viewBtn = "<a href='javascript:;' class='blueT border IconjQ' title='查看' onclick='view("
			+ rowObject.id + ")'>查看</a>";
	var editBtn = "<a class='pl10 cyan border IconjQ' title='编辑' onclick='edit("
			+ rowObject.id + ")'>编辑</a>";
	if (cellvalue == 32 && $('#editPermission').val()==1)
		return viewBtn + editBtn;
	else
		return viewBtn;
}

function view(id) {
	location.href = home_url + "/system/param/viewParamType/" + id;
}

function edit(id) {

	var myDialog = dialog({
		title : '业务管理部审核时间设置',
		width : 450,
		content : $("#editDiv"),
		lock : true,
		okValue : '确定',
		ok : function() {
			var time = $('#audit_time').val();
			if(isNaN(time)){
				alertDiag("输入参数有误，必须输入有效的数字");
				return false;
			}
			$.ajax({
				url : home_url + "/system/param/editParam?paramValue="
						+ time,
				type : "POST",
				success : function(result) {
					alertDiag(result.message);
				}
			});
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});
	myDialog.showModal();
}

function searchForm() {
	// 传入查询条件参数
	$("#jqGrid").jqGrid("setGridParam", {
		url : home_url + '/system/param/listData',
		datatype : "json",
		search : true,
		page : 1, // 每次提出新的查询都转到第一页
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}