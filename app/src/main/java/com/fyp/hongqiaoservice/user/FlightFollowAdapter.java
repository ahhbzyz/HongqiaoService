package com.fyp.hongqiaoservice.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.pojo.FlightFollow;
import com.fyp.hongqiaoservice.query.pojo.TrainFollow;

import java.util.List;



/**
 * Created by Administrator on 2015/2/9.
 */
public class FlightFollowAdapter extends RecyclerView.Adapter<FlightFollowAdapter.ViewHolder> {


    OnItemClickListener mItemClickListener;
    private String phone;

    private ProgressDialog progressDialog;

    private List<String> flightFollowList;


    Context context;
    public FlightFollowAdapter(List<String> flightFollowList, Context context) {
        this.flightFollowList = flightFollowList;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.rvitem_train_follow, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        holder.tvTrainNo.setText(flightFollowList.get(position));

    }

    @Override
    public int getItemCount() {
        return flightFollowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTrainNo;


        public ViewHolder(View view) {
            super(view);
            tvTrainNo = (TextView) view.findViewById(R.id.tv_train_no);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
            progressDialog.show();
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }





}


