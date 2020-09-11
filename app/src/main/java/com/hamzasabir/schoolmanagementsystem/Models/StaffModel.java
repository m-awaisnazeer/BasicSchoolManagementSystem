package com.hamzasabir.schoolmanagementsystem.Models;

public class StaffModel {
    String imgUrl,name,degree,experience,describe,schoolUID;

    public StaffModel() {
    }

    public StaffModel(String imgUrl, String name, String degree, String experience, String describe, String schoolUID) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.degree = degree;
        this.experience = experience;
        this.describe = describe;
        this.schoolUID = schoolUID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSchoolUID() {
        return schoolUID;
    }

    public void setSchoolUID(String schoolUID) {
        this.schoolUID = schoolUID;
    }
}
