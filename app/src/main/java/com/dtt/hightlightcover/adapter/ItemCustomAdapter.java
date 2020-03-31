package com.dtt.hightlightcover.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.constance.OnItemClick;
import com.dtt.hightlightcover.model.Content;
import com.dtt.hightlightcover.model.ItemCustom;

import java.util.ArrayList;

public class ItemCustomAdapter extends RecyclerView.Adapter<ItemCustomAdapter.ViewHolder>  {
    private Context mContext;
    private ArrayList<ItemCustom> listItemCustom;
    private OnItemClick onItemClick;


    public ItemCustomAdapter(Context mContext,  ArrayList<ItemCustom> listCategory, OnItemClick onItemClick) {
        this.mContext = mContext;
        this.listItemCustom = listCategory;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ItemCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_itemcontent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCustomAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext).load(Uri.parse("file:///android_asset/thumb/"+ listItemCustom.get(position).getName())).into(holder.imgItemContent);
        Log.d("namedelete", "onBindViewHolder: "+listItemCustom.get(position).getName());

            final Content content   = listItemCustom.get(position).getContent();

        holder.imgItemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("namedelete", "onBindViewHolder: "+listItemCustom.get(position).getName());
//                Content content   = listItemCustom.get(position).getContent();
//                Log.d("datdb", "content1: "+ content.getBg() +" "+ position);
                onItemClick.onClick(content);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItemCustom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItemContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemContent = itemView.findViewById(R.id.img_item_comtent);
        }
    }
}
