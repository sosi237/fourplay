package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class AStatPdtDao {
	private static AStatPdtDao aStatPdtDao;
	private Connection conn;
	private AStatPdtDao() {}
	public static AStatPdtDao getInstance() {
		if (aStatPdtDao == null) {
			aStatPdtDao = new AStatPdtDao();
		}
		return aStatPdtDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public ArrayList<StatPdtInfo> getBestTen() {	// 전체 판매량 순위 10개의 상품을 보여주는 메소드
		System.out.println("AStatPdtDao getBestTen");
		StatPdtInfo statPdtInfo = null;
		ArrayList<StatPdtInfo> statPdtList = new ArrayList<StatPdtInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select p.pl_id, p.pl_name, sum(s.ps_salecnt) as salecnt from t_product_size s, t_product_list p " + 
				" where p.pl_id = s.pl_id and s.ps_stock != 0 group by p.pl_id order by salecnt desc limit 10 ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				statPdtInfo = new StatPdtInfo();
				statPdtInfo.setPl_id(rs.getString("p.pl_id"));
				statPdtInfo.setPl_name(rs.getString("p.pl_name"));
				statPdtInfo.setOrders(rs.getInt("salecnt"));
				
				statPdtList.add(statPdtInfo);
			}
		} catch(Exception e) {
			System.out.println("getBestTen() 오류");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return statPdtList;
	}
}
