package com.kais.CRUD;

import com.kais.bean.ExamStudent;
import com.kais.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * @author Kais
 * @create 2021-08-19-11:09
 * @desc 针对表ExamStudent的练习
 */
public class ExerciseForExamStudent {

    @Test
    public void queryTest(){

        ExamStudent examStudent = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择你要输入的查找类型：");
        System.out.println("a:准考证号");
        System.out.println("b:身份证号");

        String choose = scanner.next();
        if(choose.equals("a")){

            System.out.println("请输入准考证号：");
            String yourExamCard = null;
            yourExamCard = scanner.next();

            String sqlExamCard = "select FlowID flowId,Type type,IDCard IdCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where examCard = ?";
            examStudent = queryForExamStudent(sqlExamCard,yourExamCard);
            if(examStudent == null){
                System.out.println("查无此人！请从新进入程序");
            }else{
                System.out.println(examStudent);
            }
        }else if(choose.equals("b")){
            System.out.println("请输入身份证号：");
            String yourIdCard = scanner.next();

            String sqlIdCard = "select FlowID flowId,Type type,IDCard IdCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where examCard = ?";
            examStudent = queryForExamStudent(sqlIdCard,yourIdCard);
            if(examStudent == null){
                System.out.println("查无此人！请从新进入程序");
            }else{
                System.out.println(examStudent);
            }
        }else{
            System.out.println("您输入的有误！请重新进入程序。");
        }
    }

    /*
    * 增加信息
    * */
    @Test
    public void insertTest(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("四级/六级：");
        int type = scanner.nextInt();
        System.out.print("身份证号：");
        String IDCard = scanner.next();
        System.out.print("准考证号：");
        String examCard = scanner.next();
        System.out.print("学生姓名：");
        String studentName = scanner.next();
        System.out.print("所在城市：");
        String location = scanner.next();
        System.out.print("考试成绩：");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent (type,IDCard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
        int insertCount = update(sql,type,IDCard,examCard,studentName,location,grade);
        if(insertCount > 0){
            System.out.println("添加成功！");
        }else{
            System.out.println("添加失败");
        }
    }
    @Test
    public void deleteTest(){
        System.out.println("请输入学生的考号：");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "delete from examstudent where examCard = ?";
        int deleteCount = update(sql, examCard);
        if(deleteCount > 0){
            System.out.println("删除成功");
        }else{
            System.out.println("查无此人，请重新输入");
        }
    }


    public ExamStudent queryForExamStudent(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //4.执行sql语句
            rs = ps.executeQuery();
            //5.获取结果集
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rs.next()) {
                ExamStudent examStudent = new ExamStudent();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    Object columnValue = rs.getObject(i + 1);

                    //获取字段的别名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    System.out.println(columnName);
                    //field的使用见QueryForCustomers
                    Field field = ExamStudent.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(examStudent,columnValue);
                }

                return examStudent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.关闭连接
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    public int update(String sql,Object...args)  {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
        return 0;
    }



}
