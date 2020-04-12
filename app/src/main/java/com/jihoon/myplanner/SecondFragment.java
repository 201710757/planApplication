package com.jihoon.myplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondFragment extends Fragment {
    // Store instance variables
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    private ListView listView;
    private ListViewAdapter adapter;
    String TAG = "jihoonDebugging";
    View view;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    int LastYear, LastMonth, LastDay;
    TextView dateShow;


    Calendar sf_cal1, sf_cal2;
    DateFormat sf_df1, sf_df2;

    String [] seven;

    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "SF_HI?ATTACH");
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
        view = inflater.inflate(R.layout.fragment_second, container, false);
        dbHelper = new DBHelper(view.getContext(), 1);
        db = dbHelper.getWritableDatabase();
        adapter = new ListViewAdapter();
        listView = (ListView)view.findViewById(R.id.SF_listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String _tmp_ = adapter.returnTitle(position); //토 2020/4/11
                String __year_ = _tmp_.substring(2,6);
                String __month_ = _tmp_.substring(7,9);
                String __day_ = _tmp_.substring(10,12);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });


        int[] res = returnDate();
        LastYear = res[0];
        LastMonth = res[1];
        LastDay = res[2];
        dateShow = (TextView)view.findViewById(R.id.SF_textViewDate);


        seven = returnWeekDay();
        dateShow.setText("오늘부터 일주일간의 계획입니다. \n(" + seven[0] + " ~ " + seven[1] + "~)");
        listUpdateFunction(LastYear, LastMonth, LastDay);

        Button button = (Button)view.findViewById(R.id.SF_buttonFirst);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[]dat = returnDate();
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LastYear = year;
                        LastMonth = month + 1; //예외처리 직접
                        LastDay = dayOfMonth;

                        seven = returnWeekDay();

                        if(LastYear == dat[0] && LastMonth == dat[1] && LastDay == dat[2])
                            dateShow.setText("오늘부터 일주일간의 계획입니다. \n(" + seven[0] + " ~ " + seven[1] + "~)");
                        else dateShow.setText("일주일간의 계획입니다.\n" + seven[0] + " ~ " + seven[1]);

                        listUpdateFunction(LastYear, LastMonth, LastDay);
                    }
                };
                Log.d(TAG, "SF dd : " + dat[0] + " " + dat[1] + " " + dat[2]);
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), callbackMethod, dat[0], dat[1]-1, dat[2]); //이것만 월에 1빼주는 예외처리 직접 작성
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "SF_HI?START");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "SF_HI?RESUME");
        listUpdateFunction(LastYear, LastMonth, LastDay);
    }

    public void listUpdateFunction(int year, int month, int day)
    {
        adapter.Listclear();

        //adapter.addItem("test","testset",99);
        String tmp = null;
        DateFormat df = null;
        Date settingTime = null;
        SimpleDateFormat settingDay = null;
        Calendar cal = null;
        String WeekDay = null;
        int dayW = 0;
        String []dayy = {"일", "월", "화", "수", "목", "금", "토"};
        //7번실행 날짜바꿔서s
        try {
            cal = Calendar.getInstance();
            settingDay = new SimpleDateFormat("yyyy-MM-dd");
            settingTime = settingDay.parse("" + year + "-" + month + "-" + day);
            cal.setTime(settingTime);
            //String tmp = settingDay.format(settingTime); //2020-04-10

            df = new SimpleDateFormat("yyyy-MM-dd");
            tmp = df.format(cal.getTime()); //2020-04-10
            Log.d(TAG, "SF hello : " + tmp);
            //cal.add(Calendar.DATE, 1);

            dayW = cal.get(Calendar.DAY_OF_WEEK);
            WeekDay = dayy[dayW-1];
        }
        catch (Exception e)
        {
            Log.d(TAG, "SF hi?" + e.getMessage());
        }



        for(int i=0;i<7;i++)
        {
            try {
                String subYEAR = tmp.substring(0,4); // YEAR
                String subMONTH = Integer.toString(Integer.parseInt(tmp.substring(5,7))); // MONTH
                String subDAY = Integer.toString(Integer.parseInt(tmp.substring(8,10))); //DAY
                String _subYear = tmp.substring(0,4);
                String _subMonth = tmp.substring(5,7);
                String _subDay = tmp.substring(8,10);

                dayW = cal.get(Calendar.DAY_OF_WEEK);
                WeekDay = dayy[dayW-1];

                Log.d(TAG, "SF database : " + subYEAR + " " + subMONTH + " " + subDAY);
                cursor = db.rawQuery("SELECT _id, title, todo FROM dateTodo WHERE year = '" + subYEAR + "' AND month = '" + subMONTH + "' AND day = '" + subDAY + "' order by _id;", null);
                //startManagingCursor(cursor);
                String info = "";
                int cnt = 0, __id = -1;
                while (cursor.moveToNext())
                {
                    String __title = cursor.getString(1);
                    try {
                        __title = __title.substring(0, 20);
                    }
                    catch (Exception e)
                    {

                    }

                    if(__title == null || __title.equals("")) break;


                    String __todo = cursor.getString(2);
                    Log.d(TAG, "SF _ TITLE : " + __title);
                    __id = cursor.getInt(0);
                    cnt++; //3개까지만 출력하려고..


                    info = info.concat(__title);

                    if(cnt < 3) info = info.concat(" / ");
                    else break;
                }
                Log.d(TAG, "SF _ INFO : " + info);
                if(!info.equals(""))
                {
                    if(cnt == 3)
                        adapter.addItem(_subYear + "/" + _subMonth + "/" + _subDay + " " + WeekDay, info + "...", __id);
                    else adapter.addItem(_subYear + "/" + _subMonth + "/" + _subDay + " " + WeekDay, info.substring(0, info.length()-3), __id);
                    adapter.notifyDataSetChanged();
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, e.getMessage());
            }

            cal.add(Calendar.DATE, 1);
            tmp = df.format(cal.getTime()); //2020-04-10
            Log.d(TAG, "SF hello : " + tmp);
            adapter.notifyDataSetChanged();
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

    public String[] returnWeekDay()
    {
        sf_cal1 = Calendar.getInstance();
        sf_df1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sf_cal1.setTime(sf_df1.parse("" + LastYear + "-" + LastMonth + "-" + LastDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sf_cal2 = Calendar.getInstance();
        sf_df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sf_cal2.setTime(sf_df2.parse("" + LastYear + "-" + LastMonth + "-" + LastDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sf_cal2.add(Calendar.DATE, 7);

        String [] tmp = {sf_df1.format(sf_cal1.getTime()), sf_df2.format(sf_cal2.getTime())};
        return tmp;
    }

    public String getDateday(int year, int month, int day)
    {
        String []dayy = {"일", "월", "화", "수", "목", "금", "토"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = ""+year+"-"+month+"-"+day;
        Date nDate = null;
        try {
            nDate = dateFormat.parse(date);
        }
        catch (Exception e)
        {

        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        return dayy[dayNum-1];
    }
}


















