<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ include file="a_menu.jsp" %>
<%
System.out.println();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="stat/js/Chart.min.js"></script>
<script src="stat/js/utils.js"></script>
<style>
.status, .sales_stat, .pdt_stat, .mem_stat { 
	width:100px; height:100px; border:1px solid gray; 
}
.order, .member {border:1px solid gray; margin:20px auto; padding:10px;}
.order {font-weight:bold; font-size:16px;}
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
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
		
%>
	<h3>미처리 주문현황:</h3>
	<div class="order">
		 입금전: <%=before %><br />
		 입금확인: <%=after %><br />
		 상품준비중: <%=ready %><br />
		 교환요청: <%=exchange %><br />
		 환불요청: <%=refund %><br />
	</div>
	<h3>회원통계</h3>
<%
	int female = 0, male = 0;
	sql = "select count(*) from t_member_list where ml_gender = 'F' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	if (rs.next())	female = rs.getInt(1);
	sql = "select count(*) from t_member_list where ml_gender = 'M' ";
	stmt = conn.createStatement();
	rs = stmt.executeQuery(sql);
	if (rs.next())	male = rs.getInt(1);
%>
<script>
var config = {
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
	options: { responsive:false, title:{ display:true, text:'회원 성별' } }
};

window.onload = function() {
	var ctx = document.getElementById('chart-area').getContext('2d');
	window.myPie = new Chart(ctx, config);
};
</script>
	<div class="member">
		<div id="canvas-holder" style="width:70%">
		<canvas id="chart-area" style="height:30vh; width:50vw"></canvas>
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