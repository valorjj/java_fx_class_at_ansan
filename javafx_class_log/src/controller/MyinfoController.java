package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MyinfoController implements Initializable {

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
		tc.setCellValueFactory(new PropertyValueFactory<>("activation"));
		tc = myproduct_list.getColumns().get(4);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));

		lbl_id.setText(member.getMember_id());
		lbl_name.setText(member.getMember_name());
		lbl_email.setText(member.getMember_email());
		lbl_point.setText(member.getMember_point() + "");

	}

	@FXML
	private Button btn_delete;

	@FXML
	private Button btn_update;

	@FXML
	private Label lbl_email;

	@FXML
	private Label lbl_id;

	@FXML
	private Label lbl_name;

	@FXML
	private Label lbl_point;

	@FXML
	private TableView<Product> myproduct_list;

	@FXML
	private TableView<Board> myboard_list;

	@FXML
	void delete(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("[알림]");
		alert.setContentText("회원탈퇴");
		alert.setHeaderText("정말 회원 탈퇴를 진행하시겠습니까?");

		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			// 회원탈퇴 진행
			boolean res = MemberDao.getMemberDao().delete(lbl_id.getText());

			Alert alert2 = new Alert(AlertType.INFORMATION);

			if (res) {
				// 탈퇴 성공 시, 로그아웃 진행
				// 1. 현재 스테이지 숨기기
				btn_delete.getScene().getWindow().hide();

				// 2. login 스테이지 열기
				Stage root = new Stage();
				Parent parent;
				try {
					parent = FXMLLoader.load(getClass().getResource("/fxml/login_1.fxml"));
					Scene scene = new Scene(parent);
					root.setScene(scene);
					root.setResizable(false);
					root.setTitle("mainPage");
					Image image = new Image("file:D:\\jj_ansan_ezen\\CLASS_JAVAFX_EZEN\\src\\fxml\\insta_icon_1.png");
					root.getIcons().add(image);
					root.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				alert2.setHeaderText("DB 오류발생, 관리자에게 문의바 ");
				alert2.setTitle("[알림]");
				alert2.showAndWait();

			}
		} else {
			// 걍 냅둬
		}

	}

	@FXML
	void update(ActionEvent event) {

	}

}
