package kr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.util.ConnectionFactory;
import kr.util.JDBCClose;
import kr.vo.MemberVO;

public class MemberDAO {
	/**
	 * ��� ȸ�� ��ȸ
	 * 
	 * @return MemberVO��ü ����Ʈ
	 */
	public List<MemberVO> selectAllMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MemberVO> memberList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select id, name, to_char(reg_date, 'YYYY-mm-dd') reg_date ");
			sql.append(" from t_member ");
			sql.append(" order by reg_date asc ");

			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberVO member = new MemberVO();

				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setRegDate(rs.getString("reg_date"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return memberList;
	}
	
	public List<MemberVO> selectAllMember(int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MemberVO> memberList = new ArrayList<>();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			// TODO Auto-generated catch block
			sql.append(" select id, name, to_char(reg_date, 'YYYY-mm-dd') reg_date ");
			sql.append(" from t_member ");
			sql.append(" where rownum >= ? and rownum <= ? ");
			sql.append(" order by reg_date asc ");
			
			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberVO member = new MemberVO();
				
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setRegDate(rs.getString("reg_date"));
				
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
		return memberList;
	}

	/**
	 * ȸ�� �߰�
	 * @param board
	 * @return �ݿ��� �� ����
	 */
	public int insertMember(MemberVO member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" insert into c_member (name, id, password, email, birth, tel, age, addr) ");
			sql.append(" values (?, ?, ?, ?, ?, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getBirth());
			pstmt.setString(6, member.getTel());
			pstmt.setInt(7, member.getAge());
			pstmt.setString(8, member.getAddr());

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * �α��� ȸ�� ��ȸ
	 * @param no
	 * @return �Խñ� ��ü
	 */
	public MemberVO selectForLogin(MemberVO member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO result = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select id, name");
			sql.append(" from c_member ");
			sql.append(" where id = ? ");
			sql.append(" and password = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new MemberVO();
				
				result.setId(rs.getString("id"));
				result.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * ���̵�� ȸ�� ��ȸ
	 * @param no
	 * @return �Խñ� ��ü
	 */
	public MemberVO selectById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MemberVO member = new MemberVO();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" select id, name, email_id, email_domain, ");
			sql.append(" tel1, tel2, tel3, ");
			sql.append(" post, basic_addr, detail_addr, ");
			sql.append(" reg_date ");
			sql.append(" from t_member ");
			sql.append(" where id = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				/*member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setEmail_id(rs.getString("email_id"));
				member.setEmail_domain(rs.getString("email_domain"));
				member.setTel1(rs.getString("tel1"));
				member.setTel2(rs.getString("tel2"));
				member.setTel3(rs.getString("tel3"));
				member.setPost(rs.getString("post"));
				member.setBasic_addr(rs.getString("basic_addr"));
				member.setDetail_addr(rs.getString("detail_addr"));
				member.setRegDate(rs.getString("reg_date"));*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}
	
	public int dele_user(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append(" delete from t_member ");
			sql.append(" where id = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
