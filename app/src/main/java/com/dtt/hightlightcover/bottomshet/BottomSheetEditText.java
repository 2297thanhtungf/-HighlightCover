package com.dtt.hightlightcover.bottomshet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dtt.hightlightcover.R;
import com.dtt.hightlightcover.view.BubbleTextView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetEditText extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetEditText bottomSheetEditText;
    private final String defaultStr;
    private EditText editText;
    private ImageView imgDone, imgClose;
    private Context mContext;
    private BubbleTextView bubbleTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottomsheet_text, container, false);
        bottomSheetEditText = this;
        return view;
    }
    public BottomSheetEditText(Context context) {
        mContext = context;
        defaultStr = getContext().getString(R.string.double_click_input_text);
        initViews();
    }

    private void initViews() {
        imgDone = view.findViewById(R.id.img_done);

        editText = view.findViewById(R.id.edt_text);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    done();
                    return true;
                }
                return false;
            }
        });
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });

    }
    public interface CompleteCallBack {
        void onComplete(View bubbleTextView, String str);
    }

    private CompleteCallBack mCompleteCallBack;

    public void setCompleteCallBack(CompleteCallBack completeCallBack) {
        this.mCompleteCallBack = completeCallBack;
    }
    public void setBubbleTextView(BubbleTextView bubbleTextView) {
        this.bubbleTextView = bubbleTextView;
        if (defaultStr.equals(bubbleTextView.getmStr())) {
            editText.setText("");
        } else {
            editText.setText(bubbleTextView.getmStr());
            editText.setSelection(bubbleTextView.getmStr().length());
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void done() {
        dismiss();
        if (mCompleteCallBack != null) {
            String str;
            if (TextUtils.isEmpty(editText.getText())) {
                str = "";
            } else {
                str = editText.getText().toString();
                Log.d("inserttextdemo", "done: "+str);
            }
            mCompleteCallBack.onComplete(bubbleTextView, str);
        }

    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 100);
    }
}
