package com.fyp.hongqiaoservice.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.query.pojo.TrainFollow;

import java.util.List;


/**
 * Created by Administrator on 2015/2/9.
 */
public class TrainFollowAdapter extends RecyclerView.Adapter<TrainFollowAdapter.ViewHolder> {


    OnItemClickListener mItemClickListener;


    private List<String> trainFollowList;


    Context context;
    public TrainFollowAdapter(List<String> trainFollowList, Context context) {
        this.trainFollowList = trainFollowList;
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
        holder.tvTrainNo.setText(trainFollowList.get(position));

    }

    @Override
    public int getItemCount() {
        return trainFollowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressDialog progressDialog;
        private TextView tvTrainNo;
        private RelativeLayout layoutFollow;

        public ViewHolder(View view) {
            super(view);

            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("正在查询中...");


            tvTrainNo = (TextView) view.findViewById(R.id.tv_train_no);
            layoutFollow = (RelativeLayout) view.findViewById(R.id.layout_follow);
            layoutFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                }
            });
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


