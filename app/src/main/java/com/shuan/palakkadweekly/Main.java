package com.shuan.palakkadweekly;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.shuan.palakkadweekly.Fragments.BusTiming;
import com.shuan.palakkadweekly.Fragments.DirectoryFragment;
import com.shuan.palakkadweekly.Fragments.HomeFragment;
import com.shuan.palakkadweekly.Fragments.NattuVarthaFragment;
import com.shuan.palakkadweekly.Fragments.SportsFragment;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.drawer.LeftNavigationDrawer;
import com.shuan.palakkadweekly.drawer.RightNavigationDrawer;
import com.shuan.palakkadweekly.tab.CacheFragmentStatePagerAdapter;
import com.shuan.palakkadweekly.tab.SlidingTab;

import java.util.Locale;


public class Main extends AppCompatActivity {


    private Common mApp;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerParentLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LeftNavigationDrawer mLeftDrawer;
    private RelativeLayout mLeftNavDrawerLayout;
    private RightNavigationDrawer mRightDrawer;
    private RelativeLayout mRightNavDrawerLayout;
    private Locale myLocale;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private NewsPortalAdapter adapter;
    private Handler mHandler;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mApp = (Common) getApplicationContext();
        if (mApp.getSharedPreferences().getString("lang", "") != null) {
            changeLang(mApp.getSharedPreferences().getString("lang", ""));
        }

        if (mApp.getSharedPreferences().getString(Common.Version, "").equalsIgnoreCase("true")) {
            builder = new AlertDialog.Builder(Main.this)
                    .setTitle("Update")
                    .setMessage("New Version of Palakkad Weekly Available.Do you want Update?");
            builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent("android.intent.action.VIEW")
                            .setData(Uri.parse("market://details?id=com.shuan.palakkadweekly"));
                    startActivity(in);
                    dialog.cancel();
                }
            }).show();

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));


        mDrawerParentLayout = (FrameLayout) findViewById(R.id.main_activity_root);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_root);
        mLeftNavDrawerLayout = (RelativeLayout) findViewById(R.id.left_nav_drawer);
        mRightNavDrawerLayout = (RelativeLayout) findViewById(R.id.right_nav_drawer);
        loadDrawerFragments();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (mRightDrawer != null && drawerView == mRightNavDrawerLayout) {
                    //mRightDrawer.setIsDrawerOpen(true);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mRightDrawer != null && drawerView == mRightNavDrawerLayout) {
                    //mRightDrawer.setIsDrawerOpen(false);
                }
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        adapter = new NewsPortalAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        SlidingTab slidingTab = (SlidingTab) findViewById(R.id.tab);
        slidingTab.setCustomTabView(R.layout.custom_tab, R.id.custom_text);
        mViewPager.setAdapter(adapter);
        slidingTab.setViewPager(mViewPager);

        /*mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_SHORT).show();
                alertTag();
            }
        }, 5000);*/


    }


    private void alertTag() {
        if (mApp.getSharedPreferences().getString(Common.Tag, "").equalsIgnoreCase("")) {
            mApp.getSharedPreferences().edit().putString(Common.Tag, "1").commit();
            final String[] tag = {"mannarkkad", "newsportal", "shuan tech pvt ltd", "kanjirapuzha"};
            builder = new AlertDialog.Builder(Main.this);
            builder.setItems(tag, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), tag[which], Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).show();
        }
    }

    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        mDrawerToggle.syncState();
    }

    private void loadDrawerFragments() {
        mLeftDrawer = new LeftNavigationDrawer();
        mLeftDrawer.setUp((DrawerLayout) findViewById(R.id.main_activity_drawer_root), toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_nav_drawer, mLeftDrawer).commit();
        mRightDrawer = new RightNavigationDrawer();
        mRightDrawer.setUp((DrawerLayout) findViewById(R.id.main_activity_drawer_root), toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_nav_drawer, mRightDrawer).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.drawer_right:
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                if (mDrawerLayout != null && mRightNavDrawerLayout != null) {
                    if (mDrawerLayout.isDrawerOpen(mRightNavDrawerLayout)) {
                        mDrawerLayout.closeDrawer(mRightNavDrawerLayout);
                        return true;
                    } else {
                        mDrawerLayout.openDrawer(mRightNavDrawerLayout);
                        return true;
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void alertTag1() {
        final String[] tag = {"mannarkkad", "newsportal", "shuan tech pvt ltd", "kanjirapuzha"};
        builder = new AlertDialog.Builder(Main.this);
        builder.setItems(tag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), tag[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).show();

    }

    public class NewsPortalAdapter extends CacheFragmentStatePagerAdapter {

        public final String[] tabs = getResources().getStringArray(R.array.main_menu);

        public NewsPortalAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment createItem(int position) {
            Fragment f = null;


            switch (position) {
                case 0:
                    f = new HomeFragment();
                    break;
                case 1:
                    f = new NattuVarthaFragment();
                    break;
                case 2:
                    f = new SportsFragment();
                    break;
                case 3:
                    f = new DirectoryFragment();
                    break;
                case 4:
                    f = new BusTiming();
                    //f = new ShopFragment();
                    break;

            }

            return f;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

    private Fragment getCurrentFragment() {
        return adapter.getItemAt(mViewPager.getCurrentItem());
    }


    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        } else {
            super.onBackPressed();
            return;
        }
    }


}
