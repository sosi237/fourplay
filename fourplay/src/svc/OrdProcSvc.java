package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdProcSvc {
	public String orderProc(String kind, String[] clIdxs, OrdListInfo ord) throws Exception {
	// �ֹ�ó���� ���� �޼ҵ�
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);

		String result = ordDao.orderProc(kind, clIdxs, ord);
		String[] arrResult = result.split(":");
		if (Integer.parseInt(arrResult[0]) > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);
		return result;
	}
	public int ordCancel(String olid) {
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);

		int result = ordDao.ordCancel(olid);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
