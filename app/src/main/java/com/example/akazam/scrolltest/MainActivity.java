package com.example.akazam.scrolltest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioButton item1;
    private RadioButton item2;
    private RadioButton item3;
    private RadioButton item4;
    private ScrollView outer;
    private int ids[] = {R.id.item1, R.id.item2, R.id.item3, R.id.item4};
    private RadioGroup header;
    private ViewPager viewPager;
    private List<Fragment> frgs = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outer=(ScrollView)super.findViewById(R.id.outer);
        header=(RadioGroup)super.findViewById(R.id.header);
        viewPager=(ViewPager)super.findViewById(R.id.view_pager);

        final View view = outer;
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams containerparams = viewPager.getLayoutParams();
                containerparams.height = outer.getHeight() - header.getHeight();
                viewPager.setLayoutParams(containerparams);
            }
        });

        TabOneFrg tab1 = new TabOneFrg();
//        tab1.outer = this.outer;
        frgs.add(tab1);
        TabTwoFrg tab2 = new TabTwoFrg();
//        tab2.outer = this.outer;
        frgs.add(tab2);
        TabThreeFrg tab3 = new TabThreeFrg();
//        tab3.outer = this.outer;
        frgs.add(tab3);
        TabFourFrg tab4 = new TabFourFrg();
        tab4.outer = this.outer;
        frgs.add(tab4);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager(), frgs));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                header.check(ids[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });





    }
}
