package com.jihoon.myplanner;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomShowPlan extends LinearLayout {
    String title;
    String todo;
    int id;
    public CustomShowPlan(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public CustomShowPlan(Context context , String _title, String _todo, int _id) {
        super(context);
        title= _title;
        todo = _todo;
        id = _id;
        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_show_plan,this,true);
        TextView textView = (TextView)findViewById(R.id.test1);
        textView.setText(id+ ". 할일 : " + title);
        TextView textView1 = (TextView)findViewById(R.id.test2);
        textView1.setText("할일 : " + todo);


    }
}