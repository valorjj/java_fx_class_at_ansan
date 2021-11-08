module CLASS_JAVAFX_EZEN {

	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.media;
	requires java.sql;

	opens Day01 to javafx.graphics, javafx.fxml;

	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml, javafx.controls;
	opens dao to java.sql;
	opens domain to java.sql, javafx.base;
}
