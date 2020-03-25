//var ats = [{"id":"01","text":"出差"},{"id":"02","text":"公事外出"},{"id":"03","text":"年假"},{"id":"04","text":"事假"},{"id":"05","text":"婚假"},{"id":"06","text":"产假"},{"id":"07","text":"病假"},{"id":"08","text":"丧假"},{"id":"09","text":"其他"},{"id":"10","text":"产检假"},{"id":"11","text":"陪产假"},{"id":"12","text":"哺乳假"},{"id":"13","text":"忘记打卡"},{"id":"14","text":"身体调养假"},{"id":"15","text":"工伤假"},{"id":"16","text":"调休假"},{"id":"17","text":"年度体检"},{"id":"18","text":"结转年假"}];

var codes = {};
function initattendCode(attendCode){
	$.each(attendCode, function(i, code){
		codes[code['dictValue']] = {"text":code['dictLabel'],"css":code['cssClass']};
	});
}

var sdate = new Date();
//获取考勤简称
function getTitle4AttendType(attendanceCode){
	var name = codes[attendanceCode] || '';
	if(name.length > 0){
		name = name.substring(0, 1);
	}
	return name;
}			
var maps = {};
var days = {};
var remarks = {};
//处理日历的每一天
function dealCalDays(queryDate){
	if(!days[queryDate]){
		///attend/AttendMachineAction!getCalendar4Person.action
		var data = postSync("/index/calendarJson", {
			"first_date": queryDate
		});
		$.extend(days, data.data);
	}
}
//处理考勤类型
function render_holiday(attendance_type, apply_desc){
	var name = getTitle4AttendType(attendance_type);
	var title = codes[attendance_type] || '';
	if(length(apply_desc) > 0){
		title += '-' + apply_desc;
	}
	return '<span class="calendar-sign holi" title="' + title + '">' + name + '</span>';
}

function initEson(divId) {
	$('#'+divId).empty();
	$('#'+divId).html('<div id="inputtext_n"></div>');
	var width = $('#'+divId).width();
	//alert(width);
	Eson('inputtext_n', {
		set_up_date: sdate,
		width : width,
		cell_height : 50,
		onselect : function(y, M, d){
			sdate = new Date(y, M-1, d);
		},
		before_render : function(date){
			maps = {};
			date.setDate(1);
         	var m = date.getMonth();
            var prevDate = new Date(date);
            prevDate.setMonth(m - 1);
            var nextDate = new Date(date);
            nextDate.setMonth(m + 1);
            //本月
            var queryDate = tostring(date).substring(0, 10);
            dealCalDays(queryDate);
            //上月
            queryDate = tostring(prevDate).substring(0, 10);
            dealCalDays(queryDate);
            //下月
            queryDate = tostring(nextDate).substring(0, 10);
            dealCalDays(queryDate);
		},
		
		after_render: function(date){
			//重新标注工作日和假日
			$('.calendar-sign').closest('li:not(.disabled)').removeClass('sunday');
			$('.calendar-sign.rest').closest('li:not(.disabled)').addClass('sunday');
			$('#'+divId+' .date').hover(function(){
				var that = $(this);
			  	try{
			  		var y = that.data("y");
			  		var M = that.data("m");
			  		var d = that.data("d");
					var date = NL(new Date(y, M-1, d)), festival = date.festival();
					if(festival){
						var fess = "", item;
						for(var i=0;i<festival.length;i++){
							item = festival[i];
							if(item) fess += item.value +  "，";
						}
						if(fess) fess = fess.slice(0, -1);
					}
					var event = remarks[y + '-' + lpad(M, 2, '0') + '-' + lpad(d, 2, '0')];
					var html = 
						"公历：" + Eson.format(date.oDate, "yyyy-M-d") + "&nbsp;星期" + date.cnDay + "<br />" + 
						"农历：" + date.lunarYear + "年(" + date.animal + ")" + date.lMonth + "月(" + (date.isBigMonth ? "大": "小")  + ")" + date.lDate + (date.term ? '&nbsp;' + date.term : "") + "<br />" + 
						"干支：" + date.gzYear + "年" + date.gzMonth + "月" + date.gzDate + "日<br />" + 
						(fess ? "节日：" + fess +"</br>" : "") + ""+
						(!isempty(event)?"事件："+event:"");
					
					layui.layer.tips(html, that);
				}catch(ex){ }
			}, function() {layui.layer.closeAll('tips');});
		},
		date_up : function(y, m, d){
			try{
				var date = NL(new Date(y, m-1, d)), festival = date.festival();
				var show = date.lDate, cls_name="";
				if(show == "初一") show = date.lMonth + "月";
				if(festival){
					var fes = "",item;
					for(var i=0;i<festival.length;i++){
						item = festival[i];
						if(!item) continue;
						if(!maps.hasOwnProperty(item.value)){
							fes = item.value;
							maps[fes] = "yes";
							break;
						}
					}
					if(fes){
						show = fes;
						cls_name = "hover";
					}
				}
				if(!fes && date.term){
					show = date.term;
					cls_name = "hover";
				}
				var spec = "";
				var work_flag = '';
				var a = days[y + '-' + lpad(m, 2, '0') + '-' + lpad(d, 2, '0')];
				if(a && length(a) > 0){
					//考勤描述 为之后onselect做准备
					remarks[y + '-' + lpad(m, 2, '0') + '-' + lpad(d, 2, '0')] = a[1] || '';
					//考勤代码
					work_flag = a[0];
					spec = codes[work_flag].css || '';
					
				}
				
				var bunch = [];
				var dateclass = 'data-y='+ y.toString() + ' data-m=' + m.toString() + ' data-d=' + d.toString() + ' class="date"';
				bunch.push(dateclass);
				
				var html =  '</br><span '+   (cls_name ? ' class="' + cls_name + '"' : ' ') + '>' + maxWord(show) + '</span>';
				var tempd = d;
				//日期颜色考勤代码 0事假 1年假 2调休假 3忘记打卡 4迟到 5早退 6矿工 7迟到+早退（迟到） 9休息日
				//10 工作日
				if(work_flag==9){
					d = redWord(d);
				}
				if(work_flag==10){
					d = blackWord(d);
				}
				
				
				//return '<div class="date" data-y="'+ y.toString() + '" data-m="' + m.toString() + '" data-d="' + d.toString() + '">
				if (spec != "") {//d长度为1补一个空,为2不补
					var reg=/./g;
					html = lpad(tempd,2,' ').replace(reg,' ')+''+ d + spec + html; 
				}else{
					html = d+ html;
				}
				html = '<i '+ dateclass +'>'+ html+'</i>'
				return html;
			}catch(ex){}
		}
	});
}
function redWord(word){
	return '<font color="red">'+word+'</font>'
}
function blackWord(word){
	return '<font color="black">'+word+'</font>'
}
function maxWord(word){
	var length = word.length;
	if(length>5){
		return word.substring(0,5);
	}
	return word;
}

function length(b) {
    if (!isvalue(b)) return 0;
    if (ismap(b)) {
        var a = 0;
        for (key in b) if (b.hasOwnProperty(key)) a++;
        return a
    } else return b.length
}

function lpad(c, a, b) {
    c = getstring(c);
    if (typeof a === "undefined") a = 0;
    if (typeof b === "undefined") b = " ";
    if (a + 1 >= c.length) c = Array(a + 1 - c.length).join(b) + c;
    return c
}
function rpad(c, a, b) {
    c = getstring(c);
    if (typeof a === "undefined") a = 0;
    if (typeof b === "undefined") b = " ";
    if (a + 1 >= c.length) c = c + Array(a + 1 - c.length).join(b);
    return c
}
function trim(a) {
    return a == null ? "": (a + "").replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "")
}
function getstring(a){
	return isvalue(a)?String(a):""
}
function tostring(b) {
    var f = "";
    if (isvalue(b)) {
        var p = Object.prototype.toString.apply(b);
        if (p === "[object Array]") {
            var h = new Array;
            for (var e = 0; e < b.length; e++) h.push(tostring(b[e]));
            f = "[" + h.join(",") + "]";
            h.length = 0;
            h = null
        } else if (p === "[object Date]") {
            var g = b.getYear();
            if (g < 1900) g += 1900;
            var c = b.getMonth() + 1;
            f = g + "-" + lpad(c, 2, "0") + "-" + lpad(b.getDate(), 2, "0") + " " + lpad(b.getHours(), 2, "0") + ":" + lpad(b.getMinutes(), 2, "0") + ":" + lpad(b.getSeconds(), 2, "0")
        } else if (p === "[object Object]") {
            var h = new Array;
            for (var d in b) {
                var j = b[d];
                if (isvalue(j)) {
                    var l = Object.prototype.toString.apply(j);
                    if (l === "[object Array]" || l === "[object Object]") h.push('"' + d + '":' + tostring(j));
                    else h.push('"' + d + '":"' + tostring(j) + '"')
                } else h.push('"' + d + '":""')
            }
            f = "{" + h.join(",") + "}";
            h.length = 0;
            h = null
        } else if (p === "[object String]") f = escape(b);
        else if (p === "[object Number]") f = isFinite(b) ? String(b) : "null";
        else f = escape(String(b))
    }
    return f
}
function isjson(a) {
    if (isvalue(a) && trim(a).match("^{(.+:.+,*){1,}}$")) return true;
    else return false
}
function tojson(str) {
    if (str==null || str=='') return null;
    try {
        return eval("(" + str + ")")
    } catch(ex) {
        return null
	}
}

function isvalue(a) {
    return typeof a !== "undefined" && a !== null ? true: false
}
var escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
var meta = {"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};
function escape(a) {
    escapable.lastIndex = 0;
    return escapable.test(a) ? a.replace(escapable,
    function(b) {
        var d = meta[b];
        return typeof d === "string" ? d: "\\u" + ("0000" + b.charCodeAt(0).toString(16)).slice( - 4)
    }) : a
}

function ismap(a) {
    return isvalue(a) ? typeof a === "object": false
}
function isempty(b) {
    if (isvalue(b)) if (isarray(b) || isstring(b)) return b.length == 0;
    else if (ismap(b)) {
        var a;
        for (a in b) return false;
        return true
    } else return false;
    return true
}
function isvalid(a) {
    return typeof a !== "undefined" && a !== null ? true: false
}
function isstring(a) {
    return typeof a == "string" || a instanceof String
}
function isnumber(a) {
    return Object.prototype.toString.call(a) === "[object Number]"
}
function isfunction(a) {
    return Object.prototype.toString.call(a) === "[object Function]"
}
function iswindow(a) {
    return a && typeof a === "object" && "setInterval" in a
}
function isarray(a) {
	return isvalue(a)?
		Object.prototype.toString.apply(a)==="[object Array]":false;
}
function trim(a) {
    return a == null ? "": (a + "").replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "")
}

function postSync(d, f) {
    var a = {
        errortype: "9",
        message: "未确认的错误"
    };
    var b = false;
    try {
        var c = {
            dataType: "json",
            processData: true,
            type: "POST",
            async: false,
            global: false,
            error: function(h, k, i) {
                var j = null;
                if (h.status != "200") if (h.status == "12007" || h.status == "12029") j = "服务器连接失败(" + h.status + h.statusText + ").";
                else if (h.status == "12002") j = "服务器连接超时(" + h.status + h.statusText + ").";
                else if (h.status == "12152") j = "连接被服务器关闭(" + h.status + h.statusText + ").";
                else if (h.status == "0") j = "无法连接服务器.";
                else j = "错误码:" + h.status + " 错误文本:" + h.statusText || "";
                else {
                    j = h.responseText;
                    if (!j && i) j = i.message;
                    if (c.dataType == "jsonp") if (powersi.isjson(j)) try {
                        a = $.parseJSON(j);
                        j = null;
                        b = true;
                        return
                    } catch(g) {} else if (j && j.indexOf("was not called") >= 0) j = "服务器不支持jsonp调用"
                }
                if (j) {
                    a.errortype = "9";
                    a.message = j
                }
            },
            success: function(g, i, h) {
                a = g
            }
        };
        if ($.isPlainObject(d)) {
            $.extend(c, d);
            if (f) c.data = f;
            if (c.jsonp) {
                a.errortype = "9";
                a.message = "不支持同步调用jsonp";
                return a
            }
        } else {
            c.url = d;
            c.data = f
        }
        jQuery.ajax(c)
    } catch(e) {
        a.errortype = "9";
        a.message = e.message
    }
    return a
}