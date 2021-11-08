package Day01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Day01_1 extends Application {

	@Override
	public void start(Stage stage01) throws Exception {

		// 컨테이너 만들기
		VBox vbox = new VBox();

		Button button = new Button();
		button.setText("EXIT");
		button.setOnAction(e -> Platform.exit());

		vbox.getChildren().add(button);

		Scene scene = new Scene(vbox, 250, 250);
		stage01.setScene(scene);
		stage01.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
