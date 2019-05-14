package test.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.swing.JOptionPane;

public class SqlServerTest {
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		Connection conn = null;
		
			String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String sDBUrl = "jdbc:sqlserver://115.29.207.187:2433;databaseName=WX_JianAn";
			Class.forName(sDriverName);
			System.out.println("注册驱动成功！");

			String userName = "sa";
			String password = "GiianHZ5777";
			conn = DriverManager.getConnection(sDBUrl, userName,
					password);
			System.out.println("连接成功！");
			
			String sql = "SELECT TOP 1000 [ID],[DeptName],[FatherID],[QrCodeImgUrl],[CreateTime] FROM [WX_JianAn].[dbo].[Dept]";
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while(rs.next())//开始循环读取，每次往表中插入一行记录  
            {  
                String s = rs.getString(1);
                System.out.println(s);
            }  

		
	}
}
