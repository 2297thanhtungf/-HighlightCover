package com.dtt.sleepsound.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.dtt.sleepsound.R;
import com.dtt.sleepsound.fragment.SoundDetailFragment;
import com.dtt.sleepsound.fragment.SoundFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.framelayout_context);
        if (fragment == null) {
            fragmentManager.beginTransaction().replace(R.id.framelayout_context, new SoundFragment()).commit();
        }

    }

    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.framelayout_context);
        if (f instanceof SoundDetailFragment) {
            ((SoundDetailFragment) f).stopService();
        }
        super.onBackPressed();
    }
}
