package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FindIdController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblconfirm.setText("");
	}

	@FXML
	private Button btn_back;

	@FXML
	private Button btn_findid;

	@FXML
	private AnchorPane find_id_pane;

	@FXML
	private Label lblconfirm;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	void back(ActionEvent event) {
		try {
			LoginController.getInstance().loadpage("login_1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void findid(ActionEvent event) {
		String res = MemberDao.getMemberDao().find_id(txtname.getText(), txtemail.getText());
		if (res == null) {
			lblconfirm.setText("아이디 찾기 실패");
		} else {
			lblconfirm.setText("회원님의 아이디 : " + res);
		}
	}

}
