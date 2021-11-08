package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
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

public class ProductRegisterController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	private Button btn_cancel;

	@FXML
	private Button btn_image_add;

	@FXML
	private Button btn_register;

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

		MainpageController.getinstance().loadpage("product_list");

	}

	@FXML
	void register(ActionEvent event) {

		// 유효성 검사 생략

		String pname = txt_p_name.getText();
		String pcontents = txt_p_contents.getText();
		int pprice = Integer.parseInt(txt_p_price.getText());
		String pcategory = "";
		if (opt_1.isSelected()) {
			pcategory = "아이폰13";
		}
		if (opt_2.isSelected()) {
			pcategory = "아이폰12";
		}
		if (opt_3.isSelected()) {
			pcategory = "아이폰11";
		}
		// 로그인 된 ID 는 Main Controller 에 있다.
		int m_no = MemberDao.getMemberDao().m_no_check(MainpageController.getinstance().getloginId());

		// 객체화 --> 상태 초기값 1
		Product product = new Product(pname, pimage, pcontents, pcategory, pprice, 1, m_no);
		boolean res = ProductDao.getProductDao().register(product);
		Alert alert = new Alert(AlertType.INFORMATION);
		if (res) {
			alert.setHeaderText("제품 등록 성공");
			alert.showAndWait();
			MainpageController.getinstance().loadpage("product_list");
		}

	}

	private String pimage; // 파일 경로 저장할 변수
	private Stage stage; // 파일경로 찾을 화면

	@FXML
	void image_add(ActionEvent event) {
		// 1. 파일 선택 클래스
		FileChooser fileChooser = new FileChooser(); // 파일 선택시 경로 저장
		// 2. 파일 스테이지 설정 getExtensionFilters : 선택할 파일 필터
		fileChooser.getExtensionFilters().add(new ExtensionFilter("그림파일 : Image File", "*png", "*jpg", "*gif"));
		// 3. 스테이지에 파일선택클래스 넣기
		File file = fileChooser.showOpenDialog(stage);
		// * 선택한 파일을 파일클래스에 저장
		lbl_img_path.setText("파일 경로 : " + file.getPath());
		pimage = file.toURI().toString(); // 파일의 실제[real] 경로

		Image image = new Image(pimage);
		p_img.setImage(image);

	}

}
