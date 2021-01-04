<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<OrdListInfo> ordList = (ArrayList<OrdListInfo>)request.getAttribute("ordList");
OrdPageInfo ordPageInfo = (OrdPageInfo)request.getAttribute("ordPageInfo");

int cpage	= ordPageInfo.getCpage();	// 현재 페이지 번호
int pcnt	= ordPageInfo.getPcnt();	// 전체 페이지 수
int psize	= ordPageInfo.getPsize();	// 페이지 크기
int bsize	= ordPageInfo.getBsize();	// 블록 페이지 개수
int spage	= ordPageInfo.getSpage();	// 블록 시작 페이지 번호
int epage	= ordPageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt	= ordPageInfo.getRcnt();	// 검색된 게시물 개수
/*
for (int i = 0; i < ordList.size(); i++){
	for (int j = 0; j < ordList.get(i).getOrdDetailList().size(); j++){
		System.out.println(ordList.get(i).getOrdDetailList().get(j).getPl_id() );
	}
}
//2101pdt101
//3102pdt101
//2101pdt110
*/
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
</style>
</head>
<body>
<h2>ORDER LIST</h2>
<div id="wrapper">
<table width="900" cellpadding="5">
	<div class="head">
		<tr><th width="15%">주문일자<br />[주문번호]</th><th width="10%">사진</th><th width="*">상품정보</th><th width="5%">수량</th>
		<th width="8%">가격</th><th width="10%">주문처리상태</th><th width="15%">취소/교환/반품</th></tr>
	</div>
<%for(int i = 0; i < ordList.size(); i++){ %>
	<div class="ordList">
		<tr><td><%=ordList.get(i).getOl_date().substring(0,11) %><br />[<%=ordList.get(i).getOl_id() %>]</td>
		<td><img src='/fourplay/product/pdt_img/<%=ordList.get(i).getOrdDetailList().get(0).getPl_img1() %>' width='50'/></td>
		<td>
		<%for (int j = 0; j < ordList.get(i).getOrdDetailList().size(); j++) {
			if (j == ordList.get(i).getOrdDetailList().size() -1)	out.print(ordList.get(i).getOrdDetailList().get(j).getPl_name());
			else 													out.print(ordList.get(i).getOrdDetailList().get(j).getPl_name() + ", ");
		} %>
		</td>
		<td>
		<%
		int cnt = 0;
		for (int j = 0; j < ordList.get(i).getOrdDetailList().size(); j++) {
			cnt += ordList.get(i).getOrdDetailList().get(j).getOd_cnt();
		} 
		out.print(cnt);
		%>
		</td>
		<td><%=ordList.get(i).getOl_pay() %></td>
		<td>
		<% switch(ordList.get(i).getOl_status()){
			case "a": 	out.print("입금 전");		break;
			case "b": 	out.print("입금 확인");	break;
			case "c": 	out.print("상품준비중");	break;
			case "d": 	out.print("배송중");		break;
			case "e": 	out.print("배송완료");	break;
			case "f": 	out.print("교환요청");	break;
			case "g": 	out.print("교환완료");	break;
			case "h": 	out.print("환불요청");	break;
			case "i": 	out.print("환불완료");	break;
			case "j": 	out.print("취소");		break;
		}
		%></td>
		<td>
		<% switch(ordList.get(i).getOl_status()){
			case "a": case "b": case"c": 	out.print("<input type='button' value='주문취소' onclick=''/>");		break;
			case "d": case "g":	out.print("<input type='button' value='교환/반품' onclick=''/>");					break;
			case "e":	out.print("<input type='button' value='구매후기' onclick='location.href=\"product_detail.jsp\" '/>");							break;
			case "f": 	out.print("교환요청");	break;
			case "h": 	out.print("환불요청");	break;
			case "i": 	out.print("환불완료");	break;
			case "j": 	out.print("취소완료");	break;
		}
		%>
		</td>
		</tr>
	</div>
<%} %>
</table>
</div>
</body>
</html>