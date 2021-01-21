package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class A_QADao {
	private static A_QADao aqaDao;
	private Connection conn;
	private A_QADao() {}
	
	public static A_QADao getInstance() {
		if (aqaDao == null) {
			aqaDao = new A_QADao();	
		}
		return aqaDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getArticleCount(String where) {
System.out.println("dao getArticleCount");		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "select count(*) from t_qna_list " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getArticleCount() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public ArrayList<QAInfo> getArticleList(String where, int cpage, int limit) {
System.out.println("dao getArticleList");
		// 검색된 게시글 목록을 ArrayList형태로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<QAInfo> articleList = new ArrayList<QAInfo>();
		QAInfo qaInfo = null;

		int snum = (cpage - 1) * limit;
		try {
			sql ="select * from t_qna_list  " + where + " order by ql_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				qaInfo = new QAInfo();
				qaInfo.setQl_idx(rs.getInt("ql_idx"));
				qaInfo.setQl_writer(rs.getString("ml_id"));
				qaInfo.setQl_title(rs.getString("ql_title"));
				qaInfo.setQl_content(rs.getString("ql_content"));
				qaInfo.setQl_qdate(rs.getString("ql_qdate"));
				qaInfo.setQl_answer(rs.getString("ql_answer"));
				qaInfo.setQl_status(rs.getString("ql_status"));

				articleList.add(qaInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	public QAInfo getArticle(int idx) {
		QAInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_qna_list where ql_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new QAInfo();
				article.setQl_idx(rs.getInt("ql_idx"));
				article.setQl_writer(rs.getString("ml_id"));
				article.setQl_title(rs.getString("ql_title"));
				article.setQl_content(rs.getString("ql_content"));
				article.setQl_qdate(rs.getString("ql_qdate"));
				article.setQl_adate(rs.getString("ql_adate"));
				article.setQl_answer(rs.getString("ql_answer"));
				article.setQl_status(rs.getString("ql_status"));
				article.setAl_idx(rs.getInt("al_idx"));
				
			}
		} catch(Exception e) {
			System.out.println("getArticle() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}

	public int aqaInsert(QAInfo qaInfo) {
	// 게시글 등록 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		int idx = 1, result = 0;	
		String sql = null;

		try {
			System.out.println("dao");
			sql = "select max(ql_idx) + 1 from t_qna_list";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);

			sql = "insert into t_qna_list (ql_idx, ql_writer, ql_title, ql_content, al_idx) values (?, ?, ?, ?, ?)";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, qaInfo.getQl_writer());
			pstmt.setString(3, qaInfo.getQl_title());
			pstmt.setString(4, qaInfo.getQl_content());
			pstmt.setInt(5, 1);

			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("aqaInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

	public int aqaUpdate(QAInfo qaInfo) {
	 // 게시글 수정을 위한 메소드
	    PreparedStatement pstmt = null;
	    String sql = null;
	    int result = 0;      // 쿼리 실행 결과 개수를 저장할 변수

     try {
	       sql = "update t_qna_list set ql_title = ?, ql_content = ? where ql_idx = ?";
	       pstmt = conn.prepareStatement(sql);
	       pstmt.setString(1, qaInfo.getQl_title());
		   pstmt.setString(2, qaInfo.getQl_content());
		   pstmt.setInt(3, qaInfo.getQl_idx());
		   result = pstmt.executeUpdate();   // 게시글 수정
		  } catch(Exception e) {
		      System.out.println("aqaUpdate() 오류");
		      e.printStackTrace();
		   } finally {
		      close(pstmt);
		   }

		   return result;
	 }

	public int aqaDelete(int idx, QAInfo qaInfo) {
	// 게시글 삭제를 위한 메소드
		Statement stmt = null;
		String sql = null;
		int result = 0;		

		try {
			sql = "delete from t_qna_list where ql_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);;

		} catch(Exception e) {
			System.out.println("aqaDelete() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public QAInfo getArticleUp(int idx, String uid) {
	// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져와 리턴할 메소드
		QAInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			String where = " and ql_writer = '" + uid + "'";
			sql = "select * from t_qna_list where ql_idx = " + idx ;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new QAInfo();
				// 검색된 게시물의 정보를 저장할 FreeInfo형 인스턴스 article 생성
				article.setQl_idx(rs.getInt("ql_idx"));
				article.setQl_writer(rs.getString("ql_writer"));
				article.setQl_title(rs.getString("ql_title"));
				article.setQl_content(rs.getString("ql_content"));
				article.setQl_qdate(rs.getString("ql_qdate"));
				// 받아온 레코드들로 article 인스턴스의 멤버 변수에 값을 넣음
			}
		} catch(Exception e) {
			System.out.println("getArticleUp() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return article;
	}
}
