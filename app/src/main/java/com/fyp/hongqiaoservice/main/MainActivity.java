package com.fyp.hongqiaoservice.main;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.fyp.hongqiaoservice.dynamic.DynamicFragment;
import com.fyp.hongqiaoservice.parking.ParkingFragment;
import com.fyp.hongqiaoservice.user.UserFragment;
import com.fyp.hongqiaoservice.utils.DBHelper;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.nearby.NearbyFragment;
import com.fyp.hongqiaoservice.query.QueryFragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thinkland.sdk.android.SDKInitializer;

import java.io.IOException;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private DynamicFragment dynamicFragment;
    private QueryFragment queryFragment;
    private NearbyFragment nearbyFragment;
    private ParkingFragment parkingFragment;
    private UserFragment userFragment;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDKInitializer.initialize(getApplicationContext());
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);


        mTitle = getString(R.string.title_section1);
        mToolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.copyDatabase("shuniu.db");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        switch (position+1) {
            case 1:
                showFragment(1);
                break;
            case 2:
                showFragment(2);
                mTitle = getString(R.string.title_section2);
                getSupportActionBar().setTitle(mTitle);
                break;
            case 3:
                showFragment(3);
                mTitle = getString(R.string.title_section3);
                getSupportActionBar().setTitle(mTitle);
                break;
            case 4:
                showFragment(4);
                mTitle = getString(R.string.title_section4);
                getSupportActionBar().setTitle(mTitle);
                break;
            case 5:
                showFragment(5);
                mTitle = getString(R.string.title_section5);
                getSupportActionBar().setTitle(mTitle);
                break;
            default:
                break;
        }
    }

    public void showFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);

        switch (index) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (dynamicFragment != null) {
                    mTitle = getString(R.string.title_section1);
                    getSupportActionBar().setTitle(mTitle);
                    ft.show(dynamicFragment);
                }
                // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    dynamicFragment = new DynamicFragment();
                    ft.add(R.id.container, dynamicFragment);
                }
                break;
            case 2:
                // 如果fragment1已经存在则将其显示出来
                if (queryFragment != null) {
                    mTitle = getString(R.string.title_section1);
                    getSupportActionBar().setTitle(mTitle);
                    ft.show(queryFragment);
                }
                    // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    queryFragment = new QueryFragment();
                    ft.add(R.id.container, queryFragment);
                }
                break;
            case 3:
                if (nearbyFragment != null) {

                    ft.show(nearbyFragment);
                }
                else {
                    nearbyFragment = new NearbyFragment();
                    ft.add(R.id.container, nearbyFragment);
                }
                break;
            case 4:
                if (parkingFragment != null) {

                    ft.show(parkingFragment);
                }
                else {
                    parkingFragment = new ParkingFragment();
                    ft.add(R.id.container, parkingFragment);
                }
                break;
            case 5:
                if (userFragment != null) {

                    ft.show(userFragment);
                }
                else {
                    userFragment = new UserFragment();
                    ft.add(R.id.container, userFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (dynamicFragment != null)
            ft.hide(dynamicFragment);
        if (queryFragment != null)
            ft.hide(queryFragment);
        if (nearbyFragment != null)
            ft.hide(nearbyFragment);
        if (parkingFragment != null)
            ft.hide(parkingFragment);
        if (userFragment != null)
            ft.hide(userFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
