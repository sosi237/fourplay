<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.page-header {
	background:#fff; position:absolute; width:100%; min-width:960px; 
	-webkit-box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
			-box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
}
.sticky {position:fixed; top:0; opacity:1;}
.no-boxshadow .page-header {border-bottom:1px solid #f2f2f2;}
#fourplay {font-size:40px; font-weight:bold; display:block; width:200px; margin:20px auto; }
.primary-nav {width:900px;font-size:18px; display:block; margin:20px auto; position:relative; cursor:pointer;}
.primary-nav th {padding-right:50px;}
.sub-nav {width:900px;font-size:18px; display:none; margin:20px auto; position:relative; vertical-align:top;}
.sub-nav th {padding-right:50px;  vertical-align:top;}

.shortcut {text-align:right;}

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:pink; text-decoration:none;  font-weight:bold;}
a:active { color:#f00; text-decoration:none; }	
a:focus { color:#f00; text-decoration:underline; }
</style>
<script src="jquery-3.5.1.js"></script>
<script>
$(document).ready(function(){
	$(".page-header").each(function(){
		var $window=$(window),	
		$header=$(this),	
		headerOffsetTop = $header.offset().top;
		$window.on('scroll', function(){
			if ($window.scrollTop() > headerOffsetTop)	{
				$header.addClass('sticky');
			}else {
				$header.removeClass('sticky');
			}
		});
		$window.trigger('scroll');
	});
	
	$(".primary-nav").mouseenter(function(){	
		$(".sub-nav").css("display", "block");
	});
	$(".sub-nav").mouseleave(function(){	
		$(".sub-nav").css("display", "none");
	});
});
</script>
</head>
<body>
<div class="shortcut">
<a href="login_form.jsp" >로그인</a> | <a href="join_form.jsp" >회원가입</a> | <a href="cart_list.crt" >장바구니</a> |
<a href="" >마이페이지</a> | <a href="order_list.mpg" >주문조회</a> | <a href="" >Q&A</a> 
</div>
<div id="logo">
<span id="fourplay"><a href="index.jsp" >fourplay</a></span>
</div>
<header class="page-header">
	<div class="primary-nav" >
		<table width="900" border="0" cellpadding="15px;">
		<tr>
		<th class="bigCatetory">BEST</th>
		<th class="bigCatetory">DRESS SHOES</th>
		<th class="bigCatetory">LOAFER</th>
		<th class="bigCatetory">BOOTS</th>
		<th class="bigCatetory">ACC</th>
		<th class="bigCatetory">COMMUNITY</th>
		</tr>
		</table>
	</div>
	<div class="sub-nav">
	<table width="900"  border="0" cellpadding="15px;">
		<tr>
		<th width="13%"></th>
		<th width="22%">
			<a href="pdt_list.pdt?scata=2101">OXFORD</a><br />
			<a href="pdt_list.pdt?scata=2102">WINGTIP</a><br />
			<a href="pdt_list.pdt?scata=2103">MONK</a><br />
			<a href="pdt_list.pdt?scata=2104">DERBY</a>
		</th>
		<th width="13%">
			<a href="pdt_list.pdt?scata=3101">PLAIN</a><br />
			<a href="pdt_list.pdt?scata=3102">PENNY</a><br />
			<a href="pdt_list.pdt?scata=3103">TASSEL</a>
		</th>
		<th width="10%">
			<a href="pdt_list.pdt?scata=4101">PLAIN</a><br />
			<a href="pdt_list.pdt?scata=4102">CHELSEA</a>
		</th>
		<th width="10%">
			<a href="pdt_list.pdt?scata=5101">ACC</a>
		</th>
		<th width="*">
			<a href="">NOTICE</a><br />
			<a href="">Q&A</a><br />
		</th>
		</tr>
		</table>
	</div>
</header>
</body>
</html>