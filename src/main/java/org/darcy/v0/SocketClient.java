package org.darcy.v0;

import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
	public static void main(String args[]) throws Exception {
		// 要连接的服务端IP地址和端口
		String host = "127.0.0.1";
		int port = 80;
		// 与服务端建立连接
		Socket socket = new Socket(host, port);
		// 建立连接后获得输出流
		OutputStream outputStream = socket.getOutputStream();
		String message = "你好，服务器，我是客户端";
		socket.getOutputStream().write(message.getBytes("UTF-8"));
		outputStream.close();
		socket.close();
	}

}