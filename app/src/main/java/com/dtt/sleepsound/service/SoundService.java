package com.dtt.sleepsound.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.dtt.sleepsound.Constance;
import com.dtt.sleepsound.R;


public class SoundService extends Service {

    private IBinder myBinder = new SoundBinder();
    public static SoundService soundService;
    private String state, stateNoti;

    private Intent intentCountDown, intenState;

    private AudioManager audioManager;
    private SoundPool soundPool;
    private int sound, soundCustom, soundStreamID, soundCustomStreamID;
    private int file, filePath, timeAlarm;

    private CountDownTimer countDownTimer;
    private IntentFilter intentFilter, intentFilterAlarmTime;

    public MediaPlayer mediaPlayer;

    public SoundService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null && action.equals(Constance.STOP)) {
            if (stateNoti.equals(Constance.PLAY)) {
                stopSelf();
                pauseMusic();
            }
            if (stateNoti.equals(Constance.STOP)) {
                stopSelf();
                pauseMusic();
            }
            stateNoti = Constance.STOP;
            state = Constance.STOP;

        } else if (action != null && action.equals(Constance.PLAY)) {

            if (stateNoti.equals(Constance.PLAY)) {
                pauseMusic();
            } else if (stateNoti.equals(Constance.STOP)) {
//                if (filePath != 0){
//                    startSoundPoolCustom(filePath);
//                }
//                startSoundPool(file);
                resumeMusic();
            } else {
                startSoundPool(file);
            }
        } else {
            state = Constance.PLAY;
            stateNoti = Constance.PLAY;
            soundService = this;
            file = intent.getIntExtra(Constance.FILE, 0);
            if (file != 0) {
                startSoundPool(file);
            }
            if (filePath != 0) {
                startSoundPoolCustom(filePath);

            }
            showNotification();
        }

        intenState = new Intent("action_music");
        intenState.putExtra(Constance.STATE, stateNoti);
        sendBroadcast(intenState);


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter(Constance.ACCTION_PUSH_FILE);
        intentFilterAlarmTime = new IntentFilter(Constance.ACCTION_FILTER_ALARM);
        this.registerReceiver(receiver, intentFilter);
        this.registerReceiver(receiver, intentFilterAlarmTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (state.equals(Constance.PLAY)) {
            pauseMusic();
        }
        soundService = null;

        this.unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class SoundBinder extends Binder {
        public SoundService getService() {
            return SoundService.this;
        }

    }

    public void pauseMusic() {
        if (state.equals(Constance.PLAY)) {
            pauseSoundPool(soundStreamID);
            pauseSoundPool(soundCustomStreamID);
        }
//        state = Constance.STOP;
//        stateNoti = Constance.STOP;
        Log.d("state", "state: " + state);
        Log.d("state", "statenoti: " + stateNoti);
    }

    public void resumeMusic() {
        if (state.equals(Constance.STOP)) {
            resumeSoundPool(soundStreamID);
            resumeSoundPool(soundCustomStreamID);
        }
    }

    public void startMusic() {
        if (state.equals(Constance.STOP)) {
            if (file != 0) {
                startSoundPool(file);
            }
            Log.d("filesound", "soundpool: " + file);
            if (filePath != 0) {
                startSoundPoolCustom(filePath);
            }
            Log.d("filesound", "soundpoolcustom: " + filePath);

        }
        state = Constance.PLAY;
        stateNoti = Constance.PLAY;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification() {

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_custom);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification_large_colored);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(notificationLayout);
        }
        builder.setContentTitle("Sleep Sounds");
        builder.setContentText("dmeo");


        notificationLayout.setOnClickPendingIntent(R.id.btn_stop, onButtonStopNotiClick(R.id.btn_stop));
        notificationLayout.setOnClickPendingIntent(R.id.btn_play, onButtonPlayNotiClick(R.id.btn_play));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123", "music", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }

        Notification notification = builder.build();
        startForeground(1, notification);

    }

    private PendingIntent onButtonPlayNotiClick(@IdRes int id) {
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction(Constance.PLAY);
        return PendingIntent.getService(SoundService.this, 1000, intent, 0);
    }

    private PendingIntent onButtonStopNotiClick(@IdRes int id) {
        Intent intent = new Intent(this, SoundService.class);
        intent.setAction(Constance.STOP);
        return PendingIntent.getService(SoundService.this, 1000, intent, 0);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constance.ACCTION_PUSH_FILE)) {
                filePath = intent.getIntExtra(Constance.FILE_CUSTOM, 0);
                startSoundPoolCustom(filePath);
            }

            if (intent.getAction().equals(Constance.ACCTION_FILTER_ALARM)) {
                timeAlarm = intent.getIntExtra(Constance.KEY_FILTER_ALARM, 0);

            }
            countTimer(timeAlarm);
            Log.d("alarmtime", "onReceive: " + timeAlarm);
        }
    };

    private void startSoundPool(int path) {

        createSoundPool();
        sound = soundPool.load(this, path, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundStreamID = soundPool.play(sound, 1, 1, 0, -1, 1);
            }
        });

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        state = Constance.PLAY;
        stateNoti = Constance.PLAY;
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
            soundPool.setVolume(soundStreamID, 1, 1);
            soundPool.setVolume(soundCustomStreamID, 1, 1);
        }
    }

    private void startSoundPoolCustom(int path) {
        soundCustom = soundPool.load(this, path, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundCustomStreamID = soundPool.play(soundCustom, 1, 1, 0, -1, 1);
            }
        });

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        state = Constance.PLAY;
        stateNoti = Constance.PLAY;
    }

    private void pauseSoundPool(int id) {
        soundPool.pause(id);
        state = Constance.STOP;
        stateNoti = Constance.STOP;
    }

    public void pauseSoundCustom() {
//        soundPool.pause(soundCustomStreamID);
        soundPool.stop(soundCustomStreamID);
    }

    private void resumeSoundPool(int id) {
        soundPool.resume(id);
        state = Constance.PLAY;
        stateNoti = Constance.PLAY;
    }


    public void setVolumeCustomSound(float volume) {
        soundPool.setVolume(soundCustomStreamID, volume, volume);
    }

    public void countTimer(int time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                intenState = new Intent(Constance.ACCTION_COUNTDOWN);
                intenState.putExtra(Constance.COUNT_DOWN, Constance.DONE);
                sendBroadcast(intenState);
                pauseMusic();
            }
        };
        countDownTimer.start();
    }


}
