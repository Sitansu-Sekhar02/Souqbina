package com.sa.souqbinadriver.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class AppEditTextView extends AppCompatEditText {
    public AppEditTextView(Context context) {
        super(context);
        init();
    }

    public AppEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "regular.otf");
        setTypeface(tf);
    }


}
