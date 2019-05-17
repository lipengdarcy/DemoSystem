$(function() {

	search();

});

// 搜索
function search() {

	$.ajax({
		url : 'http://localhost:9200/darcyjob/_search?q=content:'+$('#global_keyword').val(),
		type : 'post',
		dataType : 'json',
		success : function(data) {
			processData(data);
		}
	});
}

function processData(data) {
	var count = data.hits.total.value;
	var array = data.hits.hits;
	for (var i = 0; i < array.length; i++) {
		var cell = array[i];
		// 1.文件名
		var name = cell._source.file.filename;
		// 2.路径
		var link = cell._source.file.url;
		// 3.内容
		var content = cell._source.content;

		$('#resultbox').append(showData(name, link, content));
	}
}

function showData(title, link, content) {
	// 内容的最大长度
	var demoText = 'Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec ullamcorper nulla non metus auctor fringilla.';
	if (content.length > demoText.length) {
		content = content.substr(0, demoText.length) + '...';
	}
	var item = '<div class="search-item"><h3><a href="#">' + title
			+ '</a></h3><a class="search-link" href="javascript:void(0);">'
			+ link + '</a><p>' + content + '</p></div>';
	return item;
}
