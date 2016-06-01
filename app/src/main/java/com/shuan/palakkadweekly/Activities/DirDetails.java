package com.shuan.palakkadweekly.Activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.DirDetailsAdapter;
import com.shuan.palakkadweekly.lists.DirDetailsList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DirDetails extends AppCompatActivity {

    private Common mApp;
    private Context context;
    private Toolbar toolbar;
    private ArrayList<DirDetailsList> list;
    private DirDetailsAdapter adapter;
    private ListView mListview;
    private ProgressBar progressBar;
    private HashMap<String,String> dirData;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private php call=new php();
    String id,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=getApplicationContext();
        mApp= (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_details);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mListview= (ListView) findViewById(R.id.home_list);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        list=new ArrayList<DirDetailsList>();
        new LoadDirDetails().execute();
        progressBar.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(name.toUpperCase());

        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
    }
    public class LoadDirDetails extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            dirData=new HashMap<String,String>();
            dirData.put("id",id);
            try{
                JSONObject json=parser.makeHttpUrlConnection(call.dir_details,dirData);

                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("news");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String id=child.optString("id");
                        String name=child.optString("name");
                        String addr=child.optString("address");
                        String phone=child.optString("phone");
                        String weburl=child.optString("weburl");
                        String img="http://www.palakkadweekly.com/adminpanel/directory_item_image/"+child.optString("cover_photo");
                        list.add(new DirDetailsList(id,name,addr,phone,weburl,img));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new DirDetailsAdapter(getApplicationContext(),list);
                            mListview.setAdapter(adapter);
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
