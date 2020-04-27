package test.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQTest {

	private final static String QUEUE_NAME = "q_test_01";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明（创建）队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 消息内容
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		// 关闭通道和连接
		channel.close();
		connection.close();
	}

	public static Connection getConnection() throws Exception {
		// 定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置服务地址
		factory.setHost("localhost");
		// 端口
		factory.setPort(5672);
		// 设置账号信息，用户名、密码、vhost
		factory.setVirtualHost("mall");
		factory.setUsername("guest");
		factory.setPassword("guest");
		// 通过工程获取连接
		Connection connection = factory.newConnection();
		return connection;
	}

}
