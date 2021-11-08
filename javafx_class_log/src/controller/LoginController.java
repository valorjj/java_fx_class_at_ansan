
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lblconfirm.setText(""); // 첫 화면에서는 경고 메시지 숨기기

	}

	// 현재 클래스를 객체화 [ 클래스 자체의 객체 ]
	// 1. static 변수 선언
	private static LoginController instance;

	public LoginController() {
		// this : this 만 사용하면 자체(현재) 클래스의 메모리를 instance 에 집어넣겠다는 뜻
		instance = this; // 현재 클래스의 멤버가 포함되어있다.
	}

	public static LoginController getInstance() {
		return instance;
	}

	public static void setInstance(LoginController instance) {
		LoginController.instance = instance;
	}

	@FXML
	private Label btn_findid;

	@FXML
	private Label btn_findpassword;

	@FXML
	private Button btn_login;

	@FXML
	private Label btn_signup;

	@FXML
	private Label lblconfirm;

	@FXML
	private AnchorPane loginpane;

	@FXML
	private BorderPane mainborderpane;

	@FXML
	private TextField txtid;

	@FXML
	private PasswordField txtpassword;

	@FXML
	void findid(MouseEvent event) {

		try {
			loadpage("findid_1");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void findpassword(MouseEvent event) {
		try {
			loadpage("findpassword_1");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void signin(ActionEvent event) {

		// 로그인 했을 때, Dao 객체 메소드 호출

		boolean result = MemberDao.getMemberDao().login(txtid.getText(), txtpassword.getText());

		if (result) {
			lblconfirm.setText("success");
			// txt 필드에 있는 값을 인수로 전달한다. 실제 코드는 MemberDao 에 있다.

			MemberDao.getMemberDao().pointUpdate(txtid.getText(), 10);
			// 기존 스테이지 종료

			btn_login.getScene().getWindow().hide();

			// 메인 페이지 스테이지 실행
			Stage stage = new Stage();
			try {

				Parent parent = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
				Scene scene = new Scene(parent);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("mainPage");
				stage.showAndWait();

			} catch (Exception e) {
			}

		} else {
			lblconfirm.setText(" error ");
		}
	}

	@FXML
	void signup(MouseEvent event) {
		try {
			loadpage("signup_1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// borderpane center 변경
	public void loadpage(String page) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
		// page 를 이용해서 center 를 바꾼다.
		mainborderpane.setCenter(parent);
	}

	// 로그인 시 입력된 아이디 변환
	public String getid() {
		return txtid.getText();
	}

	public BorderPane getMainborderpane() {
		return mainborderpane;
	}

	public void setMainborderpane(BorderPane mainborderpane) {
		this.mainborderpane = mainborderpane;
	}

}
