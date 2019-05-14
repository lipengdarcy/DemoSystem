//分页查询
function jumpPage(pageIndex) {
//	$("#pageNum").val(pageNum);
//	$("#form").submit();
	var _url = location.href;
 	if (_url.indexOf("#@")) {
	_url = _url.replace("#@", "");
	_url = _url.replace("#", "");
	} else if (_url.indexOf("#") != -1) {
	_url = _url.replace("#", "");
	}
	var _n = _url.lastIndexOf("?");
	if (_n == (-1))
		return _url + "?pageNum=" + pageIndex;
	var query = _url.substring(_n + 1);
	var pairs = query.split("&");
	var newQuery = "";
	for ( var i = 0; i < pairs.length; i++) {
		var pos = pairs[i].indexOf("=");
	if (pos == -1)
		continue;
	var argname = pairs[i].substring(0, pos);
	if (argname == "pageNum" || argname == "page")
		continue;
	newQuery = newQuery + pairs[i] + "&";
	}
	
	return _url.substring(0, _n + 1) + newQuery + "pageNum=" + pageIndex+"&page="+pageIndex;
}



function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNum").val("1");
	$("#form").submit();
}

function addNew(url) {
	window.location.href=url;
}

function gotoPage() {
 	var str = document.all.jumppage.value;
    var i;
    if(str==""){
    	 alert("请您输入正确的页号");
         return false;
    }
    for (i = 0; i < str.length; i++) {
       if ((str.charAt(i)<'0') || (str.charAt(i)>'9')) {
          alert("请您输入正确的页号");
          return false;
       }
    }
	str = parseInt(str);
	var lastpage = parseInt(document.all.lastpage.value);
	if( str > lastpage ) {
		str = lastpage ;
	}
/*	$("#pageNum").val(str);
	$("#form").submit();*/
	//jumpPage(str);
	location.assign(jumpPage(str));
}

function exportExcel(url) {
	window.location.href=url;
}

function confirmDel() {
	if(confirm("请确定是否删除?")) { 
		return true;
	} else {
		return false;
	} 
}

function bringback(id, name) {
	window.parent.callbackProcess(id,name);
}
