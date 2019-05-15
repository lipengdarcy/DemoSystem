$(function() {
	jqgridInit();
});

function jqgridInit() {
	$("#jqGrid").jqGrid({
		url : '/best/TnetOrderData',
		styleUI : 'Bootstrap',
		datatype : "json",
		colModel : [ {
			label : 'id',
			name : 'id',
			hidden : true
		}, {
			label : '公司名称',
			name : 'companyName'
		}, {
			label : '已提交',
			name : 'commitCount'
		}, {
			label : '已完成',
			name : 'finishCount'
		}, {
			label : '已计费',
			name : 'jfCount'
		}, {
			label : '已出账',
			name : 'czCount'
		}, {
			label : '移动端活动上报',
			name : 'mobileCount'
		},{
			label : '下游合作伙伴',
			name : 'downPartnerCount'
		} ],
		viewrecords : true,// 是否要显示总记录数
		height : 'auto',// 高度自适应
		autowidth : true,// 宽度自适应
		rownumbers : true,// 显示序号
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
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