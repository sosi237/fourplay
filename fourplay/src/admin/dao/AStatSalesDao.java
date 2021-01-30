package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class AStatSalesDao {
	private static AStatSalesDao aStatSalesDao;
	private Connection conn;
	private AStatSalesDao() {}
	public static AStatSalesDao getInstance() {
		if (aStatSalesDao == null) {
			aStatSalesDao = new AStatSalesDao();
		}
		return aStatSalesDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public ArrayList<StatPdtInfo> getMonthSales(String year) {	// 특정 연도 월별 매출을 보여주는 메소드
		System.out.println("AStatSalesDao getMonthSales");
		StatPdtInfo statSaleInfo = null;
		ArrayList<StatPdtInfo> statSalesList = new ArrayList<StatPdtInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select left(l.ol_date,7), mid(l.ol_date,6,2), sum(d.od_price) as sales " + 
					" from t_product_size s, t_product_list p , t_order_detail d, t_order_list l where p.pl_id = s.pl_id and d.ol_id = l.ol_id and p.pl_id = d.pl_id " + 
					" and (d.od_status != 'f' and d.od_status != 'g' and d.od_status != 'h' and d.od_status != 'i' and d.od_status != 'j' ) " + 
					" and left(l.ol_date,4) = '" + year + "' group by left(l.ol_date,7) order by left(l.ol_date,4) desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				statSaleInfo = new StatPdtInfo();
				statSaleInfo.setMonth(rs.getString(2));
				statSaleInfo.setSales(rs.getInt(3));
				statSalesList.add(statSaleInfo);
			}
		} catch(Exception e) {
			System.out.println("getMonthSales() 오류");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return statSalesList;
	}
}
