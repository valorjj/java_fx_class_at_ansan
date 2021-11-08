package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SignupController implements Initializable {

	// 초기화
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblconfirm.setText("");
	}

	@FXML
	private Button btn_back;

	@FXML
	private Button btn_signup;

	@FXML
	private Label lblconfirm;

	@FXML
	private AnchorPane signuppane;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtid;

	@FXML
	private TextField txtname;

	@FXML
	private TextField txtnumber;

	@FXML
	private PasswordField txtpassword;

	@FXML
	private PasswordField txtpasswordconfirm;

	@FXML
	void back(MouseEvent event) {

		try {
			LoginController.getInstance().loadpage("login");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void signup(MouseEvent event) throws IOException {

		// 1. 유효성 검사
		// 1. 아이디 길이 체크
		if (txtid.getText().length() <= 4) {
			lblconfirm.setText("id는 4글자 이상으로 해주세요");
			return;
		}
		// 2. 패스워드 길이 체크
		if (txtpassword.getText().length() < 4 || txtpassword.getText().length() > 9) {
			lblconfirm.setText("비밀번호 길이는 4-8 여야합니다. ");
			return;
		}
		// 3. 페스워드 동일 체크
		if (!txtpassword.getText().equals(txtpasswordconfirm.getText())) {
			lblconfirm.setText("비밀번호가 동일하지 않습니다. ");
			return;
		}
		// 4. 이름 길이 체크
		if (txtname.getText().length() < 2) {
			lblconfirm.setText("이름은 2글자 이상이어야 합니다. ");
			return;
		}
		// 5. 이메일 길이 체크
		if (txtemail.getText().length() < 5 || !txtemail.getText().contains("@")) {
			lblconfirm.setText("올바른 이메일 형식이 아닙니다. ");
			return;
		}

		// 2. 중복 체크
			// java 에서도 가능, db 에서도 가능 ----> unique or pk
		boolean idcheck = MemberDao.getMemberDao().idcheck(txtid.getText());
		if(idcheck) { lblconfirm.setText("현재 사용중인 아이디입니다. "); return; }
			// DB에서 뭔가를 조회하는 것은 MemberDao 클래스에 선언한다.
			// 그리고 그 메소드를 호출해서 변수에 저장해서 조건문을 활용해서 현재 클래스 목적 달성
		

		// 3. 객체화

		Member member = new Member(txtid.getText(), txtpassword.getText(), txtname.getText(), txtemail.getText());

		boolean result = MemberDao.getMemberDao().signup(member);

		if (result) {
			lblconfirm.setText("감사합니다. ");

			// 5. 메시지창 띄우고 화면 전환
			// 1. 메시지 객체 생성
			Alert alert = new Alert(AlertType.INFORMATION);
			// 2. 메시지 내용
			alert.setContentText("KAKAO 회원가입 완료 [포인트 1000 적립]");
			// 3. 메시지 제목
			alert.setHeaderText("회원가입 성공");
			alert.setTitle("알림");
			// 4. 메시지 띄우고 Action 대기
			alert.showAndWait();
			// 5. login_1.fxml 로 이동
			LoginController.getInstance().loadpage("login");

		} else {
			lblconfirm.setText("failed to login .. ");
		}

		// 4. 파일 / DB 처리

	}

}
