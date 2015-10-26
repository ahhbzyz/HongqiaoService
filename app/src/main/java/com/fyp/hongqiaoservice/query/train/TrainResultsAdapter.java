package com.fyp.hongqiaoservice.query.train;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import com.fyp.hongqiaoservice.R;

import com.fyp.hongqiaoservice.query.pojo.RRdt;
import com.fyp.hongqiaoservice.query.pojo.Train;

import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Yaozhong on 2015/1/29.
 */

public class TrainResultsAdapter extends RecyclerView.Adapter<TrainResultsAdapter.ViewHolder> {


    private List<RRdt> rRdtList;
    OnItemClickListener mItemClickListener;
    Context context;
    private String strTrainFollow;
    private SharedPreferences sp;


    public TrainResultsAdapter(List<RRdt> rRdtList, Context context) {
        this.rRdtList = rRdtList;
        this.context = context;
        sp = context.getSharedPreferences("SP_FOLLOW", Context.MODE_APPEND);
        strTrainFollow = sp.getString("train_follow", "");


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.rvitem_train_result, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvTrainNo.setText(rRdtList.get(position).getArrive_train_no());
      //  holder.tvLishi.setText(trainList.get(position).getLishi());

        holder.tvCheckTime.setText(parseTime(rRdtList.get(position).getCheck_time()));
        holder.tvStopTIme.setText(parseTime(rRdtList.get(position).getStop_time()));

//        holder.tvFrom.setText(parseXML(rRdtList.get(position).getStartstation().getSta_name()));
//        holder.tvTo.setText(parseXML(rRdtList.get(position).getTerminalstation().getSta_name()));

        holder.tvStatus.setText(rRdtList.get(position).getAbnormalstatus());

        if (strTrainFollow.contains(rRdtList.get(position).getArrive_train_no())) {
            holder.more.setImageResource(R.drawable.done);
        } else {
            holder.more.setImageResource(R.drawable.add);
        }


//        if ((trainList.get(position).getTrain_no()).contains("G") ||
//                (trainList.get(position).getTrain_no()).contains("D")) {
//
//            holder.tiSeat1.setText("商务座");
//            holder.tiSeat2.setText("一等座");
//            holder.tiSeat3.setText("二等座");
//            holder.tiSeat4.setText("无座");
//
//            holder.tvSeat1.setText(trainList.get(position).getSwz_num().replace("--", "无"));
//            holder.tvSeat2.setText(trainList.get(position).getZy_num().replace("--", "无"));
//            holder.tvSeat3.setText(trainList.get(position).getZe_num().replace("--", "无"));
//            holder.tvSeat4.setText(trainList.get(position).getWz_num().replace("--", "无"));
//
//        } else if ((trainList.get(position).getTrain_no()).contains("T")) {
//
//            holder.tiSeat1.setText("硬卧");
//            holder.tiSeat2.setText("软座");
//            holder.tiSeat3.setText("硬座");
//            holder.tiSeat4.setText("无座");
//
//            holder.tvSeat1.setText(trainList.get(position).getYw_num().replace("--", "无"));
//            holder.tvSeat2.setText(trainList.get(position).getRz_num().replace("--", "无"));
//            holder.tvSeat3.setText(trainList.get(position).getYz_num().replace("--", "无"));
//            holder.tvSeat4.setText(trainList.get(position).getWz_num().replace("--", "无"));
//        } else {
//            holder.tiSeat1.setText("软卧");
//            holder.tiSeat2.setText("硬卧");
//            holder.tiSeat3.setText("硬座");
//            holder.tiSeat4.setText("无座");
//
//            holder.tvSeat1.setText(trainList.get(position).getRw_num().replace("--", "无"));
//            holder.tvSeat2.setText(trainList.get(position).getYw_num().replace("--", "无"));
//            holder.tvSeat3.setText(trainList.get(position).getYz_num().replace("--", "无"));
//            holder.tvSeat4.setText(trainList.get(position).getWz_num().replace("--", "无"));
//        }

        final String trainNo = rRdtList.get(position).getArrive_train_no();
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.more.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.add).getConstantState()) {
                    String followList = sp.getString("train_follow", "");
                    if (followList.contains(trainNo)) {
                        followList = followList.replace(trainNo + ",", "");
                    }
                    sp.edit().putString("train_follow", trainNo + ","+followList).apply();
                    Toast.makeText(context, "关注 "+trainNo+" 成功", Toast.LENGTH_SHORT).show();
                    holder.more.setImageResource(R.drawable.done);
                } else {
                    String followList = sp.getString("train_follow", "");
                    followList = followList.replace(trainNo + ",", "");
                    sp.edit().putString("train_follow", followList).apply();
                    holder.more.setImageResource(R.drawable.add);
                    Toast.makeText(context, "取消关注 "+ trainNo, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(rRdtList!=null){
            return rRdtList.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTrainNo, tvLishi, tvCheckTime, tvStopTIme, tvFrom, tvTo,
                tvSeat1, tvSeat2, tvSeat3, tvSeat4,tvStatus,
                tiSeat1, tiSeat2, tiSeat3, tiSeat4;
        ImageButton more;

        public ViewHolder(View view) {
            super(view);
            tvTrainNo = (TextView) view.findViewById(R.id.tv_train_no);
        //    tvLishi = (TextView) view.findViewById(R.id.tv_lishi);

            tvStatus = (TextView) view.findViewById(R.id.tv_status);

            tvCheckTime = (TextView) view.findViewById(R.id.tv_check_time);
            tvStopTIme = (TextView) view.findViewById(R.id.tv_stop_time);
//            tvFrom = (TextView) view.findViewById(R.id.tv_from_station_name);
//            tvTo = (TextView) view.findViewById(R.id.tv_to_station_name);

//            tvSeat1 = (TextView) view.findViewById(R.id.tv_seat_class1);
//            tvSeat2 = (TextView) view.findViewById(R.id.tv_seat_class2);
//            tvSeat3 = (TextView) view.findViewById(R.id.tv_seat_class3);
//            tvSeat4 = (TextView) view.findViewById(R.id.tv_seat_class4);
//
//            tiSeat1 = (TextView) view.findViewById(R.id.ti_seat_class1);
//            tiSeat2 = (TextView) view.findViewById(R.id.ti_seat_class2);
//            tiSeat3 = (TextView) view.findViewById(R.id.ti_seat_class3);
//            tiSeat4 = (TextView) view.findViewById(R.id.ti_seat_class4);

            more = (ImageButton) view.findViewById(R.id.img_more);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;

    }

    public String parseTime(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String result=sdf.format(time);
        return result;
    }
    public  String parseXML(String xml ){
        String result = xml.substring(xml.indexOf("<zh_cn>")+7,xml.indexOf("</zh_cn>"));
        return  result;
    }
}
