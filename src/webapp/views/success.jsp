<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>成功页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script>

 function init()
 {
 	var t=setTimeout("toJsp()",2000);
 }
 function toJsp()
 {
 	location.href="${ctx}/${toJsp}";
 }
 </script>
  </head>
  
  <body onload="init()">
  	<br>
  	<div align="center">操作成功</div>
  	<c:out value="${resp_xml}"></c:out>
  </body>
</html>
