package com.fyp.hongqiaoservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.main.MainActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yaozhong on 2015/2/13.
 */
public class CalendarDialogViewHelper extends FragmentActivity {

    MaterialDialog dialog;
    private Date currentDate = new Date();
     Context context;



    public CalendarDialogViewHelper(Context context, final CalendarPickerView calendarDialogView, final TextView tvDate, final TextView tvWeek) {

       this.context = context;

        Calendar NextMonth = Calendar.getInstance();
        NextMonth.add(Calendar.MONTH, 4);
        calendarDialogView.init(new Date(), NextMonth.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());

        dialog =  new MaterialDialog.Builder(context)
                .title("请选择日期")
                .customView(calendarDialogView, false)
                .negativeText("取消")
                .positiveText("确定")
                .backgroundColorRes(R.color.white)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();

                  }
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();

                    }
                }).build();

        calendarDialogView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                tvDate.setText(getStringDateFormat(date));
                tvWeek.setText(getStringWeekFormat(date));
                dialog.dismiss();
            }
            @Override
            public void onDateUnselected(Date date) {

            }
        });

        tvDate.setText(getStringDateFormat(currentDate));
        tvWeek.setText(getStringWeekFormat(currentDate));

    }

    public AlertDialog getCalendarDialog() {
        return dialog;
    }

    //转换日期格式
    public  String getStringDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    public  String getStringWeekFormat(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK)-1;
        String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
        if(day <0)day=0;
        String week = dayNames[day];
        if(getStringDateFormat(date).equals(getStringDateFormat(currentDate))){
            week="今天";
        }
        return week;
    }


}
