package com.shuan.palakkadweekly.drawer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuan.palakkadweekly.Activities.GalleryActivity;
import com.shuan.palakkadweekly.Activities.RightDrawerActiivty;
import com.shuan.palakkadweekly.Activities.VideoPlayerActivity;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.RightListAdapter;
import com.shuan.palakkadweekly.adapter.RightNavigationAdapter;
import com.shuan.palakkadweekly.lists.RightList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightNavigationDrawer extends Fragment {

    public static final String KEY_USED_DRAWER = "used drawer";
    public static final String PREF = "pref";
    private Common mApp;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public boolean mFromSavedInstance;
    public Handler mHandler;
    public boolean mUserDrawer;
    public RightNavigationAdapter adapter;
    public ExpandableListView mexpListView;
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;
    public ListView mListview;
    public ArrayList<RightList> rightList;
    public RightListAdapter menuAdapter;

    public RightNavigationDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_right_navigation_drawer, container, false);
        mexpListView= (ExpandableListView) view.findViewById(R.id.lvExp);
        mListview= (ListView) view.findViewById(R.id.list);
        rightList=new ArrayList<RightList>();
        String[] menu=getActivity().getResources().getStringArray(R.array.right_menu);
        for(int i=0;i<menu.length;i++){
            rightList.add(new RightList(menu[i].toString()));
        }
        menuAdapter=new RightListAdapter(getActivity(),rightList);
        mListview.setAdapter(menuAdapter);

        prepareList();
        adapter=new RightNavigationAdapter(getActivity(),listDataHeader,listDataChild);
        mexpListView.setAdapter(adapter);

        mexpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(getActivity(),Integer.toString(childPosition),Toast.LENGTH_SHORT).show();
                TextView txt= (TextView) v.findViewById(R.id.lblListItem);
                Intent in=new Intent(getActivity(),RightDrawerActiivty.class);
                in.putExtra("menu",txt.getText().toString());
                in.putExtra("id","d"+Integer.toString(childPosition));
                startActivity(in);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

       mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               //Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT).show();
                    TextView txt= (TextView) view.findViewById(R.id.menu_name);

                        if(position==20){
                            startActivity(new Intent(getActivity(),GalleryActivity.class));
                        }else if(position==21){
                            startActivity(new Intent(getActivity(),VideoPlayerActivity.class));
                        }else {
                            Intent in = new Intent(getActivity(), RightDrawerActiivty.class);
                            in.putExtra("menu",txt.getText().toString());
                            in.putExtra("id", Integer.toString(position));
                            startActivity(in);
                        }
                       mDrawerLayout.closeDrawers();
                    //Toast.makeText(getActivity(),txt.getText().toString(),Toast.LENGTH_SHORT).show();
           }
       });

        return view;
    }

    private void prepareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(getActivity().getResources().getString(R.string.news));

        // Adding child data
        List<String> news = new ArrayList<String>();
        String[] mnu=getActivity().getResources().getStringArray(R.array.dropDown);
        for(int i=0;i<mnu.length;i++){
            news.add(mnu[i].toString());
        }
        /*news.add("Nattu Vartha");
        news.add("Kerala");
        news.add("India");
        news.add("World");*/



        /*List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");*/

        listDataChild.put(listDataHeader.get(0), news); // Header, Child data

    }


    public void setUp(DrawerLayout drawerlayout, Toolbar toolbar){
        mDrawerLayout = drawerlayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mUserDrawer=true;
                savedToPreference(getActivity(),KEY_USED_DRAWER,mUserDrawer+"");
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
