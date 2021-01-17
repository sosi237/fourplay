package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_NoticeViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public NoticeInfo getArticle(int idx) {
	// �μ��� �޾ƿ� idx�� �ش��ϴ� �Խù� �ϳ��� ������ �����Ͽ� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		NoticeInfo article = null;	// ������ �Խù� ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		A_NoticeDao anoticeDao = A_NoticeDao.getInstance();
		anoticeDao.setConnection(conn);

		article = anoticeDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
