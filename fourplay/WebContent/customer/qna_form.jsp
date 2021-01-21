<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="../menu.jsp" %>
<%

request.setCharacterEncoding("utf-8");
String wtype = request.getParameter("wtype");
String args = "";

String msg = "수정", writer = "", title = "", content = "";
int idx = 0;
if (wtype.equals("in")) {
	msg = "등록";
} else if (wtype.equals("up")) {	// 게시글 수정일 경우
	QAInfo article = (QAInfo)request.getAttribute("article");
	idx = article.getQl_idx();
	writer = article.getQl_writer();
	title = article.getQl_title();
	content = article.getQl_content();

	int cpage = Integer.parseInt(request.getParameter("cpage"));
	args = "?cpage=" + cpage;
	String schtype = request.getParameter("schtype");
	String keyword = request.getParameter("keyword");
	if (keyword != null && !keyword.equals("")) {
		args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#wrapper h2 {margin-bottom:20px;}

.qnaForm { width:720px; display:block; margin:0 auto; }

.bqform { font-size:13px; }
.qfcon {margin-top:10px;}
.qfcon td { border-bottom:1px #8C8C8C solid; }
.qfcon input { border:1px #8C8C8C solid;}
#aname td { border-bottom:2px #BDBDBD solid; }
#content { width:100%; height:300px; border:1px #8C8C8C solid; margin-top:15px;}
.button input { 
	width:100px; height:30px; border:0px; background-color:#002266; color:#FFFFFF; font-size:13px; 
	margin:15px;
}
</style>
</head>
<body>
<div id="wrapper">

<div class="qnaForm">
<h2>Q&A 게시판 글<%=msg %></h2>
<form name="frmQA" action="brd_proc.qna<%=args %>" method="post">
<input type="hidden" name="idx" value="<%=idx %>" />
<input type="hidden" name="wtype" value="<%=wtype %>" />
<%
if (loginMember != null){
%>
<input type="hidden" name="writer" value="<%=loginMember.getMlid() %>" />
<%} %>
<table class="bqform" width="700" cellpadding="5" cellspacing="0" cellpadding="5">
<tr id="aname"><td colspan="5">
Q&A &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span> <a href="index.jsp"> HOME </a>> 고객센터 >> Q&A </span>
</td></tr>
<%
if (wtype.equals("in") ) {
%>
<tr class="qfcon">
		<%	if (adminMember != null) { // 관리자인 경우 %> 
				<th>관리자</th><td><%=adminMember.getAl_id() %></td>
		<% } else if (loginMember != null) { // 작성자 경우 %> 
			<th>작성자</th><td><%=loginMember.getMlid() %></td>
		<% } else { 
			response.setContentType("text/html;charset=utf-8");
			out.println("<script>");
			out.println("alert('로그인 해주세요.');");
			out.println("location.href='./login_form.jsp'");
			out.println("</script>");
			out.close();
		} %> 
</tr>

<%
} else { %>
	<tr><th>작성자</th><td><%=writer %></td></tr>
<% } %>		
<!-- 
<tr>
<th>분류</th>
<td>상품문의</td>
<td>소분류</td>
<td>상품명 </td>
</tr>
 -->
<tr>
<th>제목</th>
<td colspan="3"><input type="text" name="title" size="58" value="<%=title %>" /></td>
</tr>
<tr>
<th>내용</th>
<td colspan="3">
	<textarea id="content" name="content" rows="10" cols="60" placeholder=" 상품코드:&#13;&#10; 상품명:&#13;&#10; 문의사항:" ><%=content %></textarea>
</td>
</tr>
<tr><td class="button" colspan="4" align="center">
	<input type="submit" value="글<%=msg %>" />
	<input type="reset" value="다시 입력" />
</td></tr>
</table>
</form>
</div>

</div>
</body>
</html>
