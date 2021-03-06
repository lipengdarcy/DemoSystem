package org.darcy.v1;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * v0版本的SocketServer只能接受一个请求，v1版本改进下， 可以不断接受请求
 * 
 */
public class SocketServerV1 {
	public static void main(String[] args) throws Exception {
		int port = 80;
		ServerSocket server = new ServerSocket(port);
		System.out.println("自定义web服务器V1启动成功，请求一直发过来吧，全部接收");

		while (true) {
			Socket socket = server.accept();
			InputStream inputStream = socket.getInputStream();
			byte[] bytes = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = inputStream.read(bytes)) != -1) {
				// 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
				sb.append(new String(bytes, 0, len, "UTF-8"));
			}
			System.out.println("客户端的消息: " + sb);
			inputStream.close();
			socket.close();
		}

	}
}
