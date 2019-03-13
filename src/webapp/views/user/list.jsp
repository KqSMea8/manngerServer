<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>用户管理</title>
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/framejs/lib/style/skin/default/theme.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/common/page/page.css" />
<script type="text/javascript" src="${ctx}/static/common/page/page.js"></script>
<script>
 function submit_btn()
 {
 	//alert(document.forms["userForm"].action);
 	document.forms[0].submit();
 }
 
 function insert_btn()
 {
 	location.href="${ctx}/user.htm?method=toInsert";
 }
  // 页面加载时执行，有需要提示的信息时进行提示
 function init()
 {
 	var message = "${hint_message}";
 	if (message != null && message != "")
 	{
 		alert(message);
 	}
 }
 </script>
</head>
<body>
	<form id="userForm" action="${ctx}/user.htm" method="post">
	<div class="main">
		<!-- 查询部分开始 -->
		<div class="barTitle">
			<div class="content">
				<a href="javascript:;" onfocus="this.blur()"> </a> <span>用户管理</span>
			</div>
		</div>
		<hr class="barTitleHr" />
		<div class="ui-table ui-widget ui-corner-all ui-border">
			<table class="table">
				<tr>
					<td class="inputLabelTd">姓名：</td>
					<td class="inputTd"><input name="name" type="text" class="text" value="${user.name}" />
					</td>

					<td class="inputLabelTd">登陆名：</td>
					<td class="inputTd"><input name="loginId" type="text" class="text" value="${user.loginId}" />
					</td>
				</tr>
			</table>
			<table class="table foottable">
				<tfoot class="footTd">
					<tr>
						<td class="inputTd">
							<input type="button" value="查询" onclick="javascription:submit_btn()">	
							<input type="reset" value="重置">		
							<input type="button" value="新增" onclick="javascription:insert_btn()">
						</td>
					</tr>
				</tfoot>

			</table>
		</div>
	</div>
	<!-- 查询部分结束 -->

	<!-- 列表部分 start -->
	<div class="main">
		<div class="barTitle">
			<div class="content">
				<a href="javascript:;" onfocus="this.blur()"> </a> <span>用户信息列表</span>
			</div>
		</div>
		<table id="orderTable" class="grid" style="margin-left:2px">
			<thead>
				<tr style="background-color: #9FC7EB;">
					<th style="width:10%">登陆名</th>
					<th style="width:10%">姓名</th>
					<th style="width:10%">员工号</th>
					<th style="width:20%">手机</th>
					<th style="width:20%">邮箱</th>
					<th style="width:10%">状态</th>
					<th style="width:20%">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pager.data}" var="user">
				<tr>
					<td>${user.loginId}</td>
					<td>${user.name}</td>
					<td>${user.empNo}</td>
					<td>${user.mobile}</td>
					<td>${user.email}</td>
					<td>${user.state}</td>
					<td>
						<a href="${ctx}/user.htm?method=toUpdate&userId=${user.userId}">修改</a>&nbsp;&nbsp;&nbsp;
						<a href="${ctx}/user.htm?method=delete&userId=${user.userId}">删除</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<script language="JavaScript">
		// 分页开始　		
		var pg = new showPages('pg');
		pg.page = ${pager.currentPage};
		pg.pageCount =${pager.totalPages};  // 定义总页数(必要),可从后台获取
		pg.printHtml(2);		
		// 分布结束
		</script>
		
	</div>
	<!-- 列表部分 end -->
</form>
</body>
</html>
