// js、css文件所在路径。
$js_path = "./lib/js/lib/";
$csss_path = "./lib/style/";

function isrepeat(str){
	var searchStr = document.getElementsByTagName('head')[0].innerHTML;
	var patt = new RegExp(str);
					//判断是否已经加载此文件
	if(patt.test(searchStr)){return false;}else{return true;}
}

function js_include($script){
	 if(isrepeat($script)){
		var script = document.createElement('script');
		script.src = $js_path + $script;
		script.type = 'text/javascript';
		var head = document.getElementsByTagName('head').item(0);
		head.appendChild(script);
	}
}

function css_include($link){
	if(isrepeat($link)){
	var link = document.createElement('link');
	link.href = $csss_path + $link;
	link.type = 'text/css';
	link.rel = 'stylesheet';
	var head = document.getElementsByTagName('head').item(0);
	head.appendChild(link);
}
}

function iecss_include($link){
    if(isrepeat($link)){
        var link = document.createElement('link');
        link.href = $csss_path + $link;
        link.type = 'text/css';
        link.rel = 'stylesheet';
        var head = document.getElementsByTagName('head').item(0);
        //如果是ie浏览器则加载此css
        if (window.ActiveXObject)head.appendChild(link);
    }
}

//每个tab动态增加css
function css_style($cssStr ,$id){
	if (window.ActiveXObject){	
          //ie下通过字符处理
          // var obj = document.styleSheets[document.styleSheets.length-1];
          var i = 0;
          while( i < document.styleSheets.length){
             if( document.styleSheets[i].id === "modifyskin" )  break;
             i=i+1
          }
          var obj = document.styleSheets[i];
          var pattern = new RegExp("^#modifyskin"+$id+"\\s+{\\r+\\n+\\s+}");
          var cssStr = obj.cssText.toString().replace(pattern ,"#modifyskin" + $id+" {}");
          var isyes = false;
          var i,id;
          id = "#modifyskin" + $id + " {}" ;
          if(cssStr.indexOf(id)!= -1){
              cssStr = cssStr.replace(id ,id + $cssStr);
          }else{
              cssStr = id  + $cssStr + cssStr ;
          }
          obj.cssText = cssStr;
    }else{ //每个tab动态css作为一个文本节点，以nodename区分
   	    var nodes = document.getElementById("modifyskin").childNodes;
		  //用于判断此tab页是否存在文本节点
		  var isyes = false;
		  var i;
	    if(nodes.length != 0){
	    	for(i=0;i<nodes.length;i++){	
	    		  if(nodes[i].nodename == $id){
	    		  	isyes = true;break;
	    		   }
	    		}
	     }
	     if(isyes){
	     	  nodes[i].appendData($cssStr);
	     }else{
	     	   var newnode = document.createTextNode($cssStr);
	         newnode.nodename = $id;
	    	  document.getElementById("modifyskin").appendChild(newnode);
	     }
   	
   	} 
}

 //动态删除当前tab关联的动态css
    var delskin = function($id){
    	if (window.ActiveXObject){
    	    var i = 0,
                startindex = 0, //tab页修改皮肤相关css开始位置
                tempstr = "",     //tab页css开始位置往后的字符串
                tempinsex = 0,   //下一个tab在tempstr中位置
                delstr = "";     //需删除的字符串
            while( i < document.styleSheets.length){
                 if( document.styleSheets[i].id === "modifyskin" )  break;
                 i=i+1
            }
            var obj = document.styleSheets[i];
           // var pattern = new RegExp("^#modifyskin"+$id+"\\s+{\\r+\\n+\\s+}");
            $id = $id.replace("#","");
            startindex = obj.cssText.toString().indexOf("#modifyskin" + $id);
            if(startindex != -1){
                 tempstr =   obj.cssText.toString().substring(startindex);
                 tempindex =  tempstr.indexOf("#modifyskin",2);
                 delstr = (tempindex != -1) ? obj.cssText.toString().substr(startindex,tempindex) :obj.cssText.toString().substr(startindex) ;
                 obj.cssText = obj.cssText.toString().replace(delstr,"");
            }
		}else{
		 	var nodes = document.getElementById("modifyskin").childNodes;
		    if(nodes.length != 0){
		    	for(var i=0;i<nodes.length;i++){
		    		  if(("#"+nodes[i].nodename) == $id){
		    		  	 document.getElementById("modifyskin").removeChild(nodes[i]);break;
		    		   }
		    		}
		     }

		}
   }

//实现demo中源代码展开、折叠切换
function toggle(sourceBar, source) {
	 $(sourceBar).click(function() {
			$(source).toggle("fast");
	 		$(this).toggleClass(function() {
				if ($(this).is('.iconMinus')) {
				    $(this).removeClass("iconMinus");
			    	return 'iconPlus';
			  	} else {
			  	    $(this).removeClass("iconPlus");
			    	return 'iconMinus';
			  	}
			});
	   });
}; 

//需加载的文件
/*
css_include("reset.css");
css_include("layout.css");
css_include("skin/default/theme.css");
css_include("skin/default/components/tree/zTreeStyle.css");
css_include("skin/default/components/jqgrid/jquery.jqgrid.css");
js_include("jquery-all.js");
js_include("validate.js");
js_include("WdatePicker.js");
js_include("widgets.js");
$js_path = "./lib/js/i18n/";
js_include("i18n_zh.js");
$js_path = "./lib/js/lib/";
js_include("tree.js");
js_include("grid.js");
js_include("biz.js");
$js_path = "./";
js_include("indexDepend/jquery.layout.js");
js_include("indexDepend/jquery.contextMenu.js");*/
//js_include("tree/treeDemoData.js");


window.onload = function(){
   $(".sourceBar").live('click',function(){
			$(this).next().toggle("fast");
	 		$(this).toggleClass(function() {
				if ($(this).is('.iconMinus')) {
				    $(this).removeClass("iconMinus");
			    	return 'iconPlus';
			  	} else {
			  	    $(this).removeClass("iconPlus");
			    	return 'iconMinus';
			  	}
			});

  });


	outerLayout = $('body').layout({
		resizerClass: 'ui-state-default',
			center__paneSelector:	".ui-layout-center"
		,	west__paneSelector:		".ui-layout-west"
		,	west__size:				225
		,	east__size:				125
		,	spacing_open:			8 // ALL panes
		,	spacing_closed:			12 // ALL panes
		,	north__spacing_open:	0
		,	south__spacing_open:	0
	});


	var maintab =jQuery('#menutabs','#RightPane').tabs({
        add: function(e, ui) {
            // append close thingy
            $(ui.tab).parents('li:first')
                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="Close Tab"></span>')
                .find('span.ui-tabs-close')
                .click(function() {
                	  var testindex = $('li', maintab).index($(this).parents('li:first')[0]);
                    maintab.tabs('remove', testindex);
                    //删除本tab页中动态设置皮肤的css
                    delskin($(this).prev().attr("href"));
                });

        }
    });
    
	$("#tabUI").contextMenu(
		       {menu: 'myMenu'}, 
			   function(action, el, pos) {
			      var index;
			      var tablength;
                  switch (action) {
									case 'close':
										 index = $('li', maintab).index($(".ui-tabs-selected.ui-state-active"));//用id找
										 maintab.tabs('remove', index);
										 break;
                                    case 'closeOther':
									     tablength = $("li",maintab).length;
									     index = $('li', maintab).index($(".ui-tabs-selected.ui-state-active"));//用id找
									     for(var i=0;i<tablength;){
									        if(i!=index){
										      maintab.tabs('remove', i);
											  tablength--;
											  index = $('li', maintab).index($(".ui-tabs-selected.ui-state-active"));//用id找
										    }else{
											 i++;
											}
									     }
                                        break;
									case 'closeAll':
                                         tablength = $("li",maintab).length;
									     for(var i=0;i<tablength;){
										      maintab.tabs('remove', i);
										      tablength--;
									     }
                                        break;
                  }

			   }
		)

    var loadpage = function(treeId, treeNode){
    	if(treeNode.isParent == false) {
        var st = "#"+ treeNode.ename;
				if($(st).html() != null ) {
					maintab.tabs('select',st);
				} else {
					maintab.tabs('add',st, treeNode.name);
					$(st,"#menutabs").load(treeNode.href);
					maintab.tabs('select',st);
				}

    	}
    };
	var azNodes =[
			{ name:"功能目录", open:true,
				children: [
					{ name:"用户管理", index:"1",ename:"usermanager", href:"user.do"},
					{ name:"竖栏布局", index:"2",ename:"layout2", href:"layout/column.html"},
					{ name:"复杂布局", index:"3",ename:"layout3", href:"layout/complex.html"},
					{ name:"标签布局", index:"4",ename:"layout5", href:"layout/tab.html"},
					{ name:"分组布局", index:"5",ename:"layout7", href:"layout/fieldset.html"}
				]
			}
		];
    var setting = {
            id:"#demotree",
            nodes:azNodes,
			view: {
				selectedMulti: false
			},
			callback: {
				beforeClick: loadpage
			}
		};
	   var	  tempobj = new biz.tree(setting);
	   var loadpage = function(treeId, treeNode){
			if(treeNode.isParent == false) {
			        var st = "#"+ treeNode.ename;
					if($(st).html() != null ) {
						maintab.tabs('select',st);
					} else {
						alert(treeNode.href);
						maintab.tabs('add',st, treeNode.name);
						$(st,"#menutabs").load(treeNode.href);
						maintab.tabs('select',st);
					}

			}
       };
       //如果是chrome浏览器进行提示不支持信息
       if(window.navigator.userAgent.indexOf("Chrome") !== -1 ){
     	  $("#tabs1").append("<p style='font-size:30px;color:red'>暂不支持chrome浏览器，请使用ie或者firefox浏览器!</p>"); 
       }
}