package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import dao.OrdDao;
import vo.*;

public class AOrdListSvc {
	public int getOrdCount() {	//�� ȸ���� ��ü �ֹ� ������ ����
		int rcnt = 0;	
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		rcnt = aOrdDao.getOrdCount();
		close(conn);

		return rcnt;
	}
	public ArrayList<OrdListInfo> getOrdList(int cpage, int psize){
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		ordList = aOrdDao.getOrdList(cpage, psize);
		close(conn);
		return ordList;
	}
	public OrdListInfo getOrd(String olid) {		//�ϳ��� �ֹ����� ������ �������� �޼ҵ�
		System.out.println("svc getOrd");
		OrdListInfo detailInfo = new OrdListInfo();
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		detailInfo = aOrdDao.getOrd(olid);
		close(conn);			
		return detailInfo;
	}
}
