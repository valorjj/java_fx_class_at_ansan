package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BoardDao;
import domain.Board;
import domain.Reply;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommunitySpecificController implements Initializable {

	// 전역변수 사용 --> 해당 클래스의 메소드에 board 객체 전달해서 사용할 계획
	Board board = CommunitylistController.board;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		BoardDao.getBoardDao().viewupdate(board.getB_no());

		txt_title.setText(board.getB_title());
		txt_contents.setText(board.getB_contents());

		lbl_writer.setText("작성자 : " + board.getB_writer());
		lbl_date.setText("작성일 : " + board.getB_date().split(" ")[0]);
		lbl_view.setText("조회수 : " + board.getB_view() + "");

		if (!MainpageController.getinstance().getloginId().equals(board.getB_writer())) {
			// 게시물 작성자와 로그인된 아이디가 다를 경우 버튼 숨기기
			// 일치해야만 표시
			btn_delete.setVisible(false);
			btn_update.setVisible(false);
		}

	}

	@FXML
	private TextArea txt_reply;

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_delete;

	@FXML
	private Button btn_reply_write;

	@FXML
	private Button btn_update;

	@FXML
	private Label lbl_reply;

	@FXML
	private TableView<Reply> reply_list;

	@FXML
	private TextField txt_contents;

	@FXML
	private TextField txt_title;

	@FXML
	private Label lbl_date;

	@FXML
	private Label lbl_view;

	@FXML
	private Label lbl_writer;

	@FXML
	void cancel(ActionEvent event) {

		MainpageController.getinstance().loadpage("community_list");

	}

	@FXML
	void delete(ActionEvent event) {
		// 작성자 == 로그인 id 동일하면 삭제처리

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("해당 게시물을 삭제하시겠습니까?");
		alert.setTitle("[알림]");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			// OK 누르면 삭제 진행
			boolean res = BoardDao.getBoardDao().delete(board.getB_no());
			if (res) {
				MainpageController.getinstance().loadpage("community_list");
			} else {
				// DB 오류는 거의 일어나지 않는다. 여기서는 생략
				// Alert alert2 만들어서 메시지 새로 출력해야할듯
			}
		} else {

		}

	}

	@FXML
	void reply_write(ActionEvent event) {

		Reply reply = new Reply(txt_reply.getText(), MainpageController.getinstance().getloginId(), board.getB_no());
		boolean res = BoardDao.getBoardDao().replywrite(reply);

		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("댓글 등록");
			alert.showAndWait();

			replytableload();

			txt_reply.setText("");

		} else {

		}

	}

	boolean updateCheck = true; // 상태 제어 하는 스위치 역할

	@FXML
	public void update() {
		Alert alert = new Alert(AlertType.INFORMATION);

		if (updateCheck) { // 업데이트 전
			alert.setHeaderText("내용 수정 후 다시 버튼 클릭시 수정이 완료됩니다. ");
			alert.setTitle("[알림]");
			alert.showAndWait();

			txt_title.setEditable(true);
			txt_contents.setEditable(true);
			updateCheck = false;
		} else {

			// DB 처리

			BoardDao.getBoardDao().delete(board.getB_no());

			alert.setHeaderText("게시물 수정 완료 ");
			alert.setTitle("[알림]");
			alert.showAndWait();
			updateCheck = true;
			txt_title.setEditable(false);
			txt_contents.setEditable(false);

		}

	}

	public void replytableload() {
		ObservableList<Reply> replys = BoardDao.getBoardDao().replylist(board.getB_no());

		TableColumn<?, ?> tc = reply_list.getColumns().get(0);
		tc.setCellValueFactory((new PropertyValueFactory<>("r_no")));

		tc = reply_list.getColumns().get(1);
		tc.setCellValueFactory((new PropertyValueFactory<>("r_write")));

		tc = reply_list.getColumns().get(2);
		tc.setCellValueFactory((new PropertyValueFactory<>("r_contents")));

		tc = reply_list.getColumns().get(3);
		tc.setCellValueFactory((new PropertyValueFactory<>("r_date")));

		reply_list.setItems(replys);

	}

}
