package com.dtt.sleepsound.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.service.SoundService;

public class FragmentVolume extends Fragment {

    private View view;
    private AudioManager audioManager = null;
    private SeekBar sb1, sbSystem;

    private SoundService soundService;
    private boolean state = false;

    private LinearLayout llVolume;

    private int img;
    private TextView tvX;
    private ImageView img1;

    private boolean visibility = false;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_volume, container, false);
        openSoundCustomFragment();

        IntentFilter intentFilter = new IntentFilter(Constance.ACCTION_PUSH_FILE);
        getContext().registerReceiver(receiver, intentFilter);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        llVolume = view.findViewById(R.id.ll_volume);
        sb1 = view.findViewById(R.id.sb_1);
        sbSystem= view.findViewById(R.id.sb_system);

        sbSystem.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbSystem.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        sbSystem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (visibility == false) {
            llVolume.setVisibility(View.GONE);
        }

        sb1.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sb1.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        sb1.setMax(100);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SoundService.soundService.setVolumeCustomSound(((float)i / 100));
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        img1 = view.findViewById(R.id.img_1);
        tvX = view.findViewById(R.id.tv_x);
        tvX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundService.soundService.pauseSoundCustom();
                llVolume.setVisibility(View.GONE);
            }
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void openSoundCustomFragment() {
        if (state == false) {
            SoundCustomFragment soundCustomFragment = new SoundCustomFragment();
            soundCustomFragment.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade));
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout_context, soundCustomFragment).commit();
            state = true;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            img = intent.getIntExtra(Constance.IMG, 0);
            Glide.with(getContext()).load(img).into(img1);

            visibility = intent.getBooleanExtra(Constance.KEY_STATE_IMG_CUSTOM, false);
            if (visibility == true) {
                llVolume.setVisibility(View.VISIBLE);
            }
            visibility = true;
        }
    };

    public void initViews() {

    }
}
