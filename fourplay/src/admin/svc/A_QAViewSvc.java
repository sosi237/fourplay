package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public QAInfo getArticle(int idx) {
	// �μ��� �޾ƿ� idx�� �ش��ϴ� �Խù� �ϳ��� ������ �����Ͽ� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		QAInfo article = null;	// ������ �Խù� ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		article = aqaDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
