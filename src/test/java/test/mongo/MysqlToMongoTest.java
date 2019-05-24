package test.mongo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MysqlToMongoTest {

	public static void main(String[] args) {
		mongoQuery(getCollection());
	}

	/**
	 * mysql 连接
	 */
	public static void mysqlConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con = null;
			String uri = "jdbc:mysql://localhost:3306/product_master?serverTimezone=Asia/Shanghai&useSSL=true&characterEncoding=utf8";
			String user = "root";// 用户名
			String password = "123456"; // 密码
			con = DriverManager.getConnection(uri, user, password);
			System.out.println("1.mysql连接成功，地址:" + uri);

			// SqlSession session = null;
			java.sql.Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// SQL语句对象调用executeQuery()方法对数据库进行查询,返回ResultSet对象
			ResultSet rs = sql.executeQuery("SELECT * FROM hotel_data;");// 在这里指定查询语句
			/* 滚动查询方式(即可以控制游标) */
			rs.next();// 向下移动到第一行
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			int lnth = rs.getRow();// 获取当前行号
			System.out.println("当前行号:" + lnth);
			rs.last();// 移动到最后一行
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			rs.previous();// 向上移动一行
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			System.out.println("是否在最后一行之后" + rs.isAfterLast());// 是否在最后一行之后
			System.out.println("是否在第一行之前" + rs.isBeforeFirst());// 是否在第一行之前
			rs.absolute(3);// 绝对移动:到第3行
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			rs.absolute(-2);// 绝对移动:到倒数第2行
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));

			// 最后关闭连接
			// 注意当Connection对象使用close()方法关闭连接后,ResultSet对象不再可用
			// 所以如果要使用ResultSet对象就要一直保持连接
			// 如果要关闭连接后还能使用数据,就要把ResultSet对象里的数据存到别的地方去
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取mongodb 的Collection, 对应mysql 的table
	 */
	public static MongoCollection<Document> getCollection() {
		// 1.org.mongodb中提供了ServerAddress类用来储存连接mongo的ip地址以及端口，初始化的方法为：
		ServerAddress serverAddress = new ServerAddress("localhost");

		// 2.如果这个MongoDB的服务器是一个集群，那么通常来说需要设置setSlaveOK才能通过某一台服务器去访问数据，
		// 但是，我们可以把整个集群的服务器list输入给ServerAddress类，其初始化的方法为：
		MongoClient mongoClient_cluster = new MongoClient(Arrays.asList(new ServerAddress("localhost", 1000),
				new ServerAddress("localhost", 2000), new ServerAddress("localhost", 3000)));

		// 3.如果这个mongoDB的数据库是有权限访问设置的数据库，则还需要MongoCredential类将数据库的
		// 名称，用户名，密码传入连接数据库的方法中，其初始化的方法为：
		MongoCredential credential = MongoCredential.createCredential("username", "test", "password".toCharArray());

		// 4.最后才是连接MongoDB数据库的操作，使用的是MongoClient类，其初始化的方法为：
		MongoClient mongoClient = new MongoClient(serverAddress, MongoClientOptions.builder().build());

		// 5.如若要获取这个server下的某一个数据库（Database），则需要执行如下方法：
		MongoDatabase mydb = mongoClient.getDatabase("test");

		// 6.在mongoDB中，我们熟知的table被称为collection，所以如果要获得某个collection（表格），需要执行如下的方法：
		MongoCollection<Document> myCollection = mydb.getCollection("hotelData");
		return myCollection;

	}

	public static void mongoQuery(MongoCollection<Document> myCollection) {

		// 7.基于Java的MongoDB的查询语句需要指定where的条件
		// $eq相等; $ne不等于; $gte大于等于; $gt大于; $lte小于等于; $lt小于
		BasicDBObject queryParam = new BasicDBObject("name", new BasicDBObject("$eq", "李鹏"));
		// Example: 取得大于等于某个时间点的所有数据库记录BasicDBObject exObject = new
		// BasicDBObject("time", new BasicDBObject("$gte", (from + " 00:00")))

		// 使用List来连接需要用逻辑关联词串联起来的条件语句
		List<DBObject> criteria = new ArrayList<>();
		criteria.add(new BasicDBObject("time", new BasicDBObject("$gte", "2013-11-01 00:00")));
		criteria.add(new BasicDBObject("time", new BasicDBObject("$lte", "2013-11-01  23:59")));
		// 这里也可以使用"$or"字段表示"或"逻辑
		BasicDBObject andConnection = new BasicDBObject("$and", criteria);

		// 8.之后使用find方法去找到符合这个where条件的记录。
		// MongoCursor<Document> tCursor = myCollection.find(queryParam).iterator();

		// 目标：获取所有满足如下条件的数据：
		// 1. "name"字段满足某个值
		// 2. 先通过name降序，再通过birthday降序
		// 3. 获取的记录不超过10条
		MongoCursor<Document> cursor = myCollection.aggregate(Arrays.asList(
				// "$match"用于筛选date匹配这个值的记录
				new BasicDBObject("$match", new BasicDBObject("name", new BasicDBObject("$eq", "李鹏"))),
				new BasicDBObject("$sort", new BasicDBObject("name", 1)),
				new BasicDBObject("$sort", new BasicDBObject("birthday", -1)), new BasicDBObject("$limit", 10)))
				.iterator();
		// 9.打印记录
		while (cursor.hasNext()) {
			Document data = cursor.next();
			System.out.println("data：" + data.toString());
		}

		// 聚合操作（Aggregation， Group By）
		// $match 相等，类似于where中的等于某值; $sort 排序; $limit 与mysql中的limit相同

	}

}
