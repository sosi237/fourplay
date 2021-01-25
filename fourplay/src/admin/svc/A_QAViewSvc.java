package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAViewSvc {

	public QAInfo getArticle(int idx) {

		QAInfo article = null;	
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		article = aqaDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
