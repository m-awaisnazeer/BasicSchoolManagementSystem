package com.hamzasabir.schoolmanagementsystem.Models;

public class StudentModel {
    String imgURl,name,age,classTaken,describe,uid,schoolUid;

    public StudentModel() {
    }

    public StudentModel(String imgURl, String name, String age, String classTaken, String describe, String uid, String schoolUid) {
        this.imgURl = imgURl;
        this.name = name;
        this.age = age;
        this.classTaken = classTaken;
        this.describe = describe;
        this.uid = uid;
        this.schoolUid = schoolUid;
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
