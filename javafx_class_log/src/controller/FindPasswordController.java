package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FindPasswordController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lblconfirm.setText("");
	}

	@FXML
	private Button find_password_btn_back;

	@FXML
	private Button find_password_btn_sendmail;

	@FXML
	private AnchorPane find_password_pane;

	@FXML
	private TextField find_password_txtemail;

	@FXML
	private TextField find_password_txtid;

	@FXML
	private AnchorPane findpassword_pane;

	@FXML
	private Label lblconfirm;

	@FXML
	void back(ActionEvent event) {

		try {
			LoginController.getInstance().loadpage("login_1");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	@FXML
//	void find_password_sendmail(ActionEvent event) {
//
//		String res = MemberDao.getMemberDao().find_password(find_password_txtid.getText(),
//				find_password_txtemail.getText());
//
//		if (res != null) {
//			Member.sendmail(find_password_txtemail.getText(), res, 2);
//			lblconfirm.setText("확인 이메일을 보냈습니다. ");
//
//		} else {
//			lblconfirm.setText("일치하는 회원정보가 존재하지 않습니다. ");
//		}
//
//	}

}
