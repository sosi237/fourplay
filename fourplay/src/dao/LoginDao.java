package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class LoginDao {
	private static LoginDao loginDao;
	private Connection conn;

	private LoginDao() {}

	public static LoginDao getInstance() {
		if (loginDao == null) {
			loginDao = new LoginDao();
		}
		return loginDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public MemberInfo getLoginMember(String uid, String pwd) {	// �Ϲ� ȸ�� �α����� ó���ϴ� �޼ҵ�
		MemberInfo loginMember = null;	
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from t_member_list where ml_status = 'a' and ml_id = '" + uid 
					+ "' and ml_pwd = '" + pwd + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				loginMember = new MemberInfo();
				loginMember.setMlid(uid);
				loginMember.setMlpwd(pwd);
				loginMember.setMlname(rs.getString("ml_name"));
				loginMember.setMlgender(rs.getString("ml_gender"));
				loginMember.setMlbirth(rs.getString("ml_birth"));
				loginMember.setMlphone(rs.getString("ml_phone"));
				loginMember.setMlemail(rs.getString("ml_email"));
				loginMember.setMldate(rs.getString("ml_date"));
				loginMember.setMllast(rs.getString("ml_last"));
				loginMember.setMlstatus("a");
				loginMember.setMlpoint(rs.getInt("ml_point"));
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(rs);		close(stmt);
			} catch(Exception e) {
				System.out.println("getLoginMember() ����"); e.printStackTrace();
			}
		}

		return loginMember;
	}
	public AdminInfo getAdminMember(String uid, String pwd) {	// ������ ���� �α����� ó���ϴ� �޼ҵ�
		AdminInfo adminMember = null;	
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from t_admin_list where al_status = 'b' and al_id = '" + uid + "' and al_pwd = '" + pwd + "' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	// ������ �����̸�
				adminMember = new AdminInfo();
				adminMember.setAl_idx(rs.getInt("al_idx"));
				adminMember.setAl_id(uid);
				adminMember.setAl_pwd(pwd);
				adminMember.setAl_name(rs.getString("al_name"));
				adminMember.setAl_email(rs.getString("al_email"));
				adminMember.setAl_phone(rs.getString("al_phone"));
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(rs);		close(stmt);
			} catch(Exception e) {
				System.out.println("getAdminMember() ����"); e.printStackTrace();
			}
		}
		return adminMember;
	}
}
