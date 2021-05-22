package com.sa.souqbinadriver.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AppTextView extends AppCompatTextView {
    public AppTextView(Context context) {
        super(context);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "regular.otf");
        setTypeface(tf);
    }
}
