###Eson简单日历控件 @ 2015-12-8
用于web的简单日历控件。
<a href="http://www.thinkasp.cn/" target="_blank">http://www.thinkasp.cn/</a>

* 不依赖于任何前端框架。
* 支持单选，多选。
* 自定义控件数量。
* 自定义星期显示。
* 自定义工作日的第一天。
* 自定义日期格式。
* 表单自动绑定。
* 支持自定义单元格。
* 支持联动。
* 支持设置起始日期和结束日期。
* 支持多种事件。
* 自定义控件宽度、单元格高度等。

####参数
```js
Eson('calendar', {
	date : new Date(),			//初始日期，默认为当前日期
	set_up_date : null,			//包含控件展示月份的日期
	first_week_day : 0,			//一周的第一天，默认为星期日。可选。
	weeks : "日一二三四五六",	//星期的显示文本，例如 : ['Sun', 'Mon', 'Tues', 'Wed', 'Thur', 'Fri', 'Sat']。可选。
	multi_select : false,		//是否允许多选
	//日期选择事件，this引用为选择的日期对象，y, M, d分别代表年、月、日。
	//如果允许显示时间，则h,m,s分别代表小时，分钟和秒
	//如果允许多选，则y为包含已选择日期的数组
	//如果不定义，并且绑定的文本框，则直接将值赋值给文本框（多选时多个日期逗号连接）。
	onselect : function(y, M, d, h, m, s){},
	onerror : function(msg){},	//控件出错事件
	format : "",				//日期格式，未定义onselect时有效。可选yyyy/MM/M/dd/d/H/HH/h/hh/mm/m/ss/s组合。
	min : null,					//设定起始日期
	max : null,					//设定结束日期
	width : null,				//设置控件自动控制的宽度，默认不设置，根据css展示。
	cell_height : null,			//设置控件日期单元格的高度，宽度由控件自行计算。
	show_time : false,			//是否显示时间
	date_up : function(y, m, d){},		//填充单元格事件，可通过返回值控制单元格内容
	before_render : function(date){},	//开始渲染单元格事件
	after_render : function(date){},	//单元格渲染结束事件
	max_count : 0,				//显示多控件时，最多显示的控件数,
	control_union : false		//是否允许控制器联动（控制器值控件中的年份选择器）,
	after_select_year : function(y, type){},	//控制器选择年份的事件，y为选择的年份，type为操作类型
	after_select_month : function(m, type){}	//控制器选择月份的事件，m为选择的月份，type为操作类型
});
```