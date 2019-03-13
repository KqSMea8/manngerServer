<%@ page contentType="text/html; charset=UTF-8" %>
<% 
String pId=request.getParameter("id");
String pCount=request.getParameter("count");
if (pId==null || pId=="") pId = "0";
if (pCount==null || pCount=="") pCount = "10";
int max = Integer.parseInt(pCount);
String zNodes = "[";
for (int i=1; i<=max; i++) {
	String nId = pId+"_"+i;
	String nName = "tree"+nId;
	zNodes = zNodes+ "{ id:'"+nId+"',	name:'"+nName+"'},";

}
zNodes = zNodes.substring(0,zNodes.length()-1)+ "]";



%>
<%=zNodes%>