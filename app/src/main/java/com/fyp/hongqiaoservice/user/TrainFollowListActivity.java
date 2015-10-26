package com.fyp.hongqiaoservice.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fyp.hongqiaoservice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainFollowListActivity extends ActionBarActivity {

    private Context context;
    private String phone;
    private List<String> trainFollowList = new ArrayList<>();
    private  String strTrainFollow = "";
    private SharedPreferences sp;
    private  Boolean loginStatus;
    private RecyclerView mRecyclerView;
    private TrainFollowAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_follow_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("我关注的列车");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context =this;
        sp = context.getSharedPreferences("SP_FOLLOW", Context.MODE_APPEND);
        String strTrainFollowList = sp.getString("train_follow", "");

        if (!strTrainFollowList.equals("")) {
            String [] trainNoArray = strTrainFollowList.split(",");
            for (String trainNo : trainNoArray) {
                trainFollowList.add(trainNo);
            }
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.train_follow_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TrainFollowAdapter(trainFollowList,context);
        mRecyclerView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_train_follow_list, menu);
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
