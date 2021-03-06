package com.jihoon.myplanner;

import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class FT_ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<FT_ListViewItem> listViewItemList = new ArrayList<FT_ListViewItem>() ;

    // ListViewAdapter의 생성자
    public FT_ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.total_show_plan, parent, false);
            convertView = inflater.inflate(R.layout.test_show_plan, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.ft_ImageView) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.ft_textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.ft_textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        FT_ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void Listclear()
    {
        listViewItemList = new ArrayList<>();
    }
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String desc, int id, int color) {
        FT_ListViewItem item = new FT_ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.set_id(id);
        item.setColor(color);

        listViewItemList.add(item);
    }

    // 그지같은 코드
    public int returnID(int pos)
    {
        int res = listViewItemList.get(pos).get_id();
        return res;
    }
    // 그지
    public String returnTitle(int pos)
    {
        String res = listViewItemList.get(pos).getTitle();
        return res;
    }
    public String[] returnRes(int pos)
    {
        String [] res = {listViewItemList.get(pos).getTitle(), listViewItemList.get(pos).getDesc()};
        return res;
    }
    public int returnColor(int pos) // 1,2,3,4,5,6,7 형식으로 지정됨d
    {
        return listViewItemList.get(pos).getColor();
    }
}