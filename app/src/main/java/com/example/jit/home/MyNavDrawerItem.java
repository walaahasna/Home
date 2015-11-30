package com.example.jit.home;

/**
 * Created by jit on 16/08/2015.
 */
public class MyNavDrawerItem {

        private String title;
        private int icon;
    public MyNavDrawerItem(){}

    public MyNavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }



    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }}