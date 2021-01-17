package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_FreeListSvc {
// �Խñ� ��� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public int getArticleCount(String where) {
	// �Խñ��� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		A_FreeDao afreeDao = A_FreeDao.getInstance();
		afreeDao.setConnection(conn);
		rcnt = afreeDao.getArticleCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<FreeInfo> getArticleList(String where, int cpage, int limit) {
	// �Խñ� ����� ArrayList�� �����ϸ�, �ݵ�� FreeInfo�� �ν��Ͻ��� ����Ǿ�� ��
	// �Ű����� : ����, ���� ������ ��ȣ, ������ ���ڵ� ����
		ArrayList<FreeInfo> articleList = null;
		// �Խù� ����� ���� ArrayList ����
		Connection conn = getConnection();
		A_FreeDao afreeDao = A_FreeDao.getInstance();
		afreeDao.setConnection(conn);
		articleList = afreeDao.getArticleList(where, cpage, limit);
		close(conn);

		return articleList;
	}
}
