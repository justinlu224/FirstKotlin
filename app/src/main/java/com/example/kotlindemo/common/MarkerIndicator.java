package com.example.kotlindemo.common;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.kotlindemo.R;


/**
 * Created by louis383 on 2018/8/20.
 */
//圖標加上數字
public class MarkerIndicator {

    private Context context;

    @ColorRes
    private int backgroundColor;
    @ColorRes
    private int strokeColor;
    @ColorRes
    private int textColor;

    private float indicatorRadius;
    private float strokeWidth;
    private float textSize;
    private float indicatorMarginTop;
    private float indicatorMarginEnd;
    private Paint indicatorBackground;
    private Paint indicatorStrike;
    private Paint textPaint;

    private boolean drawDebugBackgroundRect;

    public MarkerIndicator(@NonNull Context context) {
        this(context, -1, -1, 0, 0, 0, 0, 0, 0, false);
    }

    public MarkerIndicator(@NonNull Context context, @ColorRes int color,
                           @ColorRes int strokeColor, @FloatRange(from = 0.0) float strokeWidth,
                           @ColorRes int textColor, @FloatRange(from = 0.0) float textSize,
                           @FloatRange(from = 0.0) float indicatorRadius,
                           @FloatRange(from = 0.0) float indicatorMarginTop,
                           @FloatRange(from = 0.0) float indicatorMarginEnd,
                           boolean autoConvertToDp) {

        this.context = context;
        this.backgroundColor = color;
        if (color == -1) {
            this.backgroundColor = R.color.color_white;
        }

        this.strokeColor = strokeColor;
        if (strokeColor == -1) {
            this.strokeColor = R.color.color_end_titlebar;
        }

        this.strokeWidth = autoConvertToDp ? convertPxToDp(strokeWidth) : strokeWidth;
        if (strokeWidth == 0) {
            // default width is 1dp
            this.strokeWidth = convertPxToDp(1f);
        }

        this.textColor = textColor;
        if (textColor == 0) {
            this.textColor = R.color.color_end_titlebar;
        }

        this.textSize = autoConvertToDp ? convertPxToDp(textSize) : textSize;
        if (textSize == 0) {
            // default size is 8dp
            this.textSize = convertPxToDp(8f);
        }

        this.indicatorRadius = autoConvertToDp ? convertPxToDp(indicatorRadius) : indicatorRadius;
        if (indicatorRadius == 0) {
            // default size is 2.5dp
            this.indicatorRadius = convertPxToDp(10f);
        }

        this.indicatorMarginTop = autoConvertToDp ? convertPxToDp(indicatorMarginTop) : indicatorMarginTop;
        this.indicatorMarginEnd = autoConvertToDp ? convertPxToDp(indicatorMarginEnd) : indicatorMarginEnd;

        initPaints(context);
    }

    public void setDrawDebugBackgroundRect(boolean drawDebugBackgroundRect) {
        this.drawDebugBackgroundRect = drawDebugBackgroundRect;
    }

    private void initPaints(Context context) {
        indicatorBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorBackground.setColor(ContextCompat.getColor(context, backgroundColor));
        indicatorBackground.setStyle(Paint.Style.FILL);

        indicatorStrike = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorStrike.setColor(ContextCompat.getColor(context, strokeColor));
        indicatorStrike.setStrokeWidth(strokeWidth);
        indicatorStrike.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(ContextCompat.getColor(context, textColor));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public Bitmap attachToMarkerDrawable(@DrawableRes int drawableId, @Nullable String text) {
        String indicatorText = "";
        if (text != null) {
            indicatorText = text;
        }

        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap markerBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas markerCanvas = new Canvas(markerBitmap);
        drawable.setBounds(0, 0, markerCanvas.getWidth(), markerCanvas.getHeight());
        drawable.draw(markerCanvas);

        Bitmap processedBitmap = Bitmap.createBitmap(markerBitmap.getWidth() + (int) indicatorRadius, markerBitmap.getHeight() + (int) indicatorRadius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(processedBitmap);
        if (drawDebugBackgroundRect) {
            Paint testBackground;
            testBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
            testBackground.setColor(ContextCompat.getColor(context, R.color.color_gray));
            testBackground.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, processedBitmap.getWidth(), processedBitmap.getHeight(), testBackground);
        }
        canvas.drawBitmap(markerBitmap, indicatorRadius / 2f, indicatorRadius, null);

        float indicatorX = markerBitmap.getWidth() - indicatorMarginEnd - 10;
        float indicatorY = indicatorRadius + indicatorMarginTop + 10;
        canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, indicatorBackground);
        canvas.drawCircle(indicatorX, indicatorY, indicatorRadius, indicatorStrike);
        canvas.drawText(indicatorText, indicatorX, indicatorY - 5 + (indicatorRadius / 2), textPaint);

        markerBitmap.recycle();

        return processedBitmap;
    }

    public float getIndicatorRadius() {
        return indicatorRadius;
    }

    private float convertPxToDp(float pixel) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, context.getResources().getDisplayMetrics());
    }

    public static class MarkerIndicatorBuilder {

        @ColorRes
        private int backgroundColor = -1;
        @ColorRes
        private int strokeColor = -1;
        @ColorRes
        private int textColor = -1;

        private float indicatorRadius;
        private float strokeWidth;
        private float textSize;
        private float indicatorMarginTop;
        private float indicatorMarginEnd;
        private boolean autoConvertToDp;

        private Context context;

        public MarkerIndicatorBuilder(Context context) {
            this.context = context;
        }

        public MarkerIndicatorBuilder setBackgroundColor(@ColorRes int resColor) {
            this.backgroundColor = resColor;
            return this;
        }

        public MarkerIndicatorBuilder setStrokeColor(@ColorRes int resColor) {
            this.strokeColor = resColor;
            return this;
        }

        public MarkerIndicatorBuilder setTextColor(@ColorRes int resColor) {
            this.textColor = resColor;
            return this;
        }

        public MarkerIndicatorBuilder setIndicatorRadius(@FloatRange(from = 0.0) float indicatorRadius) {
            this.indicatorRadius = indicatorRadius;
            return this;
        }

        public MarkerIndicatorBuilder setStrokeWidth(@FloatRange(from = 0.0) float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public MarkerIndicatorBuilder setTextSize(@FloatRange(from = 0.0) float textSize) {
            this.textSize = textSize;
            return this;
        }

        public MarkerIndicatorBuilder setIndicatorMarginTop(@FloatRange(from = 0.0) float indicatorMarginTop) {
            this.indicatorMarginTop = indicatorMarginTop;
            return this;
        }

        public MarkerIndicatorBuilder setIndicatorMarginEnd(@FloatRange(from = 0.0) float indicatorMarginEnd) {
            this.indicatorMarginEnd = indicatorMarginEnd;
            return this;
        }

        public MarkerIndicatorBuilder setAutoConvertToDp(boolean autoConvertToDp) {
            this.autoConvertToDp = autoConvertToDp;
            return this;
        }

        public MarkerIndicator build() {
            return new MarkerIndicator(context, backgroundColor, strokeColor, strokeWidth, textColor, textSize, indicatorRadius, indicatorMarginTop, indicatorMarginEnd, autoConvertToDp);
        }
    }
}

