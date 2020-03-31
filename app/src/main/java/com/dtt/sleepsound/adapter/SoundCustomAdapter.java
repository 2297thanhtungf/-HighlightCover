package com.dtt.sleepsound.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.listener.OnSoundCustomClick;
import com.dtt.sleepsound.listener.OnSoundCustomClick;
import com.dtt.sleepsound.model.SoundCustom;

import java.util.List;

public class SoundCustomAdapter extends RecyclerView.Adapter<SoundCustomAdapter.ViewHolder> {

    private List<SoundCustom> listSoundCustom;
    private Context mContext;
    private OnSoundCustomClick soundCustomClick;

    public SoundCustomAdapter(List<SoundCustom> soundCustomList, Context mContext, OnSoundCustomClick soundCustomClick) {
        this.listSoundCustom = soundCustomList;
        this.soundCustomClick = soundCustomClick;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_addsound, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final SoundCustom soundCustom = listSoundCustom.get(position);
        Glide.with(mContext).load(soundCustom.getImage()).into(holder.imgSoundCustom);

        holder.imgSoundCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundCustomClick.onClick(soundCustom);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listSoundCustom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSoundCustom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSoundCustom = itemView.findViewById(R.id.img_soundcustom);

        }
    }
}
