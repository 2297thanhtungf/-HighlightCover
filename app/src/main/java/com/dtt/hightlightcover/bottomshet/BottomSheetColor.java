package com.dtt.hightlightcover.bottomshet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.constance.Constance;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetColor extends BottomSheetDialogFragment {


    private View view;
    private ImageView imgChooseColor, imgColorDemo;
    BottomSheetColor bottomSheetColor;
    private Bitmap bitmap;

    private Button btnChoose;

    private int color;


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet_color, container, false);
        bottomSheetColor = this;
        btnChoose = view.findViewById(R.id.btn_closecolor);
        imgChooseColor = view.findViewById(R.id.img_choosecolor);
        imgColorDemo = view.findViewById(R.id.img_democolor);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Constance.ACTION_COLOR);
                intent.putExtra(Constance.KEY_COLOR, color);
                getContext().sendBroadcast(intent);
                hideBottomSheetColor();
            }
        });

        imgChooseColor.setDrawingCacheEnabled(true);
        imgChooseColor.buildDrawingCache(true);
        imgChooseColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = imgChooseColor.getDrawingCache();
                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    imgColorDemo.setBackgroundColor(Color.rgb(r, g, b));
                    color = Color.rgb(r, g, b);
                }


                return true;
            }
        });


        return view;
    }


    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    private void hideBottomSheetColor() {
        bottomSheetColor.dismiss();
    }
}

