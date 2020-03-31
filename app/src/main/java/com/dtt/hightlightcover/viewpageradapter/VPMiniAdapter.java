package com.dtt.hightlightcover.viewpageradapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.adapter.OnMiniItemClick;
import com.dtt.hightlightcover.constance.Constance;

import de.hdodenhof.circleimageview.CircleImageView;

public class VPMiniAdapter extends RecyclerView.Adapter<VPMiniAdapter.ViewHolder> {
    private Context mContext;
    private OnMiniItemClick onMiniItemClick;
    private onItemClickListener onItemClickListener;
    private String fileAsset;
    private String[] listImg;
    private String nameImage;
    private int color = Color.WHITE;

    public VPMiniAdapter(Context mContext, String fileAsset, String[] listImg, OnMiniItemClick onMiniItemClick) {
        this.mContext = mContext;
        this.fileAsset = fileAsset;
        this.listImg = listImg;
        this.onMiniItemClick = onMiniItemClick;
    }
    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    public void setListImg(String fileAssets,String[] listImg) {
        this.fileAsset = fileAssets;
        this.listImg = listImg;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(mContext).load(Uri.parse("file:///android_asset/" + fileAsset + "/" + listImg[position])).into(holder.civItem);

        holder.civItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameImage = listImg[position].substring(0, listImg[position].length() - 0);
                onMiniItemClick.onClick(nameImage);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listImg.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civItem = itemView.findViewById(R.id.civ_item);
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClicked(int position);

    }
}
