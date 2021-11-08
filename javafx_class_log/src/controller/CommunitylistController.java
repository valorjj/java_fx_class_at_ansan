package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.BoardDao;
import domain.Board;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

public class CommunitylistController implements Initializable {

	// 현재 클래스의 객체화
	public static Board board;

	@FXML
	private TableView<Board> boardlist;

	@FXML
	private Button btn_write;

	@FXML
	void write(ActionEvent event) {
		// 쓰기 페이지로 loadpage
		MainpageController.getinstance().loadpage("community_write");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 1. DAO 호출
		ObservableList<Board> boards = BoardDao.getBoardDao().boardlist();
		// 2. 테이블뷰의 필드 가져오기
		TableColumn<?, ?> tc = boardlist.getColumns().get(0); // 테이블뷰의 첫번째 필드
		tc.setCellValueFactory(new PropertyValueFactory<>("b_no"));

		// 테이블 뷰의 두번째 필드
		tc = boardlist.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));
		// 테이블 뷰의 세번째 필드
		tc = boardlist.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_writer"));
		// 테이블 뷰의 네번째 필드
		tc = boardlist.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));
		// 테이블 뷰의 다섯번째 필드
		tc = boardlist.getColumns().get(4);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));

		// 3. 테이블뷰에 리스트 설정
		boardlist.setItems(boards);

		// 4. 클릭한 아이템 가지고 페이지 전환
		// 람다식 이용 e -> { ... }
		// { 정의 return }
		// 인수 -> 정의 : 익명 메소드
		// 일회성 : 한번 쓰고 메모리 해제
		boardlist.setOnMouseClicked(e -> {

			if (e.getButton().equals(MouseButton.PRIMARY)) { // 해당 이벤트가 클릭이면
				// 테이블뷰에 선택된 모델의 아이템 값[->객체]
				//
				board = boardlist.getSelectionModel().getSelectedItem();
				// 조회수 증가
				MainpageController.getinstance().loadpage("boardview");

			}

		});

	}

}
