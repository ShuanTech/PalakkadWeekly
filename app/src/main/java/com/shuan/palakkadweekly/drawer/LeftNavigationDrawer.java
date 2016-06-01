package com.shuan.palakkadweekly.drawer;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.shuan.palakkadweekly.Activities.EditionActivity;
import com.shuan.palakkadweekly.Activities.LoginActivity;
import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.CircleImageView;
import com.shuan.palakkadweekly.Utils.Common;



/**
 * A simple {@link Fragment} subclass.
 */
public class LeftNavigationDrawer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String KEY_USED_DRAWER = "used drawer";
    public static final String PREF = "pref";
    private Switch appLang;
    private RelativeLayout col4;
    private RelativeLayout col5;
    private RelativeLayout col6;
    private RelativeLayout col7;
    private RelativeLayout col8;
    private RelativeLayout col9;
    private Switch editionLang;
    private Common mApp;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public boolean mFromSavedInstance;
    public TextView app,edt,ntf,fet,rec,cpy,abt,set,edition,edtn;
    public boolean mUserDrawer;

    private Switch notify;
    private Button signIn;
    private TextView txt;
    private RelativeLayout usr_layout;
    private RelativeLayout usr_layout1;
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
        signIn = (Button) convertView.findViewById(R.id.sign_in);
        appLang = (Switch) convertView.findViewById(R.id.app_lang);
        //editionLang = (Switch) convertView.findViewById(R.id.edition_lang);
        notify = (Switch) convertView.findViewById(R.id.notify);
        col4 = (RelativeLayout) convertView.findViewById(R.id.col4);
        col5 = (RelativeLayout) convertView.findViewById(R.id.col5);
        col6 = (RelativeLayout) convertView.findViewById(R.id.col6);
        col7 = (RelativeLayout) convertView.findViewById(R.id.col7);
        col8 = (RelativeLayout) convertView.findViewById(R.id.col8);
        col9= (RelativeLayout) convertView.findViewById(R.id.col9);
        usr_layout = (RelativeLayout) convertView.findViewById(R.id.user_layout);
        usr_layout1 = (RelativeLayout) convertView.findViewById(R.id.user_layout1);
        txt = (TextView) convertView.findViewById(R.id.usr_name);
        image = (CircleImageView) convertView.findViewById(R.id.user_img);
        notify.setChecked(true);


        app= (TextView) convertView.findViewById(R.id.app);
        //edt= (TextView) convertView.findViewById(R.id.edt);
        ntf= (TextView) convertView.findViewById(R.id.ntf);
        fet= (TextView) convertView.findViewById(R.id.fet);
        rec= (TextView) convertView.findViewById(R.id.rec);
        cpy= (TextView) convertView.findViewById(R.id.cpy);
        abt= (TextView) convertView.findViewById(R.id.abt);
        set= (TextView) convertView.findViewById(R.id.set);
        edition= (TextView) convertView.findViewById(R.id.edition);
        edtn= (TextView) convertView.findViewById(R.id.edtn);


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
            app.setTextSize(14);
           // edt.setTextSize(14);
            ntf.setTextSize(14);
            fet.setTextSize(14);
            rec.setTextSize(14);
            cpy.setTextSize(14);
            abt.setTextSize(14);
            set.setTextSize(14);
            edition.setTextSize(14);
            edtn.setTextSize(14);
        }else{

        }

        if(mApp.getSharedPreferences().getString(Common.EDTITION,"").equalsIgnoreCase("")){

            edtn.setText(getActivity().getResources().getString(R.string.palakkad));

        }else{
            edtn.setText(mApp.getSharedPreferences().getString(Common.EDTITION,""));
        }

        if (mApp.getSharedPreferences().getString(Common.LANG, "") != null) {
            if (mApp.getSharedPreferences().getString(Common.LANG, "").equalsIgnoreCase("ml")) {
                appLang.setChecked(true);
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                    //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                    appLang.setText("Mal");
                }



            } else {
                appLang.setChecked(false);
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                    //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                    appLang.setText("Eng");
                }


            }
        }

        /*if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "") != null) {
            if (mApp.getSharedPreferences().getString(Common.EDT_LANG, "").equalsIgnoreCase("ml")) {
                editionLang.setChecked(false);

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                    //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

                }else{
                    //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                    editionLang.setText("Mal");
                }



            } else {
                editionLang.setChecked(true);

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                    //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                    editionLang.setText("Eng");
                }


            }
        }*/
        if (mApp.getSharedPreferences().getBoolean(Common.Login, false) == true) {
            usr_layout.setVisibility(View.VISIBLE);
            usr_layout1.setVisibility(View.INVISIBLE);
            txt.setText(mApp.getSharedPreferences().getString(Common.usrName, ""));
            mApp.getPicasso().load("http://www.palakkadweekly.com/adminpanel/profile_pic/" + mApp.getSharedPreferences().getString(Common.usrImg, ""))
                    .placeholder(R.drawable.user).into(image);
        } else {
            usr_layout.setVisibility(View.INVISIBLE);
            usr_layout1.setVisibility(View.VISIBLE);
        }

        signIn.setOnClickListener(this);
        col4.setOnClickListener(this);
        col5.setOnClickListener(this);
        col6.setOnClickListener(this);
        col7.setOnClickListener(this);
        col8.setOnClickListener(this);
        col9.setOnClickListener(this);
        appLang.setOnCheckedChangeListener(this);
        //editionLang.setOnCheckedChangeListener(this);
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

        if (appLang.isChecked()) {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

            }else{
                //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                appLang.setText("Mal");
            }



            mApp.getSharedPreferences().edit().putString(Common.LANG, "ml").commit();
            Intent intent = new Intent(getActivity(), Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                appLang.setText("Eng");
            }

            mApp.getSharedPreferences().edit().putString(Common.LANG, "en").commit();
            Intent intent = new Intent(getActivity(), Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
       /* if (editionLang.isChecked()) {


            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                editionLang.setText("Eng");
            }
            mApp.getSharedPreferences().edit().putString(Common.EDT_LANG, "en").commit();
            getActivity().finish();
            getActivity().startActivity(new Intent(getActivity(), Main.class));




        } else {

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2){
                //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

            }else{
                //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                editionLang.setText("Mal");
            }


            mApp.getSharedPreferences().edit().putString(Common.EDT_LANG, "ml").commit();
            getActivity().finish();
            getActivity().startActivity(new Intent(getActivity(), Main.class));
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.col4:
                break;
            case R.id.col5:
                //shareApp();
                break;
            case R.id.col6:
                //copyApp();
                break;
            case R.id.col7:
                //startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.col8:
                break;
            case R.id.col9:
                startActivity(new Intent(getActivity(),EditionActivity.class));
                break;
        }
        mDrawerLayout.closeDrawers();

    }

    private void copyApp() {
        ClipboardManager clipboard= (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
        ClipData clip=ClipData.newPlainText("Mannarkkad Weekly",getActivity().getString(R.string.app_url));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(),"App Url Copied",Toast.LENGTH_SHORT).show();
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
