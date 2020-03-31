package com.dtt.sleepsound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.listener.OnItemClick;
import com.dtt.sleepsound.model.Sound;

import java.util.List;


public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {

    private List<Sound> listSounds;
    private Context mContext;
    private OnItemClick onItemClick;

    public SoundAdapter(List<Sound> listSounds, Context mContext, OnItemClick onItemClick) {
        this.listSounds = listSounds;
        this.mContext = mContext;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Sound sound = listSounds.get(position);

        Glide.with(mContext).load(sound.getImageView()).into(holder.imgSound);

        holder.tvName.setText(sound.getName());
        holder.imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSounds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSound;
        private TextView tvName;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSound = itemView.findViewById(R.id.img_item);
            tvName = itemView.findViewById(R.id.tv_name);
            linearLayout = itemView.findViewById(R.id.ll);
        }
    }
}
