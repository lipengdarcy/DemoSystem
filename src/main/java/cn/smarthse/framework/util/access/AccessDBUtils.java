package cn.smarthse.framework.util.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessDBUtils {

	
	//	private static final String dbURL = "jdbc:ucanaccess://" +"C:\\Users\\skysoft\\Desktop\\天软物料管理系统\\hyman.accdb";

    /**
     * 加载驱动
     */
    static {
        try {
            // Step 1: Loading or registering Oracle JDBC driver class
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
    }

    /**
     * 建立连接
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author horsy(何世壹) [hsy@smarthse.cn]
     * @since 2019年3月21日-上午11:42:03
     * @param dbURL 数据库文件路径"C:\\Users\\skysoft\\Desktop\\test.accdb）
     * @return
     */
    public static Connection getConn(String dbPath) {
        try {
            // Step 2: Opening database connection
            // Step 2.A: Create and get connection using DriverManager class
            return DriverManager.getConnection("jdbc:ucanaccess://"+dbPath);
        } catch (Exception e) {
            System.out.println("AccessDB connection fail");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  关闭资源
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author horsy(何世壹) [hsy@smarthse.cn]
     * @since 2019年3月21日-上午11:42:30
     * @param con
     * @param ps
     * @param rs
     */
    public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();// 这里出现异常了，rs关闭了吗？，如果没有怎么解决,ps , con也是一样的。
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
