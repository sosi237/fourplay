package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_NoticeFormSvc {
	public NoticeInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		NoticeInfo article = null;
		Connection conn = getConnection();
		A_NoticeDao anoticeDao = A_NoticeDao.getInstance();
		anoticeDao.setConnection(conn);

		article = anoticeDao.getArticleUp(idx, uid);

		close(conn);
		return article;
	}
}
