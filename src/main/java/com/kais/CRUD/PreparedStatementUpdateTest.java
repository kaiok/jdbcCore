package com.kais.CRUD;

import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Kais
 * @create 2021-07-06-14:06
 */
public class PreparedStatementUpdateTest {

    /**
     * 方法描述:插入数据
     *
     * @返回值 : void
     * @作者 : lxk
     * 时间:2021/9/2 9:22
    */
    @Test
    public void insertOrder(){

        String sql = "insert into orders( order_id, order_name, order_date) values(?,?,?)";

        Connection conn = null;
        PreparedStatement ps = null;
        //增删改操作没有返回值
        //ResultSet rs = null;
        try {
            /*
            * 获取数据库连接
            * */
            conn = JDBCUtils.getConnection();

            /*
            * 预编译sql语句
            * */
            ps = conn.prepareStatement(sql);
            System.out.println(conn);

            /*
            * 填充占位符
            * */
            ps.setInt(1,4);
            ps.setString(2,"Macbook pro");
            ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));

            /*
            * 执行sql
            * */
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
            * 关闭资源
            * */
            JDBCUtils.closeResource(conn,ps);

        }
    }

    /*
    * 更新操作
    * */
    @Test
    public void updateOrders(){

        String sql = "update orders set order_name = ? where order_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1,"ipad pro");
            ps.setInt(2,1);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }



}
