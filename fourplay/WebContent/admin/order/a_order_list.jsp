<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ include file="../a_menu.jsp" %>
<%
ArrayList<OrdListInfo> ordList = (ArrayList<OrdListInfo>)request.getAttribute("ordList");
OrdPageInfo pageInfo = (OrdPageInfo)request.getAttribute("pageInfo");
DecimalFormat df = new DecimalFormat("###,###");

String status, schtype, keyword, ord;
schtype =	pageInfo.getSchtype();	// 검색조건(관리자 아이디, 관리자 이름)
keyword =	pageInfo.getKeyword();	// 검색어
status =	pageInfo.getStatus();	// 계정 사용여부
ord =		pageInfo.getOrd();		// 정렬조건

if(keyword == null)					keyword = "";
if(schtype == null)					schtype = "";

String args = "", schArgs = "";
if (status != null) {
	schArgs += "&status=" + status;		
} else {
	status = "";
}

if (!keyword.equals("")) {
	schArgs += "&schtype=" + schtype + "&keyword=" + keyword;
}
else {
	schtype = "";	keyword = "";
}

if (ord != null)		schArgs += "&ord=" + ord;			
else					ord = "";

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
#wrapper h2 {margin-bottom:20px;}
.title th {padding:20px 0; border-bottom:1px solid black; font-size:18px;}
.list td {padding:15px 3px; border-bottom:1px solid black; text-align:center;}
.list .left {text-align:left;}
.ex {color:red; font-weight:bold;}
.pointer {cursor:pointer;}

.sch {width:100%; height:40px; border:1px solid white; display:block; text-align:right; margin:10px 0;}
.schtype { width:150px;  height:25px; font-size:15px; }
.keyword { height:20px; font-size:15px;  }
.schBtn {width:70px; height:25px; background-color:darkgray; color:white; font-size:15px; align:absmiddle;}
.statusBtn {margin-right:30px; width:100px; height:30px;background-color:black; color:white;}
</style>
<script>
function openDetail(olid){
	var win = window.open("ord_detail.orda?olid="+ olid, "a_order_detail.jsp", "width=900, height=450");
}
function openPdt(plid){
	var win = window.open("pdt_view.orda?id="+ plid, "a_product_detail.jsp", "width=900, height=450");
}
function getStatus(){
	var arrVal = document.listFrm.status;
	var status = "";
	for(var i = 0; i < arrVal.length; i++){
		status += "," + arrVal[i].options[arrVal[i].selectedIndex].value;
	}
	status = status.substring(1);
	return status;
}

function chVal(){
	var frm = document.listFrm;
	var st = document.getElementById("st");
	if(confirm("주문상태값을 변경하시겠습니까?")){
		var status = getStatus();	//b,a
		st.value = status;	
		frm.submit();
	}
}
</script>
</head>
<body>
<div id="wrapper">
<%
String plid = "";
%>
	<h2>주문 목록</h2>
	<span class="ex">주문번호를 누르면 주문 상세내역을, 상품명을 누르면 상품 상세내역을 팝업창으로 확인할 수 있습니다.</span>
	<form name="schFrm" action="" method="get">
	<div class="sch">
	<ul>
		<li>
		<select name="schtype" class="schtype">
			<option value="buyer" <% if (schtype.equals("buyer")) { %>selected="selected"<% } %>>주문자 아이디</option>
			<option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>주문번호</option>
		</select>
		<input type="text" name="keyword" class="keyword" />
		<input type="submit" class="schBtn" value="검색" />
		</li>
	</ul>
	</div>
	</form>
	<form name="listFrm" action="ord_proc.orda" method="post">
	<input type="hidden" name="wtype" value="chStatus" />
	<input type="hidden" name="st" id="st" value="" />
	<table width="100%" cellspacing="0">
	<tr class="title">
	<th>주문번호<a href="ord_list.orda?ord=ida">▲</a> <a href="ord_list.orda?ord=idd">▼</a></th>
	<th>주문자<a href="ord_list.orda?ord=buyera">▲</a> <a href="ord_list.orda?ord=buyerd">▼</a></th>
	<th>상품명</th>
	<th>주문금액</th>
	<th>주문일<a href="ord_list.orda?ord=datea">▲</a> <a href="ord_list.orda?ord=dated">▼</a></th>
	<th>결제방법</th>
	<th>주문상태</th>
<!-- <th>취소/반품/교환 사유</th>  -->
	</tr>
<%
if(ordList != null && rcnt > 0){
	String idx = "", idxs = "";
	for(int i = 0; i < ordList.size(); i++){
		idx = ordList.get(i).getOl_id();
		idxs += "," + idx;
%>
	<tr class="list">
	<td><span class="pointer" onclick="openDetail(<%=ordList.get(i).getOl_id() %>);"><%=ordList.get(i).getOl_id() %></span></td>
	<td><%=(ordList.get(i).getOl_buyer().length() > 20) ? "비회원" : ordList.get(i).getOl_buyer() %></td>
	<td class="left">
<%
		for(int j = 0; j < ordList.get(i).getOrdDetailList().size(); j++){
			plid = ordList.get(i).getOrdDetailList().get(j).getPl_id();
			String plname = ordList.get(i).getOrdDetailList().get(j).getPl_name();
%>
		<span class="pointer" onclick="openPdt('<%=plid%>');"><%=plname %></span><br />
<%
		}
%>
	</td>
	<td><%=df.format(ordList.get(i).getOl_pay()) %></td>
	<td><%=ordList.get(i).getOl_date().substring(0,11).replace("-", ".") %></td>
	<td>
<%
		switch(ordList.get(i).getOl_payment()){
			case "a" : out.println("카드 결제");	break; 
			case "b" : out.println("휴대폰 결제");	break; 
			case "c" : out.println("계좌이체");	break; 
			case "d" : out.println("무통장 입금");	break; 
		}
%>	
	</td>
	<td>
<%if (ordList.get(i).getOl_status().equals("k")) { %> 
		후기작성 완료
<%} else {%>
	<select name="status" id="status">
		<option value="a" <%if (ordList.get(i).getOl_status().equals("a")) { %> selected="selected" <%} %>>입금 전</option>
		<option value="b" <%if (ordList.get(i).getOl_status().equals("b")) { %> selected="selected" <%} %>>입금 확인</option>
		<option value="c" <%if (ordList.get(i).getOl_status().equals("c")) { %> selected="selected" <%} %>>상품준비중</option>
		<option value="d" <%if (ordList.get(i).getOl_status().equals("d")) { %> selected="selected" <%} %>>배송중</option>
		<option value="e" <%if (ordList.get(i).getOl_status().equals("e")) { %> selected="selected" <%} %>>배송완료</option>
		<option value="f" <%if (ordList.get(i).getOl_status().equals("f")) { %> selected="selected" <%} %>>교환요청</option>
		<option value="g" <%if (ordList.get(i).getOl_status().equals("g")) { %> selected="selected" <%} %>>교환완료</option>
		<option value="h" <%if (ordList.get(i).getOl_status().equals("h")) { %> selected="selected" <%} %>>환불요청</option>
		<option value="i" <%if (ordList.get(i).getOl_status().equals("i")) { %> selected="selected" <%} %>>환불완료</option>
		<option value="j" <%if (ordList.get(i).getOl_status().equals("j")) { %> selected="selected" <%} %>>취소</option>
	</select>
<%} %>
	</td>
<%
	} 
	idxs = idxs.substring(1);
%>
	<input type="hidden" name="idxs" value="<%=idxs %>" />
	</tr>	
<%
} 
else {
	out.println("<tr align='center'><td colspan='8'>주문 내역이 없습니다.</td></tr>");
}
%>
	<tr><td colspan="7" style="text-align:right; padding-top:20px;">
	<input type="button" class="statusBtn" value="주문상태 변경" onclick="chVal();"/></td></tr>
	</table>
	</form>
	<div class="paging">
	<table width="100%" cellpadding="5">
	<tr><td align="center">
<%
if (ordList != null && rcnt > 0) {	// 검색결과 주문들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("<<&nbsp;&nbsp;<&nbsp;&nbsp;");
	} else {
		out.print("<a href='ord_list.orda?cpage=1"+ schArgs +"'>");
		out.println("<<</a>&nbsp;&nbsp;");
		out.print("<a href='ord_list.orda?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("<</a>&nbsp;&nbsp;");
	}
	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='ord_list.orda?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;>&nbsp;&nbsp;>>");
	} else {
		out.print("&nbsp;&nbsp;<a href='ord_list.orda?cpage=" + (cpage + 1)  + schArgs + "'>");
		out.println("></a>");
		out.print("&nbsp;&nbsp;<a href='ord_list.orda?cpage=" + pcnt + schArgs + "'>");
		out.println(">></a>");
	}
}
%>
	</td></tr>
	</table>
	</div>
</div>
</body>
</html>