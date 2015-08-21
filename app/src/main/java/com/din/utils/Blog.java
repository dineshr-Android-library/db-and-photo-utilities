package com.din.utils;

import blogs.dinesh.com.everything.MainActivity;

/**
 * Created by Dinesh on 8/6/2015.
 */
public class Blog {

    public int blog_no;
    public byte[] photo;
    public String name;
    public String title;
    public String desc;
    public Blog(int num, byte[] p,String n,String t, String d){
        this.blog_no=num;
        this.photo=p;
        this.name=n;
        this.title=t;
        this.desc=d;
    }
    public  Blog(byte[] p,String n,String t, String d){
        this.photo=p;
        this.name=n;
        this.title=t;
        this.desc=d;
    }

    public  Blog(String t, String d){
//        this.blog_no=num;

        this.photo=new MainActivity().getBytes();
        this.name="Dinesh";
        this.title=t;
        this.desc=d;
    }
    public Blog(){

    }
    public int getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(int blog_no) {
        this.blog_no = blog_no;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
