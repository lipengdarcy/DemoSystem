package cn.smarthse.framework.util.access;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
	/**
	 * 
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2019年3月21日-上午11:48:08
	 * @param sql 查询语句
	 * @param params 查询参数
	 * @param dbURL 数据库文件路径（C:\\Users\\skysoft\\Desktop\\test.accdb）
	 * @return 是否更新成功
	 * @throws SQLException
	 */
    public static boolean update(String sql, List<Object> params,String dbPath) throws SQLException {
        int result = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = AccessDBUtils.getConn(dbPath);
            assert conn != null;//直接抛异常
            ps = conn.prepareStatement(sql);
            int index = 1;
            if (params != null && !params.isEmpty()) {
                for (Object param : params) {
                    ps.setObject(index++, param);
                }
            }
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        } finally {
            AccessDBUtils.close(conn, ps, null);
        }
        return result > 0;
    }

    /**
     * 查询多条记录
     *
     * @param sql    sql
     * @param params 参数
     * @return 查询结果
     */
    public static List<Map<String, Object>> select(String sql, List<Object> params,String dbPath) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = AccessDBUtils.getConn(dbPath);
            assert conn != null;
            ps = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (Object param : params) {
                    ps.setObject(index++, param);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int col_len = metaData.getColumnCount();
            Map<String, Object> map = null;
            while (rs.next()) {
                map = new HashMap<>();
                for (int i = 0; i < col_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = rs.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            AccessDBUtils.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 通过反射机制查询多条记录
     *
     * @param sql    sql
     * @param params 参数
     * @param clazz  类
     * @return 查询结果
     */
    public static <T> List<T> select(String sql, List<Object> params,
                                     Class<T> clazz,String dbPath) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        int index = 1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = AccessDBUtils.getConn(dbPath);
            assert conn != null;
            ps = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (Object param : params) {
                    ps.setObject(index++, param);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int cols_len = metaData.getColumnCount();
            T t;
            while (rs.next()) {
                //通过反射机制创建一个实例
                t = clazz.newInstance();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = rs.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    Field field = clazz.getDeclaredField(cols_name);//获取对象属性
                    field.setAccessible(true); //打开javabean的访问权限
                    field.set(t, cols_value);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            AccessDBUtils.close(conn, ps, rs);
        }
        return list;
    }
}
