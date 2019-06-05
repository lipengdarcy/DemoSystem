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
			label : '手机号',
			name : 'tel'
		}, {
			label : '角色',
			name : 'role',
			formatter : function(cellvalue, options, rowObject) {				
				var result = '';
				if(cellvalue){
					cellvalue.forEach(function(value, index, array) {
						result = result + value.roleName + ','
					});
				}				
				return result;
			}
		}, {
			label : '操作',
			name : 'option',
			formatter : optFormatter
		} ],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		autoheight : true,// 高度自适应
		autowidth : true,// 宽度自适应
		rownumbers : true,// 显示序号
		// 每页显示记录
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

function reloadJqgrid() {
	searchForm();
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

function view(id) {
	showDialogModal3("查看用户", "/system/user/viewUser?id=" + id, "520", "auto");
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
	var myDialog = dialog({
		title : '编辑用户',
		width : 600,
		okValue : '确定',
		ok : function() {
			// 角色
			var roleList = [];
			$('#roleList input:checkbox:checked').each(function() {
				var role = {
					id : $(this).val(),
					roleName : $(this).next().text()
				};
				roleList.push(role);
			});
			var data = {
				id : $('#editForm input[name=id]').val(),
				userName : $('#editForm input[name=userName]').val(),
				realName : $('#editForm input[name=realName]').val(),
				tel : $('#editForm input[name=tel]').val(),
				role : roleList
			};

			$.ajax({
				url : "/system/user/edit",
				type : "POST",
				// data : $('#editForm').serialize(),
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(data),
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

	$.ajax({
		url : "/system/user/editUser?id=" + id,
		success : function(data) {
			myDialog.content(data);
		}
	});
	myDialog.showModal();
}

// 表单验证
function validateForm() {
	return $("#addForm").validate({
		rules : {
			name : {
				required : true
			},
			tel : {
				required : true
			}
		}
	}).form();
};

function addUser() {
	var myDialog = dialog({
		title : '新增用户',
		width : 600,
		okValue : '确定',
		ok : function() {
			if (validateForm()) {
				// 角色
				var roleList = [];
				$('#roleList input:checkbox:checked').each(function() {
					var role = {
						id : $(this).val(),
						roleName : $(this).next().text()
					};
					roleList.push(role);
				});
				var data = {
					userName : $('#addForm input[name=userName]').val(),
					realName : $('#addForm input[name=realName]').val(),
					tel : $('#addForm input[name=tel]').val(),
					role : roleList
				};

				$.ajax({
					url : "/system/user/add",
					type : "POST",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(data),
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
		url : "/system/user/addUser",
		success : function(data) {
			myDialog.content(data);
		}
	});
	myDialog.showModal();
}
