<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>用户新增</title>
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/framejs/lib/style/skin/default/theme.css" />
<script>
 function submit_btn()
 {
 	//alert(document.forms["userForm"].action);
 	document.forms[0].submit();
 }
 
 function back_btn()
 {
 	history.back();
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
<body onload="init()">
	<form id="userForm" action="${ctx}/user.htm?method=${method}" method="post">
	<input type="hidden" name="userId" value="${user.userId}">
	<div class="main">
		<!-- 新增表单-->
		<div class="barTitle">
			<div class="content">
				<a href="javascript:;" onfocus="this.blur()"> </a> <span>用户新增</span>
			</div>
		</div>
		<hr class="barTitleHr" />
		<div class="ui-table ui-widget ui-corner-all ui-border">
			<table class="table">
				<tr>
					<td class="inputLabelTd">登陆名：</td>
					<td class="inputTd"><input name="loginId" type="text" class="text" value="${user.loginId}" />
					</td>
					<td class="inputLabelTd">密码：</td>
					<td class="inputTd"><input name="passwd" type="text" class="text" value="${user.passwd}" />
					</td>
				</tr>
				<tr>
					<td class="inputLabelTd">姓名：</td>
					<td class="inputTd"><input name="name" type="text" class="text" value="${user.name}" />
					</td>

					<td class="inputLabelTd">手机：</td>
					<td class="inputTd"><input name="mobile" type="text" class="text" value="${user.mobile}" />
					</td>
				</tr>
				<tr>
					<td class="inputLabelTd">电话：</td>
					<td class="inputTd"><input name="tel" type="text" class="text" value="${user.tel}" />
					</td>

					<td class="inputLabelTd">email：</td>
					<td class="inputTd"><input name="email" type="text" class="text" value="${user.email}" />
					</td>
				</tr>
				<tr>
					<td class="inputLabelTd">状态：</td>
					<td class="inputTd"><input name="state" type="text" class="text" value="${user.state}" />
					</td>

					<td class="inputLabelTd">工号：</td>
					<td class="inputTd"><input name="empNo" type="text" class="text" value="${user.empNo}" />
					</td>
				</tr>
			</table>
			<table class="table foottable">
				<tfoot class="footTd">
					<tr>
						<td class="inputTd">
							<input type="button" value="确定" onclick="javascription:submit_btn()">	
							<input type="reset" value="重置">		
							<input type="button" value="返回" onclick="javascription:back_btn()">
						</td>
					</tr>
				</tfoot>

			</table>
		</div>
	</div>
	<!-- 表单部分 end -->
</form>
</body>
</html>
