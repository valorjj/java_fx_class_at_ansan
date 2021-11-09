package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage root) throws Exception {

		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/graph.fxml"));

//		AnchorPane mainborderpane = new AnchorPane();
//
//		int cnt = 0;
//		Button[][] buttonlist = new Button[5][10];
//		boolean[][] checklist = new boolean[5][10];
//		for (int i = 0; i < 5; i++) {
//			for (int j = 0; j < 10; j++) {
//				int numbering = i + j + cnt;
//				String n = Integer.toString(numbering);
//
//				if (i == 0) {
//
//					buttonlist[i][j] = new Button("A" + (j + 1));
//					buttonlist[i][j].setOnMouseClicked(e -> {
//						if (e.getButton().equals(MouseButton.PRIMARY)) {
//
//						}
//
//					});
//				}
//				if (i == 1) {
//					buttonlist[i][j] = new Button("B" + (j + 1));
//				}
//				if (i == 2) {
//					buttonlist[i][j] = new Button("C" + (j + 1));
//				}
//				if (i == 3) {
//					buttonlist[i][j] = new Button("D" + (j + 1));
//				}
//				if (i == 4) {
//					buttonlist[i][j] = new Button("E" + (j + 1));
//				}
//
//				buttonlist[i][j].setLayoutX(j + j * 40);
//				buttonlist[i][j].setLayoutY(i + i * 40);
//				buttonlist[i][j].setId(n);
//				buttonlist[i][j].setOnAction(e -> {
//
//				});
//				mainborderpane.getChildren().add(buttonlist[i][j]);
//			}
//			cnt = cnt + 9;
//		}

		Scene scene = new Scene(parent);

		root.setScene(scene);
		root.setResizable(false);
		root.setTitle("logInPage");

		root.show();
	}

	public static void main(String[] args) {

		launch(args);

	}
}
