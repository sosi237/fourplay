<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<AdminInfo> admList = (ArrayList<AdminInfo>)request.getAttribute("admList");
AdmPageInfo pageInfo = (AdmPageInfo)request.getAttribute("pageInfo");
String status, schtype, keyword, ord;
schtype =	pageInfo.getSchtype();	// 검색조건(관리자 아이디, 관리자 이름)
keyword =	pageInfo.getKeyword();	// 검색어
status =	pageInfo.getStatus();	// 계정 사용여부
ord =		pageInfo.getOrd();		// 정렬조건

if(keyword == null)					keyword = "";
if(schtype == null)					schtype = "";

String args = "", schArgs = "";
if (status != null)		schArgs += "&status=" + status;		else	status = "";
if (!keyword.equals(""))					schArgs += "&schtype=" + schtype + "&keyword=" + keyword;
else 														schtype = "";	keyword = "";

if (ord != null)		schArgs += "&ord=" + ord;			else	ord = "";

int cpage	= pageInfo.getCpage();	// 현재 페이지 번호
int pcnt	= pageInfo.getPcnt();	// 전체 페이지 수
int psize	= pageInfo.getPsize();	// 페이지 크기
int bsize	= pageInfo.getBsize();	// 블록 페이지 개수
int spage	= pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage	= pageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt	= pageInfo.getRcnt();	// 검색된 게시물 개수
schArgs = "&psize=" + psize + schArgs;
args = "&cpage=" + cpage + schArgs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#sch {text-align:right;}
#admList {margin-top:10px;}
#admList table {width:900px;}
#admList th, #admList td {text-align:left; border-bottom:1px solid black;}
#paging {text-align:center; margin-top:10px;}
</style>
</head>
<body>
<h2>관리자 목록</h2>
<form name="schFrm" action="" method="get">
<div id="sch">
<table cellspacing="0">
<th>검색어</th>
<td>
	<select name="schtype">
		<option value="" <% if (schtype.equals("")) { %>selected="selected"<% } %>>검색조건</option>
		<option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>아이디</option>
		<option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>이름</option>
	</select>
	<input type="text" name="keyword" />
</td>
<td><input type="submit" value="검색" /></td>
</table>
</div>
</form>
<form name="listFrm" action="admin_proc.adm" method="post">
<div id="admList">
	<table cellspacing="0">
	<tr>
	<th>아이디<a href="admin_list.adm?ord=ida">▲</a> <a href="admin_list.adm?ord=idd">▼</a></th>
	<th>이름<a href="admin_list.adm?ord=namea">▲</a> <a href="admin_list.adm?ord=named">▼</a></th>
	<th>등록일<a href="admin_list.adm?ord=datea">▲</a> <a href="admin_list.adm?ord=dated">▼</a></th>
	<!-- <th>최근활동일</th><th>최근활동</th> -->
	<th>상태<a href="admin_list.adm?ord=statusa">▲</a> <a href="admin_list.adm?ord=statusd">▼</a></th>
	</tr>
<%
for(int i = 0; i < admList.size(); i++){
%>
	<tr>
	<td><%=admList.get(i).getAl_id() %></td><td><%=admList.get(i).getAl_name() %></td>
	<td><%=admList.get(i).getAl_date().substring(0, 11).replace('-', '.')%></td>
	<!--<td></td><td></td> -->
	<td>
		<select name="status">
			<option value="a" <%if (admList.get(i).getAl_status().equals("b")) { %> selected="selected" <%} %>>사용중</option>
			<option value="b" <%if (admList.get(i).getAl_status().equals("a")) { %> selected="selected" <%} %>>미사용</option>
		</select>
	</td>
	</tr>
<%} %>
	</table>
</div>
<div id="saveStatus"><input type="submit" value="상태변경 저장" /></div>
</form>
<div id="paging">
<table width="800" cellpadding="5">
<tr>
<td width="*">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("<<&nbsp;&nbsp;<&nbsp;&nbsp;");
	} else {
		out.print("<a href='pdt_list.pdta?cpage=1" + schArgs + "'>");
		out.println("<<</a>&nbsp;&nbsp;");
		out.print("<a href='pdt_list.pdta?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("<</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='pdt_list.pdta?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;>&nbsp;&nbsp;>>");
	} else {
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + (cpage + 1) + schArgs + "'>");
		out.println("></a>");
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + pcnt + schArgs + "'>");
		out.println(">></a>");
	}
}
%>
</td>
</tr>
</table>
</div>
</body>
</html>