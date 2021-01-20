package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AddrViewSvc {
	public ArrayList<MemberInfo> getAddrList(String uid) {
		ArrayList<MemberInfo> addrList = null;
		Connection conn = getConnection();
		MpgDao mpgDao = MpgDao.getInstance();
		mpgDao.setConnection(conn);
		addrList = mpgDao.getAddrList(uid);
		close(conn);

		return addrList;
	}
}
