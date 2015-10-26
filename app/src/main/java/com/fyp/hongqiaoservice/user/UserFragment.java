package com.fyp.hongqiaoservice.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.fyp.hongqiaoservice.R;
import com.fyp.hongqiaoservice.user.pojo.User;
import com.fyp.hongqiaoservice.utils.HttpConnectionUtil;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;


public class UserFragment extends Fragment implements View.OnClickListener {

   private  MaterialDialog dialog;
   private EditText phoneNoInput;
   private EditText passwordInput;

    private LinearLayout layoutUser;
    private RelativeLayout layoutLogin;
    private String phone;
    private  String password;
    private  String username;
    private Boolean loginStatus;
    private SharedPreferences sp;
    private User  loginUser= null;
    private Toolbar mToolbar;
    private ProgressDialog progressDialog;
    private MaterialDialog parkingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        // Inflate the layout for this fragment


        rootView.findViewById(R.id.layout_train).setOnClickListener(this);
        rootView.findViewById(R.id.layout_flight).setOnClickListener(this);

        rootView.findViewById(R.id.layout_booked).setOnClickListener(this);



        mToolbar = (Toolbar)getActivity().findViewById(R.id.my_awesome_toolbar);

        layoutUser = (LinearLayout)rootView.findViewById(R.id.layout_user);

        parkingDialog =   new MaterialDialog.Builder(getActivity())
                .title("您预定的停车位")
                .backgroundColorRes(R.color.white)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();



        return rootView;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {



            case R.id.layout_train:
                Intent intent_train = new Intent(getActivity(),TrainFollowListActivity.class);
                startActivity(intent_train);
                break;

            case R.id.layout_flight:
                Intent intent_flight = new Intent(getActivity(),FlightFollowListActivity.class);
                startActivity(intent_flight);
                break;

            case R.id.layout_booked:
                sp = getActivity().getSharedPreferences("SP_PARKING", Context.MODE_APPEND);
                String parkingSpaceSP = sp.getString("parkingSpace", "");
                if (!parkingSpaceSP.equals("")) {
                    String  parkingSpace = parkingSpaceSP.split(",")[0]+ " 区 " +parkingSpaceSP.split(",")[1];
                    parkingDialog.setContent(parkingSpace);
                    parkingDialog.show();
                }else{
                    Toast.makeText(getActivity(), "您还没有预定停车位", Toast.LENGTH_SHORT).show();
                }


                break;

            default:
                break;
        }

    }


}
