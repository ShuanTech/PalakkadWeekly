package com.shuan.palakkadweekly.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.palakkadweekly.Helper.Helper;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.GalleryAdapter;
import com.shuan.palakkadweekly.lists.GalleryList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryActivity extends AppCompatActivity {
    private php call=new php();
    private GalleryAdapter galleryAdapter;
    private HashMap<String,String> galleryData;
    private ArrayList<GalleryList> galleryList;
    private GridView gridView;
    private Helper helper;
    private String id;
    private String img;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private Common mApp;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=getApplicationContext();
        mApp= (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        gridView = (GridView)findViewById(R.id.gridView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        setTitle("My Snaps");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        galleryList = new ArrayList<GalleryList>();
        new LoadGallery().execute();
        progressBar.setVisibility(View.VISIBLE);
    }

    public class LoadGallery extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            galleryData=new HashMap<String,String>();
            galleryData.put("gallery","gallery");
            try{
                JSONObject json=null;

                /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"")!=null){
                    if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                        json=parser.makeHttpUrlConnection(call.ma_gallery,galleryData);
                    }else{
                        json=parser.makeHttpUrlConnection(call.en_gallery,galleryData);
                    }
                }*/
                json=parser.makeHttpUrlConnection(call.ma_gallery,galleryData);
                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"No Data Found!...",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("news");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        id=child.optString("id");
                        /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                            img="http://www.mannarkkadweekly.com/adminpanel/news_photos/"+child.optString("img");
                        }else {
                            img="http://www.mannarkkadweekly.com/en/adminpanel/news_photos/"+child.optString("img");
                        }*/
                        img="http://www.palakkadweekly.com/adminpanel/news_photos/"+child.optString("img");
                        galleryList.add(new GalleryList(id,img));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            galleryAdapter=new GalleryAdapter(GalleryActivity.this,galleryList);
                            gridView.setAdapter(galleryAdapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt= (TextView) view.findViewById(R.id.path);
                                    ImageView img= (ImageView) view.findViewById(R.id.grid_item_image);
                                    Intent in=new Intent(getApplicationContext(),FullScreenImageActivity.class);
                                    in.putExtra("path",txt.getText().toString());
                                    startActivity(in);
                                    //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();

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
