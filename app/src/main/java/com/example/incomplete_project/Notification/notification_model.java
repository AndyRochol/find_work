package com.example.incomplete_project.Notification;

public class notification_model {
    public String img_url;
    public String user_id;
    private String auth;

    public  notification_model(){

    }

    public notification_model(String img_url, String user_id) {

        this.img_url = img_url;

        this.user_id = user_id;
    }
    public notification_model(String img_url, String user_id , String auth) {

        this.img_url = img_url;

        this.user_id = user_id;

        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
