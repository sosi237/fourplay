package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QAListSvc {
	public int getArticleCount(String where) {
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		QADao qaDao = QADao.getInstance();
		qaDao.setConnection(conn);
		rcnt = qaDao.getArticleCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<QAInfo> getArticleList(String where, int cpage, int limit) {
		ArrayList<QAInfo> articleList = null;
		Connection conn = getConnection();
		QADao qaDao = QADao.getInstance();
		qaDao.setConnection(conn);
		articleList = qaDao.getArticleList(where, cpage, limit);
		close(conn);

		return articleList;
	}
}
