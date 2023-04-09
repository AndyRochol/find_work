package com.example.incomplete_project.Home;

import android.net.Uri;

public class post_community {
    String Img_Url;
    String description;
    String worker_need;
    String publisher_id;
    String post_id;

    public post_community(){

    }

    public post_community(String img_Url, String description, String worker_need , String publisher_id , String post_id) {
        Img_Url = img_Url;
        this.description = description;
        this.worker_need = worker_need;
        this.publisher_id = publisher_id;
        this.post_id = post_id;
    }

    public String getImg_Url() {
        return Img_Url;
    }


    public void setImg_Url(String img_Url) {
        Img_Url = img_Url;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorker_need() {
        return worker_need;
    }

    public void setWorker_need(String worker_need) {
        this.worker_need = worker_need;
    }
}
