package Day01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Day01_fxml extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		Parent parent = FXMLLoader.load( getClass().getResource("login_1.fxml"));
		// 불러온 fxml 파일을 scene 에다가 넣는다. 
		Scene scene = new Scene(parent);
		// scene --> stage
		arg0.setScene(scene);
		arg0.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
