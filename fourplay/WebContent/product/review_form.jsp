<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ include file="../menu.jsp" %>
<%
String uid = loginMember.getMlid();
String wtype= request.getParameter("wtype");
//if(wtype.equals("up"))
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#wrapper .review {width:50%; margin:0 auto;}
.rTitle {float:left; border:1px solid red;}
.rTitle:first-child {width:20%;border:1px solid red;}
.rContent {width:100%; display:block; border:1px solid blue;}
.Btn {float:left;}
.btn {}
</style>
</head>
<body>
<div id="wrapper">
<div class="review">
<form name="reviewFrm" action="review_proc.review?wtype=<%=wtype %>" method="post" enctype="multipart/form-data" >
	<h2>리뷰 등록</h2>
	<ul class="rTitle">
		<li>작성자</li>
		<li>제목</li>
		<li>평점</li>
	</ul>
	<ul class="rTitle">
		<li><%=uid %></li>
		<li><input type="text" name="title" /></li>
		<li>
			<ol>
				<input type="radio" name="rRate" value="1"/><img src='images/star.png' width="12px"/>
				<input type="radio" name="rRate" value="2" /><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/>
				<input type="radio" name="rRate" value="3" /><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/>
				<input type="radio" name="rRate" value="4" /><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/>
				<input type="radio" name="rRate" value="5" /><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/>
			</ol>
		</li>
	</ul>
	<ul class="rContent">
		<li><textarea rows="20" cols="105"></textarea></li>
	</ul>
	<ul class="Btn">
		<li><input type="file" name="reviewImg" class="btn"/></li>
		<li><input type="submit" value="등록" class="btn" /></li>
	</ul>
</form>
</div>
</div>
</body>
</html>