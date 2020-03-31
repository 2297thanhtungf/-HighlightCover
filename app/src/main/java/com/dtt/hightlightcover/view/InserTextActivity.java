package com.dtt.hightlightcover.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtt.hightlightcover.R;

public class InserTextActivity extends Dialog {
    private final String defaultStr;
    private EditText editText;
    private ImageView imgDone, imgClose;
    private Context mContext;
    private BubbleTextView bubbleTextView;


    public InserTextActivity(Context context) {
        super(context);
        mContext = context;
        defaultStr = context.getString(R.string.double_click_input_text);
        initViews();
    }


    public InserTextActivity(Context context, BubbleTextView view) {

        super(context);
        mContext = context;
        defaultStr = context.getString(R.string.double_click_input_text);
        bubbleTextView = view;
        initViews();
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

    private void initViews() {

        setContentView(R.layout.insert_text);
        imgDone = findViewById(R.id.img_donetext);
        editText = findViewById(R.id.edt_input);
        imgClose = findViewById(R.id.img_close);

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


    @Override
    public void show() {
        super.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 100);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public interface CompleteCallBack {
        void onComplete(View bubbleTextView, String str);
    }

    private InserTextActivity.CompleteCallBack mCompleteCallBack;

    public void setCompleteCallBack(InserTextActivity.CompleteCallBack completeCallBack) {
        this.mCompleteCallBack = completeCallBack;
    }

    private void done() {
        dismiss();
        if (mCompleteCallBack != null) {
            String str;
            if (TextUtils.isEmpty(editText.getText())) {
                str = "";
            } else {
                str = editText.getText().toString();

            }
            mCompleteCallBack.onComplete(bubbleTextView, str);
        }

    }
    public void setFonts(String fonts){
        Typeface font = Typeface.createFromFile(fonts);
        editText.setTypeface(font);
    }
}
