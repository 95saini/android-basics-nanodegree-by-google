package com.example.arieldiax.tourguideapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AttractionFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * @var mPageTitles List of the page titles.
     */
    private String[] mPageTitles;

    /**
     * @var mItems List of the items.
     */
    private Fragment[] mItems;

    /**
     * Creates a new AttractionFragmentPagerAdapter object.
     *
     * @param context         Instance of the Context class.
     * @param fragmentManager Instance of the FragmentManager class.
     */
    public AttractionFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mPageTitles = new String[]{
                context.getString(R.string.page_1_title),
                context.getString(R.string.page_2_title),
                context.getString(R.string.page_3_title),
                context.getString(R.string.page_4_title),
        };
        mItems = new Fragment[]{
                new AmsterdamFragment(),
                new BerlinFragment(),
                new MadridFragment(),
                new RomeFragment(),
        };
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mItems[position];
    }
}
