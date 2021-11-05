package com.kais.CRUD;

import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Kais
 * @create 2021-08-20-16:42
 * @desc 批量执行sql语句
 */


/*
* 高效的批量插入
* */
public class BatchTest {

    /*
    * 使用Statement
    * */
    @Test
    public void test01() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        Statement st = conn.createStatement();
        for(int i = 1;i <= 20000;i++){
            String sql = "insert into goods(name) values('name_' + "+ i +")";
            st.executeUpdate(sql);
        }
    }

    /*
    * 使用PreparedStatement
    * 共花费时间：57026
    * */
    @Test
    public void test02() throws Exception {
        long start = System.currentTimeMillis();

        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into goods(name)values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i <= 20000; i++) {
            ps.setString(1,"name_" + i);
            ps.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("共花费时间：" + (end - start));
        JDBCUtils.closeResource(conn,ps);
    }

    /*
    * 使用 addBatch() / executeBatch() / clearBatch()
    * mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
    *   ?rewriteBatchedStatements=true 写在配置文件的url后面
    * 花费的时间为：1339
    * */
    @Test
    public void test03() throws Exception {
        long start = System.currentTimeMillis();
        Connection conn = JDBCUtils.getConnection();

        String sql = "insert into goods (name)values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < 100000; i++) {
            ps.setString(1,"name_" + i);
//            System.out.println();
            ps.addBatch();
            if(i % 500 == 0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start));
        JDBCUtils.closeResource(conn,ps);
    }

    /*
    * 在addBatch()的基础上操作
    * 使用Connection 的 setAutoCommit(false)  /  commit()
    * 共花费时间：604
    * */
    @Test
    public void test04() throws Exception {

        long start = System.currentTimeMillis();
        Connection conn = JDBCUtils.getConnection();

        //设置为不自动提交数据
        conn.setAutoCommit(false);
        String sql = "insert into goods (name)values(?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < 100000; i++) {
            ps.setString(1,"name_" + i);
            ps.addBatch();
            if(i % 500 == 0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        conn.commit();
        long end = System.currentTimeMillis();
        System.out.println("共花费时间：" + (end - start));
    }


}
