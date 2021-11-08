package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class graphController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lbl_controller_1.setText(MemberDao.getMemberDao().member_count()+"");
		lbl_controller_1.setAlignment(Pos.CENTER);
		lbl_controller_2.setText("88");
		lbl_controller_2.setAlignment(Pos.CENTER);
		lbl_controller_3.setText("77");
		lbl_controller_3.setAlignment(Pos.CENTER);
	}

	@FXML
	private Label lbl_controller_1;
	@FXML
	private Label lbl_controller_2;
	@FXML
	private Label lbl_controller_3;

}
