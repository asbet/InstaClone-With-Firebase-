package com.betulas.instagramclonejava.model;

public class Post {
    public String email;
    public String commnet;
    public String downloadUrl;

    public Post (String email,String commnet,String downloadUrl){
        this.commnet=commnet;
        this.downloadUrl=downloadUrl;
        this.email=email;
    }


}
