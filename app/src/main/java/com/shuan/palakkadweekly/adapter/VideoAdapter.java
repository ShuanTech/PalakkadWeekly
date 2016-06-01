package com.shuan.palakkadweekly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.lists.VideoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shuan on 1/11/2016.
 */
public class VideoAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener {

    private Context mContext;
    private Map<View, YouTubeThumbnailLoader> mLoaders;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<VideoList> list;

    public VideoAdapter(Context mContext, ArrayList<VideoList> list) {
        this.mContext = mContext;
        this.list = list;
        inflater=LayoutInflater.from(mContext);
        mLoaders = new HashMap<View,YouTubeThumbnailLoader>();
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
        VideoList curr = list.get(position);
        VideoHolder holder;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.video_gallery_item,null);
            holder = new VideoHolder();
            holder.title = (TextView)convertView.findViewById(R.id.textView_title);
            holder.url = (TextView)convertView.findViewById(R.id.url);
            holder.title.setText(curr.getHeading());
            holder.url.setText(curr.getLink());
            holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
            holder.thumb.setTag(curr.getLink());
            holder.thumb.initialize(mContext.getString(R.string.apiKey), this);

            convertView.setTag(holder);
        }else {
            holder = (VideoHolder) convertView.getTag();
            final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);
            if (curr != null) {
                //Set the title
                holder.title.setText(curr.getHeading());
                holder.url.setText(curr.getLink());

                //Setting the video id can take a while to actually change the image
                //in the meantime the old image is shown.
                //Removing the image will cause the background color to show instead, not ideal
                //but preferable to flickering images.
                holder.thumb.setImageBitmap(null);

                if (loader == null) {
                    //Loader is currently initialising
                    holder.thumb.setTag(curr.getLink());
                } else {
                    //The loader is already initialised
                    //Note that it's possible to get a DeadObjectException here
                    try {
                        loader.setVideo(curr.getLink());
                    } catch (IllegalStateException exception) {
                        //If the Loader has been released then remove it from the map and re-init
                        mLoaders.remove(holder.thumb);
                        holder.thumb.initialize(mContext.getString(R.string.apiKey), this);

                    }
                }

            }

            }



        return convertView;
    }




    @Override
    public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
        mLoaders.put(view, loader);
        loader.setVideo((String) view.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        final String errorMessage = youTubeInitializationResult.toString();
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    static class VideoHolder {
        YouTubeThumbnailView thumb;
        TextView title,url;
    }
}
