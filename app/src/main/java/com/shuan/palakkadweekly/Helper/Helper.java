package com.shuan.palakkadweekly.Helper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.Utils.Methods;

import java.util.Locale;

public class Helper implements Methods {

    private Locale locale;
    private Common mApp;

    @Override
    public void changeLang(Context context,String lang) {
        if(lang.equalsIgnoreCase(""))
            return;

        locale=new Locale(lang);
       // saveLocale(lang);
        Locale.setDefault(locale);
        android.content.res.Configuration config=new android.content.res.Configuration();
        config.locale=locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());


    }

    @Override
    public void saveLocale(String lang) {
        mApp.getSharedPreferences().edit().putString("language", lang).commit();
    }
    public void alertWithOkDialog(Context context, String s, String s1)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                dialoginterface.cancel();
            }

        }).show();
    }


}
