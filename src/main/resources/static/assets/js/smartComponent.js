/*******************************************************************************
 * 自定义前端组件
 * 
 * 1：文件上传（基于uploadify）
 * 
 * 2：下拉单选
 * 
 * 3：下拉多选用户
 * 
 * 4：省市区下拉单选、行业分类下拉单选
 * 
 * 5：文件上传（基于plupload）
 * 
 ******************************************************************************/

(function($) {

	/***************************************************************************
	 * 1：文件上传
	 * 
	 * @param id
	 *            文件上传input的id，必填
	 * @param callback
	 *            回调函数，处理文件上传后的显示等，必填
	 * @param pathType
	 *            上传路径，参考枚举类：UploadFilePathEnum，可选
	 * @param buttonName
	 *            上传按钮显示的文字，可选
	 * @param fileTypeExts
	 *            可上传文件的后缀 可选
	 * @param type
	 *            默认上传多个 1上传单个
	 * @param data
	 *            已有文件的信息数组(id,name,fullpath)
	 * 
	 * 使用方法： $('#id').filetool();
	 **************************************************************************/

	// 1.定义jquery的扩展方法filetool
	$.fn.filetool = function(options) {
		var _theFileData;
		var that = this;
		var fileupload = $(this);
		// 2.将调用时候传过来的参数和default参数合并
		options = $.extend({}, $.fn.filetool.defaults, options || {});
		fileupload.siblings('.input-group').remove();
		var show = '<div class="fileupD"><ul></ul></div>';
		fileupload.after(show);
		var url = home_url + '/fileupload/upload';
		if (options.uploadType == 1) {
			url = home_url + '/admin/fileupload/upload';
		}
		fileupload.uploadify({
			'swf' : home_url + '/static/js/uploadify/uploadify.swf',
			'uploader' : url,
			'buttonText' : '<i class="icon-cloud-upload bigger-110"></i> '
					+ options.buttonText,
			'fileTypeExts' : options.fileTypeExts,
			'fileSizeLimit' : '20MB',
			'multi' : options.multi,
			'method' : 'post',
			'debug' : false,
			'successTimeout' : 60,
			'onUploadStart' : function(file) {
				var param = {};
				param.pathType = options.pathType;
				param.uid = _uid;
				fileupload.uploadify("settings", "formData", param);
			},
			'onUploadSuccess' : function(file, data, response) {
				var obj = eval('(' + data + ')');
				// 文件上传成功后的回调
				_theFileData = obj.data;
				options.callback.call(fileupload, obj.data);
			},
			// 检测FLASH失败调用
			'onFallback' : function() {
				alert("您未安装FLASH控件，无法上传！请安装FLASH控件后再试。");
			},
			'onUploadError' : function(file, errorCode, errorMsg, errorString) {
				alert('文件上传出错，文件名：' + file.name + ' 错误详情：' + errorString);
			}
		});

		that.showfile = function(id) {
			// 上传成功显示文件名和链接
			var fileData = _theFileData;
			if (fileData == undefined)
				return;

			var li = '<li value="'
					+ fileData.id
					+ '"><a target="_blank" href="'
					+ fileData.fullpath
					+ '">'
					+ fileData.fileName
					+ '</a><span onclick="$(this).li_remove();"><i class="icon-remove-sign"></i></span></li>';

			if (options.multi)
				$('#' + id).siblings('.fileupD').find('ul').append(li);
			else
				$('#' + id).siblings('.fileupD').find('ul').html(li);
		}

		return this;
	}

	// 6.默认选项
	$.fn.filetool.defaults = {
		uploadType : 0,
		pathType : 0,
		buttonText : '文件上传',
		fileTypeExts : '*.*',
		multi : true,
		callback : function(param) {
			alert(param.fileName);
		}
	};

	/***************************************************************************
	 * 2：下拉单选
	 * 
	 * https://www.cnblogs.com/landeanfen/p/5124542.html
	 * 
	 * @param id
	 *            select的id，必填
	 * @param url
	 *            加载下拉选项的text和value
	 * @param param
	 *            上传路径，参考枚举类：UploadFilePathEnum，可选
	 * @param data
	 *            下拉选项的text和value
	 * @param valueField
	 *            下拉选项的value
	 * @param textField
	 *            下拉选项的text
	 * 
	 * 使用方法： $('#id').combobox({ url: '/apiaction/Plant/Find', valueField:
	 * 'TM_PLANT_ID', textField: 'NAME_C' });
	 **************************************************************************/

	// 1.定义jquery的扩展方法combobox
	$.fn.combobox = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.combobox.methods[options](this, param);
		}
		// 2.将调用时候传过来的参数和default参数合并
		options = $.extend({}, $.fn.combobox.defaults, options || {});
		// 3.添加默认值
		var target = $(this);
		target.attr('valuefield', options.valueField);
		target.attr('textfield', options.textField);
		target.empty();
		var option = $('<option></option>');
		option.attr('value', '');
		option.text(options.placeholder);
		target.append(option);
		// 4.判断用户传过来的参数列表里面是否包含数据data数据集，如果包含，不用发ajax从后台取，否则否送ajax从后台取数据
		if (options.data) {
			init(target, options.data);
		} else {
			// var param = {};
			options.onBeforeLoad.call(target, options.param);
			if (!options.url)
				return;
			$.getJSON(options.url, options.param, function(data) {
				init(target, data);
			});
		}
		function init(target, data) {
			$.each(data, function(i, item) {
				var option = $('<option></option>');
				option.attr('value', item[options.valueField]);
				option.text(item[options.textField]);
				target.append(option);
			});
			options.onLoadSuccess.call(target);
		}
		target.unbind("change");
		target.on("change", function(e) {
			if (options.onChange)
				return options.onChange(target.val());
		});
	}

	// 5.如果传过来的是字符串，代表调用方法。
	$.fn.combobox.methods = {
		getValue : function(jq) {
			return jq.val();
		},
		setValue : function(jq, param) {
			jq.val(param);
		},
		load : function(jq, url) {
			$.getJSON(url, function(data) {
				jq.empty();
				var option = $('<option></option>');
				option.attr('value', '');
				option.text('请选择');
				jq.append(option);
				$.each(data, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item[jq.attr('valuefield')]);
					option.text(item[jq.attr('textfield')]);
					jq.append(option);
				});
			});
		}
	};

	// 6.默认参数列表
	$.fn.combobox.defaults = {
		url : null,
		param : null,
		data : null,
		valueField : 'value',
		textField : 'text',
		placeholder : '请选择',
		onBeforeLoad : function(param) {
		},
		onLoadSuccess : function() {
		},
		onChange : function(value) {
		}
	};

	/***************************************************************************
	 * 3：下拉多选用户
	 * 
	 * @param id
	 *            select的id，必填
	 * @param data
	 *            已经选中的用户id
	 * 
	 * 使用方法： $('#id').selectUser({ data: [1,2] });
	 **************************************************************************/

	$.fn.selectUser = function(options) {
		options = $.extend({}, $.fn.selectUser.defaults, options || {});
		// alert(options.id);
		var url = options.url;
		var target = $(this);
		target.empty();
		if (target.attr("multiple") == undefined)
			target
					.append('<option value="" style="display: none;" disabled selected>请选择</option>');
		// alert(url);
		url = options.url + "?type=" + options.type;
		if (options.data) {
			$.getJSON(url, function(data) {
				$.each(data.data, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					if (url.indexOf("/user/selectUserData") != -1)
						option.text(item.realName);
					else
						option.text(item.name);
					// 设置选中
					$.each(options.data, function(i, item_in) {
						if (item.id == item_in) {
							option.attr('selected', 'selected');
							//return false;
						}
					});
					target.append(option);
				});
				target.trigger("chosen:updated");
			});
		} else {
			$.getJSON(url, function(data) {
				$.each(data.data, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					if (url.indexOf("/user/selectUserData") != -1)
						option.text(item.realName);
					else
						option.text(item.name);
					target.append(option);
				});
				target.trigger("chosen:updated");
			});
		}
		target.chosen().trigger("liszt:updated");

	}
	$.fn.selectUser.defaults = {
		url : '/user/selectUserData',
		data : null,
		//用户角色默认为-1
		type : -1,
		placeholder : '选择用户'
	};

	/***************************************************************************
	 * 4：省市区下拉单选
	 * 
	 * @param id
	 *            div的id，必填
	 * @param areaId
	 *            下拉选项的value，即行政区域id
	 * 
	 * 使用方法： $('#id').selectCity({ id: '110000000000' });
	 **************************************************************************/

	// 1.定义jquery的扩展方法selectCity
	$.fn.selectCity = function(options) {
		// 2.将调用时候传过来的参数和default参数合并
		options = $.extend({}, $.fn.selectCity.defaults, options || {});
		// 3.添加默认值
		var target = $(this);
		var province = '<select class="selectCityLink"></select>';
		var city = '<select class="selectCityLink"></select>';
		var area = '<select class="selectCityLink"></select>';

		target.empty();
		target.append(province);
		target.append(city);
		target.append(area);

		// 获取数据
		$.getJSON(options.url, 'id=' + options.id, function(data) {
			init(target, data);
		});

		var defaultOption = '<option value="">请选择</option>';
		function init(target, data) {
			target.find("select:eq(0)").append(defaultOption);
			target.find("select:eq(1)").append(defaultOption);
			target.find("select:eq(2)").append(defaultOption);
			// 省数据
			var provinceId = data.data.provinceId;
			var provinceList = data.data.provinceList;

			$.each(provinceList, function(i, item) {
				var option = $('<option></option>');
				option.attr('value', item.id);
				option.text(item.name);
				if (item.id == provinceId) {
					option.attr('selected', 'selected');
				}
				target.find("select:eq(0)").append(option);
			});
			// 市数据
			var cityId = data.data.cityId;
			var cityList = data.data.cityList;
			if (cityList) {
				$.each(cityList, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					option.text(item.name);
					if (item.id == cityId) {
						option.attr('selected', 'selected');
					}
					target.find("select:eq(1)").append(option);
				});
			} else if (provinceId) {
				// 获取数据
				$.getJSON(options.getChildUrl, {
					id : provinceId
				}, function(data) {
					$.each(data.data, function(i, item) {
						var option = $('<option></option>');
						option.attr('value', item.id);
						option.text(item.name);
						target.find("select:eq(1)").append(option);
					});
				});
			}
			// 区数据
			var areaId = data.data.areaId;
			var areaList = data.data.areaList;
			if (areaList) {
				$.each(areaList, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					option.text(item.name);
					if (item.id == areaId) {
						option.attr('selected', 'selected');
					}
					target.find("select:eq(2)").append(option);
				});
			} else if (cityId) {
				// 获取数据
				$.getJSON(options.getChildUrl, {
					id : cityId
				}, function(data) {
					$.each(data.data, function(i, item) {
						var option = $('<option></option>');
						option.attr('value', item.id);
						option.text(item.name);
						target.find("select:eq(2)").append(option);
					});
				});
			}
		}
		// 省变化事件：加载市数据
		target.find("select:eq(0)").unbind("change");
		target.find("select:eq(0)").on("change", function(e) {
			target.find("select:eq(1)").empty();
			target.find("select:eq(2)").empty();
			target.find("select:eq(1)").append(defaultOption);
			target.find("select:eq(2)").append(defaultOption);
			$.getJSON(options.getChildUrl, {
				id : $(this).val()
			}, function(data) {
				$.each(data.data, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					option.text(item.name);
					target.find("select:eq(1)").append(option);
				});
			});
			//回调
			options.callback.call(target, target.find("select:eq(0)").val());
		});
		// 市变化事件：加载区数据
		target.find("select:eq(1)").unbind("change");
		target.find("select:eq(1)").on("change", function(e) {
			target.find("select:eq(2)").empty();
			target.find("select:eq(2)").append(defaultOption);
			$.getJSON(options.getChildUrl, {
				id : $(this).val()
			}, function(data) {
				$.each(data.data, function(i, item) {
					var option = $('<option></option>');
					option.attr('value', item.id);
					option.text(item.name);
					target.find("select:eq(2)").append(option);
				});
			});
			//回调
			options.callback.call(target, target.find("select:eq(1)").val());
		});
		// 区变化事件：只有回调事件
		target.find("select:eq(2)").unbind("change");
		target.find("select:eq(2)").on("change", function(e) {
			options.callback.call(target, target.find("select:eq(2)").val());
		});
	}

	// 5.如果传过来的是字符串，代表调用方法。
	$.fn.selectCity.methods = {
		getValue : function(jq) {
			return jq.val();
		},
		setValue : function(jq, param) {
			jq.val(param);
		}
	};

	// 6.默认参数列表
	// 选择行业分类： /industry/getTypeList,   /industry/getChildren
	$.fn.selectCity.defaults = {
		url : '/area/getAreaList',
		getChildUrl : '/area/getChildren',
		id : 0,
		placeholder : '请选择',
		callback : function() {
		},
	};

	/***************************************************************************
	 * 5：文件上传
	 * 
	 * @param id
	 *            文件上传的id，必填
	 * @param callback
	 *            回调函数，处理文件上传后的显示等，必填
	 * 
	 * 使用方法： $('#id').fileUpload();
	 **************************************************************************/

	// 1.定义jquery的扩展方法fileUpload, 基于plupload
	$.fn.fileUpload = function(options) {
		var fileupload = $(this);
		// 2.将调用时候传过来的参数和default参数合并
		options = $.extend({}, $.fn.fileUpload.defaults, options || {});

		var uploader = new plupload.Uploader(
				{
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : $(this).attr('id'), //上传按钮的id，是一个a
					multi_selection : false,
					flash_swf_url : '/static/js/plupload-2.1.2/js/Moxie.swf',
					silverlight_xap_url : '/static/js/plupload-2.1.2/js/Moxie.xap',
					url : 'http://oss.aliyuncs.com',
					filters : {
						mime_types : [ {
							title : "文件",
							extensions : options.extensions
						} ],
						max_file_size : '200mb', 
						prevent_duplicates : true
					},
					init : {
						PostInit : function() {
							set_upload_param(uploader, '', false);
						},
						FilesAdded : function(up, files) {
							//加入到上传列表中
							plupload
									.each(
											files,
											function(file) {
												var html = '<li class="fileItem" id='
														+ file.id
														+ '>'
														+ '<a class="fileName" href="javascript:;" target="_blank">'
														+ file.name
														+ '</a><b></b>'
														+ '<div class="clearfix"></div> '
														+ '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
														+ '<div class="status dn" onclick="$(this).li_remove();"><i class="icon-remove-sign"></i></div>'
														+ '<input name="'
														+ fieldname
														+ '" type="hidden" value="" />'
														+ '</li>';
												$(this).siblings('ul').append(html);
											});
							//直接开始上传
							set_upload_param(uploader, '', false);
						},

						BeforeUpload : function(up, file) {
							console.info("2.BeforeUpload");
							set_upload_param(up, file.name, true);
						},
						//上传进度条
						UploadProgress : function(up, file) {
							var d = document.getElementById(file.id);
							d.getElementsByTagName('b')[0].innerHTML = '<span>'
									+ file.percent + "%</span>";
							//div.progress
							var prog = d.getElementsByTagName('div')[1];
							var progBar = prog.getElementsByTagName('div')[0]
							progBar.style.width = file.percent + '%';
							progBar.setAttribute('aria-valuenow', file.percent);
						},

						FileUploaded : function(up, file, info) {
							console.info("3.FileUploaded key=" + key);

							if (info.status == 200) {
								console.info(file);
								console.info(info.response);
								console.info("g_object_name:" + g_object_name);
								//
								var obj = eval("(" + info.response + ")");
								if (obj != null) {
									console.info(obj);
									etagMd5 = obj.etag;
									fileUrl = obj.fileurl;
									mimeType = obj.mimeType;
								} else {
									fileUrl = g_object_name;
									etagMd5 = "";
									mimeType = "";
								}

								//本地信息
								originalFilename = file.name;
								origSize = file.origSize;
								description = options.modulename;
								var formData = new FormData();
								formData.append('originalFilename',	originalFilename);
								formData.append('origSize', origSize);
								formData.append('description', description);
								formData.append('etagMd5', etagMd5);
								formData.append('fileUrl', fileUrl);
								formData.append('mimeType', mimeType);
								post(addserviceUrl, formData, function(res) {
									if (res.code == 0) {
										//okcallback(file, res.data);
										$("#" + file.id + " div.status").show(
												20);
										$("#" + file.id + " b").hide(20);
										$("#" + file.id + " div.progress")
												.hide(20);
										$(
												"#" + file.id + " input[name='"
														+ fieldname + "']")
												.val(res.data.id);
										$("#" + file.id + " .fileName").attr(
												"href", res.data.fullpath);

										//移除
										up.removeFile(file);
									}
								});
							} else if (info.status == 203) {
								console
										.error('上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:'
												+ info.response);
							} else {
								console.error(info.response);
							}
						},

						Error : function(up, err) {
							if (err.code == -600) {
								console
										.error("\n选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小");
							} else if (err.code == -601) {
								console
										.error("\n选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型");
							} else if (err.code == -602) {
								console.error("\n这个文件已经上传过一遍了");
							} else {
								console.error(err.response);
							}
						}
					}
				});

		uploader.init();
		return uploader;

	}

	// 6.默认选项
	$.fn.fileUpload.defaults = {
		uploadType : 0,
		pathType : 0,
		extensions : '*',
		modulename : '公共',
		callback : function(param) {
			alert(param.fileName);
		}
	};

})(jQuery);
