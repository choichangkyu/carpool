package kr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.util.ConnectionFactory;
import kr.util.JDBCClose;
import kr.vo.ApplyVO;
import kr.vo.BoardVO;
import kr.vo.CarpoolVO;

public class CarpoolDAO {
	
	// 예약자수 증가/감소
	public void CountApply(int postNo, int cnt) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update c_carpool_post ");
		if( cnt > 0) {
			sql.append(" set apply_cnt = apply_cnt + ? ");
		} else {
			sql.append(" set apply_cnt = apply_cnt - ? ");
		}
		sql.append(" where no = ? ");
		
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setInt(1, Math.abs(cnt));
			pstmt.setInt(2, postNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//카풀글 추가
	public int insertCarpoolPost(CarpoolVO post) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" insert into c_carpool_post (no, writer_id, start_date, ");
			sql.append(" start_place, start_place_name, start_time, end_place, end_place_name, ");
			sql.append(" smoke, money, user_cnt, post_type, add_info ) ");
			sql.append(" values (seq_c_carpool_post_no.nextval , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, post.getId());
			pstmt.setString(2, post.getStart_date());
			pstmt.setString(3, post.getStart_place());
			pstmt.setString(4, post.getStart_place_name());
			pstmt.setString(5, post.getStart_time());
			pstmt.setString(6, post.getEnd_place());
			pstmt.setString(7, post.getEnd_place_name());
			pstmt.setString(8, post.getSmoke());
			pstmt.setInt(9, post.getMoney());
			pstmt.setInt(10, post.getUser_cnt());
			pstmt.setString(11, post.getPost_type());
			pstmt.setString(12, post.getAdd_info());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<CarpoolVO> selectAllPost() {
		List<CarpoolVO> carpoolList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * ");
			//sql.append(" to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append("  from c_carpool_post	 ");
			sql.append(" order by no desc ");

			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CarpoolVO post = new CarpoolVO();
				post.setNo(rs.getInt("no"));
				post.setId(rs.getString("writer_id"));
				post.setPost_type(rs.getString("post_type"));
				post.setSmoke(rs.getString("smoke"));
				post.setStart_date(rs.getString("start_date"));
				post.setStart_time(rs.getString("start_time"));
				post.setStart_place(rs.getString("start_place"));
				post.setStart_place_name(rs.getString("start_place_name"));
				post.setEnd_place(rs.getString("end_place"));
				post.setEnd_place_name(rs.getString("end_place_name"));
				post.setMoney(rs.getInt("money"));
				post.setUser_cnt(rs.getInt("user_cnt"));
				post.setAdd_info(rs.getString("add_info"));
				post.setReg_date(rs.getString("reg_date"));
				carpoolList.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return carpoolList;
	}

	public CarpoolVO selectByNo(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		CarpoolVO post = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * ");
			//sql.append(" to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append("  from c_carpool_post ");
			sql.append(" where no = ? ");
			sql.append(" order by no desc ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);;
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				post = new CarpoolVO();
				post.setNo(rs.getInt("no"));
				post.setId(rs.getString("writer_id"));
				post.setPost_type(rs.getString("post_type"));
				post.setSmoke(rs.getString("smoke"));
				post.setStart_date(rs.getString("start_date"));
				post.setStart_time(rs.getString("start_time"));
				post.setStart_place(rs.getString("start_place"));
				post.setStart_place_name(rs.getString("start_place_name"));
				post.setEnd_place(rs.getString("end_place"));
				post.setEnd_place_name(rs.getString("end_place_name"));
				post.setMoney(rs.getInt("money"));
				post.setUser_cnt(rs.getInt("user_cnt"));
				post.setApply_cnt(rs.getInt("apply_cnt"));
				post.setAdd_info(rs.getString("add_info"));
				post.setReg_date(rs.getString("reg_date"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return post;
	}

	public int deleteCarpool(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from c_carpool_post ");
			sql.append(" where no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);;
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return result;
	}
}
