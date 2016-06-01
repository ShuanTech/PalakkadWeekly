package com.shuan.palakkadweekly.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shuan.palakkadweekly.Helper.Helper;
import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.edittext.MaterialEditText;
import com.shuan.palakkadweekly.fancyButton.FancyButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private php call=new php();
    private HashMap<String,String> loinData;
    private Common mApp;
    private ProgressDialog pDialog;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private Helper helper=new Helper();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common)getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:

        }

    }

   /* public class Login extends AsyncTask<String,String,String>{


        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Login...");
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            loinData = new HashMap<String,String>();
            loinData.put("usrName", lUsrName);
            loinData.put("pass", lPassword);

            try{
                JSONObject json=parser.makeHttpUrlConnection(call.login,loinData);
                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            helper.alertWithOkDialog(LoginActivity.this,"Error","Login Failed! Try Again!...");
                            usr_name.setText("");
                            password.setText("");
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("login");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(0);
                        usrName=child.optString("name");
                        usrImg=child.optString("propic");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Success");
                            builder.setMessage("Successfully Login!...");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mApp.getSharedPreferences().edit().putString(Common.usrName,usrName).commit();
                                    mApp.getSharedPreferences().edit().putString(Common.usrImg, usrImg).commit();
                                    mApp.getSharedPreferences().edit().putBoolean(Common.Login, true).commit();
                                    startActivity(new Intent(LoginActivity.this, Main.class));
                                    finish();
                                }
                            }).show();
                        }
                    });
                }
            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }
    }*/
}
