package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionFactory;
import vo.Jobs;


public class JobsDAO implements IDao<Jobs, String> {

	@Override
	public int insert(Jobs vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "INSERT INTO JOBS " + "(JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY) "
				+ "VALUES(?,?,?,?) ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, vo.getJob_id());
		pstmt.setString(2, vo.getJob_title());
		pstmt.setInt(3, vo.getMin_salary());
		pstmt.setInt(4, vo.getMax_salary());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int delete(String key) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "DELETE FROM JOBS WHERE JOB_ID = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int update(Jobs vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "UPDATE JOBS SET JOB_TITLE=?,MIN_SALARY=?,MAX_SALARY=? "
				+ "WHERE JOB_ID=? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getJob_title());
		pstmt.setInt(2, vo.getMin_salary());
		pstmt.setInt(3, vo.getMax_salary());
		pstmt.setString(4, vo.getJob_id());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public Jobs select(String key) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM JOBS WHERE JOB_ID= '"+key+"'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		Jobs vo = new Jobs();
		while (rs.next()) {
			
			vo.setJob_id(rs.getString("JOB_ID"));
			vo.setJob_title(rs.getString("JOB_TITLE"));
			vo.setMin_salary(rs.getInt("MIN_SALARY"));
			vo.setMax_salary(rs.getInt("MAX_SALARY"));
		}
		conn.close();
		return vo;
	}

	@Override
	public List<Jobs> selectAll() throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM JOBS ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		List<Jobs> list = new ArrayList<>();
		while (rs.next()) {
			Jobs vo = new Jobs();
			vo.setJob_id(rs.getString("JOB_ID"));
			vo.setJob_title(rs.getString("JOB_TITLE"));
			vo.setMin_salary(rs.getInt("MIN_SALARY"));
			vo.setMax_salary(rs.getInt("MAX_SALARY"));
			list.add(vo);
		}
		conn.close();
		return list;
	}

	@Override
	public List<Jobs> selectByConditions(String conditions) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM JOBS " + conditions;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Jobs> data = new ArrayList<>();
		while (rs.next()) {
			Jobs vo = new Jobs();
			// 컬럼이름으로 가져오는 것이 더 안전함
			vo.setJob_id(rs.getNString("JOB_ID"));
			vo.setJob_title(rs.getNString("JOB_TITLE"));
			vo.setMin_salary(rs.getInt("MIN_SALARY"));
			vo.setMax_salary(rs.getInt("MAX_SALARY"));
			data.add(vo);
		}
		conn.close();
		return data;
	}
}
