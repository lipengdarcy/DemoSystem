
$(function() {
	prison();
	//console.log(prisoner_1); // 输出“I have escaped!"
	//console.log(prisoner_2); // 输出错误: prisoner_2 is not defined

	//输出“undefined”。regular_ joe 的声明被提升到函数的顶部，在查找全局作用域的regular_ joe之前，会先检查这一被提升的声明。
	//prison2();

	//prison3('dddd');

});

//1.变量作用域
function prison() {
	//函数内部定义变量不用var，则变为全局变量
	prisoner_1 = 'I have escaped! ';
	var prisoner_2 = 'I am locked in!';
}

//2.变量提升，变量在申明前未定义
var regular_joe = 'regular_ joe is assigned';
function prison2() {
	console.log(regular_joe);
	var regular_joe;
}

//3.变量提升，变量在申明前有值
function prison3(regular_joe) {
	console.log(regular_joe);
	var regular_joe;
	console.log(regular_joe);
}

//4.自执行匿名函数
(function() {
	var var1 = '自执行匿名函数';
	console.log(var1);
})();
// console.log(var1); //var1 is not defined

//5.自执行匿名函数传递参数
//值sandwich传递给匿名函数的第一个参数what_to_eat
//输出“I'm going to eat a sandwich"。
(function(what_to_eat) {
	var sentence = 'I am going to eat a' + what_to_eat;
	console.log(sentence);
})(' sandwich');
