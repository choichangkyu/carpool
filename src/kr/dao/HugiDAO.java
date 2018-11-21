package kr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.ConnectionFactory;
import kr.util.JDBCClose;
import kr.vo.BoardVO;

/**
 * �Խ���(t_board)�� CRUD�ϴ� ���Ŭ����
 * 
 * @author acorn
 *
 */
public class HugiDAO {
	/**
	 * ��ȸ���
	 */
	public List<BoardVO> selectAllBoard() {

		List<BoardVO> hugiList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			//sql.append(" select * ");
			sql.append(" select * ");
			//sql.append(" to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append("  from c_hugi_board ");
			//sql.append(" order by board_no desc ");

			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("board_no");
				String title = rs.getString("title");
				String id = rs.getString("id");
				String regDate = rs.getString("reg_date");

				BoardVO board = new BoardVO();
				board.setBoard_no(no);
				board.setTitle(title);
				board.setId(id);
				board.setRegDate(regDate);

				hugiList.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return hugiList;
	}

	/**
	 * �Խù� ������ ���� �����ù�ȣ ����(seq_t_board_no)
	 */

	public int selectNo() {
		String sql = "select seq_c_board_no.nextval from dual ";

		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * �Խñ� �����ϴ� ���
	 */
	public void insertBoard(BoardVO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into c_board( title,contents, id ) ");
			sql.append(" values( ?, ?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());

			int loc = 1;

			pstmt.setString(loc++, board.getTitle());
			pstmt.setString(loc++, board.getId());
			pstmt.setString(loc++, board.getContent());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

	}

	/**
	 * �Խ��� ��ȣ�� ��ȸ�ϴ� ���
	 */
	public BoardVO selectByNo(int board_no) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardVO hugiboard = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select board_no, title, id, contents, cnt ");
			sql.append("     , to_char(reg_date, 'yyyy-MM-dd') as reg_date ");
			sql.append("  from c_board ");
			sql.append(" where board_no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, board_no);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				int boardNo = rs.getInt("board_no");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String content = rs.getString("content");
				int cnt = rs.getInt("cnt");
				String regDate = rs.getString("reg_date");

				hugiboard = new BoardVO(boardNo, title, writer, content, cnt, regDate);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return hugiboard;
	}

	/**
	 * �Խù� �����ϴ� ���
	 */
	public void updateBoard(BoardVO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update c_board ");
			sql.append(" set title = ?, contents = ? ");
			sql.append(" where board_no = ? ");

			int loc = 1;

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(loc++, board.getTitle());
			pstmt.setString(loc++, board.getContent());
			pstmt.setInt(loc++, board.getBoard_no());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}
	}

	/**
	 * cnt�� �����ϴ� ���
	 */

	public void updateViewCnt(int board_no) {
//		finally�� close�� �� �ʿ䰡 ����
		StringBuilder sql = new StringBuilder();
		sql.append("update c_board ");
		sql.append(" set cnt = cnt + 1 ");
		sql.append(" where board_no = ? ");
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setInt(1, board_no);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * Connection conn = null;
		 * 
		 * try { conn = new ConnectionFactory().getConnection(); } catch(Exception e) {
		 * e.printStackTrace(); } finally {
		 * 
		 * }
		 */
	}

	/**
	 * �Խù� �����ϴ� ���
	 */
	public void deleteBoard(int board_no) {

		StringBuilder sql = new StringBuilder();
		sql.append("delete from c_board, c_board_file ");
		sql.append(" where board_no = ?  ");

		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			
			int loc = 1;
			
			pstmt.setInt(loc++, board_no);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ----------------------------------÷������---------------------------------
	/**
	 * ÷������ �����ϴ±��
	 */

	/*public void insertFile(BoardFileVO fileVO) {
//		 System.out.println(fileVO.toString()); -ok
		StringBuilder sql = new StringBuilder();
		sql.append("insert into c_board_file (no, board_no, file_ori_name, file_save_name, file_size)  ");
		sql.append("values (seq_c_board_file_no.nextval, ?, ?, ?, ?) ");

		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		) {
			pstmt.setInt(1, fileVO.getBoard_no());
			pstmt.setString(2, fileVO.getFile_ori_name());
			pstmt.setString(3, fileVO.getFile_save_name());
			pstmt.setInt(4, fileVO.getFile_size());

			int i = pstmt.executeUpdate();
			System.out.println(i);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * �Խù� ��ȣ�� �ش� ÷������ ��ȸ�ϴ� ���
	 */

	/*public List<BoardFileVO> selectFileByNo(int boardNo) {

		List<BoardFileVO> fileList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select no,file_ori_name,file_save_name,file_size ");
		sql.append(" from t_board_file ");
		sql.append(" where board_no = ? ");

		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setInt(1, boardNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardFileVO fileVO = new BoardFileVO();
				fileVO.setNo(rs.getInt("no"));
				fileVO.setFile_ori_name(rs.getString("file_ori_name"));
				fileVO.setFile_save_name(rs.getString("file_save_name"));
				fileVO.setFile_size(rs.getInt("file_size"));

				fileList.add(fileVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;

	}*/

	/**
	 * ÷������ ����
	 */

/*	public void deleteFile(int boardNo) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from t_board_file ");
		sql.append(" where board_no = ?  ");
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setInt(1, boardNo);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PreparedStatement setInt(int i, int boardNo) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
