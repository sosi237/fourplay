package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class APdtListSvc {
	public int getPdtCount(String where) {
	// �˻��� ��ǰ�� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		APdtDao aPdtDao = APdtDao.getInstance();
		aPdtDao.setConnection(conn);
		rcnt = aPdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
		Connection conn = getConnection();
		APdtDao aPdtDao = APdtDao.getInstance();
		aPdtDao.setConnection(conn);
		pdtList = aPdtDao.getPdtList(where, orderby, cpage, psize);
		close(conn);

		return pdtList;
	}
}
