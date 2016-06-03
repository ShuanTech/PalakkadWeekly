package com.shuan.palakkadweekly.drawer;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.palakkadweekly.Activities.EditionActivity;
import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.CircleImageView;
import com.shuan.palakkadweekly.Utils.Common;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftNavigationDrawer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String KEY_USED_DRAWER = "used drawer";
    public static final String PREF = "pref";
    private Switch appLang;
    private RelativeLayout col5;
    private RelativeLayout col6;
    private RelativeLayout col9;
    private Common mApp;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public boolean mFromSavedInstance;
    public TextView app, ntf, fet, rec, cpy, abt, set, edition, edtn;
    public boolean mUserDrawer;
    private DisplayImageOptions options;
    public String notifyUpdate;

    private Switch notify;
    private Button upload;
    private TextView txt;
    private CircleImageView image;

    public LeftNavigationDrawer() {
        // Required empty public constructor
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        mUserDrawer = Boolean.valueOf(readFromPreference(getActivity(), "used drawer", "false")).booleanValue();
        if (bundle != null) {
            mFromSavedInstance = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.fragment_left_navigation_drawer, container, false);

        appLang = (Switch) convertView.findViewById(R.id.app_lang);
        notify = (Switch) convertView.findViewById(R.id.notify);
        col5 = (RelativeLayout) convertView.findViewById(R.id.col5);
        col6 = (RelativeLayout) convertView.findViewById(R.id.col6);
        col9 = (RelativeLayout) convertView.findViewById(R.id.col9);
        txt = (TextView) convertView.findViewById(R.id.usr_name);
        image = (CircleImageView) convertView.findViewById(R.id.user_img);
        notify.setChecked(true);


        app = (TextView) convertView.findViewById(R.id.app);
        ntf = (TextView) convertView.findViewById(R.id.ntf);
        rec = (TextView) convertView.findViewById(R.id.rec);
        cpy = (TextView) convertView.findViewById(R.id.cpy);
        edition = (TextView) convertView.findViewById(R.id.edition);
        edtn = (TextView) convertView.findViewById(R.id.edtn);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            app.setTextSize(14);
            ntf.setTextSize(14);
            rec.setTextSize(14);
            cpy.setTextSize(14);
            edition.setTextSize(14);
            edtn.setTextSize(14);
        }

        if (mApp.getSharedPreferences().getString(Common.EDTITION, "").equalsIgnoreCase("")) {
            edtn.setText(getActivity().getResources().getString(R.string.palakkad));
        } else {
            edtn.setText(mApp.getSharedPreferences().getString(Common.EDTITION, ""));
        }

        if (mApp.getSharedPreferences().getString(Common.LANG, "") != null) {
            if (mApp.getSharedPreferences().getString(Common.LANG, "").equalsIgnoreCase("ml")) {
                appLang.setChecked(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                } else {
                    appLang.setText("Mal");
                }

            } else {
                appLang.setChecked(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                } else {
                    appLang.setText("Eng");
                }
            }
        }

        if (mApp.getSharedPreferences().getString(Common.NOTIFY, "") != null) {
            if (mApp.getSharedPreferences().getString(Common.NOTIFY, "").equalsIgnoreCase("Y")) {
                notify.setChecked(true);
            } else {
                notify.setChecked(false);
            }
        }


        if (mApp.getSharedPreferences().getBoolean(Common.Login, false) == true) {

            txt.setText(mApp.getSharedPreferences().getString(Common.usrName, ""));
            ImageLoader.getInstance().displayImage(mApp.getSharedPreferences().getString(Common.usrImg, ""), image, options, new SimpleImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    super.onLoadingCancelled(imageUri, view);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String s, View view, int i, int i1) {

                }
            });
        }

        col5.setOnClickListener(this);
        col6.setOnClickListener(this);
        col9.setOnClickListener(this);
        appLang.setOnCheckedChangeListener(this);
        notify.setOnCheckedChangeListener(this);
        return convertView;
    }


    public void setUp(DrawerLayout drawerlayout, Toolbar toolbar) {
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mUserDrawer = true;
                savedToPreference(getActivity(), KEY_USED_DRAWER, mUserDrawer + "");
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.app_lang:
                if (appLang.isChecked()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    } else {
                        appLang.setText("Mal");
                    }

                    mApp.getSharedPreferences().edit().putString(Common.LANG, "ml").commit();
                    Intent intent = new Intent(getActivity(), Main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    } else {
                        appLang.setText("Eng");
                    }

                    mApp.getSharedPreferences().edit().putString(Common.LANG, "en").commit();
                    Intent intent = new Intent(getActivity(), Main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.notify:
                if (notify.isChecked()) {
                    //Toast.makeText(getActivity(),"checked",Toast.LENGTH_SHORT).show();
                    notifyUpdate = "Y";
                    new notifyUpdate().execute();
                    mApp.getSharedPreferences().edit().putString(Common.NOTIFY, "Y").commit();
                } else {
                    notifyUpdate = "N";
                    new notifyUpdate().execute();
                    mApp.getSharedPreferences().edit().putString(Common.NOTIFY, "N").commit();
                    //Toast.makeText(getActivity(),"not checked",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.col5:
                shareApp();
                break;
            case R.id.col6:
                copyApp();
                break;
            case R.id.col9:
                startActivity(new Intent(getActivity(), EditionActivity.class));
                break;
        }
        mDrawerLayout.closeDrawers();

    }

    public class notifyUpdate extends AsyncTask<String, String, String> {
        HashMap<String, String> notifyData = new HashMap<String, String>();

        @Override
        protected String doInBackground(String... params) {


            notifyData.put("usr", mApp.getSharedPreferences().getString(Common.GMAILID, ""));
            notifyData.put("notify", notifyUpdate);

            try {
                JSONObject json = HttpUrlConnectionParser.makeHttpUrlConnection(php.notifyUpdate, notifyData);
                int succ = json.getInt("success");

                if (succ == 0) {
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (notifyUpdate.equalsIgnoreCase("N")) {
                                Toast.makeText(getActivity(), "Notification turned OFF Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Notification turned ON Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            } catch (Exception e) {
            }


            return null;
        }
    }

    private void copyApp() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Palakkad Weekly", getActivity().getString(R.string.app_url));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "App Url Copied", Toast.LENGTH_SHORT).show();
    }

    private void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("android.intent.extra.SUBJECT", getActivity().getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", getActivity().getString(R.string.app_url));
        getActivity().startActivity(Intent.createChooser(intent, "Share the App"));
    }

    public static void savedToPreference(Context context, String prefName, String perfValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(prefName, perfValue);
        edit.apply();
    }

    public static String readFromPreference(Context context, String prefName, String prefValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF, context.MODE_PRIVATE);
        return mSharedPreferences.getString(prefName, prefValue);
    }
}
