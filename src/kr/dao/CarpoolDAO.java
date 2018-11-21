package kr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.util.ConnectionFactory;
import kr.util.JDBCClose;
import kr.vo.CarpoolVO;

public class CarpoolDAO {
	
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
}
