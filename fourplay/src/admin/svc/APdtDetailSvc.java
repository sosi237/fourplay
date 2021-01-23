package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class APdtDetailSvc {
	public PdtInfo getPdtInfo(String id) {
		PdtInfo pdtInfo = null;	// ������ ��ǰ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		APdtDao aPdtDao = APdtDao.getInstance();
		aPdtDao.setConnection(conn);

		pdtInfo = aPdtDao.getPdtInfo(id);	// ������ ��ǰ���� �ޱ�

		close(conn);
		return pdtInfo;
	}
	public ArrayList<PdtSizeInfo> getPdtSizeList(String id) {
		ArrayList<PdtSizeInfo> pdtSizelist = new ArrayList<PdtSizeInfo>();	// ������ ��ǰ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		APdtDao aPdtDao = APdtDao.getInstance();
		aPdtDao.setConnection(conn);

		pdtSizelist = aPdtDao.getPdtSizeList(id);	// ������ ��ǰ���������� �ޱ�

		close(conn);
		return pdtSizelist;
	}
}
