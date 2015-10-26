package com.fyp.hongqiaoservice.query.flight;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.balysv.materialripple.MaterialRippleLayout;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.utils.CalendarDialogViewHelper;

import com.fyp.hongqiaoservice.utils.NetWorkUtils;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yaozhong on 2015/1/19.
 */
public class FlightFragment extends Fragment  implements View.OnClickListener  {

    private FrameLayout.LayoutParams params;
    private FrameLayout fl;
    private TextView tvFrom;
    private TextView tvTo;
    private TextView tvDate;
    private TextView tvWeek,tvFlightNo,tvNoRecord;

    private  RelativeLayout rlFlightNo;

    private  EditText flightNoInput ;
    private View swapBtn;

    private SharedPreferences spFlight;
    private SharedPreferences.Editor editor;


    private View itemView;
    private int item;
    private MaterialDialog itemDeletedDialog;

    private CalendarPickerView calendarDialogView;
    private CalendarDialogViewHelper calendarDialogViewHelper;
    private MaterialDialog flightNoDialog;



    private ArrayList<HashMap<String, Object>> commonFlightsList;
    private ListView lvFlights;

   private ArrayList<HashMap<String, Object>> flightsNoList;
    private ListView lvFlightsNo;

    private SimpleAdapter routeSimpleAdapter;
    private SimpleAdapter noSimpleAdapter;

   private String spTypeRoute ="flight_route";
    private String spTypeNo ="flight_no";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spFlight = getActivity().getSharedPreferences("SP_QUERY", Context.MODE_APPEND);
        editor = spFlight.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.fragment_flight, container, false);

        //4个布局
       rootView.findViewById(R.id.ly_from).setOnClickListener(this);
       rootView.findViewById(R.id.ly_to).setOnClickListener(this);
       rootView.findViewById(R.id.rl_date).setOnClickListener(this);
       rootView.findViewById(R.id.rl_flight_no).setOnClickListener(this);

        //搜素按钮
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



        tvFrom = (TextView) rootView.findViewById(R.id.tv_from);
        tvTo = (TextView) rootView.findViewById(R.id.tv_to);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        tvWeek = (TextView) rootView.findViewById(R.id.tv_week);
        tvFlightNo = (TextView) rootView.findViewById(R.id.ti_hint_flight_no);
        tvNoRecord = (TextView) rootView.findViewById(R.id.tv_noRecord);
        lvFlights = (ListView) rootView.findViewById(R.id.lv_flight_history);

        calendarDialogView = (CalendarPickerView) inflater.inflate(R.layout.dialog_calendar, null, false);
        calendarDialogViewHelper =new CalendarDialogViewHelper(getActivity(),calendarDialogView,tvDate,tvWeek);

        commonFlightsList = new ArrayList<>();
        flightsNoList = new ArrayList<>();

        flightNoDialog =   new MaterialDialog.Builder(getActivity())
                .title("请输入航班号")
                .customView(R.layout.edittext_flight_no,true)
                .backgroundColorRes(R.color.white)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        Intent intent = new Intent(getActivity(), FlightResultsActivity.class);
                        Bundle b = new Bundle();
                        b.putString("flightNo", flightNoInput.getText().toString().toUpperCase());
                        String dateName = tvDate.getText().toString();
                        b.putString("dateName",dateName);
                        intent.putExtras(b);
                        startActivity(intent);
                        dialog.dismiss();
                        saveFlight(spTypeNo, flightNoInput.getText().toString().toUpperCase());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

         flightNoInput = (EditText) flightNoDialog.getCustomView().findViewById(R.id.editText);
         flightNoInput.setHint(tvFlightNo.getText().toString());
         flightNoInput.setTransformationMethod(new InputLowerToUpper());

        lvFlightsNo = (ListView) flightNoDialog.getCustomView().findViewById(R.id.lv_flight_no);

        setFlightsHistory(spTypeRoute, lvFlights, commonFlightsList);
        setFlightsHistory(spTypeNo, lvFlightsNo, flightsNoList);
        setFlightTextView();



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
                            removeFlight(spTypeRoute,result);
                        }else {
                            removeFlight(spTypeNo,result);
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
                Intent intent_from = new Intent(getActivity(), FlightAirportsActivity.class);
                intent_from.putExtra("message", "from");
                startActivityForResult(intent_from, 1000);

                break;

            case R.id.ly_to:
                Intent intent_to = new Intent(getActivity(), FlightAirportsActivity.class);
                intent_to.putExtra("message", "to");
                startActivityForResult(intent_to, 1001);


                break;

            case R.id.ibtn_swap:

                String from =tvTo.getText().toString();
                String to =tvFrom.getText().toString();
                rotateBtn();
                swapPlace(from,to);
                break;

            case R.id.rl_date:
                calendarDialogViewHelper.getCalendarDialog().show();

                break;

            case R.id.rl_flight_no:
                flightNoDialog.show();
                break;
            case R.id.btn_search:
                //出发地，目的地名称，以及线路名称
                String  fromName = tvFrom.getText().toString();
                String  toName = tvTo.getText().toString();
                String  flightName = fromName + " → " + toName;



                String dateName = tvDate.getText().toString();

                Intent intent = new Intent(getActivity(), FlightResultsActivity.class);
                Bundle b = new Bundle();



                b.putString("fromName", fromName);
                b.putString("toName", toName);


                b.putString("dateName",dateName);

                intent.putExtras(b);
                if(NetWorkUtils.isNetworkConnected(getActivity())){
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请检查下您的网络状态", Toast.LENGTH_LONG).show();
                }


                saveFlight(spTypeRoute,flightName);

                break;
            default:
                break;
        }
    }



    private void setFlightsHistory (final String spType, ListView listView, ArrayList<HashMap<String, Object>> flightList) {

        String flightHistory = spFlight.getString(spType, "");

        if (!flightHistory.equals("")) {
            String[] flightArray = flightHistory.split(",");
            for (String newFlight : flightArray) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(spType, newFlight);
                flightList.add(map);
            }
        }

        if(spType.equals(spTypeRoute)){
            if(flightHistory.equals("")){
                tvNoRecord.setText("暂时还没有记录");
            }
            routeSimpleAdapter = new SimpleAdapter(getActivity(),flightList,
                    R.layout.listitem_query_history, new String[]{spType},
                    new int[]{R.id.tv_routeName});
            listView.setAdapter(routeSimpleAdapter);
        }else{
            noSimpleAdapter = new SimpleAdapter(getActivity(), flightList,
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
                    flightNoInput.setText(result);
                    flightNoInput.setSelection(result.length());
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                itemView = view;
                item = i;
                itemDeletedDialog.show();
                return false;
            }
        });

    }
    private void setFlightTextView() {
        //读取上次查询的出发地和目的地
        String flightHistory = spFlight.getString(spTypeRoute, "");
        if (!flightHistory.equals("")) {
               String[] flightArray = flightHistory.split(",");
                String route[] = flightArray[0].split(" → ");
                tvFrom.setText(route[0]);
                tvTo.setText(route[1]);
        }

    }
    private void saveFlight(String spType,String flight) {
        //存入数据

        String flightHistory = spFlight.getString(spType, "");
        if (flightHistory.contains(flight)) {
            flightHistory = flightHistory.replace(flight + ",", "");
        }
        editor.putString(spType, flight + "," + flightHistory);
        editor.apply();
        //重置路线list

        flightHistory = spFlight.getString(spType, "");

            String[] flightArray = flightHistory.split(",");


            if (spType.equals(spTypeRoute)) {
                tvNoRecord.setText("");
                commonFlightsList.clear();
                for (String newFlight : flightArray) {
                    HashMap<String, Object>  map = new HashMap<>();
                    map.put(spType, newFlight);
                    commonFlightsList.add(map);
                }
                routeSimpleAdapter.notifyDataSetChanged();
            } else {
                flightsNoList.clear();
                for (String newFlight : flightArray) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(spType, newFlight);
                    flightsNoList.add(map);
                }
                noSimpleAdapter.notifyDataSetChanged();
            }



    }
    private void removeFlight(String  spType,String flight ) {
        //存入数据

        String flightHistory = spFlight.getString(spType, "");
        flightHistory = flightHistory.replace(flight + ",", "");
            editor.putString(spType, flightHistory);
            editor.apply();

        if (spType.equals(spTypeRoute)) {
            commonFlightsList.remove(item);
            routeSimpleAdapter.notifyDataSetChanged();

            if(commonFlightsList.size()==0){
                tvNoRecord.setText("暂时还没有记录");
            }
        } else {
            flightsNoList.remove(item);
            noSimpleAdapter.notifyDataSetChanged();
        }


    }

    private void rotateBtn() {
        ObjectAnimator//
                .ofFloat(swapBtn, "rotation", 0.0F, 360.0F)//
                .setDuration(500)//
                .start();
    }

    private void swapPlace(final String from, final String to) {

        ObjectAnimator animFrom = ObjectAnimator//
                .ofFloat(tvFrom, "rotation", 1.0F, 0.0F, 0.0F, 1.0F)//
                .setDuration(500);//
        animFrom.start();
        animFrom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                tvFrom.setAlpha(cVal);
                tvFrom.setScaleX(cVal);
                tvFrom.setScaleY(cVal);
            }
        });

        ObjectAnimator animTo = ObjectAnimator//
                .ofFloat(tvTo, "rotation", 1.0F, 0.0F, 0.0F, 1.0F)//
                .setDuration(500);//
        animTo.start();
        animTo.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                tvTo.setAlpha(cVal);
                tvTo.setScaleX(cVal);
                tvTo.setScaleY(cVal);
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                tvFrom.setText(from);
                tvTo.setText(to);
            }
        }, 250);
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

   public static class InputLowerToUpper extends ReplacementTransformationMethod {
       @Override
       protected char[] getOriginal() {
           char[] lower = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
           return lower;
       }

       @Override
       protected char[] getReplacement() {
           char[] upper = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
           return upper;
       }


   }

}
