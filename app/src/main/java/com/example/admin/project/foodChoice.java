package com.example.admin.project;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class foodChoice extends AppCompatActivity implements View.OnClickListener{
    Adapter adapter;
    AutoScrollViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_choice);
        setTitle("Food Choice");

        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.food1);
        data.add(R.drawable.food2);
        data.add(R.drawable.food3);

        viewPager = (AutoScrollViewPager)findViewById(R.id.viewPager);
        adapter = new Adapter(this, data);
        viewPager.setAdapter(adapter);
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
    }

    @Override
    public void onClick(View v) {

    }
}
