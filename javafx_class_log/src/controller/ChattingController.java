package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChattingController implements Initializable {

	private String loginid = MainpageController.getinstance().getloginId();

	// 1. 클라이언트 소켓 선언
	Socket socket;

	// 2. 클라이언트 시작 메소드
	public void client_start() {

		// 멀티스레드를 사용하지만 스레드풀을 사용하지는 않는다.
		// 현재 server 가 아니기 때문이다.

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					// 아까 했던 서버 프로젝트에서 binding 것과 동일한 걸로 넣어야한다.
					// 서버소켓에 바인딩 된 동일한 ip, port
					socket = new Socket("127.0.0.1", 1234);
					send(loginid + "님 입장\n");
					receive();

				} catch (Exception e) {
					Platform.runLater(() -> txt_client.appendText("% 서버가 닫혀 있음 \n"));
				}
			}

		};

		thread.start();

	}

	// 3. 클라이언트 중지 메소드
	public void client_stop() {
		try {
			socket.close();
		} catch (Exception e) {
			Platform.runLater(() -> txt_client.appendText("% 서버가 중지되었음 \n"));

		}

	}

	// 4. 메시지 보내기 메소드
	public void send(String msg) {

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(msg.getBytes());
					outputStream.flush();
				} catch (IOException e) {
					Platform.runLater(() -> txt_client.appendText("% 서버에 메시지 보내기 실패 \n"));

					e.printStackTrace();
				}

			}

		};

		thread.start();

	}

	// 5. 메시지 받는 메소드
	public void receive() {

		while (true) {
			try {
				InputStream inputStream = socket.getInputStream();
				byte[] bytes = new byte[2000];
				inputStream.read(bytes);
				String msg = new String(bytes);
				Platform.runLater(() -> txt_client.appendText(msg)); // 받은 문자열을 메시지창에 띄우기

			} catch (IOException e) {
				Platform.runLater(() -> txt_client.appendText("% 서버에서 메시지 받기 실패 \n"));

				e.printStackTrace();
			}

		}

	}

	// 6. 입력 버튼을 눌렀을 때
	@FXML
	void msg_send(ActionEvent event) {

		// 메시지 보내기
		send(loginid + " : " + txt_contents.getText() + "\n");

		// 메시지 보낸 후
		txt_contents.setText("");
		txt_contents.requestFocus();

	}

	@FXML
	void msg_send2(ActionEvent event) {
		// 메시지 보내기
		send(MainpageController.getinstance().getloginId() + " : " + txt_contents.getText() + "\n");

		// 메시지 보낸 후
		txt_contents.setText("");
		txt_contents.requestFocus();

	}

	// 7. 엔터를 눌렀을 때

	@FXML
	private TextArea txt_client;
	@FXML
	private TextField txt_contents;
	@FXML
	private Button btn_input;
	@FXML
	private Button btn_connect;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_client.setEditable(false);
		txt_contents.setDisable(true);
		btn_input.setDisable(true);
	}

	@FXML
	public void connect(ActionEvent event) {

		if (btn_connect.getText().equals("접속하기")) {
			// 1. 클라이언트 실행

			client_start();
			// 2. 접속 메시지 전달
			Platform.runLater(() -> txt_client.appendText("채팅방 접속 \n"));

			// 3. 컨트롤 내용 변경
			btn_connect.setText("나가기");
			txt_contents.setDisable(false);
			txt_contents.requestFocus(); // 마우스 포인터를 이동시킨다.
			btn_input.setDisable(false);

		} else {
			// 1. 클라이언트 종료
			client_stop();

			// 2. 메시지 전달
			Platform.runLater(() -> txt_client.appendText("채팅방 퇴장 \n"));
			// 3. 컨트롤 내용 변경
			btn_connect.setText("접속하기");
			txt_contents.setDisable(true);
			btn_input.setDisable(true);

		}

	}

}
