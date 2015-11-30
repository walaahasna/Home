package com.example.jit.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by walaa-p on 08/18/2015.
 */
public class Program {
    private String name;
    private String time;
    private String detail;
    private int img;
    private String photo;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    private int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Program(String name,String time,String detail,String photo,int day ){
        this.name = name;
        this.time=time;
        this.detail=detail;
        this.photo = photo;
          this.day=day;
    }

    public Program(String name,String time,String detail,int img,int day ){
        this.name = name;
        this.time=time;
        this.detail=detail;
        this.img = img;
        this.day=day;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);


    }



}

