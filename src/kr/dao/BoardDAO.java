package kr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.util.ConnectionFactory;
import kr.util.JDBCClose;
import kr.vo.BoardFileVO;
import kr.vo.BoardVO;
import kr.vo.CommentVO;

public class BoardDAO {
	public List<BoardVO> selectAllBoard() {

		List<BoardVO> boardList = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" select * ");
			//sql.append(" to_char(reg_date, 'yyyy-mm-dd') as reg_date ");
			sql.append("  from c_board ");
			sql.append(" order by board_no desc ");

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

				boardList.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

		return boardList;
	}

	/**
	 * 게시물 삽입을 위한 시퀀시번호 추출(seq_t_board_no)
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
	 * 게시글 삽입하는 기능
	 */
	public void insertBoard(BoardVO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into c_board( title, contents ) ");
			sql.append(" values( ?, ? ) ");
			pstmt = conn.prepareStatement(sql.toString());

			int loc = 1;

			pstmt.setString(loc++, board.getTitle());
			pstmt.setString(loc++, board.getContent());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(pstmt, conn);
		}

	}

	/**
	 * 게시판 번호로 조회하는 기능
	 */
	public BoardVO selectByNo(int boardNo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardVO board = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select * ");
			sql.append("  from c_board ");
			sql.append(" where board_no = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			
			int loc = 1;
			pstmt.setInt(loc++, boardNo);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int no = rs.getInt("board_no");
				String title = rs.getString("title");
				String id = rs.getString("id");
				String content = rs.getString("content");
				int cnt = rs.getInt("cnt");
				String regDate = rs.getString("reg_date");
				
				board = new BoardVO(no, title, id, content, cnt, regDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return board;
	}

	/**
	 * 게시물 수정하는 기능
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
	 * cnt를 증가하는 기능
	 */

	public void updateViewCnt(int board_no) {
//		finally에 close를 할 필요가 없다
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
	 * 게시물 삭제하는 기능
	 * @return 
	 */
	public int deleteBoard(int no) {

		StringBuilder sql = new StringBuilder();
		sql.append("delete from c_board, c_board_file ");
		sql.append(" where board_no = ?  ");
		
		int result = 0;
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			
			int loc = 1;
			
			pstmt.setInt(loc++, no);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	public int cntAllRows() {
		
		String sql="select count(*) as count from c_board";
		int cntRows=0;
		
		try(
			Connection conn=new ConnectionFactory().getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql);	
		){
			ResultSet rs=pstmt.executeQuery();
			
			if(rs.next()) {
				cntRows = rs.getInt("count");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cntRows;
	}
	
	public List<BoardVO> getPage(int pageNo) {
		
		List<BoardVO> boardList=new ArrayList<>();
		
		StringBuilder sql=new StringBuilder();
		sql.append(" select *  ");
		sql.append(" from ( select rowNum as rnum, c.* ");
		sql.append(" from ( select * from c_board order by board_no desc) c ) ");
		sql.append(" where rnum between ? and ? ");
		
		try(
			Connection conn=new ConnectionFactory().getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());	
		){
			pstmt.setInt(1, (pageNo*5)-4);
			pstmt.setInt(2, pageNo*5);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board=new BoardVO();
				board.setBoard_no(rs.getInt("board_no"));
				board.setTitle(rs.getString("title"));
				board.setId(rs.getString("id"));
				board.setContent(rs.getString("content"));
				board.setCnt(rs.getInt("cnt"));
				board.setRegDate(rs.getString("reg_date"));
				//System.out.println(board.toString()); //board객체확인 Ok 
				boardList.add(board);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}
	
	public int insertComment(CommentVO comment) {
		
		int commentResult = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into c_comment(no,writer_id,post_no,content) ");
		sql.append(" values (seq_c_comment_no.nextval, ?,?,?) ");
		
		try(
				Connection conn=new ConnectionFactory().getConnection();
				PreparedStatement pstmt=conn.prepareStatement(sql.toString());	
			){
			int i=1;
			pstmt.setString(i++, comment.getWriter());
			pstmt.setInt(i++, comment.getPost_no());
			pstmt.setString(i++, comment.getContent());
			commentResult = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return commentResult;
	}
	
	public List<CommentVO> selectAllComment() {
		List<CommentVO> comments =new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from c_comment ");
		CommentVO CVO = null;
		
		try(
				Connection conn=new ConnectionFactory().getConnection();
				PreparedStatement pstmt=conn.prepareStatement(sql.toString());	
			){
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CVO = new CommentVO();
				CVO.setNo(rs.getInt("no"));
				CVO.setWriter(rs.getString("writer_id"));
				CVO.setPost_no(rs.getInt("post_no"));
				CVO.setContent(rs.getString("content"));
				CVO.setRegDate(rs.getString("reg_date"));
				comments.add(CVO);
				System.out.println(CVO.toString());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	
	
	
	
	
	
	
	
}
