package com.jihoon.myplanner;

import android.graphics.drawable.Drawable;

public class FT_ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;//todo
    private int _id;
    private int color;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    } //todo
    public void set_id(int i) { _id = i; }
    public void setColor(int i) { color = i; }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public int get_id() { return _id; }
    public int getColor() { return color; }
}