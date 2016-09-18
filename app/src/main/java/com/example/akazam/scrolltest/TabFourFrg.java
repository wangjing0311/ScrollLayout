package com.example.akazam.scrolltest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.akazam.scrolltest.ningfengview.NFRecyclerView;
public class TabFourFrg extends Fragment {


    NFRecyclerView rvList;
    public ScrollView outer;
    public int header;
    public int content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_first, null);
        rvList=(NFRecyclerView)view.findViewById(R.id.rv_list);

//        rvList.parentScrollView=outer;
        rvList.HeaderId=header;
        rvList.ContentContainerId=content;

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(new TestAdapter());
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
