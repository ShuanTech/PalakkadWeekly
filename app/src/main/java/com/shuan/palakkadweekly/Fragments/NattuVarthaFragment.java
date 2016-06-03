package com.shuan.palakkadweekly.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.palakkadweekly.Activities.NewsReadActivity;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.NattuVarthaAdapter;
import com.shuan.palakkadweekly.lists.HomeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NattuVarthaFragment extends Fragment {

    private Context mContext;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private php call=new php();
    public HashMap<String,String> homeData;
    public Common mApp;
    public ArrayList<HomeList> homeList;
    public NattuVarthaAdapter adapter;
    public ListView mListView;
    public ProgressBar progressBar;
    public NattuVarthaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        mApp= (Common) mContext.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_nattu_vartha, container, false);

        mListView = (ListView)view.findViewById(R.id.home_list);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        homeList = new ArrayList<HomeList>();
        progressBar.setVisibility(View.VISIBLE);
        new LoadNattuVartha().execute();

        //Toast.makeText(getActivity(),mApp.getSharedPreferences().getString(Common.EDTITION,""),Toast.LENGTH_SHORT).show();
        return view;
    }

    public class LoadNattuVartha extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            homeData=new HashMap<String,String>();
            if(mApp.getSharedPreferences().getString(Common.EDTITION,"").equalsIgnoreCase("")){
                homeData.put("place",getActivity().getResources().getString(R.string.palakkad));
            }else{
                homeData.put("place",mApp.getSharedPreferences().getString(Common.EDTITION,""));
            }

            JSONObject json=null;
            String img=null;
            try{
                /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"")!=null){
                    if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                        json=parser.makeHttpUrlConnection(call.nattuvartha,homeData);
                    }else{
                        json=parser.makeHttpUrlConnection(call.en_news,homeData);
                    }
                }*/
                json=parser.makeHttpUrlConnection(call.nattuvartha,homeData);
                int succ=json.getInt("success");
                if(succ==0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("news");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String id=child.optString("id");
                        String heading=child.optString("heading");
                        /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                            img="http://www.mannarkkadweekly.com/adminpanel/news_photos/"+child.optString("image");
                        }else {
                            img="http://www.mannarkkadweekly.com/en/adminpanel/news_photos/"+child.optString("image");
                        }*/
                        img="http://www.palakkadweekly.com/adminpanel/news_photos/"+child.optString("image");
                        //homeList.add(new HomeList(id,heading,img));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new NattuVarthaAdapter(getActivity(),homeList);
                            mListView.setAdapter(adapter);
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt= (TextView) view.findViewById(R.id.nattu_vartha_id);
                                    Intent in=new Intent(getActivity(), NewsReadActivity.class);
                                    in.putExtra("news_id",txt.getText().toString());
                                    in.putExtra("type","news");
                                    startActivity(in);
                                }
                            });
                        }
                    });
                }



            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
