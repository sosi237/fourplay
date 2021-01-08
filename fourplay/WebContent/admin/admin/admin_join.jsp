<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>관리자 계정 생성 화면</h2>

<form name="frmJoin" action="joinProc.jsp" method="post">
<input type="hidden" name="idChk" id="idChk" value="N" />
<table cellpadding="5" align="center">
<tr><td class="text" align="center"><input type="text" class="input" placeholder="이름" /></td></tr>
<tr>
<td class="text">
	<input type="text" name="uid" id="uid" onkeyup="chkDupId();" placeholder="아이디" />
	<br /><span id="idMsg">아이디는 4~20자 이내의 영문, 숫자 조합으로 입력하세요.</span>
</td>
</tr>
<tr><td class="text"><input type="password" name="pwd" placeholder="비밀번호" /></td></tr>
<tr><td class="text"><input type="password" name="pwd2" placeholder="비밀번호 확인" /></td></tr>
<tr><td class="text"><input type="text" name="email" placeholder="이메일" /></td></tr>
<tr><td class="text"><input type="text" name="phone" placeholder="휴대폰 번호" /></td></tr>
<tr><td class="text"><input type="text" name="birth" placeholder="생년월일" /></td></tr>
<tr><td align="center">
	<input type="submit" value="회원 가입" />
</td></tr>
</table>
</form>
</body>
</html>