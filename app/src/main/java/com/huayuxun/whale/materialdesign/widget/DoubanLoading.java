package com.huayuxun.whale.materialdesign.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class DoubanLoading extends View {
    private int mHeight;
    private int mWidth;
    private Paint mPaint = new Paint();
    private ValueAnimator mValueAnimator;
    private float animatedValue;                       //随时间变化的角度
    private long animatorDuration = 4000;
    private TimeInterpolator mTimeInterpolator = new LinearInterpolator();
    protected OnLoadListener mOnLoadListener;
    private void initAnimator(long duration) {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
            mValueAnimator.start();
        } else {
            //转动的角度是８５５
            mValueAnimator = ValueAnimator.ofFloat(0, 855).setDuration(animatorDuration);
            mValueAnimator.setInterpolator(mTimeInterpolator);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    animatedValue = (float) mValueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.start();
        }
    }

    private void doubanAnimation(Canvas canvas, Paint mPaint) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.RED);
        canvas.translate(mWidth / 2, mHeight / 2);
        float point = Math.min(mWidth, mHeight) / 2 * 0.06f;
        float r = point * (float) Math.sqrt(2);
        RectF rectF = new RectF(-r, -r, r, r);
        canvas.save();
        // draw eye

        if (animatedValue >= 135) {
            canvas.rotate(animatedValue - 135);
        }
        canvas.drawPoints(new float[]{
                -point,-point
                ,point,-point
        },mPaint);
        // draw mouth
        float startAngle=0, sweepAngle=0;
        if (animatedValue<135){
            startAngle = animatedValue +5;
            sweepAngle = 170+animatedValue/3;
        }else if (animatedValue<270){
            startAngle = 135+5;
            sweepAngle = 170+animatedValue/3;
        }else if (animatedValue<630){
            startAngle = 135+5;
            sweepAngle = 260-(animatedValue-270)/5;
        }else if (animatedValue<720){
            startAngle = 135-(animatedValue-630)/2+5;
            sweepAngle = 260-(animatedValue-270)/5;
        }else{
            startAngle = 135-(animatedValue-630)/2-(animatedValue-720)/6+5;
            sweepAngle = 170;
        }
        canvas.drawArc(rectF,startAngle,sweepAngle,false,mPaint);



        canvas.restore();
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public DoubanLoading(Context context) {
        super(context);
        initAnimator(animatorDuration);
    }

    public DoubanLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimator(animatorDuration);
    }

    public DoubanLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimator(animatorDuration);
    }

    public DoubanLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAnimator(animatorDuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        doubanAnimation(canvas, mPaint);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public interface OnLoadListener{
        public void onLoad();
    }

    public void startAnimator(){
        if(mValueAnimator!=null&&!mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
            mValueAnimator.start();
        }else{
            return;
        }
    }

    public void setOnLoadListener(OnLoadListener onLoadListener){
        if(onLoadListener==null)
           return;
        mOnLoadListener = onLoadListener;
    }
    private class  AnimatorHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    }
}
