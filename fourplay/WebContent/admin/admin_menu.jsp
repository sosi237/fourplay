<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/*
<jsp:include page="admin_menu.jsp">
<jsp:param name="name" value="홍길동" />
</jsp:include>
*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#menu {width:100px; border:1px solid gray;}
ol { list-style:none; margin:0; padding:0; width:100%;}	
li {list-style-type:none; margin:0; padding:0; border-bottom:1px solid gray;}
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:pink; text-decoration:underline;  font-weight:bold;}
a:active { color:#f00; text-decoration:none; }	
a:focus { color:#f00; text-decoration:underline; }
</style>
</head>
<body>
<div id="menu">
<ol>
	<li><a href="admin_list.adm">관리자 목록</a></li>
	<li><a href="admin_pms.adm">관리자<br />권한설정</a></li>
	<li><a href="admin_join.adm">관리자<br />계정 생성</a></li>
	<li><a href="admin_del.adm">관리자<br />계정 삭제</a></li>
	<li><a href="admin_form.adm">관리자 계정<br />정보 수정</a></li>
</ol>
</div>

</body>
</html>