package com.dtt.hightlightcover.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.bottomshet.BottomSheetBG;
import com.dtt.hightlightcover.bottomshet.BottomSheetEditText;
import com.dtt.hightlightcover.bottomshet.BottomSheetFonts;
import com.dtt.hightlightcover.bottomshet.BottomSheetFrame;
import com.dtt.hightlightcover.bottomshet.BottomSheetSticker;
import com.dtt.hightlightcover.constance.Constance;
import com.dtt.hightlightcover.model.Content;
import com.dtt.hightlightcover.model.ItemCustom;
import com.dtt.hightlightcover.view.BubbleTextView;
import com.dtt.hightlightcover.view.InserTextActivity;
import com.dtt.hightlightcover.view.StickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "tungdt";


    private static PreviewActivity previewActivity;
    private String imagePath;
    private ItemCustom itemCustom;
    private Content content;
    private ImageView imgPreview, imgBack, imgDownload, imgFrame, imgSticker, imgFonts, imgSetbg;
    private CircleImageView civSticker, civText;
    private RelativeLayout relativeLayout;
    private CircleImageView civBackground;

    private BottomSheetBG bottomSheetBG;
    private BottomSheetFrame bottomSheetFrame;
    private BottomSheetSticker bottomSheetSticker;
    private BottomSheetFonts bottomSheetText;

    private ArrayList<View> mCircleStickerViews;
    private ArrayList<View> mStickerViews;
    private ArrayList<View> mTextViews;

    private StickerView mCurrentStickerView;
    private BubbleTextView mCurrentEditTextView;

    private BottomSheetEditText bottomSheetEditText;

    private InserTextActivity inserTextActivity;
    private Bitmap bitmap;

    private String fonts;
    private int color;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itemcustom);
        Intent intent = getIntent();
        content = (Content) intent.getSerializableExtra(Constance.KEY);
        mCircleStickerViews = new ArrayList<>();
        mStickerViews = new ArrayList<>();
        mTextViews = new ArrayList<>();

        inserTextActivity = new InserTextActivity(this);
        inserTextActivity.setCompleteCallBack(new InserTextActivity.CompleteCallBack() {
            @Override
            public void onComplete(View bubbleTextView, String str) {
                ((BubbleTextView) bubbleTextView).setText(str);
            }
        });

        initViews();
        setBg();


        relativeLayout.post(new Runnable() {
            @Override
            public void run() {

                Glide.with(CustomActivity.this).asBitmap().load("file:///android_asset/sticker/" + content.getCircleSticker()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        addCircleStickerView(resource);
                        if (content.getSticker() != null){
                            Glide.with(CustomActivity.this).asBitmap().load("file:///android_asset/sticker/" + content.getSticker()).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    addBubbleStickerView(resource);
                                    Log.d(TAG, "onResourceReady: "+content.getText());
                                    if (!content.getText().equals("") && fonts == null ){
                                        addTextView(fonts);
                                    }
                                }
                            });
                        }else {
                            if (!content.getText().equals("") && fonts == null ){
                                addTextView(fonts);
                            }
                        }

                    }
                });
            }
        });

    }


    public void initViews() {
        imgDownload = findViewById(R.id.img_download);
        imgDownload.setOnClickListener(this);
        imgSetbg = findViewById(R.id.img_setbackground);
        imgSetbg.setOnClickListener(this);
        imgPreview = findViewById(R.id.img_preview);
        imgPreview.setOnClickListener(this);

        relativeLayout = findViewById(R.id.background);
        relativeLayout.setOnClickListener(this);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        imgFrame = findViewById(R.id.img_frame);
        imgFrame.setOnClickListener(this);
        imgFonts = findViewById(R.id.img_fonts);
        imgFonts.setOnClickListener(this);
        imgSticker = findViewById(R.id.img_sticker);
        imgSticker.setOnClickListener(this);


    }

    private void showBottomSheetBG() {
        bottomSheetBG = new BottomSheetBG();
        bottomSheetBG.show(getSupportFragmentManager(), "bottomSheetBG");
    }

    private void showBottomSheetFrame() {
        bottomSheetFrame = new BottomSheetFrame();
        bottomSheetFrame.show(getSupportFragmentManager(), "bottomSheetFrame");
    }

    private void showBottomSheetFonts() {
        bottomSheetText = new BottomSheetFonts();
        bottomSheetText.show(getSupportFragmentManager(), "bottomSheetText");
    }

    private void showBottomSheetSticker() {
        bottomSheetSticker = new BottomSheetSticker();
        bottomSheetSticker.show(getSupportFragmentManager(), "bottomSheetSticker");
    }

    private void showBottomSheetEditText() {
        bottomSheetEditText = new BottomSheetEditText(this);
        bottomSheetEditText.show(getSupportFragmentManager(), "bottomSheetEditText");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_setbackground:
                showBottomSheetBG();
                break;
            case R.id.img_frame:
                showBottomSheetFrame();
                break;
            case R.id.img_sticker:
                showBottomSheetSticker();
                break;
            case R.id.img_fonts:
                showBottomSheetFonts();
//                showBottomSheetEditText();
//                bottomSheetEditText.setCompleteCallBack(new BottomSheetEditText.CompleteCallBack() {
//
//                    @Override
//                    public void onComplete(View bubbleTextView, String str) {
//                        ((BubbleTextView) bubbleTextView).setText(str);
//                    }
//                });
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.background:
                if (mCurrentStickerView != null) {
                    mCurrentStickerView.setInEdit(false);
                }
                if (mCurrentEditTextView != null){
                    mCurrentEditTextView.setInEdit(false);
                }
                break;
            case R.id.img_preview:
                previewActivity = new PreviewActivity();
                generateBitmap();
                break;
            case R.id.img_download:
                if (bitmap != null){
                    SaveImage(this,bitmap);
                }else {
                    bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    relativeLayout.draw(canvas);
                    SaveImage(this,bitmap);
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fileAssets");
        intentFilter.addAction("fileCircle");
        intentFilter.addAction("fileSticker");
        intentFilter.addAction("filtFonts");
        intentFilter.addAction(Constance.ACTION_COLOR);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constance.ACTION_COLOR)){
                 color = intent.getIntExtra(Constance.KEY_COLOR,0);
                Log.d(TAG, "onReceive btncolor: "+color);
            }
            if (intent.getAction().equals("fileAssets")) {
                String fileName = intent.getStringExtra(Constance.KEY_FILENAME);
                setBackGroundFromBts(fileName);
            } else if (intent.getAction().equals("fileCircle")) {
                String name = intent.getStringExtra(Constance.KEY_FILENAME_CIRCLE);
                Glide.with(getApplicationContext()).asBitmap().load("file:///android_asset/sticker/" + name).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (color != 0){
                            Bitmap resultBitmap = Bitmap.createBitmap(resource, 0, 0,
                                    resource.getWidth() - 1, resource.getHeight() - 1);
                            Paint p = new Paint();
                            ColorFilter filter = new LightingColorFilter(color, 1);
                            p.setColorFilter(filter);
                            Canvas canvas = new Canvas(resultBitmap);
                            canvas.drawBitmap(resultBitmap, 0, 0, p);
                            if (mCircleStickerViews.size() >= 1) {
                                addCircleStickerView(resultBitmap);
                                relativeLayout.removeView(mCircleStickerViews.get(mCircleStickerViews.size() - 2));
                            } else {
                                addCircleStickerView(resultBitmap);
                            }
                        }else {
                            if (mCircleStickerViews.size() >= 1) {
                                addCircleStickerView(resource);
                                relativeLayout.removeView(mCircleStickerViews.get(mCircleStickerViews.size() - 2));
                            } else {
                                addCircleStickerView(resource);
                            }
                        }
                    }
                });
            } else if (intent.getAction().equals("fileSticker")) {
                String name = intent.getStringExtra(Constance.KEY_FILENAME_STICKER);
                Glide.with(getApplicationContext()).asBitmap().load("file:///android_asset/sticker/" + name).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (color != 0){
                            Bitmap resultBitmap = Bitmap.createBitmap(resource, 0, 0,
                                    resource.getWidth() - 1, resource.getHeight() - 1);
                            Paint p = new Paint();
                            ColorFilter filter = new LightingColorFilter(color, 1);
                            p.setColorFilter(filter);
                            Canvas canvas = new Canvas(resultBitmap);
                            canvas.drawBitmap(resultBitmap, 0, 0, p);
                            if (mStickerViews.size() >= 1) {
                                addBubbleStickerView(resultBitmap);
                                relativeLayout.removeView(mStickerViews.get(mStickerViews.size() - 2));
                            } else {
                                addBubbleStickerView(resultBitmap);
                            }
                        }else {
                            if (mStickerViews.size() >= 1) {
                                addBubbleStickerView(resource);
                                relativeLayout.removeView(mStickerViews.get(mStickerViews.size() - 2));
                            } else {
                                addBubbleStickerView(resource);
                            }
                        }
                    }
                });
            } else if ((intent.getAction().equals("filtFonts"))){
                 fonts = intent.getStringExtra(Constance.KEY_FILEFONTS);
               String subString =  fonts.substring(0,fonts.length()-4);
               String apend =  subString+".ttf";
                Log.d(TAG, "onReceive+: fontstext/"+apend);
                 if(mCurrentEditTextView != null){
                     mCurrentEditTextView.setFonts("fontstext/"+apend);
                 }
            }
        }
    };

    private void setBg() {
        Glide.with(this).asBitmap().load("file:///android_asset/bg/" + content.getBg()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout.setBackground(drawable);
                }
            }
        });
    }

    public void setBackGroundFromBts(String name) {
        Glide.with(this).asBitmap().load("file:///android_asset/bg/" + name).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout.setBackground(drawable);
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Uri path) {
        File f = new File(getRealPathFromURI(path));
        Drawable d = Drawable.createFromPath(f.getAbsolutePath());
        relativeLayout.setBackground(d);
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void addCircleStickerView(Bitmap bitmap) {
        final StickerView stickerView = new StickerView(this);
//        stickerView.setImageResource(imgFile);

        stickerView.setScreenSize(relativeLayout.getWidth(), relativeLayout.getHeight());
        stickerView.setBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mCircleStickerViews.remove(stickerView);
                relativeLayout.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {

                mCurrentStickerView.setInEdit(false);
                mCurrentStickerView = stickerView;
                mCurrentStickerView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mCircleStickerViews.indexOf(stickerView);
                if (position == mCircleStickerViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mCircleStickerViews.remove(position);
                mCircleStickerViews.add(mCircleStickerViews.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(stickerView, lp);
        mCircleStickerViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    private void addBubbleStickerView(Bitmap bitmap) {
        final StickerView stickerView = new StickerView(this);
//        stickerView.setImageResource(imgFile);

        stickerView.setScreenSize(relativeLayout.getWidth(), relativeLayout.getHeight());
        stickerView.setBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mStickerViews.remove(stickerView);
                relativeLayout.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentStickerView.setInEdit(false);
                mCurrentStickerView = stickerView;
                mCurrentStickerView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mStickerViews.indexOf(stickerView);
                if (position == mStickerViews.size() - 1) {
                    return;
                }
                StickerView stickerTemp = (StickerView) mStickerViews.remove(position);
                mStickerViews.add(mStickerViews.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(stickerView, lp);
        mStickerViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    private void addTextView(String fonts) {
        final BubbleTextView bubbleTextView = new BubbleTextView(this, Color.BLACK, 0);
        bubbleTextView.setScreenSize(relativeLayout.getWidth(), relativeLayout.getHeight());
        if (fonts != null){
            bubbleTextView.setFonts(fonts);
        }
        bubbleTextView.setImageResource(R.mipmap.output);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mTextViews.remove(bubbleTextView);
                relativeLayout.removeView(bubbleTextView);
            }

            @Override
            public void onEdit(BubbleTextView bubbleTextView) {
                if (mCurrentStickerView != null) {
                    mCurrentStickerView.setInEdit(false);
                }
                mCurrentEditTextView.setInEdit(false);
                mCurrentEditTextView = bubbleTextView;
                mCurrentEditTextView.setInEdit(true);
            }

            @Override
            public void onClick(BubbleTextView bubbleTextView) {
                inserTextActivity.setBubbleTextView(bubbleTextView);
                inserTextActivity.show();
            }

            @Override
            public void onTop(BubbleTextView bubbleTextView) {
                int position = mTextViews.indexOf(bubbleTextView);
                if (position == mTextViews.size() - 1) {
                    return;
                }
                BubbleTextView textView = (BubbleTextView) mTextViews.remove(position);
                mTextViews.add(mTextViews.size(), textView);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(bubbleTextView, lp);
        mTextViews.add(bubbleTextView);
        setCurrentEdit(bubbleTextView);
    }

    private void setCurrentEdit(BubbleTextView bubbleTextView) {
        if (mCurrentStickerView != null) {
            mCurrentStickerView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);


        }
        mCurrentEditTextView = bubbleTextView;
        mCurrentEditTextView.setInEdit(true);
    }

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentStickerView != null) {
            mCurrentStickerView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }

        mCurrentStickerView = stickerView;
        stickerView.setInEdit(true);
    }

    private void generateBitmap() {

        bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        relativeLayout.draw(canvas);
        imagePath = saveBitmapToLocal(bitmap, this);

        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("previre", imagePath);
        startActivity(intent);
//        previewActivity.preView(imagePath);

    }

    public String saveBitmapToLocal(Bitmap bm, Context context) {
        String path = null;
        try {
//            File file = FileUtils.getInstance(context).createTempFile("IMG_", ".jpg");
            File file = new File(context.getCacheDir(), Calendar.getInstance().getTimeInMillis() + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            } else {
            }
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    private String SaveImage(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Toast.makeText(this, "Save Image Internal Storage Success !!!", Toast.LENGTH_SHORT).show();
        return path;
    }
}
