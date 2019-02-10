package com.brin.gdufe.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brin.gdufe.R;
import com.brin.gdufe.customView.Adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SuperFragment extends Fragment implements TabLayout.OnTabSelectedListener {

//    @BindView(R.id.tab)
    TabLayout tabLayout;
//    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private List<String> mTabList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.superfragment, container, false);
//        ButterKnife.bind(this,view);
        initTabList();
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(),getActivity(),mFragments,mTabList);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置TabLayout的模式

        tabLayout.addOnTabSelectedListener(this);
//        mViewPager.setCurrentItem(0);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_course_24dp).getIcon().setColorFilter(Color.parseColor("#008577"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_me);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        }



    public static SuperFragment newInstance(String s) {

        SuperFragment superFragment = new SuperFragment();
        return superFragment;

    }

    @Override
    public void onStart() {
        super.onStart();
        initFragment();
    }

    private void initTabList() {

        mTabList.clear();
        mTabList.add("课表");
        mTabList.add("我");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      /*  SharedPreferences sp=getActivity().getSharedPreferences(this.getString(R.string.preference_file_key),MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        mOwner = sp.getBoolean("owner", false);
        if(mOwner==false){
            startActivity(new Intent(getActivity(),AuthRequestActivity.class));
            mOwner = true;
            editor.putBoolean("owner",mOwner);

        }*/

    }

    private void initFragment() {
        mFragments.clear();
        mFragments.add(courseFragment.newInstance());
        mFragments.add(meFragment.newInstance());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case 0:
                tab.getIcon().setColorFilter(Color.parseColor("#008577"), PorterDuff.Mode.SRC_IN);
                break;
            case 1:
                tab.getIcon().setColorFilter(Color.parseColor("#008577"), PorterDuff.Mode.SRC_IN);
                break;
            }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        switch (tab.getPosition()) {
            case 0:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_course_24dp);
                break;

            case 1:
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_me);
                break;
        }
    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*SharedPreferences sp=getContext().getSharedPreferences(this.getString(R.string.preference_file_key),MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("owner",true);*/
    }


}
