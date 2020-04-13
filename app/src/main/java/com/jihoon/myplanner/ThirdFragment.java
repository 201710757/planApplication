package com.jihoon.myplanner;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class ThirdFragment extends Fragment {
    // Store instance variables
    String TAG = "jihoonDebugging";
    String tag = "jihoondeibd";

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;

    private FT_ListViewAdapter adapter;
    private ListView listView;

    int fuPosition;
    View view;


    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        Log.d("jihoonGooddddddd", "1");
        ThirdFragment fragment = new ThirdFragment();
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
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.fragment_third, null);
        dbHelper = new DBHelper(view.getContext(), 1);
        db = dbHelper.getWritableDatabase();

        adapter = new FT_ListViewAdapter();
        listView = (ListView)view.findViewById(R.id.TF_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] res = adapter.returnRes(position);
                int resId = adapter.returnID(position);
                int colorId = adapter.returnColor(position);
                Log.d(TAG, "" + res[1] + " " + position);
                Intent intent = new Intent(view.getContext(), TF_PopupActivity.class);
                Log.d(TAG, "★★★★★ : " + res[0] + " " + res[1]);
                intent.putExtra("TITLE", res[0]);
                intent.putExtra("TODO", res[1]);
                intent.putExtra("_ID", resId);
                intent.putExtra("COLOR", colorId);
                intent.putExtra("ST", position);
                //intent.putExtra("ST", position);
                startActivityForResult(intent, 2);
                //((MainActivity)getActivity()).refresh();

                Log.d(TAG, "request code : " + 2);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                fuPosition = position;
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(view.getContext());
                // 다이얼로그 메세지
                alertdialog.setMessage("일정을 완료하시겠습니까?");

                // 확인버튼
                alertdialog.setNegativeButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int resId = adapter.returnID(fuPosition);
                        //deleteDB(resId);//
                        modifyDB(resId);
                        //Intent intent = new Intent(getContext(), MainActivity.class);
                        //startActivity(intent);
                        ((MainActivity)getActivity()).refresh();
                        Log.d("jihoonGoodd", "refThird");
                    }
                });

                // 취소버튼
                alertdialog.setPositiveButton("아니요", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // 메인 다이얼로그 생성
                AlertDialog alert = alertdialog.create();
                // 아이콘 설정
                //alert.setIcon(R.drawable.ic_launcher);
                // 타이틀
                //alert.setTitle("");
                // 다이얼로그 보기
                alert.show();

                //((MainActivity)getActivity()).refresh();
                return true;
            }
        });
        adapter.notifyDataSetChanged();
        Log.d("jihoonkimn", "Third Fragment createView");

        Log.d("jihoonGood", "o7");
        Button button = (Button)view.findViewById(R.id.TF_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TF_PopupActivity.class);
                intent.putExtra("TITLE", "");
                intent.putExtra("TODO", "");
                startActivityForResult(intent, 1);
                Log.d(TAG, "request code : " + 1);
            }
        });
        listUpdateCheckBox_();

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("jihoonGooddddddd", "3");
        Log.d("jihoonGoodd", "hi1?");

        Log.d("jihoonGooddddddd", "CREATETHIRD");



        Log.d("jihoonGooddddddd", "4");

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("jihoonGooddddddd", "5");
        if(requestCode==1){//일정새로생성
            if(resultCode==RESULT_OK){
                //데이터 받기
                String title = data.getStringExtra("TITLE");
                String todo = data.getStringExtra("TODO");
                int col = data.getIntExtra("COLOR", -1);
                if(col == -1) return;
                Log.d(TAG, "Hello Popup" + " Title : " + title + " todo : " + todo + " id : nooo");

                Log.d(TAG, "COLOR HELLO" + " : " + title + " todo : " + todo + " id : " + col);


                try {

                    //deleteDB(tmpYear, tmpMonth, tmpDay, id);
                    String sql = String.format("INSERT INTO totalTodo VALUES(null, '" + title + "','" + todo + "', " + col + ", 0);");
                    //sql = "INSERT INTO totalTodo VALUES(null, 'titl1e', 'todo1', 1, 0)";
                    db.execSQL(sql);
                    Log.d(TAG, "SQL : " + sql);
                }
                catch (Exception e)
                {
                    Log.d(TAG, "Hello Error 33" + e.getMessage());
                }//INSERT INTO dateTodo VALUES(null, '2020','4','12','f','fff');
                listUpdateCheckBox_();
                //listUpdateCheckBox_(LastYear,LastMonth,LastDay);
            }
        }
        else if(requestCode == 2)
        {
            Log.d("jihoonGooddddddd", "6");
            if(resultCode == RESULT_OK)
            {
                String title = data.getStringExtra("TITLE");
                String todo = data.getStringExtra("TODO");
                int col = data.getIntExtra("COLOR", -1);
                int id = data.getIntExtra("ID", -1);
                Log.d(TAG, "Hello Popup" + " Title : " + title + " todo : " + todo + " id : nooo");

                Log.d(TAG, "COLOR HELLO" + " : " + title + " todo : " + todo + " id : " + col);


                try {

                    //deleteDB(tmpYear, tmpMonth, tmpDay, id);
                    String sql = String.format("UPDATE totalTodo SET title = '" + title + "', todo = '" + todo + "', color = " + col + " WHERE _id = " + id);
                    //sql = "INSERT INTO totalTodo VALUES(null, 'titl1e', 'todo1', 1, 0)";
                    db.execSQL(sql);
                    Log.d(TAG, "SQL : " + sql);
                }
                catch (Exception e)
                {
                    Log.d(TAG, "Hello Error 33" + e.getMessage());
                }//INSERT INTO dateTodo VALUES(null, '2020','4','12','f','fff');
                listUpdateCheckBox_();
                //listUpdateCheckBox_(LastYear,LastMonth,LastDay);
            }
        }
    }

    public void listUpdateCheckBox_()
    {
        Log.d("jihoonGooddddddd", "7");
        try {
            adapter.Listclear();
            cursor = db.rawQuery("SELECT * FROM totalTodo order by _id;", null);
            //startManagingCursor(cursor);
            while (cursor.moveToNext())
            {
                int __id = cursor.getInt(0);
                String __title = cursor.getString(1);
                String __todo = cursor.getString(2);
                int __color = cursor.getInt(3);
                int __done = cursor.getInt(4);

                Log.d(TAG, "&&&&&42yh24h&&&&&&&&&&&& : " + __id + " " + __title + " " + __todo +  " " + __color +  " " + __done);

                try {
                    Drawable []drawColor = {getResources().getDrawable(R.drawable.red),
                            getResources().getDrawable(R.drawable.orange),
                            getResources().getDrawable(R.drawable.yellow),
                            getResources().getDrawable(R.drawable.green),
                            getResources().getDrawable(R.drawable.blue),
                            getResources().getDrawable(R.drawable.dark_blue),
                            getResources().getDrawable(R.drawable.purple)};

                    //CustomShowPlan n_layout1 = new CustomShowPlan(view.getContext().getApplicationContext(), __title, __todo, number);
                    Log.d(TAG, "COLOR : " + drawColor[__color-1] + " " + __title + " " + __todo);
                    if(__done != 1) adapter.addItem(drawColor[__color-1], __title, __todo, __id , __color);
                }
                catch (Exception e)
                {

                }
                //con.addView(n_layout1);
            }
            adapter.notifyDataSetChanged();
            Log.d("jihoonkimn", "Third Fragment List Update");

            Log.d("jihoonGooddddddd", "8");
            Log.d("jihoonGood", "o8");
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        Log.d("jihoonGooddddddd", "9");
    }
    public void deleteDB(int id)
    {
        //Log.d(TAG, "DELETE : " + year + " " + month + " " + day + " " + id);
        String sql = String.format("DELETE FROM totalTodo WHERE _id = '" + id + "';");
        db.execSQL(sql);
        listUpdateCheckBox_();
    }
    public void modifyDB(int id)
    {
        Log.d("jihoonGooddddddd", "10");
        String sql = String.format("UPDATE totalTodo SET done = 1 WHERE _id = " + id + ";");
        db.execSQL(sql);
        listUpdateCheckBox_();
        Log.d("jihoonGooddddddd", "11");
    }
}