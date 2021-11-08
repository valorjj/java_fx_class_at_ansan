package Day01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// 1. Application 상속을 받고 시작해야한다. 
public class AppMain extends Application {
	// 2. start 오버라이딩
		// 1. Stage 이름 정하기
	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox root = new VBox();
		root.setPrefWidth(350);
		root.setPrefHeight(300);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);

		Label label = new Label();
		label.setText("Hello, JAVAFX");
		label.setFont(new Font(50));

		Button button = new Button();
		button.setText("Check");
		button.setOnAction(event -> Platform.exit());
		root.getChildren().add(label);
		root.getChildren().add(button);

		Scene scene = new Scene(root);

		primaryStage.setTitle("AppMain");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
