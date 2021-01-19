package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FaqViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public FaqInfo getArticle(int idx) {
	// �μ��� �޾ƿ� idx�� �ش��ϴ� �Խù� �ϳ��� ������ �����Ͽ� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		FaqInfo article = null;	// ������ �Խù� ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		FaqDao faqDao = FaqDao.getInstance();
		faqDao.setConnection(conn);

		article = faqDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
