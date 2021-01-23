package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

// DB���� ���� �� ���� �޼ҵ���� ���� Ŭ����
public class JdbcUtil {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn) {	// DB���� ������ ���� �ִ� �޼ҵ�
		try {
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {		// Statement�� �ݴ� �޼ҵ�
		try {
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {		// ResultSet�� �ݴ� �޼ҵ�
		try {
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void commit(Connection conn) {	// transaction�� commit ��Ű�� �޼ҵ�
		try {
			conn.commit();
			System.out.println("commit success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {	// transaction�� rollback ��Ű�� �޼ҵ�
		try {
			conn.rollback();
			System.out.println("rollback success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
