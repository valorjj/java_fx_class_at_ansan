package domain;

//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Properties;

public class Member {

	/*
	 * 멤버 설계
	 */

	// [필드]
	private String member_id;
	private String member_password;
	private String member_name;
	private String member_email;
	private int member_point;

	// [생성자]

	public Member() {
	}

	// DB 에서 꺼내올 때
	public Member(String member_id, String member_password, String member_name, String member_email, int member_point) {
		this.member_id = member_id;
		this.member_password = member_password;
		this.member_name = member_name;
		this.member_email = member_email;
		this.member_point = member_point;
	}

	// 회원 가입 시에는 포인트를 제외시키고 초기값으로 1000 부여
	public Member(String member_id, String member_password, String member_name, String member_email) {
		this.member_id = member_id;
		this.member_password = member_password;
		this.member_name = member_name;
		this.member_email = member_email;

		this.member_point = 1000;
	}

	// 회원 수정 시 사용되는 생성자 - 이름과 이메일
	public Member(String member_name, String member_email) {
		this.member_name = member_name;
		this.member_email = member_email;
	}

	// [메소드]
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public int getMember_point() {
		return member_point;
	}

	public void setMember_point(int member_point) {
		this.member_point = member_point;
	}

	// email 전송 메소드

//	public static void sendmail(String tomail, String msg, int type) {
//
//		String fromemail = "valorjj@gmail.com";
//		String frompassword = "frhcsqpwwxdrekks";
//
//		Properties properties = new Properties();
//
//		properties.put("mail.transport.protocol", "smtp");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "465");
//		properties.put("mail.smtp.auth", "true");
//
//		properties.put("mail.smtp.quitwait", "false");
//		properties.put("mail.smtp.socketFactory.port", "465");
//		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		properties.put("mail.smtp.socketFactory.fallback", "false");
//
//	}

}
