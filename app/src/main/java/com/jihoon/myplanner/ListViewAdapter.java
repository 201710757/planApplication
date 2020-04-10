package com.jihoon.myplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private TextView title;
    private TextView todo;

    public ArrayList<ListViewItem> listViewItemList = new ArrayList<>();
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_show_plan, parent, false);
        }
        title = (TextView)convertView.findViewById(R.id.test1);
        todo = (TextView)convertView.findViewById(R.id.test2);
        title.setSingleLine();
        todo.setSingleLine();

        ListViewItem listViewItem = listViewItemList.get(position);

        title.setText(listViewItem.getTitle());
        todo.setText(listViewItem.getTodo());
        return convertView;
    }
    public void Listclear()
    {
        listViewItemList = new ArrayList<>();
    }

    public void addItem(String title, String todo, int id)
    {
        ListViewItem item = new ListViewItem();

        item.setTitle(title);
        item.setTodo(todo);
        item.setId(id);

        listViewItemList.add(item);
    }

    // 그지같은 코드
    public String[] returnRes(int pos)
    {
        String [] res = {listViewItemList.get(pos).getTitle(), listViewItemList.get(pos).getTodo()};
        return res;
    }
    // 그지같은 코드
    public int returnID(int pos)
    {
        int res = listViewItemList.get(pos).getId();
        return res;
    }
}
