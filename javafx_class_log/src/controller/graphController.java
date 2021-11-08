package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import dao.BoardDao;
import dao.MemberDao;
import dao.ProductDao;
import domain.Product;
import domain.ProductDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class graphController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lbl_controller_1.setText(MemberDao.getMemberDao().member_count() + "");
		lbl_controller_1.setAlignment(Pos.CENTER);
		lbl_controller_2.setText(BoardDao.getBoardDao().board_count() + "");
		lbl_controller_2.setAlignment(Pos.CENTER);
		lbl_controller_3.setText(ProductDao.getProductDao().product_count() + "");
		lbl_controller_3.setAlignment(Pos.CENTER);

		// line chart
//		XYChart.Series series = new XYChart.Series<>(); // 1. 계열 생성
//		series.setName("text1"); // 2. 계열 이름
//
//		XYChart.Data data1 = new XYChart.Data<>("1", 30);
//		XYChart.Data data2 = new XYChart.Data<>("2", 40);
//		XYChart.Data data3 = new XYChart.Data<>("3", 50);
//
//		series.getData().add(data1);
//		series.getData().add(data2);
//		series.getData().add(data3);
//
//		Lc.getData().add(series);

		// 실시간 동기화 하려면

		// 멀티 스레드를 쓰던가
		// 메소드를 만들어서 따로 빼서 호출시키던가

		// 제품을 등록 날짜 별로 분류해보자
		// 제품 등록 날자 == 가로
		// 제품 수 == 세로

		XYChart.Series series = new XYChart.Series<>();
		series.setName("등록 수");

		// 데이터를 꺼내와야한다.

		ObservableList<Product> products = ProductDao.getProductDao().productlist();
		// Map Set 취사선택

		// 날짜로 구분한다.
		// 날짜, 갯수를 한번에 저장한다.
		// ArrayList<HashMap<String, Integer>> dates = new ArrayList<>();

		ArrayList<ProductDate> dates = new ArrayList<>();

		for (Product product : products) {
			String date = product.getP_date().split(" ")[0];

			for (int i = 0; i < dates.size(); i++) {
				if (dates.get(i).getDate().equals(date)) {
					dates.get(i).setCount(dates.get(i).getCount() + 1);
					break;
				}
			}

			dates.add(new ProductDate(date, 1));

		}

		for (ProductDate date : dates) {
			XYChart.Data data = new XYChart.Data<>(date.getDate(), date.getCount());
			series.getData().add(data);
		}

		Lc.getData().add(series);

	}

	@FXML
	private Label lbl_controller_1;
	@FXML
	private Label lbl_controller_2;
	@FXML
	private Label lbl_controller_3;
	@FXML
	private LineChart Lc;

}
