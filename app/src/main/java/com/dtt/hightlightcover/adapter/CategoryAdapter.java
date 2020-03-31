package com.dtt.hightlightcover.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.constance.Constance;
import com.dtt.hightlightcover.constance.OnItemClick;
import com.dtt.hightlightcover.activity.CustomActivity;
import com.dtt.hightlightcover.model.Category;
import com.dtt.hightlightcover.model.Content;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements OnItemClick {

    private Context mContext;
    private List<Category> listCategory;

    private ItemCustomAdapter adapter;

    public CategoryAdapter(Context mContext, List<Category> listCategory) {
        this.mContext = mContext;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mainrv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder holder, final int position) {
        Category category = listCategory.get(position);
        holder.tvTitle.setText(category.getTitle());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,5);
        holder.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ItemCustomAdapter(mContext, category.getItemCustoms(), this);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    @Override
    public void onClick(Content content) {
        Intent intent = new Intent(mContext, CustomActivity.class);
        intent.putExtra(Constance.KEY,content);
          mContext.startActivity(intent);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rc_item);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
        }
    }
}
