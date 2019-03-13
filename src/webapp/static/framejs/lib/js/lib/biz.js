 /**
 * @namespace biz
 * @name jdh
 * @version 5.0 rc2
*/

/**
 * 声明biz包
 * @author: jdh
 */
var biz = biz || {version: "5.0 rc2"};



/**
  * @description {Method} init
  * 初始化，获取浏览器、操作系统信息
  * @param {Object}
  */
biz.init=function(win){
	//win = win || window;
    win = window;

	biz.doc=document;

	//win.undefined = win.undefined;

	var ua = win.navigator.userAgent.toLowerCase();

	biz.isIE = ua.indexOf("msie") > -1;
	biz.isIE7 = ua.indexOf("msie 7") > -1;
	biz.isIE8 = ua.indexOf("msie 8") > -1;
	biz.isIE9 = ua.indexOf("msie 9") > -1;

	biz.isFF = ua.indexOf("firefox") > -1 ;
	biz.isFF1 = ua.indexOf("firefox/1") > -1 ;
	biz.isFF2 = ua.indexOf("firefox/2") > -1 ;
	biz.isFF3 = ua.indexOf("firefox/3") > -1 ;

	biz.isOpera = ua.indexOf("opera") > -1;

	biz.isWebkit = (/webkit|khtml/).test(ua);
	biz.isSafari = ua.indexOf("safari") > -1 || biz.isWebkit ;
	biz.isChrome = ua.indexOf("chrome") > -1 || biz.isWebkit ;
	biz.isGecko = biz.isMoz =!biz.isSafari && ua.indexOf("gecko") > -1;

	biz.isStrict = biz.doc.compatMode == "CSS1Compat" || biz.isSafari ;
	biz.isBoxModel = biz.isIE && !biz.isIE8 && !biz.isIE9 && !biz.isStrict ;

	biz.isNotStrictIE = biz.isBoxModel;

	biz.isSecure = win.location.href.toLowerCase().indexOf("https") === 0;


	biz.isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1);
	biz.isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1);
	biz.isLinux = (ua.indexOf("linux") != -1);

};
biz.init();

/**
  * @description {object} utils
  * 工具类，存放常用方法
  */
biz.utils = {
     /**
     * 删除左空格
     * @param arg
     * @return string
     * @since 1.0
     * @author wangl
     */
    ltrim:function(arg) {
        return arg.replace(/^\s*/, "");
    } ,

    /**
     * 删除右空格
     * @param name
     * @return string
     * @since 1.0
     * @author wangl
     */
    rtrim:function(s) {
        return s.replace(/\s*$/, "");
    },
    clone:function(myObj){
      if(typeof(myObj) != 'object') return myObj;
      if(myObj == null) return myObj;

      var myNewObj = new Object();

      for(var i in myObj)
         myNewObj[i] = this.clone(myObj[i]);

      return myNewObj;
    },
    /**
     * 切换页面主题样式，并刷新当前页面
     * @param csstheme 主题名称
     */
    switchTheme:function(csstheme){
        if(csstheme == null || csstheme == "")return;
        $.post("./",{theme:csstheme},function(){
            window.location.href = window.location.href;
        });
    },
     /**
     * 格式id，如包含#则去掉#
     * @param sid id值
     */
    id : function(sid){
        return (sid.indexOf("#") == 0)? (sid.substring(1)):sid;
	},
	/**
	 * 创建隐藏域
	 * @param name 隐藏域的名称
	 * @param value 隐藏域的值
	 * @return hidden element
	 * @since 1.0
	 * @author wangl
	 */
	createHidden:function(name, value) {
	    var input = document.createElement("input");
	    input.type = "hidden";
	    input.id = name;
	    input.name = name;
	    input.value = value;
	    return input;
	},
	/**
	 * ajax请求错误信息处理
	 * @param xhr XMLHttpRequest 对象
	 * @param error 错误信息 捕获的异常对象
	 * @param thrown 捕获的异常对象
	 * @author jdh
	 */
	loadError: function(xhr, error, thrown){
            var $html = xhr.responseText ? xhr.responseText : I18N.ajax_error + I18N.ajax_readyState[xhr.readyState];
           //错误信息以弹窗口方式显示
			var errorDialog = new biz.dialog({
							id:$("<div>"+ $html +"</div>"),
							modal:false,
							width:600,
							height:300,
							title:$.jgrid.errors.server,
							close: function(event, ui){
								errorDialog.destroy();
							}
			});
			errorDialog.open();
     }
}

/**
 * UI基类，所有的UI都应该从这个类中派生出去
 * @name boz.uiBase
 * @grammar biz.uiBase
 * @class
 * @return {biz.uiBase}
 */
biz.uiBase ={
   target : "",
   /**
     * 控件类型：如grid
     */
    uiType : ""
    /**
     *  还可以存放ui组件相关的公关方法，例如从文档中获取指定的DOM元素
     *    getDom:function(id){
     *       if ('string' == typeof id || id instanceof String) {
     *           return document.getElementById(id);
     *       } else if (id && id.nodeName && (id.nodeType == 1 || id.nodeType == 9)) {
     *           return id;
     *       }
     *       return null;
     *     }
     * @returns {HTMLElement|null} 获取的元素，查找不到时返回null,如果参数不合法，直接返回参数
     */
};


/**
 * 创建一个UI控件类
 * @function
 * @grammar biz.createUI(constructor, options)
 * @param {Function} constructor ui控件构造器
 * @param {Object} options 选项
 * @return {Object} ui控件
 */
biz.createUI = function(constructor, options) {
    options = options || {};
    var i,j,n,
        ui = function(opt, _isInherits){// 创建新类的真构造器函数
            var me = this,finalOpt ={};
            opt = opt || {};
            /*
            if(typeof ($("#"+ opt.id).attr("mainId")) !== "undefined"){
               alert("请勿在id为"+ opt.id + "的元素上重复渲染！");
            } */
            //扩展静态配置到this上
            $.extend(me, ui.options);
            //扩展当前options中的项到this上,事件不扩展到me上
            var tempOpt = {};
            for(var i in opt){
            	if(typeof opt[i] !== "function") tempOpt[i] = opt[i];
            }
            $.extend(me, tempOpt);
            //执行控件自己的构造器
            if(typeof opt == "string"){
                return constructor.apply(me, arguments);
            }else{
                var conObj = constructor.apply(me, arguments);
                //如有返回值则使用返回值，否则与defaults深度合并
                finalOpts = conObj?conObj:$.extend(true,{},me.defaults,opt);
            }
            //如属性值为数组[]，通过$.extend()不能覆盖默认值而是合并，此做特殊处理
            for(var para in opt){
               if(typeof opt[para] === 'object' && typeof opt[para].splice==='function'
                   && typeof opt[para].length==='number' && finalOpts[para] !== opt[para])finalOpts[para] = opt[para] ;
            }
            //constructor.apply(me, arguments);
            //自己的构造器可以往ui.options里存放默认属性
           // opt = $.extend(true,{},me.defaults,opt) ;

            //初始化组件
            var tempObj = me.init(finalOpts) ;
            $.extend(me, tempObj);
            //初始化成功后给渲染元素设定其mainid值，标示已经渲染过
            if(typeof tempObj !== "undefined"){
                   $(finalOpts.id).attr("mainId",$(finalOpts.id).attr("id"));
            }else{
                   alert("id为"+ $(finalOpts.id).attr("id") +"的元素ui渲染有误！");
            }

            //执行插件的构造器
            for (i=0, n=ui._addons.length; i<n; i++) {
                ui._addons[i](me);
            }

            //释放默认属性方法内存
            me.defaults = undefined;
            me.methods = undefined;
        },
        C = function(){};

    //继承父类的原型链
    var proto = ui.prototype = new C();

    //继承Base中的方法到prototype中
    for (i in biz.uiBase) {
        proto[i] = biz.uiBase[i];
    }

    /**
     * 扩展控件的prototype,如果是对象则进行合并，否则覆盖
     * @param {Object} json 要扩展进prototype的对象
     * @return {Object} 扩展后的对象
     */
    ui.extend = function(json){
        for (i in json) {
            if(typeof json[i] == "object"){
                ui.prototype[i] = $.extend({},ui.prototype[i]||{},json[i]);
                //如果扩展的是方法，把所有方法绑定到ui对象上
                if(i == "methods"){
                     for( j in json[i]) {
                          ui.prototype[j] = json[i][j];
                     }
                }
            }else{
                ui.prototype[i] = json[i];
            }
        }
        return ui;  // 这个静态方法也返回类对象本身
    };

    //插件支持
    ui._addons = [];
    ui.register = function(f){
        if (typeof f == "function")
            ui._addons.push(f);
    };

    //静态配置支持
    ui.options = {};

    return ui;
};




 /**
 * 创建一个grid控件
 * @function
 * @return {Object} ui控件
 */
var rp_ge = {};
$.extend($.fn.jqGrid,{
	    //增加对showOnLoad（是否初始化时显示查找框）处理
		searchGrid : function (p) {
			p = $.extend({
				recreateFilter: false,
				drag: true,
				sField:'searchField',
				sValue:'searchString',
				sOper: 'searchOper',
				sFilter: 'filters',
				loadDefaults: true, // this options activates loading of default filters from grid's postData for Multipe Search only.
				beforeShowSearch: null,
				afterShowSearch : null,
				onInitializeSearch: null,
				afterRedraw : null,
				afterChange: null,
				closeAfterSearch : false,
				closeAfterReset: false,
				closeOnEscape : false,
				searchOnEnter : false,
				multipleSearch : false,
				multipleGroup : false,
				//cloneSearchRowOnAdd: true,
				top : 0,
				left: 0,
				jqModal : true,
				modal: false,
				resize : true,
				width: 450,
				height: 'auto',
				dataheight: 'auto',
				showQuery: false,
				errorcheck : true,
				// translation
				// if you want to change or remove the order change it in sopt
				// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc'],
				sopt: null,
				stringResult: undefined,
				onClose : null,
				onSearch : null,
				onReset : null,
				toTop : true,
				overlay : 30,
				columns : [],
				tmplNames : null,
				tmplFilters : null,
				// translations - later in lang file
				tmplLabel : ' Template: ',
				showOnLoad: false,
				layer: null
			}, $.jgrid.search, p || {});
			return this.each(function() {
				var $t = this;
				if(!$t.grid) {return;}
				var fid = "fbox_"+$t.p.id,
				showFrm = true,
				IDs = {themodal:'searchmod'+fid,modalhead:'searchhd'+fid,modalcontent:'searchcnt'+fid, scrollelm : fid},
				defaultFilters  = $t.p.postData[p.sFilter];
				if(typeof(defaultFilters) === "string") {
					defaultFilters = $.jgrid.parse( defaultFilters );
				}
				if(p.recreateFilter === true) {
					$("#"+$.jgrid.jqID(IDs.themodal)).remove();
				}
				function showFilter(_filter) {
					showFrm = $($t).triggerHandler("jqGridFilterBeforeShow", [_filter]);
					if(typeof(showFrm) === "undefined") {
						showFrm = true;
					}
					if(showFrm && $.isFunction(p.beforeShowSearch)) {
						showFrm = p.beforeShowSearch.call($t,_filter);
					}
					if(showFrm) {
						$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(fid),jqm:p.jqModal, modal:p.modal, overlay: p.overlay, toTop: p.toTop});
						$($t).triggerHandler("jqGridFilterAfterShow", [_filter]);
						if($.isFunction(p.afterShowSearch)) {
							p.afterShowSearch.call($t, _filter);
						}
					}
				}
				if ( $("#"+$.jgrid.jqID(IDs.themodal)).html() !== null ) {
					showFilter($("#fbox_"+$.jgrid.jqID(+$t.p.id)));
				} else {
					var fil = $("<div><div id='"+fid+"' class='searchFilter' style='overflow:auto'></div></div>").insertBefore("#gview_"+$.jgrid.jqID($t.p.id)),
					align = "left", butleft =""; 
					if($t.p.direction == "rtl") {
						align = "right";
						butleft = " style='text-align:left'";
						fil.attr("dir","rtl");
					}
					var columns = $.extend([],$t.p.colModel),
					bS  ="<a href='javascript:void(0)' id='"+fid+"_search' class='fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset'><span class='ui-icon ui-icon-search'></span>"+p.Find+"</a>",
					bC  ="<a href='javascript:void(0)' id='"+fid+"_reset' class='fm-button ui-state-default ui-corner-all fm-button-icon-left ui-search'><span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>"+p.Reset+"</a>",
					bQ = "", tmpl="", colnm, found = false, bt, cmi=-1;
					if(p.showQuery) {
						bQ ="<a href='javascript:void(0)' id='"+fid+"_query' class='fm-button ui-state-default ui-corner-all fm-button-icon-left'><span class='ui-icon ui-icon-comment'></span>Query</a>";
					}
					if(!p.columns.length) {
						$.each(columns, function(i,n){
							if(!n.label) {
								n.label = $t.p.colNames[i];
							}
							// find first searchable column and set it if no default filter
							if(!found) {
								var searchable = (typeof n.search === 'undefined') ?  true: n.search ,
								hidden = (n.hidden === true),
								ignoreHiding = (n.searchoptions && n.searchoptions.searchhidden === true);
								if ((ignoreHiding && searchable) || (searchable && !hidden)) {
									found = true;
									colnm = n.index || n.name;
									cmi =i;
								}
							}
						});
					} else {
						columns = p.columns;
					}
					// old behaviour
					if( (!defaultFilters && colnm) || p.multipleSearch === false  ) {
						var cmop = "eq";
						if(cmi >=0 && columns[cmi].searchoptions && columns[cmi].searchoptions.sopt) {
							cmop = columns[cmi].searchoptions.sopt[0];
						} else if(p.sopt && p.sopt.length) {
							cmop = p.sopt[0];
						}
						defaultFilters = {"groupOp": "AND",rules:[{"field":colnm,"op":cmop,"data":""}]};
					}
					found = false;
					if(p.tmplNames && p.tmplNames.length) {
						found = true;
						tmpl = p.tmplLabel;
						tmpl += "<select class='ui-template'>";
						tmpl += "<option value='default'>Default</option>";
						$.each(p.tmplNames, function(i,n){
							tmpl += "<option value='"+i+"'>"+n+"</option>";
						});
						tmpl += "</select>";
					}
	
					bt = "<table class='EditTable' style='border:0px none;margin-top:5px' id='"+fid+"_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='EditButton' style='text-align:"+align+"'>"+bC+tmpl+"</td><td class='EditButton' "+butleft+">"+bQ+bS+"</td></tr></tbody></table>";
					fid = $.jgrid.jqID( fid);
					$("#"+fid).jqFilter({
						columns : columns,
						filter: p.loadDefaults ? defaultFilters : null,
						showQuery: p.showQuery,
						errorcheck : p.errorcheck,
						sopt: p.sopt,
						groupButton : p.multipleGroup,
						ruleButtons : p.multipleSearch,
						afterRedraw : p.afterRedraw,
						_gridsopt : $.jgrid.search.odata,
						ajaxSelectOptions: $t.p.ajaxSelectOptions,
						groupOps: p.groupOps,
						onChange : function() {
							if(this.p.showQuery) {
								$('.query',this).html(this.toUserFriendlyString());
							}
							if ($.isFunction(p.afterChange)) {
								p.afterChange.call($t, $("#"+fid), p);
							}
						},
						direction : $t.p.direction
					});
					fil.append( bt );
					if(found && p.tmplFilters && p.tmplFilters.length) {
						$(".ui-template", fil).bind('change', function(){
							var curtempl = $(this).val();
							if(curtempl=="default") {
								$("#"+fid).jqFilter('addFilter', defaultFilters);
							} else {
								$("#"+fid).jqFilter('addFilter', p.tmplFilters[parseInt(curtempl,10)]);
							}
							return false;
						});
					}
					if(p.multipleGroup === true) {p.multipleSearch = true;}
					$($t).triggerHandler("jqGridFilterInitialize", [$("#"+fid)]);
					if($.isFunction(p.onInitializeSearch) ) {
						p.onInitializeSearch.call($t, $("#"+fid));
					}
					p.gbox = "#gbox_"+fid;
					if (p.layer) {
						$.jgrid.createModal(IDs ,fil,p,"#gview_"+$.jgrid.jqID($t.p.id),$("#gbox_"+$.jgrid.jqID($t.p.id))[0], "#"+$.jgrid.jqID(p.layer), {position: "relative"});
					} else {
						$.jgrid.createModal(IDs ,fil,p,"#gview_"+$.jgrid.jqID($t.p.id),$("#gbox_"+$.jgrid.jqID($t.p.id))[0]);
					}
					if (p.searchOnEnter || p.closeOnEscape) {
						$("#"+$.jgrid.jqID(IDs.themodal)).keydown(function (e) {
							var $target = $(e.target);
							if (p.searchOnEnter && e.which === 13 && // 13 === $.ui.keyCode.ENTER
									!$target.hasClass('add-group') && !$target.hasClass('add-rule') &&
									!$target.hasClass('delete-group') && !$target.hasClass('delete-rule') &&
									(!$target.hasClass("fm-button") || !$target.is("[id$=_query]"))) {
								$("#"+fid+"_search").focus().click();
								return false;
							}
							if (p.closeOnEscape && e.which === 27) { // 27 === $.ui.keyCode.ESCAPE
								$("#"+$.jgrid.jqID(IDs.modalhead)).find(".ui-jqdialog-titlebar-close").focus().click();
								return false;
							}
						});
					}
					if(bQ) {
						$("#"+fid+"_query").bind('click', function(){
							$(".queryresult", fil).toggle();
							return false;
						});
					}
					if (p.stringResult===undefined) {
						// to provide backward compatibility, inferring stringResult value from multipleSearch
						p.stringResult = p.multipleSearch;
					}
					$("#"+fid+"_search").bind('click', function(){
						var fl = $("#"+fid),
						sdata={}, res ,
						filters = fl.jqFilter('filterData');
						if(p.errorcheck) {
							fl[0].hideError();
							if(!p.showQuery) {fl.jqFilter('toSQLString');}
							if(fl[0].p.error) {
								fl[0].showError();
								return false;
							}
						}
	
						if(p.stringResult) {
							try {
								// xmlJsonClass or JSON.stringify
								res = xmlJsonClass.toJson(filters, '', '', false);
							} catch (e) {
								try {
									res = JSON.stringify(filters);
								} catch (e2) { }
							}
							if(typeof(res)==="string") {
								sdata[p.sFilter] = res;
								$.each([p.sField,p.sValue, p.sOper], function() {sdata[this] = "";});
							}
						} else {
							if(p.multipleSearch) {
								sdata[p.sFilter] = filters;
								$.each([p.sField,p.sValue, p.sOper], function() {sdata[this] = "";});
							} else {
								sdata[p.sField] = filters.rules[0].field;
								sdata[p.sValue] = filters.rules[0].data;
								sdata[p.sOper] = filters.rules[0].op;
								sdata[p.sFilter] = "";
							}
						}
						$t.p.search = true;
						$.extend($t.p.postData,sdata);
						$($t).triggerHandler("jqGridFilterSearch");
						if($.isFunction(p.onSearch) ) {
							p.onSearch.call($t);
						}
						$($t).trigger("reloadGrid",[{page:1}]);
						if(p.closeAfterSearch) {
							$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:p.jqModal,onClose: p.onClose});
						}
						return false;
					});
					$("#"+fid+"_reset").bind('click', function(){
						var sdata={},
						fl = $("#"+fid);
						$t.p.search = false;
						if(p.multipleSearch===false) {
							sdata[p.sField] = sdata[p.sValue] = sdata[p.sOper] = "";
						} else {
							sdata[p.sFilter] = "";
						}
						fl[0].resetFilter();
						if(found) {
							$(".ui-template", fil).val("default");
						}
						$.extend($t.p.postData,sdata);
						$($t).triggerHandler("jqGridFilterReset");
						if($.isFunction(p.onReset) ) {
							p.onReset.call($t);
						}
						$($t).trigger("reloadGrid",[{page:1}]);
						return false;
					});
					//增加对showOnLoad处理
					if(p.showOnLoad)showFilter($("#"+fid));
					$(".fm-button:not(.ui-state-disabled)",fil).hover(
						function(){$(this).addClass('ui-state-hover');},
						function(){$(this).removeClass('ui-state-hover');}
					);
				}
			});
		},
		//增加自定义按钮，处理默认按钮样式
		navButtonAdd : function (elem, p) {
	        p = $.extend({
	            caption : "newButton",
	            title: '',
	            buttonicon : 'ui-icon-newwin',
	            onClickButton: null,
	            position : "last",
	            cursor : 'pointer'
	        }, p ||{});
	        return this.each(function() {
	            if( !this.grid)  {return;}
	            if( typeof elem === "string" && elem.indexOf("#") !== 0) {elem = "#"+$.jgrid.jqID(elem);}
	            var findnav = $(".navtable",elem)[0], $t = this;
	            if (findnav) {
	                if( p.id && $("#"+$.jgrid.jqID(p.id), findnav).html() !== null )  {return;}
	                var tbd = $("<td></td>");
	                if(p.buttonicon.toString().toUpperCase() == "NONE") {
	                    $(tbd).addClass('ui-pg-button ui-corner-all').append("<div class='ui-pg-div'>"+p.caption+"</div>");
	                } else	{
	                    $(tbd).addClass('ui-pg-button ui-corner-all').append("<div class='ui-pg-div'><span class='ui-icon "+p.buttonicon+"'></span>"+p.caption+"</div>");
	                }
	                if(p.id) {$(tbd).attr("id",p.id);}
	                if(p.position=='first'){
	                    if(findnav.rows[0].cells.length ===0 ) {
	                        $("tr",findnav).append(tbd);
	                    } else {
	                        $("tr td:eq(0)",findnav).before(tbd);
	                    }
	                } else {
	                    $("tr",findnav).append(tbd);
	                }
	                $(tbd,findnav)
	                .attr("title",p.title  || "")
	                .click(function(e){
	                    if (!$(this).hasClass('ui-state-disabled')) {
	                        if ($.isFunction(p.onClickButton) ) {p.onClickButton.call($t,e);}
	                    }
	                    return false;
	                })
	                .hover(
	                    function () {
	                        if (!$(this).hasClass('ui-state-disabled')) {
	                            $(this).addClass('ui-state-hover');
	                        }
	                    },
	                    function () {$(this).removeClass("ui-state-hover");}
	                );
	                $(".ui-pg-div",tbd).button();//按钮渲染成button
	            }
	        });
	    },
		//多表头，添加保存所有表头信息属性处理
		setGroupHeaders : function ( o ) {
			o = $.extend({
				useColSpanStyle :  false,
				groupHeaders: []
			},o  || {});
			return this.each(function(){
				this.p.groupHeader = o;
				this.p.totalGroupHeader.push(o);//保存所有多表头信息
				var ts = this,
				i, cmi, skip = 0, $tr, $colHeader, th, $th, thStyle,
				iCol,
				cghi,
				//startColumnName,
				numberOfColumns,
				titleText,
				cVisibleColumns,
				colModel = ts.p.colModel,
				cml = colModel.length,
				ths = ts.grid.headers,
				$htable = $("table.ui-jqgrid-htable", ts.grid.hDiv),
				$trLabels = $htable.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header"),
				$thead = $htable.children("thead"),
				$theadInTable,
				$firstHeaderRow = $htable.find(".jqg-first-row-header");
				if($firstHeaderRow.html() === null) {
					$firstHeaderRow = $('<tr>', {role: "row", "aria-hidden": "true"}).addClass("jqg-first-row-header").css("height", "auto");
				} else {
					$firstHeaderRow.empty();
				}
				var $firstRow,
				inColumnHeader = function (text, columnHeaders) {
					var i = 0, length = columnHeaders.length;
					for (; i < length; i++) {
						if (columnHeaders[i].startColumnName === text) {
							return i;
						}
					}
					return -1;
				};
	
				$(ts).prepend($thead);
				$tr = $('<tr>', {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");
				for (i = 0; i < cml; i++) {
					th = ths[i].el;
					$th = $(th);
					cmi = colModel[i];
					// build the next cell for the first header row
					thStyle = { height: '0px', width: ths[i].width + 'px', display: (cmi.hidden ? 'none' : '')};
					//多表头标题栏第一行决定标题列宽度，绑上指定id用于自适应模式在setGridWidth中给其设置高度
					$("<th>", {role: 'gridcell',id:ts.grid.headers[i].el.id}).css(thStyle).addClass("ui-first-th-"+ts.p.direction).appendTo($firstHeaderRow);
	
					th.style.width = ""; // remove unneeded style
					iCol = inColumnHeader(cmi.name, o.groupHeaders);
					if (iCol >= 0) {
						cghi = o.groupHeaders[iCol];
						numberOfColumns = cghi.numberOfColumns;
						titleText = cghi.titleText;
	
						// caclulate the number of visible columns from the next numberOfColumns columns
						for (cVisibleColumns = 0, iCol = 0; iCol < numberOfColumns && (i + iCol < cml); iCol++) {
							if (!colModel[i + iCol].hidden) {
								cVisibleColumns++;
							}
						}
	
						// The next numberOfColumns headers will be moved in the next row
						// in the current row will be placed the new column header with the titleText.
						// The text will be over the cVisibleColumns columns
						$colHeader = $('<th>').attr({role: "columnheader"})
							.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
							.css({'height':'22px', 'border-top': '0px none'})
							.html(titleText);
						if(cVisibleColumns > 0) {
							$colHeader.attr("colspan", String(cVisibleColumns));
						}
						if (ts.p.headertitles) {
							$colHeader.attr("title", $colHeader.text());
						}
						// hide if not a visible cols
						if( cVisibleColumns === 0) {
							$colHeader.hide();
						}
	
						$th.before($colHeader); // insert new column header before the current
						$tr.append(th);         // move the current header in the next row
	
						// set the coumter of headers which will be moved in the next row
						skip = numberOfColumns - 1;
					} else {
						if (skip === 0) {
							if (o.useColSpanStyle) {
								// expand the header height to two rows
								$th.attr("rowspan", "2");
							} else {
								$('<th>', {role: "columnheader"})
									.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
									.css({"display": cmi.hidden ? 'none' : '', 'border-top': '0px none'})
									.insertBefore($th);
								$tr.append(th);
							}
						} else {
							// move the header to the next row
							//$th.css({"padding-top": "2px", height: "19px"});
							$tr.append(th);
							skip--;
						}
					}
				}
				$theadInTable = $(ts).children("thead");
				$theadInTable.prepend($firstHeaderRow);
				$tr.insertAfter($trLabels);
				$htable.append($theadInTable);
	
				if (o.useColSpanStyle) {
					// Increase the height of resizing span of visible headers
					$htable.find("span.ui-jqgrid-resize").each(function () {
						var $parent = $(this).parent();
						if ($parent.is(":visible")) {
							this.style.cssText = 'height: ' + $parent.height() + 'px !important; cursor: col-resize;';
						}
					});
	
					// Set position of the sortable div (the main lable)
					// with the column header text to the middle of the cell.
					// One should not do this for hidden headers.
					$htable.find("div.ui-jqgrid-sortable").each(function () {
						var $ts = $(this), $parent = $ts.parent();
						if ($parent.is(":visible") && $parent.is(":has(span.ui-jqgrid-resize)")) {
							$ts.css('top', ($parent.height() - $ts.outerHeight()) / 2 + 'px');
						}
					});
				}
	
				$firstRow = $theadInTable.find("tr.jqg-first-row-header");
				$(ts).bind('jqGridResizeStop.setGroupHeaders', function (e, nw, idx) {
					$firstRow.find('th').eq(idx).width(nw);
				});
			});				
		},
		//导出excel
		excelExport: function(queryObj){
	            var params={};
	            params.queryData = queryObj;
	            params.orderFields=this.getGridParam("sortname");
	            params.order=this.getGridParam("sortorder");
	            
	            if(this.exportParams.columns===undefined){
	            	params.columns=this.getGridParam("colModel");
	            }
	            
	            if(this.exportParams.groupHeaders===undefined){
	            	params.groupHeaders=this.getGridParam("totalGroupHeader");
	            }
	
	            $.extend(true,params,this.exportParams);
	            var form=$("<form>");
	            form.attr("action",params.url);
	            form.attr("method","post");
	            form.append( biz.utils.createHidden("orderFields",params.orderFields));
	            form.append( biz.utils.createHidden("order",params.order));
	            params.orderFields=undefined;
	            params.order=undefined;
	            if(queryObj){
	                for(var q in queryObj){
	                    form.append( biz.utils.createHidden(q,queryObj[q]));
	                }
	            }
	            form.append( biz.utils.createHidden("exportParams",JSON.stringify(params)));
	            $("body").append(form);
	            form.submit();
	            form.detach();
	    },
		//当数据分组设置合并单元格时，分组列始终显示
		groupingSetup : function () {
			return this.each(function (){
				var $t = this,
				grp = $t.p.groupingView;
				if(grp !== null && ( (typeof grp === 'object') || $.isFunction(grp) ) ) {
					if(!grp.groupField.length) {
						$t.p.grouping = false;
					} else {
						if ( typeof(grp.visibiltyOnNextGrouping) === 'undefined') {
							grp.visibiltyOnNextGrouping = [];
						}
	
						grp.lastvalues=[];
						grp.groups =[];
						grp.counters =[];
						for(var i=0;i<grp.groupField.length;i++) {
							if(!grp.groupOrder[i]) {
								grp.groupOrder[i] = 'asc';
							}
							if(!grp.groupText[i]) {
								grp.groupText[i] = '{0}';
							}
							if( typeof(grp.groupColumnShow[i]) !== 'boolean' || grp.mergeCell) {
								grp.groupColumnShow[i] = true;
							}
							if( typeof(grp.groupSummary[i]) !== 'boolean') {
								grp.groupSummary[i] = false;
							}
							if(grp.groupColumnShow[i] === true) {
								grp.visibiltyOnNextGrouping[i] = true;
								$($t).jqGrid('showCol',grp.groupField[i]);
							} else {
								grp.visibiltyOnNextGrouping[i] = $("#"+$.jgrid.jqID($t.p.id+"_"+grp.groupField[i])).is(":visible");
								$($t).jqGrid('hideCol',grp.groupField[i]);
							}
						}
						grp.summary =[];
						var cm = $t.p.colModel;
						for(var j=0, cml = cm.length; j < cml; j++) {
							if(cm[j].summaryType) {
								grp.summary.push({nm:cm[j].name,st:cm[j].summaryType, v: '', sr: cm[j].summaryRound, srt: cm[j].summaryRoundType || 'round'});
							}
						}
					}
				} else {
					$t.p.grouping = false;
				}
			});
		},
	    //数据分组增加合并单元格处理模式
		groupingRender : function (grdata, colspans ) {
			return this.each(function(){
				var $t = this,
				grp = $t.p.groupingView,
				str = "", icon = "", hid, clid, pmrtl = grp.groupCollapse ? grp.plusicon : grp.minusicon, gv, cp=[], ii, len =grp.groupField.length;
				pmrtl += " tree-wrap-"+$t.p.direction; 
				ii = 0;
				$.each($t.p.colModel, function (i,n){
					for(var ii=0;ii<len;ii++) {
						if(grp.groupField[ii] === n.name ) {
							cp[ii] = i;
							break;
						}
					}
				});
				var toEnd = 0;
				function findGroupIdx( ind , offset, grp) {
					if(offset===0) {
						return grp[ind];
					} else {
						var id = grp[ind].idx;
						if(id===0) { return grp[ind]; }
						for(var i=ind;i >= 0; i--) {
							if(grp[i].idx === id-offset) {
								return grp[i];
							}
						}
					}
				}
				var sumreverse = grp.groupSummary;
				sumreverse.reverse();
				$.each(grp.groups,function(i,n){
					toEnd++;
					clid = $t.p.id+"ghead_"+n.idx;
					hid = clid+"_"+i;
					icon = "<span style='cursor:pointer;' class='ui-icon "+pmrtl+"' onclick=\"jQuery('#"+$.jgrid.jqID($t.p.id)+"').jqGrid('groupingToggle','"+hid+"');return false;\"></span>";
					try {
						gv = $t.formatter(hid, n.value, cp[n.idx], n.value );
					} catch (egv) {
						gv = n.value;
					}
					if( !grp.mergeCell)   //对设置合并单元格模式进行处理
					str += "<tr id=\""+hid+"\" role=\"row\" class= \"ui-widget-content jqgroup ui-row-"+$t.p.direction+" "+clid+"\"><td style=\"padding-left:"+(n.idx * 12) + "px;"+"\" colspan=\""+colspans+"\">"+icon+$.jgrid.template(grp.groupText[n.idx], gv, n.cnt, n.summary)+"</td></tr>";
					var leaf = len-1 === n.idx; 
					if( leaf ) {
						var gg = grp.groups[i+1];
						var end = gg !== undefined ?  grp.groups[i+1].startRow : grdata.length;
						for(var kk=n.startRow;kk<end;kk++) {
							str += grdata[kk].join('');
						}
						var jj;
						if (gg !== undefined) {
							for (jj = 0; jj < grp.groupField.length; jj++) {
								if (gg.dataIndex === grp.groupField[jj]) {
									break;
								}
							}
							toEnd = grp.groupField.length - jj;
						}
						for (var ik = 0; ik < toEnd; ik++) {
							if(!sumreverse[ik]) { continue; }
							var hhdr = "";
							if(grp.groupCollapse && !grp.showSummaryOnHide) {
								hhdr = " style=\"display:none;\"";
							}
							str += "<tr"+hhdr+" role=\"row\" class=\"ui-widget-content jqfoot ui-row-"+$t.p.direction+"\">";
							var fdata = findGroupIdx(i, ik, grp.groups),
							cm = $t.p.colModel,
							vv, grlen = fdata.cnt;
							for(var k=0; k<colspans;k++) {
								var tmpdata = "<td "+$t.formatCol(k,1,'')+">&#160;</td>",
								tplfld = "{0}";
								$.each(fdata.summary,function(){
									if(this.nm === cm[k].name) {
										if(cm[k].summaryTpl)  {
											tplfld = cm[k].summaryTpl;
										}
										if(this.st.toLowerCase() === 'avg') {
											if(this.v && grlen > 0) {
												this.v = (this.v/grlen);
											}
										}
										try {
											vv = $t.formatter('', this.v, k, this);
										} catch (ef) {
											vv = this.v;
										}
										tmpdata= "<td "+$t.formatCol(k,1,'')+">"+$.jgrid.format(tplfld,vv)+ "</td>";
										return false;
									}
								});
								str += tmpdata;
							}
							str += "</tr>";
						}
						toEnd = jj;
					}
				});
				$("#"+$.jgrid.jqID($t.p.id)+" tbody:first").append(str);
				// free up memory
				str = null;
				$.each(grp.groups,function(i,n){
					//对设置合并单元格模式进行处理
					 if( grp.mergeCell) {
						 /*
						 *获取分组列当前组的单元格：通过条件大于（gt）起始单元格同时小于（lt）结束单元格获得；
						 *strtRow为起始序号，cnt为此组总单元格树；设置数据汇总每分组末尾增加一行，获取ceel时需考虑在内
						 */
						var cell = $("#"+$.jgrid.jqID($t.p.id)+" td[aria-describedby=\""+$t.p.id+'_'+n.dataIndex+"\"]:lt("
								  + parseInt(grp.groups[i].summary.length>0 ?(grp.groups[i].startRow+grp.groups[i].cnt+i):(grp.groups[i].startRow+grp.groups[i].cnt)) +")"
								  + ((grp.groups[i].startRow == 0)?"":(":gt(" + String(parseInt(grp.groups[i].summary.length>0 ?(grp.groups[i].startRow+i-1):(grp.groups[i].startRow-1)))+")")) );
						var k =true;
						$.each(cell,function(j,m){
							//分组列第一个单元格设置合并，相同组其他单元格隐藏
							if(($(m).text() == grp.groups[i].value) && (n.cnt>1))  {
							   if(k) {
								   $(m).attr("rowspan",n.cnt) ;
								   k = false;
							   }else{
								   $(m).hide();
							   }
							}
						})
					}
				})
			});
		},
		//合并单元格
	    mergeCells:function(opt){
	        var $t = this[0],
	            cellfuc = $.isFunction(opt.valuefunc) ? true : false;
	        opt = $.extend({rowid:"",column:"",rowspan:1,colspan:1,valuefunc:null},opt) ;
	        if(opt.rowid===""||$("#"+opt.rowid,this).size()==0){ return;}
	        if(opt.rowspan==1&&opt.colspan==1){ return;}
	        var celltext=cellfuc?opt.valuefunc.call(this,opt.rowid,opt.column) : $($t).jqGrid("getRowData",opt.rowid)[opt.column];
	        var tr = $( "tr[id="+opt.rowid+"]",$t);
	        var td = $( "tr[id="+opt.rowid+"]"+" td[aria-describedby=\""+$t.p.id+'_'+opt.column+"\"]",$t);
	        td.attr("rowspan",opt.rowspan).attr("colspan",opt.colspan);
	        td.addClass("grid-td-merged");
	        td.html(celltext);
	        for(var i=1;i<opt.colspan;i++){
	            td=td.next();
	            td.hide();
	           // td.val(celltext);
	        }
	        for(var i=1;i<opt.rowspan;i++){
	            tr=tr.next();
	            var td=tr.find("td[aria-describedby=\""+$t.p.id+'_'+opt.column+"\"]").hide();
	            td.val(celltext);
	            for(var j=1;j<opt.colspan;j++){
	                 td=td.next();
	                 td.hide();
	               //  td.val(celltext);
	            }
	        }
	    },
        //保存当前处于编辑状态的单元格
        saveEditCell:function(){
            return this.each(function() {
                var $t = this;
                if (!$t.grid || $t.p.cellEdit !== true) {return;}
                if ($t.p.savedRow.length>0) {
                    $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
                }
            })
        },
        //获取新增数据
        getAdded:function(){
             var  insertrow=[],jsonText;
            this.each(function(){
               var  $t = this,
                    editrowobj ,
                    delids =  $t.p._delrowid,
                    newidrule = $t.p.addParams.rowID;
               //获取之前保存编辑状态的单元格
               if ($t.p.savedRow.length>0) {
                   $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
               }
               //获取编辑的数据
               editrowobj =  jQuery($t).jqGrid('getChangedCells','all');
               for(var i=0;i<$t.p.colModel.length;i++){
                    if($t.p.colModel[i].name == "id") {
                        isid  =   true ;
                        break;
                    }
               };
               for(var i=0;i<editrowobj.length;i++){
                  if(editrowobj[i].id.search(newidrule) !== -1){
                       //新增数据主键值恢复为空 ，不传到后台
                       // var ids = $.inArray("id", editrowobj[i]);
                       // editrowobj[i].splice( ids ,1 );
                       insertrow.push(editrowobj[i])  ;
                   }
               };
            })
             //序列化json对象
             jsonText = JSON.stringify(insertrow) ;
             return  jsonText;
        },
        //获取编辑数据
        getEdited:function(){
             var   editrow=[],jsonText,isid=false;
            this.each(function(){
            
               var  $t = this,
                    editrowobj ,
                    delids =  $t.p._delrowid,
                    newidrule = $t.p.addParams.rowID;
               //获取之前保存编辑状态的单元格
               if ($t.p.savedRow.length>0) {
                   $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
               }
               //获取编辑的数据
               editrowobj =  jQuery($t).jqGrid('getChangedCells','all');
               for(var i=0;i<$t.p.colModel.length;i++){
                    if($t.p.colModel[i].name == "id") {
                        isid  =   true ;
                        break;
                    }
               };
               for(var i=0;i<editrowobj.length;i++){
                  if(editrowobj[i].id.search(newidrule) == -1){
                       editrow.push(editrowobj[i])  ;
                   }
                    //如果列模型没定义id 去掉id属性
                   if(!isid){
                       editrowobj[i].id = undefined;
                   }
               };
            })
             //序列化json对象
             jsonText = JSON.stringify(editrow) ;
             return  jsonText ;
        },
        //获取删除数据
        getDeled:function(){
             var   deleteRows=[],jsonText,delids;
            this.each(function(){
               var  $t = this,
                    newidrule = $t.p.addParams.rowID;
                    delids =  $t.p._delrowid;
               //获取之前保存编辑状态的单元格
                if ($t.p.savedRow.length>0) {
                    $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
                }
                //如果删除数据为新增数据，则不提交到服务器端
                for(var j=0;j<delids.length;j++){
                   var c = delids[j].search(newidrule);
                   if(delids[j].search(newidrule) !== -1){
                       delids.splice( j ,1 );
                       --j;
                   };
                };
            })
             //序列化json对象
             jsonText = JSON.stringify(delids) ;
             return  jsonText ;
        },
        // 获取grid中编辑、新增、删除的json数据
		// -- by zhanghp 20120804 增加参数validated bool类型，默认值 true
		// 如果设置为false，不进行数据验证就进行提交数据获取操作
        getJsontosubmit:function(custompara,validated){
                var  insertrow=[] , editrow=[],editrowobj, delrow=[],$t= this[0],jsonText,isid=false,
                     targetjson = {addRows:"",updateRows:"",deleteRows:""},
                     delids =  $t.p._delrowid,
                     newidrule = $t.p.addParams.rowID;
                //设置之前保存编辑状态的单元格
                if ($t.p.savedRow.length>0) {
                    $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
                }
                //获取编辑的数据
                editrowobj =  jQuery($t).jqGrid('getChangedCells','all');
                for(var i=0;i<$t.p.colModel.length;i++){
                    if($t.p.colModel[i].name == "id") {
                        isid  =   true ;
                        break;
                    }
                };
                for(var i=0;i<editrowobj.length;i++){
                   if($("#"+editrowobj[i].id,this.id).hasClass("added")){
                       //新增数据主键值恢复为空 ，不传到后台
                       // var ids = $.inArray("id", editrowobj[i]);
                       // editrowobj[i].splice( ids ,1 );
                       insertrow.push(editrowobj[i])  ;
                   } else{
                       editrow.push(editrowobj[i])  ;
                   };
                   //如果列模型没定义id 去掉id属性
                   if(!isid){
                       editrowobj[i].id = undefined;
                   }
                };

                //如果删除数据为新增数据，则不提交到服务器端
                for(var j=0;j<delids.length;j++){
                   var c = delids[j].search(newidrule);
                   if(delids[j].search(newidrule) !== -1){
                       delids.splice( j ,1 );
                       --j;
                   };
                };
                targetjson.addRows =  insertrow;
                targetjson.updateRows =  editrow;
                targetjson.deleteRows =  delids;
                
                // --by zhanghp 2012/08/07 start
				// 增加验证
				if ('undefined' == typeof validated || true == validated) {
					var isValid = this.checkGridData(targetjson);
					if (!isValid[0][0]){
						// showWarn(isValid[0][1],3000);
						new biz.alert({type:"alert",message:isValid[0][1],title:I18N.warn}) ;
						new biz.alert({type:"hide",times:3000}) ;
						return null;
					}
				}
				// --by zhanghp 2010/08/07 end
                
                if (typeof custompara != 'undefined') {
                     targetjson.custompara = custompara;
                };

                //序列化json对象
                jsonText = JSON.stringify(targetjson) ;
                return  jsonText;
        } ,
		/**
		 * 验证Grid组件提交的数据，一般来源于getJsonToSubmit方法
		 * 
		 * @param {string |
		 *            object} data 是被验证的数据，可以是Json格式字符串或者Js对象
		 * @return {object[]}
		 *         验证通过，返回验证信息，如果验证通过返回[[true,""]]失败返回[[false,"验证失败消息"],....]
		 * @author zhanghp 2012-08-07
		 */
		checkGridData : function(data) {
			// 如果传进来的是String，转换为Js对象
			var oData = data;
			if ("string" == typeof(data)) {
				oData = JSON.parse(data);
			}
			var $t = this[0];
			var result = [];
			for (var i = 0; i < oData.addRows.length; i++) {
				for (var j = 0; j < $t.p.colModel.length; j++) {
					var cv = $.jgrid.checkValues(
						oData.addRows[i][$t.p.colModel[j].name], j, $t,$t.p.colModel[j].editrules,$t.p.colNames[j],false);
					if (!cv[0])
						result.push(cv);
				}

			}
			for (var i = 0; i < oData.updateRows.length; i++) {
				for (var j = 0; j < $t.p.colModel.length; j++) {
					var cv = $.jgrid.checkValues(
						oData.updateRows[i][$t.p.colModel[j].name], j, $t,$t.p.colModel[j].editrules,$t.p.colNames[j],false);
					if (!cv[0])
						result.push(cv);
				}

			}
			if (0 == result.length)
				return [[true, ""]];
			return result;
		} ,
		// --by zhanghp 2012/08/07 end
        //获取编辑过得单元格值
        getChangedCells : function (mthd) {
		var ret=[];
		if (!mthd) {mthd='all';}
		this.each(function(){
			var $t= this,nm;
			if (!$t.grid) {return;}
			$($t.rows).each(function(j){
				var res = {};
				if ($(this).hasClass("edited")) {
					$('td',this).each( function(i) {
						nm = $t.p.colModel[i].name;
						if ( nm !== 'cb' && nm !== 'subgrid') {
							if (mthd=='dirty') {
								if ($(this).hasClass('dirty-cell')) {
									try {
										res[nm] = $.unformat.call($t,this,{rowId:$t.rows[j].id, colModel:$t.p.colModel[i]},i);
									} catch (e){
										res[nm] = $.jgrid.htmlDecode($(this).html());
									}
								}
							} else {
								try {
									res[nm] = $.unformat.call($t,this,{rowId:$t.rows[j].id,colModel:$t.p.colModel[i]},i);
								} catch (e) {
									res[nm] = $.jgrid.htmlDecode($(this).html());
								}
							}
						}
					});
					res.id = this.id;
					ret.push(res);
				}
			});
		});
		return ret;
	},
        //调整工具栏布局
        navGridposition :function(opt){
            return this.each(function() {
                var  $t= this;//,opt=this.p;
                if((opt.navbar == opt.pager) && opt.toppager && opt.navtype == "top"){
                    var topPagerDiv = $(opt.toppager)[0];
                    $(opt.target+"_toppager_"+opt.pagerpos).remove();
                    $(opt.target+"_toppager_"+opt.recordpos).remove();
                    if(opt.pagerpos!="left" && opt.recordpos!="left"){
                      $(opt.pager+"_left").remove();
                    };
                    $(opt.pager+"_"+opt.pagerpos).attr({ align: "left" });
                }else if(opt.navtype == "bottom"){
                    $(opt.pager+"_"+opt.pagerpos +" .ui-pg-table").css({"float":"right"});
                    if(opt.pagerwidth !==""){
                        $(opt.pager+"_"+opt.pagerpos).width(opt.pagerwidth);
                    }
                    if(opt.recordwidth !==""){
                        $(opt.pager+"_"+opt.recordpos).width(opt.recordwidth);
                          //$(opts.pager+"_"+opts.recordpos +" .ui-paging-info").css({"text-align":"center"});
                    }
                }else if(opt.navtype == "none"){
                    //添加样式
                   //$("#gview_"+opt.id).addClass("ui-jqgrid-view-nonav");
                   //$("#gbox_"+opt.id).addClass("ui-jqgrid-nonav");
                    $(opt.pager).addClass("ui-jqgrid-pager-nonav");
                    $(opt.pager+"_"+opt.pagerpos).attr({ align: "right" });
                    $(opt.pager+"_"+opt.recordpos +" .ui-paging-info").css({ "text-align":"center" });
                    if(opt.recordwidth !==""){
                        $(opt.pager+"_"+opt.recordpos).width(opt.recordwidth);
                          //$(opts.pager+"_"+opts.recordpos +" .ui-paging-info").css({"text-align":"center"});
                    }
                }
                //分页栏为simple（上一页、下一页）时相关处理
                if(opt.pagerType == "simple"){
                    $("#first_"+$(opt.pager).attr("id")).remove();
                    $("#last_"+$(opt.pager).attr("id")).remove();
                    $("#prev_"+$(opt.pager).attr("id")).append($.jgrid.nav.prev);
                    $("#next_"+$(opt.pager).attr("id")).append($.jgrid.nav.next);
                    $("#prev_"+$(opt.pager).attr("id")).html($("<div class='ui-pg-div'></div>").append($("#prev_"+$(opt.pager).attr("id")).html()).button());
                    $("#next_"+$(opt.pager).attr("id")).html($("<div class='ui-pg-div'></div>").append($("#next_"+$(opt.pager).attr("id")).html()).button());
                    $(".ui-pager-control > .ui-pg-table > tbody > tr > td").attr("style","width: auto;");
                }
            })
        } ,
        //增加一行,不需用户维护新增id
        addLocalRow: function(opt){
           var istrue;
           this.each(function() {
                var newid = this.p.addParams.rowID + this.p.addParams.serial;
                var option = $.extend({data:{},operate:"first"},opt),
                    air = $.isFunction(this.p.afterAddRow) ? true : false,
                    id = option.id?option.id:newid,$t=this;
                //增加之前保存编辑状态的单元格
                if (this.p.savedRow.length>0) {
                    $(this).jqGrid("saveCell",this.p.savedRow[0].id,this.p.savedRow[0].ic);
                }
                istrue = $(this).jqGrid('addRowData',id,option.data ,option.operate);
                if(option.data.length>0){
    				for(var i=0;i<option.data.length;i++){
    					$("#"+option.data[i][id],this).addClass("edited added");    /*新增行添加编辑样式*/
    				}
    			}else{
    				$("#"+id,this).addClass("edited added");    /*新增行添加编辑样式*/
    			}
                for(var i in option.data){ /*新增行单元格添加新增样式*/
                    $( "tr[id="+id+"]"+" td[aria-describedby=\""+$t.p.id+'_'+i+"\"]",$t).addClass("dirty-cell");
                }
                // afterAddRow事件在addRowData 完成后触发
                if(air) { this.p.afterAddRow.call(this,id,option.data); }
                if(!option.id)this.p.addParams.serial++ ;
           })
            return istrue;
        },
         //设置一行,更新行、单元格样式
        setLocalRow: function(opt){
           var istrue;
           this.each(function() {
                var option = $.extend({data:{}},opt),
                    $t=this;
                //设置之前保存编辑状态的单元格
                if (this.p.savedRow.length>0) {
                    $(this).jqGrid("saveCell",this.p.savedRow[0].id,this.p.savedRow[0].ic);
                }
                if(option.id){
                    istrue = $($t).jqGrid('setRowData',option.id,option.data);
                    $("#"+option.id,$t).addClass("edited");    /*新增行添加编辑样式*/
                    for(var i in option.data){ /*新增行单元格添加新增样式*/
                        $( "tr[id="+option.id+"]"+" td[aria-describedby=\""+$t.p.id+'_'+i+"\"]",$t).addClass("dirty-cell");
                    }
                }else{
                   istrue = false;
                }
           })
            return istrue;
        },
        //删除一行,不需用户维护id
        delLocalRow: function(ids){
          return this.each(function(){
              var curdelrowid=[];
              //删除之前保存编辑状态的单元格
              if (this.p.savedRow.length>0) {
                  $(this).jqGrid("saveCell",this.p.savedRow[0].id,this.p.savedRow[0].ic);
              }
              //如果多选不可用ids为字符串
              if(!this.p.multiselect){
                  curdelrowid.push(ids);
                   //拷贝记录删除id
                   this.p._delrowid = curdelrowid.concat(this.p._delrowid);
                   $(this).jqGrid('delRowData',ids);

              }else{
                   curdelrowid = ids.concat();
                  //拷贝记录删除id
                  this.p._delrowid = curdelrowid.concat(this.p._delrowid);
                  for(var i=0;i<curdelrowid.length;i++){
                     $(this).jqGrid('delRowData',curdelrowid[i]);
                  }

              }
           })
        },
        //修改增加、删除、编辑数据默认方式
        navGrid : function (elem, o, pEdit,pAdd,pDel,pSearch, pView) {
            o = $.extend({
                edit: true,
                editicon: "ui-icon-pencil",
                add: true,
                addicon:"ui-icon-plus",
                del: true,
                delicon:"ui-icon-trash",
                search: false,
                searchicon:"ui-icon-search",
                refresh: true,
                refreshicon:"ui-icon-refresh",
                refreshstate: 'firstpage',
                view: true,
                viewicon : "ui-icon-document",
                position : "left",
                closeOnEscape : true,
                beforeRefresh : null,
                afterRefresh : null,
                cloneToTop : true,
                alertwidth : 200,
                alertheight : 'auto',
                alerttop: null,
                alertleft: null,
                alertzIndex : null
            }, $.jgrid.nav, o ||{});
            return this.each(function() {
                if(this.nav) {return;}
                var alertIDs = {themodal:'alertmod',modalhead:'alerthd',modalcontent:'alertcnt'},
                $t = this, twd, tdw;
                if(!$t.grid || typeof elem != 'string') {return;}
                if ($("#"+alertIDs.themodal).html() === null) {
                    if(!o.alerttop && !o.alertleft) {
                        if (typeof window.innerWidth != 'undefined') {
                            o.alertleft = window.innerWidth;
                            o.alerttop = window.innerHeight;
                        } else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth !== 0) {
                            o.alertleft = document.documentElement.clientWidth;
                            o.alerttop = document.documentElement.clientHeight;
                        } else {
                            o.alertleft=1024;
                            o.alerttop=768;
                        }
                        o.alertleft = o.alertleft/2 - parseInt(o.alertwidth,10)/2;
                        o.alerttop = o.alerttop/2-25;
                    }
                    $.jgrid.createModal(alertIDs,"<div>"+o.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqModal:true,drag:true,resize:true,caption:o.alertcap,top:o.alerttop,left:o.alertleft,width:o.alertwidth,height: o.alertheight,closeOnEscape:o.closeOnEscape, zIndex: o.alertzIndex},"","",true);
                }
                var clone = 1;
                if(o.cloneToTop && $t.p.toppager) {clone = 2;}
                for(var i = 0; i<clone; i++) {
                    var tbd,
                    navtbl = $("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),
                   // sep = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>",
                    sep="",
                    pgid, elemids;
                    if(i===0) {
                        pgid = elem;
                        elemids = $t.p.id;
                        if(pgid == $t.p.toppager) {
                            elemids += "_top";
                            clone = 1;
                        }
                    } else {
                        pgid = $t.p.toppager;
                        elemids = $t.p.id+"_top";
                    }
                    if($t.p.direction == "rtl") {$(navtbl).attr("dir","rtl").css("float","right");}
                    if (o.add) {
                        pAdd = pAdd || {};
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.addicon+"'></span>"+o.addtext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.addtitle || "",id : pAdd.id || "add_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                if ($.isFunction( o.addfunc )) {
                                    o.addfunc.call($t);
                                } else {
                                    $($t).jqGrid("editGridRow","new",pAdd); // 修改为默认增加空行
                                   // $($t).jqGrid("addRow");
                                }
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        tbd = null;
                    }
                    if (o.edit) {
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        pEdit = pEdit || {};
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.editicon+"'></span>"+o.edittext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.edittitle || "",id: pEdit.id || "edit_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                var sr = $t.p.selrow;
                                if (sr) {
                                    if($.isFunction( o.editfunc ) ) {
                                        o.editfunc.call($t, sr);
                                    } else {
                                        $($t).jqGrid("editGridRow",sr,pEdit);
                                    }
                                } else {
                                    jAlert(o.alerttext, o.alertcap);
                                   // $.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});
                                   // $("#jqg_alrt").focus();
                                }
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        tbd = null;
                    }
                    if (o.view) {
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        pView = pView || {};
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.viewicon+"'></span>"+o.viewtext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.viewtitle || "",id: pView.id || "view_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                var sr = $t.p.selrow;
                                if (sr) {
                                    if($.isFunction( o.viewfunc ) ) {
                                        o.viewfunc.call($t, sr);
                                    } else {
                                        $($t).jqGrid("viewGridRow",sr,pView);
                                    }
                                } else {
                                    jAlert(o.alerttext, o.alertcap);
                                    //$.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});
                                    //$("#jqg_alrt").focus();
                                }
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        tbd = null;
                    }
                    if (o.del) {
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        pDel = pDel || {};
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.delicon+"'></span>"+o.deltext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.deltitle || "",id: pDel.id || "del_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                var dr;
                                if($t.p.multiselect) {
                                    dr = $t.p.selarrrow;
                                    if(dr.length===0) {dr = null;}
                                } else {
                                    dr = $t.p.selrow;
                                }
                                if(dr){
                                    if($.isFunction( o.delfunc )){
                                        o.delfunc.call($t, dr);
                                    }else{
                                        $($t).jqGrid("delGridRow",dr,pDel);//修改为delRow删除行，记录删除id
                                        //$($t).jqGrid("delLocalRow" ,dr);
                                    }
                                } else  {
                                     jAlert(o.alerttext, o.alertcap);
                                   // $.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});$("#jqg_alrt").focus();
                                }
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        tbd = null;
                    }
                    if(o.add || o.edit || o.del || o.view) {$("tr",navtbl).append(sep);}
                    if (o.search) {
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        pSearch = pSearch || {};
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.searchicon+"'></span>"+o.searchtext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.searchtitle  || "",id:pSearch.id || "search_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                if($.isFunction( o.searchfunc )) {
                                    o.searchfunc.call($t, pSearch);
                                } else {
                                    $($t).jqGrid("searchGrid",pSearch);
                                }
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        if (pSearch.showOnLoad && pSearch.showOnLoad === true) {
                            $(tbd,navtbl).click();
                        }
                        tbd = null;
                    }
                    if (o.refresh) {
                        tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
                        $(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.refreshicon+"'></span>"+o.refreshtext+"</div>");
                        $("tr",navtbl).append(tbd);
                        $(tbd,navtbl)
                        .attr({"title":o.refreshtitle  || "",id: "refresh_"+elemids})
                        .click(function(){
                            if (!$(this).hasClass('ui-state-disabled')) {
                                if($.isFunction(o.beforeRefresh)) {o.beforeRefresh();}
                                $t.p.search = false;
                                try {
                                    var gID = $t.p.id;
                                    $t.p.postData.filters ="";
                                    $("#fbox_"+$.jgrid.jqID(gID)).jqFilter('resetFilter');
                                    if($.isFunction($t.clearToolbar)) {$t.clearToolbar(false);}
                                } catch (e) {}
                                switch (o.refreshstate) {
                                    case 'firstpage':
                                        $($t).trigger("reloadGrid", [{page:1}]);
                                        break;
                                    case 'current':
                                        $($t).trigger("reloadGrid", [{current:true}]);
                                        break;
                                }
                                if($.isFunction(o.afterRefresh)) {o.afterRefresh();}
                                 //删除行信息刷新
                                $t.p._delrowid = [];
                            }
                            return false;
                        }).hover(
                            function () {
                                if (!$(this).hasClass('ui-state-disabled')) {
                                    $(this).addClass("ui-state-hover");
                                }
                            },
                            function () {$(this).removeClass("ui-state-hover");}
                        );
                        tbd = null;
                    }
                    tdw = $(".ui-jqgrid").css("font-size") || "11px";
                    $('body').append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+tdw+";visibility:hidden;' ></div>");
                    twd = $(navtbl).clone().appendTo("#testpg2").width();
                    $("#testpg2").remove();
                    $(pgid+"_"+o.position,pgid).append(navtbl);
                    //$(".ui-pg-div").button();
                    /*
                    if($t.p._nvtd) {
                        if(twd > $t.p._nvtd[0] ) {
                            $(pgid+"_"+o.position,pgid).width(twd);
                            $t.p._nvtd[0] = twd;
                        }
                        $t.p._nvtd[1] = twd;
                    } */
                    tdw =null;twd=null;navtbl =null;
                    this.nav = true;
                }
                //导航栏按钮
                $(".ui-pg-div").button();
            });
	    },
        //兼容百分比设置宽度 ，自适应大小
        setGridWidth : function(nwidth, shrink) {
            return this.each(function(){
                if (!this.grid ) {return;}
                var $t = this, cw,extwidth,
                initwidth = 0, brd=$.browser.webkit||$.browser.safari? 0: $t.p.cellLayout, lvc, vc=0, hs=false, scw=$t.p.scrollOffset, aw, gw=0,
                cl = 0,cr;
                if(typeof shrink != 'boolean') {
                    shrink=$t.p.shrinkToFit;
                }
                
                if(nwidth != "auto" && nwidth != "100%" && shrink===true){
                    $(window).unbind("resize.gridwidth");
                }

                if( typeof nwidth == 'number' ){
                    nwidth = parseInt(nwidth,10);
                    $t.grid.width = $t.p.width = nwidth;extwidth = nwidth+"px";
                } else {
                    extwidth = nwidth;
                }

                $("#gbox_"+$.jgrid.jqID($t.p.id)).css("width",extwidth);
                if(isNaN(nwidth)){extwidth = "100%" ;nwidth =  $("#gbox_"+$.jgrid.jqID($t.p.id)).width();}
                $("#gview_"+$.jgrid.jqID($t.p.id)).css("width",extwidth);
                $($t.grid.bDiv).css("width",extwidth);
                $($t.grid.hDiv).css("width",extwidth);
                if($t.p.pager ) {$($t.p.pager).css("width",extwidth);}
                if($t.p.toppager ) {$($t.p.toppager).css("width",extwidth);}
                if($t.p.toolbar[0] === true){
                    $($t.grid.uDiv).css("width",extwidth);
                    if($t.p.toolbar[1]=="both") {$($t.grid.ubDiv).css("width",extwidth);}
                }
                if($t.p.footerrow) { $($t.grid.sDiv).css("width",extwidth); }
                if(shrink ===false && $t.p.forceFit === true) {$t.p.forceFit=false;}
                if(shrink===true) {
                    $.each($t.p.colModel, function() {
                        if(this.hidden===false){
                            cw = this.widthOrg? this.widthOrg: parseInt(this.width,10);
                            initwidth += cw+brd;
                            if(this.fixed) {
                                gw += cw+brd;
                            } else {
                                vc++;
                            }
                            cl++;
                        }
                    });
                    if(vc  === 0) { return; }
                    $t.p.tblwidth = initwidth;
                    aw = nwidth-brd*vc-gw;
                    if(!isNaN($t.p.height)) {
                        if($($t.grid.bDiv)[0].clientHeight < $($t.grid.bDiv)[0].scrollHeight || $t.rows.length === 1){
                            hs = true;
                            aw -= scw;
                        }
                    }
                    initwidth =0;
                    var cle = $t.grid.cols.length >0;
                    $.each($t.p.colModel, function(i) {
                        if(this.hidden === false && !this.fixed){
                            cw = this.widthOrg? this.widthOrg: parseInt(this.width,10);
                            /*
                            * brd 单元格内边距+邊框 vc 可拖动列数  aw可以自由拖动大小列总宽度
                            */
                            cw = Math.round(aw*cw/($t.p.tblwidth-brd*vc-gw));
                            //cw = Math.floor(aw*cw/($t.p.tblwidth-brd*vc-gw));
                            if (cw < 0) { return; }
                            this.width =cw;
                            initwidth += cw;
                            $t.grid.headers[i].width=cw;
                            $t.grid.headers[i].el.style.width=cw+"px";
                            //修复多表头自适应组合使用对不齐问题
                            if($t.p.totalGroupHeader.length > 0){$("#"+$t.grid.headers[i].el.id).css({width:cw+"px"});}
                            if($t.p.footerrow) { $t.grid.footers[i].style.width = cw+"px"; }
                            if(cle) { $t.grid.cols[i].style.width = cw+"px"; }
                            lvc = i;
                        }
                    });

                    if (!lvc) { return; }

                    cr =0;
                    if (hs) {
                        if(nwidth-gw-(initwidth+brd*vc) !== scw){
                            cr = nwidth-gw-(initwidth+brd*vc)-scw;
                        }
                    } else if( Math.abs(nwidth-gw-(initwidth+brd*vc)) !== 1) {
                        cr = nwidth-gw-(initwidth+brd*vc);
                    }
                    $t.p.colModel[lvc].width += cr;
                    $t.p.tblwidth = initwidth+cr+brd*vc+gw;
                    if($t.p.tblwidth > nwidth) {
                        var delta = $t.p.tblwidth - parseInt(nwidth,10);
                        $t.p.tblwidth = nwidth;
                        cw = $t.p.colModel[lvc].width = $t.p.colModel[lvc].width-delta;
                    } else {
                        cw= $t.p.colModel[lvc].width;
                    }
                    $t.grid.headers[lvc].width = cw;
                    $t.grid.headers[lvc].el.style.width=cw+"px";
                    if(cle) { $t.grid.cols[lvc].style.width = cw+"px"; }
                    if($t.p.footerrow) {
                        $t.grid.footers[lvc].style.width = cw+"px";
                    }
                }
                if($t.p.tblwidth) {
                    $('table:first',$t.grid.bDiv).css("width",$t.p.tblwidth+"px");
                    $('table:first',$t.grid.hDiv).css("width",$t.p.tblwidth+"px");
                    $t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
                    if($t.p.footerrow) {
                        $('table:first',$t.grid.sDiv).css("width",$t.p.tblwidth+"px");
                    }
                }

            });
	    },
        //兼容百分比设置高度 ，自适应大小
	    setGridHeight : function (nh) {
            return this.each(function (){
                var $t = this;
                if(!$t.grid) {return;}
                var bDiv = $($t.grid.bDiv);
                bDiv.css({height: nh+(isNaN(nh)?"":"px")});
                /*
                var c = nh.charCodeAt( nh.length-1 );
                bDiv.css({height:(c < 0x30 || c > 0x39) ? nh : nh + "px"});  */
                if($t.p.frozenColumns === true){
                    //follow the original set height to use 16, better scrollbar width detection
                    $('#'+$.jgrid.jqID($t.p.id)+"_frozen").parent().height(bDiv.height() - 16);
                }
                $t.p.height = nh;
                if ($t.p.scroll) { $t.grid.populateVisible(); }
            });
	    },
        //修改编辑单元格方式
        editCell : function (iRow,iCol, ed){
		  return this.each(function (){
			var $t = this, nm, tmp,cc, cm;
			if (!$t.grid || $t.p.cellEdit !== true) {return;}
			iCol = parseInt(iCol,10);
			// select the row that can be used for other methods
			$t.p.selrow = $t.rows[iRow].id;
			if (!$t.p.knv) {$($t).jqGrid("GridNav");}
			// check to see if we have already edited cell
			if ($t.p.savedRow.length>0) {
				// prevent second click on that field and enable selects
				if (ed===true ) {
					if(iRow == $t.p.iRow && iCol == $t.p.iCol){
						return;
					}
				}
				// save the cell
				$($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
			} else {
				window.setTimeout(function () { $("#"+$.jgrid.jqID($t.p.knv)).attr("tabindex","-1").focus();},0);
			}
			cm = $t.p.colModel[iCol];
			nm = cm.name;
			if (nm=='subgrid' || nm=='cb' || nm=='rn') {return;}
			cc = $("td:eq("+iCol+")",$t.rows[iRow]);
			if (cm.editable===true && ed===true && !cc.hasClass("not-editable-cell")) {
				if(parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
					//$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
                    $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell");
					$($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
				}
				//$(cc).addClass("edit-cell ui-state-highlight");
                $(cc).addClass("edit-cell");
				// $($t.rows[iRow]).addClass(" ui-state-hover");
				try {
					tmp =  $.unformat.call($t,cc,{rowId: $t.rows[iRow].id, colModel:cm},iCol);
				} catch (_) {
					tmp = ( cm.edittype && cm.edittype == 'textarea' ) ? $(cc).text() : $(cc).html();
				}
				if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
				if (!cm.edittype) {cm.edittype = "text";}
				$t.p.savedRow.push({id:iRow,ic:iCol,name:nm,v:tmp});
				if(tmp === "&nbsp;" || tmp === "&#160;" || (tmp.length===1 && tmp.charCodeAt(0)===160) ) {tmp='';}
				if($.isFunction($t.p.formatCell)) {
					var tmp2 = $t.p.formatCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					if(tmp2 !== undefined ) {tmp = tmp2;}
				}
				var opt = $.extend({}, cm.editoptions || {} ,{id:iRow+"_"+nm,name:nm});
				var elc = $.jgrid.createEl(cm.edittype,opt,tmp,true,$.extend({},$.jgrid.ajaxOptions,$t.p.ajaxSelectOptions || {}));
				$($t).triggerHandler("jqGridBeforeEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
				if ($.isFunction($t.p.beforeEditCell)) {
					$t.p.beforeEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
				}
				$(cc).html("").append(elc).attr("tabindex","0");
				window.setTimeout(function () { $(elc).focus();},0);
				$("input, select, textarea",cc).bind("keydown",function(e) {
					if (e.keyCode === 27) {
						if($("input.hasDatepicker",cc).length >0) {
							if( $(".ui-datepicker").is(":hidden") )  { $($t).jqGrid("restoreCell",iRow,iCol); }
							else { $("input.hasDatepicker",cc).datepicker('hide'); }
						} else {
							$($t).jqGrid("restoreCell",iRow,iCol);
						}
					} //ESC
					if (e.keyCode === 13) {
						$($t).jqGrid("saveCell",iRow,iCol);
						// Prevent default action
						return false;
					} //Enter
					if (e.keyCode === 9)  {
						if(!$t.grid.hDiv.loading ) {
							if (e.shiftKey) {$($t).jqGrid("prevCell",iRow,iCol);} //Shift TAb
							else {$($t).jqGrid("nextCell",iRow,iCol);} //Tab
						} else {
							return false;
						}
					}
					e.stopPropagation();
				});
				$($t).triggerHandler("jqGridAfterEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
				if ($.isFunction($t.p.afterEditCell)) {
					$t.p.afterEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
				}
			} else {
				if (parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
					$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
                   // $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell");
					$($t.rows[$t.p.iRow]).removeClass("ui-state-hover");
				}
				//cc.addClass("edit-cell ui-state-highlight");
                cc.addClass("edit-cell");
				$($t.rows[iRow]).addClass(" ui-state-hover");
				tmp = cc.html().replace(/\&#160\;/ig,'');
				$($t).triggerHandler("jqGridSelectCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
				if ($.isFunction($t.p.onSelectCell)) {
					$t.p.onSelectCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
				}
			}
			$t.p.iCol = iCol; $t.p.iRow = iRow;
		  });
	    },
        //添加datepicke的保持单元格时处理
     	saveCell : function (iRow, iCol){
		  return this.each(function(){
			var $t= this, fr;
			if (!$t.grid || $t.p.cellEdit !== true) {return;}
			if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;}
			if(fr !== null) {
				var cc = $("td:eq("+iCol+")",$t.rows[iRow]),v,v2,
				cm = $t.p.colModel[iCol], nm = cm.name, nmjq = $.jgrid.jqID(nm) ;
				switch (cm.edittype) {
					case "select":
						if(!cm.editoptions.multiple) {
							v = $("#"+iRow+"_"+nmjq+">option:selected",$t.rows[iRow]).val();
							v2 = $("#"+iRow+"_"+nmjq+">option:selected",$t.rows[iRow]).text();
						} else {
							var sel = $("#"+iRow+"_"+nmjq,$t.rows[iRow]), selectedText = [];
							v = $(sel).val();
							if(v) { v.join(",");} else { v=""; }
							$("option:selected",sel).each(
								function(i,selected){
									selectedText[i] = $(selected).text();
								}
							);
							v2 = selectedText.join(",");
						}
						if(cm.formatter) { v2 = v; }
						break;
					case "checkbox":
						if($("#"+iRow+"_"+nmjq+" input").is(":checked")) {
                                var str = "",checkElement = " input[type=checkbox]:checked";
                                $("#"+iRow+"_"+nmjq+ checkElement).each(function(i){
                                     str+= (((i==0)?"":",") + $(this).val());
                                }) ;
                                v = str;
							}else {
								v = "";
							}
						v2=v;
						break;
                    case "radio":
						if($("#"+iRow+"_"+nmjq+" input").is(":checked")) {
                                var str = "",checkElement = " input[type=radio]:checked";
                                $("#"+iRow+"_"+nmjq+ checkElement).each(function(i){
                                     str+= (((i==0)?"":",") + $(this).val());
                                }) ;
                                v = str;
							}else {
								v = "";
							}
						v2=v;
						break;
					case "password":
					case "text":
					case "number":
					case "textarea":
                    case "comboboxlist":
                    case "comboboxtree":
                    case "datepicker": //datepicker与text等类型一样，返回具体值即可
					case "button" :
						v = $("#"+iRow+"_"+nmjq,$t.rows[iRow]).val();
						v2=v;
						break;
					case 'custom' :
						try {
							if(cm.editoptions && $.isFunction(cm.editoptions.custom_value)) {
								v = cm.editoptions.custom_value.call($t, $(".customelement",cc),'get');
								if (v===undefined) { throw "e2";} else { v2=v; }
							} else { throw "e1"; }
						} catch (e) {
							if (e=="e1") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose); }
							if (e=="e2") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose); }
							else {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose); }
						}
						break;
				}
				// The common approach is if nothing changed do not do anything
				//if (v2 != $t.p.savedRow[fr].v ){
				  if (v2 != $t.p.savedRow[fr].v || v2==""){//增加当内容为空时总进行校验
					if ($.isFunction($t.p.beforeSaveCell)) {
						var vv = $t.p.beforeSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
						if (vv) {v = vv; v2=vv;}
					}
					var cv = $.jgrid.checkValues(v,iCol,$t);
					if(cv[0] === true) {
						var addpost = {};
						if ($.isFunction($t.p.beforeSubmitCell)) {
							addpost = $t.p.beforeSubmitCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
							if (!addpost) {addpost={};}
						}
						if( $("input.hasDatepicker",cc).length >0) { $("input.hasDatepicker",cc).datepicker('hide'); }
						if ($t.p.cellsubmit == 'remote') {
							if ($t.p.cellurl) {
								var postdata = {};
								if($t.p.autoencode) { v = $.jgrid.htmlEncode(v); }
								postdata[nm] = v;
								var idname,oper, opers;
								opers = $t.p.prmNames;
								idname = opers.id;
								oper = opers.oper;
								postdata[idname] = $t.rows[iRow].id;
								postdata[oper] = opers.editoper;
								postdata = $.extend(addpost,postdata);
								$("#lui_"+$t.p.id).show();
								$t.grid.hDiv.loading = true;
								$.ajax( $.extend( {
									url: $t.p.cellurl,
									data :$.isFunction($t.p.serializeCellData) ? $t.p.serializeCellData.call($t, postdata) : postdata,
									type: "POST",
									complete: function (result, stat) {
										$("#lui_"+$t.p.id).hide();
										$t.grid.hDiv.loading = false;
										if (stat == 'success') {
											if ($.isFunction($t.p.afterSubmitCell)) {
												var ret = $t.p.afterSubmitCell.call($t, result,postdata.id,nm,v,iRow,iCol);
												if(ret[0] === true) {
													$(cc).empty();
													$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
													$(cc).addClass("dirty-cell");
													$($t.rows[iRow]).addClass("edited");
													if ($.isFunction($t.p.afterSaveCell)) {
														$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
													}
													$t.p.savedRow.splice(0,1);
												} else {
													$.jgrid.info_dialog($.jgrid.errors.errcap,ret[1],$.jgrid.edit.bClose);
													$($t).jqGrid("restoreCell",iRow,iCol);
												}
											} else {
												$(cc).empty();
												$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
												$(cc).addClass("dirty-cell");
												$($t.rows[iRow]).addClass("edited");
												if ($.isFunction($t.p.afterSaveCell)) {
													$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
												}
												$t.p.savedRow.splice(0,1);
											}
										}
									},
									error:function(res,stat) {
										$("#lui_"+$t.p.id).hide();
										$t.grid.hDiv.loading = false;
										if ($.isFunction($t.p.errorCell)) {
											$t.p.errorCell.call($t, res,stat);
											$($t).jqGrid("restoreCell",iRow,iCol);
										} else {
											$.jgrid.info_dialog($.jgrid.errors.errcap,res.status+" : "+res.statusText+"<br/>"+stat,$.jgrid.edit.bClose);
											$($t).jqGrid("restoreCell",iRow,iCol);
										}
									}
								}, $.jgrid.ajaxOptions, $t.p.ajaxCellOptions || {}));
							} else {
								try {
									$.jgrid.info_dialog($.jgrid.errors.errcap,$.jgrid.errors.nourl,$.jgrid.edit.bClose);
									$($t).jqGrid("restoreCell",iRow,iCol);
								} catch (e) {}
							}
						}
						if ($t.p.cellsubmit == 'clientArray') {
							$(cc).empty();
							if(cm.edittype == "select"){  //如果是select把value值保存在td属性里
                                $($t).jqGrid("setCell",$t.rows[iRow].id,iCol, v2, false, {value:v}, true);
                            }else {
							    $($t).jqGrid("setCell",$t.rows[iRow].id,iCol, v2, false, false, true);
							}
							$(cc).addClass("dirty-cell");
							$($t.rows[iRow]).addClass("edited");
							if ($.isFunction($t.p.afterSaveCell)) {
								$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
							}
							$t.p.savedRow.splice(0,1);
						}
					} else {
						try {
							//提示信息统一通过alerts组件弹出
							window.setTimeout(function(){jAlert(v+" "+cv[1],$.jgrid.errors.errcap);},100);
							$($t).jqGrid("restoreCell",iRow,iCol);
						} catch (e) {}
					}
				} else {
					$($t).jqGrid("restoreCell",iRow,iCol);
				}
			}
			if ($.browser.opera) {
				$("#"+$t.p.knv).attr("tabindex","-1").focus();
			} else {
				window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1").focus();},0);
			}
		  });
	    },
        // subgrid中详细编辑数据处理
        editDetailRow : function(rowid, p){
            p = $.extend({
                width: 300,
                height: 'auto',
                dataheight: 'auto',
                url: null,
                mtype : "get",
                clearAfterAdd :true,
                closeAfterEdit : false,
                reloadAfterSubmit : true,
                onInitializeForm: null,
                beforeInitData: null,
                beforeSubmit: null,
                afterSubmit: null,
                onclickSubmit: null,
                afterComplete: null,
                onclickPgButtons : null,
                afterclickPgButtons: null,
                editData : {},
                recreateForm : false,
                jqModal : true,
                closeOnEscape : false,
                addedrow : "first",
                topinfo : '',
                bottominfo: '',
                saveicon : [],
                closeicon : [],
                savekey: [false,13],
                navkeys: [false,38,40],
                checkOnSubmit : false,
                checkOnUpdate : false,
                submitType: "remote",
                _savedData : {},
                processing : false,
                onClose : null,
                ajaxEditOptions : {},
                serializeEditData : null,
                viewPagerButtons : true
            }, $.jgrid.edit, p || {});
            var rp_ge = p;
            return this.each(function(){
                var $t = this;
                if (!$t.grid || !rowid) { return; }
                //var gID = $t.p.id,
                var gID = rp_ge.subgrid_id,
                frmgr = "FrmGrid_"+gID,frmtb = "TblGrid_"+gID,
                IDs = {themodal:'editmod'+gID,modalhead:'edithd'+rp_ge.subgrid_id,modalcontent:'editcnt'+rp_ge.subgrid_id, scrollelm : frmgr},
                onBeforeInit = $.isFunction(rp_ge.beforeInitData) ? rp_ge.beforeInitData : false,
                onInitializeForm = $.isFunction(rp_ge.onInitializeForm) ? rp_ge.onInitializeForm : false,
                copydata = null,
                showFrm = true,
                maxCols = 1, maxRows=0,postdata, extpost, newData, diff;
                if (rowid === "new") {
                    rowid = "_empty";
                    p.caption=rp_ge.addCaption;
                } else {
                    p.caption=rp_ge.editCaption;
                }
                //获取form中的数据存放到postdata，如为下拉框存放到extpost
                function getFormData(){
                    $(".FormElement", "#"+frmtb).each(function(i) {
                        var celm = $(".customelement", this);
                        if (celm.length) {
                            var  elem = celm[0], nm = $(elem).attr('name');
                            $.each($t.p.colModel, function(i,n){
                                if(this.name === nm && this.editoptions && $.isFunction(this.editoptions.custom_value)) {
                                    try {
                                        postdata[nm] = this.editoptions.custom_value($("#"+$.jgrid.jqID(nm),"#"+frmtb),'get');
                                        if (postdata[nm] === undefined) { throw "e1"; }
                                    } catch (e) {
                                        if (e==="e1") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose);}
                                        else { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose); }
                                    }
                                    return true;
                                }
                            });
                        } else {
                        switch ($(this).get(0).type) {
                            case "checkbox":
                                if($(this).attr("checked")) {
                                    postdata[this.name]= $(this).val();
                                }else {
                                    var ofv = $(this).attr("offval");
                                    postdata[this.name]= ofv;
                                }
                            break;
                            case "select-one":
                                postdata[this.name]= $("option:selected",this).val();
                                extpost[this.name]= $("option:selected",this).text();
                            break;
                            case "select-multiple":
                                postdata[this.name]= $(this).val();
                                if(postdata[this.name]) { postdata[this.name] = postdata[this.name].join(","); }
                                else { postdata[this.name] =""; }
                                var selectedText = [];
                                $("option:selected",this).each(
                                    function(i,selected){
                                        selectedText[i] = $(selected).text();
                                    }
                                );
                                extpost[this.name]= selectedText.join(",");
                            break;
                            case "password":
                            case "text":
                            case "textarea":
                            case "button":
                                postdata[this.name] = $(this).val();

                            break;
                        }
                        if($t.p.autoencode) { postdata[this.name] = $.jgrid.htmlEncode(postdata[this.name]); }
                        }
                    });
                    return true;
                }
                //创建form数据
                function createData(rowid,obj,tb,maxcols){
                    var nm, hc,trdata, cnt=0,tmp, dc,elc, retpos=[], ind=false,
                    tdtmpl = "<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>", tmpl="", i; //*2
                    for (i =1; i<=maxcols;i++) {
                        tmpl += tdtmpl;
                    }
                    if(rowid != '_empty') {
                        ind = $(obj).jqGrid("getInd",rowid);
                    }
                    $(obj.p.colModel).each( function(i) {
                        nm = this.name;
                        // hidden fields are included in the form
                        if(this.editrules && this.editrules.edithidden === true) {
                            hc = false;
                        } else {
                            hc = this.hidden === true ? true : false;
                        }
                        dc = hc ? "style='display:none'" : "";
                        if ( nm !== 'cb' && nm !== 'subgrid' && this.editable===true && nm !== 'rn') {
                            if(ind === false) {
                                tmp = "";
                            } else {
                                if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
                                    tmp = $("td:eq("+i+")",obj.rows[ind]).text();
                                } else {
                                    try {
                                        tmp =  $.unformat($("td:eq("+i+")",obj.rows[ind]),{rowId:rowid, colModel:this},i);
                                    } catch (_) {
                                        tmp = $("td:eq("+i+")",obj.rows[ind]).text();
                                    }
                                }
                            }
                            var opt = $.extend({}, this.editoptions || {} ,{id:nm,name:nm}),
                            frmopt = $.extend({}, {elmprefix:'',elmsuffix:'',rowabove:false,rowcontent:''}, this.formoptions || {}),
                            rp = parseInt(frmopt.rowpos,10) || cnt+1,
                            cp = parseInt((parseInt(frmopt.colpos,10) || 1)*2,10);
                            if(rowid == "_empty" && opt.defaultValue ) {
                                tmp = $.isFunction(opt.defaultValue) ? opt.defaultValue() : opt.defaultValue;
                            }
                            if(!this.edittype) { this.edittype = "text"; }
                            if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
                            elc = $.jgrid.createEl(this.edittype,opt,tmp,false,$.extend({},$.jgrid.ajaxOptions,obj.p.ajaxSelectOptions || {}));
                           // if(tmp === "" && this.edittype == "checkbox") {tmp = $(elc).attr("offval");}
                          //  if(tmp === "" && this.edittype == "select") {tmp = $("option:eq(0)",elc).text();}
                            if(rp_ge.checkOnSubmit || rp_ge.checkOnUpdate || rp_ge.submitType ==  "clientArray") { rp_ge._savedData[nm] = tmp; }
                            $(elc).addClass("FormElement");
                            if(this.edittype == 'text' || this.edittype == 'textarea'|| this.edittype == 'datepicker'
                                || this.edittype == 'comboboxlist' || this.edittype == 'comboboxtree') {
                               // $(elc).addClass("ui-widget-content");
                            }
                            trdata = $(tb).find("tr[rowpos="+rp+"]");
                            if(frmopt.rowabove) {
                                var newdata = $("<tr><td class='contentinfo' colspan='"+(maxcols*2)+"'>"+frmopt.rowcontent+"</td></tr>");
                                $(tb).append(newdata);
                                newdata[0].rp = rp;
                            }
                            if ( trdata.length===0 ) {
                                trdata = $("<tr "+dc+" rowpos='"+rp+"'></tr>").addClass("FormData").attr("id","tr_"+nm);
                                $(trdata).append(tmpl);
                                $(tb).append(trdata);
                                trdata[0].rp = rp;
                            }
                            //单元格后面添加“：”
                            $("td:eq("+(cp-2)+")",trdata[0]).html( (typeof frmopt.label === 'undefined' ? obj.p.colNames[i]: frmopt.label)+" :");
                            $("td:eq("+(cp-1)+")",trdata[0]).append(frmopt.elmprefix).append(elc).append(frmopt.elmsuffix);
                            if(this.edittype == 'datepicker'){
                                 $(elc).click(function(){WdatePicker(opt)});
                               // $(elc).datepicker(opt);
                            }
                            if(this.edittype == 'comboboxlist'){
                                $(elc).dropdownlist(opt);
                            }
                            if(this.edittype == 'comboboxtree'){
                                var setting = $.extend(opt,{targetElem:elc});
                                new biz.comboboxtree(setting);
                                //$(elc).click(function(){WdatePicker(opt)});
                            }
                            retpos[cnt] = i;
                            cnt++;
                        }
                    });
                    if( cnt > 0) {
                        var idrow = $("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+ (maxcols*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='"+obj.p.id+"_id' value='"+rowid+"'/></td></tr>");
                        idrow[0].rp = cnt+999;
                        $(tb).append(idrow);
                        if(rp_ge.checkOnSubmit || rp_ge.checkOnUpdate ) { rp_ge._savedData[obj.p.id+"_id"] = rowid; }
                    }
                    return retpos;
                }
                function fillData(rowid,obj,fmid){
                    var nm,cnt=0,tmp, fld,opt,vl,vlc;
                    if(rp_ge.checkOnSubmit || rp_ge.checkOnUpdate) {rp_ge._savedData = {};rp_ge._savedData[obj.p.id+"_id"]=rowid;}
                    var cm = obj.p.colModel;
                    if(rowid == '_empty') {
                        $(cm).each(function(i){
                            nm = this.name;
                            opt = $.extend({}, this.editoptions || {} );
                            fld = $("#"+$.jgrid.jqID(nm),"#"+fmid);
                            if(fld && fld[0] !== null) {
                                vl = "";
                                if(opt.defaultValue ) {
                                    vl = $.isFunction(opt.defaultValue) ? opt.defaultValue() : opt.defaultValue;
                                    if(fld[0].type=='checkbox') {
                                        vlc = vl.toLowerCase();
                                        if(vlc.search(/(false|0|no|off|undefined)/i)<0 && vlc!=="") {
                                            fld[0].checked = true;
                                            fld[0].defaultChecked = true;
                                            fld[0].value = vl;
                                        } else {
                                            fld.attr({checked:"",defaultChecked:""});
                                        }
                                    } else {fld.val(vl); }
                                } else {
                                    if( fld[0].type=='checkbox' ) {
                                        fld[0].checked = false;
                                        fld[0].defaultChecked = false;
                                        vl = $(fld).attr("offval");
                                    } else if (fld[0].type && fld[0].type.substr(0,6)=='select') {
                                        fld[0].selectedIndex = 0;
                                    } else {
                                        fld.val(vl);
                                    }
                                }
                                if(rp_ge.checkOnSubmit===true || rp_ge.checkOnUpdate) { rp_ge._savedData[nm] = vl; }
                            }
                        });
                        $("#id_g","#"+fmid).val(rowid);
                        return;
                    }
                    var tre = $(obj).jqGrid("getInd",rowid,true);
                    if(!tre) { return; }
                    $('td',tre).each( function(i) {
                        nm = cm[i].name;
                        // hidden fields are included in the form
                        if ( nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn' && cm[i].editable===true) {
                            if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
                                tmp = $(this).text();
                            } else {
                                try {
                                    tmp =  $.unformat($(this),{rowId:rowid, colModel:cm[i]},i);
                                } catch (_) {
                                    tmp = $(this).text();
                                }
                            }
                            if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
                            if(rp_ge.checkOnSubmit===true || rp_ge.checkOnUpdate) { rp_ge._savedData[nm] = tmp; }
                            nm = $.jgrid.jqID(nm);
                            switch (cm[i].edittype) {
                                case "password":
                                case "text":
                                case "button" :
                                case "image":
                                    $("#"+nm,"#"+fmid).val(tmp);
                                    break;
                                case "textarea":
                                    if(tmp == "&nbsp;" || tmp == "&#160;" || (tmp.length==1 && tmp.charCodeAt(0)==160) ) {tmp='';}
                                    $("#"+nm,"#"+fmid).val(tmp);
                                    break;
                                case "select":
                                    var opv = tmp.split(",");
                                    opv = $.map(opv,function(n){return $.trim(n);});
                                    $("#"+nm+" option","#"+fmid).each(function(j){
                                        if (!cm[i].editoptions.multiple && (opv[0] == $.trim($(this).text()) || opv[0] == $.trim($(this).val())) ){
                                            this.selected= true;
                                        } else if (cm[i].editoptions.multiple){
                                            if(  $.inArray($.trim($(this).text()), opv ) > -1 || $.inArray($.trim($(this).val()), opv ) > -1  ){
                                                this.selected = true;
                                            }else{
                                                this.selected = false;
                                            }
                                        } else {
                                            this.selected = false;
                                        }
                                    });
                                    break;
                                case "checkbox":
                                    tmp = tmp+"";
                                    if(cm[i].editoptions && cm[i].editoptions.value) {
                                        var cb = cm[i].editoptions.value.split(":");
                                        if(cb[0] == tmp) {
                                            $("#"+nm,"#"+fmid).attr("checked",true);
                                            $("#"+nm,"#"+fmid).attr("defaultChecked",true); //ie
                                        } else {
                                            $("#"+nm,"#"+fmid).attr("checked",false);
                                            $("#"+nm,"#"+fmid).attr("defaultChecked",""); //ie
                                        }
                                    } else {
                                        tmp = tmp.toLowerCase();
                                        if(tmp.search(/(false|0|no|off|undefined)/i)<0 && tmp!=="") {
                                            $("#"+nm,"#"+fmid).attr("checked",true);
                                            $("#"+nm,"#"+fmid).attr("defaultChecked",true); //ie
                                        } else {
                                            $("#"+nm,"#"+fmid).attr("checked",false);
                                            $("#"+nm,"#"+fmid).attr("defaultChecked",""); //ie
                                        }
                                    }
                                    break;
                                case "comboboxlist":
                                case "comboboxtree":
                                case "datepicker" :
                                     $("#"+nm,"#"+fmid).val(tmp);
                                    // $("#"+nm,"#"+fmid).datepicker(cm[i].editoptions);
                                break;

                                case 'custom' :
                                    try {
                                        if(cm[i].editoptions && $.isFunction(cm[i].editoptions.custom_value)) {
                                            cm[i].editoptions.custom_value($("#"+nm,"#"+fmid),'set',tmp);
                                        } else { throw "e1"; }
                                    } catch (e) {
                                        if (e=="e1") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);}
                                        else { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose); }
                                    }
                                    break;
                            }
                            cnt++;
                        }
                    });
                    if(cnt>0) { $("#id_g","#"+frmtb).val(rowid); }
                }
                //提交数据处理
                function postIt() {
                    var copydata, ret=[true,"",""], onCS = {}, opers = $t.p.prmNames, idname, oper, key, selr;
                    if($.isFunction(rp_ge.beforeCheckValues)) {
                        var retvals = rp_ge.beforeCheckValues(postdata,$("#"+frmgr),postdata[$t.p.id+"_id"] == "_empty" ? opers.addoper : opers.editoper);
                        if(retvals && typeof(retvals) === 'object') { postdata = retvals; }
                    }
                    for( key in postdata ){
                        if(postdata.hasOwnProperty(key)) {
                            ret = $.jgrid.checkValues(postdata[key],key,$t);
                            if(ret[0] === false) { break; }
                        }
                    }
                    if(ret[0]) {
                        if( $.isFunction( rp_ge.onclickSubmit)) { onCS = rp_ge.onclickSubmit(rp_ge,postdata) || {}; }
                        if( $.isFunction(rp_ge.beforeSubmit))  { ret = rp_ge.beforeSubmit(postdata,$("#"+frmgr)); }
                    }

                    if(ret[0] && !rp_ge.processing) {
                        rp_ge.processing = true;
                        $("#"+sb, "#"+frmtb+"_2").addClass('ui-state-active');
                        oper = opers.oper;
                        idname = opers.id;
                        // we add to pos data array the action - the name is oper
                        postdata[oper] = ($.trim(postdata[$t.p.id+"_id"]) == "_empty") ? opers.addoper : opers.editoper;
                        //如果是保持本地数据不给idname赋值
                        if(rp_ge.submitType !== "clientArray" ){
                            if(postdata[oper] != opers.addoper ) {
                                postdata[idname] = postdata[$t.p.id+"_id"];
                            } else {
                                // check to see if we have allredy this field in the form and if yes lieve it
                                if( postdata[idname] === undefined ) { postdata[idname] = postdata[$t.p.id+"_id"]; }
                            }
                        }
                        delete postdata[$t.p.id+"_id"];
                        postdata = $.extend(postdata,rp_ge.editData,onCS);
                        if($t.p.treeGrid === true)  {
                            if(postdata[oper] == opers.addoper) {
                            selr = $($t).jqGrid("getGridParam", 'selrow');
                                var tr_par_id = $t.p.treeGridModel == 'adjacency' ? $t.p.treeReader.parent_id_field : 'parent_id';
                                postdata[tr_par_id] = selr;
                            }
                            for(i in $t.p.treeReader){
                                var itm = $t.p.treeReader[i];
                                if(postdata.hasOwnProperty(itm)) {
                                    if(postdata[oper] == opers.addoper && i === 'parent_id_field') { continue; }
                                    delete postdata[itm];
                        }
                        }
                        }
                        postdata = $.extend(postdata,extpost);

                       //增加保持数据到本地方式
                       if(rp_ge.submitType == "clientArray" ){
                           var isChange = false;
                           /*
                           ni = $t.p.rownumbers===true ? 1 :0,
				           gi = $t.p.multiselect ===true ? 1 :0,
				           si = $t.p.subGrid===true ? 1 :0;
                           //idname为主键name
                           if($t.p.keyIndex !== false) {
							   idname = $t.p.colModel[$t.p.keyIndex+gi+si+ni].name;
						   } */
                           for(var i in rp_ge._savedData){
                               //如果数据有改变,给指定单元格设置样式
                               if(rp_ge._savedData[i] !== postdata[i]){
                                   $("#"+$t.p.id+" tr[id='"+rowid +"'] td[aria-describedby='"+$t.p.id+"_"+i +"']").addClass("dirty-cell");
                                   isChange = true;
                               }
                           }
                           if(isChange){
                               $($t).jqGrid("setRowData",rowid,postdata);
                               // $(cc).addClass("dirty-cell");
                               //设置为已编辑状态
						       $($t.rows[rowid]).addClass("edited");
                           }
                           //折叠详细编辑内容行
                           $($t).jqGrid("collapseSubGridRow",rowid);

                       }else{
                            var ajaxUrl = rp_ge.url ? rp_ge.url : $($t).jqGrid('getGridParam','editurl')
                            var ajaxOptions = $.extend({
                            url: ajaxUrl,
                            type: rp_ge.mtype,
                            data: $.isFunction(rp_ge.serializeEditData) ? rp_ge.serializeEditData(postdata) :  postdata,
                            complete:function(data,Status){
                                if(Status != "success") {
                                    ret[0] = false;
                                    if ($.isFunction(rp_ge.errorTextFormat)) {
                                        ret[1] = rp_ge.errorTextFormat(data);
                                    } else {
                                        ret[1] ="\""+ajaxUrl+"\""+ I18N.error+"，"+ Status + " Status: '" + data.statusText + "'. Error code: " + data.status;
                                    }
                                } else {
                                    // data is posted successful
                                    // execute aftersubmit with the returned data from server
                                    if( $.isFunction(rp_ge.afterSubmit) ) {
                                        ret = rp_ge.afterSubmit(data,postdata);
                                    }
                                }
                                if(ret[0] === false) {
                                    $("#FormError>td","#"+frmtb).html(ret[1]);
                                    $("#FormError","#"+frmtb).show();
                                } else {
                                    // remove some values if formattaer select or checkbox
                                    $.each($t.p.colModel, function(i,n){
                                        if(extpost[this.name] && this.formatter && this.formatter=='select') {
                                            try {delete extpost[this.name];} catch (e) {}
                                        }
                                    });
                                    postdata = $.extend(postdata,extpost);
                                    if($t.p.autoencode) {
                                        $.each(postdata,function(n,v){
                                            postdata[n] = $.jgrid.htmlDecode(v);
                                        });
                                    }
                                    rp_ge.reloadAfterSubmit = rp_ge.reloadAfterSubmit && $t.p.datatype != "local";
                                    // the action is add
                                    if(postdata[oper] == opers.addoper ) {
                                        //id processing
                                        // user not set the id ret[2]
                                        if(!ret[2]) { ret[2] = $.jgrid.randId(); }
                                        postdata[idname] = ret[2];
                                        if(rp_ge.closeAfterAdd) {
                                            if(rp_ge.reloadAfterSubmit) { $($t).trigger("reloadGrid"); }
                                            else {
                                                if($t.p.treeGrid === true){
                                                    $($t).jqGrid("addChildNode",ret[2],selr,postdata );
                                                } else {
                                                $($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
                                                $($t).jqGrid("setSelection",ret[2]);
                                            }
                                            }

                                        } else if (rp_ge.clearAfterAdd) {
                                            if(rp_ge.reloadAfterSubmit) { $($t).trigger("reloadGrid"); }
                                            else {
                                                if($t.p.treeGrid === true){
                                                    $($t).jqGrid("addChildNode",ret[2],selr,postdata );
                                                } else {
                                                    $($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
                                                }
                                            }
                                            fillData("_empty",$t,frmgr);
                                        } else {
                                            if(rp_ge.reloadAfterSubmit) { $($t).trigger("reloadGrid"); }
                                            else {
                                                if($t.p.treeGrid === true){
                                                    $($t).jqGrid("addChildNode",ret[2],selr,postdata );
                                                } else {
                                                    $($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
                                        }
                                            }
                                        }
                                    } else {
                                        // the action is update
                                        if(rp_ge.reloadAfterSubmit) {
                                            $($t).trigger("reloadGrid");
                                            if( !rp_ge.closeAfterEdit ) { setTimeout(function(){$($t).jqGrid("setSelection",postdata[idname]);},1000); }
                                        } else {
                                            if($t.p.treeGrid === true) {
                                                $($t).jqGrid("setTreeRow",postdata[idname],postdata);
                                            } else {
                                                $($t).jqGrid("setRowData",postdata[idname],postdata);
                                            }
                                        }

                                    }
                                    if($.isFunction(rp_ge.afterComplete)) {
                                        copydata = data;
                                        setTimeout(function(){rp_ge.afterComplete(copydata,postdata,$("#"+frmgr));copydata=null;},500);
                                    }
                                if(rp_ge.checkOnSubmit || rp_ge.checkOnUpdate) {
                                    $("#"+frmgr).data("disabled",false);
                                    if(rp_ge._savedData[$t.p.id+"_id"] !="_empty"){
                                        for(var key in rp_ge._savedData) {
                                            if(postdata[key]) {
                                                rp_ge._savedData[key] = postdata[key];
                                            }
                                        }
                                    }
                                }
                                   //如果成功提交，折叠详细编辑内容行
                                   $($t).jqGrid("collapseSubGridRow",rowid);
                                }
                                rp_ge.processing=false;
                                $("#"+sb, "#"+frmtb+"_2").removeClass('ui-state-active');
                                try{$(':input:visible',"#"+frmgr)[0].focus();} catch (e){}

                            }
                        }, $.jgrid.ajaxOptions, rp_ge.ajaxEditOptions );

                            if (!ajaxOptions.url && !rp_ge.useDataProxy) {
                            if ($.isFunction($t.p.dataProxy)) {
                                rp_ge.useDataProxy = true;
                            } else {
                                ret[0]=false; ret[1] += " "+$.jgrid.errors.nourl;
                            }
                        }
                            if (ret[0]) {
                                if (rp_ge.useDataProxy) { $t.p.dataProxy.call($t, ajaxOptions, "set_"+$t.p.id); }
                                else { $.ajax(ajaxOptions); }
                            }
                       }
                    }
                    if(ret[0] === false) {
                        $("#FormError>td","#"+frmtb).html(ret[1]);
                        $("#FormError","#"+frmtb).show();
                        // return;
                    }
                }
                function compareData(nObj, oObj ) {
                    var ret = false,key;
                    for (key in nObj) {
                        if(nObj[key] != oObj[key]) {
                            ret = true;
                            break;
                        }
                    }
                    return ret;
                }
                function setNulls() {
                    $.each($t.p.colModel, function(i,n){
                        if(n.editoptions && n.editoptions.NullIfEmpty === true) {
                            if(postdata.hasOwnProperty(n.name) && postdata[n.name] == "") {
                                postdata[n.name] = 'null';
                            }
                        }
                    });
                }
                function checkUpdates () {
                    var stat = true;
                    $("#FormError","#"+frmtb).hide();
                    if(rp_ge.checkOnUpdate) {
                        postdata = {}; extpost={};
                        getFormData();
                        newData = $.extend({},postdata,extpost);
                        diff = compareData(newData,rp_ge._savedData);
                        if(diff) {
                            $("#"+frmgr).data("disabled",true);
                            $(".confirm","#"+IDs.themodal).show();
                            stat = false;
                        } else {
                            setNulls();
                        }
                    }
                    return stat;
                }
                function restoreInline()
                {
                    if (rowid !== "_empty" && typeof($t.p.savedRow) !== "undefined" && $t.p.savedRow.length > 0 && $.isFunction($.fn.jqGrid.restoreRow)) {
                        for (var i=0;i<$t.p.savedRow.length;i++) {
                            if ($t.p.savedRow[i].id == rowid) {
                                $($t).jqGrid('restoreRow',rowid);
                                break;
                            }
                        }
                    }
                }
                function updateNav(cr,totr){
                    if (cr===0) { $("#pData","#"+frmtb+"_2").addClass('ui-state-disabled'); } else { $("#pData","#"+frmtb+"_2").removeClass('ui-state-disabled'); }
                    if (cr==totr) { $("#nData","#"+frmtb+"_2").addClass('ui-state-disabled'); } else { $("#nData","#"+frmtb+"_2").removeClass('ui-state-disabled'); }
                }
                function getCurrPos() {
                    var rowsInGrid = $($t).jqGrid("getDataIDs"),
                    selrow = $("#id_g","#"+frmtb).val(),
                    pos = $.inArray(selrow,rowsInGrid);
                    return [pos,rowsInGrid];
                }

               //处理开始
               var dh = isNaN(p.dataheight) ? p.dataheight : p.dataheight+"px",
               frm = $("<form name='FormPost' id='"+frmgr+"' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:"+dh+";'></form>").data("disabled",false),
               tbl = $("<table id='"+frmtb+"' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>");
               if(onBeforeInit) {
                    showFrm = onBeforeInit($("#"+frmgr));
                    if(typeof(showFrm) == "undefined") {
                       showFrm = true;
                    }
               }
               if(showFrm === false) { return; }
               restoreInline();
               $($t.p.colModel).each( function(i) {
                   var fmto = this.formoptions;
                   maxCols = Math.max(maxCols, fmto ? fmto.colpos || 0 : 0 );
                   maxRows = Math.max(maxRows, fmto ? fmto.rowpos || 0 : 0 );
               });
               $(frm).append(tbl);
               var flr = $("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='"+(maxCols*2)+"'></td></tr>");
               flr[0].rp = 0;
               $(tbl).append(flr);
               //topinfo
               flr = $("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='"+(maxCols*2)+"'>"+rp_ge.topinfo+"</td></tr>");
               flr[0].rp = 0;
               $(tbl).append(flr);
                    // set the id.
                    // use carefull only to change here colproperties.
                    // create data
               var rtlb = $t.p.direction == "rtl" ? true :false,
               bp = rtlb ? "nData" : "pData",
               bn = rtlb ? "pData" : "nData",
               sb = "sData_" + rp_ge.subgrid_id ,
               cd = "cData_" + rp_ge.subgrid_id ;
               createData(rowid,$t,tbl,maxCols);
                    // buttons at footer
               var bP = "<a href='javascript:void(0)' id='"+bp+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></div>",
               bN = "<a href='javascript:void(0)' id='"+bn+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></div>",
               bS  ="<a href='javascript:void(0)' id='" +sb +"' class='ui-state-default ui-corner-all'></a>",
				bC  ="<a href='javascript:void(0)' id='" +cd+"' class='ui-state-default ui-corner-all'></a>";
               //bS  ="<a href='javascript:void(0)' id='" +sb +"' class='fm-button '>"+p.save+"</a>",
              //bC  ="<a href='javascript:void(0)' id='" +cd+"' class='fm-button '>"+p.bCancel+"</a>";
               var bt = "<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='"+frmtb+"_2'><tbody><tr id='Act_Buttons'><td class='EditButton'>"+bS+bC+"</td></tr>";
               bt += "<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>"+rp_ge.bottominfo+"</td></tr>";
               bt += "</tbody></table>";
               if(maxRows >  0) {
                   var sd=[];
                   $.each($(tbl)[0].rows,function(i,r){
                          sd[i] = r;
                   });
                   sd.sort(function(a,b){
                       if(a.rp > b.rp) {return 1;}
                       if(a.rp < b.rp) {return -1;}
                       return 0;
                   });
                   $.each(sd, function(index, row) {
                       $('tbody',tbl).append(row);
                   });
               }
               p.gbox = "#gbox_"+gID;
               var cle = false;
               if(p.closeOnEscape===true){
                    p.closeOnEscape = false;
                    cle = true;
               }
               var tms = $("<span></span>").append(frm).append(bt);
                //如果已经创建相关元素，不做处理
                //if($("#"+rp_ge.subgrid_id+" #"+frmgr)==null){
                    $.jgrid.createSubModal(IDs,tms,p,"#gview_"+$t.p.id,$("#gbox_"+$t.p.id)[0],"#"+rp_ge.subgrid_id);
               //}
               if(rtlb) {
                    $("#pData, #nData","#"+frmtb+"_2").css("float","right");
                    $(".EditButton","#"+frmtb+"_2").css("text-align","left");
               }
               if(rp_ge.topinfo) { $(".tinfo","#"+frmtb+"_2").show(); }
               if(rp_ge.bottominfo) { $(".binfo","#"+frmtb+"_2").show(); }
               tms = null; bt=null;
               p.saveicon = $.extend([true,"left","ui-icon-disk"],p.saveicon);
               p.closeicon = $.extend([true,"left","ui-icon-close"],p.closeicon);
               // beforeinitdata after creation of the form
               if(p.saveicon[0]===true) {
                   $("#"+sb,"#"+frmtb+"_2").button({icons: {primary: p.saveicon[2]},label:p.bSubmit});
               }
               if(p.closeicon[0]===true) {
                    $("#"+cd,"#"+frmtb+"_2").button({icons: {primary:p.closeicon[2]},label:p.bCancel});
               }
               if(rp_ge.checkOnSubmit || rp_ge.checkOnUpdate) {
                    bS  ="<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bYes+"</a>";
                    bN  ="<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bNo+"</a>";
                    bC  ="<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bExit+"</a>";
                    var ii, zI = p.zIndex  || 999; zI ++;
                    if ($.browser.msie && $.browser.version ==6) {
                        ii = '<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>';
                    } else { ii="";}
                    $("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:"+zI+";display:none;'>&#160;"+ii+"</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:"+(zI+1)+"'>"+p.saveData+"<br/><br/>"+bS+bN+bC+"</div>").insertAfter("#"+frmgr);

               }
               // here initform - only once
               if(onInitializeForm) { onInitializeForm($("#"+frmgr)); }
               if(rowid=="_empty" || !rp_ge.viewPagerButtons) { $("#pData,#nData","#"+frmtb+"_2").hide(); } else { $("#pData,#nData","#"+frmtb+"_2").show(); }
               $(".fm-button","#"+IDs.modalcontent).hover(
                   function(){$(this).addClass('ui-state-hover');},
                   function(){$(this).removeClass('ui-state-hover');}
               );
               $("#"+sb, "#"+frmtb+"_2").click(function(e){
                    postdata = {}; extpost={};
                    $("#FormError","#"+frmtb).hide();
                    // all depend on ret array
                    //ret[0] - succes
                    //ret[1] - msg if not succes
                    //ret[2] - the id  that will be set if reload after submit false
                    getFormData();
                    setNulls();
                    if(postdata[$t.p.id+"_id"] == "_empty")	{ postIt(); }
                    //else if(p.checkOnSubmit===true || rp_ge.submitType=="clientArray") {
                    else if(p.checkOnSubmit===true) {
                        newData = $.extend({},postdata,extpost);
                        diff = compareData(newData,rp_ge._savedData);
                        if(diff) {
                            $("#"+frmgr).data("disabled",true);
                            $(".confirm","#"+IDs.themodal).show();
                        } else {
                            postIt();
                        }
                    } else {
                        postIt();
                    }
                    return false;
               });
               $("#"+cd, "#"+frmtb+"_2").click(function(e){
                    if(!checkUpdates()) { return false; }
                    //折叠详细编辑内容行
                    $($t).jqGrid("collapseSubGridRow",rowid);
                   // $.jgrid.hideModal("#"+IDs.themodal,{gb:"#gbox_"+gID,jqm:p.jqModal,onClose: rp_ge.onClose});
                    return false;
               });
               $("#nData", "#"+frmtb+"_2").click(function(e){
                    if(!checkUpdates()) { return false; }
                    $("#FormError","#"+frmtb).hide();
                    var npos = getCurrPos();
                    npos[0] = parseInt(npos[0],10);
                    if(npos[0] != -1 && npos[1][npos[0]+1]) {
                        if($.isFunction(p.onclickPgButtons)) {
                            p.onclickPgButtons('next',$("#"+frmgr),npos[1][npos[0]]);
                        }
                        fillData(npos[1][npos[0]+1],$t,frmgr);
                        $($t).jqGrid("setSelection",npos[1][npos[0]+1]);
                        if($.isFunction(p.afterclickPgButtons)) {
                            p.afterclickPgButtons('next',$("#"+frmgr),npos[1][npos[0]+1]);
                        }
                        updateNav(npos[0]+1,npos[1].length-1);
                    }
                    return false;
               });
               $("#pData", "#"+frmtb+"_2").click(function(e){
                   if(!checkUpdates()) { return false; }
                   $("#FormError","#"+frmtb).hide();
                   var ppos = getCurrPos();
                   if(ppos[0] != -1 && ppos[1][ppos[0]-1]) {
                        if($.isFunction(p.onclickPgButtons)) {
                            p.onclickPgButtons('prev',$("#"+frmgr),ppos[1][ppos[0]]);
                        }
                        fillData(ppos[1][ppos[0]-1],$t,frmgr);
                        $($t).jqGrid("setSelection",ppos[1][ppos[0]-1]);
                        if($.isFunction(p.afterclickPgButtons)) {
                            p.afterclickPgButtons('prev',$("#"+frmgr),ppos[1][ppos[0]-1]);
                        }
                        updateNav(ppos[0]-1,ppos[1].length-1);
                   }
                   return false;
               });
                //处理结束
                var posInit =getCurrPos();
                updateNav(posInit[0],posInit[1].length-1);

            });
	    },
        //添加内嵌到subgrid中详细编辑处理
        editSubRow:function($rowid,opt){
            var nhc=0,pID = this[0].p.id,_id = $rowid,$t=this[0],$opt= $.extend({"subgrid_id":$t.p.id+"_"+$rowid},opt),
                ni = $t.p.rownumbers===true ? 1 :0,
                gi = $t.p.multiselect ===true ? 1 :0,
                atd = (ni+gi) >=1 ? "<td colspan='"+(ni+gi)+"'>&#160;</td>":"";
            $.each($t.p.colModel,function(){
                if(this.hidden === true || this.name === 'rn' || this.name === 'cb') {
				nhc++;
                }
	        });
           //保证不重复添加
           if( $("tr[id='"+$rowid +"'] + tr[class*='ui-subgrid']",$t).length==0){
            var selRow = $("tr[id='"+$rowid +"']",$t).after( "<tr role='row' class='ui-subgrid'>"+atd
                        +"<td class='ui-widget-content subgrid-cell'><span class='ui-icon "+$t.p.subGridOptions.openicon+"'></span></td><td colspan='"
                        +parseInt($t.p.colNames.length-1-nhc,10)+"' class='ui-widget-content subgrid-data'><div id="+pID+"_"
                        +_id+" class='tablediv'></div></td></tr>");
            var rowElment = $("tr[id='"+$rowid +"'] td[class*='ui-sgcollapsed']",this);
            rowElment.removeClass("sgcollapsed").addClass("sgexpanded");
            $("span[class*='ui-icon']",rowElment).removeClass($t.p.subGridOptions.plusicon).addClass($t.p.subGridOptions.minusicon);
            }
            $($t).jqGrid("editDetailRow",$rowid,$opt);


        },
        //保持行 增加datepicker处理
        saveRow : function(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc) {
		// Compatible mode old versions
		var args = $.makeArray(arguments).slice(1), o = {};

		if( $.type(args[0]) === "object" ) {
			o = args[0];
		} else {
			if ($.isFunction(successfunc)) { o.successfunc = successfunc; }
			if (typeof url !== "undefined") { o.url = url; }
			if (typeof extraparam !== "undefined") { o.extraparam = extraparam; }
			if ($.isFunction(aftersavefunc)) { o.aftersavefunc = aftersavefunc; }
			if ($.isFunction(errorfunc)) { o.errorfunc = errorfunc; }
			if ($.isFunction(afterrestorefunc)) { o.afterrestorefunc = afterrestorefunc; }
		}
		o = $.extend(true, {
			successfunc: null,
			url: null,
			extraparam: {},
			aftersavefunc: null,
			errorfunc: null,
			afterrestorefunc: null,
			restoreAfterError: true,
			mtype: "POST"
		}, $.jgrid.inlineEdit, o );
		// End compatible

		var success = false;
		var $t = this[0], nm, tmp={}, tmp2={}, tmp3= {}, editable, fr, cv, ind;
		if (!$t.grid ) { return success; }
		ind = $($t).jqGrid("getInd",rowid,true);
		if(ind === false) {return success;}
		editable = $(ind).attr("editable");
		o.url = o.url ? o.url : $t.p.editurl;
		if (editable==="1") {
			var cm;
			$('td[role="gridcell"]',ind).each(function(i) {
				cm = $t.p.colModel[i];
				nm = cm.name;
				if ( nm != 'cb' && nm != 'subgrid' && cm.editable===true && nm != 'rn' && !$(this).hasClass('not-editable-cell')) {
					switch (cm.edittype) {
						case "checkbox":
							var cbv = ["Yes","No"];
							if(cm.editoptions ) {
								cbv = cm.editoptions.value.split(":");
							}
							tmp[nm]=  $("input",this).is(":checked") ? cbv[0] : cbv[1];
							break;
						case 'text':
						case 'password':
						case 'textarea':
                        case "comboboxlist":
                        case "comboboxtree":
                        case 'datepicker':
						case "button" :
							tmp[nm]=$("input, textarea",this).val();
							break;
						case 'select':
							if(!cm.editoptions.multiple) {
								tmp[nm] = $("select option:selected",this).val();
								tmp2[nm] = $("select option:selected", this).text();
							} else {
								var sel = $("select",this), selectedText = [];
								tmp[nm] = $(sel).val();
								if(tmp[nm]) { tmp[nm]= tmp[nm].join(","); } else { tmp[nm] =""; }
								$("select option:selected",this).each(
									function(i,selected){
										selectedText[i] = $(selected).text();
									}
								);
								tmp2[nm] = selectedText.join(",");
							}
							if(cm.formatter && cm.formatter == 'select') { tmp2={}; }
							break;
						case 'custom' :
							try {
								if(cm.editoptions && $.isFunction(cm.editoptions.custom_value)) {
									tmp[nm] = cm.editoptions.custom_value.call($t, $(".customelement",this),'get');
									if (tmp[nm] === undefined) { throw "e2"; }
								} else { throw "e1"; }
							} catch (e) {
								if (e=="e1") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,$.jgrid.edit.bClose); }
								if (e=="e2") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,$.jgrid.edit.bClose); }
								else { $.jgrid.info_dialog($.jgrid.errors.errcap,e.message,$.jgrid.edit.bClose); }
							}
							break;
					}
					cv = $.jgrid.checkValues(tmp[nm],i,$t);
					if(cv[0] === false) {
						cv[1] = tmp[nm] + " " + cv[1];
						return false;
					}
					if($t.p.autoencode) { tmp[nm] = $.jgrid.htmlEncode(tmp[nm]); }
					if(o.url !== 'clientArray' && cm.editoptions && cm.editoptions.NullIfEmpty === true) {
						if(tmp[nm] === "") {
							tmp3[nm] = 'null';
						}
					}
				}
			});
			if (cv[0] === false){
				try {
					var positions = $.jgrid.findPos($("#"+$.jgrid.jqID(rowid), $t.grid.bDiv)[0]);
					$.jgrid.info_dialog($.jgrid.errors.errcap,cv[1],$.jgrid.edit.bClose,{left:positions[0],top:positions[1]});
				} catch (e) {
					alert(cv[1]);
				}
				return success;
			}
			var idname, opers, oper;
			opers = $t.p.prmNames;
			oper = opers.oper;
			idname = opers.id;
			if(tmp) {
				tmp[oper] = opers.editoper;
				tmp[idname] = rowid;
				if(typeof($t.p.inlineData) == 'undefined') { $t.p.inlineData ={}; }
				tmp = $.extend({},tmp,$t.p.inlineData,o.extraparam);
			}
			if (o.url == 'clientArray') {
				tmp = $.extend({},tmp, tmp2);
				if($t.p.autoencode) {
					$.each(tmp,function(n,v){
						tmp[n] = $.jgrid.htmlDecode(v);
					});
				}
				var resp = $($t).jqGrid("setRowData",rowid,tmp);
				$(ind).attr("editable","0");
				//給編輯過的單元格添加標示
                for(var i in tmp){
                    if( $t.p.savedRow[0][i]!=undefined && tmp[i]!=$t.p.savedRow[0][i]){
                         var temp = $(" tr[id="+rowid+"]",$t).addClass("edited");
                         $("td[aria-describedby=\""+$t.p.id+'_'+i+"\"]",temp).addClass("dirty-cell");
                    }
                }
				for( var k=0;k<$t.p.savedRow.length;k++) {
					if( $t.p.savedRow[k].id == rowid) {fr = k; break;}
				}
				if(fr >= 0) { $t.p.savedRow.splice(fr,1); }
				$($t).triggerHandler("jqGridInlineAfterSaveRow", [rowid, resp, tmp, o]);
				if( $.isFunction(o.aftersavefunc) ) { o.aftersavefunc.call($t, rowid,resp); }
				success = true;
				$(ind).unbind("keydown");
			} else {
				$("#lui_"+$.jgrid.jqID($t.p.id)).show();
				tmp3 = $.extend({},tmp,tmp3);
                //把下拉框值传过去
                $.extend(tmp3,tmp2);
				tmp3[idname] = $.jgrid.stripPref($t.p.idPrefix, tmp3[idname]);
				$.ajax($.extend({
					url:o.url,
					data: $.isFunction($t.p.serializeRowData) ? $t.p.serializeRowData.call($t, tmp3) : tmp3,
					type: o.mtype,
					async : false, //?!?
					complete: function(res,stat){
						$("#lui_"+$.jgrid.jqID($t.p.id)).hide();
						if (stat === "success"){
							var ret = true, sucret;
							sucret = $($t).triggerHandler("jqGridInlineSuccessSaveRow", [res, rowid, o]);
							if (!$.isArray(sucret)) {sucret = [true, tmp];}
							if (sucret[0] && $.isFunction(o.successfunc)) {sucret = o.successfunc.call($t, res);}
							if($.isArray(sucret)) {
								// expect array - status, data, rowid
								ret = sucret[0];
								tmp = sucret[1] ? sucret[1] : tmp;
							} else {
								ret = sucret;
							}
							if (ret===true) {
								if($t.p.autoencode) {
									$.each(tmp,function(n,v){
										tmp[n] = $.jgrid.htmlDecode(v);
									});
								}
								tmp = $.extend({},tmp, tmp2);
								$($t).jqGrid("setRowData",rowid,tmp);
								$(ind).attr("editable","0");
								for( var k=0;k<$t.p.savedRow.length;k++) {
									if( $t.p.savedRow[k].id == rowid) {fr = k; break;}
								}
								if(fr >= 0) { $t.p.savedRow.splice(fr,1); }
								$($t).triggerHandler("jqGridInlineAfterSaveRow", [rowid, res, tmp, o]);
								if( $.isFunction(o.aftersavefunc) ) { o.aftersavefunc.call($t, rowid,res); }
								success = true;
								$(ind).unbind("keydown");
							} else {
								$($t).triggerHandler("jqGridInlineErrorSaveRow", [rowid, res, stat, null, o]);
								if($.isFunction(o.errorfunc) ) {
									o.errorfunc.call($t, rowid, res, stat, null);
								}
								if(o.restoreAfterError === true) {
									$($t).jqGrid("restoreRow",rowid, o.afterrestorefunc);
								}
							}
						}
					},
					error:function(res,stat,err){
						$("#lui_"+$.jgrid.jqID($t.p.id)).hide();
						$($t).triggerHandler("jqGridInlineErrorSaveRow", [rowid, res, stat, err, o]);
						if($.isFunction(o.errorfunc) ) {
							o.errorfunc.call($t, rowid, res, stat, err);
						} else {
							try {
								jAlert("\""+o.url+"\""+ I18N.error+"，"+ res.responseText, $.jgrid.errors.errcap);
							} catch(e) {
								alert(res.responseText);
							}
						}
						if(o.restoreAfterError === true) {
							$($t).jqGrid("restoreRow",rowid, o.afterrestorefunc);
						}
					}
				}, $.jgrid.ajaxOptions, $t.p.ajaxRowOptions || {}));
			}
		}
		return success;
	},
        // 行编辑导航栏 修改新增行id序号
        inlineNav : function (elem, o) {
		o = $.extend({
			edit: true,
			editicon: "ui-icon-pencil",
			add: true,
			addicon:"ui-icon-plus",
			save: true,
			saveicon:"ui-icon-disk",
			cancel: true,
			cancelicon:"ui-icon-cancel",
			addParams : {useFormatter : false,rowID : "new_row"},
			editParams : {},
			restoreAfterSelect : true
		}, $.jgrid.nav, o ||{});
		return this.each(function(){
			if (!this.grid ) { return; }
			var $t = this, onSelect, gID = elem.id;
			$t.p._inlinenav = true;
			function showAddEditButtons(){
                $("#"+gID+"_ilsave").addClass('ui-state-disabled');
                $("#"+gID+"_ilcancel").addClass('ui-state-disabled');
                $("#"+gID+"_iladd").removeClass('ui-state-disabled');
                $("#"+gID+"_iledit").removeClass('ui-state-disabled');
            }
			// detect the formatactions column
			if(o.addParams.useFormatter === true) {
				var cm = $t.p.colModel,i;
				for (i = 0; i<cm.length; i++) {
					if(cm[i].formatter && cm[i].formatter === "actions" ) {
						if(cm[i].formatoptions) {
							var defaults =  {
								keys:false,
								onEdit : null,
								onSuccess: null,
								afterSave:null,
								onError: null,
								afterRestore: null,
								extraparam: {},
								url: null
							},
							ap = $.extend( defaults, cm[i].formatoptions );
							o.addParams.addRowParams = {
								"keys" : ap.keys,
								"oneditfunc" : ap.onEdit,
								"successfunc" : ap.onSuccess,
								"url" : ap.url,
								"extraparam" : ap.extraparam,
								"aftersavefunc" : ap.afterSavef,
								"errorfunc": ap.onError,
								"afterrestorefunc" : ap.afterRestore
							};
						}
						break;
					}
				}
			}
			if(o.add) {
				$($t).jqGrid('navButtonAdd', elem,{
					caption : o.addtext,
					title : o.addtitle,
					buttonicon : o.addicon,
					id : gID+"_iladd",
					onClickButton : function () {
                        //新增行序号
                        o.addParams.rowID = $t.p.addParams.rowID + $t.p.addParams.serial;
						$($t).jqGrid('addRow', o.addParams);
                        //序号维护
                        $t.p.addParams.serial++;
						if(!o.addParams.useFormatter) {
							$("#"+gID+"_ilsave").removeClass('ui-state-disabled');
							$("#"+gID+"_ilcancel").removeClass('ui-state-disabled');
							$("#"+gID+"_iladd").addClass('ui-state-disabled');
							$("#"+gID+"_iledit").addClass('ui-state-disabled');
						}
					}
				});
			}
			if(o.edit) {
				$($t).jqGrid('navButtonAdd', elem,{
					caption : o.edittext,
					title : o.edittitle,
					buttonicon : o.editicon,
					id : gID+"_iledit",
					onClickButton : function () {
						var sr = $($t).jqGrid('getGridParam','selrow');
						if(sr) {
							$($t).jqGrid('editRow', sr, o.editParams);
							$("#"+gID+"_ilsave").removeClass('ui-state-disabled');
							$("#"+gID+"_ilcancel").removeClass('ui-state-disabled');
							$("#"+gID+"_iladd").addClass('ui-state-disabled');
							$("#"+gID+"_iledit").addClass('ui-state-disabled');
						} else {
                            jAlert(o.alerttext, o.alertcap);
							//$.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+gID,jqm:true});$("#jqg_alrt").focus();
						}
					}
				});
			}
			if(o.save) {
				$($t).jqGrid('navButtonAdd', elem,{
					caption : o.savetext || '保存',
					title : o.savetitle || '保存编辑数据',
					buttonicon : o.saveicon,
					id : gID+"_ilsave",
					onClickButton : function () {
						var sr = $t.p.savedRow[0].id;
						if(sr) {
							var opers = $t.p.prmNames,
							oper = opers.oper;
							if(!o.editParams.extraparam) {
								o.editParams.extraparam = {};
							}
							if($("#"+$.jgrid.jqID(sr), "#"+gID ).hasClass("jqgrid-new-row")) {
								o.editParams.extraparam[oper] = opers.addoper;
							} else {
								o.editParams.extraparam[oper] = opers.editoper;
							}

							if( $($t).jqGrid('saveRow', sr, o.editParams) ) {
                                if(o.editParams.url === "clientArray"){
                                    showAddEditButtons();
                                }else{$($t).trigger("reloadGrid");}
							}else{
                                showAddEditButtons();
                            }
                           // $($t).jqGrid('saveRow', sr, o.editParams);
                           // $($t).jqGrid('showAddEditButtons');
						} else {
                            jAlert(o.alerttext, o.alertcap);
							//$.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+gID,jqm:true});$("#jqg_alrt").focus();
						}
					}
				});
				$("#"+gID+"_ilsave").addClass('ui-state-disabled');
			}
			if(o.cancel) {
				$($t).jqGrid('navButtonAdd', elem,{
					caption : o.canceltext || '取消',
					title : o.canceltitle || '取消数据编辑',
					buttonicon : o.cancelicon,
					id : gID+"_ilcancel",
					onClickButton : function () {
						var sr = $t.p.savedRow[0].id;
						if(sr) {
							$($t).jqGrid('restoreRow', sr, o.editParams);
							showAddEditButtons();
						} else {
                            jAlert(o.alerttext, o.alertcap);
							//$.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+gID,jqm:true});$("#jqg_alrt").focus();
						}
					}
				});
				$("#"+gID+"_ilcancel").addClass('ui-state-disabled');
			}
			if(o.restoreAfterSelect === true) {
				if($.isFunction($t.p.beforeSelectRow)) {
					onSelect = $t.p.beforeSelectRow;
				} else {
					onSelect =  false;
				}
				$t.p.beforeSelectRow = function(id, stat) {
					var ret = true;
					if($t.p.savedRow.length > 0 && $t.p._inlinenav===true && ( id !== $t.p.selrow && $t.p.selrow !==null) ) {
						if($t.p.selrow == o.addParams.rowID ) {
							$($t).jqGrid('delRowData', $t.p.selrow);
						} else {
							$($t).jqGrid('restoreRow', $t.p.selrow, o.editParams);
						}
						showAddEditButtons();
					}
					if(onSelect) {
						ret = onSelect.call($t, id, stat);
					}
					return ret;
				};
			}
			//导航栏按钮
            //$(".ui-pg-div").button();
		});
	},
        //弹出框编辑 添加datepicker处理
        editGridRow : function(rowid, p){
		p = $.extend({
			top : 0,
			left: 0,
			width: 600,
			height: 'auto',
			dataheight: 'auto',
			modal: false,
			overlay : 30,
			drag: true,
			resize: true,
			url: null,
			mtype : "post",
			clearAfterAdd :true,
			closeAfterEdit : false,
			reloadAfterSubmit : true,
			onInitializeForm: null,
			beforeInitData: null,
			beforeShowForm: null,
			afterShowForm: null,
			beforeSubmit: null,
			afterSubmit: null,
			onclickSubmit: null,
			afterComplete: null,
			onclickPgButtons : null,
			afterclickPgButtons: null,
			editData : {},
			recreateForm : false,
			jqModal : true,
			closeOnEscape : false,
			addedrow : "first",
			topinfo : '',
			bottominfo: '',
			saveicon : [],
			closeicon : [],
			savekey: [false,13],
			navkeys: [false,38,40],
			checkOnSubmit : false,
			checkOnUpdate : false,
			_savedData : {},
			processing : false,
			onClose : null,
			ajaxEditOptions : {},
			serializeEditData : null,
			viewPagerButtons : true
		}, $.jgrid.edit, p || {});
        rp_ge[$(this)[0].p.id] = p;
		return this.each(function(){
			var $t = this;
			if (!$t.grid || !rowid) {return;}
			var gID = $t.p.id,
			frmgr = "FrmGrid_"+gID, frmtborg = "TblGrid_"+gID, frmtb = "#"+$.jgrid.jqID(frmtborg),
			IDs = {themodal:'editmod'+gID,modalhead:'edithd'+gID,modalcontent:'editcnt'+gID, scrollelm : frmgr},
			onBeforeShow = $.isFunction(rp_ge[$t.p.id].beforeShowForm) ? rp_ge[$t.p.id].beforeShowForm : false,
			onAfterShow = $.isFunction(rp_ge[$t.p.id].afterShowForm) ? rp_ge[$t.p.id].afterShowForm : false,
			onBeforeInit = $.isFunction(rp_ge[$t.p.id].beforeInitData) ? rp_ge[$t.p.id].beforeInitData : false,
			onInitializeForm = $.isFunction(rp_ge[$t.p.id].onInitializeForm) ? rp_ge[$t.p.id].onInitializeForm : false,
			showFrm = true,
			maxCols = 1, maxRows=0,	postdata, extpost, newData, diff, frmoper;
			frmgr = $.jgrid.jqID(frmgr);
			if (rowid === "new") {
				rowid = "_empty";
				frmoper = "add";
				p.caption=rp_ge[$t.p.id].addCaption;
			} else {
				p.caption=rp_ge[$t.p.id].editCaption;
				frmoper = "edit";
			}
			if(p.recreateForm===true && $("#"+$.jgrid.jqID(IDs.themodal)).html() !== null) {
				$("#"+$.jgrid.jqID(IDs.themodal)).remove();
			}
			var closeovrl = true;
			if(p.checkOnUpdate && p.jqModal && !p.modal) {
				closeovrl = false;
			}
			function getFormData(){
				$(frmtb+" > tbody > tr > td > .FormElement").each(function() {
					var celm = $(".customelement", this);
					if (celm.length) {
						var  elem = celm[0], nm = $(elem).attr('name');
						$.each($t.p.colModel, function(){
							if(this.name === nm && this.editoptions && $.isFunction(this.editoptions.custom_value)) {
								try {
									postdata[nm] = this.editoptions.custom_value.call($t, $("#"+$.jgrid.jqID(nm),frmtb),'get');
									if (postdata[nm] === undefined) {throw "e1";}
								} catch (e) {
									if (e==="e1") {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose);}
									else {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose);}
								}
								return true;
							}
						});
					} else {
					switch ($(this).get(0).type || $( "input",this).get(0).type) {
						case "checkbox":
							if($("input",this).is(":checked")) {
                                var str = "",checkElement = "input[type=checkbox]:checked";
                                $(checkElement,this).each(function(i){
                                     str+= (((i==0)?"":",") + $(this).val());
                                }) ;
                                postdata[$(this).attr("name")]= str;
							}else {
								postdata[$(this).attr("name")]= "";
							}
						break;
						case "radio":
							if($("input",this).is(":checked")) {
                                var str = "",checkElement = "input[type=radio]:checked";
                                $(checkElement,this).each(function(i){
                                     str= $(this).val();
                                }) ;
                                postdata[$(this).attr("name")]= str;
							}else {
								postdata[$(this).attr("name")]= "";
							}
						break;
						case "select-one":
							postdata[this.name]= $("option:selected",this).val();
							extpost[this.name]= $("option:selected",this).text();
						break;
						case "select-multiple":
							postdata[this.name]= $(this).val();
							if(postdata[this.name]) {postdata[this.name] = postdata[this.name].join(",");}
							else {postdata[this.name] ="";}
							var selectedText = [];
							$("option:selected",this).each(
								function(i,selected){
									selectedText[i] = $(selected).text();
								}
							);
							extpost[this.name]= selectedText.join(",");
						break;
						case "password":
						case "text":
						case "textarea":
                        case "comboboxlist":
                        case "comboboxtree":
                        case "datepicker":
						case "button":
							postdata[this.name] = $(this).val();

						break;
					}
					if($t.p.autoencode) {postdata[this.name] = $.jgrid.htmlEncode(postdata[this.name]);}
					}
				});
				return true;
			}
			function createData(rowid,obj,tb,maxcols){
				var nm, hc,trdata, cnt=0,tmp, dc,elc, retpos=[], ind=false,
				tdtmpl = "<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>", tmpl="", i; //*2
				for (i =1; i<=maxcols;i++) {
					tmpl += tdtmpl;
				}
				if(rowid != '_empty') {
					ind = $(obj).jqGrid("getInd",rowid);
				}
				$(obj.p.colModel).each( function(i) {
					nm = this.name;
					// hidden fields are included in the form
					if(this.editrules && this.editrules.edithidden === true) {
						hc = false;
					} else {
						hc = this.hidden === true ? true : false;
					}
					dc = hc ? "style='display:none'" : "";
					if ( nm !== 'cb' && nm !== 'subgrid' && this.editable===true && nm !== 'rn') {
						if(ind === false) {
							tmp = "";
						} else {
							if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
								tmp = $("td:eq("+i+")",obj.rows[ind]).text();
							} else {
								try {
									tmp =  $.unformat.call(obj, $("td:eq("+i+")",obj.rows[ind]),{rowId:rowid, colModel:this},i);
								} catch (_) {
									tmp =  (this.edittype && this.edittype == "textarea") ? $("td:eq("+i+")",obj.rows[ind]).text() : $("td:eq("+i+")",obj.rows[ind]).html();
								}
								if(!tmp || tmp == "&nbsp;" || tmp == "&#160;" || (tmp.length==1 && tmp.charCodeAt(0)==160) ) {tmp='';}
							}
						}
						var opt = $.extend({}, this.editoptions || {} ,{id:nm,name:nm}),
						frmopt = $.extend({}, {elmprefix:'',elmsuffix:'',rowabove:false,rowcontent:''}, this.formoptions || {}),
						rp = parseInt(frmopt.rowpos,10) || cnt+1,
						cp = parseInt((parseInt(frmopt.colpos,10) || 1)*2,10);
						if(rowid == "_empty" && opt.defaultValue ) {
							tmp = $.isFunction(opt.defaultValue) ? opt.defaultValue() : opt.defaultValue;
						}
						if(!this.edittype) {this.edittype = "text";}
						if($t.p.autoencode) {tmp = $.jgrid.htmlDecode(tmp);}
						elc = $.jgrid.createEl(this.edittype,opt,tmp,false,$.extend({},$.jgrid.ajaxOptions,obj.p.ajaxSelectOptions || {}));
						//if(tmp === "" && this.edittype == "checkbox") {tmp = $(elc).attr("offval");}
						//if(tmp === "" && this.edittype == "select") {tmp = $("option:eq(0)",elc).text();}
						if(rp_ge[$t.p.id].checkOnSubmit || rp_ge[$t.p.id].checkOnUpdate) {rp_ge[$t.p.id]._savedData[nm] = tmp;}
						$(elc).addClass("FormElement");
						if(this.edittype == 'text' || this.edittype == 'textarea' || this.edittype == 'datepicker'
                                || this.edittype == 'comboboxlist' || this.edittype == 'comboboxtree') {
							//$(elc).addClass("ui-widget-content");
						}
						trdata = $(tb).find("tr[rowpos="+rp+"]");
						if(frmopt.rowabove) {
							var newdata = $("<tr><td class='contentinfo' colspan='"+(maxcols*2)+"'>"+frmopt.rowcontent+"</td></tr>");
							$(tb).append(newdata);
							newdata[0].rp = rp;
						}
						if ( trdata.length===0 ) {
							trdata = $("<tr "+dc+" rowpos='"+rp+"'></tr>").addClass("FormData").attr("id","tr_"+nm);
							$(trdata).append(tmpl);
							$(tb).append(trdata);
							trdata[0].rp = rp;
						}
                        //单元格后面添加“：”
						$("td:eq("+(cp-2)+")",trdata[0]).html( (typeof frmopt.label === 'undefined' ? obj.p.colNames[i]: frmopt.label)+" :");
						$("td:eq("+(cp-1)+")",trdata[0]).append(frmopt.elmprefix).append(elc).append(frmopt.elmsuffix);
						retpos[cnt] = i;
						cnt++;
					}
				});
				if( cnt > 0) {
					var idrow = $("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+ (maxcols*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='"+obj.p.id+"_id' value='"+rowid+"'/></td></tr>");
					idrow[0].rp = cnt+999;
					$(tb).append(idrow);
					if(rp_ge[$t.p.id].checkOnSubmit || rp_ge[$t.p.id].checkOnUpdate) {rp_ge[$t.p.id]._savedData[obj.p.id+"_id"] = rowid;}
				}
				return retpos;
			}
			function fillData(rowid,obj,fmid){
				var nm,cnt=0,tmp, fld,opt,vl,vlc;
				if(rp_ge[$t.p.id].checkOnSubmit || rp_ge[$t.p.id].checkOnUpdate) {rp_ge[$t.p.id]._savedData = {};rp_ge[$t.p.id]._savedData[obj.p.id+"_id"]=rowid;}
				var cm = obj.p.colModel;
				if(rowid == '_empty') {
					$(cm).each(function(){
						nm = this.name;
						opt = $.extend({}, this.editoptions || {} );
						fld = $("#"+$.jgrid.jqID(nm),"#"+fmid);
						if(fld && fld.length && fld[0] !== null) {
							vl = "";
							if(opt.defaultValue ) {
								vl = $.isFunction(opt.defaultValue) ? opt.defaultValue() : opt.defaultValue;
								if(fld[0].type=='checkbox') {
									/*vlc = vl.toLowerCase();
									if(vlc.search(/(false|0|no|off|undefined)/i)<0 && vlc!=="") {
										fld[0].checked = true;
										fld[0].defaultChecked = true;
										fld[0].value = vl;
									} else {
										fld[0].checked = false;
										fld[0].defaultChecked = false;
									}*/
								} else {fld.val(vl);}
							} else {
								if( fld[0].type=='checkbox' ) {
									/*fld[0].checked = false;
									fld[0].defaultChecked = false;
									vl = $(fld).attr("offval");*/
								} else if (fld[0].type && fld[0].type.substr(0,6)=='select') {
									//fld[0].selectedIndex = 0;
								} else {
									fld.val(vl);
								}
							}
							if(rp_ge[$t.p.id].checkOnSubmit===true || rp_ge[$t.p.id].checkOnUpdate) {rp_ge[$t.p.id]._savedData[nm] = vl;}
						}
					});
					$("#id_g","#"+fmid).val(rowid);
					return;
				}
				var tre = $(obj).jqGrid("getInd",rowid,true);
				if(!tre) {return;}
				$('td[role="gridcell"]',tre).each( function(i) {
					nm = cm[i].name;
					// hidden fields are included in the form
					if ( nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn' && cm[i].editable===true) {
						if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
							tmp = $(this).text();
						} else {
							try {
								tmp =  $.unformat.call(obj, $(this),{rowId:rowid, colModel:cm[i]},i);
							} catch (_) {
								tmp = cm[i].edittype=="textarea" ? $(this).text() : $(this).html();
							}
						}
						if($t.p.autoencode) {tmp = $.jgrid.htmlDecode(tmp);}
						if(rp_ge[$t.p.id].checkOnSubmit===true || rp_ge[$t.p.id].checkOnUpdate) {rp_ge[$t.p.id]._savedData[nm] = tmp;}
						nm = $.jgrid.jqID(nm);
						switch (cm[i].edittype) {
							case "password":
							case "text":
							case "button" :
							case "image":
                            case "comboboxlist":
                            case "comboboxtree":
                            case "datepicker":
							case "textarea":
								if(tmp == "&nbsp;" || tmp == "&#160;" || (tmp.length==1 && tmp.charCodeAt(0)==160) ) {tmp='';}
								$("#"+nm,"#"+fmid).val(tmp);
								break;
							/*case "select":
								var opv = tmp.split(",");
								opv = $.map(opv,function(n){return $.trim(n);});
								$("#"+nm+" option","#"+fmid).each(function(){
									if (!cm[i].editoptions.multiple && ($.trim(tmp) == $.trim($(this).text()) || opv[0] == $.trim($(this).text()) || opv[0] == $.trim($(this).val())) ){
										this.selected= true;
									} else if (cm[i].editoptions.multiple){
										if(  $.inArray($.trim($(this).text()), opv ) > -1 || $.inArray($.trim($(this).val()), opv ) > -1  ){
											this.selected = true;
										}else{
											this.selected = false;
										}
									} else {
										this.selected = false;
									}
								});
								break;
							case "checkbox":
								tmp = tmp+"";
								if(cm[i].editoptions && cm[i].editoptions.value) {
									var cb = cm[i].editoptions.value.split(":");
									if(cb[0] == tmp) {
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("checked",true);
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("defaultChecked",true); //ie
									} else {
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("checked", false);
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("defaultChecked", false); //ie
									}
								} else {
									tmp = tmp.toLowerCase();
									if(tmp.search(/(false|0|no|off|undefined)/i)<0 && tmp!=="") {
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("checked",true);
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("defaultChecked",true); //ie
									} else {
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("checked", false);
										$("#"+nm,"#"+fmid)[$t.p.useProp ? 'prop': 'attr']("defaultChecked", false); //ie
									}
								}
								break;*/
							case 'custom' :
								try {
									if(cm[i].editoptions && $.isFunction(cm[i].editoptions.custom_value)) {
										cm[i].editoptions.custom_value.call($t, $("#"+nm,"#"+fmid),'set',tmp);
									} else {throw "e1";}
								} catch (e) {
									if (e=="e1") {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);}
									else {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose);}
								}
								break;
						}
						cnt++;
					}
				});
				if(cnt>0) {$("#id_g",frmtb).val(rowid);}
			}
			function setNulls() {
				$.each($t.p.colModel, function(i,n){
					if(n.editoptions && n.editoptions.NullIfEmpty === true) {
						if(postdata.hasOwnProperty(n.name) && postdata[n.name] === "") {
							postdata[n.name] = 'null';
						}
					}
				});
			}
			function postIt() {
				var copydata, ret=[true,"",""], onCS = {}, opers = $t.p.prmNames, idname, oper, key, selr, i;

				var retvals = $($t).triggerHandler("jqGridAddEditBeforeCheckValues", [$("#"+frmgr), frmoper]);
				if(retvals && typeof(retvals) === 'object') {postdata = retvals;}

				if($.isFunction(rp_ge[$t.p.id].beforeCheckValues)) {
					retvals = rp_ge[$t.p.id].beforeCheckValues.call($t, postdata,$("#"+frmgr),postdata[$t.p.id+"_id"] == "_empty" ? opers.addoper : opers.editoper);
					if(retvals && typeof(retvals) === 'object') {postdata = retvals;}
				}
				for( key in postdata ){
					if(postdata.hasOwnProperty(key)) {
						ret = $.jgrid.checkValues(postdata[key],key,$t);
						if(ret[0] === false) {break;}
					}
				}
				setNulls();
				if(ret[0]) {
					onCS = $($t).triggerHandler("jqGridAddEditClickSubmit", [rp_ge[$t.p.id], postdata, frmoper]);
					if( onCS === undefined && $.isFunction( rp_ge[$t.p.id].onclickSubmit)) {
						onCS = rp_ge[$t.p.id].onclickSubmit.call($t, rp_ge[$t.p.id], postdata) || {};
					}
					ret = $($t).triggerHandler("jqGridAddEditBeforeSubmit", [postdata, $("#"+frmgr), frmoper]);
					if(ret === undefined) {
						ret = [true,"",""];
					}
					if( ret[0] && $.isFunction(rp_ge[$t.p.id].beforeSubmit))  {
						ret = rp_ge[$t.p.id].beforeSubmit(postdata,$("#"+frmgr));
					}
				}

				if(ret[0] && !rp_ge[$t.p.id].processing) {
					rp_ge[$t.p.id].processing = true;
					$("#sData", frmtb+"_2").addClass('ui-state-active');
					oper = opers.oper;
					idname = opers.id;
					// we add to pos data array the action - the name is oper
					postdata[oper] = ($.trim(postdata[$t.p.id+"_id"]) == "_empty") ? opers.addoper : opers.editoper;
					if(postdata[oper] != opers.addoper) {
						postdata[idname] = postdata[$t.p.id+"_id"];
					} else {
						// check to see if we have allredy this field in the form and if yes lieve it
						if( postdata[idname] === undefined ) {postdata[idname] = postdata[$t.p.id+"_id"];}
					}
					delete postdata[$t.p.id+"_id"];
					postdata = $.extend(postdata,rp_ge[$t.p.id].editData,onCS);
					if($t.p.treeGrid === true)  {
						if(postdata[oper] == opers.addoper) {
						selr = $($t).jqGrid("getGridParam", 'selrow');
							var tr_par_id = $t.p.treeGridModel == 'adjacency' ? $t.p.treeReader.parent_id_field : 'parent_id';
							postdata[tr_par_id] = selr;
						}
						for(i in $t.p.treeReader){
							if($t.p.treeReader.hasOwnProperty(i)) {
								var itm = $t.p.treeReader[i];
								if(postdata.hasOwnProperty(itm)) {
									if(postdata[oper] == opers.addoper && i === 'parent_id_field') {continue;}
									delete postdata[itm];
								}
							}
						}
					}

					postdata[idname] = $.jgrid.stripPref($t.p.idPrefix, postdata[idname]);
                    //把下拉框值传过去
                    //$.extend(postdata,extpost);
                    var ajaxUrl = rp_ge[$t.p.id].url ? rp_ge[$t.p.id].url : $($t).jqGrid('getGridParam','editurl') ;
					var ajaxOptions = $.extend({
						url: ajaxUrl,
						type: rp_ge[$t.p.id].mtype,
						data: $.isFunction(rp_ge[$t.p.id].serializeEditData) ? rp_ge[$t.p.id].serializeEditData(postdata) :  postdata,
						complete:function(data,Status){
							postdata[idname] = $t.p.idPrefix + postdata[idname];
							if(Status != "success") {
								ret[0] = false;
								ret[1] = $($t).triggerHandler("jqGridAddEditErrorTextFormat", [data, frmoper]);
								if ($.isFunction(rp_ge[$t.p.id].errorTextFormat)) {
									ret[1] = rp_ge[$t.p.id].errorTextFormat.call($t, data);
								} else {
									ret[1] = "\""+ajaxUrl+"\""+ I18N.error+"，"+ Status + " Status: '" + data.statusText + "'. Error code: " + data.status;;
								}
							} else {
								// data is posted successful
								// execute aftersubmit with the returned data from server
								ret = $($t).triggerHandler("jqGridAddEditAfterSubmit", [data, postdata, frmoper]);
								if(ret === undefined) {
									ret = [true,"",""];
								}
								if( ret[0] && $.isFunction(rp_ge[$t.p.id].afterSubmit) ) {
									ret = rp_ge[$t.p.id].afterSubmit.call($t, data,postdata);
								}
							}
							if(ret[0] === false) {
								$("#FormError>td",frmtb).html(ret[1]);
								$("#FormError",frmtb).show();
							} else {
								// remove some values if formattaer select or checkbox
								$.each($t.p.colModel, function(){
									if(extpost[this.name] && this.formatter && this.formatter=='select') {
										try {delete extpost[this.name];} catch (e) {}
									}
								});
								postdata = $.extend(postdata,extpost);
								if($t.p.autoencode) {
									$.each(postdata,function(n,v){
										postdata[n] = $.jgrid.htmlDecode(v);
									});
								}
								//rp_ge[$t.p.id].reloadAfterSubmit = rp_ge[$t.p.id].reloadAfterSubmit && $t.p.datatype != "local";
								// the action is add
								if(postdata[oper] == opers.addoper ) {
									//id processing
									// user not set the id ret[2]
									if(!ret[2]) {ret[2] = $.jgrid.randId();}
									postdata[idname] = ret[2];
									if(rp_ge[$t.p.id].closeAfterAdd) {
										if(rp_ge[$t.p.id].reloadAfterSubmit) {$($t).trigger("reloadGrid");}
										else {
											if($t.p.treeGrid === true){
												$($t).jqGrid("addChildNode",ret[2],selr,postdata );
											} else {
											$($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
											$($t).jqGrid("setSelection",ret[2]);
										}
										}
										$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal,onClose: rp_ge[$t.p.id].onClose});
									} else if (rp_ge[$t.p.id].clearAfterAdd) {
										if(rp_ge[$t.p.id].reloadAfterSubmit) {$($t).trigger("reloadGrid");}
										else {
											if($t.p.treeGrid === true){
												$($t).jqGrid("addChildNode",ret[2],selr,postdata );
											} else {
												$($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
											}
										}
										fillData("_empty",$t,frmgr);
									} else {
										if(rp_ge[$t.p.id].reloadAfterSubmit) {$($t).trigger("reloadGrid");}
										else {
											if($t.p.treeGrid === true){
												$($t).jqGrid("addChildNode",ret[2],selr,postdata );
											} else {
												$($t).jqGrid("addRowData",ret[2],postdata,p.addedrow);
									}
										}
									}
								} else {
									// the action is update
									if(rp_ge[$t.p.id].reloadAfterSubmit) {
										$($t).trigger("reloadGrid");
										if( !rp_ge[$t.p.id].closeAfterEdit ) {setTimeout(function(){$($t).jqGrid("setSelection",postdata[idname]);},1000);}
									} else {
										if($t.p.treeGrid === true) {
											$($t).jqGrid("setTreeRow", postdata[idname],postdata);
										} else {
											$($t).jqGrid("setRowData", postdata[idname],postdata);
										}
									}
									if(rp_ge[$t.p.id].closeAfterEdit) {$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal,onClose: rp_ge[$t.p.id].onClose});}
								}
								if($.isFunction(rp_ge[$t.p.id].afterComplete)) {
									copydata = data;
									setTimeout(function(){
										$($t).triggerHandler("jqGridAddEditAfterComplete", [copydata, postdata, $("#"+frmgr), frmoper]);
										rp_ge[$t.p.id].afterComplete.call($t, copydata, postdata, $("#"+frmgr));
										copydata=null;
									},500);
								}
							if(rp_ge[$t.p.id].checkOnSubmit || rp_ge[$t.p.id].checkOnUpdate) {
								$("#"+frmgr).data("disabled",false);
								if(rp_ge[$t.p.id]._savedData[$t.p.id+"_id"] !="_empty"){
									for(var key in rp_ge[$t.p.id]._savedData) {
										if(postdata[key]) {
											rp_ge[$t.p.id]._savedData[key] = postdata[key];
										}
									}
								}
							}
							}
							rp_ge[$t.p.id].processing=false;
							$("#sData", frmtb+"_2").removeClass('ui-state-active');
							try{$(':input:visible',"#"+frmgr)[0].focus();} catch (e){}
						}
					}, $.jgrid.ajaxOptions, rp_ge[$t.p.id].ajaxEditOptions );

					if (!ajaxOptions.url && !rp_ge[$t.p.id].useDataProxy) {
						if ($.isFunction($t.p.dataProxy)) {
							rp_ge[$t.p.id].useDataProxy = true;
						} else {
							ret[0]=false;ret[1] += " "+$.jgrid.errors.nourl;
						}
					}
					if (ret[0]) {
						if (rp_ge[$t.p.id].useDataProxy) {
							var dpret = $t.p.dataProxy.call($t, ajaxOptions, "set_"+$t.p.id);
							if(typeof(dpret) == "undefined") {
								dpret = [true, ""];
							}
							if(dpret[0] === false ) {
								ret[0] = false;
								ret[1] = dpret[1] || "Error deleting the selected row!" ;
							} else {
								if(ajaxOptions.data.oper == opers.addoper && rp_ge[$t.p.id].closeAfterAdd ) {
									$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});
								}
								if(ajaxOptions.data.oper == opers.editoper && rp_ge[$t.p.id].closeAfterEdit ) {
									$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});
								}
							}
						} else {
							$.ajax(ajaxOptions);
						}
					}
				}
				if(ret[0] === false) {
					$("#FormError>td",frmtb).html(ret[1]);
					$("#FormError",frmtb).show();
					// return;
				}
			}
			function compareData(nObj, oObj ) {
				var ret = false,key;
				for (key in nObj) {
					if(nObj[key] != oObj[key]) {
						ret = true;
						break;
					}
				}
				return ret;
			}
			function checkUpdates () {
				var stat = true;
				$("#FormError",frmtb).hide();
				if(rp_ge[$t.p.id].checkOnUpdate) {
					postdata = {};extpost={};
					getFormData();
					newData = $.extend({},postdata,extpost);
					diff = compareData(newData,rp_ge[$t.p.id]._savedData);
					if(diff) {
						$("#"+frmgr).data("disabled",true);
						$(".confirm","#"+IDs.themodal).show();
						stat = false;
					}
				}
				return stat;
			}
			function restoreInline()
			{
				if (rowid !== "_empty" && typeof($t.p.savedRow) !== "undefined" && $t.p.savedRow.length > 0 && $.isFunction($.fn.jqGrid.restoreRow)) {
					for (var i=0;i<$t.p.savedRow.length;i++) {
						if ($t.p.savedRow[i].id == rowid) {
							$($t).jqGrid('restoreRow',rowid);
							break;
						}
					}
				}
			}
			function updateNav(cr,totr){
				if (cr===0) {$("#pData",frmtb+"_2").addClass('ui-state-disabled');} else {$("#pData",frmtb+"_2").removeClass('ui-state-disabled');}
				if (cr==totr) {$("#nData",frmtb+"_2").addClass('ui-state-disabled');} else {$("#nData",frmtb+"_2").removeClass('ui-state-disabled');}
			}
			function getCurrPos() {
				var rowsInGrid = $($t).jqGrid("getDataIDs"),
				selrow = $("#id_g",frmtb).val(),
				pos = $.inArray(selrow,rowsInGrid);
				return [pos,rowsInGrid];
			}

			if ( $("#"+$.jgrid.jqID(IDs.themodal)).html() !== null ) {
				showFrm = $($t).triggerHandler("jqGridAddEditBeforeInitData", [$("#"+$.jgrid.jqID(frmgr))]);
				if(typeof(showFrm) == "undefined") {
					showFrm = true;
				}
				if(showFrm && onBeforeInit) {
					showFrm = onBeforeInit.call($t, $("#"+frmgr));
				}
				if(showFrm === false) {return;}
				restoreInline();
				$(".ui-jqdialog-title","#"+$.jgrid.jqID(IDs.modalhead)).html(p.caption);
				$("#FormError",frmtb).hide();
				if(rp_ge[$t.p.id].topinfo) {
					$(".topinfo",frmtb).html(rp_ge[$t.p.id].topinfo);
					$(".tinfo",frmtb).show();
				} else {
					$(".tinfo",frmtb).hide();
				}
				if(rp_ge[$t.p.id].bottominfo) {
					$(".bottominfo",frmtb+"_2").html(rp_ge[$t.p.id].bottominfo);
					$(".binfo",frmtb+"_2").show();
				} else {
					$(".binfo",frmtb+"_2").hide();
				}
				// filldata
				fillData(rowid,$t,frmgr);
				///
				if(rowid=="_empty" || !rp_ge[$t.p.id].viewPagerButtons) {
					$("#pData, #nData",frmtb+"_2").hide();
				} else {
					$("#pData, #nData",frmtb+"_2").show();
				}
				if(rp_ge[$t.p.id].processing===true) {
					rp_ge[$t.p.id].processing=false;
					$("#sData", frmtb+"_2").removeClass('ui-state-active');
				}
				if($("#"+frmgr).data("disabled")===true) {
					$(".confirm","#"+$.jgrid.jqID(IDs.themodal)).hide();
					$("#"+frmgr).data("disabled",false);
				}
				$($t).triggerHandler("jqGridAddEditBeforeShowForm", [$("#"+frmgr), frmoper]);
				if(onBeforeShow) { onBeforeShow.call($t, $("#"+frmgr)); }
				$("#"+$.jgrid.jqID(IDs.themodal)).data("onClose",rp_ge[$t.p.id].onClose);
				$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, jqM: false, overlay: p.overlay, modal:p.modal});
				if(!closeovrl) {
					$(".jqmOverlay").click(function(){
						if(!checkUpdates()) {return false;}
						$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});
						return false;
					});
				}
				$($t).triggerHandler("jqGridAddEditAfterShowForm", [$("#"+frmgr), frmoper]);
				if(onAfterShow) { onAfterShow.call($t, $("#"+frmgr)); }
			} else {
				var dh = isNaN(p.dataheight) ? p.dataheight : p.dataheight+"px",
				frm = $("<form name='FormPost' id='"+frmgr+"' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:"+dh+";'></form>").data("disabled",false),
				tbl = $("<table id='"+frmtborg+"' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>");
				showFrm = $($t).triggerHandler("jqGridAddEditBeforeInitData", [$("#"+frmgr), frmoper]);
				if(typeof(showFrm) == "undefined") {
					showFrm = true;
				}
				if(showFrm && onBeforeInit) {
					showFrm = onBeforeInit.call($t, $("#"+frmgr));
				}
				if(showFrm === false) {return;}
				restoreInline();
				$($t.p.colModel).each( function() {
					var fmto = this.formoptions;
					maxCols = Math.max(maxCols, fmto ? fmto.colpos || 0 : 0 );
					maxRows = Math.max(maxRows, fmto ? fmto.rowpos || 0 : 0 );
				});
				$(frm).append(tbl);
				var flr = $("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='"+(maxCols*2)+"'></td></tr>");
				flr[0].rp = 0;
				$(tbl).append(flr);
				//topinfo
				flr = $("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='"+(maxCols*2)+"'>"+rp_ge[$t.p.id].topinfo+"</td></tr>");
				flr[0].rp = 0;
				$(tbl).append(flr);
				// set the id.
				// use carefull only to change here colproperties.
				// create data
				var rtlb = $t.p.direction == "rtl" ? true :false,
				bp = rtlb ? "nData" : "pData",
				bn = rtlb ? "pData" : "nData";
				createData(rowid,$t,tbl,maxCols);
				// buttons at footer
				var bP = "<a href='javascript:void(0)' id='"+bp+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>",
				bN = "<a href='javascript:void(0)' id='"+bn+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",
				//bS  ="<a href='javascript:void(0)' id='sData' class='fm-button ui-state-default ui-corner-all'>"+p.bSubmit+"</a>",
				//bC  ="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+p.bCancel+"</a>";
				bS  ="<a href='javascript:void(0)' id='sData' class='ui-state-default ui-corner-all'></a>",
				bC  ="<a href='javascript:void(0)' id='cData' class='ui-state-default ui-corner-all'></a>";
				var bt = "<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='"+frmtborg+"_2'><tbody><tr id='Act_Buttons'><td class='navButton'>"+(rtlb ? bN+bP : bP+bN)+"</td><td class='EditButton'>"+bS+bC+"</td></tr>";
				bt += "<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>"+rp_ge[$t.p.id].bottominfo+"</td></tr>";
				bt += "</tbody></table>";
				if(maxRows >  0) {
					var sd=[];
					$.each($(tbl)[0].rows,function(i,r){
						sd[i] = r;
					});
					sd.sort(function(a,b){
						if(a.rp > b.rp) {return 1;}
						if(a.rp < b.rp) {return -1;}
						return 0;
					});
					$.each(sd, function(index, row) {
						$('tbody',tbl).append(row);
					});
				}
				p.gbox = "#gbox_"+$.jgrid.jqID(gID);
				var cle = false;
				if(p.closeOnEscape===true){
					p.closeOnEscape = false;
					cle = true;
				}
				var tms = $("<span></span>").append(frm).append(bt);
				$.jgrid.createModal(IDs,tms,p,"#gview_"+$.jgrid.jqID($t.p.id),$("#gbox_"+$.jgrid.jqID($t.p.id))[0]);
				if(rtlb) {
					$("#pData, #nData",frmtb+"_2").css("float","right");
					$(".EditButton",frmtb+"_2").css("text-align","left");
				}
				if(rp_ge[$t.p.id].topinfo) {$(".tinfo",frmtb).show();}
				if(rp_ge[$t.p.id].bottominfo) {$(".binfo",frmtb+"_2").show();}
				tms = null;bt=null;
				$("#"+$.jgrid.jqID(IDs.themodal)).keydown( function( e ) {
					var wkey = e.target;
					if ($("#"+frmgr).data("disabled")===true ) {return false;}//??
					if(rp_ge[$t.p.id].savekey[0] === true && e.which == rp_ge[$t.p.id].savekey[1]) { // save
						if(wkey.tagName != "TEXTAREA") {
							$("#sData", frmtb+"_2").trigger("click");
							return false;
						}
					}
					if(e.which === 27) {
						if(!checkUpdates()) {return false;}
						if(cle)	{$.jgrid.hideModal(this,{gb:p.gbox,jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});}
						return false;
					}
					if(rp_ge[$t.p.id].navkeys[0]===true) {
						if($("#id_g",frmtb).val() == "_empty") {return true;}
						if(e.which == rp_ge[$t.p.id].navkeys[1]){ //up
							$("#pData", frmtb+"_2").trigger("click");
							return false;
						}
						if(e.which == rp_ge[$t.p.id].navkeys[2]){ //down
							$("#nData", frmtb+"_2").trigger("click");
							return false;
						}
					}
				});
				if(p.checkOnUpdate) {
					$("a.ui-jqdialog-titlebar-close span","#"+$.jgrid.jqID(IDs.themodal)).removeClass("jqmClose");
					$("a.ui-jqdialog-titlebar-close","#"+$.jgrid.jqID(IDs.themodal)).unbind("click")
					.click(function(){
						if(!checkUpdates()) {return false;}
						$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal,onClose: rp_ge[$t.p.id].onClose});
						return false;
					});
				}
				p.saveicon = $.extend([true,"left","ui-icon-disk"],p.saveicon);
				p.closeicon = $.extend([true,"left","ui-icon-close"],p.closeicon);
				// beforeinitdata after creation of the form
				if(p.saveicon[0]===true) {
					/*$("#sData",frmtb+"_2").addClass(p.saveicon[1] == "right" ? 'fm-button-icon-right' : 'fm-button-icon-left')
					.append("<span class='ui-icon "+p.saveicon[2]+"'></span>");*/
					$("#sData",frmtb+"_2").button({icons: {primary: p.saveicon[2]},label:p.bSubmit});
				}
				if(p.closeicon[0]===true) {
					/*$("#cData",frmtb+"_2").addClass(p.closeicon[1] == "right" ? 'fm-button-icon-right' : 'fm-button-icon-left')
					.append("<span class='ui-icon "+p.closeicon[2]+"'></span>");*/
					$("#cData",frmtb+"_2").button({icons: {primary:p.closeicon[2]},label:p.bCancel});
				}
				if(rp_ge[$t.p.id].checkOnSubmit || rp_ge[$t.p.id].checkOnUpdate) {
					bS  ="<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bYes+"</a>";
					bN  ="<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bNo+"</a>";
					bC  ="<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+p.bExit+"</a>";
					var ii, zI = p.zIndex  || 999;zI ++;
					if ($.browser.msie && $.browser.version ==6) {
						ii = '<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>';
					} else {ii="";}
					$("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:"+zI+";display:none;'>&#160;"+ii+"</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:"+(zI+1)+"'>"+p.saveData+"<br/><br/>"+bS+bN+bC+"</div>").insertAfter("#"+frmgr);
					$("#sNew","#"+$.jgrid.jqID(IDs.themodal)).click(function(){
						postIt();
						$("#"+frmgr).data("disabled",false);
						$(".confirm","#"+$.jgrid.jqID(IDs.themodal)).hide();
						return false;
					});
					$("#nNew","#"+$.jgrid.jqID(IDs.themodal)).click(function(){
						$(".confirm","#"+$.jgrid.jqID(IDs.themodal)).hide();
						$("#"+frmgr).data("disabled",false);
						setTimeout(function(){$(":input","#"+frmgr)[0].focus();},0);
						return false;
					});
					$("#cNew","#"+$.jgrid.jqID(IDs.themodal)).click(function(){
						$(".confirm","#"+$.jgrid.jqID(IDs.themodal)).hide();
						$("#"+frmgr).data("disabled",false);
						$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal,onClose: rp_ge[$t.p.id].onClose});
						return false;
					});
				}
				// here initform - only once
				$($t).triggerHandler("jqGridAddEditInitializeForm", [$("#"+frmgr), frmoper]);
				if(onInitializeForm) {onInitializeForm($("#"+frmgr));}
				if(rowid=="_empty" || !rp_ge[$t.p.id].viewPagerButtons) {$("#pData,#nData",frmtb+"_2").hide();} else {$("#pData,#nData",frmtb+"_2").show();}
				$($t).triggerHandler("jqGridAddEditBeforeShowForm", [$("#"+frmgr), frmoper]);
				if(onBeforeShow) { onBeforeShow.call($t, $("#"+frmgr));}
				$("#"+$.jgrid.jqID(IDs.themodal)).data("onClose",rp_ge[$t.p.id].onClose);
				$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, overlay: p.overlay,modal:p.modal});
				if(!closeovrl) {
					$(".jqmOverlay").click(function(){
						if(!checkUpdates()) {return false;}
						$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});
						return false;
					});
				}
				$($t).triggerHandler("jqGridAddEditAfterShowForm", [$("#"+frmgr), frmoper]);
				if(onAfterShow) { onAfterShow.call($t, $("#"+frmgr)); }
				$(".fm-button","#"+$.jgrid.jqID(IDs.themodal)).hover(
					function(){$(this).addClass('ui-state-hover');},
					function(){$(this).removeClass('ui-state-hover');}
				);
				$("#sData", frmtb+"_2").click(function(){
					postdata = {};extpost={};
					$("#FormError",frmtb).hide();
					// all depend on ret array
					//ret[0] - succes
					//ret[1] - msg if not succes
					//ret[2] - the id  that will be set if reload after submit false
					getFormData();
					if(postdata[$t.p.id+"_id"] == "_empty")	{postIt();}
					else if(p.checkOnSubmit===true ) {
						newData = $.extend({},postdata,extpost);
						diff = compareData(newData,rp_ge[$t.p.id]._savedData);
						if(diff) {
							$("#"+frmgr).data("disabled",true);
							$(".confirm","#"+$.jgrid.jqID(IDs.themodal)).show();
						} else {
							postIt();
						}
					} else {
						postIt();
					}
					return false;
				});
				$("#cData", frmtb+"_2").click(function(){
					if(!checkUpdates()) {return false;}
					$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal,onClose: rp_ge[$t.p.id].onClose});
					return false;
				});
				$("#nData", frmtb+"_2").click(function(){
					if(!checkUpdates()) {return false;}
					$("#FormError",frmtb).hide();
					var npos = getCurrPos();
					npos[0] = parseInt(npos[0],10);
					if(npos[0] != -1 && npos[1][npos[0]+1]) {
						$($t).triggerHandler("jqGridAddEditClickPgButtons", ['next',$("#"+frmgr),npos[1][npos[0]]]);
						if($.isFunction(p.onclickPgButtons)) {
							p.onclickPgButtons.call($t, 'next',$("#"+frmgr),npos[1][npos[0]]);
						}
						fillData(npos[1][npos[0]+1],$t,frmgr);
						$($t).jqGrid("setSelection",npos[1][npos[0]+1]);
						$($t).triggerHandler("jqGridAddEditAfterClickPgButtons", ['next',$("#"+frmgr),npos[1][npos[0]]]);
						if($.isFunction(p.afterclickPgButtons)) {
							p.afterclickPgButtons.call($t, 'next',$("#"+frmgr),npos[1][npos[0]+1]);
						}
						updateNav(npos[0]+1,npos[1].length-1);
					}
					return false;
				});
				$("#pData", frmtb+"_2").click(function(){
					if(!checkUpdates()) {return false;}
					$("#FormError",frmtb).hide();
					var ppos = getCurrPos();
					if(ppos[0] != -1 && ppos[1][ppos[0]-1]) {
						$($t).triggerHandler("jqGridAddEditClickPgButtons", ['prev',$("#"+frmgr),ppos[1][ppos[0]]]);
						if($.isFunction(p.onclickPgButtons)) {
							p.onclickPgButtons.call($t, 'prev',$("#"+frmgr),ppos[1][ppos[0]]);
						}
						fillData(ppos[1][ppos[0]-1],$t,frmgr);
						$($t).jqGrid("setSelection",ppos[1][ppos[0]-1]);
						$($t).triggerHandler("jqGridAddEditAfterClickPgButtons", ['prev',$("#"+frmgr),ppos[1][ppos[0]]]);
						if($.isFunction(p.afterclickPgButtons)) {
							p.afterclickPgButtons.call($t, 'prev',$("#"+frmgr),ppos[1][ppos[0]-1]);
						}
						updateNav(ppos[0]-1,ppos[1].length-1);
					}
					return false;
				});
			}
			var posInit =getCurrPos();
			updateNav(posInit[0],posInit[1].length-1);

		});
	},
        //弹出框查看，修改样提示框
        viewGridRow : function(rowid, p){
		p = $.extend({
			top : 0,
			left: 0,
			width: 0,
			height: 'auto',
			dataheight: 'auto',
			modal: false,
			overlay: 30,
			drag: true,
			resize: true,
			jqModal: true,
			closeOnEscape : false,
			labelswidth: '30%',
			closeicon: [],
			navkeys: [false,38,40],
			onClose: null,
			beforeShowForm : null,
			beforeInitData : null,
			viewPagerButtons : true
		}, $.jgrid.view, p || {});
		return this.each(function(){
			var $t = this;
			if (!$t.grid || !rowid) {return;}
			var gID = $t.p.id,
			frmgr = "ViewGrid_"+$.jgrid.jqID( gID  ), frmtb = "ViewTbl_" + $.jgrid.jqID( gID ),
			frmgr_id = "ViewGrid_"+gID, frmtb_id = "ViewTbl_"+gID,
			IDs = {themodal:'viewmod'+gID,modalhead:'viewhd'+gID,modalcontent:'viewcnt'+gID, scrollelm : frmgr},
			onBeforeInit = $.isFunction(p.beforeInitData) ? p.beforeInitData : false,
			showFrm = true,
			maxCols = 1, maxRows=0;
			function focusaref(){ //Sfari 3 issues
				if(p.closeOnEscape===true || p.navkeys[0]===true) {
					setTimeout(function(){$(".ui-jqdialog-titlebar-close","#"+$.jgrid.jqID(IDs.modalhead)).focus();},0);
				}
			}
			function createData(rowid,obj,tb,maxcols){
				var nm, hc,trdata, cnt=0,tmp, dc, retpos=[], ind=false,
				tdtmpl = "<td class='CaptionTD form-view-label' width='"+p.labelswidth+"'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>", tmpl="",
				tdtmpl2 = "<td class='CaptionTD form-view-label'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>",
				fmtnum = ['integer','number','currency'],max1 =0, max2=0 ,maxw,setme, viewfld;
				for (var i =1;i<=maxcols;i++) {
					tmpl += i == 1 ? tdtmpl : tdtmpl2;
				}
				// find max number align rigth with property formatter
				$(obj.p.colModel).each( function() {
					if(this.editrules && this.editrules.edithidden === true) {
						hc = false;
					} else {
						hc = this.hidden === true ? true : false;
					}
					if(!hc && this.align==='right') {
						if(this.formatter && $.inArray(this.formatter,fmtnum) !== -1 ) {
							max1 = Math.max(max1,parseInt(this.width,10));
						} else {
							max2 = Math.max(max2,parseInt(this.width,10));
						}
					}
				});
				maxw  = max1 !==0 ? max1 : max2 !==0 ? max2 : 0;
				ind = $(obj).jqGrid("getInd",rowid);
				$(obj.p.colModel).each( function(i) {
					nm = this.name;
					setme = false;
					// hidden fields are included in the form
					if(this.editrules && this.editrules.edithidden === true) {
						hc = false;
					} else {
						hc = this.hidden === true ? true : false;
					}
					dc = hc ? "style='display:none'" : "";
					viewfld = (typeof this.viewable != 'boolean') ? true : this.viewable;
					if ( nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn' && viewfld) {
						if(ind === false) {
							tmp = "";
						} else {
							if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
								tmp = $("td:eq("+i+")",obj.rows[ind]).text();
							} else {
								tmp = $("td:eq("+i+")",obj.rows[ind]).html();
							}
						}
						setme = this.align === 'right' && maxw !==0 ? true : false;
						var opt = $.extend({}, this.editoptions || {} ,{id:nm,name:nm}),
						frmopt = $.extend({},{rowabove:false,rowcontent:''}, this.formoptions || {}),
						rp = parseInt(frmopt.rowpos,10) || cnt+1,
						cp = parseInt((parseInt(frmopt.colpos,10) || 1)*2,10);
						if(frmopt.rowabove) {
							var newdata = $("<tr><td class='contentinfo' colspan='"+(maxcols*2)+"'>"+frmopt.rowcontent+"</td></tr>");
							$(tb).append(newdata);
							newdata[0].rp = rp;
						}
						trdata = $(tb).find("tr[rowpos="+rp+"]");
						if ( trdata.length===0 ) {
							trdata = $("<tr "+dc+" rowpos='"+rp+"'></tr>").addClass("FormData").attr("id","trv_"+nm);
							$(trdata).append(tmpl);
							$(tb).append(trdata);
							trdata[0].rp = rp;
						}
						//$("td:eq("+(cp-2)+")",trdata[0]).html('<b>'+ (typeof frmopt.label === 'undefined' ? obj.p.colNames[i]: frmopt.label)+'</b>');
                        $("td:eq("+(cp-2)+")",trdata[0]).html((typeof frmopt.label === 'undefined' ? obj.p.colNames[i]: frmopt.label));
						$("td:eq("+(cp-1)+")",trdata[0]).append("<span>"+tmp+"</span>").attr("id","v_"+nm);
						if(setme){
							$("td:eq("+(cp-1)+") span",trdata[0]).css({'text-align':'right',width:maxw+"px"});
						}
						retpos[cnt] = i;
						cnt++;
					}
				});
				if( cnt > 0) {
					var idrow = $("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+ (maxcols*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='"+rowid+"'/></td></tr>");
					idrow[0].rp = cnt+99;
					$(tb).append(idrow);
				}
				return retpos;
			}
			function fillData(rowid,obj){
				var nm, hc,cnt=0,tmp, opt,trv;
				trv = $(obj).jqGrid("getInd",rowid,true);
				if(!trv) {return;}
				$('td',trv).each( function(i) {
					nm = obj.p.colModel[i].name;
					// hidden fields are included in the form
					if(obj.p.colModel[i].editrules && obj.p.colModel[i].editrules.edithidden === true) {
						hc = false;
					} else {
						hc = obj.p.colModel[i].hidden === true ? true : false;
					}
					if ( nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn') {
						if(nm == obj.p.ExpandColumn && obj.p.treeGrid === true) {
							tmp = $(this).text();
						} else {
							tmp = $(this).html();
						}
						opt = $.extend({},obj.p.colModel[i].editoptions || {});
						nm = $.jgrid.jqID("v_"+nm);
						$("#"+nm+" span","#"+frmtb).html(tmp);
						if (hc) {$("#"+nm,"#"+frmtb).parents("tr:first").hide();}
						cnt++;
					}
				});
				if(cnt>0) {$("#id_g","#"+frmtb).val(rowid);}
			}
			function updateNav(cr,totr){
				if (cr===0) {$("#pData","#"+frmtb+"_2").addClass('ui-state-disabled');} else {$("#pData","#"+frmtb+"_2").removeClass('ui-state-disabled');}
				if (cr==totr) {$("#nData","#"+frmtb+"_2").addClass('ui-state-disabled');} else {$("#nData","#"+frmtb+"_2").removeClass('ui-state-disabled');}
			}
			function getCurrPos() {
				var rowsInGrid = $($t).jqGrid("getDataIDs"),
				selrow = $("#id_g","#"+frmtb).val(),
				pos = $.inArray(selrow,rowsInGrid);
				return [pos,rowsInGrid];
			}

			if ( $("#"+$.jgrid.jqID(IDs.themodal)).html() !== null ) {
				if(onBeforeInit) {
					showFrm = onBeforeInit($("#"+frmgr));
					if(typeof(showFrm) == "undefined") {
						showFrm = true;
					}
				}
				if(showFrm === false) {return;}
				$(".ui-jqdialog-title","#"+$.jgrid.jqID(IDs.modalhead)).html(p.caption);
				$("#FormError","#"+frmtb).hide();
				fillData(rowid,$t);
				if($.isFunction(p.beforeShowForm)) {p.beforeShowForm($("#"+frmgr));}
				$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, jqM: false, overlay: p.overlay, modal:p.modal});
				focusaref();
				$("#cData","#"+frmtb_id+"_2").removeClass("ui-state-focus ui-state-hover");
			} else {
				var dh = isNaN(p.dataheight) ? p.dataheight : p.dataheight+"px";
				var frm = $("<form name='FormPost' id='"+frmgr_id+"' class='FormGrid' style='width:100%;overflow:auto;position:relative;height:"+dh+";'></form>"),
				tbl =$("<table id='"+frmtb_id+"' class='EditTable' cellspacing='1' cellpadding='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");
				if(onBeforeInit) {
					showFrm = onBeforeInit($("#"+frmgr));
					if(typeof(showFrm) == "undefined") {
						showFrm = true;
					}
				}
				if(showFrm === false) {return;}
				$($t.p.colModel).each( function() {
					var fmto = this.formoptions;
					maxCols = Math.max(maxCols, fmto ? fmto.colpos || 0 : 0 );
					maxRows = Math.max(maxRows, fmto ? fmto.rowpos || 0 : 0 );
				});
				// set the id.
				$(frm).append(tbl);
				createData(rowid, $t, tbl, maxCols);
				var rtlb = $t.p.direction == "rtl" ? true :false,
				bp = rtlb ? "nData" : "pData",
				bn = rtlb ? "pData" : "nData",

				// buttons at footer
				bP = "<a href='javascript:void(0)' id='"+bp+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>",
				bN = "<a href='javascript:void(0)' id='"+bn+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",
				//bC  ="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+p.bClose+"</a>";
				bC  ="<a href='javascript:void(0)' id='cData' class='ui-state-default ui-corner-all'></a>";
				if(maxRows >  0) {
					var sd=[];
					$.each($(tbl)[0].rows,function(i,r){
						sd[i] = r;
					});
					sd.sort(function(a,b){
						if(a.rp > b.rp) {return 1;}
						if(a.rp < b.rp) {return -1;}
						return 0;
					});
					$.each(sd, function(index, row) {
						$('tbody',tbl).append(row);
					});
				}
				p.gbox = "#gbox_"+$.jgrid.jqID(gID);
				var cle = false;
				if(p.closeOnEscape===true){
					p.closeOnEscape = false;
					cle = true;
				}
				var bt = $("<span></span>").append(frm).append("<table border='0' class='EditTable' id='"+frmtb+"_2'><tbody><tr id='Act_Buttons'><td class='navButton' width='"+p.labelswidth+"'>"+(rtlb ? bN+bP : bP+bN)+"</td><td class='EditButton'>"+bC+"</td></tr></tbody></table>");
				$.jgrid.createModal(IDs,bt,p,"#gview_"+$.jgrid.jqID($t.p.id),$("#gview_"+$.jgrid.jqID($t.p.id))[0]);
				if(rtlb) {
					$("#pData, #nData","#"+frmtb+"_2").css("float","right");
					$(".EditButton","#"+frmtb+"_2").css("text-align","left");
				}
				if(!p.viewPagerButtons) {$("#pData, #nData","#"+frmtb+"_2").hide();}
				bt = null;
				$("#"+IDs.themodal).keydown( function( e ) {
					if(e.which === 27) {
						if(cle)	{$.jgrid.hideModal(this,{gb:p.gbox,jqm:p.jqModal, onClose: p.onClose});}
						return false;
					}
					if(p.navkeys[0]===true) {
						if(e.which === p.navkeys[1]){ //up
							$("#pData", "#"+frmtb+"_2").trigger("click");
							return false;
						}
						if(e.which === p.navkeys[2]){ //down
							$("#nData", "#"+frmtb+"_2").trigger("click");
							return false;
						}
					}
				});
				p.closeicon = $.extend([true,"left","ui-icon-close"],p.closeicon);
				if(p.closeicon[0]===true) {
					/*$("#cData","#"+frmtb+"_2").addClass(p.closeicon[1] == "right" ? 'fm-button-icon-right' : 'fm-button-icon-left')
					.append("<span class='ui-icon "+p.closeicon[2]+"'></span>");修改按钮样式为button组件形式*/
					$("#cData","#"+frmtb+"_2").button({icons: {primary: p.closeicon[2]},label:p.bClose});
				}
				if($.isFunction(p.beforeShowForm)) {p.beforeShowForm($("#"+frmgr));}
				$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, modal:p.modal});
				$(".fm-button:not(.ui-state-disabled)","#"+frmtb+"_2").hover(
					function(){$(this).addClass('ui-state-hover');},
					function(){$(this).removeClass('ui-state-hover');}
				);
				focusaref();
				$("#cData", "#"+frmtb+"_2").click(function(){
					$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: p.onClose});
					return false;
				});
				$("#nData", "#"+frmtb+"_2").click(function(){
					$("#FormError","#"+frmtb).hide();
					var npos = getCurrPos();
					npos[0] = parseInt(npos[0],10);
					if(npos[0] != -1 && npos[1][npos[0]+1]) {
						if($.isFunction(p.onclickPgButtons)) {
							p.onclickPgButtons('next',$("#"+frmgr),npos[1][npos[0]]);
						}
						fillData(npos[1][npos[0]+1],$t);
						$($t).jqGrid("setSelection",npos[1][npos[0]+1]);
						if($.isFunction(p.afterclickPgButtons)) {
							p.afterclickPgButtons('next',$("#"+frmgr),npos[1][npos[0]+1]);
						}
						updateNav(npos[0]+1,npos[1].length-1);
					}
					focusaref();
					return false;
				});
				$("#pData", "#"+frmtb+"_2").click(function(){
					$("#FormError","#"+frmtb).hide();
					var ppos = getCurrPos();
					if(ppos[0] != -1 && ppos[1][ppos[0]-1]) {
						if($.isFunction(p.onclickPgButtons)) {
							p.onclickPgButtons('prev',$("#"+frmgr),ppos[1][ppos[0]]);
						}
						fillData(ppos[1][ppos[0]-1],$t);
						$($t).jqGrid("setSelection",ppos[1][ppos[0]-1]);
						if($.isFunction(p.afterclickPgButtons)) {
							p.afterclickPgButtons('prev',$("#"+frmgr),ppos[1][ppos[0]-1]);
						}
						updateNav(ppos[0]-1,ppos[1].length-1);
					}
					focusaref();
					return false;
				});
			}
			var posInit =getCurrPos();
			updateNav(posInit[0],posInit[1].length-1);
		});
	},
        //弹出框删除，修改提示框
        delGridRow : function(rowids,p) {
		p = $.extend({
			url : '',
			mtype : "post",
			reloadAfterSubmit: true,
			beforeShowForm: null,
			beforeInitData : null,
			afterShowForm: null,
			beforeSubmit: null,
			onclickSubmit: null,
			afterSubmit: null,
			jqModal : true,
			closeOnEscape : false,
			delData: {},
			delicon : [],
			cancelicon : [],
			onClose : null,
			ajaxDelOptions : {},
			processing : false,
			serializeDelData : null,
			useDataProxy : false
		}, $.jgrid.del, p ||{});
		rp_ge[$(this)[0].p.id] = p;
		return this.each(function(){
			var $t = this;
			if (!$t.grid ) {return;}
			if(!rowids) {return;}
			var onBeforeShow = $.isFunction( rp_ge[$t.p.id].beforeShowForm  ),
			onAfterShow = $.isFunction( rp_ge[$t.p.id].afterShowForm ),
			onBeforeInit = $.isFunction(rp_ge[$t.p.id].beforeInitData) ? rp_ge[$t.p.id].beforeInitData : false,
			gID = $t.p.id, onCS = {},
            dtbl = "DelTbl_"+$.jgrid.jqID(gID),postd, idname, opers, oper,
			dtbl_id = "DelTbl_" + gID,
			showFrm = true;
			if (jQuery.isArray(rowids)) {rowids = rowids.join();}

			var dh = isNaN(rp_ge[$t.p.id].dataheight) ? rp_ge[$t.p.id].dataheight : rp_ge[$t.p.id].dataheight+"px";


			if(onBeforeInit) {
				showFrm = onBeforeInit( $("#"+dtbl) );
				if(typeof(showFrm) == "undefined") {
					showFrm = true;
				}
			}
			if(showFrm === false) {return;}

			if(onBeforeShow) {rp_ge[$t.p.id].beforeShowForm($("#"+dtbl));}


            var okFuc = function(){
					var ret=[true,""];onCS = {};
					var postdata = rowids; //the pair is name=val1,val2,...
					if( $.isFunction( rp_ge[$t.p.id].onclickSubmit ) ) {onCS = rp_ge[$t.p.id].onclickSubmit(rp_ge[$t.p.id], postdata) || {};}
					if( $.isFunction( rp_ge[$t.p.id].beforeSubmit ) ) {ret = rp_ge[$t.p.id].beforeSubmit(postdata);}
					if(ret[0] && !rp_ge[$t.p.id].processing) {
						rp_ge[$t.p.id].processing = true;
						opers = $t.p.prmNames;
						postd = $.extend({},rp_ge[$t.p.id].delData, onCS);
						oper = opers.oper;
						postd[oper] = opers.deloper;
						idname = opers.id;
						postdata = String(postdata).split(",");
						if(!postdata.length) { return false; }
						for( var pk in postdata) {
							if(postdata.hasOwnProperty(pk)) {
								postdata[pk] = $.jgrid.stripPref($t.p.idPrefix, postdata[pk]);
							}
						}
						postd[idname] = postdata.join();
						$(this).addClass('ui-state-active');
                        var ajaxUrl =  rp_ge[$t.p.id].url ? rp_ge[$t.p.id].url : $($t).jqGrid('getGridParam','editurl');
						var ajaxOptions = $.extend({
							url: ajaxUrl,
							type: rp_ge[$t.p.id].mtype,
							data: $.isFunction(rp_ge[$t.p.id].serializeDelData) ? rp_ge[$t.p.id].serializeDelData(postd) : postd,
							complete:function(data,Status){
								if(Status != "success") {
									ret[0] = false;
									if ($.isFunction(rp_ge[$t.p.id].errorTextFormat)) {
										ret[1] = rp_ge[$t.p.id].errorTextFormat(data);
									} else {
                                        jAlert("\""+ajaxUrl+"\""+ I18N.error+"，"+Status + " Status: '" + data.statusText + "'. Error code: " + data.status, rp_ge[$t.p.id].caption);
										//ret[1] = Status + " Status: '" + data.statusText + "'. Error code: " + data.status;
									}
								} else {
									// data is posted successful
									// execute aftersubmit with the returned data from server
									if( $.isFunction( rp_ge[$t.p.id].afterSubmit ) ) {
										ret = rp_ge[$t.p.id].afterSubmit(data,postd);
									}
								}
								if(ret[0] === false) {
									$("#DelError>td","#"+dtbl).html(ret[1]);
									$("#DelError","#"+dtbl).show();
								} else {
									if(rp_ge[$t.p.id].reloadAfterSubmit && $t.p.datatype != "local") {
										$($t).trigger("reloadGrid");
									} else {
										if($t.p.treeGrid===true){
												try {$($t).jqGrid("delTreeNode",$t.p.idPrefix+postdata[0]);} catch(e){}
										} else {
											for(var i=0;i<postdata.length;i++) {
												$($t).jqGrid("delRowData",$t.p.idPrefix+ postdata[i]);
											}
										}
										$t.p.selrow = null;
										$t.p.selarrrow = [];
									}
									if($.isFunction(rp_ge[$t.p.id].afterComplete)) {
										setTimeout(function(){rp_ge[$t.p.id].afterComplete(data,postdata);},500);
									}
								}
								rp_ge[$t.p.id].processing=false;
								$("#dData", "#"+dtbl+"_2").removeClass('ui-state-active');
								if(ret[0]) {$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});}
							}
						}, $.jgrid.ajaxOptions, rp_ge[$t.p.id].ajaxDelOptions);


						if (!ajaxOptions.url && !rp_ge[$t.p.id].useDataProxy) {
							if ($.isFunction($t.p.dataProxy)) {
								rp_ge[$t.p.id].useDataProxy = true;
							} else {
								ret[0]=false;ret[1] += " "+$.jgrid.errors.nourl;
							}
						}
						if (ret[0]) {
							if (rp_ge[$t.p.id].useDataProxy) {
								var dpret = $t.p.dataProxy.call($t, ajaxOptions, "del_"+$t.p.id);
								if(typeof(dpret) == "undefined") {
									dpret = [true, ""];
								}
								if(dpret[0] === false ) {
									ret[0] = false;
									ret[1] = dpret[1] || "Error deleting the selected row!" ;
								} else {
									$.jgrid.hideModal("#"+$.jgrid.jqID(IDs.themodal),{gb:"#gbox_"+$.jgrid.jqID(gID),jqm:p.jqModal, onClose: rp_ge[$t.p.id].onClose});
								}
							}
							else {$.ajax(ajaxOptions);}
						}
					}

					if(ret[0] === false) {
						if($("#DelError>td","#"+dtbl).length > 1){
							$("#DelError>td","#"+dtbl).html(ret[1]);
							$("#DelError","#"+dtbl).show();
						}else{
							jAlert(ret[1], rp_ge[$t.p.id].caption);
						}
					}
					return false;
				}
            jConfirm(rp_ge[$t.p.id].msg,rp_ge[$t.p.id].caption,
                     function(r) {if(r){okFuc();}});
                // jAlert(rp_ge[$t.p.id].msg, rp_ge[$t.p.id].caption);
				//$.jgrid.viewModal("#"+$.jgrid.jqID(IDs.themodal),{gbox:"#gbox_"+$.jgrid.jqID(gID),jqm:rp_ge[$t.p.id].jqModal, overlay: rp_ge[$t.p.id].overlay, modal:rp_ge[$t.p.id].modal});
			if(onAfterShow) {rp_ge[$t.p.id].afterShowForm($("#"+dtbl));}

			if(rp_ge[$t.p.id].closeOnEscape===true) {
				setTimeout(function(){$(".ui-jqdialog-titlebar-close","#"+$.jgrid.jqID(IDs.modalhead)).focus();},0);
			}
		});
	},
	//修复锁列功能在ie下高度问题
    setFrozenColumns : function () {
		return this.each(function() {
			if ( !this.grid ) {return;}
			var $t = this, cm = $t.p.colModel,i=0, len = cm.length, maxfrozen = -1, frozen= false;
			// TODO treeGrid and grouping  Support
			if($t.p.subGrid === true || $t.p.treeGrid === true || $t.p.cellEdit === true || $t.p.sortable || $t.p.scroll || $t.p.grouping )
			{
				return;
			}
			if($t.p.rownumbers) { i++; }
			if($t.p.multiselect) { i++; }
	
			// get the max index of frozen col
			while(i<len)
			{
				// from left, no breaking frozen
				if(cm[i].frozen === true)
				{
					frozen = true;
					maxfrozen = i;
				} else {
					// break; 支持所锁列左边有隐藏列
				}
				i++;
			}
			if( maxfrozen>=0 && frozen) {
				var top = $t.p.caption ? $($t.grid.cDiv).outerHeight() : 0,
				hth = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).height();
				//headers
				if($t.p.toppager) {
					top = top + $($t.grid.topDiv).outerHeight();
				}
				if($t.p.toolbar[0] === true) {
					if($t.p.toolbar[1] != "bottom") {
						top = top + $($t.grid.uDiv).outerHeight();
					}
				}
				$t.grid.fhDiv = $('<div style="position:absolute;left:0px;top:'+top+'px;height:'+hth+'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>');
				$t.grid.fbDiv = $('<div style="position:absolute;left:0px;top:'+(parseInt(top,10)+parseInt(hth,10) + 1)+'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>');
				$("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fhDiv);
				var htbl = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).clone(true);
				// groupheader support - only if useColSpanstyle is false
				if($t.p.groupHeader) {
					$("tr.jqg-first-row-header, tr.jqg-third-row-header", htbl).each(function(){
						$("th:gt("+maxfrozen+")",this).remove();
					});
					var swapfroz = -1, fdel = -1;
					$("tr.jqg-second-row-header th", htbl).each(function(){
						var cs= parseInt($(this).attr("colspan"),10);
						if(cs) {
							swapfroz = swapfroz+cs;
							fdel++;
						}
						if(swapfroz === maxfrozen) {
							return false;
						}
					});
					if(swapfroz !== maxfrozen) {
						fdel = maxfrozen;
					}
					$("tr.jqg-second-row-header", htbl).each(function(){
						$("th:gt("+fdel+")",this).remove();
					});
				} else {
					$("tr",htbl).each(function(){
						$("th:gt("+maxfrozen+")",this).remove();
					});
				}
				$(htbl).width(1);
				// resizing stuff
				$($t.grid.fhDiv).append(htbl)
				.mousemove(function (e) {
					if($t.grid.resizing){ $t.grid.dragMove(e);return false; }
				});
				$($t).bind('jqGridResizeStop.setFrozenColumns', function (e, w, index) {
					var rhth = $(".ui-jqgrid-htable",$t.grid.fhDiv);
					$("th:eq("+index+")",rhth).width( w );
					var btd = $(".ui-jqgrid-btable",$t.grid.fbDiv);
					$("tr:first td:eq("+index+")",btd).width( w );
				});
				// sorting stuff
				$($t).bind('jqGridOnSortCol.setFrozenColumns', function (index, idxcol) {
	
					var previousSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+$t.p.lastsort+")",$t.grid.fhDiv), newSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+idxcol+")",$t.grid.fhDiv);
	
					$("span.ui-grid-ico-sort",previousSelectedTh).addClass('ui-state-disabled');
					$(previousSelectedTh).attr("aria-selected","false");
					$("span.ui-icon-"+$t.p.sortorder,newSelectedTh).removeClass('ui-state-disabled');
					$(newSelectedTh).attr("aria-selected","true");
					if(!$t.p.viewsortcols[0]) {
						if($t.p.lastsort != idxcol) {
							$("span.s-ico",previousSelectedTh).hide();
							$("span.s-ico",newSelectedTh).show();
						}
					}
				});
	
				// data stuff
				//TODO support for setRowData
				$("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fbDiv);
				jQuery($t.grid.bDiv).scroll(function () {
					jQuery($t.grid.fbDiv).scrollTop(jQuery(this).scrollTop());
				});
				if($t.p.hoverrows === true) {
					$("#"+$.jgrid.jqID($t.p.id)).unbind('mouseover').unbind('mouseout');
				}
				$($t).bind('jqGridAfterGridComplete.setFrozenColumns', function () {
					$("#"+$.jgrid.jqID($t.p.id)+"_frozen").remove();
					//jQuery($t.grid.fbDiv).height( jQuery($t.grid.bDiv).height()-16);
	                jQuery($t.grid.fbDiv).height( "auto");//锁定列高度设置为auto
					var btbl = $("#"+$.jgrid.jqID($t.p.id)).clone(true);
					$("tr",btbl).each(function(){
						$("td:gt("+maxfrozen+")",this).remove();
					});
	
					$(btbl).width(1).attr("id",$t.p.id+"_frozen");
					$($t.grid.fbDiv).append(btbl);
					if($t.p.hoverrows === true) {
						$("tr.jqgrow", btbl).hover(
							function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).addClass("ui-state-hover"); },
							function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).removeClass("ui-state-hover"); }
						);
						$("tr.jqgrow", "#"+$.jgrid.jqID($t.p.id)).hover(
							function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").addClass("ui-state-hover");},
							function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").removeClass("ui-state-hover"); }
						);
					}
					btbl=null;
				});
				$t.p.frozenColumns = true;
			}
		});
    }
    });
$.extend($.jgrid,{
        // 添加默认编辑类型datepicker
        createEl : function(eltype,options,vl,autowidth, ajaxso) {
            var elem = "";
            function bindEv (el, opt) {
                if($.isFunction(opt.dataInit)) {
                    opt.dataInit(el);
                }
                if(opt.dataEvents) {
                    $.each(opt.dataEvents, function() {
                        if (this.data !== undefined) {
                            $(el).bind(this.type, this.data, this.fn);
                        } else {
                            $(el).bind(this.type, this.fn);
                        }
                    });
                }
                return opt;
            }
            function setAttributes(elm, atr) {
                var exclude = ['dataInit','dataEvents','dataUrl', 'buildSelect','sopt', 'searchhidden', 'defaultValue', 'attr'];
                $.each(atr, function(key, value){
                    if($.inArray(key, exclude) === -1) {
                        $(elm).attr(key,value);
                    }
                });
                if(!atr.hasOwnProperty('id')) {
                    $(elm).attr('id', $.jgrid.randId());
            }
            }
            switch (eltype)
            {
                case "textarea" :
                    elem = document.createElement("textarea");
                    elem.className = "textarea";
                    if(autowidth) {
                        if(!options.cols) { $(elem).css({width:"98%"});}
                    } else if (!options.cols) { options.cols = 18; }
                    if(!options.rows) { options.rows = 2; }
                    if(vl=='&nbsp;' || vl=='&#160;' || (vl.length==1 && vl.charCodeAt(0)==160)) {vl="";}
                    elem.value = vl;
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    $(elem).attr({"role":"textbox","multiline":"true"});
                break;
                case "radio" : 
                    elem = document.createElement("span");
                    var setting = $.extend({},options);
                    options.data = null;
                    options.url = null;
                    setting.value =  vl;
                    setting.id = elem;
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    $(elem).attr("role","radio");
                    new biz.radio(setting);
                break;
                case "checkbox" : 
                    elem = document.createElement("span");
                    var setting = $.extend({},options);
                    options.data = null;
                    options.url = null;
                    setting.value =  vl;
                    setting.id = elem;
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    $(elem).attr("role","checkbox");
                    new biz.checkbox(setting);
                break;
                case "select" :
                    elem = document.createElement("select");
                    elem.setAttribute("role","select");
                    elem.className = "select";
                    var setting = $.extend({},options);
                    if(setting.parent){
                        setting.parent = isetting.parent.replace("{rowid}" , $(elem).attr("id").split("_")[0]);
                    }
                    options.data = null;
                    options.url = null;
                    setting.value =  vl;
                    setting.id = elem;
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    new biz.select(setting);
                break;
				case "number" :
                    elem = document.createElement("input");
                    elem.type = "text";
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
					var setting = $.extend({},options);
					elem.value =  vl;
                    setting.id = elem;
					new biz.number(setting);
                break;
                case "text" :
                case "password" :
                case "button" :
                    var role;
                    if(eltype=="button") { role = "button"; }
                    else { role = "textbox"; }
                    elem = document.createElement("input");
                    elem.type = eltype;
                    elem.value = vl;
                    if(eltype=="text"){
                        elem.className = "text";
                    }else if(eltype=="password"){
                        elem.className = "password";
                    }
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    if(eltype != "button"){
                        if(autowidth) {
                            if(!options.size) { $(elem).css({width:"98%"}); }
                        } else if (!options.size) { options.size = 20; }
                    }
                    $(elem).attr("role",role);
                break;
                case "image" :
                case "file" :
                    elem = document.createElement("input");
                    elem.type = eltype;
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
					if(eltype==="file"){
						var setting = $.extend({},options);
						new biz.file(setting);
					}
                    break;
                case "comboboxlist" :
                    elem = document.createElement("input");
                    elem.type = "text";
                    elem.value = vl;
                    elem.id = options.id;
                    //setAttributes(elem, options);
                    options = bindEv(elem,options);
                     var setting = $.extend(options,{selectedtext:vl});
                    $(elem).dropdownlist(setting);
                    break;
                case "comboboxtree" :
                    elem = document.createElement("input");
                    elem.type = "text";
                    elem.value = vl;
                    elem.id = options.id;
                    //setAttributes(elem, options);
                    options = bindEv(elem,options);
                    var setting = $.extend(options,{targetElem:elem,value:vl});
                    new biz.comboboxtree(setting);
                   // $(elem).empty().append(celm);
                    // $(elem).datepicker(options);
                    break;
                case "datepicker" :
                    elem = document.createElement("input");
                    elem.type = "text";
                    elem.className = "Wdate";
                    elem.value = vl;
                    if(autowidth) {
                        if(!options.size) { $(elem).css({width:"98%"}); }
                    } else if (!options.size) { options.size = 20; }
                    setAttributes(elem, options);
                    options = bindEv(elem,options);
                    $(elem).live('click',function(){WdatePicker(options)});
                    break;
                case "custom" :
                    elem = document.createElement("span");
                    try {
                        if($.isFunction(options.custom_element)) {
                            var celm = options.custom_element.call(this,vl,options);
                            if(celm) {
                                celm = $(celm).addClass("customelement").attr({id:options.id,name:options.name});
                                $(elem).empty().append(celm);
                            } else {
                                throw "e2";
                            }
                        } else {
                            throw "e1";
                        }
                    } catch (e) {
                        if (e=="e1") { this.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.nodefined, $.jgrid.edit.bClose);}
                        if (e=="e2") { this.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.novalue,$.jgrid.edit.bClose);}
                        else { this.info_dialog($.jgrid.errors.errcap,typeof(e)==="string"?e:e.message,$.jgrid.edit.bClose); }
                    }
                    break;
            }
            return elem;
        },
        createSubModal : function(aIDs, content, p, insertSelector, posSelector, appendsel, css) {
            var mw  = document.createElement('div'), rtlsup, self = this;
            css = $.extend({}, css || {});
            rtlsup = $(p.gbox).attr("dir") == "rtl" ? true : false;
            var mc = document.createElement('div');
            $(mc).addClass("ui-jqdialog-content ui-widget-content").attr("id",aIDs.modalcontent);
            $(mc).append(content);
            if (typeof appendsel == "string"){
                $(appendsel).append(mc);
            }else {$(mc).insertBefore(insertSelector);}
            $(mc).css(css);
        },
		// --by zhanghp 2012/08/07 start
		/**
		 * 覆盖jgrid的checkValues方法，每次更新jqGrid内核时需要检查覆写内容，是否修改了覆写的方法
		 * 
		 * @param {objcet} val 被验证的数据值
		 * @param {integer} valref 对应的column的index
		 * @param {objcet} g grid对象的dom对象
		 * @param {objcet} customobject 
		 * @param {objcet} nam 
		 * @param {bool} isEdit 内部参数，如果设置为true，则表示是编辑时触发的此方法，如果grid的editvalid设置为false就不进行校验
		 * @return {object[]}
		 *         验证通过，返回验证信息，如果验证通过返回[true,""，""]失败返回[false,"验证失败消息"]
		 * @author zhanghp 2012-08-07
		 * @override
		 */
		checkValues : function(val, valref, g, customobject, nam, isEdit) {
			//如果设置editvalid属性为false，则不进行编辑时验证
			if("undefined" === typeof(isEdit))
				isEdit = true;
			if(!g.p.editvalid&&isEdit)
				return [true, "", ""];
			var edtrul, i, nm, dft, len;
			if (typeof(customobject) === "undefined") {
				if (typeof(valref) == 'string') {
					for (i = 0, len = g.p.colModel.length; i < len; i++) {
						if (g.p.colModel[i].name == valref) {
							edtrul = g.p.colModel[i].editrules;
							valref = i;
							try {
								nm = g.p.colModel[i].formoptions.label;
							} catch (e) {
							}
							break;
						}
					}
				} else if (valref >= 0) {
					edtrul = g.p.colModel[valref].editrules;
				}
			} else {
				edtrul = customobject;
				nm = nam === undefined ? "_" : nam;
			}
			if (edtrul) {
				if (!nm) {
					nm = g.p.colNames[valref];
				}
				if (edtrul.required === true) {
					if ($.jgrid.isEmpty(val)) {
						return [false, nm + ": " + $.jgrid.edit.msg.required, ""];
					}
				}
				// force required
				var rqfield = edtrul.required === false ? false : true;
				if (edtrul.number === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						if (isNaN(val)) {
							return [false, nm + ": " + $.jgrid.edit.msg.number, ""];
						}
					}
				}
				if (typeof edtrul.minValue != 'undefined'
						&& !isNaN(edtrul.minValue)) {
					if (parseFloat(val) < parseFloat(edtrul.minValue)) {
						return [
								false,
								nm + ": " + $.jgrid.edit.msg.minValue + " "
										+ edtrul.minValue, ""];
					}
				}
				if (typeof edtrul.maxValue != 'undefined'
						&& !isNaN(edtrul.maxValue)) {
					if (parseFloat(val) > parseFloat(edtrul.maxValue)) {
						return [
								false,
								nm + ": " + $.jgrid.edit.msg.maxValue + " "
										+ edtrul.maxValue, ""];
					}
				}
				var filter;
				if (edtrul.email === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						// taken from $ Validate plugin
						filter = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
						if (!filter.test(val)) {
							return [false, nm + ": " + $.jgrid.edit.msg.email, ""];
						}
					}
				}
				if (edtrul.integer === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						if (isNaN(val)) {
							return [false, nm + ": " + $.jgrid.edit.msg.integer, ""];
						}
						if ((val % 1 !== 0) || (val.indexOf('.') != -1)) {
							return [false, nm + ": " + $.jgrid.edit.msg.integer, ""];
						}
					}
				}
				if (edtrul.date === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						if (g.p.colModel[valref].formatoptions
								&& g.p.colModel[valref].formatoptions.newformat) {
							dft = g.p.colModel[valref].formatoptions.newformat;
						} else {
							dft = g.p.colModel[valref].datefmt || "Y-m-d";
						}
						if (!$.jgrid.checkDate(dft, val)) {
							return [
									false,
									nm + ": " + $.jgrid.edit.msg.date + " - " + dft,
									""];
						}
					}
				}
				if (edtrul.time === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						if (!$.jgrid.checkTime(val)) {
							return [
									false,
									nm + ": " + $.jgrid.edit.msg.date
											+ " - hh:mm (am/pm)", ""];
						}
					}
				}
				if (edtrul.url === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						filter = /^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
						if (!filter.test(val)) {
							return [false, nm + ": " + $.jgrid.edit.msg.url, ""];
						}
					}
				}
				if (edtrul.custom === true) {
					if (!(rqfield === false && $.jgrid.isEmpty(val))) {
						if ($.isFunction(edtrul.custom_func)) {
							var ret = edtrul.custom_func.call(g, val, nm);
							if ($.isArray(ret)) {
								return ret;
							} else {
								return [false, $.jgrid.edit.msg.customarray, ""];
							}
						} else {
							return [false, $.jgrid.edit.msg.customfcheck, ""];
						}
					}
				}
				//添加校验，与validate一致start
				if (edtrul.string === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter = /^[\u0391-\uFFE5\w]+$/;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_specialSignal, ""];
                        }
                    }
                }
                if (edtrul.stringCH === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter = /[^u4E00-u9FA5]/g;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_chineseOnly, ""];
                        }
                    }
                }
                if (edtrul.stringEN === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter = /^[A-Za-z]+$/g;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_letterOnly, ""];
                        }
                    }
                }
                if (edtrul.ip === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
                        if (!(filter.test(val)&&(RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256))) {
                            return [false, nm + ": " + I18N.validator_IP, ""];
                        }
                    }
                }
                if (edtrul.port === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!(val < 65536 && val > 0)) {
                            return [false, nm + ": " + I18N.validator_Port, ""];
                        }
                    }
                }
                if (edtrul.postalcode === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter =/^[0-9]{6}$/;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_Postalcode, ""];
                        }
                    }
                }
                if (edtrul.mobile === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter =/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                        if (!(filter.test(val) && val.length == 11)) {
                            return [false, nm + ": " + I18N.validator_mobile, ""];
                        }
                    }
                }
                if (edtrul.alnum === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter =/^[a-zA-Z0-9]+$/;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_alnum, ""];
                        }
                    }
                }
                if (edtrul.naturalnum === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter =/^[0-9]+$/;
                        if (!(filter.test(val) && val>0)) {
                            return [false, nm + ": " + I18N.validator_naturalnum, ""];
                        }
                    }
                }
                if (edtrul.idcardno === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!isIdCardNo(val)) {
                            return [false, nm + ": " + I18N.validator_idcardno, ""];
                        }
                    }
                }
                if (edtrul.time === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        filter =/^(\d{2}):(\d{2}):(\d{2})\s*([ap]m)?$/;
                        if (!filter.test(val)) {
                            return [false, nm + ": " + I18N.validator_time, ""];
                        }
                    }
                }
                if (typeof edtrul.minlength != 'undefined' && !isNaN(edtrul.minlength)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val).length>=edtrul.minlength)) {
                            return [false, nm + ": " + I18N.validator_minlength_1 + edtrul.minlength + I18N.validator_minlength_2, ""];
                        }
                    }
                }
                if (typeof edtrul.maxlength != 'undefined' && !isNaN(edtrul.maxlength)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val).length<=edtrul.maxlength)) {
                            return [false, nm + ": " + I18N.validator_maxlength_1 + edtrul.maxlength + I18N.validator_maxlength_2, ""];
                        }
                    }
                }
                if (typeof edtrul.rangelength != 'undefined' && $.isArray(edtrul.rangelength)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val).length >= edtrul.rangelength[0] && $.trim(val).length <= edtrul.rangelength[1])) {
                            return [false, nm + ": " + I18N.validator_rangelength_1+ edtrul.rangelength[0] + I18N.validator_rangelength_2+edtrul.rangelength[1]+I18N.validator_rangelength_3, ""];
                        }
                    }
                }
                if (typeof edtrul.min != 'undefined' && !isNaN(edtrul.min)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val)>=edtrul.min)) {
                            return [false, nm + ": " + I18N.validator_min_1 + edtrul.min + I18N.validator_min_2, ""];
                        }
                    }
                }
                if (typeof edtrul.max != 'undefined' && !isNaN(edtrul.max)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val)<=edtrul.max)) {
                            return [false, nm + ": " + I18N.validator_max_1 + edtrul.max + I18N.validator_max_2, ""];
                        }
                    }
                }
                if (typeof edtrul.range != 'undefined' && $.isArray(edtrul.range)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($.trim(val) >= edtrul.range[0] && $.trim(val).length <= edtrul.range[1])) {
                            return [false, nm + ": " + I18N.validator_range_1+ edtrul.range[0] + I18N.validator_range_2+edtrul.range[1]+I18N.validator_range_3, ""];
                        }
                    }
                }
                if (edtrul.creditcard === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                            var nCheck = 0,
                                nDigit = 0,
                                bEven = false;
                            value = val.replace(/\D/g, "");

                            for (var n = val.length - 1; n >= 0; n--) {
                                var cDigit = val.charAt(n);
                                var nDigit = parseInt(cDigit, 10);
                                if (bEven) {
                                    if ((nDigit *= 2) > 9)
                                        nDigit -= 9;
                                }
                                nCheck += nDigit;
                                bEven = !bEven;
                            }

                        if (/[^0-9 -]+/.test(val) || !((nCheck % 10) == 0)) {
                            return [false, nm + ": " + I18N.validator_creditcard, ""];
                        }
                    }
                }
                if (typeof edtrul.accept != 'undefined' && !$.jgrid.isEmpty(edtrul.accept)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        var param = edtrul.accept;
                        param = typeof param == "string" ? param.replace(/,/g, '|') : "png|jpe?g|gif";
                        if (!val.match(new RegExp(".(" + param + ")$", "i"))) {
                            return [false, nm + ": " + I18N.validator_accept, ""];
                        }
                    }
                }
                if (typeof edtrul.equalTo != 'undefined' && !$.jgrid.isEmpty(edtrul.equalTo)) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!($(edtrul.equalTo).val() === val)) {
                            return [false, nm + ": " + I18N.validator_equalTo, ""];
                        }
                    }
                }
                if (edtrul.digits === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!( /^\d+$/.test(val))) {
                            return [false, nm + ": " + I18N.validator_digits, ""];
                        }
                    }
                }
				if (edtrul.number === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!( /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val))) {
                            return [false, nm + ": " + I18N.validator_number, ""];
                        }
                    }
                }
				if (edtrul.dateISO === true) {
                    if (!(rqfield === false && $.jgrid.isEmpty(val))) {
                        if (!( /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(val))) {
                            return [false, nm + ": " + I18N.validator_dateISO, ""];
                        }
                    }
                }
                //添加校验，与validate一致end
			}
			return [true, "", ""];
		},
		// --by zhanghp 2012/08/07 end
		info_dialog : function(caption, content,c_b, modalopt) {
			// showWarn(content,3000);
			new biz.alert({type:"alert",message:content,title:I18N.warn}) ;
			new biz.alert({type:"hide",times:3000}) ;
		}
    });
 //格式化时替换{columnName}为当前行列值
 $.fmatter.replacePara = function (inString,rowData,opts){
    for ( var columnName in rowData ){
       if( inString.indexOf(columnName)){
       		inString = inString.replace("{"+columnName+"}" , rowData[columnName]);
       }
    }
    return inString;
 };
 //格式化为密码
 $.fn.fmatter.password = function(cellval, opts) {
        var op =  $.extend({},opts.colModel.formatoptions);
		if(!$.fmatter.isEmpty(cellval)) {
            var tempval = cellval;
            tempval = tempval.replace(/./g,op.passwordStr?op.passwordStr:opts.passwordStr);  // 替换所有字符
			return "<span value=\"" + cellval + "\">" + tempval + "</span>";
		}else {
			return $.fn.fmatter.defaultFormat(cellval,opts );
		}
 };
 //创建img格式化,支持多个img，设置各自属性
 $.fn.fmatter.img = function(cellval, opts, rowData) {
		var op = {baseUrl: opts.baseUrl,showAction:opts.showAction,idName: opts.idName,imgsOptions:opts.imgsOptions||[],suffix:opts.suffix||""},
		target = "", idUrl,imgString="",$p=$('#'+opts.gid)[0].p;
		if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({},op,opts.colModel.formatoptions);
		}

        if(op.imgsOptions.length> 0){
            for(var i=0;i<op.imgsOptions.length;i++){
                 var opt = $.extend({},op,op.imgsOptions[i]) ;
                 //获取showAction对应列值
                 var cm = $.fmatter.replacePara(opt.showAction,rowData,opts);
                 var elem = document.createElement("img");
                 //按钮id，保证其唯一性
                 $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId+"_"+i);
                 $(elem).attr("src",opt.baseUrl+cm+opt.suffix);
                 for(var param in opt){
                     //如为事件则绑定到元素上
                     if($.isFunction(opt[param])){
                         $("#"+elem.id).die(param).live(param,function(e){
                                 opt[e.type].call(this,opts.rowId,cellval,opts);
                         })
                     }else if((typeof opt[param] !== "object") && param!=="baseUrl" && param!=="showAction"){
                         $(elem).attr(param,opt[param]);
                     }
                 }
                imgString += elem.outerHTML ;
            }
            return imgString;
        }else{
             //获取showAction对应列值
        	 var cm = $.fmatter.replacePara(op.showAction,rowData,opts);
             var elem = document.createElement("img");
             //按钮id，保证其唯一性
             $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId);
             $(elem).attr("src",op.baseUrl+cm+op.suffix);
             for(var param in op){
                 //如为事件则绑定到元素上
                 if($.isFunction(op[param])){
                     $("#"+elem.id).die(param).live(param,function(e){
                             op[e.type].call(this,opts.rowId,cellval,opts);
                     })
                 }else if((typeof op[param] !== "object") && param!=="baseUrl" && param!=="showAction"){
                     $(elem).attr(param,op[param]);
                 }
             }
             return   elem.outerHTML;
        }
	};
 //创建button格式化,支持多个button，设置各自属性
 $.fn.fmatter.button = function(cellval, opts) {
		var op = {idName: opts.idName,buttonsOptions:[]},
		target = "", idUrl,buttonString="";
		if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({},op,opts.colModel.formatoptions);
		}

        if(op.buttonsOptions.length> 0){
            for(var i=0;i<op.buttonsOptions.length;i++){
                 var opt = $.extend({},op,op.buttonsOptions[i]) ;
                 var elem = document.createElement("input");
                 $(elem).attr("type","button");
                 //按钮id，保证其唯一性
                 $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId+"_"+i);
                 if(opt.value == undefined){
                     $(elem).attr("value",cellval);
                 }
                 for(var param in opt){
                     //如为事件则绑定到元素上
                     if($.isFunction(opt[param])){
                        $("#"+elem.id).die(param).live(param,function(e){
                             opt[e.type].call(this,opts.rowId,cellval,opts);
                        })
                     }else{
                         $(elem).attr(param,opt[param]);
                     }
                 }
                buttonString += elem.outerHTML ;
            }
            return buttonString;
        }else{
             var elem = document.createElement("input");
             $(elem).attr("type","button");
             $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId);
             if(op.value == undefined){
                 $(elem).attr("value",cellval);
             }
             for(var param in op){
                 if($.isFunction(op[param])){
                    $("#"+elem.id).die(param).live(param,function(e){
                         op[e.type].call(this,opts.rowId,cellval,opts);
                    })
                 }else{
                     $(elem).attr(param,op[param]);
                 }
             }
             return   elem.outerHTML;
        }
	};
 //link类型格式化 修改支持多个link
 $.fn.fmatter.showlink = function(cellval, opts, rowData) {
		var op = {baseLinkUrl: opts.baseLinkUrl,showAction:opts.showAction, addParam: opts.addParam || "", target: opts.target, idName: opts.idName,linksOptions:opts.linksOptions||[]},
		target = "", idUrl,linkString="";
		if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({},op,opts.colModel.formatoptions);
		}
        if(op.linksOptions.length> 0){
            for(var i=0;i<op.linksOptions.length;i++){
                var opt = $.extend({},op,op.linksOptions[i]) ;
                var cm = $.fmatter.replacePara(opt.showAction,rowData,opts);
                var elem = document.createElement("a");
                $(elem).attr("class","showlink");
                $(elem).attr("href",opt.baseLinkUrl+ cm + '?'+ opt.idName+'='+opts.rowId+opt.addParam);
                //按钮id，保证其唯一性
                $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId+"_"+i);
                if(opt.opeName) {
                     $(elem).text(opt.opeName);
                }else if($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval)){
                     $(elem).text(cellval);
                }
                for(var param in opt){
                     //如为事件则绑定到元素上
                     if($.isFunction(opt[param])){
                        $("#"+elem.id).die(param).live(param,function(e){
                             opt[e.type].call(this,opts.rowId,cellval,opts);
                        })
                     }else if( opt[param]!="" && param!="baseLinkUrl" && param!="showAction" && param!="idName"  && param!="addParam"){
                         $(elem).attr(param,opt[param]);
                     }
                }
                linkString += ($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval) || opt.opeName )?elem.outerHTML:$.fn.fmatter.defaultFormat(cellval,opts) ;
            }
            return linkString;
        }else{
        	 var cm = $.fmatter.replacePara(op.showAction,rowData,opts);
             var elem = document.createElement("a");
             $(elem).attr("class","showlink");
             $(elem).attr("href",op.baseLinkUrl+ cm + '?'+ op.idName+'='+opts.rowId+op.addParam);
             //按钮id，保证其唯一性
             $(elem).attr("id",opts.gid+"_"+opts.colModel.name+"_"+opts.rowId);
             if(op.opeName) {
                  $(elem).text(op.opeName);
             }else if($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval)){
                  $(elem).text(cellval);
             }
             for(var param in op){
                  //如为事件则绑定到元素上
                 if($.isFunction(op[param])){
                     $("#"+elem.id).die(param).live(param,function(e){
                          op[e.type].call(this,opts.rowId,cellval,opts);
                     })
                  }else if( op[param]!="" && param!="baseLinkUrl" && param!="showAction" && param!="idName" && param!="addParam"){
                      $(elem).attr(param,op[param]);
                  }
             }
             return  ($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval) || op.opeName )?elem.outerHTML:$.fn.fmatter.defaultFormat(cellval,opts) ;
        }
	};
 //checkbox格式化增加筛选function filter
 $.fn.fmatter.checkbox =function(cval, opts) {
		var op = $.extend({},opts.checkbox), ds,$t= this;
		if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({},op,opts.colModel.formatoptions);
		}
        //数据筛选接口
        if($.isFunction(op.filter) && (op.filter(cval))) {
			return cval ;
	    }else{
            if(op.disabled===true) {ds = "disabled=\"disabled\"";} else {ds="";}
            if($.fmatter.isEmpty(cval) || $.fmatter.isUndefined(cval) ) {cval = $.fn.fmatter.defaultFormat(cval,op);}
            cval=cval+"";cval=cval.toLowerCase();
            var bchk = cval.search(/(false|0|no|off)/i)<0 ? " checked='checked' " : "";
            return "<input type=\"checkbox\" " + bchk  + " value=\""+ cval+"\" offval=\"no\" "+ds+ "/>";
        }
	};
 //checkbox反格式化增加筛选function filter
 $.unformat = function (cellval,options,pos,cnt) {
		// specific for jqGrid only
		var ret, formatType = options.colModel.formatter,
		op =options.colModel.formatoptions || {}, sep,
		re = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
		unformatFunc = options.colModel.unformat||($.fn.fmatter[formatType] && $.fn.fmatter[formatType].unformat);
		if(typeof unformatFunc !== 'undefined' && $.isFunction(unformatFunc) ) {
			ret = unformatFunc.call(this, $(cellval).text(), options, cellval);
		} else if(!$.fmatter.isUndefined(formatType) && $.fmatter.isString(formatType) ) {
			var opts = $.jgrid.formatter || {}, stripTag;
			switch(formatType) {
                case 'password':
                    ret = ($("span",cellval).length === 0) ? $(cellval).text():$("span",cellval).attr("value");
                    break;
				case 'integer' :
					op = $.extend({},opts.integer,op);
					sep = op.thousandsSeparator.replace(re,"\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text().replace(stripTag,'');
					break;
				case 'number' :
					op = $.extend({},opts.number,op);
					sep = op.thousandsSeparator.replace(re,"\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text().replace(stripTag,"").replace(op.decimalSeparator,'.');
					break;
				case 'currency':
					op = $.extend({},opts.currency,op);
					sep = op.thousandsSeparator.replace(re,"\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text();
					if (op.prefix && op.prefix.length) {
						ret = ret.substr(op.prefix.length);
					}
					if (op.suffix && op.suffix.length) {
						ret = ret.substr(0, ret.length - op.suffix.length);
					}
					ret = ret.replace(stripTag,'').replace(op.decimalSeparator,'.');
					break;
				case 'checkbox':
                     //数据筛选接口
                    if($.isFunction(options.colModel.formatoptions.filter) && (options.colModel.formatoptions.filter($(cellval).text()))) {
                        return $(cellval).text() ;
                    }else{
                        var cbv = (options.colModel.editoptions) ? options.colModel.editoptions.value.split(":") : ["Yes","No"];
                        ret = $('input',cellval).is(":checked") ? cbv[0] : cbv[1];
                    }
					break;
				case 'select' :
					ret = $.unformat.select(cellval,options,pos,cnt);
					break;
				case 'actions':
					return "";
				default:
					ret= $(cellval).text();
			}
		}
		return ret !== undefined ? ret : cnt===true ? $(cellval).text() : $.jgrid.htmlDecode($(cellval).html());
	};
biz.grid = biz.createUI(function(options){
    if(typeof options == "object"){
         var me = this;
         me.target = options.id;
         me.uiType = 'grid'  ;
         options.target = options.id;
         //把扩展方法加到grid组件上
         $.extend($.fn.jqGrid,this.methods||{});
         //$.extend(true,$.jgrid.defaults,this.defaults);
        // $.extend(this.defaults || {} ,options );
         //部分内部方法需要扩展到 $.jgrid 上面
         $.extend($.jgrid,this._methods||{});
    }else if(typeof options == 'string'){
         //调用jqgrid方法，在grid渲染之前调用方法必须用此调用方式
         //使用方式：biz.grid("#gridid","方法名",参数1,参数2……);

           var tempValue,args = $.makeArray(arguments).slice(1);
           $(arguments[0]).each(function(){
                    tempValue =  $(this).jqGrid.apply($(this),args);
           })
           return tempValue;
    }
}).extend({
    init:function(opts){
	       //opts =  $.extend({}, this.defaults || {} ,opts ) ;
	       //如果导航栏类型为自定义类型，则做相应处理
	       if(opts.navtype == "bottom" || opts.navtype == "top" || opts.navtype == "both" || opts.navtype == "none"){
	           if(opts.navtype !== "none")opts.navbar = opts.pager;
	           if(opts.navtype == "bottom" ||opts.navtype == "none"){
	                 opts.toppager =  false;
	           }else{
	                 opts.toppager =  true;
	           }
	       }
	       //如果编辑方式为自定义类型，做相应默认设置
	       if(opts.editway == "cell"){
	             opts.navopt = $.extend({},{edit:false,view:false,/*导航栏默认属性*/
	      	        addfunc:function(rowid){
	      	        	$(opts.target).jqGrid("saveEditCell");/*保存处于编辑状态的单元格*/
	      	        	$(opts.target).jqGrid("addLocalRow");} ,/*新增按钮处理*/
	      	        delfunc:function(rowid){$(opts.target).jqGrid("delLocalRow",rowid)}},opts.navopt); /*删除按钮处理*/
	      	     opts.cellEdit = true	/*单元格是否可编辑*/;
	       }else if(opts.editway == "row"){
	             opts.navopt = $.extend({},{view:false,edit:false,add:false,del:true},opts.navopt);  //导航栏默认属性
	       }
	       //分页栏类型为simple时分页栏相关属性值设置
	       if(opts.pagerType == "simple"){
               opts.pginput = false;
               opts.pgtext = "";
               opts.rowList = [];
           }
	       var returnObj = $(opts.target).jqGrid(opts) ;
	       //把grid方法直接绑定到返回对象上
	       $.extend(returnObj , returnObj.jqGrid) ;
	       //自适应大小
	       returnObj.setGridWidth(opts.width);
	       // returnObj.setGridHeight(opts.height);
	       if((opts.width == "auto" || opts.width == "100%") && opts.shrinkToFit!==false ){
               $(window).bind("resize.gridwidth",function(){
                    returnObj.setGridWidth("auto");
               });
           }
	
	       if(opts.navbar!==""){
	          //导航栏初始化
	    	  returnObj.navGrid(opts.navbar,opts.navopt,opts.pEdit,opts.pAdd,opts.pDel,opts.pSearch,opts.pView);
	       }
	       //导航、分页栏位置调整
	       returnObj.navGridposition(opts);
	       //如编辑方式为行编辑，则对导航栏进行行编辑渲染
	       if(opts.editway == "row"){
	    	   var inlineTar = opts.navtype == "bottom"?opts.pager:(opts.navtype == "top"?opts.id+"_toppager":(opts.navtype == "none"?"":opts.pager+" , "+opts.id+"_toppager"))
	           if(inlineTar!=="")$(inlineTar).each(function(){returnObj.inlineNav(this,opts.rownavopt||{})});
	       }
	       //修复集成到portal里ie下分页栏布局错位问题
	       $(".ui-pager-control > .ui-pg-table").attr("style","width: 100%;  height: 100%;");
	       return returnObj;
    },
    defaults:{
         rowList: [10,15,50,100],
         rowNum:10,
         height:"100%",
         width:"auto",
         multiselect: true,
         datatype: "json",
         mtype: "post",
         viewrecords: true,
         cellEdit: false,
	     cellsubmit: "clientArray",
         toppager: true,
         sortorder: "asc",
		 sortname: "",
         pagerpos: 'center',
		 recordpos: 'right',
         prmNames:{page:"pageNo",rows:"pageSize",sort:"orderFields",order:"order"},
         jsonReader:{repeatitems:false},
         hoverrows:true,//是否使用悬停行样式
         altRows:true, //是否使用斑马样式
         altclass:"even",//定义斑马样式class
         pagerwidth:"",//分页内容宽度
         recordwidth:"150",//数据信息内容宽度
         navtype:"none",//导航栏类型top、both、bottom、none
         navbar:"",//定义导航栏位置
         navopt:{},  //导航栏默认属性
         pEdit:{},  //导航栏编辑数据属性，同navGrid的pEdit属性
         pAdd:{},  //导航栏增加数据属性，同navGrid的pAdd
         pDel:{},  //导航栏删除数据属性，同navGrid的pDel
         pSearch:{},  //导航栏查找数据属性，同navGrid的pSearch
         pView:{},  //导航栏查看数据属性，同navGrid的pView
         _delrowid:[],//保存删除行id
		 editvalid : false,// 是否在编辑时校验
         addParams : {serial:0,rowID : "new_row"}, //新增行属性
         groupingView : { mergeCell:true},
         totalGroupHeader:[],//多表头信息
         pagerType:"full",//分页栏类型，包括full和simple
         autoencode:true,//对特殊字符自动编码，例如单元格内容包含"<" （转换为"&lt"后显示）
         loadError: function(xhr, error, thrown){
        	 biz.utils.loadError(xhr,error, thrown);
         }
    }
  }) ;
 /**
 * 扩展默认属性、方法

biz.grid.extend({
    draggable: true,
    defaults:{
         rowList: [50,200],
         rowNum:20,
         height:200
        // width:500
     }
});  */
 /**
 * 注册一个构造方法
 *
 *biz.grid.register(function(opt){
 *   var c = opt ;
 *   alert("register函数");
 *});
 *
 */

 /**
 * 创建一个tree控件
 * @function
 * @return {Object} ui控件
 */
 biz.tree = biz.createUI(function(options){
      var zt = $.fn.zTree,
         tools = zt._z.tools,
         consts = zt.consts,
         view = zt._z.view,
         data = zt._z.data,
         event = zt._z.event;
     if(typeof options == "object"){
         var me = this;
         me.target = options.id;
         me.uiType = 'tree'  ;
         options.target = options.id;
         var _methods = this._methods||{};
         //扩展内部方法名添加到tree上
         data.addZTreeTools( _methods);
         //把扩展默认属性添加到tree上
        // data.exSetting(this.defaults || {});
        // $.extend(true,this.defaults || {} ,options) ;
     }else if(typeof options == 'string'){
         var treeId = arguments[0],methodName = arguments[1],
              args = $.makeArray(arguments).slice(2),temObj = $.fn.zTree.getZTreeObj(treeId),
              tempValue = (typeof temObj[methodName] =="function") ? temObj[methodName].apply($(treeId),args):jAlert(I18N.param_error + options , I18N.promp);;
         return tempValue;
     }
 }).extend({
    init:function(opts){
        var returnObj = $.fn.zTree.init($(opts.target), opts, opts.nodes);
        return returnObj;
    },
    defaults:{
    	callback:{
            onAsyncError:function(event,treeId,treeNode,XMLHttpRequest,textStatus,errorThrown){
                  biz.utils.loadError(XMLHttpRequest,textStatus,errorThrown);
            }
        }
    },
    //提供给用户扩展用，只能通过返回对象调用方法 返回对象.方法名（参数1，参数2……）
    methods:{
        /*
        getabc:function(){
            alert("dd");
        }  */
    },
     //内部扩展用，可以通过biz.tree("id","方法名")方式调用
    _methods:function(setting, zTreeTools) {
        //获取指定id 的tree对象
        zTreeTools.getTreeObj =  function(){
           return  $.fn.zTree.getZTreeObj(this[0].id);
        }
    }
 });


 /**
 * 创建一个comboboxlist控件,下拉多列数据选择列表
 * @function
 * @return {Object} ui控件
 */
 biz.comboboxlist = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'comboboxlist' ;
	    options.target = options.id;
    }else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
        var targetElem = opts.targetElem ? opts.targetElem:(opts.id);
        var returnObj = $(targetElem).dropdownlist(opts);
        return returnObj;
    },
    defaults:{
        checkbox:true,
        ajaxOptions:{
       	  type: "get",
       	  dataType:"json",
          data: "",
          error:function(xhr,st,err){
            	   biz.utils.loadError(xhr,st,err);
          }
       }
    },
    methods:{
        getParam : function(pName) {
            var $t = this[0];
            if (!$t || !$t.settings) {return;}
            if (!pName) { return $t.settings; }
            else {return typeof($t.settings[pName]) != "undefined" ? $t.settings[pName] : null;}
        },
        setParam : function (newParams){
            return this.each(function(){
                if (this.settings && typeof(newParams) === 'object') {$.extend(true,this.settings,newParams);}
            });
        }
    }
 });

 /**
 * 创建一个comboboxtree控件,下拉树选择列表
 * @function
 * @return {Object} ui控件
 */
 biz.comboboxtree = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'comboboxtree'  ;
    }else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
    	var treeObj,setting,targetElem = opts.targetElem ? opts.targetElem:(opts.id),id = $(targetElem).attr("id"),
            nodeid = opts.valueName ? opts.valueName : (opts.data.simpleData.enable ? (opts.data.simpleData.idKey ? opts.data.simpleData.idKey :"id"):opts.data.key.name),
            nodename = opts.data.key.name ? opts.data.key.name : "name" ;
            opts.id = "#"+id;
        function _createTree(){
            var contentElem = document.createElement("div"),
                treeElem = document.createElement("ul");
            $(contentElem).attr("id",id+"_menuContent");
            $(contentElem).addClass("menuContent");
            $(contentElem).attr("style","display:none; position: absolute;z-index:" + opts.zindex + ";");
            $(treeElem).attr("id",id+"_treeContent");
            $(treeElem).addClass("ztree");
            $(treeElem).css({width:opts.width,height:opts.height,"margin-top":0,"margin-left":0 });
            contentElem.appendChild(treeElem);
            $("body").append(contentElem);
        }
        function _showMenu() {
			var cityObj = $(opts.id),
                cityOffset = $(spanElem).offset();
			$(opts.id+"_menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", _onBodyDown);
			$spanElem.bind("keydown", _ontargetDown);
		}
        function _hideMenu() {
			$(opts.id+"_menuContent").fadeOut("fast");
			$("body").unbind("mousedown", _onBodyDown);
			$spanElem.unbind("keydown", _ontargetDown);
		}
        function _onBodyDown(event) {
			if (!(event.target.id == id || event.target.id == id+"_menuContent" || $(event.target).parents(opts.id+"_menuContent").length>0)) {
				_hideMenu();
			}
		}
        function _ontargetDown(event) {
			if ((event.target.id == id || event.target.id == id+"_menuContent" || $(event.target).parents(opts.id+"_menuContent").length>0)) {
				_hideMenu();
			}
		}
        function _beforeClick(treeId, treeNode) {
            if(opts.check !== undefined && opts.check.enable !== undefined && opts.check.enable){
                treeObj.checkNode(treeNode, !treeNode.checked, null, true);
                return false;
            }else{
                var check = (treeNode && !treeNode.isParent);
                //if (!check) alert("只能选择城市...");
                return check;
            }
		}
        function _onCheck(e, treeId, treeNode) {
			var nodes = treeObj.getCheckedNodes(true),v = "",n = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i][nodeid] + ",";
				n += nodes[i][nodename] + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (n.length > 0 ) n = n.substring(0, n.length-1);
			$(targetElem).attr("value", v);
            $spanElem.html(n);
		}
        function _onClick(e, treeId, treeNode) {
			var nodes = treeObj.getSelectedNodes(),v = "",n = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i][nodeid] + ",";
				n += nodes[i][nodename] + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (n.length > 0 ) n = n.substring(0, n.length-1);
			$(targetElem).attr("value", v);
            $spanElem.html(n);
		}
        function _setval(){
            var idKeys = (opts.selected==undefined)?[]:opts.selected.split(","),name = "";
            $(targetElem).attr("value",opts.selected);
            for(var i=0;i<idKeys.length;i++){
                var node = treeObj.getNodesByParam(nodeid, idKeys[i], null);
                $.each( node, function(j, n){
                    name = name + node[j][nodename] + (i==(idKeys.length-1)?"":",");
                    if(opts.check.enable){
                        treeObj.checkNode(node[j], true, true);
                    }else{
                        treeObj.selectNode(node[j],true);
                    }
                });
            }
            if(name == ""){
                $spanElem.html(I18N.dropdownlist_defaultValue);
            }else{
                $spanElem.html(name);
            }
        }
        if( opts.callback == undefined || opts.callback.beforeClick == undefined){
           opts.callback = $.extend({beforeClick:_beforeClick},opts.callback||{});
        }
        if((opts.callback == undefined || opts.callback.onCheck == undefined)
            && opts.check !== undefined && opts.check.enable !== undefined && opts.check.enable){
           opts.callback = $.extend({onCheck:_onCheck},opts.callback||{});
        }
        if(opts.callback == undefined || opts.callback.onClick == undefined){
           opts.callback = $.extend({onClick:_onClick},opts.callback||{});
        }
        
        //增加span标签
        var spanElem=document.createElement("span"),$spanElem = $(spanElem);
        $spanElem.addClass("droplist csiui-file-wrapper ui-widget");
        $spanElem.attr("id",$(targetElem).attr("id")+"_span");
        $spanElem.width(opts.width);
        $spanElem.live("click",function(){_showMenu();});
        $(targetElem).hide();
        $(targetElem).after($spanElem);

        //创建tree元素 ,不重复创建
        if(!$(opts.id+"_menuContent").length > 0){
            _createTree();
        }

        setting = $.extend({},opts,{id:opts.id+"_treeContent"});
        //初始化树
        treeObj = new biz.tree(setting);
        _setval();//设置默认值，根据默认值勾选tree节点
        return treeObj;
    },
    defaults:{
    	width:200,
        zindex:2000,
        data:{simpleData:{enable:false,idKey:"id"},key:{name:"name"},check:{enable: true}},
        valueName:"",//value值字段name，即树节点对象中作为value的字段名
        selected:""//默认值选中值value，通过逗号分隔多个值，例如"shangdong,guangxi"
        
    }
 });

 /**
 * 创建一个alert控件,提示框
 * @function
 * @return {Object} ui控件
 */
 biz.alert = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'alert'  ;
	    //$.extend($.alerts ,options ) ;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
        //var targetElem = opts.targetElem ? opts.targetElem:("#"+opts.id);
        var alertObj =  $.alerts ;
        switch(opts.type){
            case "alert":
                alertObj.alert(opts.message, opts.title?opts.title:I18N.warn , opts.callback);
                break;
            case "confirm":
                alertObj.confirm(opts.message, opts.title?opts.title:I18N.promp , opts.callback);
                break ;
            case "prompt":
                alertObj.prompt(opts.message, opts.value, opts.title?opts.title:I18N.promp , opts.callback);
                break;
            case "hide":
                jHide(opts.times);
                break;
        }
        return $(opts.id);
    },
    defaults:{
        type:"alert"
    }
 });

  /**
 * 创建一个datepicker控件,日期选择
 * @function
 * @return {Object} ui控件
 */
 biz.datepicker = biz.createUI(function(options){
	if(typeof options == "object"){	 
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'datepicker'  ;
	    options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }	 
 }).extend({
    init:function(opts){
        $(opts.target).live(opts.event,function(){WdatePicker(opts)});
        return this;
    },
    defaults:{
        event:"focus" //focus or click
    },
    methods:{
        show:function(){
            $dp.show();
        },
        hide:function(){
            $dp.hide();
        },
        diffFormat:function(target,opt,isId){
            if(isId || typeof isId == undefined ){
                 $dp.$D(target,opt);
            }else{
                 $dp.$DV(target,opt);
            }
        }
    }
 });

 /**
 * 创建一个dialog控件,弹出框
 * @function
 * @return {Object} ui控件
 */
 biz.dialog = biz.createUI(function(options){
	if(typeof options == "object"){	
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'dialog' ;
	    options.target = options.id;
	    //把扩展方法加到dialog组件上
	    $.extend($.ui.dialog.prototype,this._methods||{});
	    options = $.extend(true,{},this.defaults,options) ;
	    if(options.titleIco && options.title){
	        options.title = "<a href= '#'> </a> <span>"+options.title+"</span>";
	    }
	    return options;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
         if(opts.isDescription){
             $(opts.target).prepend("<table class='descriptiontable'  border=0 cellSpacing=0 cellPadding=0 width='100%'><tr><td height=50  width=50 align='center' >"
				                     +"<a href= '#' onfocus='this.blur();' > </a></td><td  style='LINE-HEIGHT: 16px; align:left'><div style='FONT-WEIGHT: bold;'>"+opts.desTitle
                                     +"</div><div>" +opts.desContent+"</div></td></tr></table>");
         }
         $(opts.target).dialog(opts);
         if(opts.times)setTimeout(function(){ $(opts.target).dialog("close")},opts.times);
         return this;
    },
    defaults:{
        isDescription :false,
        desTitle:"",
        desContent:"",
        titleIco:true,
        bgiframe:true,
        autoOpen:false
       // times:3000
    },
    methods:{
        destroy:function(){
            $(this.target).dialog("destroy");
        },
        close:function(){
            $(this.target).dialog("close");
        },
        disable:function(){
            $(this.target).dialog("disable");
        },
        enable:function(){
            $(this.target).dialog("enable");
        },
        /*
         *opts为属性对象时为给多个属性同时设置值；{属性1:"属性值1"，属性2:"属性值2"}
         *opts为字符串时为给单个属性取值或设置值
         */
        option:function(opts ,value){
            $(this.target).dialog("option",opts,value);
        },
        widget:function(){
            $(this.target).dialog("widget");
        },
        isOpen:function(){
            $(this.target).dialog("isOpen");
        },
        moveToTop:function(){
            $(this.target).dialog("moveToTop");
        },
        open:function(){
        	var $t = this;
            $($t.target).dialog("open");
            if($t.times) setTimeout(function(){ $($t.target).dialog("close")},$t.times);
        }
    },
    _methods:{
    }
 });

 /**
 * 创建一个Tab控件,标签页
 * @function
 * @return {Object} ui控件
 */
 biz.tabs = biz.createUI(function(options){
	if(typeof options == "object"){	 
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'tab';
	    options.target = options.id;
	    //把扩展方法加到tab组件上
	    $.extend($.ui.tabs.prototype,this._methods||{});
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    } 
 }).extend({
	 init:function(opts){
         $(opts.target).tabs(opts);
         //增加标签页位置处理
         switch(opts.position){
              case "bottom":
            	  $( opts.target ).addClass( "tabs-bottom" );
                  $( opts.target + " .ui-tabs-nav,"+  opts.target + " .ui-tabs-nav > *" )
                              .removeClass( "ui-corner-all ui-corner-top" )
                              .addClass( "ui-corner-bottom" );
                  $( opts.target + "  .ui-tabs-nav" ).appendTo( ".tabs-bottom" );
                  break;
              case "left":
                   $( opts.target ).addClass( "ui-tabs-vertical ui-helper-clearfix" );
                   $( opts.target + " li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
                   break;
              default: break;
         }
         return this;
	},
	defaults:{
	    position:"top"//标签页位置top、bottom、left
	},
    methods:{
        destroy:function(){
            $(this.target).tabs("destroy");
        },
        disable:function(index){
            $(this.target).tabs("disable",index);
        },
        enable:function(index){
            $(this.target).tabs("enable",index);
        },
        /*
         *opts为属性对象时为给多个属性同时设置值；{属性1:"属性值1"，属性2:"属性值2"}
         *opts为字符串时为给单个属性取值或设置值
         */
        option:function(opts ,value){
            $(this.target).tabs("option",opts,value);
        },
        widget:function(){
            $(this.target).tabs("widget");
        },
        add:function(url , label , index){
            $(this.target).tabs("add",url , label , index);
        },
        remove:function(index){
            $(this.target).tabs("remove",index);
        },
        select:function(index){
            $(this.target).tabs( "select" , index );
        },
        load:function(index){
            $(this.target).tabs( "load" , index );
        },
        url:function( index , url){
            $(this.target).tabs( "url" , index , url );
        },
        length:function(){
            $(this.target).tabs( "length");
        },
        abort:function(){
            $(this.target).tabs( "abort");
        },
        rotate:function(ms , continuing ){
            $(this.target).tabs( "rotate" , ms , continuing );
        }
    }
 });

 /**
 * 创建一个doubleselect控件,双向选择器
 * @function
 * @return {Object} ui控件
 */
 biz.doubleselect = biz.createUI(function(options){
    if(typeof options == "object"){	 
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'doubleselect';
	    options.id = biz.utils.id(options.id);
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }	
 }).extend({
    init:function(opts){
        var obj =  new GBPSelectList(opts);
        obj.make();
        return obj;
    },
    defaults:{
        ajaxOptions:{
           error:function(xhr,st,err){
             	   biz.utils.loadError(xhr,st,err);
           }
        }
    },
    methods:{}
 });

 /**
 * 创建一个select控件,下拉选择框
 * @function
 * @return {Object} ui控件
 */
 biz.select = biz.createUI(function(options){
	if(typeof options == "object"){ 
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'select';
	    options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
        var obj;
        if(opts.isCascade){
            var setting = opts;
            if(setting.data){setting.list = setting.data;setting.data = undefined}
            if(setting.url){setting.ajax =$.extend({},setting.ajaxOptions,{url:setting.url}); setting.url = undefined}
            obj = $(opts.target).cascade(setting);
        }else{
            obj = $(opts.target).combobox(opts);
        }
        return obj;
    },
    defaults:{
        isCascade:false,
        url:"",
        data:[],
        parent:"" ,
        timeout:10,
        event:"change",
        ajaxOptions:{},
        value:""
    },
    methods:{}
 });

  /**
 * 创建一个radio控件,单选框
 * @function
 * @return {Object} ui控件
 */
 biz.radio = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'radio';
	    $(options.id).attr("uiType","radio");//添加uiType属性，用于查询框查询时，辨别是否是checkbox控件
	    options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    } 
 }).extend({
    init:function(opts){
        return  $(opts.target).radio(opts)
    },
    defaults:{
        data:"",
        url:""
    },
    methods:{
    	check:function(elem){
            $(this.target).radio("check",elem) ;
        },
        uncheck:function(elem){
             $(this.target).radio("uncheck",elem) ;
        },
        enable:function(elem){
            $(this.target).radio("enable",elem) ;
        },
        disable:function(elem){
            $(this.target).radio("disable",elem) ;
        },
        getCheckValue:function(name){
            return $(this.target).radio("getCheckValue",name);
        },
        getUncheckValue:function(name){
            return $(this.target).radio("getUncheckValue",name);
        }
    }
 });

  /**
 * 创建一个checkbox控件,复选框
 * @function
 * @return {Object} ui控件
 */
 biz.checkbox = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'checkbox';
	    $(options.id).attr("uiType","checkbox");//添加uiType属性，用于查询框查询时，辨别是否是checkbox控件
	    options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
    	return  $(opts.target).checkbox(opts)
    },
    defaults:{
        data:"",
        url:""
    },
    methods:{
    	check:function(elem){
            $(this.target).checkbox("check",elem) ;
        },
        uncheck:function(elem){
             $(this.target).checkbox("uncheck",elem) ;
        },
        checkAll:function(name){
            $(this.target).checkbox("checkAll",name);
        },
        uncheckAll:function(name){
            $(this.target).checkbox("uncheckAll",name);
        },
        toggles:function(name){
             $(this.target).checkbox("toggle",name);
        },
        enable:function(elem){
            $(this.target).checkbox("enable",elem) ;
        },
        disable:function(elem){
            $(this.target).checkbox("disable",elem) ;
        },
        enableAll:function(name){
            $(this.target).checkbox("enableAll",name);
        },
        disableAll:function(name){
            $(this.target).checkbox("disableAll",name);
        },
        getCheckValues:function(name){
            return $(this.target).checkbox("getCheckValues",name);
        },
        getUncheckValues:function(name){
            return $(this.target).checkbox("getUncheckValues",name);
        }
    }
 });

 /**
 * 创建一个button控件,按钮
 * @function
 * @return {Object} ui控件
 */
 biz.button = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'button';
	    options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
        if(opts.isButtonset){
             return  $(opts.target).buttonset(opts);
        }else{
             return  $(opts.target).button(opts);
        }
    },
    defaults:{
        isButtonset:false,
        disabled: null,
		text: true,
		label: null,
		icons: {
			primary: null,
			secondary: null
		}
    },
    methods:{
        enable:function(){
            $(this.target).button("enable");
        },
        disable:function(){
            $(this.target).button("disable");
        },
        destroy:function(){
            $(this.target).button("destroy");
        },
        /*
         *opts为属性对象时为给多个属性同时设置值；{属性1:"属性值1"，属性2:"属性值2"}
         *opts为字符串时为给单个属性取值或设置值
         */
        option:function(opts ,value){
            $(this.target).button("option",opts,value);
        },
        widget:function(){
            $(this.target).button("widget");
        },
        refresh:function(){
            $(this.target).button("refresh");
        }
    }
 });

 /**
 * 创建一个file控件,文件上传
 * @function
 * @return {Object} ui控件
 */
 biz.file = biz.createUI(function(options){
	if(typeof options == "object"){
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'file';
	    options.target = options.id;
	    /*如果封装的属性结构与源结构对比需要调整，需要做以下两三步：
	     * 1、把options与this.defaults合并；
	     * 2、进行机构调整；
	     * 3、返回调整后属性options
	     * 源结构：  hint:{denied: "请选择正确的文件，后缀为：", wrongname: "请选择正确的文件，文件名为:"}
	     * 调整后结构  denied: "请选择正确的文件，后缀为：", wrongname: "请选择正确的文件，文件名为:"
	     *
	     */
	    options = $.extend(true,{},this.defaults,options) ;
	    options.hint = {"denied":options.denied?options.denied:0,"wrongname":options.wrongname?options.wrongname:0};
	    return options;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }
 }).extend({
    init:function(opts){
        $(opts.target).file(opts);
        return  $(opts.target);
    },
    defaults:{
        buttonText:I18N.file_bDefaultValue,
        inputText: "",
		accept: "", //格式如：“jpg|gif|png”
        unaccept:"",//格式同accept
		filename: "",//格式如：“file.zip”
		denied:I18N.file_suffix,
	    wrongname:I18N.file_name
    },
    methods:{
        getValue:function(){
            $(this.target).file("getValue");
        },
        reset:function(){
            $(this.target).file("reset");
        },
        destroy:function(){
            $(this.target).file("destroy");
        }
    }
 });

 /**
 * 创建一个validate控件,校验
 * @function
 * @return {Object} ui控件
 */
 biz.validate = biz.createUI(function(options){
	if(typeof options == "object"){	
	    var me = this;
	    me.target = options.id;
	    me.uiType = 'validate';
	    options.target = options.id;
	}else{
		//全局方法使用方式：biz.validate("方法名",参数1,参数2……);
        var tempValue,args = $.makeArray(arguments).slice(1);
        tempValue = this.validate.prototype.methods[arguments[0]].apply(this,args);
        return tempValue;
        // jAlert(I18N.param_error + options , I18N.promp);
    }	
 }).extend({
    init:function(opts){
         var $t = this,$this = $(opts.target)[0],tempOpts = $.extend({},opts),returnObj = {} ;
         tempOpts.target = undefined;
         tempOpts.id = undefined;
         tempOpts.optType = undefined;
         if($this.nodeName.toLowerCase() == "input" || $this.nodeName.toLowerCase() == "textarea"|| $this.nodeName.toLowerCase() == "select"){
             //默认给input所在form初始化校验
             $($this.form).each(function(){$(this).validate({ignore:"",errorClass:"error validatebox",ignoreTitle: true,showErrors: $t.showErrors})});
             $(opts.target).rules("add",tempOpts);
             returnObj =  $(opts.target);
         }else if($this.nodeName.toLowerCase() == "div"  ){//add by futh 验证checkbox、radio必填
			 $($(opts.target).find(".checkbox")[0].form).each(function(){$(this).validate({ignore:"",errorClass:"error validatebox",ignoreTitle: true,showErrors: $t.showErrors})});
           
		   //获取div 下面的 checkbox or  radio元素
			var tempCheckBoxObj = $(opts.target).find(".checkbox");
			if(tempCheckBoxObj!=null && tempCheckBoxObj.length>0){
				 tempCheckBoxObj.rules("add",tempOpts);
                 returnObj =  tempCheckBoxObj ;
			}
			var tempRadioObj = $(opts.target).find(".radio");
			if(tempRadioObj!=null && tempRadioObj.length>0){
				 tempRadioObj.rules("add",tempOpts);
                 returnObj =  tempRadioObj ;
			}
            
		 }else if($this.nodeName.toLowerCase() == "form"){
             returnObj =  $(opts.target).validate($.extend({ignore:"",errorClass:"error validatebox",ignoreTitle: true,showErrors: $t.showErrors},tempOpts)) ;
         }
         return  returnObj;
    },
    defaults:{
        // optType:"add"//add or remove
    },
    methods:{
    	//全局方法，获取设置form校验属性
        setting:function(element,options){
           var settings = $(element).validate().settings;
           if(typeof options == "string"){
                return settings[options];
           }else{
              for(var opt in options){
                  settings[opt] = options[opt];
              }
           }
        },
    	//全局方法，校验是否通过
        valid:function(element){
           return $(element).valid();
        },
        //调用validate的rules方法
        rules:function(element,opreate,opts){
           return $(element).rules(opreate,opts);
        },
        //全局方法，设置默认值
        setDefaults:function(defaults){
           $.validator.setDefaults(defaults);
        },
        //全局方法，用于扩展校验规则
        addMethod:function(name,methodfuc,message){
           $.validator.addMethod(name,methodfuc,message);
        },
        //全局方法，用于扩展校验规则
        addClassRules:function(name,rules){
           $.validator.addClassRules(name,rules);
        },
        //全局方法，用于设置提示信息
        messages :function(message){
           $.extend($.validator.messages,message);
        },
        /**
	     * JQuery.Validate组件扩展：自定义校验信息提示框
	    */
        showErrors:function(){
            var t = this;
			for ( var i = 0; this.errorList[i]; i++ ) {
				var error = this.errorList[i];
				this.settings.highlight && this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );

				var elename = this.idOrName(error.element);
				// 错误信息div
				var errdiv = $('div[validId='+ elename + ']');
				if(errdiv.length == 0){ // 没有div则创建
					errdiv = $('<div>'
							+ '<div class="errmsgdiv fl errmsg"></div>'
							+ '</div>');
					errdiv.attr({"validId":  this.idOrName(error.element), generated: true})
					.addClass(this.settings.errorClass);
					errdiv.appendTo($('body'));
				}

				if($(error.element)[0].type == "textarea"){
						var position_x = 1 - 16/ $(error.element)[0].clientWidth + "";
						position_x = position_x.substring(2,4) + "%";
						$(error.element).css("background-position-x",position_x);
				}
				
				if($(error.element)[0].type == "checkbox"||$(error.element)[0].type == "radio"){
				    $(error.element).removeClass("error");
				    $(error.element).removeClass("validatebox");
				    if($(error.element).parent().attr("name")!="_diverr"){
				       var checkboxerrdiv = $('<div name="_diverr" style="border:1px dotted #FF0000;overflow:hidden;height:14px;padding-bottom:4px;width:auto; float: left;"></div>');
						$(error.element).wrap(checkboxerrdiv);
				    }
						
				}else if($(error.element)[0].type == "select-one"||$(error.element)[0].type == "select-multiple"){
				    if($(error.element).parent().attr("name")!="_diverr"){
						var selecterrdiv = $('<div name="_diverr" style="border:1px dotted #FF0000;overflow:hidden;width:auto; float: left;"></div>');
						$(error.element).wrap(selecterrdiv);
					}
				}
				errdiv.find(".errmsg").html(error.message || "");//设置校验提示信息

				// 鼠标放到控件上校验提示信息
				$(error.element).hover(function(e){
						$('div[validId="'+ t.idOrName(this) + '"]').css({left : (e.pageX+10) + 'px',top : (e.pageY + 5) + 'px'}); // 显示在鼠标位置偏移20的位置
						$('div[validId="'+ t.idOrName(this) + '"]').fadeIn(200);
					},
					function(){
						$('div[validId="'+ t.idOrName(this) + '"]').fadeOut(200);
					});
				}

			// 校验成功的去掉错误提示
			for ( var i = 0; this.successList[i]; i++ ) {
					$('div[validId="'+ this.idOrName(this.successList[i]) + '"]').remove();
					if("checkbox"==this.successList[i].type||"radio"==this.successList[i].type||"select-one"==this.successList[i].type||"select-multiple"==this.successList[i].type){
					    if($(this.successList[i]).parent().attr("name")=="_diverr"){
					        $(this.successList[i]).unwrap();
					    }
					}else{
					  $(this.successList[i]).removeClass("validatebox");
					}
			}
			// 自定义高亮
			if (this.settings.unhighlight) {
				for ( var i = 0, elements = this.validElements(); elements[i]; i++ ) {
					this.settings.unhighlight.call( this, elements[i], this.settings.errorClass, this.settings.validClass );
				}
			}
        }
    }
 });
 
 /**
  * 创建一个form控件,form表单
  * @function
  * @return {Object} ui控件
  */
 biz.form = biz.createUI(function(options){
	if(typeof options == "object"){	 
	     var me = this;
	     me.target = options.id;
	     me.uiType = 'form';
	     options.target = options.id;
	}else{
        jAlert(I18N.param_error + options , I18N.promp);
    }	
 }).extend({
     init:function(opts){
         var obj =  $(opts.target).form(opts);
         return obj;
     },
     defaults:{},
     methods:{}
 });
  
 /**
  * 创建一个number控件,数字录入框
  * @function
  * @return {Object} ui控件
  */
 biz.number = biz.createUI(function(options){
	 if(typeof options == "object"){
	     var me = this;
	     me.target = options.id;
	     me.uiType = 'number';
	     options.target = options.id;
	 }else{
         jAlert(I18N.param_error + options , I18N.promp);
     }  
 }).extend({
      init:function(opts){
          var obj =  $(opts.target).number(opts);
          return obj;
      },
      defaults:{},
      methods:{}
 });
   
 /**
  * 创建一个autocomplete控件,自动提示
  * @function
  * @return {Object} ui控件
  */
 biz.autocomplete = biz.createUI(function(options){
	 if(typeof options == "object"){	 
	     var me = this;
	     me.target = options.id;
	     me.uiType = 'autocomplete';
	     options.target = options.id;
	 }else{
        jAlert(I18N.param_error + options , I18N.promp);
     }	 
 }).extend({
     init:function(opts){
        return  $(opts.target).autocomplete(opts);
     },
     defaults:{
        disabled:false,
        appendTo:"body",
   		autoFocus:false,
   		delay: 300,
        minLength:1,
        position:{ my: "left top", at: "left bottom", collision: "none" },
        source:""
     },
     methods:{
        enable:function(){
            $(this.target).autocomplete("enable");
        },
        disable:function(){
            $(this.target).autocomplete("disable");
        },
        destroy:function(){
            $(this.target).autocomplete("destroy");
        },
        /*
         *opts为属性对象时为给多个属性同时设置值；{属性1:"属性值1"，属性2:"属性值2"}
         *opts为字符串时为给单个属性取值或设置值
         */
        option:function(opts ,value){
            $(this.target).autocomplete("option",opts,value);
        },
        widget:function(){
            $(this.target).autocomplete("widget");
        },
        search:function(value){
            $(this.target).autocomplete("search",value);
        },
        close:function(){
            $(this.target).autocomplete("close");
        }
     }
 });﻿jQuery.extend(jQuery.validator.messages, {  
    required: I18N.validator_required,  
    remote: I18N.validator_remote,  
    email: I18N.validator_email,  
    url: I18N.validator_url,  
    date:I18N.validator_date,  
    dateISO: I18N.validator_dateISO,  
    number: I18N.validator_number,  
    digits: I18N.validator_digits,  
    creditcard: I18N.validator_creditcard,  
    equalTo: I18N.validator_equalTo,  
    accept: I18N.validator_accept,  
    maxlength: jQuery.validator.format(I18N.validator_maxlength_1+"{0}"+I18N.validator_maxlength_2),  
    minlength: jQuery.validator.format(I18N.validator_minlength_1+"{0}"+I18N.validator_minlength_2),  
    rangelength: jQuery.validator.format(I18N.validator_rangelength_1+"{0}"+I18N.validator_rangelength_2+"{1}"+I18N.validator_rangelength_3),  
    range: jQuery.validator.format(I18N.validator_range_1+" {0}"+I18N.validator_range_2+" {1}"+ I18N.validator_range_3),  
    max: jQuery.validator.format(I18N.validator_max_1+" {0}"+I18N.validator_max_2),  
    min: jQuery.validator.format(I18N.validator_min_1+" {0}"+I18N.validator_min_2) 
});
    // 字符验证  
    jQuery.validator.addMethod("string", function(value, element) {  
        return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);  
    }, I18N.validator_specialSignal);
 
    // 只能输入中文  
    jQuery.validator.addMethod("stringCH", function(value, element) {  
        var length = value.length;  
        for(var i = 0; i < value.length; i++){  
            if(value.charCodeAt(i) > 127){  
                length++;  
            }  
        }  
        return this.optional(element) || /[^u4E00-u9FA5]/g.test(value);  
    }, I18N.validator_chineseOnly);  

    // 只能输入26个字母  
    jQuery.validator.addMethod("stringEN", function(value, element) {  
        var length = value.length;  
        for(var i = 0; i < value.length; i++){  
            if(value.charCodeAt(i) > 127){  
                length++;  
            }  
        }    
        return this.optional(element) || /^[A-Za-z]+$/g.test(value);  
    }, I18N.validator_letterOnly);  

    //ip地址
    jQuery.validator.addMethod("ip", function(value, element) {  
		return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));  
	}, I18N.validator_IP);
	
	//port
    jQuery.validator.addMethod("port", function(value, element) {  
		return this.optional(element) || (value < 65536 && value > 0);  
	}, I18N.validator_Port); 
	
	// 邮政编码验证
	jQuery.validator.addMethod("postalcode", function(value, element) {
		var tel = /^[0-9]{6}$/;
		return this.optional(element) || (tel.test(value));
	}, I18N.validator_Postalcode); 
	
	// 手机号码验证
	jQuery.validator.addMethod("mobile", function(value, element) {
		var length = value.length;
		//长度为11，以13，15，18开头的
		return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
	}, I18N.validator_mobile); 
	
	//字母数字
	jQuery.validator.addMethod("alnum", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, I18N.validator_alnum);
	
	//自然数
	jQuery.validator.addMethod("naturalnum", function(value, element) {
		return this.optional(element) || (/^[0-9]+$/.test(value) && (value > 0));
	},I18N.validator_naturalnum);
	
	//身份证号码
	jQuery.validator.addMethod("idcardno", function(value, element) {
		return this.optional(element) || isIdCardNo(value);   
	}, I18N.validator_idcardno);
	
	//时间
	jQuery.validator.addMethod("time", function(value, element) {
	    var tel = /^(\d{2}):(\d{2}):(\d{2})\s*([ap]m)?$/;
		return this.optional(element) || (tel.test(value));   
	}, I18N.validator_time);
	
/**
 * 身份证号码验证
 *
 */
function isIdCardNo(num) {
 var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
 var parityBit=new Array("1","0","X","9","8","7","6","5","4","3","2");
 var varArray = new Array();
 var intValue;
 var lngProduct = 0;
 var intCheckDigit;
 var intStrLen = num.length;
 var idNumber = num;
   // initialize
     if ((intStrLen != 15) && (intStrLen != 18)) {
         return false;
     }
     // check and set value
     for(i=0;i<intStrLen;i++) {
         varArray[i] = idNumber.charAt(i);
         if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
             return false;
         } else if (i < 17) {
             varArray[i] = varArray[i] * factorArr[i];
         }
     }
     
     if (intStrLen == 18) {
         //check date
         var date8 = idNumber.substring(6,14);
         if (isDate8(date8) == false) {
            return false;
         }
         // calculate the sum of the products
         for(i=0;i<17;i++) {
             lngProduct = lngProduct + varArray[i];
         }
         // calculate the check digit
         intCheckDigit = parityBit[lngProduct % 11];
         // check last digit
         if (varArray[17] != intCheckDigit) {
             return false;
         }
     }
     else{        //length is 15
         //check date
         var date6 = idNumber.substring(6,12);
         if (isDate6(date6) == false) {

             return false;
         }
     }
     return true;
     
 }
 function isDate6(sDate) {
     if (!/^[0-9]{6}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     if (year < 1700 || year > 2500) return false
     if (month < 1 || month > 12) return false
     return true
 }
 /**
 * 判断是否为“YYYYMMDD”式的时期
 *
 */
 function isDate8(sDate) {
     if (!/^[0-9]{8}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     day = sDate.substring(6, 8);
     var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
     if (year < 1700 || year > 2500) return false
     if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
     if (month < 1 || month > 12) return false
     if (day < 1 || day > iaMonthDays[month - 1]) return false
     return true
 }function isDate6(sDate) {
     if (!/^[0-9]{6}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     if (year < 1700 || year > 2500) return false
     if (month < 1 || month > 12) return false
     return true
 }
 /**
 * 判断是否为“YYYYMMDD”式的时期
 *
 */
 function isDate8(sDate) {
     if (!/^[0-9]{8}$/.test(sDate)) {
         return false;
     }
     var year, month, day;
     year = sDate.substring(0, 4);
     month = sDate.substring(4, 6);
     day = sDate.substring(6, 8);
     var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
     if (year < 1700 || year > 2500) return false
     if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
     if (month < 1 || month > 12) return false
     if (day < 1 || day > iaMonthDays[month - 1]) return false
     return true
 }
 $.template("optionTmpl",
        '<option value="${value}">${name}</option>');
$.template("checkboxTmpl",
        '<label><input type="checkbox" class="checkbox" name="${name}" value="${value}"/><span>${label}</span></label>');
$.template("radioTmpl",
        '<label><input type="radio" class="radio" name="${name}" value="${value}"/><span>${label}</span></label>');
$.template("notFound",
        '<div><h1>The data you wanted can\'t be found, please try again!</h1></div>');
$.template("btnTmpl",
        '<button type="button">${label}</button>');
$.template("textTmpl",
        '<label><span>${label}</span><input type="text" name="${name}" value="${value}"/></label>');
$.template("hiddenTmpl",
        '<input {{if id}} id="${id}" {{/if}} type="hidden" name="${name}" value="${value}"/>');
$.template("avatarTmpl",
        '{{if url}}<a href="{{= url}}">{{/if}}<dl><dt title="{{= email}}"></dt><dd>{{= username}}</dd></dl>{{if url}}</a>{{/if}}');
$.template("editBtnTmpl",
        '<button {{if id}}id="edit-{{= id}}"{{/if}} action="edit">Edit</button>');
$.template("delBtnTmpl",
        '<button {{if id}}id="delete-{{= id}}"{{/if}} action="del">Delete</button>');
$.template("addBtnTmpl",
        '<button {{if id}}id="add-{{= id}}"{{/if}} action="add">Add</button>');
$.template("listCItems",
        '<li><a href="#" action="topic" id="item-${id}">${title}</a><p>by ${author} at <span class="timestamp" title="${dateCreated}"></span></p></li>');
$.template("listItems",
        '<li><a href="#" action="topic" id="item-${id}">${title}</a>by ${author} at <span class="timestamp" title="${dateCreated}"></span></li>');
$.template("anchorTmpl",
        '<li><a href="#${id}">${label}</a></li>');
$.template("crumbItems",
        '<li><a href="#" {{if id}}id="${id}"{{/if}} {{if goto}}goto="${goto}"{{/if}} {{if action}}action="${action}"{{/if}} title="${description}">${label}</a></li>');
$.template("boxTmpl",
        '<div {{if id}}id="${id}"{{/if}}><h3>{{if wrap}}<strong>${label}</strong>{{else}}${label}{{/if}}{{if goto}}<a href="#" goto="${goto}">more ${String.toLowerCase(label)}</a>{{/if}}</h3>{{if tag}}<${tag}></${tag}>{{/if}}</div>');


/*
 * 修复在3.6.13火狐下不支持outerHTML属性问题
 */
if(typeof(HTMLElement)!="undefined" && window.navigator.userAgent.toLowerCase().indexOf("firefox") > -1){
	HTMLElement.prototype.__defineGetter__("outerHTML",function(){
		var a=this.attributes, str="<"+this.tagName, i=0;for(;i<a.length;i++)
		if(a[i].specified) str+=" "+a[i].name+'="'+a[i].value+'"';
		if(!this.canHaveChildren) return str+" />";
		return str+">"+this.innerHTML+"</"+this.tagName+">";
	});
	HTMLElement.prototype.__defineSetter__("outerHTML",function(s){
		var d = document.createElement("DIV"); d.innerHTML = s;
		for(var i=0; i<d.childNodes.length; i++)
		this.parentNode.insertBefore(d.childNodes[i], this);
		this.parentNode.removeChild(this);
	});
	HTMLElement.prototype.__defineGetter__("canHaveChildren",function(){
		return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
	});
}

/*
 * jquery resize事件只对window有效，通过此插件增强其使用范围，能监听div的resize事件；
 * 目前此功能用于grid自适应大小功能改进
 */
/*!
 * jQuery resize event - v1.1 - 3/14/2010
 * http://benalman.com/projects/jquery-resize-plugin/
 *
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */

// Script: jQuery resize event
//
// *Version: 1.1, Last updated: 3/14/2010*
//
// Project Home - http://benalman.com/projects/jquery-resize-plugin/
// GitHub       - http://github.com/cowboy/jquery-resize/
// Source       - http://github.com/cowboy/jquery-resize/raw/master/jquery.ba-resize.js
// (Minified)   - http://github.com/cowboy/jquery-resize/raw/master/jquery.ba-resize.min.js (1.0kb)
//
// About: License
//
// Copyright (c) 2010 "Cowboy" Ben Alman,
// Dual licensed under the MIT and GPL licenses.
// http://benalman.com/about/license/
//
// About: Examples
//
// This working example, complete with fully commented code, illustrates a few
// ways in which this plugin can be used.
//
// resize event - http://benalman.com/code/projects/jquery-resize/examples/resize/
//
// About: Support and Testing
//
// Information about what version or versions of jQuery this plugin has been
// tested with, what browsers it has been tested in, and where the unit tests
// reside (so you can test it yourself).
//
// jQuery Versions - 1.3.2, 1.4.1, 1.4.2
// Browsers Tested - Internet Explorer 6-8, Firefox 2-3.6, Safari 3-4, Chrome, Opera 9.6-10.1.
// Unit Tests      - http://benalman.com/code/projects/jquery-resize/unit/
//
// About: Release History
//
// 1.1 - (3/14/2010) Fixed a minor bug that was causing the event to trigger
//       immediately after bind in some circumstances. Also changed $.fn.data
//       to $.data to improve performance.
// 1.0 - (2/10/2010) Initial release

(function($,window,undefined){
  '$:nomunge'; // Used by YUI compressor.

  // A jQuery object containing all non-window elements to which the resize
  // event is bound.
  var elems = $([]),

    // Extend $.resize if it already exists, otherwise create it.
    jq_resize = $.resize = $.extend( $.resize, {} ),

    timeout_id,

    // Reused strings.
    str_setTimeout = 'setTimeout',
    str_resize = 'resize',
    str_data = str_resize + '-special-event',
    str_delay = 'delay',
    str_throttle = 'throttleWindow';

  // Property: jQuery.resize.delay
  //
  // The numeric interval (in milliseconds) at which the resize event polling
  // loop executes. Defaults to 250.

  jq_resize[ str_delay ] = 250;

  // Property: jQuery.resize.throttleWindow
  //
  // Throttle the native window object resize event to fire no more than once
  // every <jQuery.resize.delay> milliseconds. Defaults to true.
  //
  // Because the window object has its own resize event, it doesn't need to be
  // provided by this plugin, and its execution can be left entirely up to the
  // browser. However, since certain browsers fire the resize event continuously
  // while others do not, enabling this will throttle the window resize event,
  // making event behavior consistent across all elements in all browsers.
  //
  // While setting this property to false will disable window object resize
  // event throttling, please note that this property must be changed before any
  // window object resize event callbacks are bound.

  jq_resize[ str_throttle ] = true;

  // Event: resize event
  //
  // Fired when an element's width or height changes. Because browsers only
  // provide this event for the window element, for other elements a polling
  // loop is initialized, running every <jQuery.resize.delay> milliseconds
  // to see if elements' dimensions have changed. You may bind with either
  // .resize( fn ) or .bind( "resize", fn ), and unbind with .unbind( "resize" ).
  //
  // Usage:
  //
  // > jQuery('selector').bind( 'resize', function(e) {
  // >   // element's width or height has changed!
  // >   ...
  // > });
  //
  // Additional Notes:
  //
  // * The polling loop is not created until at least one callback is actually
  //   bound to the 'resize' event, and this single polling loop is shared
  //   across all elements.
  //
  // Double firing issue in jQuery 1.3.2:
  //
  // While this plugin works in jQuery 1.3.2, if an element's event callbacks
  // are manually triggered via .trigger( 'resize' ) or .resize() those
  // callbacks may double-fire, due to limitations in the jQuery 1.3.2 special
  // events system. This is not an issue when using jQuery 1.4+.
  //
  // > // While this works in jQuery 1.4+
  // > $(elem).css({ width: new_w, height: new_h }).resize();
  // >
  // > // In jQuery 1.3.2, you need to do this:
  // > var elem = $(elem);
  // > elem.css({ width: new_w, height: new_h });
  // > elem.data( 'resize-special-event', { width: elem.width(), height: elem.height() } );
  // > elem.resize();

  $.event.special[ str_resize ] = {

    // Called only when the first 'resize' event callback is bound per element.
    setup: function() {
      // Since window has its own native 'resize' event, return false so that
      // jQuery will bind the event using DOM methods. Since only 'window'
      // objects have a .setTimeout method, this should be a sufficient test.
      // Unless, of course, we're throttling the 'resize' event for window.
      if ( !jq_resize[ str_throttle ] && this[ str_setTimeout ] ) { return false; }

      var elem = $(this);

      // Add this element to the list of internal elements to monitor.
      elems = elems.add( elem );

      // Initialize data store on the element.
      $.data( this, str_data, { w: elem.width(), h: elem.height() } );

      // If this is the first element added, start the polling loop.
      if ( elems.length === 1 ) {
        loopy();
      }
    },

    // Called only when the last 'resize' event callback is unbound per element.
    teardown: function() {
      // Since window has its own native 'resize' event, return false so that
      // jQuery will unbind the event using DOM methods. Since only 'window'
      // objects have a .setTimeout method, this should be a sufficient test.
      // Unless, of course, we're throttling the 'resize' event for window.
      if ( !jq_resize[ str_throttle ] && this[ str_setTimeout ] ) { return false; }

      var elem = $(this);

      // Remove this element from the list of internal elements to monitor.
      elems = elems.not( elem );

      // Remove any data stored on the element.
      elem.removeData( str_data );

      // If this is the last element removed, stop the polling loop.
      if ( !elems.length ) {
        clearTimeout( timeout_id );
      }
    },

    // Called every time a 'resize' event callback is bound per element (new in
    // jQuery 1.4).
    add: function( handleObj ) {
      // Since window has its own native 'resize' event, return false so that
      // jQuery doesn't modify the event object. Unless, of course, we're
      // throttling the 'resize' event for window.
      if ( !jq_resize[ str_throttle ] && this[ str_setTimeout ] ) { return false; }

      var old_handler;

      // The new_handler function is executed every time the event is triggered.
      // This is used to update the internal element data store with the width
      // and height when the event is triggered manually, to avoid double-firing
      // of the event callback. See the "Double firing issue in jQuery 1.3.2"
      // comments above for more information.

      function new_handler( e, w, h ) {
        var elem = $(this),
          data = $.data( this, str_data );

        // If called from the polling loop, w and h will be passed in as
        // arguments. If called manually, via .trigger( 'resize' ) or .resize(),
        // those values will need to be computed.
        data.w = w !== undefined ? w : elem.width();
        data.h = h !== undefined ? h : elem.height();

        old_handler.apply( this, arguments );
      };

      // This may seem a little complicated, but it normalizes the special event
      // .add method between jQuery 1.4/1.4.1 and 1.4.2+
      if ( $.isFunction( handleObj ) ) {
        // 1.4, 1.4.1
        old_handler = handleObj;
        return new_handler;
      } else {
        // 1.4.2+
        old_handler = handleObj.handler;
        handleObj.handler = new_handler;
      }
    }

  };

  function loopy() {

    // Start the polling loop, asynchronously.
    timeout_id = window[ str_setTimeout ](function(){

      // Iterate over all elements to which the 'resize' event is bound.
      elems.each(function(){
        var elem = $(this),
          width = elem.width(),
          height = elem.height(),
          data = $.data( this, str_data );

        // If element size has changed since the last time, update the element
        // data store and trigger the 'resize' event.
        if ( width !== data.w || height !== data.h ) {
          elem.trigger( str_resize, [ data.w = width, data.h = height ] );
        }

      });

      // Loop.
      loopy();

    }, jq_resize[ str_delay ] );

  };

})(jQuery,this);
