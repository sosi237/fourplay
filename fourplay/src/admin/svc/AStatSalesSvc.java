package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AStatSalesSvc {
	public ArrayList<StatPdtInfo> getMonthSales(String year) {
		ArrayList<StatPdtInfo> statSalesList = new ArrayList<StatPdtInfo>();
		Connection conn = getConnection();
		AStatSalesDao aStatSalesDao = AStatSalesDao.getInstance();
		aStatSalesDao.setConnection(conn);
		statSalesList = aStatSalesDao.getMonthSales(year);
		close(conn);
		return statSalesList;
	}
}
