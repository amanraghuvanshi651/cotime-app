package com.example.cotime_app.onboarding.helperClass;

public class OnBoardingList {

    String title;
    String decs;
    Integer img;

    public OnBoardingList() {
    }

    public OnBoardingList(String title, String decs, Integer img_name) {
        this.title = title;
        this.decs = decs;
        this.img = img_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
