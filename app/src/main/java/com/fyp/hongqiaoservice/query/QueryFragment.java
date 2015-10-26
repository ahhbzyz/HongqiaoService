package com.fyp.hongqiaoservice.query;

/**
 * Created by Yaozhong on 2014/10/22.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.nearby.BusinessesFragment;
import com.fyp.hongqiaoservice.query.bus.BusQueryFragment;
import com.fyp.hongqiaoservice.query.coach.CoachQueryFragment;
import com.fyp.hongqiaoservice.query.flight.FlightFragment;
import com.fyp.hongqiaoservice.query.subway.SubwayQueryFragment;
import com.fyp.hongqiaoservice.query.train.TrainFragment;
import com.fyp.hongqiaoservice.utils.PagerSlidingTabStrip;

import java.util.ArrayList;


public  class QueryFragment extends Fragment {








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_query, container, false);

         //viewPager
        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_query);
        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(3);//预加载页数
        PagerSlidingTabStrip mPagerSlidingTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs_query);
        mPagerSlidingTabStrip.setBackgroundColor(getResources().getColor(R.color.teal));
        mPagerSlidingTabStrip.setIndicatorHeight(4);
        mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.white));
        mPagerSlidingTabStrip.setTextSize(30);
        mPagerSlidingTabStrip.setTabBackground(getResources().getColor(R.color.transparent));
        mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.transparent));
        mPagerSlidingTabStrip.setUnderlineColor(getResources().getColor(R.color.teal));
        mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.white));
        mPagerSlidingTabStrip.setTextColor(getResources().getColor(R.color.deep_grey));
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        return rootView;

    }




    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = { "高铁", "航班","地铁","公交"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {



            final int pattern = position % 5;
            switch (pattern) {
                case 0:
                default: {


                    return new TrainFragment();

                }
                case 1:{



                    return new FlightFragment();
                }
                case 2:{

                    return new SubwayQueryFragment();
                }
                case 3:{


                    return new BusQueryFragment();
                }
            }

        }

    }
}

