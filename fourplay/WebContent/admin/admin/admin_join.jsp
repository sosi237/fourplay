<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ include file="../a_menu.jsp" %>
<%@ include file="../admin_menu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="jquery-3.5.1.js"></script>
<script>
function chkDupAId(){
	var aid = $("#aid").val();
	if (aid.length > 1) {
		$.ajax({
			type : "POST",	// 데이터 전송방법 
			url : "/fourplay/dupAID", 	// 중복체크를 위해 이동할 컨트롤러 URL
			data : {"aid":aid}, // url로 지정한 곳으로 보낼 데이터
			success : function(chkRst) {
			// 함수 실행시 url로 지정한 곳에서 작업한 결과를 chkRst로 받아 옴
				var msg = "";
				if(chkRst == 0) {
					msg = "<span class='fontBlue'>사용하실 수 있는 ID입니다.</span>";
					$("#idChk").val("Y");
				} else {
					msg = "<span class='fontRed'>이미 사용중인 ID입니다.</span>";
					$("#idChk").val("N");
				}
				document.getElementById("idMsg").innerHTML = msg;
			}
		});
	}
}
</script>
<style>
.join {
	width:800px; display:block; margin-left:380px; margin-top:50px;
}
.join .text input { width:400px; height:40px; margin-bottom:15px;}
.join .text { display:block; text-align:center; }
.join #btn { 
	width:110px; height:50px; background-color:black; color:white; font-weight:bold;
}
.fontRed { color:red; font-weight:bold; }
.fontBlue { color:blue; font-weight:bold; }

h2 {display:block; margin-left:480px;}
</style>
</head>
<body>
<div id="wrapper">
	<h2>관리자 계정 생성</h2>
	<form name="frmJoin" action="admin/a_join_proc.jsp" method="post">
	<input type="hidden" name="idChk" id="idChk" value="N" />
	<div class="join">
	<table cellpadding="5" align="center">
	<tr><td class="text">
		<input type="text" name="aid" id="aid" onkeyup="chkDupAId();" placeholder="아이디" />
		<br /><span id="idMsg"> </span>
	</td></tr>
	<tr><td class="text" align="center"><input type="text" name="name" class="input" placeholder="이름" /></td></tr>
	<tr><td class="text"><input type="password" name="pwd" placeholder="비밀번호" /></td></tr>
	<tr><td class="text"><input type="password" name="pwd2" placeholder="비밀번호 확인" /></td></tr>
	<tr><td class="text"><input type="text" name="email" placeholder="이메일" /></td></tr>
	<tr><td class="text"><input type="text" name="phone" placeholder="휴대폰 번호" /></td></tr>
	<tr><td align="center">
		<input id="btn" type="submit" value="계정 생성" />
	</td></tr>
	</table>
	</div>
	</form>
</div>
</body>
</html>