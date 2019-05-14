

$(document).ready(function(){
	dateRangePiker();
	jqgridInit();
});

function dateRangePiker(){
	$('.journel').daterangepicker({
		//singleDatePicker: true,//是否单个日历
		timePickerIncrement: 1,//time选择递增数
		format: 'YYYY-MM-DD HH:mm',//date/time格式
		minDate: '1870-01-01',//可选最早时间
		maxDate: '2100-12-30',//可选最迟时间
		timePicker12Hour: false,//是否12小时制
		timePicker24Hour: true, //时间制
		startDate: moment().subtract('days', 29),//起始时间
		endDate: moment(),//结束时间
		showDropdowns: true,//是否出现年月可选
		opens: 'right',
		buttonClasses: ['btn btn-default'],//按钮样式
		applyClass: 'btn-small btn-cyan',//应用按钮的样式
		cancelClass: 'btn-small',//完成按钮样式
		separator: ' 至 ',//间隔符
		timePicker:true,//可选中时分
		//showWeekNumbers: true,//是否显示第几周
	},function(start, end, label) {
		//$scope.start_time = start.format('YYYY-MM-DD h:mm A')
	});
}

function jqgridInit(){
	$("#jqGrid").jqGrid({
		url: home_url+'/system/log/listData',
		editurl: 'clientArray',
		datatype: "json",
		//复选框
		//multiselect : true,
		
		colModel: [
			{label: 'id',name: 'id',width: 110,hidden:true},
			{label: '类型',name: 'logTypeName',width: 110},
			{label: '账号',name: 'userName',width: 100},
			{label: '姓名',name: 'userFullname',width: 100},
			{label: '操作时间',name: 'optDateTime',width: 150},
			{label: 'ip',name: 'requestIp',width: 150},
			{label: '内容',name: 'desc',width: 150}
			
		],
		
		//sortable:'boolean',//是否可排序
		sortname: 'name',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
		sortorder : 'asc',//排序顺序，升序或者降序（asc or desc）
//		loadonce: true,//如果为ture则数据只从服务器端抓取一次，之后所有操作都是在客户端执行，翻页功能会被禁用
		viewrecords: true,//是否要显示总记录数
		// 表格标题
		//caption : "历次职业健康检查情况",
		autowidth : true,//宽度自适应
		height : 'auto',//高度自适应
		autoheight: true,//高度自适应
		rownumbers : true,//显示序号
		rownumWidth: 40,//序号列宽度				
		//每页显示记录
		rowNum : 10,
		rowList : [ 10, 20, 30, 40 ],
		pager: "#jqGridPager",
	});
	
}

function searchForm(){
	//传入查询条件参数
	$("#jqGrid").jqGrid("setGridParam",{
		url:home_url+'/system/log/listData',
		datatype:"json",
		search:true,
		page:1,     //每次提出新的查询都转到第一页
		postData:$("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}