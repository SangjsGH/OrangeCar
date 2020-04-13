package com.example.orangecar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private FragmentManager fm;

    public ViewpagerAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fm=fm;
        this.fragmentList=fragmentList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
