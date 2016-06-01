package com.shuan.palakkadweekly.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.palakkadweekly.Helper.Helper;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class NewsReadActivity extends AppCompatActivity {

    private php call = new php();
    TextView content;
    TextView date;
    String getContent;
    String getDate;
    String getHeading;
    String getImg;
    String getPlace;
    TextView heading;
    private Helper helper;
    ImageView img;
    private Common mApp;
    private Context mContext;
    private HashMap<String,String> newsData;
    private String news_id;
    private ProgressDialog pDialog;
    private HttpUrlConnectionParser parser = new HttpUrlConnectionParser();
    TextView place;
    public ProgressBar progressBar;
    private Toolbar toolbar;
    private String type,head;
    private DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext = getApplicationContext();
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_read);
        Intent in=getIntent();
        news_id = in.getStringExtra("news_id");
        type = in.getStringExtra("type");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        date = (TextView)findViewById(R.id.date);
        place = (TextView)findViewById(R.id.place);
        heading = (TextView)findViewById(R.id.heading);
        content = (TextView)findViewById(R.id.content);
        img = (ImageView)findViewById(R.id.big_content_img);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, mApp.getStatusBarHeight(mContext), 0, 0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        Intent i=getIntent();
        if(i.getStringExtra("news").equalsIgnoreCase("")){
            new LoadNewsDetails().execute();
        }else {
            //Toast.makeText(getApplicationContext(),i.getStringExtra("news"),Toast.LENGTH_SHORT).show();
            head=i.getStringExtra("news");
            new LoadGcmNews().execute();
        }

        progressBar.setVisibility(View.VISIBLE);
        //Toast.makeText(getApplicationContext(),news_id+type,Toast.LENGTH_SHORT).show();

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }


    public class LoadGcmNews extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            newsData=new HashMap<String,String>();
            newsData.put("head",head);
            try {
                JSONObject json = parser.makeHttpUrlConnection(php.gcm_news, newsData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    JSONArray jsonArray=json.getJSONArray("newsDetails");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        getDate = child.optString("date");
                        getPlace = child.optString("place");
                        getHeading = child.optString("heading");
                        getContent = child.optString("description");
                        getImg = "http://www.palakkadweekly.com/adminpanel/news_photos/" + child.optString("image");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            date.setText(getDate);
                            place.setText(getPlace);
                            heading.setText(getHeading);
                            content.setText(getContent);
                            ImageLoader.getInstance().displayImage(getImg, img, options, new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String imageUri, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                    String message = null;
                                    switch (failReason.getType()) {
                                        case IO_ERROR:
                                            message = "Input/Output error";
                                            break;
                                        case DECODING_ERROR:
                                            message = "Image can't be decoded";
                                            break;
                                        case NETWORK_DENIED:
                                            message = "Downloads are denied";
                                            break;
                                        case OUT_OF_MEMORY:
                                            message = "Out Of Memory error";
                                            break;
                                        case UNKNOWN:
                                            message = "Unknown error";
                                            break;
                                    }
                                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                                    //spinner.setVisibility(View.GONE);
                                }

                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    //spinner.setVisibility(View.GONE);
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


    public class LoadNewsDetails extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            newsData=new HashMap<String,String>();
            newsData.put("news_id",news_id);
            newsData.put("type",type);
            JSONObject json=null;
            try{
                /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"")!=null){
                    if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                        json=parser.makeHttpUrlConnection(call.ma_news_details,newsData);
                    }else{
                        json=parser.makeHttpUrlConnection(call.en_news_details,newsData);
                    }
                }*/
                json=parser.makeHttpUrlConnection(call.ma_news_details,newsData);
                int succ=json.getInt("success");

                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("newsDetails");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        if(type.equalsIgnoreCase("Articles")){
                            getHeading=child.optString("heading");
                            getContent=child.optString("description");
                           /* if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                                getImg = "http://www.mannarkkadweekly.com/adminpanel/articles/" + child.optString("image");
                            }else{
                                getImg="http://www.mannarkkadweekly.com/en/adminpanel/articles/"+child.optString("image");
                            }*/
                            getImg = "http://www.palakkadweekly.com/adminpanel/articles/" + child.optString("image");
                        }else if(type.equalsIgnoreCase("Obituaries")){
                            getDate = child.optString("date");
                            getPlace = child.optString("place");
                            getHeading = child.optString("name");
                            getContent = child.optString("description");
                            /*if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "").equalsIgnoreCase("ml")) {

                                getImg = "http://www.mannarkkadweekly.com/adminpanel/obituries/" + child.optString("photo");

                            } else {

                                getImg = "http://www.mannarkkadweekly.com/en/adminpanel/obituries/" + child.optString("photo");

                            }*/

                            getImg = "http://www.palakkadweekly.com/adminpanel/obituries/" + child.optString("photo");
                        }else {
                            getDate = child.optString("date");
                            getPlace = child.optString("place");
                            getHeading = child.optString("heading");
                            getContent = child.optString("description");
                            /*if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "").equalsIgnoreCase("ml")) {

                                    getImg = "http://www.mannarkkadweekly.com/adminpanel/news_photos/" + child.optString("image");

                            } else {

                                    getImg = "http://www.mannarkkadweekly.com/en/adminpanel/news_photos/" + child.optString("image");

                            }*/
                            getImg = "http://www.palakkadweekly.com/adminpanel/news_photos/" + child.optString("image");
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(type.equalsIgnoreCase("Articles")){
                                heading.setText(getHeading);
                                content.setText(getContent);
                                //mApp.getPicasso().load(getImg).placeholder(R.drawable.no_image).into(img);
                            }else {
                                date.setText(getDate);
                                place.setText(getPlace);
                                heading.setText(getHeading);
                                content.setText(getContent);
                                //mApp.getPicasso().load(getImg).placeholder(R.drawable.no_image).into(img);
                            }

                            ImageLoader.getInstance().displayImage(getImg,img,options,new SimpleImageLoadingListener(){
                                @Override
                                public void onLoadingStarted(String imageUri, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                    String message = null;
                                    switch (failReason.getType()) {
                                        case IO_ERROR:
                                            message = "Input/Output error";
                                            break;
                                        case DECODING_ERROR:
                                            message = "Image can't be decoded";
                                            break;
                                        case NETWORK_DENIED:
                                            message = "Downloads are denied";
                                            break;
                                        case OUT_OF_MEMORY:
                                            message = "Out Of Memory error";
                                            break;
                                        case UNKNOWN:
                                            message = "Unknown error";
                                            break;
                                    }
                                    //Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                                    //spinner.setVisibility(View.GONE);
                                }

                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    //spinner.setVisibility(View.GONE);
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
