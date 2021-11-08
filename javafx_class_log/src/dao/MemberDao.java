package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Member;

public class MemberDao {

	// JDBC 주요 인터페이스, 클래스
	// 1. Connection : DB연결 인터페이스 [DriverManager 클래스]

	// [필드]
	private Connection connection; // 연결 인터페이스 선언
	private PreparedStatement preparedStatement; // SQL 연결 인터페이스
	private ResultSet resultSet; // 쿼리(Query) (검색결과) 연결 인터페이스

	private static MemberDao memberDao = new MemberDao();

	// [생성자]

	public MemberDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javafx_1?serverTimezone=UTC", "root",
					"1234");
			System.out.println("connection success .. ");
		} catch (Exception e) {
			System.out.println("db synchronized failed .. " + e);
		}
	}

	// [메소드]

	public static MemberDao getMemberDao() {
		return memberDao;
	}

	// [기능 메소드]
	// 1. 회원가입 메소드
	public boolean signup(Member member) {

		/////////////////////////////////////////////////////////////////////

		// SQL 작성 [ SQL : DB 조작어 DML ]
		String sql = "insert into member(m_id, m_password, m_name, m_email, m_point)" + "values(?, ?, ?, ?, ?)";

		/////////////////////////////////////////////////////////////////////

		try {
			// SQL ---> DB 연결
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, member.getMember_id());
			preparedStatement.setString(2, member.getMember_password());
			preparedStatement.setString(3, member.getMember_name());
			preparedStatement.setString(4, member.getMember_email());
			preparedStatement.setInt(5, member.getMember_point());

			preparedStatement.executeUpdate();

			return true;

		}

		/////////////////////////////////////////////////////////////////////

		catch (SQLException e) {
			System.out.println("connections failed .. ");
			e.printStackTrace();
			return false;
		}

		// SQL 조작 [ ? : 와일드 카드 : Data 넣기 ]

	}

	// 2. 로그인 메소드

	public boolean login(String id, String password) {

		// 1. SQL 작성

		String sql = "select * from member where m_id=? and m_password=?";

		// 2. SQL DB 연결

		try {
			preparedStatement = connection.prepareStatement(sql);

			// 3. SQL 설정

			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);

			// 4. SQL 실행

			resultSet = preparedStatement.executeQuery();

			// 5. SQL 결과 확인

			if (resultSet.next()) { // Query 결과에 다음 내용이 있으면 true
				return true; // 로그인 성공
			} else {
				return false; // 로그인 실패
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false; // DB 오류

	}

	// 3. 아이디 찾기 메소드

	public String find_id(String name, String email) {

		// 1. SQL 작성

		String sql = "select m_id from member where m_name=? and m_email=?";
		// 2. SQL ---> DB 연결
		try {
			preparedStatement = connection.prepareStatement(sql);
			// 3. SQL 설정
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			// 4. SQL 실행
			resultSet = preparedStatement.executeQuery();
			// 5. SQL 결과
			if (resultSet.next()) {
				return resultSet.getString(1);
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String find_password(String id, String email) {

		String sql = "select m_password from member where m_id=? and m_email=?";
		// 2. SQL ---> DB 연결
		try {
			preparedStatement = connection.prepareStatement(sql);
			// 3. SQL 설정
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, email);
			// 4. SQL 실행
			resultSet = preparedStatement.executeQuery();
			// 5. SQL 결과
			if (resultSet.next()) {
				return resultSet.getString(1);
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	// 4. 패스워드 찾기 메소드

	// 5. 회원수정 메소드

	public boolean update(String id, String name, String email) {

		String sql = "update member from set m_name=?, m_email=? where m_id= ?";
		// update 테이블명 set 변경필드=값, 변경필드2=값2, where ----> 조건;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	// 6. 회원탈퇴 메소드

	public boolean delete(String loginid) {
		String sql = "delete from member where m_id=?";
		// String sql = "delete from member"; ----> 전부 삭제

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginid);
			preparedStatement.executeUpdate();
			return true; // 삭제 성공 시 true 리턴
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // db 오류

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 7. 회원 조회 메소드
	public Member getmember(String loginid) {

		// 1. SQL 작성
		String sql = "select * from member where m_id=?";

		// 2. SQL 연결

		try {
			preparedStatement = connection.prepareStatement(sql);

			// 3. SQL 설정
			preparedStatement.setString(1, loginid);
			// 4. SQL 실행
			resultSet = preparedStatement.executeQuery();
			// 5. SQL결과
			if (resultSet.next()) {
				// 패스워드를 제외한 회원 정보 출력
				Member member = new Member(
						resultSet.getString(2), 
						"", 
						resultSet.getString(4), 
						resultSet.getString(5),
						resultSet.getInt(6));
				return member;

			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // db 오류

	}

	// 아이디 체크 메소드
	public boolean idcheck(String id) {

		String sql = "select m_id from member where m_id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true; // 중복 아이디가 존재하면

			} else {
				return false; // 중복 아이디가 존재하지 않으면
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true; // DB 오류
	}

	// 포인트 증감 메소드
	public boolean pointUpdate(String id, int point) {
		String sql = "update member set m_point = m_point+? where m_id=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, point);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원id 의 회원번호 찾기 메소드

	public int m_no_check(String id) {

		String sql = "select m_no from member where m_id=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0; // 회원 번호가 없다는 뜻
			}
		} catch (Exception e) {
		}
		return 0;

	}

	public String m_id_check(int m_no) {

		String sql = "select m_id from member where m_no=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, m_no);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			} else {
				return ""; // 회원 번호가 없다는 뜻
			}
		} catch (Exception e) {
		}
		return "";

	}

}
