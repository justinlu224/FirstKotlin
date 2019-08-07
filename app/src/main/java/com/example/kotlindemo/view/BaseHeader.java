package com.example.kotlindemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.LayoutRes;

public abstract class BaseHeader {

    public BaseHeader(Context context) {
        contentView = LayoutInflater.from(context)
                .inflate(getLayoutRes(), null, false);


        onCreateView(context);
    }

    abstract @LayoutRes
    int getLayoutRes();

    abstract void onCreateView(Context context);

    protected View contentView;

    public View getContentView() {
        return contentView;
    }
}
