package com.kais.CRUD;

import com.kais.bean.Customer;
import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kais
 * @create 2021-07-06-14:05
 */

/*
* 数据库查询操作
* */
public class QuerySimple {
    @Test
    public void orderTest(){
        String sql = "select id,age,name,birth,email,sex from customers";
        List list;
        list = queryOrderBy(sql);
        for(Object object : list){
            System.out.println(object);
        }
    }

    public List<Customer> queryOrderBy(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        Customer customer = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //rs获取执行查询语句后的结果，可以调用其get方法获取数据库字段的值
            rs = ps.executeQuery();

            //获取数据库表中字段数
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            System.out.println("结果集中每一行数据的列元素为：" + resultSetMetaData.getColumnCount());
            //指向数据库中的一行数据
            while(rs.next()){
                customer = new Customer(
                        //获取每一行中的每个列数据，并将游标向下移动一位
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getString(5),
                        rs.getString(6)
                );
                list.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return list;
    }
}
