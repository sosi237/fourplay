package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AMemStatSvc {
	public int getMemCount(String gender) {
		System.out.println("AMemStatSvc getMemCount");
		int g = 0;
		Connection conn = getConnection();
		AMemStatDao aMemStatDao = AMemStatDao.getInstance();
		aMemStatDao.setConnection(conn);
		g = aMemStatDao.getMemCount(gender);
		close(conn);
		return g;
	}
}
