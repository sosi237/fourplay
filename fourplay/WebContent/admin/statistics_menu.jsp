<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
String requestUri = request.getRequestURI();
String contextPath = request.getContextPath();
String command = requestUri.substring(contextPath.length());
System.out.println(command);
AdminInfo admMember = (AdminInfo)session.getAttribute("adminMember");

String kind = (String)request.getParameter("kind");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.statMenu {
	position:absolute; top:30%; left:50px; width:100px;  padding:10px;
	font-size:18px; text-align:center;
}
.statMenu ol { list-style:none; margin:0; padding:0; width:100%;}	
.statMenu li {height:30px; list-style-type:none; margin-bottom:10px; padding:0; }
.sub { width:80%; margin:10px auto; margin-bottom:15px; font-size:15px; border:1px solid white;}
.selected {background-color:#5b8588; font-weight:bold;}
.selected a{ text-decoration: none; color: white; }
.subSlt {background-color:#85baaa; font-weight:bold;}
.subSlt a{ text-decoration: none; color: white; }
</style>
<script src="https://code.jquery.com/jquery-3.5.1.js" ></script>
<script>
$(document).ready(function(){
<%if(command.equals("/admin/stat/mem_stat.jsp")) { %>
	$("#member").addClass("selected");
	<% if(kind != null && kind.equals("gender")){%>
		$("#gender").addClass("subSlt");
	<%} else if(kind != null && kind.equals("age")){%>
		$("#age").addClass("subSlt");
	<%}%>
<%} else if(command.equals("/admin/stat/pdt_stat.jsp")){%>
	$("#product").addClass("selected");
<%} else {%>
	$("#sales").addClass("selected");
<%}%>
});
</script>
</head>
<body>
<div class="statMenu">
<ol>
	<li id="member"><a href="mem_stat.stat?kind=gender">회원 통계</a></li>
		<%if(command.equals("/admin/stat/mem_stat.jsp")) { %>
			<li id="gender" class="sub"><a href="mem_stat.stat?kind=gender">성별 비율</a></li>
			<li id="age" class="sub"><a href="mem_stat.stat?kind=age">연령 통계</a></li>
		<%} %>
	<li id="product"><a href="pdt_stat.stat">상품 통계</a></li>
	<li id="sales" ><a href="sales_stat.stat">매출 통계</a></li>
</ol>
</div>
</body>
</html>