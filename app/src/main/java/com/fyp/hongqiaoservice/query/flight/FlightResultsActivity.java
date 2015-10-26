package com.fyp.hongqiaoservice.query.flight;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.pojo.F1DynFlight;
import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;
import com.thinkland.sdk.android.Parameters;
import java.util.ArrayList;
import java.util.List;

import static com.fyp.hongqiaoservice.utils.NetWorkUtils.isNetworkConnected;

public class FlightResultsActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private FlightResultsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<F1DynFlight> f1DynFlightList = new ArrayList<>();
    private boolean isLoaded =false;
    private String URL;
    private Boolean isJSON = true;
    private Boolean isZero = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_results);
        context =this;

        Bundle b = getIntent().getExtras();
        final String fromName = b.getString("fromName");
        final String toName = b.getString("toName");
        String dateName = b.getString("dateName");
        String flightNo = b.getString("flightNo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String routeName = fromName + " 至 " + toName;



        if(fromName!=null){
            getSupportActionBar().setTitle(routeName);
            URL = Constant.URL+Constant.FlightQueryServlet +"type=airport"+"&origin="+fromName+"&dest="+toName+"&date="+dateName;
        }else{
            getSupportActionBar().setTitle(flightNo);
            URL = Constant.URL+Constant.FlightQueryServlet +"type=flight_no"+"&flight_no="+flightNo+"&date="+dateName;
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.flight_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.teal,R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoaded){
                    refreshFlightData();
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(FlightResultsActivity.this, "正在加载中", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(isNetworkConnected(this)){
            new ProgressTask().execute();
        }else {
            Toast.makeText(this, "请检查下您的网络状态", Toast.LENGTH_LONG).show();
        }

    }

    public void refreshFlightData(){
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                HttpConnectionUtil conn = new HttpConnectionUtil();
                conn.asyncConnect(URL, HttpConnectionUtil.HttpMethod.GET,
                        new HttpConnectionUtil.HttpConnectionCallback()
                        {
                            @Override
                            public void execute(String response)
                            {
                                Message msg = new Message();
                                if(response!=null) {
                                    try {
                                        f1DynFlightList.clear();
                                        f1DynFlightList = JSON.parseArray(response, F1DynFlight.class);
                                        isJSON = true;
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        isJSON = false;
                                    }
                                    if(isJSON){

                                        adapter = new FlightResultsAdapter(f1DynFlightList, context);
                                        msg.what=0;
                                        handler.obtainMessage(0, msg.obj).sendToTarget();
                                        if(f1DynFlightList.size()==0){
                                            msg.what=1;
                                            handler.obtainMessage(1, msg.obj).sendToTarget();
                                        }else{

                                        }
                                    }else {
                                        msg.what=2;
                                        handler.obtainMessage(2, msg.obj).sendToTarget();
                                    }
                                }
                            }
                        });
            }
        };
        new Thread(mRunnable).start();
    }
    Handler handler= new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage (Message msg) {
            switch(msg.what) {
                case 0:
                    mRecyclerView.setAdapter(adapter);
                    break;
                case 1:
                    Toast.makeText(FlightResultsActivity.this, "很抱歉，暂时没有符合您条件的航班", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(FlightResultsActivity.this, "很抱歉， 解析JSON出错", Toast.LENGTH_SHORT).show();
                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
            return false;
        }
    });


    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            findViewById(R.id.wheel).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpConnectionUtil conn = new HttpConnectionUtil();
            conn.asyncConnect(URL, HttpConnectionUtil.HttpMethod.GET,
                    new HttpConnectionUtil.HttpConnectionCallback() {
                        @Override
                        public void execute(String response) {
                            if(response!=null) {
                                try {
                                    f1DynFlightList = JSON.parseArray(response, F1DynFlight.class);
                                    isJSON = true;
                                }catch (Exception e){
                                    e.printStackTrace();
                                    isJSON = false;
                                }
                                if(isJSON){
                                    adapter = new FlightResultsAdapter(f1DynFlightList, context);
                                    if(f1DynFlightList.size()==0){
                                        isZero = true;
                                    }else{
                                        isZero = false;
                                    }
                                }
                            }
                        }
                    });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            findViewById(R.id.wheel).setVisibility(View.INVISIBLE);
            mRecyclerView.setAdapter(adapter);
            isLoaded = true;
            if(isZero){
                Toast.makeText(FlightResultsActivity.this, "很抱歉，暂时没有符合您条件的航班", Toast.LENGTH_SHORT).show();
            }
            if(!isJSON){
                Toast.makeText(FlightResultsActivity.this, "很抱歉， 解析JSON出错", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flight_results, menu);
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
            case R.id.action_settings:
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
