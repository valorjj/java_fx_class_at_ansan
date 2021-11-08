package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import dao.ProductDao;
import domain.Board;
import domain.Member;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MyinfoController implements Initializable {

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_update;

	@FXML
	private Label lbl_id;

	@FXML
	private Label lbl_name;

	@FXML
	private Label lbl_point;

	@FXML
	private TextArea update_email;

	@FXML
	private TextArea update_name;

	@FXML
	private TableView<Board> myboard_list;

	@FXML
	private TableView<Product> myproduct_list;

	@FXML
	void cancel(ActionEvent event) {
		MainpageController.getinstance().loadpage("myinfo");

	}

	@FXML
	void update(ActionEvent event) {

		boolean res = MemberDao.getMemberDao().update(lbl_id.getText(), update_name.getText(), update_email.getText());
		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("회원정보 수정 완료");
			alert.setTitle("[알림]");
			alert.showAndWait();
			MainpageController.getinstance().loadpage("myinfoupdate");

		} else {

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String loginid = MainpageController.getinstance().getloginId();
		Member member = MemberDao.getMemberDao().getmember(loginid);

		// 내가 쓴 글 가져오기
		ObservableList<Board> boards = BoardDao.getBoardDao().myboardlist(loginid);
		myboard_list.setItems(boards);

		TableColumn<?, ?> tc = myboard_list.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_no"));

		tc = myboard_list.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_title"));

		tc = myboard_list.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_date"));

		tc = myboard_list.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("b_view"));

		int m_no = MemberDao.getMemberDao().m_no_check(loginid);
		ObservableList<Product> products = ProductDao.getProductDao().myproductlist(m_no);

		myproduct_list.setItems(products);

		tc = myproduct_list.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		tc = myproduct_list.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));

		tc = myproduct_list.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		tc = myproduct_list.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_activation"));
		tc = myproduct_list.getColumns().get(4);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));

		lbl_id.setText(member.getMember_id());
		update_name.setText(member.getMember_name());
		update_email.setText(member.getMember_email());
		lbl_point.setText(member.getMember_point() + "");

	}

}
