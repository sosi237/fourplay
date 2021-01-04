package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

// db관련 연결 및 공용 메소드들을 위한 클래스 
public class JdbcUtil {
	public static Connection getConnection() {	// db에 연결하여 커넥션 객체를 리턴하는 메소드
		Connection conn = null;
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
			conn = ds.getConnection();
			conn.setAutoCommit(false);			// 자동으로 commit 되는 것을 막아 트랜잭션을 시작시킴
		} catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {	//db와의 연결을 끊어주는 메소드
		try {
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {	
	// PreparedStatement와 CallabledStatement는 Statement를 상속받으므로 따로 close()메소드를 만들 필요가 없음
		try {
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {	
		try {
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {	// transaction을 commit시키는 메소드
		try {
			conn.commit();
			System.out.println("commit success");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {	// transaction을 rollback시키는 메소드
		try {
			conn.rollback();
			System.out.println("rollback success");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}


















