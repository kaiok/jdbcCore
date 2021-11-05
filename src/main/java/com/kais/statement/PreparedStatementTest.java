package com.kais.statement;

import org.junit.Test;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author Kais
 * @create 2021-07-06-10:45
 */
public class PreparedStatementTest {

    @Test
    public void updateTest() {

        try{
            Properties properties = new Properties();
            /*
            * PreparedStatementTest.class.getClassLoader();  ？？？
            * */
            ClassLoader classLoader = PreparedStatementTest.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("jdbc.properties");
            properties.load(inputStream);

            String user = properties.getProperty("user");
            System.out.println(user);
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println(conn);

            //4.预编译sql语句，返回Preparedstatement实例
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            PreparedStatement ps= conn.prepareStatement(sql);

            //5.填充占位符
            ps.setString(1,"kais");
            ps.setString(2,"Kais_215@outlook.com");
            //格式化生日
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
            java.util.Date date = simpleDateFormat.parse("1000-01-01");
            ps.setDate(3, new java.sql.Date(date.getTime()));

            //6.执行sql
            ps.execute();

            //7.资源关闭
            ps.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
