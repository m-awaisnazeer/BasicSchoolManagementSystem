package com.usamatariq.schoolmanagementsystem.Models;

public class StudentModel {


    String imgURl,name,age,classTaken,describe,uid,schoolUid;
    int totalMarks=0,obtainedMarks=0;

    public StudentModel() {
    }


    public StudentModel(String imgURl, String name, String age, String classTaken, String describe, String uid, String schoolUid, int totalMarks, int obtainedMarks) {
        this.imgURl = imgURl;
        this.name = name;
        this.age = age;
        this.classTaken = classTaken;
        this.describe = describe;
        this.uid = uid;
        this.schoolUid = schoolUid;
        this.totalMarks = totalMarks;
        this.obtainedMarks = obtainedMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public String getImgURl() {
        return imgURl;
    }

    public void setImgURl(String imgURl) {
        this.imgURl = imgURl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClassTaken() {
        return classTaken;
    }

    public void setClassTaken(String classTaken) {
        this.classTaken = classTaken;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSchoolUid() {
        return schoolUid;
    }

    public void setSchoolUid(String schoolUid) {
        this.schoolUid = schoolUid;
    }
}
