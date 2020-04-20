package com.example.covid19;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GlobalFragment globalFragment = new GlobalFragment();
                return globalFragment;
            case 1:
                IndiaFragment indiaFragment = new IndiaFragment();
                return indiaFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return numOfTabs;
    }
}