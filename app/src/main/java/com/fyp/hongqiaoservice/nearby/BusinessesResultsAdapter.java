package com.fyp.hongqiaoservice.nearby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.nearby.pojo.businesses;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by Administrator on 2015/2/17.
 */
public class BusinessesResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List <businesses> businessesList;
    private Context context;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(null)
            .showImageOnFail(null)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName,tvCategories,tvPrice,tvReviewCount;
        RatingBar ratingBar;
        ImageView imageView;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
        tvName= (TextView) view.findViewById(R.id.tv_name);
        tvCategories   = (TextView) view.findViewById(R.id.tv_categories);
        tvPrice  = (TextView) view.findViewById(R.id.tv_avg_price);
        tvReviewCount= (TextView) view.findViewById(R.id.tv_review_count);
            layout= (LinearLayout) view.findViewById(R.id.lr_business);

        ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(view.getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_ATOP);
            //显示图片的配置

            view.setOnClickListener(this);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BusinessesInfoActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("url",businessesList.get(getPosition()).getBusiness_url());
                    bundle.putString("title", businessesList.get(getPosition()).getName());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View v) {
            super(v);

        }
    }

    public BusinessesResultsAdapter(List<businesses> businessesList, Context context){
        this.businessesList = businessesList;
        this.context = context;
        //创建默认的ImageLoader配置参数

    }

    @Override
    public int getItemViewType(int position) {
        return businessesList.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType==VIEW_ITEM) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rvitem_businesses_result, parent, false);

            vh = new ViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_footer, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).imageView.setImageDrawable(null);
            ((ViewHolder)holder).tvName.setText(businessesList.get(position).getName()
                    .replace("(这是一条测试商户数据，仅用于测试开发，开发完成后请申请正式数据...)",""));
            ImageLoader.getInstance().displayImage(businessesList.get(position).getS_photo_url(),  ((ViewHolder)holder).imageView, options);
            ((ViewHolder)holder).tvPrice.setText("￥"+businessesList.get(position).getAvg_price()+position);
            ((ViewHolder)holder).ratingBar.setRating(businessesList.get(position).getAvg_rating()+3);
            ((ViewHolder)holder).tvReviewCount.setText(businessesList.get(position).getReview_count()+3+"条");

            String categories ="";
            for(int i =0; i<businessesList.get(position).getCategories().size();i++){
                categories=categories+" "+businessesList.get(position).getCategories().get(i);
            }
            ((ViewHolder)holder).tvCategories.setText(categories.substring(1));

        }else{

        }
    }

    @Override
    public int getItemCount() {
        if(businessesList!=null){
            return businessesList.size();
        }else {
            return 0;
        }

    }
}

