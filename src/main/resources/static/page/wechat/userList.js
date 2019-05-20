$(function() {
	ztreeInit();
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid")
			.jqGrid(
					{
						url : '/wechat/user/userData',
						styleUI : 'Bootstrap',
						datatype : "json",
						colModel : [
								{
									label : 'id',
									name : 'id',
									hidden : true
								},
								{
									label : '微信id',
									name : 'userid'
								},
								{
									label : '姓名',
									name : 'name'
								},
								{
									label : '部门',
									name : 'deptName'
								},
								{
									label : '岗位',
									name : 'position'
								},
								{
									label : '手机号',
									name : 'mobile'
								},
								{
									label : '头像',
									name : 'avatar',
									formatter : function(cellvalue, options,
											rowObject) {
										var link = '<img height="50px;" width="50px;" src="'
												+ cellvalue
												+ '" alt="微信头像" title="微信头像">';
										return link;
									}
								}, {
									label : '操作',
									name : 'option',
									formatter : optFormatter
								} ],
						viewrecords : true,
						height : 'auto',
						autowidth : true,// 宽度自适应
						rownumbers : true,
						rownumWidth : 50,// 序号列宽度
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : "#jqGridPager",
					});

}

function ztreeInit() {
	var setting = {
		view : {
			selectedMulti : false
		},
		async : {
			enable : true,
			autoParam : [ "id" ],
			url : "/wechat/user/deptData",
			type : "GET"
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentid",
			}
		},
		callback : {
			onClick : clickSearch
		},
	};
	$.fn.zTree.init($("#treeDemo"), setting);
}

// 点击节点，查询该部门的人员
function clickSearch(event, treeId, treeNode) {
	$("#deptId").val(treeNode.id);
	searchForm();
}

// 点击搜索
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		url : "/wechat/user/userData",
		datatype : "json",
		search : true,
		page : 1, // 每次提出新的查询都转到第一页
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}

// 操作列格式化：只有查看
function optFormatter(cellvalue, options, rowObject) {
	var viewBtn = "<a href='javascript:;' class='blueT border IconjQ' title='查看' onclick='view("
			+ rowObject.id + ")'>查看</a>";
	return viewBtn;
}

function view(id) {
	showDialogModal3("查看用户", "/system/user/viewUser?id=" + id, "520", "auto");
}
