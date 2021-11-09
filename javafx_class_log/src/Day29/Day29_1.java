package Day29;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Day29_1 {

	// 인터넷 : TCP/IP 라는 통신 프로토콜을 이용한 컴퓨터 네트워크

	// TCP/IP

	// Server 로 들어간다

	// 네이버 접속 학원ip -----> 네이버 ip

	// server pc

	public static void main(String[] args) {

		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println("local pc 이름" + inetAddress.getHostName());
			System.out.println("local pc 주소" + inetAddress.getHostAddress());

			InetAddress[] inetAddresses = InetAddress.getAllByName("www.naver.com");
			for (InetAddress address : inetAddresses) {
				System.out.println("naver pc 이름 " + address.getHostName());
				System.out.println("naver pc 주소 " + address.getHostAddress());
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
