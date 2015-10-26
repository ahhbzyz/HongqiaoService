package com.fyp.hongqiaoservice.parking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.balysv.materialripple.MaterialRippleLayout;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.flight.FlightFragment;
import com.fyp.hongqiaoservice.query.pojo.RRdt;
import com.fyp.hongqiaoservice.query.train.TrainResultsActivity;
import com.fyp.hongqiaoservice.query.train.TrainResultsAdapter;
import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.fyp.hongqiaoservice.utils.NetWorkUtils.isNetworkConnected;


public class ParkingFragment extends Fragment implements View.OnClickListener ,  TimePickerDialog.OnTimeSetListener {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;

    public static final String TIMEPICKER_TAG = "timepicker";
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvParkingArea;
    private TextView tvParkingSpace;
    private ProgressDialog progressDialog;
    private MaterialDialog dateDialog;
    private DatePicker datePicker;
    private String  URL;
    private  Spinner s;
    private Boolean isJSON = null;
    private Boolean isZero = null;
    private  List<ParkingSpace> parkingSpaceList;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();


        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        timePickerDialog.setUserVisibleHint(true);
        sp = getActivity().getSharedPreferences("SP_PARKING", Context.MODE_APPEND);
        editor = sp.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_parking, container, false);
        rootView.findViewById(R.id.layout_date).setOnClickListener(this);
        rootView.findViewById(R.id.layout_time).setOnClickListener(this);

        View view = rootView.findViewById(R.id.btn_booking);
        MaterialRippleLayout.on(view)
                .rippleColor(Color.BLACK)
                .rippleAlpha(0.2f)
                .rippleDuration(500)
                .rippleHover(true)
                .rippleOverlay(true)
                .create();
        view.setOnClickListener(this);

        // Inflate the layout for this fragment

        tvDate = (TextView)rootView.findViewById(R.id.tv_date);
        tvTime = (TextView)rootView.findViewById(R.id.tv_time);
        tvParkingArea = (TextView)rootView.findViewById(R.id.tv_parking_area);
        tvParkingSpace = (TextView)rootView.findViewById(R.id.tv_parking_space);
        tvDate.setText(parseDate(calendar.getTime()));
        tvTime.setText(parseTime(calendar.getTime()));

         s = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.duration, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在为您随机分配车位中...");
        progressDialog.setCanceledOnTouchOutside(false);

        timePickerDialog.setStartTime(Integer.valueOf(tvTime.getText().toString().split(":")[0]),0);

        dateDialog =   new MaterialDialog.Builder(getActivity())
                .title("请选择日期")
                .customView(R.layout.dialog_datepicker, true)
                .backgroundColorRes(R.color.white)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        tvDate.setText(datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

        datePicker = (DatePicker) dateDialog.getCustomView().findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);


        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_date:

                dateDialog.show();
                break;
            case R.id.layout_time:
                timePickerDialog.setVibrate(true);
                timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
                break;
            case R.id.btn_booking:
                if(isNetworkConnected(getActivity())){
                    new ProgressTask().execute();
                }else {
                    Toast.makeText(getActivity(), "请检查下您的网络状态", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            URL = Constant.URL+Constant.BookingQueryServlet +"type=booking&start_time="+tvDate.getText().toString()+"%20"+toTime(tvTime.getText().toString())+"&duration="+s.getSelectedItem().toString();
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
                                    parkingSpaceList = JSON.parseArray(response, ParkingSpace.class);
                                    isJSON = true;
                                }catch (Exception e){
                                    e.printStackTrace();
                                    isJSON = false;
                                }
                                if(isJSON){
                                    if(parkingSpaceList.size()==0){
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
            progressDialog.dismiss();

            if(!isZero){
                String parkingSpace = parkingSpaceList.get(0).getParking_area() +","+parkingSpaceList.get(0).getParking_zone() + parkingSpaceList.get(0).getParking_space();
                for(int i=0; i<parkingSpaceList.size();i++){
                    tvParkingArea.setText(parkingSpaceList.get(i).getParking_area()+" 区 ");
                    tvParkingSpace.setText(parkingSpaceList.get(i).getParking_zone() + parkingSpaceList.get(i).getParking_space());
                }

                editor.putString("parkingSpace", parkingSpace);
                editor.apply();

            }else {
                Toast.makeText(getActivity(), "抱歉，该时段暂时没有车位", Toast.LENGTH_SHORT).show();
                tvParkingSpace.setText("");

            }

        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String min = null;
        if(minute ==0){
             min ="00";
        }
        tvTime.setText(hourOfDay + ":" + min);
        timePickerDialog.setStartTime(Integer.valueOf(tvTime.getText().toString().split(":")[0]),0);
    }

    private String toTime(String time){

        return time+":00";

    }

    public String parseTime(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:00");
        String result=sdf.format(time);
        return result;
    }
    public String parseDate(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String result=sdf.format(time);
        return result;
    }
}
