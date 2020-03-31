package com.dtt.sleepsound.fragment;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dtt.sleepsound.R;
import com.dtt.sleepsound.adapter.SoundAdapter;
import com.dtt.sleepsound.listener.OnItemClick;
import com.dtt.sleepsound.model.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundFragment extends Fragment implements OnItemClick {


    private List<Sound> listSounds;
    private SoundAdapter adapter;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rc_sound);
        listSounds = new ArrayList<>();
        listSounds.add(new Sound("Fefreshing Rain", R.drawable.pic_refreshing_rain, R.raw.rainforest_main));
        listSounds.add(new Sound("Rain on Windown", R.drawable.pic_rain_on_window, R.raw.rain_main));
        listSounds.add(new Sound("Rain in Forest", R.drawable.pic_rain_in_forest, R.raw.rain_in_forest_main));
        listSounds.add(new Sound("Peaceful Night", R.drawable.pic_night, R.raw.night_main));
        listSounds.add(new Sound("Oncean", R.drawable.pic_ocean, R.raw.ocean_main));
        listSounds.add(new Sound("Vivid creek", R.drawable.pic_creek, R.raw.creek));
        listSounds.add(new Sound("Winter Cottaget", R.drawable.pic_winter, R.raw.winter_main));
        listSounds.add(new Sound("Autumn woods", R.drawable.pic_autumn_forest, R.raw.autumn_forest_main));
        listSounds.add(new Sound("Rainforest", R.drawable.pic_jungle, R.raw.rain_in_forest_main));
        listSounds.add(new Sound("Quiet Cave", R.drawable.pic_cave, R.raw.cave_main));
        listSounds.add(new Sound("Desert", R.drawable.pic_desert, R.raw.desert));
        listSounds.add(new Sound("Lake", R.drawable.pic_lake, R.raw.lake));
        listSounds.add(new Sound("Vintage Train", R.drawable.pic_train, R.raw.train_main));
        listSounds.add(new Sound("Above the Sky", R.drawable.pic_plane, R.raw.airplane_main));
        listSounds.add(new Sound("Soothing Ride", R.drawable.pic_car, R.raw.car_rain));

        adapter = new SoundAdapter(listSounds, getContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("image", listSounds.get(position).getImageView());
        bundle.putString("namemusic", listSounds.get(position).getName());
        bundle.putInt("file", listSounds.get(position).getFile());

        SoundDetailFragment detailFragment = new SoundDetailFragment();

        detailFragment.setArguments(bundle);
        detailFragment.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.fade));
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout_context, detailFragment).commit();


    }


}
