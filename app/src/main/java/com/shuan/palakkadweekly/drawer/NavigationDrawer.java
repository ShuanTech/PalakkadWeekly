package com.shuan.palakkadweekly.drawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shuan.palakkadweekly.R;

import java.util.ArrayList;

public class NavigationDrawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> list_item;
    public static final String PREF="pref";
    public static final String KEY_USED_DRAWER="used drawer";
    public boolean mUserDrawer;
    public boolean mFromSavedInstance;
    public View containerView;
    public ListView list;



    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=getActivity();
        inflater=LayoutInflater.from(mContext);
        list_item=new ArrayList<String>();
        mUserDrawer=Boolean.valueOf(readFromPreference(getActivity(),KEY_USED_DRAWER,"false"));
        if (savedInstanceState!=null){
            mFromSavedInstance=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        list= (ListView) rootView.findViewById(R.id.list_view);


        //for(int i=0;i<5;i++){
            //list_item.add("Menu - "+i);
        //}
        String[] menu=mContext.getResources().getStringArray(R.array.draweMenu);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,menu);
        list.setAdapter(ad);

        return rootView;
    }

    public void setUp(int fragmentId,DrawerLayout drawerLayout, Toolbar toolbar) {

        containerView=getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

             @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(!mUserDrawer){
                    mUserDrawer=true;
                    savedToPreference(getActivity(),KEY_USED_DRAWER,mUserDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getActivity(),"Drawer Open",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getActivity(),"Drawer Close",Toast.LENGTH_SHORT).show();
            }
        };

        /*if(!mUserDrawer && !mFromSavedInstance){
            mDrawerLayout.openDrawer(containerView);
        }*/

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
             mDrawerToggle.syncState();
            }
        });
    }


    public static void savedToPreference(Context context,String prefName,String perfValue){
        SharedPreferences mSharedPreferences=context.getSharedPreferences(PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor edit=mSharedPreferences.edit();
        edit.putString(prefName,perfValue);
        edit.apply();
    }

    public static String readFromPreference(Context context,String prefName,String prefValue){
        SharedPreferences mSharedPreferences=context.getSharedPreferences(PREF,context.MODE_PRIVATE);
        return mSharedPreferences.getString(prefName,prefValue);
    }

}
