package com.dtt.hightlightcover.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.constance.Constance;
import com.dtt.hightlightcover.viewpageradapter.VPMiniAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class VPAdapter extends PagerAdapter implements OnMiniItemClick {

    private static final String TAG = "tungdt";

    private Context mContext;
    private LayoutInflater inflater;
    private VPMiniAdapter miniAdapter;
    private onItemClickListener onItemClickListener;
    private String fileAsset;
    private String[] listImg;
    private OnMiniItemClick onMiniItemClick;
    private int color = Color.WHITE;
    private int size;


    public VPAdapter(Context mContext, String fileAsset, String[] listImg,int size, OnMiniItemClick onMiniItemClick) {
        this.mContext = mContext;
        this.fileAsset = fileAsset;
        this.listImg = listImg;
        this.size =size;
        this.onMiniItemClick = onMiniItemClick;
        inflater = LayoutInflater.from(mContext);
    }
    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
        miniAdapter.setColor(color);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);


        RecyclerView rc = view.findViewById(R.id.rc_viewpager);
        rc.setLayoutManager(new GridLayoutManager(mContext, 2, RecyclerView.HORIZONTAL, false));
        miniAdapter = new VPMiniAdapter(mContext, fileAsset, listImg, this);
        rc.setAdapter(miniAdapter);



        if (position == 0) {
            sendBroadCast(0);
        } else if (position == 1) {
            sendBroadCast(1);
        } else if (position == 2) {
            sendBroadCast(2);
        } else if (position == 3) {
            sendBroadCast(3);
        } else if (position == 4) {
            sendBroadCast(4);
        } else {
            sendBroadCast(0);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void onClick(String nameImage) {
        onMiniItemClick.onClick(nameImage);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public interface onItemClickListener {
        void onItemClicked(int position, int type);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "tung";
//            case 1:
//                return "thanh";
//            case 2:
//                return "duong";
//            case 3:
//                return "duong";
//            case 4:
//                return "duong";
//            case 5:
//                return "duong";
//            default:
//                return "";
//        }
        return null;
    }

    private void sendBroadCast(int possition) {
        Intent intent = new Intent();
        intent.setAction("action");
        intent.putExtra(Constance.KEY, possition);
        mContext.sendBroadcast(intent);
    }

    public void setListImg(String fileAsset, String[] listImg) {
        miniAdapter.setListImg(fileAsset, listImg);
        notifyDataSetChanged();
    }


}
