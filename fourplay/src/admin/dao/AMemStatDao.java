package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class AMemStatDao {
	private static AMemStatDao aMemStatDao;
	private Connection conn;
	private AMemStatDao() {}
	public static AMemStatDao getInstance() {
		if (aMemStatDao == null) {
			aMemStatDao = new AMemStatDao();
		}
		return aMemStatDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getMemCount(String gender) {	
		int g = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_member_list where ml_gender = '"+ gender +"' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	g = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getMemCount() 오류");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return g;
	}
	public ArrayList getAgeList() {
		ArrayList ageList = new ArrayList();
		int ageMem = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			for(int i = 10; i <= 50; i += 10) {
				sql = "select count(*) from t_member_list " + 
						"where (left(now(), 4) - left(ml_birth, 4) +1 ) >= " + i + " and (left(now(), 4) - left(ml_birth, 4) +1 ) < " + (i + 10) ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					ageMem = rs.getInt(1);
					ageList.add(ageMem);
				}
			}
		} catch(Exception e) {
			System.out.println("getAgeList() 오류");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ageList;
	}
}
