package com.kais.CRUD;


import com.kais.bean.Orders;
import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author Kais
 * @create 2021-08-16-15:23
 */
public class QueryForOrders {
    @Test
    public void testQueryForOrder(){

        String sql = "select order_id orderId, order_name orderName, order_date orderDate from order where orderId = ?";
        Orders order = queryForOrder(sql, 1);
        System.out.println(order);

/*        sql = "select name,email from customers where name = ?";
        Customer customer1 = queryForCustomers(sql,"周杰伦");
        System.out.println(customer1);*/

    }

    /*
     * 针对于order表的通用的查询操作
     * */
    public Orders queryForOrder(String sql, Object...args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = JDBCUtils.getConnection();
            System.out.println(conn);
            ps = conn.prepareStatement(sql);

            System.out.println("args.length=" + args.length);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                Orders order = new Orders();
                //处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columValue = rs.getObject(i + 1);

                    //获取每个列的列名,getColumnName
                    //获取列的别名，getColumnLabel
//					String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //给cust对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = Orders.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order, columValue);
                }
                return order;
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


}
