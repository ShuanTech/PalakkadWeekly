package com.shuan.palakkadweekly.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.palakkadweekly.Helper.Helper;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.RightDrawerAdapter;
import com.shuan.palakkadweekly.lists.HomeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RightDrawerActiivty extends AppCompatActivity {

    public php call=new php();
    String heading;
    public Helper helper;
    String image;
    public Common mApp;
    public Context mContext;
    String menu,id;
    public HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private ProgressBar progressBar;
    public RightDrawerAdapter rightDraweAdapter;
    public HashMap<String,String> rightDrawerData;
    public ArrayList<HomeList> rightDrawerList;
    public ListView rightDrawerListView;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mApp = (Common)mContext.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_drawer_actiivty);
        menu = getIntent().getStringExtra("menu");
        id=getIntent().getStringExtra("id");
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(menu);
        rightDrawerListView = (ListView)findViewById(R.id.right_drawer_list);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        rightDrawerList = new ArrayList<HomeList>();
        //Toast.makeText(getApplicationContext(),menu,Toast.LENGTH_SHORT).show();
        new LoadNews().execute();
        progressBar.setVisibility(View.VISIBLE);
    }

    public class LoadNews extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            rightDrawerData=new HashMap<String,String>();
            if(id.equalsIgnoreCase("d0")){
                rightDrawerData.put("id","nattuvartha");
            }else if(id.equalsIgnoreCase("d1")){
                rightDrawerData.put("id","kerala");
            }else if(id.equalsIgnoreCase("d2")){
                rightDrawerData.put("id","india");
            }else if(id.equalsIgnoreCase("d3")){
                rightDrawerData.put("id","world");
            }else{
                rightDrawerData.put("id",id);
            }

            JSONObject json=null;
            String img=null;
            try{
               /* if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"")!=null){
                    if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                        json=parser.makeHttpUrlConnection(call.ma_news,rightDrawerData);
                    }else{
                        json=parser.makeHttpUrlConnection(call.en_news,rightDrawerData);
                    }
                }*/
                json=parser.makeHttpUrlConnection(call.ma_news,rightDrawerData);
                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("news");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);

                        if(menu.equalsIgnoreCase("Obituaries")){
                            String id=child.optString("id");
                            String heading=child.optString("name");
                            /*if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "").equalsIgnoreCase("ml")) {

                                    img = "http://www.mannarkkadweekly.com/adminpanel/obituries/" + child.optString("photo");

                            } else {

                                    img = "http://www.mannarkkadweekly.com/en/adminpanel/obituries/" + child.optString("photo");

                            }*/
                            img = "http://www.palakkadweekly.com/adminpanel/obituries/" + child.optString("photo");
                            //rightDrawerList.add(new HomeList(id,heading,img));
                        }else {

                            String id = child.optString("id");
                            String heading = child.optString("heading");
                            /*if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "").equalsIgnoreCase("ml")) {
                                if (menu.equalsIgnoreCase("Articles")) {
                                    img = "http://www.mannarkkadweekly.com/adminpanel/articles/" + child.optString("image");
                                } else {
                                    img = "http://www.mannarkkadweekly.com/adminpanel/news_photos/" + child.optString("image");
                                }
                            } else {
                                if (menu.equalsIgnoreCase("Articles")) {
                                    img = "http://www.mannarkkadweekly.com/en/adminpanel/articles/" + child.optString("image");
                                } else {
                                    img = "http://www.mannarkkadweekly.com/en/adminpanel/news_photos/" + child.optString("image");
                                }
                            }*/
                            if (menu.equalsIgnoreCase("Articles")) {
                                img = "http://www.palakkadweekly.com/adminpanel/articles/" + child.optString("image");
                            } else {
                                img = "http://www.palakkadweekly.com/adminpanel/news_photos/" + child.optString("image");
                            }
                           // rightDrawerList.add(new HomeList(id,heading,img));
                        }

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rightDraweAdapter = new RightDrawerAdapter(getApplicationContext(), rightDrawerList);
                            rightDrawerListView.setAdapter(rightDraweAdapter);
                            rightDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt = (TextView) view.findViewById(R.id.nattu_vartha_id);

                                    if(menu.equalsIgnoreCase("Articles")){
                                        Intent in = new Intent(getApplicationContext(), NewsReadActivity.class);
                                        in.putExtra("news_id", txt.getText().toString());
                                        in.putExtra("type", "Articles");
                                        startActivity(in);
                                    }else if(menu.equalsIgnoreCase("Obituaries")){
                                        Intent in = new Intent(getApplicationContext(), NewsReadActivity.class);
                                        in.putExtra("news_id", txt.getText().toString());
                                        in.putExtra("type", "Obituaries");
                                        startActivity(in);
                                    }else {
                                        Intent in = new Intent(getApplicationContext(), NewsReadActivity.class);
                                        in.putExtra("news_id", txt.getText().toString());
                                        in.putExtra("type", "news");
                                        startActivity(in);
                                    }
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
