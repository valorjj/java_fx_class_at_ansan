package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.BoardDao;
import domain.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CommunityWriteController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_write;

	@FXML
	private TextArea txtcontent;

	@FXML
	private TextField txttitle;

	@FXML
	void cancel(ActionEvent event) {
		MainpageController.getinstance().loadpage("community_list");

	}

	@FXML
	void write(ActionEvent event) {
		Board board = new Board(txttitle.getText(), txtcontent.getText(),
				MainpageController.getinstance().getloginId());

		boolean result = BoardDao.getBoardDao().write(board);
		Alert alert = new Alert(AlertType.INFORMATION);
		if (result) {

			alert.setHeaderText("게시물 등록 성공");
			alert.showAndWait();
			MainpageController.getinstance().loadpage("community_list");

		} else {
			alert.setHeaderText("게시물 등록 실패");
			alert.showAndWait();

		}

	}

}
