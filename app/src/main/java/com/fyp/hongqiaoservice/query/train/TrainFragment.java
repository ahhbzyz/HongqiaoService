package com.fyp.hongqiaoservice.query.train;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.balysv.materialripple.MaterialRippleLayout;
import com.fyp.hongqiaoservice.main.MainActivity;
import com.fyp.hongqiaoservice.query.flight.FlightFragment;
import com.fyp.hongqiaoservice.query.flight.FlightResultsActivity;
import com.fyp.hongqiaoservice.utils.CalendarDialogViewHelper;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.train.TrainTypeAdapter.ViewHolder;



import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.fyp.hongqiaoservice.query.flight.FlightFragment.*;

/**
 * Created by Yaozhong on 2015/1/19.
 */
public class TrainFragment extends Fragment implements View.OnClickListener{


    private FrameLayout.LayoutParams params;
    private FrameLayout fl;

    private TextView tvFrom;
    private TextView tvTo;
    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvTrainType;
    private TextView tvNoRecord;

    private SharedPreferences spTrain;
    private Editor editor;


    private ArrayList<HashMap<String, Object>> commonRoutesList;
    private ListView lvRoutes;
    private SimpleAdapter routeSimpleAdapter;
    private SimpleAdapter noSimpleAdapter;

    private CalendarPickerView calendarDialogView;
    private CalendarDialogViewHelper calendarDialogViewHelper;

    private View itemView;
    private int item;
    private MaterialDialog itemDeletedDialog;

    private MaterialDialog trainTypeDialog;
    private ListView trainTypeDialogView;
    private String[]  trainTypeArray =  new String[]{"高铁 G","动车 D","特快 T","直达 Z","快速 K","其它"};
    private String[]  trainTypeArrayNew =  new String[]{"高铁","动车","特快","直达","快速","其它"};
    private String[]   trainTypeArrayShort=  new String[]{"G","D","T","Z","K","O"};
    private String trainTypeName ="GDTZKO";
    private String strAllTrainType="高铁,动车,特快,直达,快速,其它,";
    private View swapBtn;

    private MaterialDialog trainNoDialog;
    private  EditText trainNoInput;
    private TextView tvTrainNo;


    private ArrayList<HashMap<String, Object>> trainsNoList;
    private ListView lvTrainsNo;

    private String spTypeRoute ="train_route";
    private String spTypeNo ="train_no";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spTrain = getActivity().getSharedPreferences("SP_QUERY", Context.MODE_APPEND);
        editor = spTrain.edit();

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_train, container, false);

        //4个布局
        rootView.findViewById(R.id.ly_from).setOnClickListener(this);
        rootView.findViewById(R.id.ly_to).setOnClickListener(this);
        rootView.findViewById(R.id.rl_date).setOnClickListener(this);
        rootView.findViewById(R.id.rl_train_no).setOnClickListener(this);
        swapBtn = rootView.findViewById(R.id.ibtn_swap);
        swapBtn.setOnClickListener(this);
        View view = rootView.findViewById(R.id.btn_search);
        MaterialRippleLayout.on(view)
                .rippleColor(Color.BLACK)
                .rippleAlpha(0.2f)
                .rippleDuration(500)
                .rippleHover(true)
                .rippleOverlay(true)
                .create();
        view.setOnClickListener(this);

        commonRoutesList = new ArrayList<>();
        trainsNoList = new ArrayList<>();

        //textView
        tvFrom = (TextView) rootView.findViewById(R.id.tv_from);
        tvTo = (TextView) rootView.findViewById(R.id.tv_to);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        tvWeek = (TextView) rootView.findViewById(R.id.tv_week);
        tvTrainType = (TextView) rootView.findViewById(R.id.tv_train_type);
        tvNoRecord = (TextView) rootView.findViewById(R.id.tv_noRecord);
        tvTrainNo = (TextView) rootView.findViewById(R.id.ti_hint_train_no);

        trainNoDialog =   new MaterialDialog.Builder(getActivity())
                .title("请输入航班号")
                .customView(R.layout.edittext_flight_no,true)
                .backgroundColorRes(R.color.white)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        Intent intent = new Intent(getActivity(), TrainResultsActivity.class);
                        Bundle b = new Bundle();
                        b.putString("trainNo", trainNoInput.getText().toString().toUpperCase());
                        String dateName = tvDate.getText().toString();
                        b.putString("dateName",dateName);
                        intent.putExtras(b);
                        startActivity(intent);
                        dialog.dismiss();
                        saveRoute(spTypeNo, trainNoInput.getText().toString().toUpperCase());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

        trainNoInput = (EditText) trainNoDialog.getCustomView().findViewById(R.id.editText);
        trainNoInput.setHint(tvTrainNo.getText().toString());
        trainNoInput.setTransformationMethod(new FlightFragment.InputLowerToUpper());

        lvTrainsNo = (ListView) trainNoDialog.getCustomView().findViewById(R.id.lv_flight_no);


        //线路列表
        lvRoutes = (ListView) rootView.findViewById(R.id.lv_routes_history);

        //calendar dialog
        calendarDialogView = (CalendarPickerView) inflater.inflate(R.layout.dialog_calendar, null, false);
        calendarDialogViewHelper =new CalendarDialogViewHelper(getActivity(),calendarDialogView,tvDate,tvWeek);
        //trainType dialog
        trainTypeDialogView = (ListView) inflater.inflate(R.layout.dialog_train_type, null, false);
        commonRoutesList = new ArrayList<>();
        setCommonRoutesListView(spTypeRoute, lvRoutes, commonRoutesList);
        setCommonRoutesListView(spTypeNo, lvTrainsNo, trainsNoList);

        setRouteTextView();
        setTrainTypeDialogView();

          itemDeletedDialog =  new MaterialDialog.Builder(getActivity())
                .title("提示")
                .content("删除该条路线吗？")
                .positiveText("确定")
                .negativeText("取消")
                .backgroundColorRes(R.color.white)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        TextView text = (TextView)itemView.findViewById(R.id.tv_routeName);
                        String result = text.getText().toString();
                        if(result.contains(" → ")){
                            removeRoute(spTypeRoute,result);
                        }else {
                            removeRoute(spTypeNo,result);
                        }
                        dialog.dismiss();
                    }
                }).build();

        return rootView;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ly_from:
                Intent intent_from = new Intent(getActivity(), TrainStationsActivity.class);
                intent_from.putExtra("message", "from");
                startActivityForResult(intent_from, 1000);
                break;

            case R.id.ly_to:
                Intent intent_to = new Intent(getActivity(), TrainStationsActivity.class);
                intent_to.putExtra("message", "to");
                startActivityForResult(intent_to, 1001);
                break;

            case R.id.rl_date:

                calendarDialogViewHelper.getCalendarDialog().show();

                break;

            case R.id.rl_train_no:

                trainNoDialog.show();
                break;

            case R.id.ibtn_swap:
                String from =tvTo.getText().toString();
                String to =tvFrom.getText().toString();
                rotateBtn();
                swapPlace(from,to);
                break;

            case R.id.btn_search:

                //出发地，目的地名称，以及线路名称
                String  fromName = tvFrom.getText().toString();
                String  toName = tvTo.getText().toString();
                String  routeName = fromName + " → " + toName;
                String dateName = tvDate.getText().toString();

                Intent intent = new Intent(getActivity(), TrainResultsActivity.class);
                Bundle b = new Bundle();
                b.putString("fromName", fromName);
                b.putString("toName", toName);
                b.putString("dateName",dateName);
                b.putString("trainTypeName",trainTypeName);
                intent.putExtras(b);
                startActivity(intent);

                //线路历史更新
                saveRoute(spTypeRoute,routeName);


                break;
            default:
                break;
        }
    }

    private void setCommonRoutesListView(final String spType, ListView listView, ArrayList<HashMap<String, Object>> trainList){

        String strCommonRoutes = spTrain.getString(spType, "");

        if (!strCommonRoutes.equals("")) {
            String [] commonRoutesArray = strCommonRoutes.split(",");
            for (String route : commonRoutesArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(spType, route);
                trainList.add(map);
            }
        }

        if(spType.equals(spTypeRoute)){
            if(strCommonRoutes.equals("")){
                tvNoRecord.setText("暂时还没有记录");
            }
            routeSimpleAdapter = new SimpleAdapter(getActivity(),trainList,
                    R.layout.listitem_query_history, new String[]{spType},
                    new int[]{R.id.tv_routeName});
            listView.setAdapter(routeSimpleAdapter);
        }else{
            noSimpleAdapter = new SimpleAdapter(getActivity(), trainList,
                    R.layout.listitem_query_history, new String[]{spType},
                    new int[]{R.id.tv_routeName});
            listView.setAdapter(noSimpleAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text = (TextView) view.findViewById(R.id.tv_routeName);
                String result = text.getText().toString();
                if(result.contains(" → ")){
                    String route[] = result.split(" → ");
                    swapPlace(route[0],route[1]);
                }else{
                    trainNoInput.setText(result);
                    trainNoInput.setSelection(result.length());
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final  int i, long l) {
                itemView =view;
                item=i;
                itemDeletedDialog.show();
                return false;
            }
        });

    }
    private void setRouteTextView() {
        //读取上次查询的出发地和目的地
        String strCommonRoutes = spTrain.getString(spTypeRoute, "");
        if (!strCommonRoutes.equals("")) {
            String [] commonRoutesArray = strCommonRoutes.split(",");
            String route[] = commonRoutesArray[0].split(" → ");
            tvFrom.setText(route[0]);
            tvTo.setText(route[1]);
        }
    }
    private void setTrainTypeDialogView(){


        final TrainTypeAdapter trainTypeAdapter =new TrainTypeAdapter(trainTypeArray,getActivity());
        trainTypeDialogView.setDivider(null);
        trainTypeDialogView.setAdapter(trainTypeAdapter);

        trainTypeDialogView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.cb.toggle();
                trainTypeAdapter.getTypeIsSelected().put(i, holder.cb.isChecked());
            }
        });
         trainTypeDialog =   new MaterialDialog.Builder(getActivity())
                .title("请选择车型")
                .customView(trainTypeDialogView,false)
                 .backgroundColorRes(R.color.white)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        String strTrainType="";
                        trainTypeName="";
                        Set<Map.Entry<Integer, Boolean>> entrySet=trainTypeAdapter.getTypeIsSelected().entrySet();
                        for(Map.Entry<Integer, Boolean> entry: entrySet){
                            if(entry.getValue()){
                                strTrainType =  strTrainType+ trainTypeArrayNew[entry.getKey()]+",";
                                trainTypeName = trainTypeName + trainTypeArrayShort[entry.getKey()];
                            }
                        }

                        if(strTrainType.equals("")){
                            strTrainType = "请选择至少一种车型";
                        }else if(strTrainType.equals(strAllTrainType)){
                            strTrainType = "全部";
                        }else{
                            strTrainType = strTrainType.substring(0,strTrainType.length()-1);//去逗号
                        }
                        tvTrainType.setText(strTrainType);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1002:
                String result_from = data.getStringExtra("result");
                tvFrom.setText(result_from);
                break;
            case 1003:
                String result_to = data.getStringExtra("result");
                tvTo.setText(result_to);
                break;
            default:
                break;
        }
    }

    //路线操作
    private void removeRoute(String spType, String route) {
        String strCommonRoutes = spTrain.getString(spType, "");
        strCommonRoutes = strCommonRoutes.replace(route + ",", "");
        editor.putString(spType, strCommonRoutes);
        editor.apply();


        if (spType.equals(spTypeRoute)) {
            commonRoutesList.remove(item);
            routeSimpleAdapter.notifyDataSetChanged();

            if(commonRoutesList.size()==0){
                tvNoRecord.setText("暂时还没有记录");
            }
        } else {
            trainsNoList.remove(item);
            noSimpleAdapter.notifyDataSetChanged();
        }

    }
    private void saveRoute(String spType, String route) {


        String strCommonRoutes = spTrain.getString(spType, "");
        if (strCommonRoutes.contains(route)) {
            strCommonRoutes = strCommonRoutes.replace(route + ",", "");
        }
        editor.putString(spType, route + "," + strCommonRoutes);
        editor.apply();
        //重置路线list

        strCommonRoutes = spTrain.getString(spType, "");

        String[] commonRoutesArray = strCommonRoutes.split(",");


        if (spType.equals(spTypeRoute)) {
            tvNoRecord.setText("");
            commonRoutesList.clear();
            for (String newRoute : commonRoutesArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(spType, newRoute);
                commonRoutesList.add(map);
            }
            routeSimpleAdapter.notifyDataSetChanged();
        } else {
            trainsNoList.clear();
            for (String newRoute : commonRoutesArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(spType, newRoute);
                trainsNoList.add(map);
            }
            noSimpleAdapter.notifyDataSetChanged();
        }


    }
    private void rotateBtn(){

        ObjectAnimator animFrom = ObjectAnimator//
                .ofFloat(swapBtn, "rotation", 1.0F,  0.0F, 0.0F,  1.0F)//
                .setDuration(500);//
        animFrom.start();
        animFrom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (float) animation.getAnimatedValue();
                swapBtn.setAlpha(cVal);
                swapBtn.setScaleX(cVal);
                swapBtn.setScaleY(cVal);
            }
        });
    }
    private void swapPlace(final String from, final String to) {

        ObjectAnimator animFrom = ObjectAnimator//
                .ofFloat(tvFrom, "rotation", 1.0F,  0.0F, 0.0F,  1.0F)//
                .setDuration(500);//
        animFrom.start();
        animFrom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (float) animation.getAnimatedValue();
                tvFrom.setAlpha(cVal);
                tvFrom.setScaleX(cVal);
                tvFrom.setScaleY(cVal);
            }
        });

        ObjectAnimator animTo = ObjectAnimator//
                .ofFloat(tvTo, "rotation", 1.0F,  0.0F,0.0F,  1.0F)//
                .setDuration(500);//
        animTo.start();
        animTo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (Float) animation.getAnimatedValue();
                tvTo.setAlpha(cVal);
                tvTo.setScaleX(cVal);
                tvTo.setScaleY(cVal);
            }
        });
        new Handler().postDelayed(new Runnable(){
            public void run() {
                tvFrom.setText(from);
                tvTo.setText(to);
            }
        }, 250);

    }


}
