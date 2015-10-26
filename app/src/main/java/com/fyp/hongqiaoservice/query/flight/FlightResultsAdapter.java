package com.fyp.hongqiaoservice.query.flight;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.fyp.hongqiaoservice.query.pojo.F1DynFlight;
import com.fyp.hongqiaoservice.query.pojo.Flight;
import com.fyp.hongqiaoservice.utils.Constant;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/2/9.
 */
public class FlightResultsAdapter extends RecyclerView.Adapter<FlightResultsAdapter.ViewHolder> {
    private List<F1DynFlight> f1DynFlightList;

    OnItemClickListener mItemClickListener;

    private String strFlightFollow;



    private SharedPreferences sp;


    Context context;
    public FlightResultsAdapter(List<F1DynFlight> f1DynFlightList, Context context) {
        this.f1DynFlightList = f1DynFlightList;
        this.context = context;

        sp = context.getSharedPreferences("SP_FOLLOW", Context.MODE_APPEND);
        strFlightFollow = sp.getString("flight_follow", "");

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.rvitem_flight_result, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , int position) {

        holder.tvComplany.setText(parseXML(f1DynFlightList.get(position).getAirline_iata().getName_xml()).replaceAll("公司","").replaceAll("有限",""));

        holder.tvName.setText(f1DynFlightList.get(position).getFlight_no());

        holder.tvStatus.setText(parseXML(f1DynFlightList.get(position).getRecent_abnormal_status().getName_xml()));

        holder.tvRemark.setText(f1DynFlightList.get(position).getRecent_abnormal_status().getDescription());

        //出发到达机场
        holder.tvDepAirport.setText(parseXML(f1DynFlightList.get(position).getOrigin_airport_iata().getName_xml()));
        holder.tvArrAirport.setText(parseXML(f1DynFlightList.get(position).getDest_airport_iata().getName_xml()));


        //计划起飞
        if(f1DynFlightList.get(position).getStd() ==null){
            holder.tvDepTime.setText("-");
        }else {
            holder.tvDepTime.setText(parseTime(f1DynFlightList.get(position).getStd()));
        }

        //计划落地
        if(f1DynFlightList.get(position).getSta() ==null){
            holder.tvArrTime.setText("-");
        }else {
            holder.tvArrTime.setText(parseTime(f1DynFlightList.get(position).getSta()));
        }

        //预计起飞
        if(f1DynFlightList.get(position).getEtd() ==null){
            holder.tvDexpected.setText("-");
        }else {
            holder.tvDexpected.setText(parseTime(f1DynFlightList.get(position).getEtd()));
        }

        //预计落地
        if(f1DynFlightList.get(position).getEta() ==null){
            holder.tvAexpected.setText("-");
        }else {
            holder.tvAexpected.setText(parseTime(f1DynFlightList.get(position).getEta()));
        }

        //实际起飞
        if(f1DynFlightList.get(position).getAtd() ==null){
            holder.tvDactual.setText("-");
        }else {
            holder.tvDactual.setText(parseTime(f1DynFlightList.get(position).getAtd()));
        }

        //实际落地
        if(f1DynFlightList.get(position).getAta() ==null){
            holder.tvAactual.setText("-");
        }else {
            holder.tvAactual.setText(parseTime(f1DynFlightList.get(position).getAta()));
        }


        if(strFlightFollow.contains("'"+f1DynFlightList.get(position).getFlight_no()+"'")){
            holder.more.setImageResource(R.drawable.done);
        }else{
            holder.more.setImageResource(R.drawable.add);
        }

       final String  flightNo=f1DynFlightList.get(position).getFlight_no();

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.more.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.add).getConstantState()) {
                    String followList = sp.getString("flight_follow", "");
                    if (followList.contains(flightNo)) {
                        followList = followList.replace(flightNo + ",", "");
                    }
                    sp.edit().putString("flight_follow", flightNo + ","+followList).apply();
                    Toast.makeText(context, "关注 "+flightNo+" 成功", Toast.LENGTH_SHORT).show();
                    holder.more.setImageResource(R.drawable.done);
                } else {
                    String followList = sp.getString("flight_follow", "");
                    followList = followList.replace(flightNo + ",", "");
                    sp.edit().putString("flight_follow", followList).apply();
                    holder.more.setImageResource(R.drawable.add);
                    Toast.makeText(context, "取消关注 "+ flightNo, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(f1DynFlightList!=null){
            return f1DynFlightList.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvComplany, tvFlyTime,tvStatus, tvOnTimeRate,
                 tvDepTime,tvArrTime,  tvDepAirport,tvArrAirport,tvDepTerminal,tvArrTerminal,
                tvDexpected,tvAexpected,tvDactual,tvAactual,tvRemark;
        ImageButton more;



        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvComplany = (TextView) view.findViewById(R.id.tv_complany);

            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            tvRemark = (TextView) view.findViewById(R.id.tv_remark);

            tvDepTime = (TextView) view.findViewById(R.id.tv_DepTime);
            tvArrTime = (TextView) view.findViewById(R.id.tv_ArrTime);
            tvDepAirport = (TextView) view.findViewById(R.id.tv_DepAirport);
            tvArrAirport = (TextView) view.findViewById(R.id.tv_ArrAirport);


            tvDexpected = (TextView) view.findViewById(R.id.tv_Dexpected);
            tvAexpected = (TextView) view.findViewById(R.id.tv_Aexpected);
            tvDactual = (TextView) view.findViewById(R.id.tv_Dactual);
            tvAactual = (TextView) view.findViewById(R.id.tv_Aactual);

            more = (ImageButton)view.findViewById(R.id.img_more);

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
        public void onItemClick(View view , int position);
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

