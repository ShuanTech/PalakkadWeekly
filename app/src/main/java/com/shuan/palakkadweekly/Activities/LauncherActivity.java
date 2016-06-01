package com.shuan.palakkadweekly.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.Utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class LauncherActivity extends AppCompatActivity {

    private Common mApp;
    private Context context;
    private static int SPLASH_TIME_OUT = 2000;
    private AlertDialog.Builder builder;
    private HashMap<String, String> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        mApp = (Common) context.getApplicationContext();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);


        if (NetworkUtil.getConnectivityStatus(getApplicationContext()) == 0) {

            showAlert();

        } else {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    //Toast.makeText(getApplicationContext(),Boolean.toString(mApp.getSharedPreferences().getBoolean(Common.Login,false)),Toast.LENGTH_SHORT).show();

                    if (mApp.getSharedPreferences().getBoolean(Common.Login, false) == false) {

                        //Toast.makeText(getApplicationContext(),"Not Login",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {

                        new checkUser().execute();
                    }
                }
            }, SPLASH_TIME_OUT);
        }

        /*new Handler().postDelayed(new Runnable() {

            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                //Toast.makeText(getApplicationContext(),Boolean.toString(mApp.getSharedPreferences().getBoolean(Common.Login,false)),Toast.LENGTH_SHORT).show();

                if (mApp.getSharedPreferences().getBoolean(Common.Login, false) == false) {

                    //Toast.makeText(getApplicationContext(),"Not Login",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LauncherActivity.this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LauncherActivity.this, Main.class);
                    startActivity(i);
                    finish();

                }
            }
        }, SPLASH_TIME_OUT);*/
    }

    private void showAlert() {
        builder = new AlertDialog.Builder(LauncherActivity.this)
                .setTitle("No Internet")
                .setCancelable(false)
                .setMessage("No Internet Connection Available.\n Do you want to try again?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.cancel();
            }
        }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                checkConnection();
            }
        }).show();

    }

    private void checkConnection() {

        if (NetworkUtil.getConnectivityStatus(getApplicationContext()) == 0) {

            showAlert();

        } else {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    //Toast.makeText(getApplicationContext(),Boolean.toString(mApp.getSharedPreferences().getBoolean(Common.Login,false)),Toast.LENGTH_SHORT).show();

                    if (mApp.getSharedPreferences().getBoolean(Common.Login, false) == false) {

                        //Toast.makeText(getApplicationContext(),"Not Login",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        new checkUser().execute();

                    }
                }
            }, SPLASH_TIME_OUT);
        }

    }

    public class checkUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            userData = new HashMap<String, String>();
            userData.put("id", mApp.getSharedPreferences().getString(Common.usrId, ""));
            try {

                JSONObject json = HttpUrlConnectionParser.makeHttpUrlConnection(php.usr_check, userData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            finish();
                        }
                    });
                } else {

                    JSONArray jsonArray = json.getJSONArray("user");
                    JSONObject child = jsonArray.getJSONObject(0);
                    final String name = child.optString("name");
                    final String propic = child.optString("propic");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mApp.getSharedPreferences().edit().putString(Common.usrName, name).commit();
                            mApp.getSharedPreferences().edit().putString(Common.usrImg, propic).commit();
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            finish();

                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }


}
