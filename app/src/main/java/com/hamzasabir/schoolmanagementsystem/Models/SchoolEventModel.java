package com.hamzasabir.schoolmanagementsystem.Models;

public class SchoolEventModel {
    String img1,img2,img3,eventName,eventDate,eventDescription;

    public SchoolEventModel() {
    }

    public SchoolEventModel(String img1, String img2, String img3, String eventName, String eventDate, String eventDescription) {
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
