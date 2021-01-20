<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ include file="../a_menu.jsp" %>
<%
ArrayList<OrdListInfo> ordList = (ArrayList<OrdListInfo>)request.getAttribute("ordList");
OrdPageInfo pageInfo = (OrdPageInfo)request.getAttribute("pageInfo");
int cpage	= pageInfo.getCpage();	// 현재 페이지 번호
int pcnt	= pageInfo.getPcnt();	// 전체 페이지 수
int psize	= pageInfo.getPsize();	// 페이지 크기
int bsize	= pageInfo.getBsize();	// 블록 페이지 개수
int spage	= pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage	= pageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt	= pageInfo.getRcnt();	// 검색된 게시물 개수

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#wrapper table {}
.pointer {cursor:pointer;}
</style>
<script>
function openDetail(olid){
	var win = window.open("ord_detail.orda?olid="+olid, "a_order_detail.jsp", "width=900, height=1200");
	
}
</script>
</head>
<body>
<div id="wrapper">
<%
//"order/a_order_detail.jsp?olid="+ olid 
if(ordList != null) out.println("ordList not null");
if(pageInfo != null) out.println("pageInfo not null");
%>
	<h3>주문 목록</h3>
	<table width="100%">
	<tr>
	<th>주문번호</th><th>주문자</th><th>상품명</th><th>주문금액</th>
	<th>주문일</th><th>결제방법</th><th>주문상태</th>
<!-- <th>취소/반품/교환 사유</th>  -->
	</tr>
<%
if(ordList != null && rcnt > 0){
	for(int i = 0; i < ordList.size(); i++){
%>
	<tr>
	<td><span class="pointer" onclick="openDetail(<%=ordList.get(i).getOl_id() %>);"><%=ordList.get(i).getOl_id() %></span></td>
	<td><%=(ordList.get(i).getOl_buyer().length() > 20) ? "비회원" : ordList.get(i).getOl_buyer() %></td>
	<td>
<%
		for(int j = 0; j < ordList.get(i).getOrdDetailList().size(); j++){
			String plid = ordList.get(i).getOrdDetailList().get(j).getPl_id();
			String plname = ordList.get(i).getOrdDetailList().get(j).getPl_name();
			out.println("<a href='../pdt_view.pdt?id="+plid+"'>" + plname + "</a><br />");
		}
%>
	</td>
	<td><%=ordList.get(i).getOl_pay() %></td>
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
<%
switch(ordList.get(i).getOl_status()){
	case "a": 				out.print("입금 전<br />계좌번호: 국민은행<br />6131802-01473-365(김현수)");		break;
	case "b": 				out.print("입금 확인");								break;
	case "c": 				out.print("상품준비중");							break;
	case "d": 				out.print("배송중<br/>한진택배: 419079564046");		break;
	case "e": case "k":		out.print("배송완료");								break;
	case "f": 				out.print("교환요청");								break;
	case "g": 				out.print("교환완료");								break;
	case "h": 				out.print("환불요청");								break;
	case "i": 				out.print("환불완료");								break;
	case "j": 				out.print("취소");								break;
}
%>
	</td>
<!--<td>
<%
switch(ordList.get(i).getOl_status()){
	case "f": case "g": case "h": case "i": case "j": 
%>
	<input type="button" value="취소/반품/교환 사유" onclick=""/>
<%
	break;
}
%>
	</td>
 -->	
	</tr>	
<%
	}
} else{
	out.println("<tr align='center'><td colspan='8'>주문 내역이 없습니다.</td></tr>");
}
%>
	</table>
	<div class="paging">
	<table width="100%" cellpadding="5">
	<tr><td align="center">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("<<&nbsp;&nbsp;<&nbsp;&nbsp;");
	} else {
		out.print("<a href='ord_list.orda?cpage=1'>");
		out.println("<<</a>&nbsp;&nbsp;");
		out.print("<a href='ord_list.orda?cpage=" + (cpage - 1) +  "'>");
		out.println("<</a>&nbsp;&nbsp;");
	}
	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='ord_list.orda?cpage=" + j +  "'>");
			out.println(j + "</a>&nbsp;");
		}
	}
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;>&nbsp;&nbsp;>>");
	} else {
		out.print("&nbsp;&nbsp;<a href='ord_list.orda?cpage=" + (cpage + 1)  + "'>");
		out.println("></a>");
		out.print("&nbsp;&nbsp;<a href='ord_list.orda?cpage=" + pcnt  + "'>");
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