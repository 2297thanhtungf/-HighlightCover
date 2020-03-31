package com.dtt.sleepsound.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.adapter.SoundCustomAdapter;
import com.dtt.sleepsound.listener.OnSoundCustomClick;
import com.dtt.sleepsound.model.SoundCustom;
import com.dtt.sleepsound.service.SoundService;

import java.util.ArrayList;
import java.util.List;

public class SoundCustomFragment extends Fragment implements OnSoundCustomClick {

    private List<SoundCustom> listSoundInstruments, listSoundAnimals, listSoundNatures, listSoundRains, listSoundTransport;
    private SoundCustomAdapter adapter1, adapter2, adapter3, adapter4, adapter5;
    private View view;

    private ServiceConnection serviceConnection;
    private SoundService soundService;


    private static final int streamType = AudioManager.STREAM_MUSIC;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.view_addsound, container, false);
        initViews();
        serviceConect();


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(SoundCustom soundCustom) {

        Intent intentFile = new Intent();
        intentFile.setAction(Constance.ACCTION_PUSH_FILE);
        intentFile.putExtra(Constance.FILE_CUSTOM, soundCustom.getFile());
        intentFile.putExtra(Constance.IMG, soundCustom.getImage());
        intentFile.putExtra(Constance.KEY_STATE_IMG_CUSTOM, Constance.STATE_IMG_CUSTOM);
        getActivity().sendBroadcast(intentFile);


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    public void initViews() {
        RecyclerView rcIntrument = view.findViewById(R.id.rc_instrument);
        RecyclerView rcAnimals = view.findViewById(R.id.rc_animals);
        RecyclerView rcNature = view.findViewById(R.id.rc_nature);
        RecyclerView rcRain = view.findViewById(R.id.rc_rain);
        RecyclerView rcTransport = view.findViewById(R.id.rc_transport);

        listSoundTransport = new ArrayList<>();
        listSoundTransport.add(new SoundCustom(R.drawable.ic_car_gray, R.raw.car_main));
        listSoundTransport.add(new SoundCustom(R.drawable.ic_train_gray, R.raw.car_main));
        listSoundTransport.add(new SoundCustom(R.drawable.ic_plane_gray, R.raw.car_main));

        listSoundRains = new ArrayList<>();
        listSoundRains.add(new SoundCustom(R.drawable.ic_train_gray, R.raw.rain_main));
        listSoundRains.add(new SoundCustom(R.drawable.ic_rain_on_roof_gray, R.raw.train_rain));
        listSoundRains.add(new SoundCustom(R.drawable.ic_rain_in_forest_gray, R.raw.rainforest_main));
        listSoundRains.add(new SoundCustom(R.drawable.ic_snow_flakes_gray, R.raw.rain_main));
        listSoundRains.add(new SoundCustom(R.drawable.ic_storm_gray, R.raw.thunders));

        listSoundNatures = new ArrayList<>();
        listSoundNatures.add(new SoundCustom(R.drawable.ic_stream_gray, R.raw.thunders));
        listSoundNatures.add(new SoundCustom(R.drawable.ic_desert_gray, R.raw.desert));
        listSoundNatures.add(new SoundCustom(R.drawable.ic_fireplace_gray, R.raw.fire));
        listSoundNatures.add(new SoundCustom(R.drawable.ic_car_gray, R.raw.cave_main));
        listSoundNatures.add(new SoundCustom(R.drawable.ic_lake_gray, R.raw.lake));
        listSoundNatures.add(new SoundCustom(R.drawable.ic_forest_gray, R.raw.autumn_forest_main));

        listSoundAnimals = new ArrayList<>();
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_bird_gray, R.raw.birds));
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_owl_gray, R.raw.owls));
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_forest_gray, R.raw.frogs));
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_wolf_gray, R.raw.wolves));
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_cricket_gray, R.raw.crikets));
        listSoundAnimals.add(new SoundCustom(R.drawable.ic_bird_tropical_gray, R.raw.birds));

        listSoundInstruments = new ArrayList<>();
        listSoundInstruments.add(new SoundCustom(R.drawable.ic_piano_gray_1, R.raw.piano));
        listSoundInstruments.add(new SoundCustom(R.drawable.ic_piano_gray_2, R.raw.piano_2));
        listSoundInstruments.add(new SoundCustom(R.drawable.ic_harp_gray, R.raw.harp));
        listSoundInstruments.add(new SoundCustom(R.drawable.ic_stones_gray, R.raw.stones));

        adapter1 = new SoundCustomAdapter(listSoundTransport, getContext(), this);
        adapter2 = new SoundCustomAdapter(listSoundAnimals, getContext(), this);
        adapter3 = new SoundCustomAdapter(listSoundInstruments, getContext(), this);
        adapter4 = new SoundCustomAdapter(listSoundNatures, getContext(), this);
        adapter5 = new SoundCustomAdapter(listSoundRains, getContext(), this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rcTransport.setLayoutManager(gridLayoutManager);
        rcTransport.setAdapter(adapter1);


        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 4);
        rcAnimals.setLayoutManager(gridLayoutManager1);
        rcAnimals.setAdapter(adapter2);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 4);
        rcIntrument.setLayoutManager(gridLayoutManager2);
        rcIntrument.setAdapter(adapter3);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 4);
        rcNature.setLayoutManager(gridLayoutManager3);
        rcNature.setAdapter(adapter4);

        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getContext(), 4);
        rcRain.setLayoutManager(gridLayoutManager4);
        rcRain.setAdapter(adapter5);
    }

    public void serviceConect() {
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
    }
}
