//建安js-core//
var SORT = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
     		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
var _backurl;
//异步请求页面，登录超时时，需要返回到登录页面
var _loginTimeout = "loginTimeout";

/**
 * 日期格式化
 * 
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function dateformatter(cellvalue, options, rowObject) {
	if (cellvalue != null
			&& cellvalue != undefined){
		return new Date(cellvalue).Format("yyyy-MM-dd");
		//formatDate(new Date(cellvalue));
	}
	return "";
}
/**
 * 日期时间格式化()
 * 
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function datetimeformatter(cellvalue, options, rowObject) {
	if (cellvalue != null
			&& cellvalue != undefined){
		return new Date(cellvalue).Format("yyyy-MM-dd h:m");
		//formatDate(new Date(cellvalue));
	}
	return "";
}

/**
 * 附件列表
 * <a href='' class='pr10' title='附件1'>附件1</a>
 * <a href='' class='pr10' title='附件2'>附件2</a>
 * <a href='' class='pr10' title='附件3'>附件3</a>
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function fileformatter(cellvalue, options, rowObject) {
	var info=""
	if(cellvalue!=null && jQuery.isArray( cellvalue )){
		$.each(cellvalue,function(index,value){
			info+="<a href='"+value.fullpath+"' class='pr10' title='"+value.fileName+"' target='_blank'>"+value.fileName+"</a>";
		});
		
	}else if(cellvalue!=null){
		info+="<a href='"+cellvalue.fullpath+"' class='pr10' title='"+cellvalue.fileName+"' target='_blank'>"+cellvalue.fileName+"</a>";
	}
	return info;
}





/**
*	重新加载jqgrid
**/	
function	jqGridRedraw(jqGrid_name,data, pageIndex, rowCount){
	if(pageIndex==undefined) pageIndex = 1;
	if(rowCount==undefined) rowCount = 10;
	data.page=pageIndex;
	data.rows=rowCount;
	//TODO 其他默认参数
	//console.info(data);
	//每次提出新的查询都转到第一页
    $(jqGrid_name).jqGrid("setGridParam",{  
    		page:pageIndex,
    		rows:rowCount,
            postData:data
        }).trigger("reloadGrid"); //重新载入  
}

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象 
    var obj = ev.target || ev.srcElement; //获取事件源 
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型 
    //获取作为判断条件的事件类型 
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况 
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
    //并且readOnly属性为true或disabled属性为true的，则退格键失效 
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
    var flag2 = ev.keyCode == 8 && !(t == "password" || t == "text" || t == "textarea");
    //判断 
    if (flag2 || flag1) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;


/**
 * jqGrid 编辑框
 * @param jqGrid   jqGrid的id
 * @param id  编辑数据的id
 * @param url 页面地址
 * @param okcallback 提交按钮回到
 * @param initcallback 页面加载后调用
 */
function jqGridModal(jqGrid ,id,url ,okcallback, initcallback){
	var content =  "";
	var properties ={top:true,jqModal:true,modal:true,url:"",viewPagerButtons:false,//是否显示上一条下一条
	beforeShowForm : function(e) {//加载数据之前的时候
		//获取修改设备参数页面
		var form = $(e[0]); 
		getSimplePage(url, function(data){
			form.html($.parseHTML( data, true ));
			initcallback();
		});
	},
	afterShowForm  : function(formid){//页面加载后
		
	},
	closeAfterEdit : true,//保存记录后关闭对话框
	beforeSubmit : okcallback ,//提交之前触发
	};
	
	jQuery("#" + jqGrid).editGridRow(id, properties );
}

/**
 * 确定提示框
 * @param okcallback
 */
function alertDiag(content, url , width, height){
	//验证页面元素是否存在
	if(width==undefined) width=320;
	if(height==undefined) height=60;
	
	var contenthtml = '<i class="jian-alertico jian-alert-xs jian-ico-alert"></i>' + content;
	var myDialog = dialog({
		title : '提示',
		width : width,
		height : height,
		content: contenthtml,	
		lock : true,
		okValue : '确定',
		ok : function(){
			if(url==undefined) return true;
			
			if(IsURL(url)){
				setTimeout(function(){
					window.location.href = url;
				},10);
			}else if(isFunction(url)){
				url();
			}
			
			return true;
		}
	});				

	myDialog.showModal();
}

/**
 * 显示对话框
 * @param title		标题
 * @param url		内容URL
 * @param okcallback	确定按钮回调
 * @param width		对话框宽度(默认320)
 * @param height	对话框高度(默认120)	
 */
function showDialogModal(title, url, width, height, okcallback, initcallback){
	if(width==undefined) width=320;
	if(height==undefined) height=120;
	
	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		lock : true,
		okValue : '确定',
		ok : okcallback,
		cancelValue : '取消',
		cancel : function() {
		}
	});
	
	//获取修改设备参数页面
	getSimplePage(url, function(data){
		myDialog.content(data);
		if (isFunction(initcallback)) {
			initcallback();
		}
	});
	
	myDialog.showModal();
	
	return myDialog;
}
/**
 * 显示对话框
 * @param title		标题
 * @param url		内容URL
 * @param okcallback	确定按钮回调
 * @param width		对话框宽度(默认320)
 * @param height	对话框高度(默认120)
 * @param cancelback 取消按钮回调
 */
function showDialogModalWithCancel(title, url, width, height, okcallback, cancelback){
	if(width==undefined) width=320;
	if(height==undefined) height=120;

	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		lock : true,
		okValue : '确定',
		ok : okcallback,
		cancelValue : '取消',
		cancel : cancelback
	});

	//获取修改设备参数页面
	getSimplePage(url, function(data){
		myDialog.content(data);
	});

	myDialog.showModal();

	return myDialog;
}
/**
 * 显示对话框
 * @param title		标题
 * @param url		内容URL
 * @param okcallback	确定按钮回调
 * @param width		对话框宽度(默认320)
 * @param height	对话框高度(默认120)	
 */
function showDialogModal2(title, url, okcallback,width,height,initCallback,okText,cancelText){
	if(width==undefined) width=320;
	if(height==undefined) height=120;
	if(okText == undefined || okText.lenght==0){
		okText = "确定";
	}
	if(cancelText == undefined || cancelText.length==0){
		cancelText="取消";
	}
	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		lock : true,
		okValue : okText,
		ok : function(){
			if (isFunction(okcallback)) {
				return okcallback();
			}
		},
		cancelValue : cancelText,
		cancel : function() {
		}
	});
	//获取修改设备参数页面
	getSimplePage(url, function(data){
		myDialog.content(data);
		if (isFunction(initCallback)) {
			initCallback();
		}
		disabledEnter();
	});
	
	myDialog.showModal();
	
	return myDialog;
}
function disabledEnter() {
	$("div .ui-dialog input").keydown(function(event) {
		if (event.keyCode == 13) {
			return false;
		}
	});
}
/**
 * 显示对话框
 * @param title		标题
 * @param url		内容URL
 * @param width		对话框宽度(默认320)
 * @param height	对话框高度(默认120)	
 */
function showDialogModal3(title, url,width,height){
	if(width==undefined) width=320;
	if(height==undefined) height=120;
	
	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		lock : true
	});
	
	//获取修改设备参数页面
	getSimplePage(url, function(data){
		myDialog.content(data);
	});
	
	myDialog.showModal();
	return myDialog;
}


/**
 * 显示对话框  (只有取消按钮)
 * @param title		标题
 * @param url		内容URL
 * @param width		对话框宽度(默认320)
 * @param height	对话框高度(默认120)	
 */
function showDialogModal4(title, url,width,height){
	if(width==undefined) width=320;
	if(height==undefined) height=120;
	
	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		lock : true,
		cancelValue : '取消',
		cancel : function() {
		}
	});
	
	//获取修改设备参数页面
	getSimplePage(url, function(data){
		myDialog.content(data);
	});
	
	myDialog.showModal();
	return myDialog;
}

/**
 * 只有取消按钮的对话提示框
 * @param okcallback
 */
function ConfirmDiag2(content, title ){
	if(title==null || title=="" || title==undefined){
		title = '提示';
	}
	var myDialog = dialog({
		title : title,
		content: content,	
		lock : true,
		cancelValue : '取消',
		cancel : function() {
		}
	});				

	myDialog.showModal();
	return myDialog;
}

/**
 * 通用删除对话提示框
 * @param okcallback
 */
function delConfirmDiag(okcallback, title, content){
	if(title==null || title=="" || title==undefined){
		title = '删除';
	}
	
	if(content==null || content=="" || content==undefined){
		content = '<i class="jian-alertico jian-alert-xs jian-ico-alert"></i>是否确定要删除?';
	}
	var myDialog = dialog({
		title : title,
		content: content,	
		lock : true,
		okValue : '确定',
		ok : okcallback,
		cancelValue : '取消',
		cancel : function() {
		}
	});				

	myDialog.showModal();
	return myDialog;
}

//预览图片元素
function bpreview(image){
	var src = "<img  src='" + $(image).attr("src") + "' >";
	var myDialog = dialog({
		title : "预览",
		content: src,	
		lock : true,
		width : "800",
		height : "600",
		cancelValue : '取消',
		cancel : function() {
		}
	});				

	myDialog.showModal();
	return myDialog;
}


// 刷新页面
function refreshPage(){
	window.location.reload(true);
}

//全选
function chkall($this, subchk){
	var st = $this.checked==true || $this.checked=='checked';
	setTimeout(function() {
		$(subchk).each(function() {
			if(st==true) {
				this.checked = true;
			}else{
				this.checked = false;
			}
		});  
	},10);
}

 // get 请求
 function get(url, param, success) {
 	ajax(url, param, "get", success, "html");
 }

//get 请求
 function get_json(url, param, success) {
 	ajax(url, param, "get", success, "json");
 }

 
 // post 请求
 function post(url, param, success) {
 	ajax(url, param, "post", success, 'json');
 }

 // 异步ajax提交
 function ajax(url, param, mt, success, dt) {
 	// ajax
	jQuery.ajax({
		type : mt,
		data : param,
		dataType : dt,
		url : url,
		cache : false,
		contentType: false,
		error : function(data, status, e) {
			if (data && data.statusText == "OK" && data.status == 200) {
				var data = json_parse(data.responseText);
				success(data);
				return;
			}
			//alert("Ajax错误! e="+e);
			alertDiag("异常！请重新尝试或者联系管理员! e="+e);
//			
//			$.jGrowl("异常！请重新尝试或者联系管理员!", {
//				header : "error"
//			});
			
		},
		success : success,
		processData : false
	}); // ajax end
 }

 // 异步提交form
 function submitForm(formId, url, beforeSubmit, success, dataType) {
	 if(dataType==undefined) dataType="html/text" ;
 		jQuery(formId).ajaxSubmit({
 			// dataType:'html/text',
 			dataType : dataType,
			type : 'post',
			// content-type: 'text/html',
			url : url,
			beforeSubmit : beforeSubmit,
			success : function(data){
				var isjson = data!=null && typeof(data) == "object" && Object.prototype.toString.call(data).toLowerCase() == "[object object]";
				if(isjson==false){
					try{
						data = json_parse(data);
					}catch(e){}
					
				}
				success(data);
			},
			error : function(data, status, e) {
				if (data && data.statusText == "OK" && data.status == 200) {
					success(data.responseText);
					return;
				}
				//alert("Ajax错误! e="+e);
				alertDiag("异常！请重新尝试或者联系管理员! e="+e);
//				$.jGrowl("异常！请重新尝试或者联系管理员!", {
//					header : "error"
//				});
				
			},
			uploadProgress : function(event, position, total, percentComplete) { // 请求进度
 			},
 			resetForm : false,
 			clearForm : false
 		});
 }

//读取单个页面
 function getSimplePage(url, $callback) {
 		get(url, null, function(data) {
 			if(data.indexOf(_loginTimeout) != -1) {  
                refreshPage();
                return;  
            }
 			
 			if (isFunction($callback)) {
 				$callback(data);
 			}
 		});
 }

 // Post读取单个页面
 function postSimplePage(url, $callback) {
 		post(url, null, function(data) {
 			if(data.indexOf(loginTimeout) != -1) {  
                refreshPage();
                return;  
            }
 			if (isFunction($callback)) {
 				$callback(data);
 			}
 		});
 }
 
 /**
  * 生成表单
  */
function createForm(action){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","_blank");
	form.attr("method","post");
	form.attr("action",action);
	$("body").append(form);
	//
	return form;
}
 
// 判断字符是否为空
function checkNull(str) {
 	if (str == "") return true;
 	var regu = "^[ ]+$";
 	var re = new RegExp(regu);
 	return re.test(str);
}

 /*
	 * 用途：检查输入手机号码是否正确 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
	 */  
 function checkMobile( s ){      
	 var regu =  /^1[0-9]{1}[0-9]{1}[0-9]{8}$/; 
	 var re = new RegExp(regu);   
	 if (re.test(s)) {   
 		return true;   
	 }else{   
 		return false;   
	 }   
 }

 // 匹配国内电话号码(0511-4405222 或 021-87888822)
 function checktell(str)
 {
     var result=str.match(/\d{3}-\d{8}|\d{4}-\d{7}/);
     if(result==null) return false;
     return true;
 }

 /*
	 * 用途：检查输入对象的值是否符合E-Mail格式 输入：str 输入的字符串 返回：如果通过验证返回true,否则返回false
	 */  
 function checkEmail(str) { 
 	var patrn=/^[a-zA-Z0-9_\-.]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
 	if (!patrn.exec(str)) {
    		return false;
    	}
   	return true;
 } 

 /*
	 * 用途：检查输入字符串是否符合正整数格式 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
	 */  
 function isNumber( str ){    
 	var patrn=/^[0-9]+$/;
 	if (!patrn.exec(str)) {
    		return false;
    	}
   	return true;
 } 

 // 判断是否是Function
 function isFunction(fn) {
 	return !!fn && !fn.nodeName && fn.constructor != String
 			&& fn.constructor != RegExp && fn.constructor != Array
 			&& /function/i.test(fn + "");
 }

 // 异步分页
 function ajaxPage(url, $callback) {
 	jQuery(window).bind("scroll",	function(event) {
	// 滚动条到网页头部的 高度，兼容ie,ff,chrome
	var top = document.documentElement.scrollTop
			+ document.body.scrollTop; // ??????
	// 网页的高度
	var textheight = $(document).height();
	// 网页高度-top-当前窗口高度 && 还未加载
	if (textheight - top - $(window).height() <= 100) {
		if (page >= parseInt(pageTotal)) {
			return; // 控制最大只能加载到100条
	}
	
	var dataParas = 'page=' + (parseInt(page) + 1); // 这里要直接使用JOSN作为webService参数
	post(url, dataParas, function(data) {
		if (data.data.hasNext == "true") {
		}
		if (data.errcode == "0") { //
 					page = (parseInt(page) + 1);
 				}

 				if (isFunction($callback)) {
 					$callback(data);
 				}

 			});
 		}
 	});
 }

 // 读取页面Html
 function getPage(url, $callback) {
 	jQuery(window).bind("scroll",			function(event) {
	// 滚动条到网页头部的 高度，兼容ie,ff,chrome
	var top = document.documentElement.scrollTop
			+ document.body.scrollTop; // ??????
	// 网页的高度
	var textheight = $(document).height();
	// 网页高度-top-当前窗口高度 && 还未加载
	if (textheight - top - $(window).height() <= 100) {
		if (page >= parseInt(pageTotal)) {
			return; // 控制最大只能加载到100条
	}
	jQuery(".displayNone").show();
	
	var dataParas = 'page=' + (parseInt(page) + 1); // 这里要直接使用JOSN作为webService参数
	 			post(url, dataParas, function(data) {
	 				if (isFunction($callback)) {
	 					$callback(data);
	 				}
	
	 				page = (parseInt(page) + 1);
	 			});
	 		}
	 	});
 }

 // 读取更多
 function getmore(url, $callback ){
	if (page >= parseInt(pageTotal)) {
		return; // 控制最大只能加载到100条
	jQuery(".displayNone").show();
	
	var dataParas = 'page=' + (parseInt(page) + 1); // 这里要直接使用JOSN作为webService参数
	 		post(url, dataParas, function(data) {
	 			page = (parseInt(page) + 1);
	 			if (isFunction($callback)) {
	 				$callback(data);
	 			}
	 		});
	 	}
 }

 
//项目委托编号新增，可通用
function men_redio(obj, fieldInput){
	var input=$(obj).parent().siblings('input')
	var value=$.trim(input.val())
	if(value==''){
		return 
	}
		var str='<li><span>'+value+'</span><a href="javascript:;" onclick="$(this).li_remove();">'
		str+='<input type="hidden" name="'+fieldInput+'" value="'+value+'">'
		str+='<i class="icon-remove white"></i></a></li>';
		
		$(obj).parent().parent().siblings('.editcode').append(str);
	input.val('')
}
 

 // 预览图片
 function PreviewImage(obj,prev, width, height){
 	var files = obj.files,
 	img = new Image();
 	if(window.URL){
 		// File API
		  // alert("window.URL===="+files[0].name + "," + files[0].size + " bytes");
		   img.src = window.URL.createObjectURL(files[0]); // 创建一个object URL，并不是你的本地路径
		   img.width = width;
		   img.height = height;
		   img.onload = function(e) {
		      window.URL.revokeObjectURL(this.src); // 图片加载后，释放object URL
		   }
		   jQuery(prev).append(img);
	}else if(window.FileReader){
		// opera不支持createObjectURL/revokeObjectURL方法。我们用FileReader对象来处理
	var reader = new FileReader();
	reader.onload = function(e){
		// alert("window.FileReader===="+files[0].name + "," +e.total + " bytes");
	img.src = this.result;
	img.src="data:application/octet-stream;"+this.result.substr(this.result.indexOf("base64,"));
	img.onload = function () { // binding onload event
		// TODO 加载预览
	}
	
	img.onerror=function(){
		// TODO 显示预览失败
			}
			img.width = width;
		    img.height = height;
			$(prev).append(img);
		}
		reader.readAsDataURL(files[0]);
	}else{
		// ie
		obj.select();
		obj.blur();
		var nfile = document.selection.createRange().text;
		document.selection.empty();
		img.src = nfile;
		img.width = width;
		img.height = height;
		img.onload=function(){
		      // alert("else===="+nfile+","+img.fileSize + " bytes");
		}
		$(prev).append(img);
		$(prev).css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='"+ nfile + "')");
// fileList.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src='"+nfile+"')";
 	}
 }

 // <!--把文件转换成可读URL-->
 function getObjectURL(file) {
 	var url = null;
 	if (window.createObjectURL != undefined) { //  basic
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { //  mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { //  webkit or chrome
 		url = window.webkitURL.createObjectURL(file);
 	}
 	return url;
 }


 function getPageLocation(pageIndex) {
 	var _url = location.href;
 	if (_url.indexOf("#@")) {
	_url = _url.replace("#@", "");
	_url = _url.replace("#", "");
	} else if (_url.indexOf("#") != -1) {
	_url = _url.replace("#", "");
	}
	var _n = _url.lastIndexOf("?");
	if (_n == (-1))
		return _url + "?page=" + pageIndex;
	var query = _url.substring(_n + 1);
	var pairs = query.split("&");
	var newQuery = "";
	for ( var i = 0; i < pairs.length; i++) {
		var pos = pairs[i].indexOf("=");
	if (pos == -1)
		continue;
	var argname = pairs[i].substring(0, pos);
	if (argname == "page")
		continue;
	newQuery = newQuery + pairs[i] + "&";
	}
	return _url.substring(0, _n + 1) + newQuery + "page=" + pageIndex;
 }

 /**
  * 分解URL地址
  * @param _url;jsessionid=xxxxx
  * @returns {String}
  */
 function getUrlLocation(_url) {
	 console.log(_url);
 	if (_url.indexOf("#@") != -1) {
		_url = _url.replace("#@", "");
		_url = _url.replace("#", "");
	} else if (_url.indexOf("#") != -1) {
		_url = _url.replace("#", "");
	} else if (_url.indexOf(";") !=-1){
		//console.log("indexOf  ;");
		_url = _url.substring(0, _url.indexOf(";") )
	}
 	
	var _n = _url.lastIndexOf("?");
	if (_n == (-1))
		return _url ;

	return  _url.substring(_n + 1);
 }
 
//validator4位有效数字 验证
	jQuery.validator.addMethod("verifyfigure", function(value, element) {
		if(isNaN(value)){//判断是否为数字  
			return false;
		}
		var str = parseFloat(value).toString();//去掉前后多余的0
		if(str == 0){
			return false;
		}
		
		var index = str.indexOf(".");//找到小数点的位置
		if(index == -1){//没有小数点 
			if(str.length > 4){
				return false;
			}
			return true;
		}
		var sun = 0;//一共有几位有效数字
		var b = 0;//小数点后面有几位有效数字
		var c = true;
		if(index == 1 && str.charAt(0) == 0){//小数点前面只有一位且为0
			c = false;
		}
		if(c){
			sun = index;
		}
		for(i = 0; i < str.length - 2; i++){//获取0后面的有效数字
			if(str.charAt(2 + i) != 0){
				c = true;
			}
			if(c){
				b = str.length - 2 - i;
				break;
			}
		}
		if(sun != 0){
			sun += str.length - index -1;
		}else{
			sun += b;
		}
		
		if(sun <= 4){
			return true;
		}
		return false;
	});
	
 /**
	 * 完美验证URL正则
	 * 
	 * @param str_url
	 * @return
	 */
 function IsURL(str_url){
     var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
 + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
 + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
 + "|" // 允许IP和DOMAIN（域名）
 + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
 + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
 + "[a-z]{2,6})" // first level domain- . or .museum
 + "(:[0-9]{1,4})?" // 端口- :80
 + "((/?)|" // a slash isn't required if there is no file name
 + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
 var re=new RegExp(strRegex);
// re.test()
     if (re.test(str_url)){
         return (true);
     }else{
         return (false);
     }
 }

 // 计算剩余几个字
 function typenums(obj,words){
     var cval=$(obj).val();
     var len=$.trim(cval).length;
 	if (words!=undefined) {
 		len=words-len;
 	} else {
 		len=weibowords-len;
     }
     return len;
 }
 
 /**
  * 序列化对象
  */
  $.fn.serializeObject = function() {
 	 var o = {};
 	 var a = this.serializeArray();
 		$.each(a, function() {
 		 if (o[this.name] !== undefined) {
 		 if (!o[this.name].push) {
 		 o[this.name] = [o[this.name]];
 		}
 	 	o[this.name].push(this.value || '');
 	 } else {
 	 o[this.name] = this.value || '';
 	}
 	});
  return o;
 };
 
//利用三方提示框显示错误
 function showTipDialog(content){
 	if($.jGrowl!=undefined){
 		 $.jGrowl(content, {  
              header: '提示'
          }); 
 	}else{
 		alert(content);
 	}
 }
 
 ////判断时间是否过期
function judgeTime(time){
	var strtime = time.replace("/-/g", "/");//时间转换
	//时间
	var date1=new Date(strtime);
	//现在时间
	var date2=new Date();
	//判断时间是否过期
	 return date1<date2?true:false;
}
function showDialogModalC(title, content, okcallback,width,height,initcallback){
	if(width==undefined) width=320;
	if(height==undefined) height=120;
	
	var myDialog = dialog({
		title : title,
		width : width,
		height : height,
		//content:content,
		lock : true,
		okValue : '确定',
		ok : function(){
			if (isFunction(okcallback)) {
				return okcallback();
			}
		},
		cancelValue : '取消',
		cancel : function() {
		}
	});
	myDialog.content(content);
	myDialog.showModal();
	disabledEnter();
	if (isFunction(initcallback)) {
		initcallback();
	}
	return myDialog;
}