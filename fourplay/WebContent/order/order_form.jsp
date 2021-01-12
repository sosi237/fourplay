<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#kTitle {color:#8e8e8e; font-size:17px; }
#head {background-color:lightgray; width:100%; height:190px; line-height:170px; margin:5px auto; text-align:center;}
#head .hImg{vertical-align:middle; margin-right:10px;}
#ordTitle {background-color:lightgray; list-style:none;}
#ordTitle li {list-style-type:none; }
</style>
</head>
<body>
<h2>MY ORDER <span id="kTitle"> 주문서 작성</span></h2>
<div id="head">
	<img src="../images/shopping-cart.png" name="cartImg" class="hImg" width="128"/>
	<img src="../images/checklist.png" name="ordFormImg" class="hImg" width="128"/>
	<img src="../images/computer-mouse.png" name="ordChkImg" class="hImg" width="128"/>
	<img src="../images/delivery-complete.png" name="ordDoneImg" class="hImg" width="128"/><br />
</div>
<br />
<div id="ordList">
	<ul id="ordTitle">
		<li>상품명/색상, 사이즈</li>
		<li>수량</li>
		<li>가격</li>
		<li>적립 마일리지</li>
		<li>배송비</li>
	</ul>
	<ul id="ordContent">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
</div>
<form name="frmOrd" action="ord_proc.ord" method="post">
<h3>01 주문하시는 분</h3>
<div id="bInfo">
	<ul id="bTitle">
		<li>이름</li>
		<li>연락처</li>
		<li>이메일</li>
		<li>주소</li>
	</ul>
	<ul id="bContent">
		<li><input type="text" name="bname" id="bname"/></li>
		<li>
			<select name="bphone">
				<option value="010">010</option>
				<option value="016">016</option>
				<option value="02">02</option>
				<option value="070">070</option>
			</select> -
			<input type="text" name="bp2" /> -
			<input type="text" name="bp3" />
		</li>
		<li>
			<input type="text" name="be1" /> @ <input type="text" name="be2" />
			<select name="bemail">
				<option value="naver.com">naver.com</option>
				<option value="gmail.com">gmail.com</option>
				<option value="hanmail.net">hanmail.net</option>
				<option value="nate.com">nate.com</option>
				<option value="직접입력">직접입력</option>
			</select>
		</li>
		<li>
			<input type="text" name="bzip" />&nbsp;<input type="button" value="우편번호 검색" /><br />
			<input type="text" name="baddr1" /><br /><input type="text" name="baddr2" />
		</li>
	</ul>
</div>
<br />
<h3>02 상품 받으시는 분</h3><input type="checkbox" name="showInfo" /> 위 정보와 같음
<div id="rInfo">
	<ul id="rTitle">
		<li>이름</li>
		<li>배송지 선택</li>
		<li>주소</li>
		<li>연락처</li>
		<li>배송시 요청사항</li>
	</ul>
	<ul id="rContent">
		<li><input type="text" name="rname" id="rname"/></li>
		<li>
			<input type="text" name="be1" /> @ <input type="text" name="be2" />
			<select name="bemail">
				<option value="naver.com">naver.com</option>
				<option value="gmail.com">gmail.com</option>
				<option value="hanmail.net">hanmail.net</option>
				<option value="nate.com">nate.com</option>
				<option value="직접입력">직접입력</option>
			</select>
		</li>
		<li>
			<input type="text" name="bzip" />&nbsp;<input type="button" value="우편번호 검색" /><br />
			<input type="text" name="baddr1" /><br /><input type="text" name="baddr2" />
		</li>
		<li>
			<select name="rphone">
				<option value="010">010</option>
				<option value="016">016</option>
				<option value="02">02</option>
				<option value="070">070</option>
			</select> -
			<input type="text" name="rp2" /> -
			<input type="text" name="rp3" />
		</li>
		<li>
			<textarea name="delMemo" cols="50" rows="5"></textarea>
		</li>
	</ul>
</div>
<br />
<h3>03 결제 예정 금액</h3>
<div id="payList">
	<ul id="payTitle">
		<li>상품명/색상, 사이즈</li>
		<li>수량</li>
		<li>가격</li>
		<li>적립 마일리지</li>
		<li>배송비</li>
	</ul>
	<ul id="payContent">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
	<ul id="point">
		<li>마일리지</li>
		<li><input type="text" name="pnt" /><input type="checkbox" name="useAll" /> 전액사용(사용가능 적립금: )
		<br /><input type="button" value="적용" id="usePnt" /><span id="pntMsg"></span>
	</ul>
</div>
<h3>04 결제정보</h3>
<div id="payment">
	<ul>
		<li>결제 방법</li>(a:카드,b:휴대폰,c:계좌이체,d:무통장)
		<li>
			<input type="radio" name="payment" value="a" />카드 결제<br />
			<input type="radio" name="payment" value="a" />계좌이체<br />
			<input type="radio" name="payment" value="a" />휴대폰 결제<br />
			<input type="radio" name="payment" value="a" />무통장 입금
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
</body>
</html>