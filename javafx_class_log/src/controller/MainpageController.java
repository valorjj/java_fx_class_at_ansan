package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainpageController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_login_id.setText(LoginController.getInstance().getid());
		loadpage("graph"); // mainpage 실행 시 가운데 home.fxml 배치
	}

	@FXML
	private Button btn_chat;

	@FXML
	private Button btn_community;

	@FXML
	private Button btn_home;

	@FXML
	private Button btn_info;

	@FXML
	private Button btn_logout;

	@FXML
	private Button btn_product;

	@FXML
	private AnchorPane cp;

	@FXML
	private Label lbl_login_id;

	@FXML
	private AnchorPane lp;

	@FXML
	private BorderPane mainpage_borderpane;

	// 가운데 pane 을 변경하는 메소드

	public static MainpageController instance;

	public MainpageController() {
		instance = this;
	}

	public static MainpageController getinstance() {
		return instance;
	}

	public String getloginId() {
		return lbl_login_id.getText();
	}

	// public String loginid = LoginController.getInstance().getid();

	public void loadpage(String page) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
			mainpage_borderpane.setCenter(parent);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void chat(ActionEvent event) {
		loadpage("chatting");
	}

	@FXML
	void community(ActionEvent event) {
		loadpage("community_list");

	}

	@FXML
	void home(ActionEvent event) {
		loadpage("graph");

	}

	@FXML
	void info(ActionEvent event) {
		loadpage("myinfo");

	}

	// 로그아웃 메소드 [현재 스테이지 닫고 -> login 스테이지 열기]
	@FXML
	void logout(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		// Confirmation ----> OK / Cancel
		// AlertType.ERROR
		// AlertType.
		alert.setContentText("LOGOUT");
		alert.setHeaderText("로그아웃 하시겠습니까? ");
		alert.setTitle("확인");

		// 메시지 버튼을 눌렀을 때
		// Optional 클래스 : null 포함하는 클래스
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) { // 버튼 타입이 OK 이면

			// 1. 현재 스테이지 숨기기
			btn_logout.getScene().getWindow().hide();

			// 2. login 스테이지 열기
			Stage root = new Stage();
			Parent parent;
			try {
				parent = FXMLLoader.load(getClass().getResource("/CLASS_JAVAFX_EZEN/src/fxml/login_1.fxml"));
				Scene scene = new Scene(parent);
				root.setScene(scene);
				root.setResizable(false);
				root.setTitle("mainPage");
				Image image = new Image("/CLASS_JAVAFX_EZEN/src/fxml/insta_icon_1.png");
				root.getIcons().add(image);
				root.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 버튼 타입이 Cancel 일 때

		}
	}

	@FXML
	void product(ActionEvent event) {
		loadpage("product_list");

	}

}
