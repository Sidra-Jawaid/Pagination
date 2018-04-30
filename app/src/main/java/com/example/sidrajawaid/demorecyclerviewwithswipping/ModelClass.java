package com.example.sidrajawaid.demorecyclerviewwithswipping;

public class ModelClass  {
    private int imageview;
    String textview1;
    String textview2;

    public ModelClass(int imageview, String textview1, String textview2) {
        this.imageview = imageview;
        this.textview1 = textview1;
        this.textview2 = textview2;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public String getTextview1() {
        return textview1;
    }

    public void setTextview1(String textview1) {
        this.textview1 = textview1;
    }

    public String getTextview2() {
        return textview2;
    }

    public void setTextview2(String textview2) {
        this.textview2 = textview2;
    }
}
