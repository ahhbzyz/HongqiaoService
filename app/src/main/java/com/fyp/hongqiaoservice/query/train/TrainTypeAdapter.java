package com.fyp.hongqiaoservice.query.train;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.fyp.hongqiaoservice.R;
import java.util.HashMap;

/**
 * Created by Yaozhong on 2015/2/8.
 */
public class TrainTypeAdapter extends BaseAdapter {


    private String[]  trainTypeArray;
    private HashMap <Integer,Boolean> typeIsSelected;
    private LayoutInflater inflater = null;


    public TrainTypeAdapter(String[]  trainTypeArray,Context context) {

        this.trainTypeArray =trainTypeArray;

        inflater = LayoutInflater.from(context);

        typeIsSelected = new HashMap<>();

        for(int i=0; i<trainTypeArray.length;i++) {
            typeIsSelected.put(i,true);
        }
    }

    public HashMap<Integer, Boolean> getTypeIsSelected() {
        return typeIsSelected;
    }

    public void setTypeIsSelected(HashMap<Integer, Boolean> typeIsSelected) {
        this.typeIsSelected = typeIsSelected;
    }

    @Override
    public int getCount() {
        return trainTypeArray.length;
    }

    @Override
    public Object getItem(int i) {
        return trainTypeArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            view = inflater.inflate(R.layout.listitem_train_type,null);
            holder.tv = (TextView) view.findViewById(R.id.tv_train_type);
            holder.cb = (CheckBox) view.findViewById(R.id.cb_train_type);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final ViewHolder finalHolder = holder;
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeIsSelected.put(i, finalHolder.cb.isChecked());
            }
        });
        holder.tv.setText(trainTypeArray[i]);
        return view;
    }
    public  static class ViewHolder{
        CheckBox cb;
        TextView tv;
    }
}
