package com.dtt.hightlightcover.bottomshet;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.activity.CustomActivity;
import com.dtt.hightlightcover.adapter.OnMiniItemClick;
import com.dtt.hightlightcover.adapter.VPAdapter;
import com.dtt.hightlightcover.constance.Constance;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class BottomSheetFonts extends BottomSheetDialogFragment implements OnMiniItemClick, View.OnClickListener {

    static final int PERMISSION_CODE = 1;
    static final int IMAGE_PICK_CODE = 1000;
    private CustomActivity customActivity;

    private View view;
    private ViewPager viewPager;
    private VPAdapter adapter;
    private String fileAsset;
    private String[] listImg;
    private AssetManager assetManager;

    private ImageView imgDone, imgText;

    private BottomSheetFonts bottomSheetSticker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet_text, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        adapter = new VPAdapter(getContext(), fileAsset, listImg, 1, this);
        viewPager.setAdapter(adapter);
        bottomSheetSticker = this;
    }

    private void initViews() {
        viewPager = view.findViewById(R.id.viewpager_bg);
        imgDone = view.findViewById(R.id.img_done);
        imgDone.setOnClickListener(this);


        fileAsset = "fonts";
        assetManager = getContext().getAssets();
        try {
            listImg = assetManager.list(fileAsset);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action");
        intentFilter.addAction("fileAssets");
        getContext().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(receiver);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    @Override
    public void onClick(String nameImage) {

        Intent intent = new Intent();
        intent.setAction("filtFonts");
        intent.putExtra(Constance.KEY_FILEFONTS, nameImage);
        getContext().sendBroadcast(intent);
    }

    private void updateImage(String file) {
        fileAsset = file;
        assetManager = getContext().getAssets();
        try {
            listImg = assetManager.list(fileAsset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.setListImg(fileAsset, listImg);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_done:
                hideBottomSheet();
                break;

        }
    }

    private void hideBottomSheet() {
        bottomSheetSticker.dismiss();
    }
}
