package com.kais.CRUD;

import com.kais.bean.ExamStudent;
import com.kais.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * @author Kais
 * @create 2021-08-18-21:52
 */


/*
* 实现操作：输入学生考号，查询examstudent表中的学生信息
* */
public class QueryForExercise {


    public static void main(String[] args) {

        ExamStudent examStudent = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择你要输入的查找类型：");
        System.out.println("a:准考证号");
        System.out.println("b:身份证号");

        String choose = scanner.next();
        if(choose.equals("a")){

            System.out.println("请输入准考证号：");
            String yourExamCard = scanner.next();


            String sqlExamCard = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where examCard = ?";
            examStudent = queryForExamStudent(sqlExamCard,yourExamCard);
            if(examStudent == null){
                System.out.println("查无此人！请从新进入程序");
            }else{
                System.out.println(examStudent);
            }

        }else if (choose.equals("b")){


            System.out.println("请输入身份证号：");
            String yourIdCard = scanner.next();

            String sqlIdCard = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where IdCard = ?";
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

    private static ExamStudent queryForExamStudent(String sql, String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ExamStudent examStudent = null;

        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1,string);

            //4.执行sql语句
            rs = ps.executeQuery();
            //5.获取结果集
            while(rs.next()){
                examStudent = new ExamStudent(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.关闭连接
            JDBCUtils.closeResource(conn,ps,rs);
        }

        return examStudent;
    }

}
