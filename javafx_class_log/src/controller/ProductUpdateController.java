package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.ProductDao;
import domain.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ProductUpdateController implements Initializable {
	Product product = ProductListController.product; // 테이블 뷰에서 클릭된 객체

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txt_p_name.setText(product.getP_name()); // 클릭된 객체 제품명
		txt_p_contents.setText(product.getP_contents()); // 클릭된 객체 제품 내용
		txt_p_price.setText(product.getP_price() + ""); // 클릭된 객체 제품 가격
		Image image = new Image(product.getP_img()); // 클릭된 객체 내 이미지 경로
		p_img.setImage(image); // * 이미지 세팅
		lbl_img_path.setText(product.getP_img()); // * 이미지 경로
		pimage = product.getP_img();

		// ---- 라디오 버튼 ----
		if (product.getP_category().equals("아이폰13")) {
			opt_1.setSelected(true);
		}
		if (product.getP_category().equals("아이폰12")) {
			opt_2.setSelected(true);
		}
		if (product.getP_category().equals("아이폰11")) {
			opt_3.setSelected(true);
		}

	}

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_image_add;

	@FXML
	private Button btn_update;

	@FXML
	private ToggleGroup iphone;

	@FXML
	private Label lbl_img_path;

	@FXML
	private RadioButton opt_1;

	@FXML
	private RadioButton opt_2;

	@FXML
	private RadioButton opt_3;

	@FXML
	private ImageView p_img;

	@FXML
	private TextArea txt_p_contents;

	@FXML
	private TextField txt_p_name;

	@FXML
	private TextField txt_p_price;

	@FXML
	void cancel(ActionEvent event) {

	}

	@FXML
	void image_add(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("그림파일만가능", "*jpg", "*png", "*gif"));
		File file = fileChooser.showOpenDialog(stage);

		lbl_img_path.setText("파일 경로 : " + file.getPath());
		pimage = file.toURI().toString();
		Image image = new Image(pimage);
		p_img.setImage(image);
	}

	private Stage stage; // 파일 선택 스페이지
	private String pimage; // 선택된 이미지 파일의 경로 저장

	@FXML
	void update(ActionEvent event) {
		String p_name = txt_p_name.getText();
		String p_contents = txt_p_contents.getText();
		int p_price = Integer.parseInt(txt_p_price.getText());

		String category = "";
		if (opt_1.isSelected()) {
			category = "아이폰13";
		}
		if (opt_2.isSelected()) {
			category = "아이폰12";
		}
		if (opt_3.isSelected()) {
			category = "아이폰11";
		}

		// 객체화
		Product product2 = new Product(
				product.getP_no(), 
				p_name, 
				pimage, 
				p_contents, 
				category, 
				p_price, 
				0, 
				"0", 
				0);
		// DB 넣기

		boolean res = ProductDao.getProductDao().update(product2);
		
		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("성공");
			alert.showAndWait();

			MainpageController.getinstance().loadpage("product_list");
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("실패");
			alert.showAndWait();
		}

	}

}
