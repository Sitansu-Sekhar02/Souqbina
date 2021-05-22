package com.sa.souqbinadriver.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AppTextView_SubTitle extends AppCompatTextView {
    public AppTextView_SubTitle(Context context) {
        super(context);
        init();
    }

    public AppTextView_SubTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView_SubTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "medium.otf");
        setTypeface(tf);
    }
}
