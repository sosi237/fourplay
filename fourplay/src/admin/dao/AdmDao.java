package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class AdmDao {
	private static AdmDao admDao;
	private Connection conn;
	private AdmDao() {}
	public static AdmDao getInstance() {
		if (admDao == null) {
			admDao = new AdmDao();
		}
		return admDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public boolean isSA(AdminInfo adminMember) {
		boolean isSA = false;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_admin_list where al_id = '" + adminMember.getAl_id() + "' and al_pwd = '" + adminMember.getAl_pwd() + "' and al_status = 'b' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {	// 현재 로그인한 계정이 SuperAdmin 계정이면
				isSA = true;
			}
		} catch(Exception e) {
			System.out.println("isSA() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return isSA;
	}
	
	public boolean admUpdate(AdminInfo adminMember) {
		System.out.println("dao admUpdate");
		boolean isSuccess = false;
		Statement stmt = null;
		int result = 0;
		
		try {
			String sql = "update t_admin_list set al_pwd = '" + adminMember.getAl_pwd() + "', al_phone = '"+ adminMember.getAl_phone()
				+ "', al_email = '"+ adminMember.getAl_email() +"' where al_id = '"+ adminMember.getAl_id() +"' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			System.out.println(result);
			
			if(result != 0) {	// 정보 수정이 정상적으로 이뤄졌으면
				isSuccess = true;
				
			}
			System.out.println(isSuccess);
		} catch(Exception e) {
			System.out.println("admUpdate() 오류");			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return isSuccess;
	}
	
}
