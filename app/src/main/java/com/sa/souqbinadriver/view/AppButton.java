package com.sa.souqbinadriver.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class AppButton extends AppCompatButton {
    public AppButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.get("medium.otf", context);
        setTypeface(customFont);
    }

}
