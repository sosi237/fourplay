<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ include file="../menu.jsp" %>
<%
String uid = loginMember.getMlid();
String wtype= request.getParameter("wtype");
String plid = request.getParameter("plid");
String plname = request.getParameter("plname");
String odidx = request.getParameter("odidx");
String olid = request.getParameter("olid");
String msg = "등록";
if(wtype.equals("up"))	msg = "수정";
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
<script>
//이미지 파일만 업로드 가능하도록
function fileCheck(obj){
	var imgName = obj.value;
	var idx = imgName.lastIndexOf('.');
	var ext = imgName.substring(idx + 1, obj.length);
	var type = ext.toLowerCase();
	
	if(!(type == "jpa" || type == "gif" || type == "png" || type == "jpeg")){
		alert("이미지 파일(jpg, gif, png, jpeg)만 업로드 가능합니다.");
		obj.value="";
	}
}
</script>
</head>
<body>
<div id="wrapper">
<div class="review">
<form name="reviewFrm" action="review_proc.review" method="post" enctype="multipart/form-data" >
	<input type="hidden" name="plid" value="<%=plid %>" />
	<input type="hidden" name="uid" value="<%=uid %>" />
	<input type="hidden" name="olid" value="<%=olid %>" />
	<input type="hidden" name="wtype" value="<%=wtype %>" />
	<input type="hidden" name="odidx" value="<%=odidx %>" />
	<h2>리뷰 <%=msg %></h2>
	<ul class="rTitle">
		<li>작성자</li>
		<li>제목</li>
		<li>평점</li>
		<li>상품명</li>
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
				<input type="radio" name="rRate" value="5" checked="checked" /><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/><img src='images/star.png' width="12px"/>
			</ol>
		</li>
		<li><%=plname %></li>
	</ul>
	<ul class="rContent">
		<li><textarea rows="20" cols="105" name="content"></textarea></li>
	</ul>
	<ul class="Btn">
		<li><input type="file" name="reviewImg" class="btn" onchange="fileCheck(this);"/></li>
		<li><input type="submit" value="등록" class="btn" /></li>
	</ul>
</form>
</div>
</div>
</body>
</html>