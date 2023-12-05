package com.mycomapny.meditaionapp;

public class Upload {
    private String mImageUrl;

    public Upload(){

    }

    public Upload(String imageUrl){
        mImageUrl = imageUrl;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public void setmImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

}
