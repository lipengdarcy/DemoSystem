//本书中的示例源代码可以到Manning 出版社的网站下载: 
//www.manning.com/SinglePagWebApplications
$(function() {
	spa.initModule($('#spa'));
});

// Module /spa/
// Provides chat slider capability

// 将代码封装在spa名字空间内。
var spa = (function($) {

	// Module. scope variables

	// 在使用之前声明所有的变量。把模块的配置值保存在configMap中,把模块的状态值保存在stateMap中。
	var configMap = {
		extended_height : 434,
		extended_title : '点击关闭聊天窗口',
		retracted_height : 16,
		retracted_title : '点击打开聊天窗口',
		template_html : '<div class="spa-slider"><\/div>'
	};

	// Declare all other module scope variables
	var $chatSlider, toggleSlider, onClickSlider, initModule;

	toggleSlider = function() {

		var slider_height = $chatSlider.height();
		// extend slider if fully retracted
		if (slider_height === configMap.retracted_height) {
			$chatSlider.animate({
				height : configMap.extended_height
			}).attr('title', configMap.extended_title);
			return true;
		}
		// retract slider if fully extended
		else if (slider_height === configMap.extended_height) {
			$chatSlider.animate({
				height : configMap.retracted_height
			}).attr('title', configMap.retracted_title);
			return true;
		}
		// do not take action if slider is in transition
		return false;
	};

	// Event handler /onClickSlider/

	onClickSlider = function(event) {
		toggleSlider();
		return false;
	};

	// Public method / ini tModule/

	initModule2 = function($container) {
		// render HTML
		$container.html(configMap.template_html);
		$chatSlider = $container.find('.spa-slider');
		// initialize slider height and title
		// bind the user click eventto the event handler
		$chatSlider.attr('title', configMap.retracted_title).click(
				onClickSlider);
		return true;
	};

	initModule = function($container) {
		$container.html('<h1 style= "display : inline-block; margin:25px;">'
				+ 'hello world!' + '</h1>');
	};

	// 通过返回spa名字空间中的对象，导出公开方法。我们只导出了一个方法: initModule。
	return {
		initModule : initModule
	};

}(jQuery));
