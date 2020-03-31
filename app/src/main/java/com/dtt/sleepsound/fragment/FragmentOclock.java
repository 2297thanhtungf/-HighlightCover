package com.dtt.sleepsound.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dtt.sleepsound.AlarmReceiver;
import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;
import com.dtt.sleepsound.service.SoundService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentOclock extends Fragment implements View.OnClickListener {
    private View view;

    private RadioButton rbCustom, rb5, rb10, rb15, rb20;
    private RadioGroup rg;
    private TextView tvCustomDuration;

    SoundService soundService;
    SoundDetailFragment soundDetailFragment;

    private int timeAlarm;

    private AlarmManager alarmManager;
    private Calendar calendarCurrent, calendarChose;
    private CountDownTimer countDownTimer, countDownTimer5;
    PendingIntent pendingIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_oclock, container, false);
        calendarCurrent = Calendar.getInstance();
        calendarChose = Calendar.getInstance();
        initViews();

        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(getContext(), AlarmReceiver.class);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_5:
                        Toast.makeText(getContext(), rb5.getText(), Toast.LENGTH_SHORT).show();
                        final int time5 = 10000;

                        countDownTimer5 = new CountDownTimer(time5, 1000) {
                            @Override
                            public void onTick(long l) {
                                Log.d("time", "onTick: " + time5);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onFinish() {
                                SoundDetailFragment.soundDetailFragment.setBgPlay();
                                SoundService.soundService.pauseMusic();
                            }
                        };
                        countDownTimer5.start();
                        popBackStack();
                        break;
                    case R.id.rb_10:
                        Toast.makeText(getContext(), rb10.getText(), Toast.LENGTH_SHORT).show();
                        popBackStack();
                        break;
                    case R.id.rb_15:
                        Toast.makeText(getContext(), rb15.getText(), Toast.LENGTH_SHORT).show();
                        popBackStack();
                        break;
                    case R.id.rb_20:
                        Toast.makeText(getContext(), rb20.getText(), Toast.LENGTH_SHORT).show();
                        popBackStack();
                        break;

                    case R.id.rb_custom:
                        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                        int hour = calendarChose.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendarChose.get(Calendar.MINUTE);
                        final int year = calendarChose.get(Calendar.YEAR);
                        final int month = calendarChose.get(Calendar.MONTH);
                        final int date = calendarChose.get(Calendar.DATE);


                        int current = (int) calendarCurrent.getTimeInMillis();
                        String current1 = simpleDateFormat.format(calendarCurrent.getTime());
                        Log.d("current", "time current: " + current);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                                        calendarChose.set(year, month, date, i, i1);

                                        int time = (int) calendarChose.getTimeInMillis();
                                        String time1 = simpleDateFormat.format(calendarChose.getTime());
                                        Log.d("current", "time chose: " + time);


                                        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendarChose.getTimeInMillis(), pendingIntent);

                                        timeAlarm = (int) (calendarChose.getTimeInMillis() - calendarCurrent.getTimeInMillis());
                                        Log.d("current", "onTimeSet: " + timeAlarm);

                                        Intent intentAlarm = new Intent();
                                        intentAlarm.setAction(Constance.ACCTION_FILTER_ALARM);
                                        intentAlarm.putExtra(Constance.KEY_FILTER_ALARM, timeAlarm);
                                        getActivity().sendBroadcast(intentAlarm);


//                                        countDownTimer = new CountDownTimer(timeAlarm, 1000) {
//                                            @Override
//                                            public void onTick(long l) {
//                                                Log.d("time", "onTick: " + timeAlarm);
//                                            }
//
//                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                                            @Override
//                                            public void onFinish() {
//                                                SoundDetailFragment.soundDetailFragment.setBgPlay();
//                                                SoundService.soundService.pauseMusic();
//                                            }
//                                        };
//                                        countDownTimer.start();


                                    }
                                }, hour, minutes, true);
                        timePickerDialog.show();
                        break;
                }
            }
        });


        return view;
    }

    private void initViews() {
        rb5 = view.findViewById(R.id.rb_5);
        rb5.setOnClickListener(this);

        rg = view.findViewById(R.id.rg);
        rg.setOnClickListener(this);

        rb10 = view.findViewById(R.id.rb_10);
        rb10.setOnClickListener(this);

        rb15 = view.findViewById(R.id.rb_15);
        rb15.setOnClickListener(this);

        rb20 = view.findViewById(R.id.rb_20);
        rb20.setOnClickListener(this);

        rbCustom = view.findViewById(R.id.rb_custom);
        rbCustom.setOnClickListener(this);
    }

    private void popBackStack() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


}
