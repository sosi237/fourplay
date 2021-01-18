<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ include file="../menu.jsp" %>
<%
String ismember = request.getParameter("ismember");
if(ismember == null)	ismember = "";	// 비회원 구매하기로 페이지에 들어왔는지 여부를 담는 변수 

String kind = request.getParameter("kind");	// 장바구니(cart), 바로구매(direct) 중 어디를 통해 들어왔는지 여부
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
ul {list-style:none;}
li {list-style-type:none; }
#head, #ordList, #bInfo, #rInfo, #payList, #payment {
	width:100%; border:1px solid lightgray; 
}
#kTitle {color:#8e8e8e; font-size:17px; }
#head {background-color:#d1d1d1; width:100%; height:190px; line-height:170px; margin:5px auto; text-align:center;}
#head .hImg{vertical-align:middle; margin-right:10px;}

#ordList {}
#ordList #ordTitle {width:100%; height:50px; background-color:#d1d1d1; border-top:2px solid darkgray;}
#ordList #ordTitle li { width:11%; float:left; padding:10px; text-align:center;}
#ordList #ordTitle li:first-child { width:20%; margin-left:200px; margin-right:55px;}
#ordList #ordTitle .cnt{ width:6%;margin-right:20px; }
#ordList #ordTitle .point{ margin-left:20px; }
#ordList #ordTitle .deli{ width:6%; margin-left:20px; }

#ordList .ordContent  {width:100%; height:80px; border:1px solid white; font-size:14px;}
#ordList .ordContent li {display:block; text-align:center; width:5%; float:left; margin:30px 10px 0 10px;}
#ordList .ordContent li:first-child { width:2%; margin: 30px 17px; }
#ordList .ordContent .pdtName {text-align:left; width:35%; }
#ordList .ordContent .price {margin-left:80px; margin-right:65px; color:red; font-weight:bold; }
#ordList .ordContent .point {margin-right:70px;}
#ordList .ordContent .pdtImg {margin:10px; vertical-align:center;}

#wrapper table {width:100%;}
.title { 
	width:150px; background-color:#d1d1d1; 
	border-left:2px solid darkgray; border-bottom:1px solid darkgray; 
	text-align:left;
	}
.tPrice {display:block; height:30px; text-align:right; margin-right:10px; font-weight:bold; }

#btn {display:block; width:100%; margin-top:30px; text-align:center;}
.sBtn, .rBtn {display:inline;width:100px; height:35px; color:white; margin: 30px auto; }
.sBtn {width:100px; height:35px; background-color:black; margin-right:30px; }
.rBtn {width:100px; height:35px; background-color:lightgray; border:0;}
h3 { margin-top:30px; margin-bottom:15px;}
</style>
<script src="jquery-3.5.1.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js?autoload=false"></script>
<script>
$(document).ready(function (){
	$("#sameChk").click(function(){
		if($("#sameChk").is(":checked") == false){	// '위 정보와 같음' 체크를 해제하면
			frmOrd.rname.value = "";   	frmOrd.rp1.value = "010";   frmOrd.rp2.value = "";
			frmOrd.rp3.value = "";      frmOrd.rzip.value = "";   	frmOrd.raddr1.value = "";   frmOrd.raddr2.value = "";
			$("#newAddr").attr("checked", true);
	   } else{	// '위 정보와 같음' 체크하면
	   		frmOrd.rname.value = frmOrd.bname.value;   	frmOrd.rp1.value = frmOrd.bp1.value;
		    frmOrd.rp2.value = frmOrd.bp2.value;      	frmOrd.rp3.value = frmOrd.bp3.value;
		    frmOrd.rzip.value = frmOrd.bzip.value;   	frmOrd.raddr1.value = frmOrd.baddr1.value;
		    frmOrd.raddr2.value = frmOrd.baddr2.value;
		    $("#newAddr").removeAttr("checked");
		}
	});
	
	$("#newAddr").click(function(){
		if($("#newAddr").is(":checked") == true){	//신규 배송지 체크박스를 체크하면
			document.getElementById("rzip").value = "";
			document.getElementById("raddr1").value = "";
			document.getElementById("raddr2").value = "";
		}
	});
	
});
/* 우편번호 찾기 기능 - 다음 API 활용 */
function getZip(type) {
	daum.postcode.load(function(){
	    new daum.Postcode({
	        oncomplete: function(data) {
	        	var addr = "";
	        	// 사용자가 선택한 주소 타입에 따라 해당 주소 가져옴
	        	if(data.userSelectedType === "R"){	// 도로명 주소를 선택했을 경우
	        		addr = data.roadAddress;
	        	} else {	// 지번 주소를 선택했을 경우
	        		addr = data.jibunAddress;
	        	}
	        	if(type == "b"){
		        	document.getElementById("bzip").value = data.zonecode;
		        	document.getElementById("baddr1").value = addr;
		        	document.getElementById("baddr2").value = "";
		        	document.getElementById("baddr2").focus();
	        	} else {
	        		document.getElementById("rzip").value = data.zonecode;
		        	document.getElementById("raddr1").value = addr;
		        	document.getElementById("raddr2").value = "";
		        	document.getElementById("raddr2").focus();
	        	}
	        }
	    }).open();
	});
}

</script>
</head>
<body>
<div id="wrapper">
	<h2>MY ORDER <span id="kTitle"> 주문서 작성</span></h2>
	<div id="head">
		<img src="images/CARTLIST.png" name="cartImg" class="hImg" width="128"/>
		<img src="images/checklist.png" name="ordFormImg" class="hImg" width="128" alt="주문서 작성" 
			style="padding:8px; border:6px solid darkgray;"/>
		<img src="images/computer_mouse.png" name="ordChkImg" class="hImg" width="128"/>
		<img src="images/delivery_complete.png" name="ordDoneImg" class="hImg" width="128"/><br />
	</div>
	<br />
	<form name="frmOrd" action="ord_proc.ord" method="post">
	<input type="hidden" name="ismember" value="<%=ismember %>" />
	<input type="hidden" name="pdtList" value="<%=pdtList %>" />
	<div id="ordList">
		<ul id="ordTitle">
			<li>상품명</li>
			<li class="cnt">수량</li>
			<li>가격</li>
			<li class="point">적립 마일리지</li>
			<li class="deli">배송비</li>
		</ul>
	<%
	int tPrice = 0, beforeDC = 0;	
	// tPrice: 총 구매가격(할인 후), beforeDC: 할인전 총가격
	if (pdtList != null && pdtList.size() > 0) {	// 구매할 상품이 있으면
		int seq = 0;
		for (int i = 0 ; i < pdtList.size() ; i++) {
			seq++;
			CartInfo crt = pdtList.get(i);
			
			String pnt = Math.floor(crt.getPrice() * 0.0001) * 100 +"";
			pnt = pnt.substring(0, pnt.length() -2);
			int point = Integer.valueOf(pnt);
			
			String option = "옵션 없음";
			String opts = crt.getPl_opt();				// 상품이 가지는 옵션
			String opt = crt.getCl_opt();				// 사용자가 선택한 옵션
			if (opts != null && !opts.equals("")) {		// 해당 상품에 옵션이 있으면
				String[] arrOpt = opts.split(":");		// 옵션의 종류를 배열로 생성
				String[] arrChoose = opt.split(",");	// 선택한 옵션값을 배열로 생성
				option = "";
				for (int j = 0 ; j < arrOpt.length ; j++) {
					String[] arrTmp = arrOpt[j].split(",");
					option += " / " + arrTmp[0] + " : " + arrChoose[j];
				}
				option = option.substring(3).replace(" /", ", ").replace(" :", ":");
				option = option.replace("Size", "사이즈").replace("Width", "발볼").replace("Heel", "속굽");
			}
	%>
	
		<ol class="ordContent">
			<li><%=seq %></li>
			<li class="pdtImg">
				<img src="/fourplay/product/pdt_img/<%=crt.getPl_img1() %>" width="50" height="50" align="absmiddle" style="margin:2px 0;" />
			</li>
			<li class="pdtName"><%=crt.getPl_name()  + "<br /> 옵션/ " + option %></li>
			<li><%=crt.getCl_cnt() %></li>
			<li class="price"><%=crt.getPrice() %></li>
			<li class="point"><%=point %></li>
			<li>0</li>
		</ol>
	<%
			beforeDC += crt.getPl_price();
			tPrice += crt.getPrice();
			System.out.println("beforeDC: "+beforeDC +" / tPrice: " + tPrice);
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
			<span class="tPrice">총 <%=tPrice %> 원</span>
	</div>
	<h3>01 주문하시는 분</h3>
	<div id="bInfo">
	<table cellspacing="0">
	<tr><th class="title">이름</th><td><input type="text" name="bname" id="bname" value="<%=name%>"/></td></tr>
	<tr><th class="title">연락처</th>
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
	<tr><th class="title">이메일</th>
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
	<tr><th class="title">주소</th>
	<td>
	<input type="text" id="bzip" name="bzip"  value="<%=zip%>" placeholder="우편번호" />
	<input type="button" onclick="getZip('b')" value="우편번호 찾기" /><br>
	<input type="text" id="baddr1" name="baddr1" value="<%=addr1%>" placeholder="주소" /><br />
	<input type="text" id="baddr2" name="baddr2" value="<%=addr2%>" placeholder="상세주소" />
	</td>
	</tr>
	</table>
	</div>
	
	<h3>02 상품 받으시는 분</h3><input type="checkbox" name="sameChk" id="sameChk"/> 위 정보와 같음
	<div id="rInfo">
	<table cellspacing="0">
	<tr><th class="title">이름</th><td><input type="text" name="rname" id="rname"/></td></tr>
	<tr><th class="title">연락처</th>
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
	<tr><th class="title">배송지 선택</th>
	<td>
	<input type="button" name="addrList" value="배송지 목록"/>&nbsp;
	<input type="checkbox" name="newAddr" id="newAddr" value="신규배송지" checked="checked"/> 신규 배송지
	</td>
	</tr>
	<tr><th class="title">주소</th>
	<td>
	<input type="text" name="rzip" id="rzip" placeholder="우편번호" />
	<input type="button" onclick="getZip('r')" value="우편번호 찾기" /><br>
	<input type="text" name="raddr1" id="raddr1" placeholder="주소" /><br />
	<input type="text" name="raddr2" id="raddr2" placeholder="상세주소" />
	</td>
	</tr>
	<tr><th class="title">배송시 요청사항</th><td><textarea name="delMemo" cols="50" rows="5"></textarea></td></tr>
	</table>
	</div>
	
	<h3>03 결제 예정 금액</h3>
	<div id="payList">
	<table id="t_payList" cellspacing="0" cellpadding="5">
	<tr class="title"><th>총 주문금액</th><th></th><th>배송비</th><th></th><th>할인금액</th><th></th><th>결제 예정 금액</th></tr>
	<tr align="center">
		<td><%=beforeDC %></td>
		<td><img src="images/plus.png" width="45" alt="plus"/></td>
		<td>0</td>
		<td><img src="images/minus.png"  width="45" alt="minus"/></td>
		<td><%=beforeDC - tPrice %></td>
		<td><img src="images/equals.png"  width="35" alt="equals"/></td>
		<td><%=tPrice %></td>
	</tr>
	<tr>
	<th>마일리지</th>
	<td colspan="3"><input type="text" name="pnt" />
	<input type="checkbox" name="useAll" /> 전액사용(사용가능 적립금: )
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
				<input type="radio" name="payment" value="a" checked="checked" />카드 결제<br />
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
		<input type="submit" class="sBtn" value="결제하기">&nbsp;&nbsp;
		<input type="button" class="rBtn" value="취소" onclick="location.href='cart_list.crt';"/>
	</div>
	</form>
</div>
<br /><br /><br /><br /><br /><br />
</body>
</html>