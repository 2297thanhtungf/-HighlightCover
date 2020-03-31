package com.dtt.sleepsound.fragment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.model.Sound;
import com.dtt.sleepsound.service.SoundService;


public class SoundDetailFragment extends Fragment implements View.OnClickListener {

    AudioManager audioManager = null;

    SoundService soundService;

    private Sound sound;
    private int image, file;
    private String name;

    private ImageView imgBg;
    private TextView tvName;
    private SeekBar sb;
    private Button btnPlay, btnClock, btnAddSound, btnIcon1, btnIcon2;

    private ServiceConnection serviceConnection;

    public static SoundDetailFragment soundDetailFragment;

    private String state, stateNoti;
    private boolean isPlay = false;
    private boolean countDown = false;

    public SoundDetailFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detail, container, false);

        file = getArguments().getInt("file");
        image = getArguments().getInt("image");
        name = getArguments().getString("namemusic");

        soundDetailFragment = this;

        btnAddSound = view.findViewById(R.id.btn_addsound);
        btnAddSound.setOnClickListener(this);
        btnIcon1 = view.findViewById(R.id.btn_iconsound1);
        btnIcon2 = view.findViewById(R.id.btn_iconsound2);
        btnClock = view.findViewById(R.id.btn_clock);
        btnClock.setOnClickListener(this);
        btnPlay = view.findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        sb = view.findViewById(R.id.sb);
        sb.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sb.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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


        tvName = view.findViewById(R.id.tv_detail_name);
        tvName.setText(name);
        imgBg = view.findViewById(R.id.img_bg_sound);


        Glide.with(getContext()).load(image).into(imgBg);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                SoundService.SoundBinder soundBinder = (SoundService.SoundBinder) iBinder;
                soundService = soundBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                soundService = null;
            }
        };


        onClick(view);

        if (SoundService.soundService == null) {
            startService();
            isPlay = true;
        }

        Log.d("state", "isplay: " + isPlay);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), SoundService.class);
        intent.putExtra(Constance.FILE, file);
        getActivity().startService(intent);
    }

    public void stopService() {
        Intent intent = new Intent(getActivity(), SoundService.class);
        getActivity().stopService(intent);

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action_music")) {
                stateNoti = intent.getStringExtra(Constance.STATE);
            }
            if (intent.getAction().equals(Constance.ACCTION_COUNTDOWN)) {
                countDown = intent.getBooleanExtra(Constance.COUNT_DOWN, Constance.DONE);
                if (countDown == true) {
                    Drawable d = getResources().getDrawable(R.drawable.btn_play_normal);
                    btnPlay.setBackground(d);
                    isPlay = false;
                }
            }

            if (stateNoti.equals(Constance.STOP)) {
                Drawable d = getResources().getDrawable(R.drawable.btn_play_normal);
                btnPlay.setBackground(d);
                isPlay = false;
            }
            if (stateNoti.equals(Constance.PLAY)) {
                Drawable d = getResources().getDrawable(R.drawable.btn_pause_normal);
                btnPlay.setBackground(d);
                isPlay = true;
            }

        }
    };


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("action_music");
        IntentFilter intentFilterCountDown = new IntentFilter(Constance.ACCTION_COUNTDOWN);
        getContext().registerReceiver(receiver, intentFilter);
        getContext().registerReceiver(receiver, intentFilterCountDown);
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(receiver);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if (SoundService.soundService != null) {
                    if (isPlay == true) {
                        SoundService.soundService.pauseMusic();
                        Drawable d = getResources().getDrawable(R.drawable.btn_play_normal);
                        btnPlay.setBackground(d);
                        isPlay = false;
                    } else if (isPlay == false) {
//                        SoundService.soundService.startMusic();
                        SoundService.soundService.resumeMusic();
                        Drawable d = getResources().getDrawable(R.drawable.btn_pause_normal);
                        btnPlay.setBackground(d);
                        isPlay = true;
                    }
                } else {
                    startService();
                }
                break;
            case R.id.btn_addsound:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    soundCustom();
                }
                break;

            case R.id.btn_clock:
                setTime();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void soundCustom() {
//        SoundCustomFragment soundCustomFragment = new SoundCustomFragment();
//        soundCustomFragment.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade));
//        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout_context, soundCustomFragment).commit();
//

        FragmentVolume fragmentVolume = new FragmentVolume();
        fragmentVolume.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade));
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout_context, fragmentVolume).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setTime() {
        FragmentOclock fragmentOclock = new FragmentOclock();
        fragmentOclock.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade));
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout_context, fragmentOclock).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBgPlay() {
        Drawable d = getResources().getDrawable(R.drawable.btn_play_normal);
        btnPlay.setBackground(d);
    }

}

