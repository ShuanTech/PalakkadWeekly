package com.shuan.palakkadweekly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.EdtList;

import java.util.ArrayList;

/**
 * Created by shuan on 2/23/2016.
 */
public class EdtnAdapter extends BaseAdapter{

    private ArrayList<EdtList> edtList;
    private Context context;
    private LayoutInflater inflater;


    public EdtnAdapter(Context context, ArrayList<EdtList> edtList) {
        this.context = context;
        this.edtList = edtList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return edtList.size();
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

        convertView=inflater.inflate(R.layout.edt_item,null);
        EdtList currItem=edtList.get(position);
        TextView txt= (TextView) convertView.findViewById(R.id.edt_text);
        txt.setText(currItem.getEdtn());
        return convertView;
    }
}
