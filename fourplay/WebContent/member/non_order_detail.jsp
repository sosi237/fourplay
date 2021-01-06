<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="vo.*" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
OrdListInfo ordInfo = (OrdListInfo)request.getAttribute("ordInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 
<link href="css/reset.css" type="text/css" rel="stylesheet" />
<link href="css/base.css" type="text/css" rel="stylesheet" />
 -->
</head>
<body>
<h2>비회원 ORDER DETAIL</h2>
<div id="wrapper">
<div>
<table cellpadding="5" border="0">
<tr><th colspan="2">주문정보</th><th colspan="2">결제정보</th></tr>
<tr>
<th>주문번호</th><td><%=ordInfo.getOl_id() %></td>
<th rowspan="2">총 결제금액</th><td rowspan="2"><%=ordInfo.getOl_pay() %></td>
</tr>
<tr><th>주문일자</th><td><%=ordInfo.getOl_date() %></td>
</tr>
<tr><th>주문자</th><td><%=ordInfo.getOl_bname() %></td>
<th rowspan="2">결제수단</th>
<td rowspan="2">
<%
switch(ordInfo.getOl_payment()){
case "a": 	out.print("카드");		break;
case "b": 	out.print("휴대폰");		break;
case "c": 	out.print("계좌이체");		break;
case "d": 	out.print("무통장입금");	break;
}
 %>
</td>
</tr>
<tr><th>주문처리상태</th>
<td>
<% switch(ordInfo.getOl_status()){
	case "a": 	out.print("입금 전<br />계좌번호: 국민은행<br />6131802-01473-365(김현수)");		break;
	case "b": 	out.print("입금 확인");		break;
	case "c": 	out.print("상품준비중");	break;
	case "d": 	out.print("배송중<br/>한진택배: 419079564046");		break;
	case "e": case "k": 		out.print("배송완료");				break;
	case "f": 	out.print("교환요청");		break;
	case "g": 	out.print("교환완료");		break;
	case "h": 	out.print("환불요청");		break;
	case "i": 	out.print("환불완료");		break;
	case "j": 	out.print("취소");		break;
}
%>
</td>
</tr>
</table>
</div>
<div>
<table>
<tr><th colspan="6">주문 상품 정보</th></tr>
<tr><th>사진</th><th>상품정보</th><th>수량</th><th>가격</th><th>주문처리상태</th><th>취소/교환/반품</th></tr>
<%
for (int i = 0; i < ordInfo.getOrdDetailList().size(); i++){
%>
<tr>
<td><img src='/fourplay/product/pdt_img/<%=ordInfo.getOrdDetailList().get(i).getPl_img1() %>' width="50" /></td>
<td><%=ordInfo.getOrdDetailList().get(i).getPl_name() + "<br />옵션: " 
+ ordInfo.getOrdDetailList().get(i).getOd_opt().replace(':', ',') %></td>
<td><%=ordInfo.getOrdDetailList().get(i).getOd_cnt() %></td>
<td><%=ordInfo.getOrdDetailList().get(i).getOd_price() %></td>
<td>
<% switch(ordInfo.getOl_status()){
	case "a": 	out.print("입금 전<br />계좌번호: 국민은행<br />6131802-01473-365(김현수)");		break;
	case "b": 	out.print("입금 확인");		break;
	case "c": 	out.print("상품준비중");	break;
	case "d": 	out.print("배송중<br/>한진택배: 419079564046");		break;
	case "e": 	out.print("배송완료");		break;
	case "f": 	out.print("교환요청");		break;
	case "g": 	out.print("교환완료");		break;
	case "h": 	out.print("환불요청");		break;
	case "i": 	out.print("환불완료");		break;
	case "j": 	out.print("취소");		break;
}%> 
</td>
<td>
<% switch(ordInfo.getOl_status()){
	case "a": case "b": case"c": 	out.print("<input type='button' value='주문취소' onclick='ordCancel("+ ordInfo.getOl_id()+");'/>");		break;
	case "d": case "g":	out.print("<input type='button' value='교환/반품' onclick=''/>");					break;
	case "e":	out.print("<input type='button' value='구매후기' onclick='location.href=\"product_detail.jsp\" '/>");							break;
	case "f": 	out.print("교환요청");		break;
	case "h": 	out.print("환불요청");		break;
	case "i": 	out.print("환불완료");		break;
	case "j": 	out.print("취소완료");		break;
}
%>
</td>
</tr>
<%} %>
</table>
</div>
<div>
<table>
<tr><th colspan="6">배송 정보</th></tr>
<tr><th>받으시는 분</th><td><%=ordInfo.getOl_rname() %></td>
<tr><th>우편번호</th><td><%=ordInfo.getOl_rzip() %></td>
<tr><th>주소</th><td><%=ordInfo.getOl_raddr1() %></td>
<tr><th>전화</th><td><%=ordInfo.getOl_raddr2() %></td>
<tr><th>송장번호</th><td></td>
</table>
</div>
</div>
</body>
</html>