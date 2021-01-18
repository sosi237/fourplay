package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class MpgDao {
	private static MpgDao mpgDao;
	private Connection conn;

	private MpgDao() {}

	public static MpgDao getInstance() {
		if (mpgDao == null) {
			mpgDao = new MpgDao();
		}
		return mpgDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int memberUpdate(MemberInfo loginMember) {
	// 회원 수정을 위한 메소드
		int result = 0;
		Statement stmt = null;
		String sql = null;
		
		try {
			sql = "update t_member_list set ml_pwd = '" + loginMember.getMlpwd() + "', ml_phone = '"+ loginMember.getMlphone()
				+ "', ml_email = '"+ loginMember.getMlemail() +"' where ml_id = '"+ loginMember.getMlid() +"' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("memberUpdate() 오류");			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	
	public int memDelete(String uid, String pwd) {
	// 회원 삭제 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		String sql = null;

		try {
			sql = "update t_member_list set ml_status = 'c' where ml_id = '" + uid + "' and ml_pwd = '" + pwd + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("pdtDelete() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

}



	