package com.fyp.hongqiaoservice.query.train;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fyp.hongqiaoservice.utils.DBHelper;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.utils.PingYinUtil;


import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by Yaozhong on 2015/1/29.
 */
public class TrainStationsActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private GridView gvTrainStations;
    private GridView gvHotStations;
    private GridView gvRecentStations;

    private TextView tvRecent;
    private TextView tvHot,tvNoRecord;

    private ArrayList<String> firstLettersList = new ArrayList<>();
    private ArrayList<String> stationNamesList = new ArrayList<>();

    private Filter filter;
    private String intent_message;
    private  SharedPreferences  spTrain;
    private  Editor editor;

    private String strRecentStations;
    private ArrayList<HashMap<String, Object>>  recentStationsList= new ArrayList<>();;
    private SimpleAdapter  recentStationsAdapter ;

    private LinearLayout layAllStations,layPartStations;

    private String[]  hotStationsArray =  new String[]{"上海","北京","天津","深圳","武汉",
            "广州","郑州","杭州","济南","南京","长春","昆明",
            "长沙","沈阳","成都","合肥","福州","太原"};
    private MaterialDialog itemDeletedDialog;
    private View cityView;
    private int cityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_stations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("城市列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置actionbar返回键

        spTrain = this.getSharedPreferences("SP_QUERY", Context.MODE_APPEND);
        editor = spTrain.edit();

        //获取intent
        Intent intent = getIntent();
        intent_message = intent.getStringExtra("message").trim();

        //textView
        tvRecent = (TextView)findViewById(R.id.tv_recent);
        tvHot = (TextView)findViewById(R.id.tv_hot);
        tvNoRecord = (TextView) findViewById(R.id.tv_noRecord);

        //两个gridView布局
        gvTrainStations = (GridView) findViewById(R.id.gv_train_stations);
        gvHotStations = (GridView)findViewById(R.id.gv_hot_stations);
        gvRecentStations = (GridView)findViewById(R.id.gv_recent_stations);


        layAllStations = (LinearLayout)findViewById(R.id.layout_all_stations);
        layPartStations = (LinearLayout)findViewById(R.id.layout_part_stations);

        loadDataBase();
        setRecentStations();
        setHotStations();
        setAllStations();

    }

    //读取数据库
    private void loadDataBase(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase mDataBase = dbHelper.openDataBase("shuniu.db");

        Cursor cursor =mDataBase.rawQuery("select sta_abb, sta_name from r_sta", null);
        while(cursor.moveToNext()) {
            String station  = parseXML(cursor.getString(1)) ;
            firstLettersList.add(cursor.getString(0));
            stationNamesList.add(station);
        }
        cursor.close();
        dbHelper.close();
    }
    //选择近期查询城市
    private void setRecentStations(){

        strRecentStations = spTrain.getString("recent_stations","");
        if(!strRecentStations.equals("")) {

            String recentStationsArray[] = strRecentStations.split(",");

            for (String station : recentStationsArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("station", station);
                recentStationsList.add(map);
            }
        }else{
            tvNoRecord.setText( "暂时还没有记录");
        }

            recentStationsAdapter = new SimpleAdapter(this,recentStationsList,
                    R.layout.gvitem_city, new String[]{"station"},
                    new int[]{R.id.tv_cityName});

            gvRecentStations.setAdapter(recentStationsAdapter);

            gvRecentStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                    String station = text.getText().toString();
                    returnStation(station);
                }
            });
        itemDeletedDialog =  new MaterialDialog.Builder(this)
                .title("提示")
                .content("删除该条路线吗？")
                .positiveText("确定")
                .negativeText("取消")
                .backgroundColorRes(R.color.white).callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        TextView text = (TextView) cityView.findViewById(R.id.tv_cityName);
                        String station = text.getText().toString();
                        removeStation(station);
                        recentStationsList.remove(cityItem);
                        recentStationsAdapter.notifyDataSetChanged();
                        if(recentStationsList.size()==0){
                            tvNoRecord.setText("暂时还没有记录");
                        }
                        dialog.dismiss();
                    }
                }).build();
        gvRecentStations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,  View view,  int i, long l) {
                cityView = view;
                cityItem = i;
                itemDeletedDialog.show();
                return false;
            }
        });

    }
    //选择热门城市
    private void setHotStations(){

        ArrayList<HashMap<String, Object>>  hotStationsList = new ArrayList<>();

        for(String station : hotStationsArray){
            HashMap<String, Object> map = new HashMap<>();
            map.put("hotStation",station);
            hotStationsList.add(map);
        }

        SimpleAdapter  hotStationsAdapter = new SimpleAdapter(this,hotStationsList,
                R.layout.gvitem_city, new String[]{"hotStation"},
                new int[]{R.id.tv_cityName});

        gvHotStations.setAdapter(hotStationsAdapter);

        gvHotStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                String station = text.getText().toString();
                returnStation(station);
            }
        });

    }
    //选择站点
    private void setAllStations(){

        ArrayList<HashMap<String, Object>>  stationsList = new ArrayList<>();

        for(int i = 0; i < firstLettersList.size();i++){
            HashMap<String, Object> map = new HashMap<>();
            map.put("firstLetter", firstLettersList.get(i));
            map.put("stationName", stationNamesList.get(i));
            stationsList.add(map);
        }

        SimpleAdapter stationsAdapter = new SimpleAdapter(this, stationsList,
                R.layout.gvitem_city,new String[] {"firstLetter","stationName"},
                new int[] {R.id.tv_cityAcronym,R.id.tv_cityName});
        gvTrainStations.setAdapter(stationsAdapter);

        gvTrainStations.setTextFilterEnabled(true);

        gvTrainStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                String station = text.getText().toString();
                returnStation(station);
            }
        });

        filter = stationsAdapter.getFilter();
    }
    //回传station
    private void returnStation(String station){
        saveStation(station);
        Intent intent = new Intent();
        switch (intent_message) {
            case "from":
                intent.putExtra("result", station);
                setResult(1002, intent);
                break;
            case "to":
                intent.putExtra("result", station);
                setResult(1003, intent);
                break;
            default:
                break;
        }
        finish();
    }
    //保存station
    private void saveStation(String station){
        //存入数据

        if(strRecentStations.contains(station)){
            strRecentStations = strRecentStations.replace(station+"," , "");
        }
        editor.putString("recent_stations",station+","+ strRecentStations);
        editor.apply();
    }
    //删除station
    private void removeStation(String station) {

        //存入数据
            strRecentStations = strRecentStations.replace(station + ",", "");
            editor.putString("recent_stations", strRecentStations);
            editor.apply();

    }
    @Override
    public boolean onQueryTextChange(String s) {

        if(!s.isEmpty()){
            filter.filter(s);
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    layAllStations.setVisibility(View.VISIBLE);
                }
            }, 100);   //
            layPartStations.setVisibility(View.INVISIBLE);


        }else{
            layAllStations.setVisibility(View.GONE);
            layPartStations.setVisibility(View.VISIBLE);
        }


        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_train_station, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("站点名称、首字母或拼音");
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        //searchView.setSubmitButtonEnabled(true);

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
    public  String parseXML(String xml ){
        String result = xml.substring(xml.indexOf("<zh_cn>")+7,xml.indexOf("</zh_cn>"));
        return  result;
    }


}
