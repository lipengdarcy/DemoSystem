package org.darcy.v2;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * v1版本的SocketServer每次都是创建新的Socket来处理请求，效率低下，
 * 
 * v2版本改由线程池来处理客户端请求
 * 
 */
public class SocketServerV2 {
	public static void main(String args[]) throws Exception {
		// 监听指定的端口
		int port = 80;
		ServerSocket server = new ServerSocket(port);
		System.out.println("自定义web服务器V2启动成功，这次是线程池来处理请求");

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
