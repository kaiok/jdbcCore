package com.kais.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Kais
 * @create 2021-07-05-14:41
 */

/**
 * 方法描述:获取数据库链接的方式
 *
 * @author Kais
 * @返回值 :
 * @作者 : lxk
 * 时间:2021/9/3 17:13
*/
public class ConnectionTest {

    /**
     * 方法描述:输出：com.mysql.jdbc.JDBC4Connection@490d6c15
     *
     * @返回值 : void
     * @作者 : lxk
     * 时间:2021/9/2 9:23
    */

    @Test
    public void testConnection1() {
        try{
            Driver driver = null;
            //三方数据库的API
            driver = new com.mysql.jdbc.Driver();

            String url = "jdbc:mysql://localhost:3306/jdbc_data";

            Properties info = new Properties();
            info.setProperty("user","root");
            info.setProperty("password","123456");

            java.sql.Connection connect = driver.connect(url, info);
            System.out.println(connect);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 方法描述:输出：com.mysql.jdbc.JDBC4Connection@490d6c15
     * 这里使用反射实例化Driver，不在代码中体现第三方数据库的API。
     * 体现了面向接口编程思想
     *
     * @返回值 : void
     * @作者 : lxk
     * 时间:2021/9/3 17:14
    */
    @Test
    public void testConnection2(){

        try{
            String ClassName = "com.mysql.jdbc.Driver";
            Class clazz = Class.forName(ClassName);
            Driver driver = (Driver) clazz.newInstance();

            String url = "jdbc:mysql://localhost:3306/jdbc_data";

            Properties info = new Properties();
            info.setProperty("user","root");
            info.setProperty("password","123456");

            java.sql.Connection conn = driver.connect(url,info);
            System.out.println(conn);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 方法描述:使用DriverManager类实现数据库的连接。体会获取连接必要的4个基本要素
     *
     * @返回值 : void
     * @作者 : lxk
     * 时间:2021/9/6 9:27
    */
    @Test
    public void testConnection3() {
        try {
            //1.数据库连接的4个基本要素：
            String url = "jdbc:mysql://localhost:3306/jdbc_data";
            String user = "root";
            String password = "123456";
            String driverName = "com.mysql.jdbc.Driver";

            //2.实例化Driver
            Class clazz = Class.forName(driverName);
            Driver driver = (Driver) clazz.newInstance();
            //3.注册驱动
            DriverManager.registerDriver(driver);
            //4.获取连接
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testConnection4() {
        try {
            //1.数据库连接的4个基本要素：
            String url = "jdbc:mysql://localhost:3306/jdbc_data";
            String user = "root";
            String password = "123456";
            String driverName = "com.mysql.jdbc.Driver";

            //2.加载驱动 （1.实例化Driver 2.注册驱动）
            Class.forName(driverName);

            //Driver driver = (Driver) clazz.newInstance();
            //3.注册驱动
            //DriverManager.registerDriver(driver);
            /*
            可以注释掉上述代码的原因，是因为在mysql的Driver类中声明有：
            static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
             */

            //3.获取连接
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*
    * 使用配置文件的方式保存配置信息，在代码中加载配置文件
    * 1.实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码
    * 2.如果修改了配置信息，省去重新编译的过程
    * */


    @Test
    public void testConnection5() throws Exception {
        //1.加载配置文件
        Properties pros = new Properties();

        ClassLoader classLoader = ConnectionTest.class.getClassLoader();


        //src目录下的文件可以直接通过输入文件名通过反射进行获取，返回一个文件输入流
        InputStream resourceAsStream = classLoader.getResourceAsStream("jdbc.properties");
        pros.load(resourceAsStream);

        //2.读取配置信息
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //3.加载驱动
        Class.forName(driverClass);

        //4.获取连接
        java.sql.Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);

    }


}
