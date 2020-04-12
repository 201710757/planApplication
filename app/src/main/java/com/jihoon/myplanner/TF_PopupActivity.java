package com.jihoon.myplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class TF_PopupActivity extends Activity {

    EditText title;
    EditText todo;
    TextView notice;
    String TAG = "jihoonDebugging";
    RadioButton red, orange, yellow, green, blue, dark_blue, purple;
    RadioButton[] radio = new RadioButton[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tf_popup_activity);


        //UI 객체생성
        title = (EditText) findViewById(R.id.title);
        todo = (EditText)findViewById(R.id.todo);
        notice = (TextView)findViewById(R.id.notice);

        //데이터 가져오기
        Intent intent = getIntent();
        String _title = intent.getStringExtra("TITLE");
        String _todo = intent.getStringExtra("TODO");
        radio[0] = findViewById(R.id.radioButtonRed);
        radio[1] = findViewById(R.id.radioButtonOrange);
        radio[2] = findViewById(R.id.radioButtonYellow);
        radio[3] = findViewById(R.id.radioButtonGreen);
        radio[4] = findViewById(R.id.radioButtonBlue);
        radio[5] = findViewById(R.id.radioButtonDark_blue);
        radio[6] = findViewById(R.id.radioButtonPurple);

        final int _id = intent.getIntExtra("_ID",-1);
        int color = intent.getIntExtra("COLOR", -1);
        try
        {
            radio[color-1].toggle();
        }
        catch (Exception e)
        {

        }
        int st = intent.getIntExtra("ST", -1);
        title.setText(_title);
        todo.setText(_todo);
        if(st == 0)
            notice.setText("First 목표");
        else if(st == 1)
            notice.setText("Second 목표");
        else if(st == 2)
            notice.setText("Third 목표");
        else if(st == -1) {
            notice.setText("새 목표");
            title.setText("");
            todo.setText("");
        }
        else
            notice.setText((st + 1) + "th 목표");

        Button buttonOK = (Button)findViewById(R.id.ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rad = -1;
                Log.d(TAG, "RADIO COLOR = " + radio[0].isChecked() + radio[1].isChecked() + radio[2].isChecked() + " " + radio[3].isChecked() + " " + radio[4].isChecked() + " " + radio[5].isChecked() + " " + radio[6].isChecked());
                try
                {
                    String tmpTitle = title.getText().toString();
                    String tmpTodo = todo.getText().toString();
                    Log.d(TAG, "RADIO COLOR = " + radio[0].isChecked() + radio[1].isChecked() + radio[2].isChecked() + " " + radio[3].isChecked() + " " + radio[4].isChecked() + " " + radio[5].isChecked() + " " + radio[6].isChecked());
                    //여기서 못불러온다 해결합시다아
                    for(int i=0;i<7;i++)
                    {
                        Log.d(TAG, "RADIO COLOR = " + radio[0].isChecked() + radio[1].isChecked() + radio[2].isChecked() + " " + radio[3].isChecked() + " " + radio[4].isChecked() + " " + radio[5].isChecked() + " " + radio[6].isChecked());
                        if(radio[i].isChecked())
                        {
                            rad = (i+1);
                            Log.d(TAG, "&&&&&&&&&&&&&&&&&" + rad);
                            break;
                        }
                    }



                    if(!tmpTitle.equals(""))
                    {
                        Intent intent = new Intent();
                        intent.putExtra("TITLE", tmpTitle);
                        intent.putExtra("TODO", tmpTodo);
                        intent.putExtra("COLOR", rad);
                        intent.putExtra("ID", _id);

                        Log.d(TAG, "&&&&&&&&&&&&&&&&&@@@@@@@@@@" + rad);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else finish();
                    //액티비티(팝업) 닫기

                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                }
            }
        });
        Button buttonX = (Button)findViewById(R.id.cancel);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}


