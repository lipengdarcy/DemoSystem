package test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MbatisCacheTest {
    public static void main(String[] args) throws IOException {
        String config = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(config);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = factory.openSession();
        System.out.println(session.selectOne("selectUserByID", 1).toString());
        // 同一个session的相同sql查询,将会使用一级缓存 
        System.out.println(session.selectOne("selectUserByID", 1).toString());
        // 参数改变,需要重新查询
        System.out.println(session.selectOne("selectUserByID", 2).toString());
        // 清空缓存后需要重新查询
        session.clearCache();
        System.out.println(session.selectOne("selectUserByID", 1).toString());
        // session close以后,仍然使用同一个db connection
        session.close();
        session = factory.openSession();
        System.out.println(session.selectOne("selectUserByID", 1).toString()); 
    }
}