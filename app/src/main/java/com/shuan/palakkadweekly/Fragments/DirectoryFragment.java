package com.shuan.palakkadweekly.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.palakkadweekly.Activities.DirDetails;
import com.shuan.palakkadweekly.Parser.HttpUrlConnectionParser;
import com.shuan.palakkadweekly.Parser.php;
import com.shuan.palakkadweekly.R;
import com.shuan.palakkadweekly.Utils.Common;
import com.shuan.palakkadweekly.adapter.DirAdapter;
import com.shuan.palakkadweekly.edittext.MaterialAutoCompleteTextView;
import com.shuan.palakkadweekly.lists.HomeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends Fragment {


    public RelativeLayout search;
    public ImageButton ib;
    public MaterialAutoCompleteTextView inputSearch;
    private Context mContext;
    private HttpUrlConnectionParser parser=new HttpUrlConnectionParser();
    private php call=new php();
    public HashMap<String,String> homeData;
    public Common mApp;
    public ArrayList<HomeList> homeList;
    public DirAdapter adapter;
    public ListView mListView;
    public ProgressBar progressBar;

    public DirectoryFragment() {
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
       View view= inflater.inflate(R.layout.fragment_directory, container, false);

        /*inputSearch= (MaterialAutoCompleteTextView) view.findViewById(R.id.inputSearch);

        ib= (ImageButton) view.findViewById(R.id.ib);
        search= (RelativeLayout) view.findViewById(R.id.row);*/

        mListView = (ListView)view.findViewById(R.id.home_list);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        homeList = new ArrayList<HomeList>();
        progressBar.setVisibility(View.VISIBLE);
        new LoadDir().execute();


        return view;
    }

    public class LoadDir extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            homeData=new HashMap<String,String>();
            homeData.put("dir","dir");
            JSONObject json=null;
            String img=null;
            try{
                json=parser.makeHttpUrlConnection(call.dir,homeData);
                int succ=json.getInt("success");
                if(succ==0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    JSONArray jsonArray=json.getJSONArray("directory");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject child=jsonArray.getJSONObject(i);
                        String id=child.optString("id");
                        String heading=child.optString("name");

                        img="http://www.palakkadweekly.com/adminpanel/diroctory_images/"+child.optString("cover_photo");

                        //homeList.add(new HomeList(id,heading,img));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new DirAdapter(getActivity(), homeList);
                            mListView.setAdapter(adapter);
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt= (TextView) view.findViewById(R.id.nattu_vartha_id);
                                    TextView txt1= (TextView) view.findViewById(R.id.title);
                                    Intent in=new Intent(getActivity(),DirDetails.class);
                                    in.putExtra("name",txt1.getText().toString());
                                    in.putExtra("id", txt.getText().toString());
                                    startActivity(in);
                                    //adminpanel/directory_item_image/de975591ba5e6cb16b7570b6f73a6ece48181.jpg
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

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.dir_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.search:

               search.setVisibility(View.VISIBLE);


               ib.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       search.setVisibility(View.GONE);
                       InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                       imm.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);
                   }
               });
               inputSearch.requestFocus();
               inputSearch.setFocusable(true);
               InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.showSoftInput(inputSearch, InputMethodManager.SHOW_IMPLICIT);
               inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                   @Override
                   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                       if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                           Toast.makeText(getActivity(),inputSearch.getText().toString()+"Searc Start",Toast.LENGTH_SHORT).show();
                           InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                           imm.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);
                           return true;
                       }
                       return false;
                   }
               });


               break;
       }

        return super.onOptionsItemSelected(item);
    }*/
}
