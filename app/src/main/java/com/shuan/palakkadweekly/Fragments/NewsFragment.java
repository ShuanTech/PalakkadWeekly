package com.shuan.palakkadweekly.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shuan.palakkadweekly.Activities.NewsReadActivity;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.adapter.NewsAdapter;
import com.shuan.palakkadweekly.lists.NewsList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView newsList;
    private Context mContext;
    private ArrayList<NewsList> list;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=getActivity();
        list=new ArrayList<NewsList>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_news, container, false);

        newsList= (ListView) rootView.findViewById(R.id.news_list);



        for(int i=0;i<20;i++){
            list.add(new NewsList("Heading - "+i));
        }
        adapter=new NewsAdapter(mContext,list);
        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(mContext,NewsReadActivity.class));

    }
}
