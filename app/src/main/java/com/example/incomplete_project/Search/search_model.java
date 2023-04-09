package com.example.incomplete_project.Search;

public class search_model {
    private String name ;
    private String img;
    private String tag;
    private String userid;
    private String auth;

    public search_model(){

    }

    public search_model(String name, String img, String tag, String userid) {
        this.name = name;
        this.img = img;
        this.tag = tag;
        this.userid = userid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
