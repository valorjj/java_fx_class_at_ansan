package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Product;
import domain.ProductDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductDao {

	// ---- Field ----
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	// ---- Object ----

	public static ProductDao productDao = new ProductDao();

	public static ProductDao getProductDao() {
		return productDao;
	}

	// ---- Constructor ----
	public ProductDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javafx_1?serverTimezone=UTC", "root",
					"1234");
			System.out.println("connection success .. ");
		} catch (Exception e) {
			System.out.println("db synchronized failed .. " + e);
		}
	}

	// ---- Method ----
	public boolean register(Product product) {
		// sql query statement
		String sql = "insert into product(p_name, p_img, p_contents, p_category, p_price, p_activation, m_no) values(?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, product.getP_name());
			preparedStatement.setString(2, product.getP_img());
			preparedStatement.setString(3, product.getP_contents());
			preparedStatement.setString(4, product.getP_category());
			preparedStatement.setInt(5, product.getP_price());
			preparedStatement.setInt(6, product.getP_activation());
			preparedStatement.setInt(7, product.getM_no());
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public ObservableList<Product> productlist() {
		ObservableList<Product> products = FXCollections.observableArrayList();
		String sql = "select * from product order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				Product product = new Product(

						resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getString(8),
						resultSet.getInt(9)

				);

				products.add(product);

			}

			return products;

		} catch (Exception e) {
		}

		return products;

	}
	// ---- ?????? ?????? ????????? ----

	public boolean delete(int p_no) {
		String sql = "delete from product where p_no=?";

		try {

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, p_no);
			preparedStatement.executeUpdate(); // select ??? query ??? ????????????.
			return true;

		} catch (Exception e) {
		}
		return false;
	}

	// ---- ?????? ???????????? ----

	public boolean update(Product product) {

		String sql = "update product set p_name=?, p_img=?, p_contents=?, p_category=?, p_price=? where p_no=? ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, product.getP_name());
			preparedStatement.setString(2, product.getP_img());
			preparedStatement.setString(3, product.getP_contents());
			preparedStatement.setString(4, product.getP_category());
			preparedStatement.setInt(5, product.getP_price());
			preparedStatement.setInt(6, product.getP_no());
			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ObservableList<Product> myproductlist(int m_no) {
		ObservableList<Product> products = FXCollections.observableArrayList();
		String sql = "select * from product where m_no=? order by p_no desc";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, m_no);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				Product product = new Product(

						resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getString(8),
						resultSet.getInt(9)

				);

				products.add(product);

			}

			return products;

		} catch (Exception e) {
		}

		return products;

	}

	// ?????? ????????? ????????? ???????????? ???????????? ????????? - product list ??? ?????? ?????? ????????? ????????????
	public boolean activation_update(int p_activation, int p_no) {
		String sql = "update product set p_activation=? where p_no=?";
		// p_activation ??? ???????????? p_no ??? ???????????? ?????? ?????????

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, p_activation);
			preparedStatement.setInt(2, p_no);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public int product_count() {

		String sql = "select count(*) from product ";

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			// resultSet ???????????? null ?????? ???????????????.
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;

	}

	// ????????? ?????? ??????
	public ArrayList<ProductDate> productdatelist() {

		ArrayList<ProductDate> productDates = new ArrayList<>();
		String sql = "select substring_index(p_date, ' ', 1) , count(*) from product group by substring_index(p_date, ' ', 1)";

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ProductDate date = new ProductDate(resultSet.getString(1), resultSet.getInt(2));
				productDates.add(date);
			}
			return productDates;
		} catch (Exception e) {
		}
		return productDates;
	}

	// ???????????? ??? ????????? ??????

	public HashMap<String, Integer> product_category_list() {
		HashMap<String, Integer> hashMap = new HashMap<>();

		String sql = "select p_category, count(*) from product group by p_category";

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				hashMap.put(resultSet.getString(1), resultSet.getInt(2));

			}
			return hashMap;
		} catch (Exception e) {
		}
		return hashMap;

	}

	public HashMap<String, Integer> product_activation_list() {
		HashMap<String, Integer> hashMap = new HashMap<>();

		String sql = "select p_activation, count(*) from product group by p_activation";

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				hashMap.put(resultSet.getString(1), resultSet.getInt(2));

			}
			return hashMap;
		} catch (Exception e) {
		}
		return hashMap;

	}

}
