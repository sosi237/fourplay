package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AStatPdtSvc {
	public ArrayList<StatPdtInfo> getBestTen() {
		System.out.println("AStatPdtSvc getBestTen");
		ArrayList<StatPdtInfo> statPdtList = new ArrayList<StatPdtInfo>();
		Connection conn = getConnection();
		AStatPdtDao aStatPdtDao = AStatPdtDao.getInstance();
		aStatPdtDao.setConnection(conn);
		statPdtList = aStatPdtDao.getBestTen();
		close(conn);
		return statPdtList;
	}
}
