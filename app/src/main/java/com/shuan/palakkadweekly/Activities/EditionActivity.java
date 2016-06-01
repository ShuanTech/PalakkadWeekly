package com.shuan.palakkadweekly.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.EdtnAdapter;
import com.shuan.palakkadweekly.lists.EdtList;

import java.util.ArrayList;

public class EditionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private ArrayList<EdtList> edtList;
    private EdtnAdapter edtnAdapter;
    private Common mApp;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=getApplicationContext();
        mApp= (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Edition));
        listView= (ListView) findViewById(R.id.edt_list);
        String[] edtn=getResources().getStringArray(R.array.places);
        edtList=new ArrayList<EdtList>();
        for(int i=0;i<edtn.length;i++){
            edtList.add(new EdtList(edtn[i].toString()));
        }
        edtnAdapter=new EdtnAdapter(getApplicationContext(),edtList);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,edtn);
        listView.setAdapter(edtnAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt= (TextView) view.findViewById(R.id.edt_text);
                mApp.getSharedPreferences().edit().putString(Common.EDTITION,txt.getText().toString()).commit();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),txt.getText().toString()+Integer.toString(position),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
