package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdFormSvc {
	public ArrayList<CartInfo> getOrdFrmPdtList(String kind, String where) {
		ArrayList<CartInfo> pdtList = null;
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		pdtList = ordDao.getOrdFrmPdtList(kind, where);
		close(conn);

		return pdtList;
	}
	
	public MemberInfo getaddr(String uid) {
		MemberInfo addrInfo = new MemberInfo();
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		addrInfo = ordDao.getaddr(uid);
		close(conn);

		return addrInfo;
	}
}
