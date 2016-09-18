package com.example.akazam.scrolltest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akazam.scrolltest.ningfengview.NFScrollView;
import com.example.akazam.scrolltest.wjdemo.ScrollLayout;


public class TabThreeFrg extends Fragment {

    NFScrollView rvList;
    public ScrollLayout outer;
    public int header;
    public int content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_three, null);
        rvList=(NFScrollView)view.findViewById(R.id.rv_list);

        rvList.parentScrollView=outer;
        rvList.HeaderId=header;
        rvList.ContentContainerId=content;

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
