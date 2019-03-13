<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>

<title>bizfoundation v5 页面组件示例中心</title>
<script src="${ctx}/static/framejs/indexDepend/index.js" type="text/javascript"></script>
<link href="${ctx}/static/framejs/indexDepend/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/lib/style/reset.css" media="screen, projection"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/lib/style/layout.css" media="screen, projection"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/lib/style/skin/default/theme.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/lib/style/skin/default/components/jqgrid/jquery.jqgrid.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/lib/style/skin/default/components/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/tree/api/api/apiCss/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framejs/tree/api/api/apiCss/zTreeStyleForApi.css"/>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/jquery-all.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/widgets.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/i18n/i18n_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/tree.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/grid.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/validate.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/lib/js/lib/biz.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/indexDepend/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/indexDepend/jquery.contextMenu.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/tree/api/api/apiCss/api.js"></script>
<!-- 代码高亮组件syntaxhighlighte -->
<script type="text/javascript" src="${ctx}/static/framejs/indexDepend/syntaxhighlighter/scripts/shCore.js"></script>
<script type="text/javascript" src="${ctx}/static/framejs/indexDepend/syntaxhighlighter/scripts/shBrushJScript.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/framejs/indexDepend/syntaxhighlighter/styles/shCoreDefault.css"/>
<script type="text/javascript">SyntaxHighlighter.all();</script>

<style id="modifyskin"></style>
</head>
<body>

  	<div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
		<!-- table id="west-grid"></table-->
		<ul id="demotree" class="ztree" style="width:auto;height:auto;margin-top:3px;"></ul>
	</div> <!-- #LeftPane -->
	<div id="RightPane" class="ui-layout-center ui-helper-reset ui-widget-content" ><!-- Tabs pane -->
    <div id="switcher"></div>
		<div id="menutabs" class="jqgtabs">
			<ul id="tabUI">
				<li><a href="#tabs1">简介</a></li>
			</ul>
			<div id="tabs1"> <p style="font-size:12px;">bizfoundation v5页面组件全面使用jquery插件机制实现，具体有轻量级、易掌握、好扩等展特性。</p><br/>
				</div>
		</div>
	</div> <!-- #RightPane -->
	<ul id="myMenu" class="contextMenu">
			<li><a href="#close">关闭</a></li>
			<li><a href="#closeOther">关闭其他</a></li>
			<li><a href="#closeAll">关闭所有</a></li>

		</ul>
	
<div class="ui-layout-north">
  <div class="title1">
		<div class="content">
			网络柜箱智能系统
		</div>
	</div>	
</div> 
<div class="ui-layout-south">BizFoundation V5</div> 
</body>

</html>