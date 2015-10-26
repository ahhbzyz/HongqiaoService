package com.fyp.hongqiaoservice.nearby;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.nearby.pojo.businesses;
import com.fyp.hongqiaoservice.nearby.pojo.BusinessesRoot;
import com.fyp.hongqiaoservice.utils.DemoApiTool;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import static com.fyp.hongqiaoservice.utils.NetWorkUtils.isNetworkConnected;

/**
 * Created by Administrator on 2015/2/17.
 */
public class BusinessesFragment extends Fragment {

    private BusinessesResultsAdapter adapter;
    private String apiUrl;
    private  Map<String, String> paramMap;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ObservableRecyclerView mRecyclerView;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public static  final String ARG_INITIAL_CONTENT= "ARG_INITIAL_CONTENT";
    private int page;
    private ProgressBar wheel;
    private TextView tvTip;
    private boolean isLoaded;
    private   List<businesses> businessesList;
    Handler handler;
    LinearLayoutManager mLayoutManager;
    private boolean listIsEmpty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiUrl = "http://api.dianping.com/v1/business/find_businesses";
        paramMap = new HashMap<>();
        paramMap.put("latitude", "31.200546");
        paramMap.put("longitude", "121.326997");
        paramMap.put("radius", "5000");
        paramMap.put("limit", "10");
        paramMap.put("sort", "7");
        paramMap.put("format", "json");
        Bundle args = getArguments();
        int i =  (int)args.get(ARG_INITIAL_CONTENT);
        switch (i) {
            case 0:
                paramMap.put("category", "美食");
                break;
            case 1:
                paramMap.put("category", "酒店");
                break;
            case 2:
                paramMap.put("category", "购物");
                break;
            case 3:
                paramMap.put("category", "生活服务");
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_businesses, container, false);


        page =1;
        businessesList = new ArrayList<>();

        Toolbar  mToolbar = (Toolbar)getActivity().findViewById(R.id.my_awesome_toolbar);
        wheel= (ProgressBar)rootView.findViewById(R.id.wheel);
        tvTip = (TextView)rootView.findViewById(R.id.tv_tip);
        tvTip.setVisibility(View.INVISIBLE);

        wheel.setPadding( wheel.getPaddingLeft(),  wheel.getPaddingTop(),
                wheel.getPaddingRight(),  wheel.getPaddingBottom()+mToolbar.getHeight()*2);

        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.teal,R.color.orange,R.color.green);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoaded){
                    pullDown();
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "正在加载中", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Activity parentActivity = getActivity();
        mRecyclerView = (ObservableRecyclerView)rootView.findViewById(R.id.food_recycler_view);
        mLayoutManager = new LinearLayoutManager(parentActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Fragment parentFragment = getParentFragment();
        mRecyclerView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentFragment);

        try{
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleItemCount = mRecyclerView.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if (totalItemCount > previousTotal + 1) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        if(isNetworkConnected(getActivity())){
                            page ++;
                            businessesList.add(null);
                            adapter.notifyItemInserted(businessesList.size());
                            pullUp();
                            loading = true;
                        }else {
                            Toast.makeText(getActivity(), "请检查下您的网络状态", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }




        handler = new Handler();
        if(isNetworkConnected(getActivity())){
            new ProgressTask().execute();
        }else {
            isLoaded = true;
            tvTip.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "请检查下您的网络状态", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }


    private class ProgressTask extends AsyncTask<Void, Void, BusinessesResultsAdapter> {
        @Override
        protected void onPreExecute(){
            wheel.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
        }

        @Override
        protected BusinessesResultsAdapter doInBackground(Void... arg0) {
            //my stuff is here
            String requestResult = DemoApiTool.requestApi(apiUrl, Constant.appKey, Constant.secret, paramMap);
            try{
                BusinessesRoot businessesRoot = JSON.parseObject(requestResult, BusinessesRoot.class);
                businessesList = businessesRoot.getBusinesses();
                adapter = new BusinessesResultsAdapter(businessesList,getActivity());
            }catch(Exception e){
                e.printStackTrace();
                listIsEmpty=true;
            }
            return adapter;
        }

        @Override
        protected void onPostExecute(BusinessesResultsAdapter result) {
            if(listIsEmpty){
                tvTip.setVisibility(View.VISIBLE);
            }
            mRecyclerView.setAdapter(result);
            wheel.setVisibility(View.INVISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            isLoaded = true;
        }
    }

    private void pullDown(){

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                previousTotal = 0;
                page = 1;
                paramMap.put("page", String.valueOf(page));
                String requestResult = DemoApiTool.requestApi(apiUrl, Constant.appKey, Constant.secret, paramMap);
                BusinessesRoot businessesRoot = null;
                businessesList.clear();
                try{
                    businessesRoot = JSON.parseObject(requestResult, BusinessesRoot.class);
                    businessesList = businessesRoot.getBusinesses();
                    adapter = new BusinessesResultsAdapter(businessesList,getActivity());
                }catch(Exception e){
                    e.printStackTrace();
                }
                Message msg = new Message();
                if(businessesRoot!=null){
                    msg.what=0;
                    handler_pullDown.obtainMessage(0, msg.obj).sendToTarget();
                }else{
                    msg.what=1;
                    handler_pullDown.obtainMessage(1, msg.obj).sendToTarget();
                }
            }
        };
        new Thread(mRunnable).start();
    }
    Handler handler_pullDown= new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage (Message msg) {
            switch(msg.what) {
                case 0:
                    tvTip.setVisibility(View.INVISIBLE);
                    mRecyclerView.setAdapter(adapter);
                    break;
                case 1:
                 //   tvTip.setVisibility(View.VISIBLE);
                    break;
            }
            loading = true;
            mSwipeRefreshLayout.setRefreshing(false);
            return false;
        }
    });

    private void pullUp(){
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                paramMap.put("page", String.valueOf(page));
                BusinessesRoot businessesRoot = null;
                try{
                     String newRequestResult = DemoApiTool.requestApi(apiUrl, Constant.appKey, Constant.secret, paramMap);
                     businessesRoot = JSON.parseObject(newRequestResult, BusinessesRoot.class);
                }catch(Exception e){
                    e.printStackTrace();
                    businessesList.remove(businessesList.size() - 1);
                    adapter.notifyItemRemoved(businessesList.size());
                    page--;
                }
                if(businessesRoot!=null){

                    businessesList.remove(businessesList.size() - 1);
                    try{
                        adapter.notifyItemRemoved(businessesList.size());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    int oldSize = businessesList.size();

                    int size = businessesRoot.getBusinesses().size();
                    for (int i = 0; i < size; i++) {
                        businessesList.add(businessesRoot.getBusinesses().get(i));
                        adapter.notifyItemInserted(oldSize+i);
                    }

                }else{
                    loading = false;
                }
            }
        };
        new Thread(mRunnable).start();
    }

}




