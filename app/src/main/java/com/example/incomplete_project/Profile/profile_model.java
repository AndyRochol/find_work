package com.example.incomplete_project.Profile;

public class profile_model {

    private String user_name;
    private String profile_url;
    private String back_img_url;

    public profile_model(String user_name, String profile_url, String back_img_url) {
        this.user_name = user_name;
        this.profile_url = profile_url;
        this.back_img_url = back_img_url;
    }

    public profile_model(){}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getBack_img_url() {
        return back_img_url;
    }

    public void setBack_img_url(String back_img_url) {
        this.back_img_url = back_img_url;
    }
}
