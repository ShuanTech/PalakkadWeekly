package com.shuan.palakkadweekly.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.shuan.palakkadweekly.lists.DirDetailsList;

import java.util.ArrayList;

/**
 * Created by shuan on 1/11/2016.
 */
public class DirDetailsAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<DirDetailsList> list;
    private LayoutInflater inflater;
    private Common mApp;
    private DisplayImageOptions options;
    public DirDetailsAdapter(Context context, ArrayList<DirDetailsList> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        mApp= (Common) context.getApplicationContext();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.load2)
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
        convertView=inflater.inflate(R.layout.dir_details_item,null);

        final DirDetailsList curr=list.get(position);
        TextView name= (TextView) convertView.findViewById(R.id.name);
        TextView addr= (TextView) convertView.findViewById(R.id.addr);
        TextView phone= (TextView) convertView.findViewById(R.id.phone);
        final TextView weburl= (TextView) convertView.findViewById(R.id.weburl);
        ImageView img= (ImageView) convertView.findViewById(R.id.small_content_img);

            name.setText(curr.getName());
            addr.setText(curr.getAddress());
            phone.setText(curr.getPhone());
            weburl.setText(curr.getWeburl());
            weburl.setTextColor(Color.BLUE);
            /*weburl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,weburl.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(weburl.getText().toString()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });*/

            //mApp.getPicasso().load(curr.getCover_photo()).placeholder(R.drawable.no_image).into(img);

        ImageLoader.getInstance().displayImage(curr.getCover_photo(), img, options, new SimpleImageLoadingListener() {
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
