<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ include file="a_menu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="stat/js/Chart.min.js"></script>
<script src="stat/js/utils.js"></script>
<style>
.order, .member, .product, .sales {border:1px solid gray; margin:20px auto; padding:10px;}
.order {font-weight:bold; font-size:16px;}
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
<script>

window.onload = function() {

	var memCtx = document.getElementById('member').getContext('2d');
	window.myPie = new Chart(memCtx, memConfig);

	var salesCtx = document.getElementById('sales').getContext('2d');
	window.myLine = new Chart(salesCtx, salesConfig);
	
	var pdtCtx = document.getElementById('canvas').getContext('2d');
	
	window.myBar = new Chart(pdtCtx, {
		type: 'bar',
		data: barChartData,
		options: {
			responsive: true,
			legend:{ position:'top' }, 
			title:{ display:true, text:'누적 판매량 상위 10 상품' },
			scales: {
				yAxes : [{
					ticks: {
						beginAtZero:true
					}
				}]
			},
		}
	});
};
</script>
</head>
<body>
<div id="wrapper">
<%
session.setMaxInactiveInterval(1800);
request.setCharacterEncoding("utf-8");
DecimalFormat df = new DecimalFormat("###,###");
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
String sql = null;
String driver ="com.mysql.cj.jdbc.Driver";
String url = "jdbc:mysql://localhost:3306/fourplay?useUnicode=true&characterEncoding=utf8&verifyServerCertificate=false&useSSL=false&serverTimezone=UTC";
try{
	Class.forName(driver);
	conn = DriverManager.getConnection(url, "root", "1234");
	PdtInfo pdtInfo = null;
	int before, after, ready, exchange, refund;
	ArrayList<PdtInfo> bestList = new ArrayList<PdtInfo>();
	sql = "select count(*) from t_order_list where ol_status = 'a' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	rs.next(); 		before = rs.getInt(1);
	sql = "select count(*) from t_order_list where ol_status = 'b' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	rs.next();  	after = rs.getInt(1);
	sql = "select count(*) from t_order_list where ol_status = 'c' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	rs.next();  	ready = rs.getInt(1);
	sql = "select count(*) from t_order_list where ol_status = 'f' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	rs.next();  	exchange = rs.getInt(1);
	sql = "select count(*) from t_order_list where ol_status = 'h' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	rs.next();  	refund = rs.getInt(1);
	int female = 0, male = 0;
	sql = "select count(*) from t_member_list where ml_gender = 'F' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	if (rs.next())	{
		female = rs.getInt(1);
	}
	sql = "select count(*) from t_member_list where ml_gender = 'M' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	if (rs.next())	{
		male = rs.getInt(1);
	}
%>
<script>
var memConfig = {
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

</script>
<%
	sql =  "select p.pl_id, p.pl_name, sum(s.ps_salecnt) as salecnt from t_product_size s, t_product_list p " + 
		" where p.pl_id = s.pl_id and s.ps_stock != 0 group by p.pl_id order by salecnt desc limit 10 ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	String plname = "", order = "";
	StatPdtInfo statPdtInfo = null;
	ArrayList<StatPdtInfo> statPdtList = new ArrayList<StatPdtInfo>();
	if(rs.next()){
		statPdtInfo = new StatPdtInfo();
		do{
			plname += ", '"+ rs.getString("p.pl_name")+ "'";
			order += ", "+ rs.getInt("salecnt");
		}while(rs.next());
		plname = plname.substring(1);
		order = order.substring(1);
%>
<script>
var color = Chart.helpers.color;
var barChartData = {
	labels: [<%=plname%>],
	datasets: [{
		label: '판매량',
		backgroundColor: [
			'rgba(255, 99, 132, 0.5)',			'rgba(54, 162, 235, 0.5)',			'rgba(255, 206, 86, 0.5)',			'rgba(75, 192, 192, 0.5)',
			'rgba(153, 102, 255, 0.5)',			'rgba(255, 159, 64, 0.5)',			'rgba(96, 36, 99, 0.5)',			'rgba(54, 222, 54, 0.5)',
			'rgba(125, 77, 55, 0.5)',			'rgba(255, 187, 64, 0.5)'
		],
		borderColor: [
			'rgba(255, 99, 132, 1)',			'rgba(54, 162, 235, 1)',			'rgba(255, 206, 86, 1)',			'rgba(75, 192, 192, 1)',
			'rgba(153, 102, 255, 1)',			'rgba(255, 159, 64, 1)',			'rgba(96, 36, 99, 1)',			'rgba(54, 222, 54, 1)',
			'rgba(125, 77, 55, 1)',				'rgba(255, 187, 64, 1)'
		],
		borderWidth: 1,
		data: [<%=order%>]
	}]
};
</script>
<%
	}
	Calendar today = Calendar.getInstance();
//	String year = today.get(Calendar.YEAR) + "";
	String year = "2020";
	String month = "", sales = "";
	sql = "select left(l.ol_date,7), mid(l.ol_date,6,2), sum(d.od_price) as sales " + 
			" from t_product_size s, t_product_list p , t_order_detail d, t_order_list l where p.pl_id = s.pl_id and d.ol_id = l.ol_id and p.pl_id = d.pl_id " + 
			" and (d.od_status != 'f' and d.od_status != 'g' and d.od_status != 'h' and d.od_status != 'i' and d.od_status != 'j' ) " + 
			" and left(l.ol_date,4) = '" + year + "' group by left(l.ol_date,7) order by left(l.ol_date,4) desc";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	while(rs.next()){
		month += ", '" + rs.getString(2) + "월' ";
		sales += ", " + rs.getInt(3);
	}
	month = month.substring(1);
	sales = sales.substring(1);
%>
<script>
var salesConfig = {
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
</script>
	<h3>미처리 주문현황:</h3>
	<div class="order">
		 입금전: <%=before %><br />
		 입금확인: <%=after %><br />
		 상품준비중: <%=ready %><br />
		 교환요청: <%=exchange %><br />
		 환불요청: <%=refund %><br />
	</div>
	<h3>회원통계</h3>
	<div class="member">
		<div id="canvas-holder" style="width:70%">
			<canvas id="member" style="height:30vh; width:50vw"></canvas>
		</div>
	</div>
	<h3>상품통계</h3>
	<div class="product">
		<div id="container" style="width: 75%;">
			<canvas id="canvas" style="height:30vh; width:50vw"></canvas>
		</div>
	</div>
	<h3>매출통계</h3>
	<div class="sales">
		<div style="width:75%;">
		<canvas id="sales" style="height:30vh; width:50vw"></canvas>
		</div>
	</div>
<%

}catch(Exception e){
	e.printStackTrace();
	out.println("오류가 발생했습니다.");
}finally{
	try {
		rs.close(); 	stmt.close();	conn.close();
	} catch(Exception e){
		e.printStackTrace();
	}
}
%>
</div>
</body>
</html>