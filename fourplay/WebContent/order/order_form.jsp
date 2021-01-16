<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#wrapper {width:100%; position:absolute; top:300px;}
#head, #ordList, #bInfo, #rInfo, #payList, #payment {border:1px solid lightgray;}
#kTitle {color:#8e8e8e; font-size:17px; }
#head {background-color:lightgray; width:100%; height:190px; line-height:170px; margin:5px auto; text-align:center;}
#head .hImg{vertical-align:middle; margin-right:10px;}
ul {list-style:none;}
li {list-style-type:none; }
.total {text-align:right;}
</style>
<script src="jquery-3.5.1.js"></script>
<script>
$(document).ready(function (){
	$("#sameChk").click(function(){
		if($("#sameChk").is(":checked") == false){
			frmOrd.rname.value = "";   	frmOrd.rp1.value = "010";   frmOrd.rp2.value = "";
			frmOrd.rp3.value = "";      frmOrd.re1.value = "";      frmOrd.re2.value = "";
			frmOrd.rzip.value = "";   	frmOrd.raddr1.value = "";   frmOrd.raddr2.value = "";
	   } else{
	   		frmOrd.rname.value = frmOrd.bname.value;   	frmOrd.rp1.value = frmOrd.bp1.value;
		    frmOrd.rp2.value = frmOrd.bp2.value;      	frmOrd.rp3.value = frmOrd.bp3.value;
		    frmOrd.rzip.value = frmOrd.bzip.value;   	frmOrd.raddr1.value = frmOrd.baddr1.value;
		    frmOrd.raddr2.value = frmOrd.baddr2.value;
		}
	});
});
</script>
</head>
<body>
<%@ include file="../menu.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
MemberInfo addrInfo = (MemberInfo)request.getAttribute("addrInfo");
ArrayList<CartInfo> pdtList = (ArrayList<CartInfo>)request.getAttribute("pdtList");

String name = "", p1 = "", p2 = "", p3 = "", e1 = "", e2 = "", zip = "", addr1 = "", addr2 = "";
if (loginMember != null) {	// 로그인 한 회원일 경우
	name = loginMember.getMlname();
	String[] arrPhone = loginMember.getMlphone().split("-");
	p1 = arrPhone[0];	p2 = arrPhone[1];	p3 = arrPhone[2];
	String[] arrEmail = loginMember.getMlemail().split("@");
	e1 = arrEmail[0];	e2 = arrEmail[1];
	if(addrInfo != null){	//기본 배송지가 설정되어 있는 회원이면
		zip = addrInfo.getMazip();
		addr1 = addrInfo.getMaaddr1();
		addr2 = addrInfo.getMaaddr2();
	}
}
%>
<div id="wrapper">
<h2>MY ORDER <span id="kTitle"> 주문서 작성</span></h2>
<div id="head">
	<img src="images/shopping_cart.png" name="cartImg" class="hImg" width="128"/>
	<img src="images/checklist.png" name="ordFormImg" class="hImg" width="128"/>
	<img src="images/computer_mouse.png" name="ordChkImg" class="hImg" width="128"/>
	<img src="images/delivery_complete.png" name="ordDoneImg" class="hImg" width="128"/><br />
</div>
<br />
<form name="frmOrd" action="ord_proc.ord" method="post">
<div id="ordList">
	<ul id="ordTitle">
		<li>상품명/색상, 사이즈</li>
		<li>수량</li>
		<li>가격</li>
		<li>적립 마일리지</li>
		<li>배송비</li>
	</ul><br />
	<ul id="ordContent">
<%
int total = 0;	// 총 구매가격을 저장할 변수
if (pdtList != null && pdtList.size() > 0) {	// 구매할 상품이 있으면
	int seq = 0;
	for (int i = 0 ; i < pdtList.size() ; i++) {
		seq++;
		CartInfo crt = pdtList.get(i);

		String option = "해당 상품은 옵션이 없음";
		String opts = crt.getPl_opt();	// 상품이 가지는 옵션
		String opt = crt.getCl_opt();	// 사용자가 선택한 옵션
		if (opts != null && !opts.equals("")) {		// 해당 상품에 옵션이 있으면
			String[] arrOpt = opts.split(":");		// 옵션의 종류를 배열로 생성
			String[] arrChoose = opt.split(",");	// 선택한 옵션값을 배열로 생성
			option = "";
			for (int j = 0 ; j < arrOpt.length ; j++) {
				String[] arrTmp = arrOpt[j].split(",");
				option += " / " + arrTmp[0] + " : " + arrChoose[j];
			}
			option = option.substring(3);
		}
%>
		<li><%=seq %><img src="/fourplay/product/pdt_img/<%=crt.getPl_img1() %>" width="50" height="50" align="absmiddle" style="margin:2px 0;" />
		<%=crt.getPl_name()  + " / " + option + " / " + crt.getCl_cnt() + " / " + crt.getPrice() %></li>
<%
		total += crt.getPrice();
	}
} else {	// 구매할 상품이 없으면
%>
<script>
alert("잘못된 경로로 들어오셨습니다.");
history.back();
</script>
<%
}
%>
		<li class="total">총 <%=total %> 원</li>
	</ul>
</div>
<h3>01 주문하시는 분</h3>
<div id="bInfo">
<table>
<tr><th>이름</th><td><input type="text" name="bname" id="bname" value="<%=name%>"/></td></tr>
<tr><th>연락처</th>
<td>
<select name="bp1">
	<option value="010" <%if(p1.equals("010")) { %> selected="selected" <%} %>>010</option>
	<option value="016" <%if(p1.equals("016")) { %> selected="selected" <%} %>>016</option>
	<option value="02" <%if(p1.equals("02")) { %> selected="selected" <%} %>>02</option>
	<option value="070" <%if(p1.equals("070")) { %> selected="selected" <%} %>>070</option>
</select> -
<input type="text" name="bp2" value="<%=p2%>"/> -
<input type="text" name="bp3" value="<%=p3%>"/>
</td>
</tr>
<tr><th>이메일</th>
<td>
<input type="text" name="be1" value="<%=e1%>"/> @ <input type="text" name="be2" value="<%=e2%>"/>
<select name="bemail">
	<option value="naver.com" <%if(e2.equals("naver.com")) { %> selected="selected" <%} %>>naver.com</option>
	<option value="gmail.com" <%if(e2.equals("gmail.com")) { %> selected="selected" <%} %>>gmail.com</option>
	<option value="hanmail.net" <%if(e2.equals("hanmail.net")) { %> selected="selected" <%} %>>hanmail.net</option>
	<option value="nate.com" <%if(e2.equals("nate.com")) { %> selected="selected" <%} %>>nate.com</option>
	<option value="직접입력">직접입력</option>
</select>
</td>
</tr>
<tr><th>주소</th>
<td>
<input type="text" name="bzip"  value="<%=zip%>"/>&nbsp;<input type="button" name="schBZip" value="우편번호 검색" /><br />
<input type="text" name="baddr1"  value="<%=addr1%>"/>
<br /><input type="text" name="baddr2"  value="<%=addr2%>"/>
</td>
</tr>
</table>
</div>
<br />
<h3>02 상품 받으시는 분</h3><input type="checkbox" name="sameChk" id="sameChk"/> 위 정보와 같음
<div id="rInfo">
<table>
<tr><th>이름</th><td><input type="text" name="rname" id="rname"/></td></tr>
<tr><th>연락처</th>
<td>
<select name="rp1">
	<option value="010">010</option>
	<option value="016">016</option>
	<option value="02">02</option>
	<option value="070">070</option>
</select> -
<input type="text" name="rp2" /> -
<input type="text" name="rp3" />
</td>
</tr>
<tr><th>배송지 선택</th>
<td>
<input type="button" name="addrList" value="배송지 목록"/>&nbsp;
<input type="checkbox" name="newAddr" value="신규배송지"/> 신규 배송지
</td>
</tr>
<tr><th>주소</th>
<td>
<input type="text" name="rzip" />&nbsp;<input type="button" name="schRZip" value="우편번호 검색" /><br />
<input type="text" name="raddr1" /><br /><input type="text" name="raddr2" />
</td>
</tr>
<tr><th>배송시 요청사항</th><td><textarea name="delMemo" cols="50" rows="5"></textarea></td></tr>
</table>
</div>
<br />
<h3>03 결제 예정 금액</h3>
<div id="payList">
<table id="t_payList" cellspacing="0" cellpadding="5">
<tr><th>총 주문금액</th><th>배송비</th><th>할인금액</th><th>결제 예정 금액</th></tr>
<tr align="center"><td></td><td></td><td></td><td><%=total %></td></tr>
<tr><th>마일리지</th>
<td colspan="3"><input type="text" name="pnt" /><input type="checkbox" name="useAll" /> 전액사용(사용가능 적립금: )
<br /><input type="button" value="적용" id="usePnt" /><span id="pntMsg"></span>
</td>
</tr>
</table>
</div>
<h3>04 결제정보</h3>
<div id="payment">
	<ul>
		<li>결제 방법</li> 
		<li>
			<input type="radio" name="payment" value="a" />카드 결제<br />
			<input type="radio" name="payment" value="b" />휴대폰 결제<br />
			<input type="radio" name="payment" value="c" />계좌이체<br />
			<input type="radio" name="payment" value="d" />무통장 입금
			<select name="account">
				<option value="">입금 계좌번호 선택</option>
			</select>
		</li>
	</ul>
</div>
<div id="btn">
	<input type="submit" value="결제하기">&nbsp;&nbsp;<input type="button" value="취소" />
</div>
</form>
</div>
</body>
</html>