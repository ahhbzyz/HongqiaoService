package com.fyp.hongqiaoservice.nearby;

/**
 * Created by Yaozhong on 2014/10/22.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.utils.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;


public class NearbyFragment extends Fragment implements ObservableScrollViewCallbacks {


    private Toolbar mToolbar;
    ViewPager mViewPager;
    int Height;
    View lrContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);

        lrContent = getActivity().findViewById(R.id.container);
        mToolbar = (Toolbar)getActivity().findViewById(R.id.my_awesome_toolbar);
        Height = getActivity().findViewById(R.id.container).getHeight();
        //viewPager
        MyPagerAdapter  mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_businesses);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        PagerSlidingTabStrip mPagerSlidingTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs_businesses);

        mPagerSlidingTabStrip.setBackgroundColor(getResources().getColor(R.color.teal));
        mPagerSlidingTabStrip.setIndicatorHeight(4);
        mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.white));
        mPagerSlidingTabStrip.setTextSize(30);
        mPagerSlidingTabStrip.setTabBackground(getResources().getColor(R.color.transparent));
        mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.transparent));
        mPagerSlidingTabStrip.setUnderlineColor(getResources().getColor(R.color.teal));
        mPagerSlidingTabStrip.setTextColor(getResources().getColor(R.color.deep_grey));
        mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.white));
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
    @Override
    public void onScrollChanged(int i, boolean b, boolean b2) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {

//            if (toolbarIsShown()) {
//                hideToolbar();
//            }
//        } else if (scrollState == ScrollState.DOWN) {
//
//            if (toolbarIsHidden()) {
//                showToolbar();
//            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if(hidden){
//            if (toolbarIsHidden()) {
//                showToolbar();
//            }
//        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
                ViewHelper.setTranslationY(lrContent, translationY);
                ViewGroup.LayoutParams lp = lrContent.getLayoutParams();
                lp.height = (int) -translationY +Height;
                lrContent.requestLayout();
            }
        });
        animator.start();
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = { "美食", "酒店","购物","服务"};

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

            final int pattern = position % 4;
            switch (pattern) {
                case 0:
                default: {
                    BusinessesFragment   f = new BusinessesFragment();
                    Bundle args = new Bundle();
                    args.putInt(f.ARG_INITIAL_CONTENT, 0);
                    f.setArguments(args);
                    return f;
                }
                case 1: {
                    BusinessesFragment  f = new BusinessesFragment();
                    Bundle args = new Bundle();
                    args.putInt(f.ARG_INITIAL_CONTENT, 1);
                    f.setArguments(args);

                    return f;
                }
                case 2: {
                    BusinessesFragment  f = new BusinessesFragment();
                    Bundle args = new Bundle();
                    args.putInt(f.ARG_INITIAL_CONTENT, 2);
                    f.setArguments(args);
                    return f;
                }
                case 3: {
                    BusinessesFragment    f = new BusinessesFragment();
                    Bundle args = new Bundle();
                    args.putInt(f.ARG_INITIAL_CONTENT, 3);
                    f.setArguments(args);
                    return f;
                }
            }
        }

    }
}

