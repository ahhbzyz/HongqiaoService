package com.fyp.hongqiaoservice.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.pojo.Flight;
import com.fyp.hongqiaoservice.query.pojo.FlightFollow;
import com.fyp.hongqiaoservice.query.pojo.TrainFollow;
import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class FlightFollowListActivity extends ActionBarActivity {


    private Context context;
    private String phone;
    private List<String> flightFollowList = new ArrayList<>();
    private SharedPreferences sp;
    private  Boolean loginStatus;
    private RecyclerView mRecyclerView;
    private FlightFollowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_follow_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("我关注的航班");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context =this;
        sp = context.getSharedPreferences("SP_FOLLOW", Context.MODE_APPEND);
        String strFlightFollowList = sp.getString("flight_follow", "");

        if (!strFlightFollowList.equals("")) {
            String [] flightNoArray = strFlightFollowList.split(",");
            for (String flightNo : flightNoArray) {
                flightFollowList.add(flightNo);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.flight_follow_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new FlightFollowAdapter(flightFollowList,context);
        mRecyclerView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_flight_follow_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;


            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
