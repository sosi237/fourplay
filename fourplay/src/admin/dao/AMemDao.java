package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class AMemDao {
	private static AMemDao aMemDao;
	private Connection conn;
	private AMemDao() {}
	public static AMemDao getInstance() {
		if (aMemDao == null) {
			aMemDao = new AMemDao();
		}
		return aMemDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getMemCount(String where) {	
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_member_list ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getMemCount() ¿À·ù");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MemberInfo> getMemberList(String where, String orderby, int cpage, int psize) {
		System.out.println("dao getMemberList");
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		MemberInfo memberInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int snum = (cpage - 1) * psize;
		
		try {
			sql = "select ml_id, ml_pwd, ml_name, ml_birth, ml_phone, ml_email, ml_point, ml_date, ml_last, " +
			" if(ml_status = 'a', 'ÀÏ¹Ý', if(ml_status = 'b', 'ÈÞ¸Õ', 'Å»Åð')) stat, " +
			" if(ml_gender = 'F', '¿©', '³²') gender from t_member_list " + where + orderby + " limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMlid(rs.getString("ml_id"));
				memberInfo.setMlpwd(rs.getString("ml_pwd"));
				memberInfo.setMlname(rs.getString("ml_name"));
				memberInfo.setMlgender(rs.getString("gender"));
				memberInfo.setMlbirth(rs.getString("ml_birth"));
				memberInfo.setMlphone(rs.getString("ml_phone"));
				memberInfo.setMlemail(rs.getString("ml_email"));
				memberInfo.setMldate(rs.getString("ml_date"));
				memberInfo.setMllast(rs.getString("ml_last"));
				memberInfo.setMlstatus(rs.getString("stat"));
				memberInfo.setMlpoint(rs.getInt("ml_point"));
				memberList.add(memberInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getMemberList() ¿À·ù");			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return memberList;
	}
}

