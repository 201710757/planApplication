package com.jihoon.myplanner;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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

        return view;
    }

    public void listUpdateCheckBox_()
    {
        try {
            adapter.Listclear();
            cursor = db.rawQuery("SELECT title, todo, color, done FROM totalTodo order by _id;", null);
            //startManagingCursor(cursor);
            while (cursor.moveToNext())
            {
                String __title = cursor.getString(0);
                String __todo = cursor.getString(1);
                int __color = cursor.getInt(2);
                int __done = cursor.getInt(3);


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
}