/*!
Eson Date input control
first create by 'UANV,ESONCalendar and so on'
modify by anlige @ www.thinkasp.cn
last modify at 2015-11-2
*/
(function(){
	var __eson = 0, __days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31], __skins = {}, __skin_exp = 'calendar-skin-(\\w+)';
	var create_xhr = function() {
		var b = null;
		if (window.XMLHttpRequest) {
			b = new XMLHttpRequest();
			create_xhr = function() {
				return new XMLHttpRequest()
			}
		} else {
			if (window.ActiveXObject) {
				var AXO = ["MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp", "Microsoft.XMLHttp", "MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.4.0"], xhr;
				for (var i = AXO.length - 1; i >= 0; i--) {
					try {
						xhr = new ActiveXObject(AXO[i]);
						create_xhr = (function(obj){ return function(){ return new ActiveXObject(obj);};})(AXO[i]);
						b = xhr;
					} catch (ex) {}
				}
			}
		}
		return b
	};
	var skin_loader = function(skin, callback, retry){
		retry = retry || 0;
	    callback   = callback || function() {};
		if(__skins.hasOwnProperty(skin)) return callback();
		__skins[skin] = 'yes';
		var xhr = create_xhr();
		if(!xhr) return;
		xhr.open("GET", Eson.skin_path + skin + ".css", true);
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200 || xhr.status == 304){
					var css = ".inline-block {display:inline-block;zoom:1;*display:inline; }\n" + 
						(xhr.responseText || "").replace(/\r\n/g, '\n').replace(/\r/g, '\n').replace(/\}([\w\.\#])/g, '}\n$1').replace(/(^|,)(\s*)\./gm, '$1.calendar-skin-' + skin + ' .');
					try{
						var ele = document.createElement('style');
						ele.type = "text/css";
						ele.innerHTML = css;
						document.getElementsByTagName("head")[0].appendChild(ele);
					}catch(e){
						var ele = document.createElement("div");
						ele.innerHTML = '_<style type="text/css">' + css + '</style>';
						document.body.appendChild(ele);
						ele.removeChild(ele.firstChild);
					}
					callback();
				}else{
					if(retry == 0)skin_loader('eson', callback, 1);
				}
				xhr = null;
			}
		};
		xhr.send(null);
	};
	function set_skin(ctrl, skin){
		skin_loader(skin, function(){
			if(has_class(ctrl, __skin_exp)){
				remove_class(ctrl, __skin_exp);
				add_class(ctrl, "calendar-skin-" + skin);
			}
		});
	}
	var format_date = function(dt, fs) {
		dt = dt || (new Date());

		fs = fs.replace(/(yyyy|mm|dd|hh|ss|m|d|h|s)/ig,function(diff){
			switch(diff){
				case "yyyy" : return dt.getFullYear();
				case "M" : return dt.getMonth() + 1;
				case "MM" : return fixnumber(dt.getMonth() + 1);
				case "d" : return dt.getDate();
				case "dd" : return fixnumber(dt.getDate());
				case "H" : return dt.getHours();
				case "HH" : return fixnumber(dt.getHours());
				case "h" : return dt.getHours() % 12;
				case "hh" : return fixnumber(dt.getHours() % 12);
				case "m" : return dt.getMinutes();
				case "mm" : return fixnumber(dt.getMinutes());
				case "s" : return dt.getSeconds();
				case "ss" : return fixnumber(dt.getSeconds());
				default : return diff;
			}
		});

		return fs;
	};
	function fix_event(e){
		if(!e) return document;
		var target = e.target;
		if(!target) target = e.srcElement;
		if(target.nodeType === 3) target = target.parentNode;
		return target || document;
	}
	function fixnumber(n){
		return ("0" + n).slice(-2);
	}
	function object_copy_b(a, b){
		for(var i in a){
			if(!b.hasOwnProperty(i)) b[i] = a[i];
		}
		return b;
	}
	function ui_create_elements(target, eles) {
		if(!eles) return;
		target || (target = document.body);
		var len = eles.length, item, ele;
		for(var i=0;i<len;i++){
			item = eles[i];
			if(typeof item == 'string') target.appendChild(document.createTextNode(item));
			else{
				ele = document.createElement(item[0]);
				if(item[1]) ele.className = item[1];
				if(item[2]) ele.innerHTML = item[2];
				target.appendChild(ele);
				eles[i] = ele;
			}
		}
	}
	function ui_create_element(type, id, parent, className, html) {
		var obj = document.createElement(type.toUpperCase());
		id && (obj.id = id);
		className && (obj.className = className);
		html && (obj.innerHTML = html);
		parent || (parent = document.body);
		return parent.appendChild(obj);
	}
	function tool_get_week(date, i) {
		var tmp = new Date(date);
		tmp.setDate(i);
		return tmp.getDay();
	}
	function tool_get_days(date){
		var y, d = __days[date.getMonth()];
		if(d != 28) return d;
		y  = date.getFullYear();
		if(y % 4 == 0 && y % 100 != 0 || y % 400 == 0) return 29;
		return d;
	}
	function init_years(year, ele, start){
		ele.className = 'calendar-quick-contents date-part-y';
		start = start || (year - 11)
		if(start<0) start = 0;
		var html = "<a>&lt;&lt;&lt;</a>";
		for(var i=start;i<start+23;i++){
			html += '<a' + (i== year ? ' class="hover"' : '') + '>' + i + '</a>';
		}
		html += "<a>&gt;&gt;&gt;</a>";
		HTML(ele, html);
		show(ele.parentNode);
	}
	function init_monthes(month, ele){
		var html = '<div class="date-part-M">';
		for(var i=1;i<=12;i++){
			html += '<a' + (i== month ? ' class="hover"' : '') + '>' + i + '</a>';
		}
		HTML(ele, html + "</div>");
		show(ele.parentNode);
	}
	function init_times(ele, c, end, type){
		var html = '<div class="date-part-' + type + '">', cls = "";
		for(var i=0;i<=end;i++){
			cls = "";
			if(i==c) cls += "hover ";
			if(i % 2 == 1) cls += "odd "
			if(cls!="") cls = cls.slice(0, -1);
			html += '<a' + (cls!='' ? ' class="' + cls + '"' : "") + '>' + i + '</a>';
		}
		HTML(ele, html + "</div>");
		show(ele.parentNode);
	}
	function HTML(src, val){
		if(val === true) return parseInt(src.innerHTML);
		if(val!==undefined) src.innerHTML = typeof val == 'object' ? val.innerHTML : val;
		return src.innerHTML;
	}
	function get_center(src, offset){return src.offsetLeft + Math.floor(src.offsetWidth/2) + offset;}
	function show(src, value){src.style.display = value || "block";}
	function hide(src){src.style.display = "none";}
	function get_tag(dt){return dt.getFullYear() + ('0' + (dt.getMonth()+1)).slice(-2) + ('0' + dt.getDate()).slice(-2);}
	function visiable(src) { return src.style.display != 'none';}
	function next(src){ return src.nextSibling;}
	function last(src){ return src.previousSibling;}
	
	function ABS(a){
		var b = { x: a.offsetLeft, y: a.offsetTop, h:a.offsetHeight};
		while (a = a.offsetParent) {
			b.x += a.offsetLeft;
			b.y += a.offsetTop;
		}
		return b;
	}
	function fix_position(src, target){
		show(src.main);
		var pos = ABS(target), top = pos.y + pos.h, left = pos.x;
		show(src.arraw_up)
		hide(src.arraw_down)
		if(top + src.main.offsetHeight > find_height() + find_top()){
			top = pos.y - src.main.offsetHeight - 5;
			hide(src.arraw_up)
			show(src.arraw_down)
		}else{
			top += 5;
		}
		src.main.style.cssText = "position:absolute; top:" + top + "px; left:" + left + "px;";
	}
	function find_height(){
		if (document.documentElement  && document.documentElement.clientHeight) return document.documentElement.clientHeight;
		if (window.innerWidth) return window.innerHeight;
		if (document.body && document.body.clientWidth) return document.body.clientHeight;
		return 0;
	}
	function find_top(){
		if (document.documentElement  && document.documentElement.scrollTop) return document.documentElement.scrollTop;
		return (window.pageYOffset || document.body.scrollTop) || 0;
	}
	function cancel_bubble(e){
		if(e && e.stopPropagation){
			e.stopPropagation();
		}else{
			window.event.cancelBubble = true;
		}
	}
	function on(ele, ev, handler, cap){
		if(ele.addEventListener){
			ele.addEventListener(ev, handler, cap!==true);
		}else if(ele.attachEvent){
			ele.attachEvent("on" + ev, handler);
		}else{
			ele["on" + ev] = handler;
		}
	}
	function off(ele, ev, handler, cap){
		if(ele.removeEventListener){
			ele.removeEventListener(ev, handler, cap!==true);
		}else if(ele.detachEvent){
			ele.detachEvent("on" + ev, handler);
		}else{
			ele["on" + ev] = null;
		}
	}
	function child_of(a, b){
		while(a = a.parentNode){
			if(a == b) return true;
		}
		return false;
	}
	function parse_date(dt){
		if(typeof dt != "string") return dt;
		var ma = /^(\d{4})\-(\d{1,2})\-(\d{1,2})( (\d{1,2})\:(\d{1,2})\:(\d{1,2}))?$/.exec(dt);
		if(!ma) return null;
		if(ma[4]) return new Date(parseInt(ma[1]), parseInt(ma[2])-1, parseInt(ma[3]), parseInt(ma[5]), parseInt(ma[6]), parseInt(ma[7]));
		return new Date(parseInt(ma[1]), parseInt(ma[2])-1, parseInt(ma[3]));
	}
	function create_exp(e){if(e && typeof e == 'object') return e;return new RegExp('\\b(' + e.replace(/\-/g, '\\-').replace(/\s+/g, '|') + ')\\b','g');}
	function add_class(src, cls_name, str){
		src.className = ((src.className || "").replace(create_exp(cls_name), '') + " " + (str || cls_name)).replace(/\s+/g, ' ').replace(/(^\s+|\s+$)/g,"");
	}
	function remove_class(src, cls_name){
		src.className = (src.className || "").replace(create_exp(cls_name), '').replace(/\s+/g, ' ').replace(/(^\s+|\s+$)/g,"");
	}
	function has_class(src, cls_name){
		return create_exp(cls_name).test(src.className || "");
	}
	function toggle_class(src, cls_name){
		var exp = create_exp(cls_name), has = has_class(src, exp);
		if(has) remove_class(src, exp)
		else add_class(src, exp, cls_name);
		return has;
	}
	function create_ui(target, opt){
		var width = 0, cell_height = opt.cell_height || 0, cell_width = 0, h = '<div class="b"></div><div class="a"></div>', skin = null;
		if(opt.width){
			cell_width = Math.floor(opt.width / 7);
			width = cell_width * 7;
			opt.cell_width = cell_width;
		}
		if(opt.has_container !== true && opt.skin_class_name) skin = ui_create_element("div", null, target, 'calendar-skin-' + opt.skin_class_name + ' inline-block');
		var main = ui_create_element("div", null, skin || target, "calendar-main inline-block"),
			arraw_up_main = null, 
			arraw_down_main = null,
			_caption, _quick, arraw_up, _quick_content, arraw_down, sel_eles, middle_year, middle_month, body, table, time_hours, time_minutes, time_seconds, cell;
		
		if(width>0) main.style.width = width + "px";
		if(opt.has_container !== true) arraw_up_main = ui_create_element("div", null, main, "calendar-arraw calendar-arraw-up", h);
		_caption = ui_create_element("div", null, main, "calendar-header");
		_quick = ui_create_element("div", null, main, "calendar-quick");
			arraw_up = ui_create_element("div", null, _quick, "calendar-arraw calendar-arraw-up", h);
			_quick_content = ui_create_element("div", null, _quick, "calendar-quick-contents");
			arraw_down = ui_create_element("div", null, _quick, "calendar-arraw calendar-arraw-down", h);
			
		sel_eles = [['li', 'area-left'],['li', 'area-middle'],['li', 'area-right'],['li', 'area-left area-month-left'],['li', 'area-middle-month'],['li', 'area-right area-month-right']];
			ui_create_elements(ui_create_element("ul", null, _caption, "select-box"), sel_eles);
			middle_year = sel_eles[1];
			middle_month = sel_eles[4];
			hide(_quick);
			
		while(sel_eles.length>0) sel_eles.pop();
			
		body = ui_create_element("div", null, main, "calendar-body-wapper");
		table = ui_create_element("ul", null, body, "calendar-body");
		for (var i = 0; i < 7; i++) {
			cell = ui_create_element("li", false, table, "weeks", opt.weeks[i % 7]);
			if(cell_width>0) cell.style.width = cell_width + "px";
		}
		if(opt.show_time === true){
			sel_eles = [["div", "calendar-hours", "0"], ":", ["div", "calendar-minutes", "0"], ":", ["div", "calendar-seconds", "0"]];
			ui_create_elements(ui_create_element("div", null, body, "calendar-time"), sel_eles);
			time_hours = sel_eles[0];
			time_minutes = sel_eles[2];
			time_seconds = sel_eles[4];
			while(sel_eles.length>0) sel_eles.pop();
		}
		if(opt.has_container !== true) arraw_down_main = ui_create_element("div", null, body, "calendar-arraw calendar-arraw-down", h);
		return {
			year : middle_year,
			month : middle_month,
			weeks : table,
			main : skin || main,
			quick : _quick,
			quick_content : _quick_content,
			hours : time_hours,
			minutes : time_minutes,
			seconds : time_seconds,
			arraw_down : arraw_down_main,
			arraw_up : arraw_up_main,
			down : arraw_down,
			up : arraw_up
		};
	}
	var Eson = window.Eson = function(src, option, _maps){
		option = option || {};
		var skin = option.skin || Eson.skin;
		if(skin){
			option.skin_class_name = skin;
			if(!__skins.hasOwnProperty(skin)){
				skin_loader(skin, function(){
					Eson(src, option, _maps);
				});
				return;
			}
		}
		if(option.multi === true) {
			option.multi = null;
			return multi_control(src, option);
		}
		var options = {
			date : null,
			weeks : "日一二三四五六",
			onselect : null,
			first_week_day : 0,
			weeks_count : 1,
			multi_select : false,
			min : 0,
			max : 0,
			control_union : false,
			skin : ""
		}, dateBox = [], controls = null, maps = _maps || {}, __date, _last_selected, last_quick = null, inited = false;
		if(option.min) option.min = +parse_date(option.min);
		if(option.max) option.max = +parse_date(option.max);
		for(var i in option){
			if(!option.hasOwnProperty(i)) continue;
			options[i] = option[i];
		}
		if(typeof options.weeks == "string") options.weeks = options.weeks.split("");
		"string" == typeof(src) && (src = document.getElementById(src));
		if(!src){
			if(options.onerror) options.onerror('target element error.');
			return;
		}
		var tag = src.tagName.toLowerCase();
		if(tag == "input"){
			if(src.type!="text"){
				if(options.onerror) options.onerror('target element error.');
				return;
			}
		}
		function time_hours(val){
			return HTML(controls.hours, val);
		}
		function time_minutes(val){
			return HTML(controls.minutes, val);
		}
		function time_seconds(val){
			return HTML(controls.seconds, val);
		}
		function date_year(){return HTML(controls.year, true);}
		function date_month(){return HTML(controls.month, true);}
		function time_set(dt){
			HTML(controls.hours, dt.getHours());
			HTML(controls.minutes, dt.getMinutes());
			HTML(controls.seconds, dt.getSeconds());
		}
		function show_arraw(up, down, uleft, dleft){
			show(controls.up, up);
			controls.up.style.marginLeft = uleft;
			show(controls.down, down);
			controls.down.style.marginLeft = dleft;
		}
		function find_element(target){
			var cls_name = null;
			while((cls_name = target.parentNode.className) != 'calendar-body'){
				if(cls_name.indexOf("calendar-main")>=0) return null;
				target = target.parentNode;
			}
			return target;
		}
		function set_event_listener(e){
			if(!controls) return;
			var target = fix_event(e || event);
			if(target == document) return;
			var node = find_element(target);
			if(node){
				if(has_class(node,"weeks")){
					/*点击星期，多选有效*/
					if(options.multi_select !== true) return;
					var items = controls.weeks.getElementsByTagName("li"), tmp_date, up_date, mon, index = -1;
					for(var i=0;i<items.length;i++){
						if(node == items[i]){
							index = i;
						}else{
							if(index>=0 && i % 7 == index){
								tmp_date = new Date(date_year(), items[i].month-1, items[i].day); 
								if(options.before_select && options.before_select.call(_this,tmp_date)===false) continue;
								mon = get_tag(tmp_date);
								if(maps[mon]) maps[mon] = null;
								else maps[mon] = tmp_date;
								if(items[i].isInMonth) up_date = tmp_date;
							}
						}
					}
					if(up_date)date_up(up_date);
					invoke_multi_select(onselect);
					toggle_class(node, "checked");
				}else if(!has_class(node, "readonly")){
					/*点击普通日期单元格*/
					var new_date = new Date(node.year, node.month-1, node.day), args = [new_date.getFullYear(), fixnumber(new_date.getMonth()+1), fixnumber(new_date.getDate())];
					if(options.show_time===true) {
						new_date.setHours(time_hours(), time_minutes(), time_seconds(), 0);
						args.push(fixnumber(new_date.getHours()),fixnumber(new_date.getMinutes()),fixnumber(new_date.getSeconds()));
					}
					if(options.before_select && options.before_select.call(_this, new_date)===false) return;
					hide(controls.quick);
					remove_class(node,"current")
					if(options.multi_select===true){
						args.push(!toggle_class(node, "selected"));
					}else{
						if(_last_selected)remove_class(_last_selected,"selected")
						_last_selected = node;
						add_class(node,"selected");
						maps = {};
						maps[get_tag(new_date)] = new_date;
						args.push(node);
						__date = new_date;
					}
					args.push(true);
					if(options.onselect)options.onselect.apply(new_date, args);
					if(options.multi_select!==true && !node.isInMonth) date_up(new_date);
					if(options.remain!==true)hide(controls.main);					
				}
				return;
			}
			var tag = target.tagName.toLowerCase(), cls_name = target.className || "", par_cls_name = target.parentNode.className || "";
			var qu_content = controls.quick_content, quick = controls.quick;
			if(par_cls_name == 'calendar-time'){
				/*点击时间选择器*/
				var end = 0, c, ishour="", type = 'h';
				if(cls_name == 'calendar-hours'){
					end = 23;
				}else if(cls_name == 'calendar-minutes' || cls_name == 'calendar-seconds'){
					end = 59;
					ishour = " calendar-quick-minorsec";
					type = cls_name == 'calendar-seconds' ? 's' : 'm';
				}
				if(end>0){
					show_arraw("none", "block", "0", get_center(target, -4) + "px");
					c = HTML(target, true);
					if(last_quick != cls_name || quick.style.display == "none"){
						last_quick = cls_name;
						init_times(qu_content, c, end, type);
						quick.className="calendar-quick calendar-quick-time" + ishour;
						quick.style.marginTop = (controls.weeks.offsetHeight - quick.offsetHeight) + "px";
					}else{
						hide(quick);
					}
				}
				return;
			}
			if(par_cls_name.indexOf("date-part-")>=0){
				/*选择小时*/
				if(par_cls_name.indexOf("date-part-h")>=0){
					if(options.before_select && options.before_select.call(_this, get_date(null, HTML(target, true), null, null))===false) return;
					time_hours(HTML(target, true));
				}
				/*选择分钟*/
				else if(par_cls_name.indexOf("date-part-m")>=0){
					if(options.before_select && options.before_select.call(_this, get_date(null, null, HTML(target, true), null))===false) return;
					time_minutes(HTML(target, true));
				}
				/*选择秒*/
				else if(par_cls_name.indexOf("date-part-s")>=0){
					if(options.before_select && options.before_select.call(_this, get_date(null, null, null, HTML(target, true)))===false) return;
					time_seconds(HTML(target, true));
				}else{
					var html = HTML(target);
					if(par_cls_name.indexOf("date-part-y")>=0){
						/*选择年份*/
						if(html == "&lt;&lt;&lt;"){
							init_years(HTML(controls.year, true), target.parentNode, HTML(next(target), true)-23);
							return;
						}else if(html == "&gt;&gt;&gt;"){
							init_years(HTML(controls.year, true), target.parentNode, HTML(last(target), true)+1);
							return;
						}else{
							html = parseInt(html);
							set_up(html);
							if(options.after_select_year) options.after_select_year.call(_this, html, 0);
						}
					}
					else{
						/*选择月份*/
						html = parseInt(html);
						set_up(null, html);
						if(options.after_select_month) options.after_select_month.call(_this, html, 0);
					}
				}
				hide(quick);
				invoke_select();
				return;
			}
			/*点击左侧月份控制器*/
			if(has_class(target,"area-month-left")){
				set_up(null, HTML(next(target), true) - 1);
				var m = HTML(next(target), true);
				if(visiable(quick)) init_monthes(m, qu_content);
				if(options.after_select_month) options.after_select_month.call(_this, m, -1);
				return;
			}
			/*点击右侧月份控制器*/
			if(has_class(target,"area-month-right")){
				set_up(null, HTML(last(target), true) + 1);
				var m = HTML(last(target), true);
				if(visiable(quick)) init_monthes(m, qu_content);
				if(options.after_select_month) options.after_select_month.call(_this, m, 1);
				return;
			}
			/*点击月份控制器主体*/
			if(has_class(target,"area-middle-month")){
				if(last_quick != "month" || quick.style.display == "none"){
					init_monthes(HTML(target, true), qu_content);
					last_quick = "month";
					quick.style.marginTop = "2px";
					quick.className="calendar-quick";
					show_arraw("block", "none", get_center(target, -4) + "px", "0");
				}else{
					hide(quick);
				}
				return;
			}
			/*点击左侧年份控制器*/
			if(has_class(target,"area-left")){
				var year = HTML(next(target), true) - 1;
				if(year<=99) return;
				if(visiable(quick)) init_years(year, qu_content, null);
				set_up(year);
				if(options.after_select_year) options.after_select_year.call(_this, year, -1);
				return;
			}
			/*点击右侧年份控制器*/
			if(has_class(target,"area-right")){
				var year = HTML(last(target), true)+1;
				if(visiable(quick)) init_years(year, qu_content, null);
				set_up(year);
				if(options.after_select_year) options.after_select_year.call(_this, year, 1);
				return;
			}
			/*点击年份控制器主体*/
			if(has_class(target,"area-middle")){
				if(last_quick!="year" || quick.style.display == "none"){
					init_years(HTML(target, true), qu_content, null);
					last_quick = "year";
					quick.style.marginTop = "2px";
					quick.className="calendar-quick";
					show_arraw("block", "none", get_center(target, -4) + "px", "0");
				}else{
					hide(_quick);
				}
				return;
			}
		}
		/*更新单元格*/
		function updatebox(lines){
			var table = controls.weeks, node;
			dateBox.length=0;
			while(table.childNodes.length > 7){
				node = table.removeChild(table.lastChild);
				node.innerHTML = "";
				node.onmouseover = node.onmouseout = null;
			}
			node = null;
			var size = lines * 7, last_line = size - 7;
			for (var i = 0; i < size; i++) {
				dateBox[i] = ui_create_element("li", false, table, i>=last_line ? "calendar-last-line" : "");
			};
		}
		/*渲染控件*/
		function date_up(date) {
			_last_selected = null;
			if(options.before_render) options.before_render.call(_this, date);
			var space = tool_get_week(date, 1) - options.first_week_day;
			if(space<0) space = 7+space;
			var index = 1,
				y = date.getFullYear(),
				m = date.getMonth() + 1,
				d = date.getDate();
			HTML(controls.year, y);
			HTML(controls.month, m);
			var days= tool_get_days(date) + space,lines = options.lines || Math.ceil(days/7), boxs = lines * 7;
			updatebox(lines);
			for (var i = 0; i < boxs; i++) {
				var _this = dateBox[i], tmp_date;
				_this.isInMonth = false;
				if (i < space || i > (days - 1)) {
					add_class(_this, "disabled");
					if(i < space){
						var _last = (new Date(+date));
						_last.setDate(i-space+1);
						_this.day = _last.getDate();
						_this.month = m-1;
					}else{
						_this.day = i-days+1;
						_this.month = m+1;
					}
				}else{
					_this.month = m;
					_this.day = index;
					_this.isInMonth = true;
					var week = tool_get_week(date, index) % 7;
					if (week == 0  || week == 6)  add_class(_this, "sunday");
					index++;
				}
				tmp_date = new Date(y, _this.month-1, _this.day);
				_this.year = y;
				HTML(_this, "<div>" + (options.date_up ? (options.date_up.call(_this, y, _this.month, _this.day) || _this.day) : _this.day) + "</div>");
				
				if (maps[get_tag(tmp_date)]) {
					add_class(_this, "selected");
					_last_selected = _this;
				}
				tmp_date = +tmp_date;
				if(options.min>0 && tmp_date <options.min || options.max>0 && tmp_date > options.max){
					remove_class(_this, "selected sunday");
					add_class(_this, "disabled readonly");
				}else{
					_this.onmouseover = function() {
						if (!has_class(this, "selected")) add_class(this, "current");
					};
					_this.onmouseout = function() {
						remove_class(this, "current");
					};
				}
			};
			if(options.cell_width || options.cell_height){
				for(var i=0;i<dateBox.length;i++){
					if(options.cell_width){
						dateBox[i].style.width = options.cell_width + "px";
						dateBox[i].childNodes[0].style.width = (options.cell_width-4) + "px";
					}
					if(options.cell_height){
						dateBox[i].style.height = options.cell_height + "px";
						dateBox[i].childNodes[0].style.height = (options.cell_height-4) + "px";
						dateBox[i].childNodes[0].style.lineHeight = (options.cell_height-4) + "px";
					}
				}
			}
			if(options.after_render) options.after_render.call(_this, date);
		}
		/*指定月份和年份渲染控件*/
		function set_up(year, month){
			year || (year = HTML(controls.year, true));
			if(month!==0) month || (month = HTML(controls.month, true));
			while(month <= 0){
				month = 12 + month;
				year--;
			}
			while(month>12){
				month -= 12;
				year++;
			}
			date_up(new Date(year, month-1, 1));
		}
		function get_date(date, h, m, s){
			date = date || __date;
			date.setHours(h===null ? time_hours() : h, m===null ? time_minutes() : m, s===null ? time_seconds() : s);
			return date;
		}
		function invoke_multi_select(fn){
			var lists=[];
			for(var i in maps){
				if(maps.hasOwnProperty(i) && maps[i]) lists.push(format_date(maps[i], options.format ? options.format : "yyyy-MM-dd"));
			}
			if(fn) fn.call(maps, lists);
			else src.value = lists.join(",");
		}
		function invoke_select(date){
			date = date || __date;
			var args = [date.getFullYear(), fixnumber(date.getMonth()+1),fixnumber(date.getDate())];
			if(options.show_time===true) args.push(fixnumber(time_hours()),fixnumber(time_minutes()),fixnumber(time_seconds()));
			options.onselect.apply(__date, args);
		}
		__date = options.date;
		if(!__date && src.value) __date = src.value;
		if(options.multi_select===true){
			/*多选，分析日期，对日期按月份分组*/
			var maps_month = {}, count=0;
			if(__date){
				var dt, mon, non;
				if(typeof __date == "string") __date = __date.split(",");
				for(var i=0;i<__date.length;i++){
					dt = parse_date(__date[i]);
					mon = get_tag(dt);
					maps[mon] = dt;
					non = mon.substr(0,6);
					if(!maps_month.hasOwnProperty(non)){
						maps_month[non]= [];
						count++;
					}
					maps_month[non].push(dt);
				}
				/*如果月份数超过1，显示多个控件*/
				if(count>1 && options.multi!==false){
					options.max_count = options.max_count || 0;
					options.set_up_months = [];
					for(var i in maps_month){
						if(maps_month.hasOwnProperty(i)) options.set_up_months.push(maps_month[i][0]);
					}
					if(options.max_count>0 && count>options.max_count) count = options.max_count;
					options.count = count;
					return multi_control(src, options, maps);
				}
			}else __date=[];
			var onselect = options.onselect;
			options.onselect = function(y, m ,d, isadd){
				if(isadd) {
					maps[y+m+d] = this;
				}else{
					maps[y+m+d] = null;
				}
				invoke_multi_select(onselect);
			};
			invoke_multi_select(onselect);
			options.remain = true;
			__date = parse_date(__date[0]);
			if(!__date) __date = new Date();
		}else{
			__date = parse_date(__date);
			if(!__date){
				__date = new Date();
			}else{
				maps[get_tag(__date)] = __date;
			}
			if(!options.onselect) options.onselect = function(y, M, d, h, m, s){
				src.value = options.format ? format_date(this, options.format) : (y + "-" + M + "-" + d + (options.show_time===true ? (" " + h + ":" + m + ":" + s) : ""));
			};
		}
		function init_maps(value, clear){
			if(clear===true){
				for(var i in maps){
					if(maps.hasOwnProperty(i)) maps[i] = null;
				}
			}
			if(!value){
				date_up(new Date());
				return;
			}
			if(typeof value == 'string'){
				if(!value){
					date_up(new Date());
					return;
				}
				value = value.split(',');
			}
			if(value.length<=0) {
				date_up(new Date());
				return;
			}
			var dt;
			for(var i=0;i<value.length;i++){
				dt = parse_date(value[i]);
				maps[get_tag(dt)] = dt;
			}
			date_up(dt);
		}
		function handler_doc_click(e){
			if(!controls || !controls.main) return;
			var target = fix_event(e || event);
			if(src == target || controls.main == target || child_of(target, controls.main)) return;
			hide(controls.main);
		}
		function handler_src_click(e){
			cancel_bubble(e);
			fix_position(controls, options.bindto || src);
			hide(controls.quick);
			if(inited == true)init_maps(src.value, true);
			inited = true
		}
		function handler_src_focus(e){
			fix_position(controls, src);
			hide(controls.quick);
			if(inited == true)init_maps(src.value, true);
			inited = true
		}
		var bind = null;
		if(tag == "input"){
			on(document,"click", handler_doc_click);
			on(src, "focus", handler_src_focus);
		}else if(tag == "div"){
			options.remain = true;
			bind = src;
		}else{
			on(document,"click",handler_doc_click);
			on(src, "click", handler_src_click);
		}
		controls = create_ui(bind, options);
		if(!bind)hide(controls.main);
		controls.main.onclick = set_event_listener;
		__eson++;
		if(options.set_up_date) __date = parse_date(options.set_up_date) || __date;
		date_up(__date);
		if(options.show_time===true) time_set(__date);
		var _this = {
			options : options,
			controls : controls,
			set_up : set_up,
			id : __eson,
			year : date_year,
			month : date_month,
			destroy : function(){
				off(src, 'click', handler_src_click);
				off(src, 'focus', handler_src_focus);
				off(document, 'click', handler_doc_click);
				controls.main.parentNode.removeChild(controls.main);
				controls.main.onclick = null;
				for(var i in controls){
					if(controls.hasOwnProperty(i)) controls[i] = null;
				}
				controls = null;
				src = null;
			},
			skin : function(skin){
				set_skin(controls.main, skin);
			},
			init_maps : init_maps
		};
		if(options.has_container !== true){
			var fn = options.after_build || Eson.after_build;
			if(fn) fn(_this);
		}
		return _this;
	};
	Eson.skin = null;
	Eson.after_build = null;
	Eson.skin_path = null;
	Eson.format = format_date;
	Eson.on = on;
	Eson.off = off;
	Eson.cancel_bubble = cancel_bubble;
	Eson.child_of = child_of;
	var multi_control = function(src, option, maps){
		var tools = [], tool = null, inited = false;
		function on_select_year(ym, val, t, id){
			if(t == 0) return;
			var tool;
			for(var i=0;i<tools.length;i++){
				if(i != id){
					tool = tools[i];
					if(!tool) continue;
					if(ym == 'y'){
						tool.set_up(tool.year() + t);
					}else{
						tool.set_up(null, tool.month() + t);
					}
				}
			}
		}
		function create_group_ui(){
			var group, set_up_date, arraw_down_main, arraw_up_main, skin = null;
			if(options.skin_class_name) skin = ui_create_element("div", null, null, 'calendar-skin-' + options.skin_class_name + ' inline-block');
			group = ui_create_element("div", null, skin, "calendar-group inline-block");
			arraw_up_main = ui_create_element("div", null, group, "calendar-arraw calendar-arraw-up", '<div class="b"></div><div class="a"></div>');
			for(var i=0;i<options.count;i++){
				if(options.set_up_months) set_up_date = options.set_up_months[i];
				tool = Eson(group, object_copy_b(options, {set_up_date : set_up_date || new Date(), has_container : true}), maps);
				if(options.control_union===true){
					tool.options.after_select_year = (function(id){
						return function(y, t){
							on_select_year('y', y, t, id);
						};
					})(tools.length);
					tool.options.after_select_month = (function(id){
						return function(y, t){
							on_select_year('m', y, t, id);
						};
					})(tools.length);
				}
				tools.push(tool);
				tool = null;
			}
			if(options.show_close !== false){
				var close = ui_create_element("div", null, group, "calendar-close", "x");
				close.onclick = function(e){
					hide(skin || group);
				};
			}
			arraw_down_main = ui_create_element("div", null, group, "calendar-arraw calendar-arraw-down", '<div class="b"></div><div class="a"></div>');
			return {
				main : skin || group,
				arraw_up : arraw_up_main,
				arraw_down : arraw_down_main,
				close : close
			};
		}
		function handler_doc_click(e){
			if(!controls || !controls.main) return;
			var target = fix_event(e || event);
			if(src == target || controls.main == target || child_of(target, controls.main)) return;
			hide(controls.main);
		}
		function re_date_up(){
			if(inited == true){
				for(var i in maps){
					if(maps.hasOwnProperty(i)) maps[i] = null;
				}
				var dt, mon, non, __date = src.value, maps_month = {},count = 0;
				if(!__date) __date = [];
				if(typeof __date == "string") __date = __date.split(",");
				for(var i=0;i<__date.length;i++){
					dt = parse_date(__date[i]);
					mon = get_tag(dt);
					maps[mon] = dt;
					non = mon.substr(0,6);
					if(!maps_month.hasOwnProperty(non)){
						maps_month[non]= [];
						count++;
					}
					maps_month[non].push(dt);
				}
				var index = 0, tool;
				for(var i in maps_month){
					if(!maps_month.hasOwnProperty(i)) continue;
					if(tools[index]){
						hide(tools[index].controls.quick);
						tools[index].init_maps(maps_month[i]);
					}
					index++;
				}
				if(index<tools.length){
					for(var i=index;i<tools.length;i++){
						if(tools[i]) tools[i].init_maps();
					}
				}
			}
			inited = true;
		}
		function handler_src_click(e){
			cancel_bubble(e);
			fix_position(controls, options.bindto || src);
			re_date_up();
		}
		function handler_src_focus(e){
			fix_position(controls, src);
			re_date_up();
		}
		var options = {
			date : [],
			onselect : null,
			count:2
		}, controls = null, dates = [];
		
		for(var i in option){
			if(!option.hasOwnProperty(i)) continue;
			options[i] = option[i];
		}
		options.remain = true;
		options.lines = 6;
		if(!options.onselect) options.onselect = function(y){
			src.value = y.join(",");
		};
		"string" == typeof(src) && (src = document.getElementById(src));
		if(!src){
			if(options.onerror) options.onerror('target element error.');
			return;
		}
		var tag = src.tagName.toLowerCase(), bint2 = false;
		if(tag == "input"){
			if(src.type!="text"){
				if(options.onerror) options.onerror('target element error.');
				return;
			}
			on(document,"click", handler_doc_click);
			on(src, "focus", handler_src_focus);
		}else if(tag == "div"){
			bint2 = true;
		}else{
			on(document,"click", handler_doc_click);
			on(src, "click", handler_src_click);
		}
		controls = create_group_ui();
		if(bint2) src.appendChild(controls.main);
		else hide(controls.main);
		var _this = {
			options : options,
			controls : controls,
			destroy : function(){
				off(src, 'click', handler_src_click);
				off(src, 'focus', handler_src_focus);
				off(document, 'click', handler_doc_click);
				for(var i = 0; i<tools.length; i++) tools[i].destroy();
				controls.main.parentNode.removeChild(controls.main);
				if(controls.close) controls.close.onclick = null;
				for(var i in controls){
					if(controls.hasOwnProperty(i)) controls[i] = null;
				}
				controls = null;
				src = null;
			},
			skin : function(skin){
				set_skin(controls.main, skin);
			}
		};
		var fn = options.after_build || Eson.after_build;
		if(fn) fn(_this);
		return _this;
	};
})();