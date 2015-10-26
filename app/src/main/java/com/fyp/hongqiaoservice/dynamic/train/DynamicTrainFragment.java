package com.fyp.hongqiaoservice.dynamic.train;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.pojo.RRdt;
import com.fyp.hongqiaoservice.query.train.TrainResultsAdapter;
import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import static com.fyp.hongqiaoservice.utils.NetWorkUtils.isNetworkConnected;

/**
 * Created by Yaozhong on 2015/1/19.
 */
public class DynamicTrainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TrainResultsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<RRdt> rRdtList = new ArrayList<>();

    private boolean isLoaded;
    private ProgressBar wheel;
    private String URL;
    private Boolean isJSON = true;
    private Boolean isZero = false;
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_dynamic_train, container, false);
        Toolbar mToolbar = (Toolbar)getActivity().findViewById(R.id.my_awesome_toolbar);
        wheel= (ProgressBar)rootView.findViewById(R.id.wheel);
        wheel.setPadding( wheel.getPaddingLeft(),  wheel.getPaddingTop(),
                wheel.getPaddingRight(),  wheel.getPaddingBottom()+mToolbar.getHeight()*2);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.train_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        URL = Constant.URL+Constant.TrainQueryServlet +"type=dynamic";

        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.teal,R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoaded){
                    refreshTrainData();
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "正在加载中", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if(isNetworkConnected(getActivity())){
            new ProgressTask().execute();
        }else {
            Toast.makeText(getActivity(), "请检查下您的网络状态", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }
    public void refreshTrainData(){
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
                                if(rRdtList!=null) {
                                    try {
                                        rRdtList.clear();
                                        rRdtList = JSON.parseArray(response, RRdt.class);
                                        isJSON = true;
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        isJSON = false;
                                    }
                                    if(isJSON){
                                        //trainTypePre(rRdtList);
                                        adapter = new TrainResultsAdapter(rRdtList, getActivity());
                                        msg.what=0;
                                        handler.obtainMessage(0, msg.obj).sendToTarget();
                                        if(rRdtList.size()==0){
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
                    Toast.makeText(getActivity(), "很抱歉，暂时没有符合您条件的车次", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "很抱歉， 解析JSON出错", Toast.LENGTH_SHORT).show();
                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
            return false;
        }
    });



//
//      private void trainTypePre(List<RRdt> trainList){
//           String commonTrainType = "GDTZK";
//        for(int i=0; i<trainList.size();i++){
//            String strTrainNoFirst = trainList.get(i).getArrive_train_no().charAt(0)+"";
//            if(!commonTrainType.contains(strTrainNoFirst)){
//                strTrainNoFirst="O";
//            }
//            if(!trainTypeName.contains(strTrainNoFirst)){
//                trainList.remove(i);
//                i--;
//            }
//        }
//    }



    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            wheel.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpConnectionUtil conn = new HttpConnectionUtil();
            conn.asyncConnect(URL, HttpConnectionUtil.HttpMethod.GET,
                    new HttpConnectionUtil.HttpConnectionCallback() {
                        @Override
                        public void execute(String response) {
                            if(rRdtList!=null) {
                                try {
                                    rRdtList = JSON.parseArray(response, RRdt.class);
                                    isJSON = true;
                                }catch (Exception e){
                                    e.printStackTrace();
                                    isJSON = false;
                                }
                                if(isJSON){
                                    //trainTypePre(rRdtList);
                                    adapter = new TrainResultsAdapter(rRdtList, getActivity());
                                    if(rRdtList.size()==0){
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
            wheel.setVisibility(View.INVISIBLE);
            mRecyclerView.setAdapter(adapter);
            isLoaded = true;
            if(isZero){
                Toast.makeText(getActivity(), "很抱歉，暂时没有符合您条件的车次", Toast.LENGTH_SHORT).show();
            }
            if(!isJSON){
                Toast.makeText(getActivity(), "很抱歉， 解析JSON出错", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }
}
