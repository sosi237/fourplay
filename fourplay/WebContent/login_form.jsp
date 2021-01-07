<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
String cnt = request.getParameter("cnt");
String args = request.getParameter("args");
String optValue = "";	// 선택한 옵션들을 담을 변수
int optCnt = 0;			// 옵션의 개수를 담을 변수
if (request.getParameter("optCnt") != null) {	// 옵션이 있으면
	optCnt = Integer.parseInt(request.getParameter("optCnt"));
	for (int i = 0 ; i < optCnt ; i++) {
		optValue += "," + request.getParameter("opt" + i);
	}
	optValue = optValue.substring(1);
	// 선택한 옵션들을 쉼표를 구분자로 하여 문자열로 만듦
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.btn { 
	width:100px; height:80px; background-color:black;  
	border:solid 1px black; color:white; font-size:20px; 
}
.btn2 {
	width:140px; height:30px; background-color:black;  
	border:solid 1px black; color:white; font-size:15px; 
}
.btn3 {
	width:140px; height:30px; background-color:gray;  
	border:solid 1px gray; color:white; font-size:15px; 
}
.text {
	width:250px; height:20px;
}
hr {
	width:530px;
}
</style>
<script>
function goJoin() {
// 회원 가입 폼으로 이동시키는 함수
	var frm = document.frmLog;
	frm.action = "join_form.jsp";
	frm.submit();
}

<% if (id != null && cnt != null) { %>
function goDirect() {
// 로그인 없이 비회원인 상태로 구매할 때 구매폼으로 이동시키는 함수
	var frm = document.frmLogin;
	frm.action = "ord_form.ord";
	frm.submit();
}
<% } %>
</script>
</head>
<body>
<h2 align="center">LOGIN</h2>
<form name="frmLog" action="login" method="post">
<table cellpadding="3" align="center"> 
<tr>
	<td>아이디&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="uid"  class="text" /></td>
	<td rowspan="2" align="right"><input type="submit" value="LOGIN" class="btn"  /></td>
</tr>
<tr><td>비밀번호&nbsp;&nbsp;&nbsp;<input type="password" name="pwd" class="text"/></td></tr>
</table>
<hr />
<table cellpadding="3" align="center">
<tr>
	<td>회원가입 하시면 더 많은 혜택을 누리실 수 있습니다.</td><td><input type="button" value="회원가입" onclick="goJoin();" class="btn2" /></td>
</tr>
<tr>
	<td>아이디/비밀번호를 잊으셨나요?</td><td><input type="button" value="아이디/비밀번호 찾기" onclick="goChk();" class="btn3" /></td>
</tr>
</table>
<hr />
<% if (id != null && cnt != null) { %>
<table cellpadding="3" align="center">
<tr>
	<td>비회원님도 상품구매가 가능합니다. 단 쇼핑몰에서<br />제공하는 다양한 회원 혜택에서 제외될 수 있습니다.</td>
	<td><input type="button" value="비회원 구매하기" onclick="goDirect();" class="btn2" /></td>
</tr>
</table>
<hr />
<% } %>
</form>
</body>
</html>
