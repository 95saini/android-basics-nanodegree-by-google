package com.example.arieldiax.tourguideapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * @var mAttractionsViewPager View pager for Attractions.
     */
    private ViewPager mAttractionsViewPager;

    /**
     * @var mAttractionsTabLayout Tab layout for Attractions.
     */
    private TabLayout mAttractionsTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        init();
    }

    /**
     * Initializes the user interface view bindings.
     */
    private void initUi() {
        mAttractionsViewPager = (ViewPager) findViewById(R.id.attractions_view_pager);
        mAttractionsTabLayout = (TabLayout) findViewById(R.id.attractions_tab_layout);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        AttractionFragmentPagerAdapter attractionFragmentPagerAdapter = new AttractionFragmentPagerAdapter(this, getSupportFragmentManager());
        mAttractionsViewPager.setAdapter(attractionFragmentPagerAdapter);
        mAttractionsTabLayout.setupWithViewPager(mAttractionsViewPager);
    }
}
