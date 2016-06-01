package com.shuan.palakkadweekly.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuan.palakkadweekly.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    private  WebView myWebView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shop, container, false);
         myWebView= (WebView) view.findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl("http://www.google.com");
        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            if (Uri.parse(url).getHost().equals("http://www.google.com")) {
                return false;
            }else {
                view.loadUrl(url);
                return true;

            }

        }
    }






}
