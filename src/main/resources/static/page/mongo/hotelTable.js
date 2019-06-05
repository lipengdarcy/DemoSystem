$(function() {

	$('#dataTable').DataTable({
		language : {
			"lengthMenu" : "每页 _MENU_ 条记录",
			"zeroRecords" : "没有找到记录",
			"info" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
			"infoEmpty" : "无记录",
			"infoFiltered" : "(从 _MAX_ 条记录过滤)"
		},
		ajax : {
			url : '/mongo/hotel/getMongo',
			data : function(d) {
				var param = $("#searchForm").serializeObject();
				param.draw = d.draw;
				param.start = d.start;
				param.length = d.length;
				param.search = d.search;
				return param;
			}
		},
		deferRender : true, // 延迟渲染，适用于数据较多的情况
		processing : true,// 刷新的那个对话框
		serverSide : true,// 服务器端获取数据
		paging : true,// 开启分页
		columns : [ {
			data : "name"
		}, {
			data : "gender",
			render : function(data, type, full, meta) {
				if (data == 'F')
					return "女";
				else
					return "男";
			}
		}, {
			data : "birthday",
			defaultContent : ""
		}, {
			data : "ctfid"
		}, {
			data : "mobile",
			defaultContent : ""
		}, {
			data : "email",
			defaultContent : ""
		}, {
			data : "version",
			defaultContent : ""
		}, {
			data : "address",
			defaultContent : ""
		} ]
	});

});

// 查询
function searchForm() {
	$('#dataTable').DataTable().draw();
}
