<%@ page contentType="text/html; charset=UTF-8" %>
<% 

String zNodes = "[";
String pId = "0";
String pName = "";
String pLevel = "";
String pCheck = "";
pId = request.getParameter("id")!=null?request.getParameter("id"):pId;
pLevel = request.getParameter("lv")!=null?request.getParameter("lv"):pLevel;
pName = request.getParameter("n")!=null?request.getParameter("n"):pName;
pCheck = request.getParameter("chk")!=null?request.getParameter("chk"):pCheck;
if (pId==null || pId=="") pId = "0";
if (pLevel==null || pLevel=="") pLevel = "0";
if (pName==null) {
	 pName = "";
}else{
	 pName = pName+".";
}
for (int i=1; i<5; i++) {
	 String nId = pId+i;
	 String nName  = pName+"n"+i;
     zNodes += "{ id:'"+nId+"',	name:'"+nName+"',	isParent:"+(( Integer.parseInt(pLevel) < 2 && (i%2)!=0)?"true":"false")+(pCheck==""?"":(((Integer.parseInt(pLevel) < 2 && (i%2)!=0)?", halfCheck:true":"")+(i==3?", checked:true":"")))+"}";
	 if (i<4) {
		zNodes +=  ",";
	 }
}
zNodes +=  "]";
%>

<%=zNodes%>