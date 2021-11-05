package com.kais.bean;

/**
 * @author Kais
 * @create 2021-08-18-22:04
 * @desc 学生四级成绩表
 */
public class ExamStudent {

    private int flowId;
    private int type;
    private String IdCard;
    private String examCard;
    private String studentName;
    private String location;
    private int grade;


    public ExamStudent() {
    }

    public ExamStudent(int flowId, int type, String idCard, String examCard, String studentName, String location, int grade) {
        this.flowId = flowId;
        this.type = type;
        IdCard = idCard;
        this.examCard = examCard;
        this.studentName = studentName;
        this.location = location;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "======查询结果====="
                + "\n流水号：" + flowId
                + "\n四级/六级：" + type
                + "\n身份证号：" + IdCard
                + "\n准考证号：" + examCard
                + "\n学生姓名：" + studentName
                + "\n区域：" + location + "\n"
                + "成绩：" + grade
                ;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
