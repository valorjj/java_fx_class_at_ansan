package Day29;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Day29_client {

	// 클라이언트용 서버 만들기 (통신용 클라이언트)

	public static void main(String[] args) {

		// 1. 소켓 만들기
		Socket socket = new Socket();

		try {
			while (true) {
				socket.connect(new InetSocketAddress("127.168.102.50", 5000));
				System.out.println("[서버와 연결 성공]");

				System.out.print("서버에게 보낼 메시지 : ");
				Scanner scanner = new Scanner(System.in);
				String msg = scanner.nextLine();

				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(msg.getBytes());
				System.out.println("서버에게 메시지 전송 성공");

				InputStream inputStream = socket.getInputStream();
				// int size = inputStream.available();
				byte[] bytes = new byte[1000];
				inputStream.read(bytes);
				System.out.println("서버의 메시지 " + new String(bytes));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
