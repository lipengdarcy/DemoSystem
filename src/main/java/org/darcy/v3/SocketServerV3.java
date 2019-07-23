package org.darcy.v3;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * v3版本的SocketServer改造成接收http请求，而非socket请求
 * 
 */
public class SocketServerV3 {
	public static void main(String args[]) throws Exception {
		ServerSocket server = new ServerSocket();
		InetSocketAddress address = new InetSocketAddress("localhost", 80);
		server.bind(address);
		System.out.println("自定义web服务器V3启动成功，接收http请求");

		// 如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
		ExecutorService threadPool = Executors.newFixedThreadPool(100);

		while (true) {
			Socket socket = server.accept();
			Runnable runnable = () -> {
				try {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			threadPool.submit(runnable);
		}
	}
}
