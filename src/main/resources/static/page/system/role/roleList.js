$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/system/role/listData',
		styleUI : 'Bootstrap',
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
		},  {
			label : '更新时间',
			name : 'updateTime'
		}, {
			label : '操作',
			name : 'option',
			formatter : optFormatter
		} ],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		//autoheight : true,// 高度自适应
		autowidth : true,// 宽度自适应
		rownumbers : true,// 显示序号
		rownumWidth : 40,// 序号列宽度
		// 每页显示记录
		rowNum : 10,
		rowList : [ 10, 20, 30, 40 ],
		pager : "#jqGridPager",
	});

}

function optFormatter(cellvalue, options, rowObject) {
	var viewBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='查看' onclick='view(\""
			+ rowObject.id + "\")'><i class='ion-eye'></i></a></div>";
	var editBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='编辑' onclick='edit(\""
			+ rowObject.id + "\")'><i class='ion-edit'></i></a></div>";
	var delBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='删除' onclick='del(\""
			+ rowObject.id + "\")'><i class='ion-trash-a'></i></a></div>";

	return viewBtn + editBtn + delBtn;
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

function view(id) {
	showDialogModal3("查看角色", "/system/role/view?id=" + id, "520", "auto");
}

function del(id) {
	var myDialog = dialog({
		title : '提示',
		width : 300,
		content : '确认删除？',
		lock : true,
		okValue : '确定',
		ok : function() {
			$.ajax({
				url : "/system/role/del?id=" + id,
				type : "POST",
				success : function(result) {
					if (result.code == -1) {
						alertDiag(result.message);
						return false;
					} else
						alertDiag(result.message, function() {
							reloadJqgrid();
						});
				}
			});
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});
	myDialog.showModal();
}

function delBatch() {
	// 获取选中行的IDs
	var ids = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
	var userIds = "";
	$(ids).each(function(index, item) {
		userIds += (item + ",");
	});
	if (userIds == "") {
		alertDiag("请选择要删除的行!");
		return false;
	}
	var myDialog = dialog({
		title : '提示',
		width : 300,
		content : $('#delByBatch'),
		lock : true,
		okValue : '确定',
		ok : function() {
			$.ajax({
				url : "/system/role/del?ids=" + userIds,
				type : "POST",
				success : function(result) {
					if (result.code == -1) {
						alertDiag(result.message);
						return false;
					} else
						alertDiag(result.message, function() {
							reloadJqgrid();
						});
				}
			});
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});
	myDialog.showModal();
}

function edit(id) {

	var myDialog = dialog({
		title : '编辑角色',
		width : 600,
		okValue : '确定',
		ok : function() {
			if (validEdit()) {
				$.ajax({
					url : "/system/role/edit",
					type : "POST",
					data : $('#editForm').serialize(),
					success : function(result) {
						if (result.code == -1) {
							alertDiag(result.message);
							return false;
						} else
							alertDiag(result.message, function() {
								reloadJqgrid();
							});
					}
				});
			}
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});

	$.ajax({
		url : "/system/role/edit?id=" + id,
		success : function(data) {
			myDialog.content(data);
		}
	});
	myDialog.showModal();

}



//新增表单验证
function validateForm() {
	return $("#addForm").validate({
		rules : {
			roleName : {
				required : true
			},
			roleCode : {
				required : true
			}
		}
	}).form();
};
//编辑表单验证
function validEdit() {
	return $("#editForm").validate({
		rules : {
			roleName : {
				required : true
			},
			roleCode : {
				required : true
			}
		}
	}).form();
}

function addRole() {
	var myDialog = dialog({
		title : '新增角色',
		width : 600,
		okValue : '确定',
		ok : function() {
			if (validateForm()) {
				$.ajax({
					url : "/system/role/add",
					type : "POST",
					data : $('#addForm').serialize(),
					success : function(result) {
						if (result.code == -1) {
							alertDiag(result.message);
							return false;
						} else
							alertDiag(result.message, function() {
								reloadJqgrid();
							});
					}
				});
			}
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});

	$.ajax({
		url : "/system/role/add",
		success : function(data) {
			myDialog.content(data);
		}
	});
	myDialog.showModal();
}