package com.example.CSC_Mudassir.freelancing;

import java.util.concurrent.atomic.AtomicInteger;

public class Project {
    private String ProjectName;
    private String ProjectDescription;
    private String TeacherContactNo;
    private String TeacherDepartment;
    private String TeacherEmail;
    private long timeStamp;
    private static final AtomicInteger count = new AtomicInteger(0);
    private String PrimaryID;
    public Project() {
    }

    public Project(String projectName, String projectDescription, String teacherContactNo, String teacherDepartment, long timeStamp, String primaryID) {
        ProjectName = projectName;
        ProjectDescription = projectDescription;
        TeacherContactNo = teacherContactNo;
        TeacherDepartment = teacherDepartment;
        this.timeStamp = timeStamp;
        PrimaryID = primaryID;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public void setPrimaryID(String primaryID) {
        PrimaryID = primaryID;
    }

    public String getPrimaryID() {
        return PrimaryID;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectDescription() {
        return ProjectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        ProjectDescription = projectDescription;
    }

    public String getTeacherContactNo() {
        return TeacherContactNo;
    }

    public void setTeacherContactNo(String teacherContactNo) {
        TeacherContactNo = teacherContactNo;
    }

    public String getTeacherDepartment() {
        return TeacherDepartment;
    }

    public void setTeacherDepartment(String teacherDepartment) {
        TeacherDepartment = teacherDepartment;
    }
}
