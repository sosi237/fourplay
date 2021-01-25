package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class A_FaqDao {
	private static A_FaqDao afaqDao;
	private Connection conn;
	private A_FaqDao() {}
	
	public static A_FaqDao getInstance() {
		if (afaqDao == null) {
			afaqDao = new A_FaqDao();	
		}
		return afaqDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getArticleCount(String where) {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "select count(*) from t_faq_list " +
					" where fq_status = 'a' " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getArticleCount() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public ArrayList<FaqInfo> getArticleList(String where, int cpage, int limit) {
	// �˻��� �Խñ� ����� ArrayList���·� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<FaqInfo> articleList = new ArrayList<FaqInfo>();
		FaqInfo faqInfo = null;

		int snum = (cpage - 1) * limit;

		try {
			sql ="select * from t_faq_list where fq_status = 'a' " + 
					where + " order by fq_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				faqInfo = new FaqInfo();
				faqInfo.setFq_idx(rs.getInt("fq_idx"));
				faqInfo.setFq_read(rs.getInt("fq_read"));
				faqInfo.setFq_writer(rs.getString("fq_writer"));
				faqInfo.setFq_title(rs.getString("fq_title"));
				faqInfo.setFq_content(rs.getString("fq_content"));
				faqInfo.setFq_date(rs.getString("fq_date"));

				articleList.add(faqInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	
	public FaqInfo getArticle(int idx) {
	// ������ �Խù��� �����͸� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		FaqInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_faq_list where fq_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new FaqInfo();
				article.setFq_idx(rs.getInt("fq_idx"));
				article.setFq_read(rs.getInt("fq_read"));
				article.setFq_writer(rs.getString("fq_writer"));
				article.setFq_title(rs.getString("fq_title"));
				article.setFq_content(rs.getString("fq_content"));
				article.setFq_date(rs.getString("fq_date"));
				article.setFq_status(rs.getString("fq_status"));
			}
		} catch(Exception e) {
			System.out.println("getArticle() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}

	public int afaqInsert(FaqInfo faqInfo) {
	// �Խñ� ��� �޼ҵ�
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		int idx = 1, result = 0;	
		String sql = null;

		try {
			System.out.println("dao");
			sql = "select max(fq_idx) + 1 from t_faq_list";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);

			sql = "insert into t_faq_list (fq_idx, fq_writer, fq_title, fq_content, al_idx) values (?, ?, ?, ?, ?)";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, faqInfo.getFq_writer());
			pstmt.setString(3, faqInfo.getFq_title());
			pstmt.setString(4, faqInfo.getFq_content());
			pstmt.setInt(5, 1);

			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("afaqInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

	public int afaqUpdate(FaqInfo faqInfo) {
	 // �Խñ� ������ ���� �޼ҵ�
	    PreparedStatement pstmt = null;
	    String sql = null;
	    int result = 0;      // ���� ���� ��� ������ ������ ����

     try {
	       sql = "update t_faq_list set fq_title = ?, fq_content = ? where fq_idx = ?";
	       pstmt = conn.prepareStatement(sql);
	       pstmt.setString(1, faqInfo.getFq_title());
		   pstmt.setString(2, faqInfo.getFq_content());
		   pstmt.setInt(3, faqInfo.getFq_idx());
		   result = pstmt.executeUpdate();   // �Խñ� ����
		  } catch(Exception e) {
		      System.out.println("afaqUpdate() ����");
		      e.printStackTrace();
		   } finally {
		      close(pstmt);
		   }

		   return result;
	 }

	public int afaqDelete(int idx, FaqInfo faqInfo) {
	// �Խñ� ������ ���� �޼ҵ�
		Statement stmt = null;
		String sql = null;
		int result = 0;		

		try {
			sql = "delete from t_faq_list where fq_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);;

		} catch(Exception e) {
			System.out.println("afaqDelete() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public FaqInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		FaqInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			String where = " and fq_writer = '" + uid + "'";
			sql = "select * from t_faq_list where fq_idx = " + idx ;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new FaqInfo();
				// �˻��� �Խù��� ������ ������ FreeInfo�� �ν��Ͻ� article ����
				article.setFq_idx(rs.getInt("fq_idx"));
				article.setFq_read(rs.getInt("fq_read"));
				article.setFq_writer(rs.getString("fq_writer"));
				article.setFq_title(rs.getString("fq_title"));
				article.setFq_content(rs.getString("fq_content"));
				article.setFq_date(rs.getString("fq_date"));
				// �޾ƿ� ���ڵ��� article �ν��Ͻ��� ��� ������ ���� ����
			}
		} catch(Exception e) {
			System.out.println("getArticleUp() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return article;
	}
}
