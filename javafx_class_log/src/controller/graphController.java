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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

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

		// 라인차트에 날짜별 제품수를 표시할 것이다.
//		XYChart.Series series = new XYChart.Series<>(); // 1. 계열 생성
//		series.setName("등록된 제품 갯수"); // 2. 계열 이름 부여
//
//		// 데이터를 꺼내와야한다.
//		// 왜 observablelist 를 써야만 했을까?
//		ObservableList<Product> products = ProductDao.getProductDao().productlist(); // 제품들을 전부 다 꺼내온다.
//
//		// Map Set 을 사용해서 쓸 수도 있지만 여기서는 ProductDate 라는 객체를 하나 생성했다.
//		// ArrayList<hashMap<String, Integer>> 이렇게 만들 수도 있다.
//
//		// 날짜로 구분한다.
//		// 날짜, 갯수를 한번에 저장한다.
//
//		ArrayList<ProductDate> dates = new ArrayList<>(); // 날짜, 갯수
//
//		// product 리스트에 집어넣는다.
//		for (Product product : products) { // 모든 제품 하나씩 객체를 꺼낸다. 
//			String date = product.getP_date().split(" ")[0]; // 날짜로 분류한다. 날짜만 가져온다 (뒤에나오는 시간은 제외)
//
//			boolean datecheck = true; // 중복 체크 
//			for (int i = 0; i < dates.size(); i++) {
//				if (dates.get(i).getDate().equals(date)) { // 만약에 날짜별 갯수 리스트에 동일한 날짜가 있으면 갯수를 증가시킨다. 
//					dates.get(i).setCount(dates.get(i).getCount() + 1);
//					datecheck = false;
//					break;
//				}
//			}
//			if (datecheck) {
//				dates.add(new ProductDate(date, 1)); // 동일한 날짜가 없으면 리스트에 새롭게 추가한다. 
//			}
//
//		}
//
//		for (ProductDate date : dates) { // 날짜별 갯수 리스트에서 하나씩 꺼내서 
//			XYChart.Data data = new XYChart.Data<>(date.getDate() + "", date.getCount()); // 객체를 XY 차트데이터에 데이터에 값 넣기 
//			series.getData().add(data); // 3. 계열에 값 넣기
//		}
//
//		Lc.getData().add(series); // 4. 계열을 차트에 넣기

		// -------------------------------------------------------------------

		XYChart.Series series = new XYChart.Series<>(); // DB 쪽으로 간다?
		series.setName("제품 수");
		ArrayList<ProductDate> productDates = ProductDao.getProductDao().productdatelist();
		for (ProductDate productdate : productDates) {

			// 1. 값 설정
			XYChart.Data data = new XYChart.Data<>(productdate.getDate(), productdate.getCount());
			// 1 계열 값에 노드를 설정할 수 있다. [data.setNode({$컨테이너})]
			AnchorPane anchorPane = new AnchorPane(); // 컨테이너 만들기
			Label label = new Label(productdate.getCount() + ""); // 레이블 생성
			label.setPadding(new Insets(5));
			anchorPane.getChildren().add(label); // 컨테이너 레이블 넣기

			data.setNode(anchorPane); // 값에 컨테이너 넣기
			// 2. 계열에 값 넣기
			series.getData().add(data);
		}

		// y축 설정
		// 자동으로 설정되는 것을 꺼야한다.w
		lc.getYAxis().setAutoRanging(false);

		lc.getData().add(series);

		// get(productDates.size() - 1) --> 리스트내 마지막 객체
		// get(productDates.size() - 2) --> 마지막의 바로 이전 객체
		// 얘네 두개를 비교한다
		if (productDates.get(productDates.size() - 1).getCount() > productDates.get(productDates.size() - 2)
				.getCount()) {
			lbl_decrease.setVisible(false);
		} else {
			lbl_increase.setVisible(false);
		}

		// -------------------------------------------------------

		XYChart.Series series2 = new XYChart.Series<>();

		HashMap<String, Integer> hashMap = ProductDao.getProductDao().product_category_list();

		String max_category = "";
		int max = 0;
		for (String key : hashMap.keySet()) {
			if (hashMap.get(key) > max) {
				max = hashMap.get(key);
				max_category = key;
			}
			XYChart.Data data = new XYChart.Data<>(key, hashMap.get(key));
			series2.getData().add(data);
		}

		bc.getData().add(series2);
		lbl_category.setText(max_category);

		// 원형 차트

		ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();
		observableList.add(new PieChart.Data("13", 10));
		observableList.add(new PieChart.Data("12", 15));
		observableList.add(new PieChart.Data("11", 9));

		pc.setData(observableList);

	}

	@FXML
	private Label lbl_controller_1;
	@FXML
	private Label lbl_controller_2;
	@FXML
	private Label lbl_controller_3;
	@FXML
	private Label lbl_increase;
	@FXML
	private Label lbl_decrease;
	@SuppressWarnings("rawtypes")
	@FXML
	private LineChart lc;
	@SuppressWarnings("rawtypes")
	@FXML
	private BarChart bc;
	@FXML
	private Label lbl_category;
	@FXML
	private PieChart pc;

}
