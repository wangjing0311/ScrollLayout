package com.example.akazam.scrolltest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.akazam.scrolltest.ningfengview.NFWebView;
import com.example.akazam.scrolltest.wjdemo.ScrollLayout;

public class TabTwoFrg extends Fragment {


    NFWebView rvList;
    public ScrollLayout outer;
    public int header;
    public int content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_two, null);
        rvList=(NFWebView)view.findViewById(R.id.rv_list);
        rvList.setVerticalScrollBarEnabled(false);
        rvList.setVerticalScrollbarOverlay(false);
        rvList.setHorizontalScrollBarEnabled(false);
        rvList.setHorizontalScrollbarOverlay(false);
        rvList.getSettings().setJavaScriptEnabled(true);
        rvList.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        rvList.parentScrollView=outer;
        rvList.HeaderId=header;
        rvList.ContentContainerId=content;

        rvList.loadUrl("http://www.baidu.com");
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
