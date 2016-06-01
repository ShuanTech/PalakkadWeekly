package com.shuan.palakkadweekly.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.NewsList;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<NewsList> list;
    private LayoutInflater inflater;

    public NewsAdapter(Context mContext, ArrayList<NewsList> list) {
        this.mContext = mContext;
        this.list = list;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
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

        convertView=inflater.inflate(R.layout.news_list_item,parent,false);

        NewsList currItem=list.get(position);
        TextView txt= (TextView) convertView.findViewById(R.id.title);

        txt.setText(currItem.getHeading());
        return convertView;
    }
}
