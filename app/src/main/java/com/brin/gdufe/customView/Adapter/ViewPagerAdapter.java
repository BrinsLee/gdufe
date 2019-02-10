package com.brin.gdufe.customView.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private  List<Fragment> fragmentList ;
    private  List<String> fragmentListTitle ;
    private Context mContext;
    private  int [] images;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(FragmentManager fm, Context mContext, List<Fragment> fragmentList, List<String> fragmentListTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mContext=mContext;
        this.fragmentListTitle = fragmentListTitle;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentListTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get(position);
    }

    /*public void AddFragment(Fragment fragment, String Title) {
        fragmentList.add(fragment);
        fragmentListTitle.add(Title);

    }*/
}
