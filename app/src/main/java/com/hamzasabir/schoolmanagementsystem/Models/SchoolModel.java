package com.hamzasabir.schoolmanagementsystem.Models;

public class SchoolModel {
    String name,address,description,moto,school_type,principlename,prinicpleContact,img1,img2,img3,uid;

    public SchoolModel() {
    }

    public SchoolModel(String name, String address, String description, String moto, String school_type, String principlename, String prinicpleContact, String img1, String img2, String img3,String uid) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.moto = moto;
        this.school_type = school_type;
        this.principlename = principlename;
        this.prinicpleContact = prinicpleContact;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getPrinciplename() {
        return principlename;
    }

    public void setPrinciplename(String principlename) {
        this.principlename = principlename;
    }

    public String getPrinicpleContact() {
        return prinicpleContact;
    }

    public void setPrinicpleContact(String prinicpleContact) {
        this.prinicpleContact = prinicpleContact;
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
}
