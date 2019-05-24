$(function() {
	var grid_selector = "#jqGrid";
	var pager_selector = "#jqGridPager";

	$(grid_selector)
			.jqGrid(
					{
						// url : '/mongo/hotel/getData', //mysq：很慢
						url : '/mongo/hotel/getMongoData',// mongodb：飞快
						datatype : "json",
						colNames : [ '姓名', '性别', '生日', '身份证', '手机', '邮箱',
								'入住时间', '住址' ],
						colModel : [
								{
									name : "name"
								},
								{
									name : "gender",
									formatter : function(cellvalue, options,
											rowObject) {
										if (cellvalue == 'F')
											return "女";
										else
											return "男";
									}
								},
								{
									name : "birthday"
								},
								{
									name : "ctfid"
								},
								{
									name : "mobile"
								},
								{
									name : "email"
								},
								{
									name : "version",
									formatter : function(cellvalue, options,
											rowObject) {
										if (cellvalue == undefined)
											return "";
										return moment(new Date(cellvalue))
												.format("YYYY-MM-DD hh:mm:ss");
									}
								}, {
									name : "address"
								} ],

						viewrecords : true,
						autowidth : true, // 宽度自适应
						height : 'auto', // 高度自适应
						rownumbers : true, // 显示序号
						rownumWidth : 60,// 序号列宽度
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : pager_selector
					});

});


//查询
function searchForm() {
	$("#jqGrid").jqGrid("setGridParam", {
		search : true,
		page : 1,
		postData : $("#searchForm").serializeObject(),
	}).trigger("reloadGrid");
}
