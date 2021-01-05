<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
session.setMaxInactiveInterval(1800);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="css/reset.css" type="text/css" rel="stylesheet" />
<link href="css/base.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%
if (loginMember == null) {
%>
<a href="login_form.jsp">로그인</a>
<%
} else {
%>
<%=loginMember.getMlid() + "(" + loginMember.getMlname() + ")" %>
님 환영합니다.<br />
현재 <%=loginMember.getMlpoint() %>p 보유중<br />
<a href="logout">로그아웃</a>
<%
}
%>
<hr />
<a href="order_list.mpg">주문내역- 프론트</a>
</body>
</html>