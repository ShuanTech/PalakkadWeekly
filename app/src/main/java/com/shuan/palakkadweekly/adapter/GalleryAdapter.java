package com.shuan.palakkadweekly.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.lists.GalleryList;

import java.util.ArrayList;

/**
 * Created by shuan on 1/11/2016.
 */
public class GalleryAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<GalleryList> galleryList;
    private LayoutInflater inflater;
    private Common mApp;
    private DisplayImageOptions options;

    public GalleryAdapter(Context context, ArrayList<GalleryList> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
        inflater=LayoutInflater.from(context);
        mApp= (Common) context.getApplicationContext();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.load1)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return galleryList.size();
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
        convertView = inflater.inflate(R.layout.grid_item, null);
        TextView txt = (TextView)convertView.findViewById(R.id.grid_item_title);
        ImageView imageview = (ImageView)convertView.findViewById(R.id.grid_item_image);
        TextView textview = (TextView)convertView.findViewById(R.id.path);
        GalleryList curr = galleryList.get(position);
        txt.setText(curr.getId());
        textview.setText(curr.getImg());
        //mApp.getPicasso().load(curr.getImg()).placeholder(R.drawable.no_image).into(imageview);
        ImageLoader.getInstance().displayImage(curr.getImg(), imageview, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // holder.progressBar.setProgress(0);
                //holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                //holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                //holder.progressBar.setVisibility(View.GONE);
            }

        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
        return convertView;
    }
}
