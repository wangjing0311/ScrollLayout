package com.example.akazam.scrolltest.wjdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.akazam.scrolltest.R;
import com.example.akazam.scrolltest.TabOneFrg;
import com.example.akazam.scrolltest.TabThreeFrg;
import com.example.akazam.scrolltest.TabTwoFrg;

import java.util.ArrayList;
import java.util.List;

public class WjMainActivity extends AppCompatActivity {

    private ScrollLayout scrollView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private WjViewPagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wj_activity_main);
        scrollView = (ScrollLayout) findViewById(R.id.wj_scrollview);
        mTabLayout = (TabLayout) findViewById(R.id.wj_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.wj_viewpager);
        final View view = scrollView;
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                ViewGroup.LayoutParams containerparams = mViewPager.getLayoutParams();
//                containerparams.height = scrollView.getHeight() - mTabLayout.getHeight();
//                mViewPager.setLayoutParams(containerparams);
//            }
//        });
        setupViewPager();
    }

    TabOneFrg oneFrg;
    TabTwoFrg twoFrg;
    TabThreeFrg threeFrg;

    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("one");
        titles.add("two");
        titles.add("three");
        oneFrg = new TabOneFrg();
        twoFrg = new TabTwoFrg();
        threeFrg = new TabThreeFrg();
        fragments.add(oneFrg);
        fragments.add(twoFrg);
        fragments.add(threeFrg);
//        oneFrg.outer = this.scrollView;
//        twoFrg.outer = this.scrollView;
//        threeFrg.outer = this.scrollView;

        adapter = new WjViewPagerFragmentAdapter(this.getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
    }

}
