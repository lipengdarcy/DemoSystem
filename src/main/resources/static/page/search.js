$(function() {

	//search();
	
	//1.创建client：
	var client = new $.es.Client({
	    hosts: 'http://localhost:9200'
	});
	//2.测试client是否创建成功：
	client.ping({
	    requestTimeout: 30000,
	}, function (error) {
	    if (error) {
	        console.error('elasticsearch cluster is down!');
	    } else {
	        console.log('All is well');
	    }
	});
	//2.搜索
	client.search({
	    index: 'darcyjob',
	    body: {
	        query: {
	        	match: {
	        		content : getQueryString('keyword')
	            }
	        }      	    	
	    	/*highlight:{
	    		pre_tags: "<b class='key' style='color:red'>",
	    		post_tags : "</b>",
	    		fields:{
	    			content : ""
	    		}
	    	}    	*/
	    }
	}).then(function (resp) {
		processData(resp);
	}, function (err) {
	    console.trace(err.message);
	});

});

//获取查询参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURIComponent(r[2]);
	return null;
}

// 搜索
function search() {
	

	$.ajax({
		//url : 'http://localhost:9200/darcyjob/_search?q=content:'+ getQueryString('keyword'),
		url : 'http://localhost:9200/darcyjob/_search',
		data : param,
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
			+ '</a></h3><a class="search-link" href="' + link + '">' + link
			+ '</a><p>' + content + '</p></div>';
	return item;
}
