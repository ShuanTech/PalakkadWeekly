package com.shuan.palakkadweekly.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.RightList;

import java.util.ArrayList;

public class RightListAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<RightList> rightList;
    public LayoutInflater inflater;

    public RightListAdapter(Context context, ArrayList<RightList> rightList) {
        this.context = context;
        this.rightList = rightList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rightList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.menu_item,null);

        TextView menuName= (TextView) convertView.findViewById(R.id.menu_name);

        RightList currMenu=rightList.get(position);

        menuName.setText(currMenu.getMenuName());

        return convertView;
    }
}
