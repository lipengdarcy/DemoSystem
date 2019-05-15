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
		}, {
			label : '智能调度',
			name : 'smartCount'
		}, {
			label : '微信',
			name : 'weixinCount'
		}, {
			label : '上游合作伙伴',
			name : 'upPartnerCount'
		}, {
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

//搜索
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}
