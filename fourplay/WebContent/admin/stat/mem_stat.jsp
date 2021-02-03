<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ include file="../a_menu.jsp" %>
<%@ include file="../statistics_menu.jsp" %>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
<script src="stat/js/Chart.min.js"></script>
<script src="stat/js/utils.js"></script>
</head>
<body>
<div id="wrapper">
<%
if(kind.equals("gender")){
	int male = (int)request.getAttribute("male");
	int female = (int)request.getAttribute("female");
%>
<script>
var gConfig = {
	type: 'pie',
	data: {
		datasets: [{
			data: [<%=male%>, <%=female%>], 
			backgroundColor: [
				window.chartColors.blue,
				window.chartColors.red,
			],
			label: '회원 성별'
		}],
		labels: ["남자", "여자"]
	},
	options: { responsive:true, title:{ display:true, text:'회원 성별' } }
};

window.onload = function() {
	var gCtx = document.getElementById('chart-gender').getContext('2d');
	window.myPie = new Chart(gCtx, gConfig);
};
</script>
	<div id="canvas-holder" style="width:70%">
	<canvas id="chart-gender"></canvas>
	</div>
<%
} else if(kind.equals("age")){
	ArrayList ageList = (ArrayList)request.getAttribute("ageList");
	String age = "";
	for(int i = 0; i < ageList.size(); i++){
		System.out.print(ageList.get(i) + ", ");
		age += ", " + ageList.get(i);
	}
	age = age.substring(1);
	System.out.println(age);
%>
<script>
var aConfig = {
	type: 'doughnut',
	data: {
		datasets: [{
			data: [<%=age%>], 
			backgroundColor: [
				window.chartColors.orange, 
				window.chartColors.red, 
				window.chartColors.yellow, 
				window.chartColors.green, 
				window.chartColors.blue
			],
			label: '점유율'
		}],
		labels: ["10대", "20대", "30대", "40대", "50대"]
	},
	options: {
		responsive: true, legend: { position: 'top', }, 
		title: { display: true, text: '회원 연령 통계' },
		animation: { animateScale: true, animateRotate: true }
	}
};

window.onload = function() {
	var aCtx = document.getElementById('chart-age').getContext('2d');
	window.myDoughnut = new Chart(aCtx, aConfig);
};
</script>
	<div id="canvas-holder" style="width:70%">
		<canvas id="chart-age"></canvas>
	</div>	
<%
}
%>
</div>
</body>
</html>