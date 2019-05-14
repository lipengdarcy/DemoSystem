$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/system/user/listData',
		styleUI : 'Bootstrap',
		datatype : "json",
		// 复选框
		multiselect : true,
		colModel : [ {
			label : 'id',
			name : 'id',
			hidden : true
		}, {
			label : '用户名',
			name : 'userName'
		}, {
			label : '姓名',
			name : 'realName'
		}, {
			label : '工号',
			name : 'staffNo'
		}, {
			label : '部门',
			name : 'deptName'
		}, {
			label : '岗位状态',
			name : 'dutyState'
		}, {
			label : '手机号',
			name : 'tel'
		}, {
			label : '角色',
			name : 'roleNames'
		}, {
			label : '操作',
			name : 'option',
			formatter : optFormatter
		} ],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		autoheight : true,// 高度自适应
		rownumbers : true,// 显示序号
		// 每页显示记录
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

function reloadJqgrid() {
	searchForm();
}

function optFormatter(cellvalue, options, rowObject) {
	var viewBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='查看' onclick='view(" + rowObject.id
			+ ")'><i class='ion-eye'></i></a></div>";
	var editBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='编辑' onclick='edit(" + rowObject.id
			+ ")'><i class='ion-edit'></i></a></div>";
	var delBtn = "<div class='col-md-2 col-sm-3 col-xs-4 text-center'><a title='删除' onclick='del(" + rowObject.id
			+ ")'><i class='ion-trash-a'></i></a></div>";

	return viewBtn + editBtn + delBtn;
}

function view(id) {
	showDialogModal3("查看用户", "/system/user/viewUser?id=" + id, "520", "auto");
}

function del(id) {
	var myDialog = dialog({
		title : '提示',
		width : 300,
		content : $('#delOne'),
		lock : true,
		okValue : '确定',
		ok : function() {
			$.ajax({
				url : "/system/user/del?ids=" + id,
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
				url : "/system/user/del?ids=" + userIds,
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

	myDialog = showDialogModal("编辑用户", "/system/user/editUser?id=" + id, "520",
			"auto", function() {
				submitForm("#editForm", "/system/user/edit", null, function(
						data) {
					_contentLoadTriggered = false;

					if (data.code == -1) {
						alertDiag(data.message);
						return false;
					}

					alertDiag(data.message, function() {
						reloadJqgrid();
						view(id);
						myDialog.close().remove();
					});
				}, 'json');
				return false;
			});

}
function addUser() {
	myDialog1 = showDialogModal("新增用户", +"/system/user/addUser", "520", "auto",
			function() {
				submitForm("#addForm", +"/system/user/add", null,
						function(data) {
							_contentLoadTriggered = false;
							if (data.code == -1) {
								alertDiag(data.message);
								return false;
							}

							alertDiag(data.message, function() {
								reloadJqgrid();
								view(data.data);
								myDialog1.close().remove();
							});

						}, 'json');
				return false;
			});

}
