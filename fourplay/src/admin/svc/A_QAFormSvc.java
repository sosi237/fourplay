package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAFormSvc {
	public QAInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		QAInfo article = null;
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		article = aqaDao.getArticleUp(idx, uid);

		close(conn);
		return article;
	}
}
