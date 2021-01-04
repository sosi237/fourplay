package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdListSvc {
	public int getOrdCount(String buyer) {	// �˻��� �ֹ��� ��ü ������ �����ϴ� �޼ҵ�
		System.out.println("svc getOrdCount");
		
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		rcnt = ordDao.getOrdCount(buyer);
		close(conn);
		
		System.out.println(rcnt);
		return rcnt;
	}

	public ArrayList<OrdListInfo> getOrdList(String buyer, int cpage, int psize) {
		System.out.println("svc getOrdList");
		
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		ordList = ordDao.getOrdList(buyer, cpage, psize);
		close(conn);

		return ordList;
	}
}
