package com.jihoon.myplanner;


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

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;

    private FT_ListViewAdapter adapter;
    private ListView listView;



    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
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
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        dbHelper = new DBHelper(view.getContext(), 1);
        db = dbHelper.getWritableDatabase();

        adapter = new FT_ListViewAdapter();
        listView = (ListView)view.findViewById(R.id.TF_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                
                return true;
            }
        });
        adapter.notifyDataSetChanged();

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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){//일정새로생성
            if(resultCode==RESULT_OK){
                //데이터 받기
                String title = data.getStringExtra("TITLE");
                String todo = data.getStringExtra("TODO");
                int col = data.getIntExtra("COLOR", -1);
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
    }

    public void listUpdateCheckBox_()
    {
        try {
            adapter.Listclear();
            cursor = db.rawQuery("SELECT * FROM totalTodo WHERE _id > 1 order by _id;", null);
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
                    if(__done != 1) adapter.addItem(drawColor[__color-1], __title, __todo);
                }
                catch (Exception e)
                {

                }
                //con.addView(n_layout1);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
    }
}