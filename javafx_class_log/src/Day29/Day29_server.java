package Day29;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Day29_server {

	public static void main(String[] args) {

		// 통신용 서버 만들기
		// 서버소켓 : ServerSocker 클래스
		// 소켓 : 네트워크상에서 동작하는 프로그램 간 통신 종착점 [serversocket 이 허락하여 client 와 연결]
		// 바인딩 :
		// 서버에 ip 주소와 port 번호를 설정한다.
		// 그럼 포트는 뭐야
		// ip에서 포트는 운영체제 통신의 종단점이다. 식별 논리 단위로 사용한다.
		// port : ip당 6만개 정도 설정된 번호 [ip 내 process 연결 번호]
		// cmd - netstat
		try {
			// 서버소켓 만들기
			ServerSocket serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("127.168.102.50", 5000));
			while (true) {
				System.out.println("[서버에서 연결대기중]");
				Socket socket = serverSocket.accept(); // 클라이언트 요청 시 수락
				// 수락된 클라이언트 소켓 확인
				InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[클라이언트와 연결이 되었습니다. 클라이언트 정보 :" + socketAddress.getHostName() + "]");

				// 데이터 수신하기 [받기] 스트림을 이용한 외부 네트워크와 통신
				InputStream inputStream = socket.getInputStream();
				int size = inputStream.available();
				byte[] bytes = new byte[size];
				inputStream.read(bytes);
				System.out.println("클라이언트의 메시지 : " + new String(bytes));

				OutputStream outputStream = socket.getOutputStream();
				Scanner scanner = new Scanner(System.in);
				System.out.println("클라이언트에게 보낼 메시지 : ");
				String msg = scanner.nextLine();
				outputStream.write(msg.getBytes());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
