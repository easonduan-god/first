$(function(){
	$(".link-tab").each(function () {
		var url = $(this).attr("href")
		var tabName = $(this).attr("title");
		$(this).attr("href","#");
		$(this).attr("onclick","createTab('"+url+"','"+tabName+"')");
	})
	
	$(".notice-link").each(function () {
		var url = '/system/notice/detail/'+$(this).attr("title");
		//alert(url);
		$(this).attr("onclick","javascript:openNotice('详情','"+url+"')");
	})
});

function createTab(url,tabName){
	createMenuItem(url,tabName);
	//layer.close(index);
}

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    dataIndex = random(1,100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .numberone_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.numberone_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="numberone_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.numberone_iframe').hide().parents('.mainContent').append(str1);
        $("#loadingA",topWindow).click();
        setTimeout(function(){
        	$("#closeloadingA",topWindow).click();
        },300);
        
        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

function random(min, max){
	return Math.floor((Math.random() * max) + min);
}

function showhideoptdiv(divid,optid,ishow)
{//定一个遮罩层临时元素
	var opthtml="<div class='loaderbox'><div class='loading-activity'></div> 数据加载中，请稍后...</div>";
	//console.log(opthtml);
	//$('#led').append(opthtml);
	$(document.body).append(opthtml);
	//console.log(optid);
	if(ishow)
	{//显示
		 //先获取目标的l,t,w,h
		 //console.log($(divid).offset());
		 var sleft=divid.offset().left+"px";
		 var stop=divid.offset().top-5+"px";
		 var swidth=divid.width()+"px";
		 var sheight=divid.height()+5+"px";

		 //console.log(swidth);
		 //console.log(sheight);
		 //将目标的四坐标元素给遮罩层
		$('#'+optid).css("left",sleft);
		$('#'+optid).css("top",stop);
		//$("#mydiv").height(10); 等效于 $("#mydiv").css("height","10px");{ height: "10px", background: "blue" }
		$('#'+optid).css("width",swidth);
		$('#'+optid).css("height",sheight);
		$('#'+optid).css({'display':'block'});
		console.log('遮罩层显示');//opacitybox
		
	}else{//移除
	  $('#'+optid).css({'display':'none'});
	  $('#'+optid).remove();
	}
}

//弹出页面
function openNotice(title, url) {
	var width;
	var height;
	width = 800;
	height = ($(window).height() - 50);
	//如果是移动端，就使用自适应大小弹窗
	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
	    width = 'auto';
	    height = 'auto';
	}
	if (isEmpty(title)) {
        title = false;
    };
	layer.open({
		type: 2,
		area: [width + 'px', height + 'px'],
		fix: false,
		//不固定
		maxmin: true,
		shade: 0.3,
		title: title,
		content: url,
		btn: ['关闭'],
	    // 弹层外区域关闭
		shadeClose: true,
	    cancel: function(index) {
	        return true;
	    }
	});
}

function isEmpty(value) {
    if (value == null || value == "") {
        return true;
    }
    return false;
}
