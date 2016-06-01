package com.shuan.palakkadweekly.Activities;

import android.content.Context;
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

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.shuan.palakkadweekly.Helper.Helper;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.VideoAdapter;
import com.shuan.palakkadweekly.lists.VideoList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoPlayerActivity extends AppCompatActivity {

    private php call=new php();
    private Helper helper;
    private ListView mListView;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private VideoAdapter videoAdapter;
    private HashMap<String,String> videoData;
    private ArrayList<VideoList> videoList;
    private Context context;
    private Common mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context=getApplicationContext();
        mApp= (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        mListView = (ListView)findViewById(R.id.video_list);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        videoList=new ArrayList<VideoList>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Videos");
        new LoadVideos().execute();
        progressBar.setVisibility(View.VISIBLE);
        /*String url="https://www.youtube.com/embed/nJOztonkIHg".replace("https://www.youtube.com/embed/", "");
        for(int i=0;i<10;i++){
            videoList.add(new VideoList("heading",url));
        }
        videoAdapter=new VideoAdapter(VideoPlayerActivity.this,videoList);
        mListView.setAdapter(videoAdapter);*/
        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);

        if (result != YouTubeInitializationResult.SUCCESS) {
            //If there are any issues we can show an error dialog.
            result.getErrorDialog(this, 0).show();
        }



    }

    public class LoadVideos extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            videoData=new HashMap<String,String>();
            videoData.put("video","video");
            try{
                JSONObject json=null;//parser.makeHttpUrlConnection(call.en_video,videoData);
                /*if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"")!=null){
                    if(mApp.getSharedPreferences().getString(Common.EDT_LANG,"").equalsIgnoreCase("ml")){
                        json=parser.makeHttpUrlConnection(call.ma_video,videoData);
                    }else{
                        json=parser.makeHttpUrlConnection(call.en_video,videoData);
                    }
                }*/
                json=parser.makeHttpUrlConnection(call.ma_video,videoData);
                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("news");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String heading=child.optString("heading");
                        String link=child.optString("vdo").replace("https://www.youtube.com/embed/", "");


                        //String link=child.optString("vdo").replace("https://www.youtube.com/embed/", "");
                        videoList.add(new VideoList(heading,link));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoAdapter=new VideoAdapter(getApplicationContext(),videoList);
                            mListView.setAdapter(videoAdapter);
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt = (TextView) view.findViewById(R.id.url);
                                    //Toast.makeText(getApplicationContext(),"clicked"+txt.getText().toString(),Toast.LENGTH_SHORT).show();
                                    startVideo(txt.getText().toString(), position);
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

    private void startVideo(String s, int position) {
        String s1 = getString(R.string.apiKey);
        if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(getApplicationContext())) {
            //Opens in the StandAlonePlayer but in "Light box" mode
            startActivity(YouTubeStandalonePlayer.createVideoIntent(VideoPlayerActivity.this,
                    s1, s, 0, true, true));
        }
    }
}
