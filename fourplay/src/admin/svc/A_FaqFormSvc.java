package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_FaqFormSvc {
	public FaqInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		FaqInfo article = null;
		Connection conn = getConnection();
		A_FaqDao afaqDao = A_FaqDao.getInstance();
		afaqDao.setConnection(conn);

		article = afaqDao.getArticleUp(idx, uid);

		close(conn);
		return article;
	}
}
