package com.example.incomplete_project;

public class sign_up_mode {
    String username ;
    String imglink;
    String bgimglink;
    String bio;
    String country;
    String uid;
    String work_tag;
    int need_in_no;

   public sign_up_mode(){

    }

    public sign_up_mode(String username , String imglink, String bgimglink, String bio, String country , String uid , String work_tag , int need_in_no) {
        this.username = username;
        this.imglink = imglink;
        this.bgimglink = bgimglink;
        this.bio = bio;
        this.uid = uid;
        this.country = country;
        this.work_tag = work_tag;
        this.need_in_no = need_in_no;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWork_tag() {
        return work_tag;
    }

    public void setWork_tag(String work_tag) {
        this.work_tag = work_tag;
    }

    public int getNeed_in_no() {
        return need_in_no;
    }

    public void setNeed_in_no(int need_in_no) {
        this.need_in_no = need_in_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBgimglink() {
        return bgimglink;
    }

    public void setBgimglink(String bgimglink) {
        this.bgimglink = bgimglink;
    }



    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }
}
