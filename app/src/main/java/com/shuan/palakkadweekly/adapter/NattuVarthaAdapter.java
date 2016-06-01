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
import com.shuan.palakkadweekly.lists.HomeList;

import java.util.ArrayList;

/**
 * Created by shuan on 1/9/2016.
 */
public class NattuVarthaAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<HomeList> homeList;
    public LayoutInflater inflater;
    public Common mApp;
    private DisplayImageOptions options;

    public NattuVarthaAdapter(Context context, ArrayList<HomeList> homeList) {
        this.context = context;
        this.homeList = homeList;
        mApp= (Common) context.getApplicationContext();
        inflater=LayoutInflater.from(context);
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
        return homeList.size();
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
        convertView=inflater.inflate(R.layout.home_list_item,null);
        TextView id= (TextView) convertView.findViewById(R.id.nattu_vartha_id);
        TextView heading = (TextView)convertView.findViewById(R.id.title);
        ImageView imageview = (ImageView)convertView.findViewById(R.id.small_content_img);
        HomeList homelist = (HomeList)homeList.get(position);
        if (mApp.getSharedPreferences().getString("edt_lang", "").equalsIgnoreCase("ml"))
        {
            heading.setTextSize(12);
            heading.setText(homelist.getHeading());
        } else
        {
            heading.setTextSize(14);
            heading.setText(homelist.getHeading());
        }
        id.setText(homelist.getId());


        ImageLoader.getInstance().displayImage(homelist.getImg(), imageview, options, new SimpleImageLoadingListener() {
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

        /*mApp.getPicasso().load(homelist.getImg())
                .placeholder(R.drawable.no_image)
                .into(imageview);*/

        return convertView;
    }
}
