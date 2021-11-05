package com.kais.CRUD;

import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Kais
 * @create 2021-08-17-8:50
 */

/*
* 通用增删改操作
* */
public class GeneralCUD {

    /*
    * 通用增删改方法的实现
    * 使用Object... args实现动态参数获取，动态填充占位符
    * */
    public  void preparedStatementGeneral(String sql, Object... args) throws Exception {

        /*
        * 获取数据库连接
        * */
        Connection conn = null;
        PreparedStatement ps = null;
        conn = JDBCUtils.getConnection();

        /*
        * 预编译sql语句
        * */
        ps = conn.prepareStatement(sql);

        /*
        * 填充占位符
        * Object... args:动态参数是数组类型数据
        * 注意：数据库的结果集是从1开始计数的数据表，而数组是从0开始计数的
        * */
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1,args[i]);
        }
        /*
        * 执行sql
        * */
        ps.execute();
        /*
        * 关闭数据库连接
        * */
        JDBCUtils.closeResource(conn,ps);
    }

    @Test
    public void test01() throws Exception {

        String sql01 = "delete from orders where order_id = ?";
        preparedStatementGeneral(sql01,4);
    }
    @Test
    public void test02() throws Exception {
        String sql = "update orders set order_name = ? where order_id = ?";
        preparedStatementGeneral(sql,"macbook",3);
    }
    @Test
    public void test03() throws Exception {
        String sql = "insert into orders(order_id,order_name,order_date) values(?,?,?)";
        preparedStatementGeneral(sql,4,"honor","2021-8-17");
    }
}
