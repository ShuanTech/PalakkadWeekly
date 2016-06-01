package com.shuan.palakkadweekly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.BusCodeList;

import java.util.ArrayList;


public class BusCodeAdapter extends ArrayAdapter<BusCodeList> implements Filterable{

    private Context context;
    private int resource,textViewResourceId;
    private ArrayList<BusCodeList> busCodeList,temp,sugg;
    private LayoutInflater inflater;
    private ArrayFilter mFilter;

    public BusCodeAdapter(Context context, int resource, int textViewResourceId,ArrayList<BusCodeList> busCodeList) {
        super(context, resource, textViewResourceId);
        this.context=context;
        this.resource=resource;
        this.textViewResourceId=textViewResourceId;
        this.busCodeList=busCodeList;
        this.temp=new ArrayList<BusCodeList>(busCodeList);
        this.sugg=new ArrayList<BusCodeList>();
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return busCodeList.size();
    }

    @Override
    public BusCodeList getItem(int position) {
        return busCodeList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(resource,null);
        BusCodeList curr=busCodeList.get(position);

        TextView id= (TextView) convertView.findViewById(R.id.busId);
        TextView busName= (TextView) convertView.findViewById(R.id.bus_name);
        TextView bus= (TextView) convertView.findViewById(R.id.bus);

        busName.setText(curr.getName() + " - " +curr.getBusCode());
        bus.setText(curr.getName());
        id.setText(curr.getId());
        return  convertView;
    }



    @Override
    public Filter getFilter() {
        //return nameFilter;
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }




    private class ArrayFilter extends Filter {
        private  BusCodeList lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (temp == null) {
                synchronized (lock) {
                    temp = new ArrayList<BusCodeList>(busCodeList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<BusCodeList> list = new ArrayList<BusCodeList>(
                            temp);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<BusCodeList> values = temp;
                int count = values.size();

                ArrayList<BusCodeList> newValues = new ArrayList<BusCodeList>(count);

                for (int i = 0; i < count; i++) {
                    BusCodeList item = values.get(i);
                    if (item.getName().toLowerCase().contains(prefix.toString().toLowerCase())) {
                        newValues.add(item);
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                busCodeList = (ArrayList<BusCodeList>) results.values;
            } else {
                busCodeList = new ArrayList<BusCodeList>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }


}
