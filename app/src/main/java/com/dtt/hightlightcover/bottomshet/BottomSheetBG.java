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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.adapter.OnMiniItemClick;
import com.dtt.hightlightcover.adapter.VPAdapter;
import com.dtt.hightlightcover.constance.Constance;
import com.dtt.hightlightcover.activity.CustomActivity;
import com.dtt.hightlightcover.viewpageradapter.VPMiniAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class BottomSheetBG extends BottomSheetDialogFragment implements OnMiniItemClick, View.OnClickListener {
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private VPAdapter adapter;
    private VPMiniAdapter miniAdapter;
    private String fileAsset;
    private String[] listImg;
    private AssetManager assetManager;
    private int possition;
    private RecyclerView rc;
    private String nameImage;

    private BottomSheetBG bottomSheetBG;
    static final int PERMISSION_CODE = 1;
    static final int IMAGE_PICK_CODE = 1000;
    private ImageView imgPicture,imgDone;
    private CustomActivity customActivity;

    private int color;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet_bg, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        adapter = new VPAdapter(getContext(), fileAsset, listImg,6, this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        bottomSheetBG = this;
    }

    private void initViews() {
        viewPager = view.findViewById(R.id.viewpager_bg);
        tabLayout = view.findViewById(R.id.tab_layout);
        imgDone = view.findViewById(R.id.img_done);
        imgDone.setOnClickListener(this);

        imgPicture = view.findViewById(R.id.img_picture);
        imgPicture.setOnClickListener(this);

        fileAsset = "colorbg";
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
//        nameImage = listImg[possition].substring(0, listImg[possition].length() - 0);
        Intent intent = new Intent();
        intent.setAction("fileAssets");
        intent.putExtra(Constance.KEY_FILENAME,nameImage);
        getContext().sendBroadcast(intent);
        Log.d("tungdt", "onClick name image + : "+nameImage);
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
                updateImage("texture");
            } else if (possiion == 1) {
                updateImage("magic");
            } else if (possiion == 2) {
                updateImage("wc");
            } else if (possiion == 3) {
                updateImage("glitter");
            } else if (possiion == 4) {
                updateImage("metal");
            } else if (possiion == 5) {
                updateImage("floral");
            } else {
                updateImage("colorbg");
            }
        }
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.color7);
        tabLayout.getTabAt(1).setIcon(R.drawable.marble_8);
        tabLayout.getTabAt(2).setIcon(R.drawable.wc15);
        tabLayout.getTabAt(3).setIcon(R.drawable.bg012);
        tabLayout.getTabAt(4).setIcon(R.drawable.texture9);
        tabLayout.getTabAt(5).setIcon(R.drawable.background_floral);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getContext(), "Permission denied...", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri file = data.getData();
            customActivity.setBackground(file);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_done:
                hideBottomSheet();
                break;
            case R.id.img_picture:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
                break;
        }
    }
    private void hideBottomSheet(){
        bottomSheetBG.dismiss();
    }
}
