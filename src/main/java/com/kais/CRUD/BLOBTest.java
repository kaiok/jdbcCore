package com.kais.CRUD;

import com.kais.bean.Actor;
import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Kais
 * @create 2021-08-20-15:31
 * @desc BLOL类型数据练习
 */
public class BLOBTest {

    /*
    * 插入一条BLOB数据
    * */
   @Test
    public void InsertTest() throws Exception {
       Connection conn = JDBCUtils.getConnection();

       String sql = "insert into actor(id,name,photo)values(?,?,?)";

       PreparedStatement ps = conn.prepareStatement(sql);
       ps.setObject(1,20);
       ps.setObject(2,"xxx");

      FileInputStream fileInputStream = new FileInputStream("src/main/test.jpg");
       ps.setObject(3,fileInputStream);

       ps.execute();

       fileInputStream.close();
       JDBCUtils.closeResource(conn,ps);
   }

   /*
   * 修改一条数据
   * */
   @Test
    public void updateTest() throws Exception {
       Connection conn = JDBCUtils.getConnection();
       String sql = "update actor set photo = ? where id = ?";
       PreparedStatement ps = conn.prepareStatement(sql);

       FileInputStream fileInputStream = new FileInputStream("src/main/beautyfulgirl.jpg");

       ps.setObject(1,fileInputStream);
       ps.setObject(2,10);

       ps.execute();
       fileInputStream.close();
       JDBCUtils.closeResource(conn,ps);
   }

   /*
   * 从数据库中读取BLOB数据
   * */
   @Test
    public void queryBlob() throws Exception {

       String sql = "select id,name,photo from actor where id = ?";
       Connection conn = JDBCUtils.getConnection();
       PreparedStatement ps = conn.prepareStatement(sql);
       ResultSet rs = null;

       ps.setObject(1,10);
       rs = ps.executeQuery();

       if(rs.next()){
           int id = rs.getInt(1);
           String name = rs.getString(2);

           Actor actor = new Actor(id,name);
           System.out.println(actor);

           //读取BLOB字段
           Blob blob = rs.getBlob(3);
           InputStream inputStream = blob.getBinaryStream();
           //outputgirl.jpg文件将会生成在项目路径下
           OutputStream outputStream = new FileOutputStream("outputgirl.jpg");
           byte [] bytes = new byte[1024];
           int len = 0;
           while ((len = inputStream.read(bytes)) != -1) {
               outputStream.write(bytes,0,len);
           }
           JDBCUtils.closeResource(conn,ps,rs);
           if(inputStream != null){
               inputStream.close();
           }
           if(outputStream != null){
               outputStream.close();
           }
       }


   }


}
