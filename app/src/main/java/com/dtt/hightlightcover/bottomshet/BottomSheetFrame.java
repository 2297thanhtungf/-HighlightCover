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
import android.util.Log;
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
import com.dtt.hightlightcover.adapter.OnMiniItemClick;
import com.dtt.hightlightcover.adapter.VPAdapter;
import com.dtt.hightlightcover.constance.Constance;
import com.dtt.hightlightcover.activity.CustomActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class BottomSheetFrame extends BottomSheetDialogFragment implements OnMiniItemClick, View.OnClickListener {

    static final int PERMISSION_CODE = 1;
    static final int IMAGE_PICK_CODE = 1000;
    private BottomSheetFrame bottomSheetFrame;

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private VPAdapter adapter;

    private String fileAsset;
    private String[] listImg;
    private String nameImage;

    private ImageView imgDone, imgColor, imgFrame;
    private AssetManager assetManager;
    private CustomActivity customActivity;

    private BottomSheetColor bottomSheetColor;

    private int color;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet_frame, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        adapter = new VPAdapter(getContext(), fileAsset, listImg, 3, this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        bottomSheetFrame = this;
    }

    private void initViews() {
        viewPager = view.findViewById(R.id.viewpager_bg);
        tabLayout = view.findViewById(R.id.tab_layout);
        imgDone = view.findViewById(R.id.img_done);
        imgDone.setOnClickListener(this);

        imgColor = view.findViewById(R.id.img_color);
        imgColor.setOnClickListener(this);
        imgFrame = view.findViewById(R.id.img_frame);
        imgFrame.setOnClickListener(this);

        fileAsset = "circle1";
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
        intentFilter.addAction(Constance.ACTION_COLOR);
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
//        nameImage = listImg[possition].substring(0, listImg[possition].length() - 0);

        if (!nameImage.equals("gradient_1.jpg")){
            Intent intent = new Intent();
            intent.setAction("fileCircle");
            intent.putExtra(Constance.KEY_FILENAME_CIRCLE, nameImage);
            getContext().sendBroadcast(intent);
        }

        if (nameImage.equals("gradient_1.jpg")) {
            bottomSheetColor = new BottomSheetColor();
            bottomSheetColor.show(getActivity().getSupportFragmentManager(), "bottomSheetColor");
        }
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


                int possiion = intent.getIntExtra(Constance.KEY, 0);

                if (possiion == 0) {
                    updateImage("circle1");
                } else if (possiion == 1) {
                    updateImage("circle2");
                } else if (possiion == 2) {
                    updateImage("brush");
                } else {
                    updateImage("circle1");

            }

            if (intent.getAction().equals(Constance.ACTION_COLOR)){
                color = intent.getIntExtra(Constance.KEY_COLOR,0);
                adapter.setColor(color);
            }

            Log.d("tungdt", "onReceive: "+color);





        }
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.color7);
        tabLayout.getTabAt(1).setIcon(R.drawable.marble_8);
        tabLayout.getTabAt(2).setIcon(R.drawable.wc15);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_done:
                hideBottomSheet();
                break;
            case R.id.img_color:
//                    updateImage("color");
                fileAsset = "color";
                assetManager = getContext().getAssets();
                try {
                    listImg = assetManager.list(fileAsset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setAdapterMini(fileAsset, listImg);
                break;
            case R.id.img_frame:
                fileAsset = "circle1";
                assetManager = getContext().getAssets();
                try {
                    listImg = assetManager.list(fileAsset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setAdapterMini(fileAsset, listImg);

                break;
        }
    }

    private void hideBottomSheet() {
        bottomSheetFrame.dismiss();
    }

    private void setAdapterMini(String fileAsset, String[] listImg) {
        adapter = new VPAdapter(getContext(), fileAsset, listImg, 3, this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


}
