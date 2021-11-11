package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.Board;
import domain.Reply;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardDao {

	
	// 누구쎄용
	// [필드]
	private Connection connection; // 연결 인터페이스 선언
	private PreparedStatement psmt; // SQL 연결 인터페이스
	private ResultSet resultSet; // 쿼리(Query) (검색결과) 연결 인터페이스

	// 객체 만들기
	private static BoardDao boardDao = new BoardDao();

	public static BoardDao getBoardDao() {
		return boardDao;
	}

	public BoardDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javafx_1?serverTimezone=UTC", "root",
					"1234");
			System.out.println("connection success .. ");
		} catch (Exception e) {
			System.out.println("db synchronized failed .. " + e);
		}
	}

	public boolean write(Board board) {
		String sql = "insert into board(b_title, b_contents, b_write)" + "values(?, ?, ?)";

		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, board.getB_title());
			psmt.setString(2, board.getB_contents());
			psmt.setString(3, board.getB_writer());
			psmt.executeUpdate(); // insert 할 때는 excuteUpdate();
			return true;

		} catch (Exception e) {
		}
		return false;
	}

	public ObservableList<Board> boardlist() {
		String sql = "SELECT * from board order by b_no desc"; // 1. 조건없이 모두 출력
		ObservableList<Board> boards = FXCollections.observableArrayList();
		try {
			psmt = connection.prepareStatement(sql);
			resultSet = psmt.executeQuery();
			// 2. 검색된[쿼리] 레코드의 하나씩 객체화
			while (resultSet.next()) {
				Board board = new Board(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
				// 3. 객체를 리스트에 담기
				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
		}
		return boards;
	}

	// 게시물 조회수 증가 메소드
	public boolean viewupdate(int b_no) {
		String sql = "UPDATE board SET b_view=b_view+1 WHERE b_no=?";

		try {
			psmt = connection.prepareStatement(sql);
			psmt.setInt(1, b_no);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 게시물 삭제 메소드
	public boolean delete(int b_no) {
		String sql = "DELETE FROM board WHERE b_no=?";
		try {
			psmt = connection.prepareStatement(sql);
			psmt.setInt(1, b_no);
			psmt.executeUpdate();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	// 게시물 수정 메소드
	public boolean update(int b_no, String b_title, String b_contents) {
		String sql = "UPDATE board SET b_title=?, b_contents=? WHERE b_no=?";
		try {
			psmt = connection.prepareStatement(sql);

			psmt.setString(1, b_title);
			psmt.setString(2, b_contents);
			psmt.setInt(3, b_no);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 댓글 작성 메소드
	public boolean replywrite(Reply reply) {

		String sql = "INSERT into reply(r_contents, r_write, r_b_no) values(?, ?, ?) ";

		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, reply.getR_contents());
			psmt.setString(2, reply.getR_write());
			psmt.setInt(3, reply.getR_b_no());
			psmt.executeUpdate();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	// 댓글 출력 메소드

	public ObservableList<Reply> replylist(int r_b_no) {

		ObservableList<Reply> replys = FXCollections.observableArrayList();
		String sql = "SELECT * FROM reply WEHRE r_b_no=? ORDER BY r_no DESC";
		try {
			psmt = connection.prepareStatement(sql);

			psmt.setInt(1, r_b_no);
			resultSet = psmt.executeQuery();

			while (resultSet.next()) {
				Reply reply = new Reply(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getInt(5));
				replys.add(reply);
			}
			return replys;

		} catch (Exception e) {
		}
		return replys;
	}
	
	// 로그인 된 회원의 게시물 출력
	public ObservableList<Board> myboardlist(String id) {
		ObservableList<Board> boards = FXCollections.observableArrayList();
		String sql = "select * from board where b_write=? order by b_no desc"; // 1. 조건없이 모두 출력
		try {
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, id);
			resultSet = psmt.executeQuery();
			// 2. 검색된[쿼리] 레코드의 하나씩 객체화
			while (resultSet.next()) {
				Board board = new Board(
						resultSet.getInt(1), 
						resultSet.getString(2), 
						resultSet.getString(3),
						resultSet.getString(4), 
						resultSet.getString(5), 
						resultSet.getInt(6));
				// 3. 객체를 리스트에 담기
				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
		}
		return boards;
	}
	
	
	
	public int board_count() {

		String sql = "select count(*) from board ";

		try {
			psmt = connection.prepareStatement(sql);
			resultSet = psmt.executeQuery();
			// resultSet 처음값이 null 값이 들어가있다.
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	
	
	
	
	
	
	
	
	

	
	

}
