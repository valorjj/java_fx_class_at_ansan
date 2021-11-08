package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class ProductListController implements Initializable {

	public static Product product;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btn_delete.setVisible(false);
		btn_update.setVisible(false);
		btn_activation.setVisible(false);
		product_load();

	}

	@FXML
	private Button btn_delete;

	@FXML
	private Button btn_register;

	@FXML
	private Button btn_update;

	@FXML
	private Button btn_activation;

	@FXML
	private Label lbl_p_contents;

	@FXML
	private Label lbl_p_id;

	@FXML
	private Label lbl_p_name;

	@FXML
	private Label lbl_price;

	@FXML
	private ImageView p_img;

	@FXML
	private TableView<Product> product_list;

	@FXML
	Button btn_cancel;

	@FXML
	void activation(ActionEvent event) {

		btn_activation.setText(product.getActivation());// 1. 선택 제품의 상태가 버튼 텍스트에 표시

		int pa = product.getP_activation(); // 2. 선택 제품의 상태를 가져오기 
		int ch = pa + 1; // 3. 누를 때 마다 상태 변경 [1-->2, 2-->3, 3-->1]

		if (ch > 3) {
			ch = 1;
		}

		if (ch == 1) { // 4. 변경된 상태가 1이면 판매중 업데이트 처리 
			ProductDao.getProductDao().activation_update(1, product.getP_no()); // 업데이트 DB 처리 
			product_load(); // 테이블 새로 고침
			btn_activation.setText("판매중"); // 버튼 텍스트 변경 
		}

		if (ch == 2) {
			ProductDao.getProductDao().activation_update(2, product.getP_no());
			product_load();
			btn_activation.setText("거래중");
		}
		if (ch == 3) {
			ProductDao.getProductDao().activation_update(3, product.getP_no());
			product_load();
			btn_activation.setText("판매완료");
		}

	}

	// 테이블 최신화 시키는 메소드 --> 기존 initialize 에서 이사왔음 
	public void product_load() {

		// DB 에서 제품목록 가져오기
		ObservableList<Product> products = ProductDao.getProductDao().productlist();
		// 제품목록을 테이블뷰에 넣기
		product_list.setItems(products);

		TableColumn<?, ?> tc = product_list.getColumns().get(0);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		tc = product_list.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_category"));

		tc = product_list.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		tc = product_list.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("activation"));

		tc = product_list.getColumns().get(4);
		tc.setCellValueFactory(new PropertyValueFactory<>("p_date"));

		// 테이블뷰에서 클릭했을 때, 아이템 가져오기
		// 1. 테이블 뷰에 클릭이벤트

		product_list.setOnMouseClicked(e -> {
			// 클릭 이벤트가 마우스 클릭과 같으면
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				//
				product = product_list.getSelectionModel().getSelectedItem();

				Image image = new Image(product.getP_img());
				p_img.setImage(image);

				lbl_p_name.setText(product.getP_name());
				lbl_p_contents.setText(product.getP_contents());
				lbl_price.setText(String.format("%,d", product.getP_price()));
				String writer = (MemberDao.getMemberDao().m_id_check(product.getM_no()));
				lbl_p_id.setText(writer);

				if (MainpageController.getinstance().getloginId().equals(writer)) {
					btn_delete.setVisible(true);
					btn_update.setVisible(true);
					btn_activation.setVisible(true);

				} else {
					btn_delete.setVisible(false);
					btn_update.setVisible(false);
					btn_activation.setVisible(false);
				}

				btn_activation.setText(product.getActivation());

			}

		});

	}

	@FXML
	void delete(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("제ButtonType까?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			ProductDao.getProductDao().delete(product.getP_no());
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setHeaderText("삭제 되었습니다. ");
			alert2.showAndWait();
			MainpageController.getinstance().loadpage("product_list");
		}

	}

	@FXML
	void register(ActionEvent event) {

		MainpageController.getinstance().loadpage("product_enlist");

	}

	@FXML
	void update(ActionEvent event) {

		MainpageController.getinstance().loadpage("product_update");

	}

	@FXML
	public void cancel(ActionEvent event) {
	}

}
