package org.darcy.v0;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 启动SocketServer，模拟服务端；
 * 
 * 启动SocketClient，模拟客户端；
 * 
 * 一个最简单的web服务就搭好啦。
 */
public class SocketServer {
	public static void main(String[] args) throws Exception {
		// 监听指定的端口
		int port = 80;
		ServerSocket server = new ServerSocket(port);
		System.out.println("自定义web服务器启动成功");
		Socket socket = server.accept();
		// 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
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
		server.close();
	}
}
