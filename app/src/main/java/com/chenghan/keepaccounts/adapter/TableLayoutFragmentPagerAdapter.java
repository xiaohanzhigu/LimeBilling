package com.chenghan.keepaccounts.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TableLayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragList;
    private String[] titles;

    public TableLayoutFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragList, String[] titles) {
        super(fm);
        this.fragList = fragList;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
