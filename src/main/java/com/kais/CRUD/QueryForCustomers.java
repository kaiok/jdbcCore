package com.kais.CRUD;

import com.kais.bean.Customer;
import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author Kais
 * @create 2021-07-06-14:05
 */
public class QueryForCustomers {

    @Test
    public void testQueryForCustomers(){
        String sql = "select id,age,name,birth,email,sex from customers where id = ?";
        Customer customer = queryForCustomers(sql, 12);
        System.out.println(customer);
    }

    @Test
    public void test(){
        String sql = "select name,id,age,birth,email,sex from customers where name = ?";
        Customer customer = queryForCustomers(sql,"成龙");
        System.out.println(customer);
    }



    /*
    * 针对于customers表的通用的查询操作
    * */
    public Customer queryForCustomers(String sql,Object...args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            System.out.println(conn);

            /*
            * 填充占位符
            * 将参数存入可变数组参数args中
            * */
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            /*
            * 执行sql语句
            * */
            rs = ps.executeQuery();
//            System.out.println("==========================");
//            System.out.println("rs.next = " + rs.next());
            //获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();

            //通过ResultSetMetaData获取结果集中的列数
            System.out.println("结果集中的表共有列数：" + rsmd.getColumnCount());
            //System.out.println(rs.next());
            if (rs.next()) {
                Customer cust = new Customer();
                /*
                * 处理结果集一行数据中的每一个列
                * 输出columValue和每一次循环的Customer变量cust，可查看变化
                * */
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    //获取列值，结果集中的下标从1开始
                    Object columValue = rs.getObject(i + 1);
                    System.out.println(i + "columValue的值为：" + columValue);

                    //获取每个列的列名
//					String columnName = rsmd.getColumnName(i + 1);
                    //获取每个列的别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //getDeclaredField方法返回一个Field对象，它反映此Class对象所表示的类或接口的指定已声明字段
                    // 给cust对象指定的columnName属性，赋值为columValue：通过反射
                    System.out.println(Customer.class);
                    //游标获取每个字段后便将其字段的类型赋值给field
                    Field field = Customer.class.getDeclaredField(columnLabel);
                    System.out.println("field的值为：" + field);
                    field.setAccessible(true);
                    //每次循环便将获取的结果集的columValue（列类型）值赋值给cust类变量中，一次存入Customer中
                    field.set(cust, columValue);
                    System.out.println(i + " " + cust);

                }
                return cust;
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

}
