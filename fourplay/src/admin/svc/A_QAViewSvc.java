package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAViewSvc {
// 게시글 보기의 비즈니스 로직을 처리하는 클래스
	public QAInfo getArticle(int idx) {
	// 인수로 받아온 idx에 해당하는 게시물 하나의 정보를 추출하여 FreeInfo형 인스턴스로 리턴하는 메소드
		QAInfo article = null;	// 리턴할 게시물 정보를 담을 인스턴스 선언
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		article = aqaDao.getArticle(idx);	// 보여줄 게시글 받기

		close(conn);
		return article;
	}
}
