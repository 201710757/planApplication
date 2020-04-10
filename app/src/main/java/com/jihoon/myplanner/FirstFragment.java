package com.jihoon.myplanner;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    String TAG = "jihoonDebugging";
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    int tmpYear, tmpMonth, tmpDay;
    String tmpTitle, tmpTodo;
    EditText tvLabel;
    TextView dateShow;
    TextView showTitle, showTodo;
    LinearLayout con;

    int fuPosition;

    int LastYear, LastMonth, LastDay;

    private ListView listView;
    private ListViewAdapter adapter;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    View view;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        dbHelper = new DBHelper(view.getContext(), 1);
        db = dbHelper.getWritableDatabase();
        adapter = new ListViewAdapter();
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //
                String[] res = adapter.returnRes(position);
                int resId = adapter.returnID(position);
                Log.d(TAG, "" + res[1] + " " + position);
                Intent intent = new Intent(view.getContext(), PopupActivity.class);
                intent.putExtra("TITLE", res[0]);
                intent.putExtra("TODO", res[1]);
                intent.putExtra("_ID", resId);
                intent.putExtra("ST", position);
                startActivityForResult(intent, 2);

                Log.d(TAG, "request code : " + 2);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                fuPosition = position;
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(view.getContext());
                // 다이얼로그 메세지
                alertdialog.setMessage("삭제하시겠습니까?");

                // 확인버튼
                alertdialog.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int resId = adapter.returnID(fuPosition);
                        deleteDB(LastYear, LastMonth, LastDay, resId);
                    }
                });

                // 취소버튼
                alertdialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // 메인 다이얼로그 생성
                AlertDialog alert = alertdialog.create();
                // 아이콘 설정
                //alert.setIcon(R.drawable.ic_launcher);
                // 타이틀
                alert.setTitle("제목");
                // 다이얼로그 보기
                alert.show();

                return true;
            }
        });
        /*
        adapter.addItem("title1", "todo1");
        adapter.addItem("title1", "todo2");
        adapter.addItem("title1", "todo3");
        adapter.addItem("title1", "todo4");
        adapter.addItem("title1", "todo5");
        adapter.addItem("title1", "todo6");
        adapter.addItem("title1", "todo7");
        adapter.addItem("title1", "todo8");
        adapter.addItem("title1", "todo9");
        adapter.addItem("title1", "todo10");
        adapter.addItem("title1", "todo11");
        adapter.addItem("title1", "todo12");
        adapter.addItem("title1", "todo13");
        adapter.addItem("title1", "todo14");

         */
        adapter.notifyDataSetChanged();






        int[] res = returnDate();
        LastYear = res[0];
        LastMonth = res[1];
        LastDay = res[2];
        String[] lst = listUpdateCheckBox(res[0], res[1], res[2]);
        Log.d(TAG, "hi~ : " + res[0] + " " + res[1] + " " + res[2]);
        Log.d(TAG, "hh : " + lst[0] + " " + lst[1]);
        dateShow = (TextView)view.findViewById(R.id.textViewDate);
        dateShow.setText("오늘 날짜 : "+ res[0] + "/" + res[1] + "/" + res[2]);
        listUpdateCheckBox_(res[0], res[1], res[2]);

        Button okButton = (Button)view.findViewById(R.id.buttonok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PopupActivity.class);
                intent.putExtra("TITLE", tmpTitle);
                intent.putExtra("TODO", tmpTodo);
                startActivityForResult(intent, 1);
                Log.d(TAG, "request code : " + 1);
            }
        });


        Button button = (Button)view.findViewById(R.id.buttonFirst);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[]dat = returnDate();
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tmpYear = year;
                        tmpMonth = month+1; //이거도 예외처리 직접작성
                        tmpDay = dayOfMonth;
                        LastYear = tmpYear;
                        LastMonth = tmpMonth;
                        LastDay = tmpDay;

                        Log.d(TAG, "dd : " + tmpYear + " " + tmpMonth + " " + tmpDay);
                        if(tmpYear == dat[0] && tmpMonth == dat[1] && tmpDay == dat[2])
                            dateShow.setText("(오늘) " + tmpYear + "/" + tmpMonth + "/" + tmpDay + " 일정 입니다");
                        else dateShow.setText(tmpYear + "/" + tmpMonth + "/" + tmpDay + " 일정 입니다");
                        String[]res = listUpdateCheckBox(tmpYear, tmpMonth, tmpDay);
                        tmpTitle = res[0];
                        tmpTodo = res[1];

                        listUpdateCheckBox_(tmpYear, tmpMonth, tmpDay);
                    }
                };
                Log.d(TAG, "dd : " + dat[0] + " " + dat[1] + " " + dat[2]);
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), callbackMethod, dat[0], dat[1]-1, dat[2]); //이것만 월에 1빼주는 예외처리 직접 작성
                dialog.show();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){//일정새로생성
            if(resultCode==RESULT_OK){
                //데이터 받기
                String title = data.getStringExtra("TITLE");
                String todo = data.getStringExtra("TODO");
                Log.d(TAG, "Hello Popup" + " Title : " + title + " todo : " + todo + " id : nooo");

                try {

                    //deleteDB(tmpYear, tmpMonth, tmpDay, id);
                    String sql = String.format("INSERT INTO dateTodo VALUES(null, '" + LastYear + "','" + LastMonth + "','" + LastDay + "','" + title + "','" + todo + "');");
                    //String sql_ = String.format("UPDATE dateTodo SET title = '" + title + "', todo = '" + todo + "' where _id = " + id);
                    db.execSQL(sql);
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                }
                listUpdateCheckBox_(LastYear,LastMonth,LastDay);
            }
        }
        else if(requestCode == 2)//일정수정 / 리스트 클릭해서 팝업띄웠을때
        {
            if(resultCode == RESULT_OK)
            {
                String title = data.getStringExtra("TITLE");
                String todo = data.getStringExtra("TODO");
                int id = data.getIntExtra("_ID", -1);
                Log.d(TAG, "LIST CLICKED!!!!! " + " Title : " + title + " todo : " + todo + " id : " + id);

                try {
                    //deleteDB(tmpYear, tmpMonth, tmpDay, id);
                    Log.d(TAG, "UPDATE ID = " + id);
                    //String sql = String.format("INSERT INTO dateTodo VALUES('" + Integer.toString(tmpYear) + "','" + Integer.toString(tmpMonth) + "','" + Integer.toString(tmpDay) + "','" + title + "','" + todo + "');");
                    String sql = String.format("UPDATE dateTodo SET title = '" + title + "', todo = '" + todo + "' where _id = " + id);
                    db.execSQL(sql);
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                }
                listUpdateCheckBox_(LastYear, LastMonth,LastDay);
            }
        }
    }


    public void deleteDB(int year, int month, int day, int id)
    {
        Log.d(TAG, "DELETE : " + year + " " + month + " " + day + " " + id);
        String sql = String.format("DELETE FROM dateTodo WHERE year = '" + Integer.toString(year) + "' AND month = '" + Integer.toString(month) + "' AND day = '" + Integer.toString(day) + "' AND _id = '" + id + "';");
        db.execSQL(sql);
        listUpdateCheckBox_(LastYear, LastMonth,LastDay);
    }




    public String[] listUpdateCheckBox(int year, int month, int day)
    {
        String[] str = new String[2];
        Log.d(TAG, "database : " + year + " " + month + " " + day);
        cursor = db.rawQuery("SELECT title, todo FROM dateTodo WHERE year = '" + Integer.toString(year) + "' AND month = '" + Integer.toString(month) + "' AND day = '" + Integer.toString(day) + "';", null);
        //startManagingCursor(cursor);
        while (cursor.moveToNext())
        {
            for(int i=0;i<2;i++) str[i] = cursor.getString(i);
        }
        return str;
    }

    public void listUpdateCheckBox_(int year, int month, int day)
    {
        try {
            adapter.Listclear();
            //con.removeAllViews();
            //listView.removeAllViews();
            Log.d(TAG, "database : " + year + " " + month + " ");
            cursor = db.rawQuery("SELECT _id, title, todo FROM dateTodo WHERE year = '" + year + "' AND month = '" + month + "' AND day = '" + day + "' order by _id;", null);
            //startManagingCursor(cursor);
            while (cursor.moveToNext())
            {
                String __title = cursor.getString(1);
                String __todo = cursor.getString(2);
                int __id = cursor.getInt(0);

                //CustomShowPlan n_layout1 = new CustomShowPlan(view.getContext().getApplicationContext(), __title, __todo, number);
                adapter.addItem(__title, __todo, __id);
                //con.addView(n_layout1);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
    }


    public int[] returnDate()
    {
        int[] res = new int[3];
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        res[0] = Integer.parseInt(year);
        res[1] = Integer.parseInt(month);
        res[2] = Integer.parseInt(day);
        return res;
    }


}