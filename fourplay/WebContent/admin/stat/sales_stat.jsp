<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*, java.util.*" %>
<%@ include file="../a_menu.jsp" %>
<%@ include file="../statistics_menu.jsp" %>
<%
String year = (String)request.getAttribute("year");
ArrayList<StatPdtInfo> statSalesList = (ArrayList<StatPdtInfo>)request.getAttribute("statSalesList");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
canvas{
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
if(kind.equals("monthly")){
	String month = "", sales = "";
	for(int i = 0; i < statSalesList.size(); i++ ){
		month += ", '" + statSalesList.get(i).getMonth() + "월' ";
		sales += ", " + statSalesList.get(i).getSales();
	}
	month = month.substring(1);
	sales = sales.substring(1);
%>
<script>
var config = {
	type: 'line',
	data: {
		labels: [<%=month%>], 
		datasets: [{
			label: '매출액', 
			backgroundColor: window.chartColors.red,
			borderColor: window.chartColors.red,
			data: [<%=sales%>], 
			fill: false,
		}]
	},
	options: {
		responsive: true,
		title: { display: true, text: '<%=year%>년 월별 매출' }, 
		tooltips: { mode: 'index', intersect: false }, 
		hover: { mode: 'nearest', intersect: true }, 
		scales: { 
			xAxes: [{ display:true, scaleLabel:{ display:true, labelString:'월별' } }],
			yAxes: [{ display:true, scaleLabel:{ display:true, labelString:'금액' } }]
		}
	}
};

window.onload = function() {
	var ctx = document.getElementById('canvas').getContext('2d');
	window.myLine = new Chart(ctx, config);
};
</script>
	<div style="width:75%;">
		<canvas id="canvas"></canvas>
	</div>
<%} else if(kind.equals("quarter")){
		if(statSalesList.size() / 3 > 0){	// 3개월 이상의 자료가 있으면
			int qSales = 0;
			String sales = "";
			for(int i = 0; i < statSalesList.size(); i++){
				qSales += statSalesList.get(i).getSales();
				if(i % 3 == 0){
					sales += ", " + qSales;
					qSales = 0;
				} else if(i == statSalesList.size() -1){
					sales += ", " + qSales;
				}
			}
			sales = sales.substring(1);
%>	
<script>
var color = Chart.helpers.color;
var barChartData = {
	labels: ['1분기', '2분기', '3분기', '4분기'],
	datasets: [{
		label: '매출액',
		backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
		borderColor: window.chartColors.red,
		borderWidth: 1,
		data: [<%=sales%>]
	}]
};

window.onload = function() {
	var ctx = document.getElementById('canvas').getContext('2d');
	window.myBar = new Chart(ctx, {
		type: 'bar',
		data: barChartData,
		options: {
			responsive: true,
			legend:{ position:'top' }, 
			title:{ display:true, text:'<%=year%>년 분기별 매출' }
		}
	});
};
</script>
	<div id="container" style="width: 75%;">
		<canvas id="canvas"></canvas>
	</div>
<%
	} else {
		out.println("해당 연도의 분기별 자료가 없습니다.");
	}
}
%>
</div>
</body>
</html>