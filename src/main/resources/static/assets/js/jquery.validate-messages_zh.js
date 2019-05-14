(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "jquery.validate"], factory );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
$.extend($.validator.messages, {
	required: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>必须填写</div></div>',
	remote: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请修正此栏位</div></div>',
	email: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的电子邮件</div></div>',
	url: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的网址</div></div>',
	date: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的日期</div></div>',
	dateISO:'<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的日期 (YYYY-MM-DD)</div></div>',
	number: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入正确的数字</div></div>',
	digits: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>只可输入数字</div></div>',
	creditcard: '<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的信用卡号码</div></div>',
	equalTo:'<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>你的输入不相同</div></div>',
	extension:'<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入有效的后缀</div></div>',
	maxlength: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>最多 {0} 个字</div></div>'),
	minlength: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>最少 {0} 个字</div></div>'),
	rangelength: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入长度为 {0} 至 {1} 之間的字串</div></div>'),
	range: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入 {0} 至 {1} 之间的数值</div></div>'),
	max: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入不大于 {0} 的数值</div></div>'),
	min: $.validator.format('<div class="w1h1"><div class="jian-alert"><i class="jian-alertico jian-alert-xs jian-ico-error"></i>请输入不小于 {0} 的数值</div></div>')
});

}));