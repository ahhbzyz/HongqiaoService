package com.fyp.hongqiaoservice.query.flight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.utils.DBHelper;
import com.fyp.hongqiaoservice.utils.PingYinUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class FlightAirportsActivity extends ActionBarActivity  implements SearchView.OnQueryTextListener {

    private GridView gvFlightCities;
    private GridView gvHotCities;
    private GridView gvRecentCities;


    private TextView tvRecent;
    private TextView tvHot,tvNoRecord;

    private ArrayList<String> pinyinList = new ArrayList<>();
    private ArrayList<String> cityNamesList = new ArrayList<>();

    private SharedPreferences spFlight;
    private Filter filter;
    private String intent_message;

    private String strRecentCities;//近期查询的所有stations
    private ArrayList<HashMap<String, Object>>  recentCitiesList= new ArrayList<>();;
    private SimpleAdapter  recentCitiesAdapter ;

    private LinearLayout layAllCities,layPartCities;
    private MaterialDialog itemDeletedDialog;
    private View cityView;
    private int cityItem;

    private String[]  hotCitiesArray =  new String[]{"上海虹桥","北京首都","成都双流","厦门高崎","青岛流亭","广州白云","昆明长水","北京南苑",
            "呼和浩特","徐州观音","深圳宝安","上海浦东","宁夏中卫","杭州萧山","兰州中川","台北桃园","南京禄口","恩施湖北","榆林西沙",
            "柳州白莲","长沙黄花","天津滨海","乌鲁木齐","重庆江北","太原武宿","南昌昌北","沈阳桃仙","济宁曲阜","黄山屯溪",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_cities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("城市列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置actionbar返回键

        spFlight = this.getSharedPreferences("SP_QUERY", Context.MODE_APPEND);
        gvFlightCities = (GridView)findViewById(R.id.gv_flight_cities);
        gvHotCities = (GridView)findViewById(R.id.gv_hot_cities);
        gvRecentCities = (GridView) findViewById(R.id.gv_recent_cities);

        tvRecent = (TextView)findViewById(R.id.tv_recent);
        tvHot = (TextView)findViewById(R.id.tv_hot);
        tvNoRecord = (TextView) findViewById(R.id.tv_noRecord);

        Intent intent = getIntent();
        intent_message = intent.getStringExtra("message").trim();

        layAllCities = (LinearLayout)findViewById(R.id.layout_all_cities);
        layPartCities = (LinearLayout)findViewById(R.id.layout_part_cities);
        loadDataBase();
        setRecentCities();
        setHotCities();
        setAllCities();
    }

    private void loadDataBase(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase mDataBase = dbHelper.openDataBase("shuniu.db");
        Cursor cursor =mDataBase.rawQuery("select airport_abb, name_xml from f_3_airport", null);
        while(cursor.moveToNext()) {
            String airport  = parseXML(cursor.getString(1)) ;
            pinyinList.add(cursor.getString(0));
            cityNamesList.add(airport);
        }
        cursor.close();
        dbHelper.close();
    }
    private void returnStation(String city){
        saveCity(city);
        Intent intent = new Intent();
        switch (intent_message) {
            case "from":
                intent.putExtra("result", city);
                setResult(1002, intent);
                break;
            case "to":
                intent.putExtra("result", city);
                setResult(1003, intent);
                break;
            default:
                break;
        }
        finish();
    }
    private void saveCity(String city){
        //存入数据
        SharedPreferences.Editor editor = spFlight.edit();
        if(strRecentCities.contains(city)){
            strRecentCities = strRecentCities.replace(city+"," , "");
        }
        editor.putString("recent_cities",city+","+ strRecentCities);
        editor.apply();
    }
    private void removeCity(String city) {
        SharedPreferences.Editor editor = spFlight.edit();
        //存入数据
        if (strRecentCities.contains(city)) {
            strRecentCities = strRecentCities.replace(city + ",", "");
            editor.putString("recent_cities", strRecentCities);
            editor.apply();
        }
    }
    private void setRecentCities(){

        strRecentCities = spFlight.getString("recent_cities","");
        if(!strRecentCities.equals("")) {

            String recentCitiesArray[] = strRecentCities.split(",");

            for (String city : recentCitiesArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("city", city);
                recentCitiesList.add(map);
            }
        }else{
            tvNoRecord.setText( "暂时还没有记录");
        }

        recentCitiesAdapter = new SimpleAdapter(this,recentCitiesList,
                R.layout.gvitem_city, new String[]{"city"},
                new int[]{R.id.tv_cityName});

        gvRecentCities.setAdapter(recentCitiesAdapter);

        gvRecentCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                String city = text.getText().toString();
                returnStation(city);
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
                        removeCity(station);
                        recentCitiesList.remove(cityItem);
                        recentCitiesAdapter.notifyDataSetChanged();
                        if(recentCitiesList.size()==0){
                            tvNoRecord.setText("暂时还没有记录");
                        }
                        dialog.dismiss();
                    }
                }).build();
        gvRecentCities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,  View view,  int i, long l) {
                cityView = view;
                cityItem = i;
                itemDeletedDialog.show();
                return false;
            }
        });

    }
    private void setHotCities(){

        ArrayList<HashMap<String, Object>>  hotCitiesList = new ArrayList<>();

        for(String city : hotCitiesArray){
            HashMap<String, Object> map = new HashMap<>();
            map.put("hotCity",city);
            hotCitiesList.add(map);
        }

        SimpleAdapter  hotCitiesAdapter = new SimpleAdapter(this,hotCitiesList,
                R.layout.gvitem_city, new String[]{"hotCity"},
                new int[]{R.id.tv_cityName});

        gvHotCities.setAdapter(hotCitiesAdapter);

        gvHotCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                String city = text.getText().toString();
                returnStation(city);
            }
        });

    }
    private void setAllCities(){

        ArrayList<HashMap<String, Object>>  airportsList = new ArrayList<>();

        for(int i = 0; i < pinyinList.size();i++){
            HashMap<String, Object> map = new HashMap<>();
            map.put("pinyin", pinyinList.get(i));
            map.put("cityName", cityNamesList.get(i));
            airportsList.add(map);
        }

        SimpleAdapter airportsAdapter = new SimpleAdapter(this, airportsList,
                R.layout.gvitem_city,new String[] {"pinyin","cityName"},
                new int[] {R.id.tv_cityAcronym,R.id.tv_cityName});
        gvFlightCities.setAdapter(airportsAdapter);

        gvFlightCities.setTextFilterEnabled(true);

        gvFlightCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text=(TextView)view.findViewById(R.id.tv_cityName);
                String city = text.getText().toString();
                returnStation(city);
            }
        });
        filter = airportsAdapter.getFilter();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_flight_airports, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("站点名称、首字母或拼音");
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
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
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {

        if(!s.isEmpty()){
            filter.filter(s);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    layAllCities.setVisibility(View.VISIBLE);
                }
            }, 100);   //
            layPartCities.setVisibility(View.INVISIBLE);


        }else{
            layAllCities.setVisibility(View.GONE);
            layPartCities.setVisibility(View.VISIBLE);
        }
        return false;
    }
    public  String parseXML(String xml ){
        String result = xml.substring(xml.indexOf("<zh_cn>")+7,xml.indexOf("</zh_cn>"));
        return  result;
    }
}
