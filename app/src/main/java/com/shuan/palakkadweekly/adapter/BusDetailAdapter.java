package com.shuan.palakkadweekly.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.BusDetailsList;

import java.util.ArrayList;

public class BusDetailAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<BusDetailsList> list;
    public LayoutInflater inflater;

    public BusDetailAdapter(Context context, ArrayList<BusDetailsList> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
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
        convertView =inflater.inflate(R.layout.bus_details_item,null);
        BusDetailsList curr=list.get(position);

        //TextView busName= (TextView) convertView.findViewById(R.id.bus_name);
        TextView from= (TextView) convertView.findViewById(R.id.from);
        //TextView fromTime= (TextView) convertView.findViewById(R.id.from_time);
        TextView to= (TextView) convertView.findViewById(R.id.to);
       // TextView toTime= (TextView) convertView.findViewById(R.id.to_time);
        TextView travel_time= (TextView) convertView.findViewById(R.id.travel_time);
        //TextView source= (TextView) convertView.findViewById(R.id.source);
        //TextView destination= (TextView) convertView.findViewById(R.id.destination);

       // busName.setText(curr.getBusName());
        from.setText(curr.getFrm());
        //fromTime.setText(curr.getFrmTime());
        to.setText(curr.getTo());
        //toTime.setText(curr.getToTime());
        travel_time.setText(curr.getTrvlTime());
        //source.setText(curr.getSrc());
        //destination.setText(curr.getDest());


        return convertView;
    }
}
