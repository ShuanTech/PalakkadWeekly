package com.shuan.palakkadweekly.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.adapter.BusCodeAdapter;
import com.shuan.palakkadweekly.adapter.BusDetailAdapter;
import com.shuan.palakkadweekly.edittext.MaterialAutoCompleteTextView;
import com.shuan.palakkadweekly.fancyButton.FancyButton;
import com.shuan.palakkadweekly.lists.BusCodeList;
import com.shuan.palakkadweekly.lists.BusDetailsList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusTiming extends Fragment {

    private Menu menu;
    private FancyButton searchBuses;
    private RelativeLayout searchLayout,showLayout;
    private ListView mListView;
    private MaterialAutoCompleteTextView to_place;
    private ProgressBar progressBar;
    private String[] places={"Palakkad","Tirupur","coimbatore","salem"};
    private ArrayList<BusDetailsList> list;
    private BusDetailAdapter busAdapter;
    private Spinner type;
    private ArrayList<BusCodeList> busCodeList;
    private BusCodeAdapter adapter;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private php call=new php();
    private HashMap<String,String> busCodeData;
    private HashMap<String,String> searchData;
    private String busId,toPlace="";
    public BusTiming() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_bus_timing, container, false);


        searchLayout= (RelativeLayout) view.findViewById(R.id.search);
        showLayout= (RelativeLayout) view.findViewById(R.id.show);
        mListView= (ListView) view.findViewById(R.id.bus_details_list);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        searchBuses= (FancyButton) view.findViewById(R.id.btn_search);
        to_place= (MaterialAutoCompleteTextView) view.findViewById(R.id.to_place);
        //type= (Spinner) view.findViewById(R.id.type);
        /*List<String> types=new ArrayList<String>();
        types.add("All Type");
        types.add("Private");
        types.add("Krstc");
        final ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);*/
        new LoadBusCode().execute();
        progressBar.setVisibility(View.VISIBLE);
        list=new ArrayList<BusDetailsList>();
        busCodeList=new ArrayList<BusCodeList>();




        searchBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getActivity(),to_place.getText().toString(),Toast.LENGTH_SHORT).show();

                if(toPlace.toString().equalsIgnoreCase("")){
                    to_place.setError("Enter To Place");
                }else {
                    showLayout.setVisibility(View.VISIBLE);
                    searchLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    new LoadBusDetails().execute();
                    to_place.setText("");
                }
                //busAdapter=new BusDetailAdapter(getActivity(),list);
                //mListView.setAdapter(busAdapter);

            }
        });



        return view;
    }




    public class LoadBusCode extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            busCodeData=new HashMap<String,String>();
            busCodeData.put("buscode","buscode");
            try{
                JSONObject json=parser.makeHttpUrlConnection(call.buscode,busCodeData);
                int succ=json.getInt("success");
                if(succ==0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"No Buses Available",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("buscode");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String id=child.optString("id");
                        String buscode=child.optString("buscode");
                        String name=child.optString("fullName");
                        busCodeList.add(new BusCodeList(id,buscode,name));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            to_place.setThreshold(1);
                            adapter=new BusCodeAdapter(getActivity(),R.layout.bus_code_item,R.id.bus_name,busCodeList);
                            to_place.setAdapter(adapter);

                            to_place.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt= (TextView) view.findViewById(R.id.bus_name);
                                    TextView txt1= (TextView) view.findViewById(R.id.busId);
                                    TextView txt2= (TextView) view.findViewById(R.id.bus);
                                    to_place.setText(txt.getText().toString());
                                    busId=txt1.getText().toString();
                                    toPlace=txt2.getText().toString();
                                    //Toast.makeText(getActivity(),txt1.getText().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }



    public class LoadBusDetails extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            searchData=new HashMap<String,String>();
            searchData.put("busid", busId);
            try{

                JSONObject json=parser.makeHttpUrlConnection(call.busdetail,searchData);
                int succ=json.getInt("success");
                if(succ==0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"No Buses Available",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("busdetail");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String frmTime=child.optString("fromTime");
                        list.add(new BusDetailsList("Mannarkkad",toPlace,frmTime));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            busAdapter=new BusDetailAdapter(getActivity(),list);
                            mListView.setAdapter(busAdapter);
                        }
                    });
                }

            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu=menu;
        getActivity().getMenuInflater().inflate(R.menu.dir_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.search:
                //Toast.makeText(getActivity(),"search",Toast.LENGTH_SHORT).show();
                if(searchLayout.isShown()){

                }else {
                    list.clear();
                    toPlace="";
                    showLayout.setVisibility(View.INVISIBLE);
                    searchLayout.setVisibility(View.VISIBLE);

                }
                break;
        }
       return false;
    }
}
