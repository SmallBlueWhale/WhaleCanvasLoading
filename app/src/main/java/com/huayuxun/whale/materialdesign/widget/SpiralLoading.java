package com.huayuxun.whale.materialdesign.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class SpiralLoading extends View {
    private int mHeight;
    private int mWidth;
    private Canvas mCanvas;
    private boolean isFinished = false;
    private Bitmap mCanvasBitmap;
    private Paint mPaint = new Paint();
    private ValueAnimator mAngleAnimator;
    private ValueAnimator mValueAnimator;
    private long duration = 5000;
    private float mAnimatorValue;
    private float startAngle = 0;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(30f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initAnimator(final long duration) {
        if (mValueAnimator != null && mValueAnimator.isRunning()&&!isFinished) {
            mValueAnimator.cancel();
            mValueAnimator.start();
        } else {
            mValueAnimator = mValueAnimator.ofFloat(0, 800).setDuration(duration);
            mValueAnimator.setInterpolator(timeInterpolator);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAnimatorValue = (float) mValueAnimator.getAnimatedValue();
                    isFinished = false;
                    invalidate();
                }


            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    isFinished = true;
                    initAnimator(duration);
                }
            });
            mValueAnimator.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCanvasBitmap = Bitmap.createBitmap(mWidth , mHeight , Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mCanvasBitmap);
    }

    public SpiralLoading(Context context) {
        super(context);
        initAnimator(duration);
    }

    public SpiralLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimator(duration);
    }

    public SpiralLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimator(duration);
    }

    public SpiralLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAnimator(duration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        initSun(canvas);
    }

    private void initSun(Canvas canvas) {
        mCanvas.save();
        mCanvas.translate(mWidth    * 0.5f,mHeight   * 0.5f);
        RectF mRectF  = new RectF();
        mRectF.left   =     - mAnimatorValue*0.5f;
        mRectF.right  =     + mAnimatorValue*0.5f;
        mRectF.top    =     - mAnimatorValue*0.5f;
        mRectF.bottom =     + mAnimatorValue*0.5f;
        if(mAnimatorValue < 360){
            mCanvas.drawArc(mRectF, startAngle, 10, false, mPaint);
            Log.e("1--------->",""+startAngle);
        }
        else if(mAnimatorValue >= 360 &&mAnimatorValue < 720 ) {
            mCanvas.drawArc(mRectF, startAngle - 360, 10, false, mPaint);
            Log.e("2--------->",""+startAngle);
        }
        else if(mAnimatorValue >= 720){
            mCanvas.drawArc(mRectF, startAngle - 720, 10, false, mPaint);
            Log.e("3--------->",""+startAngle);
        }
        startAngle = mAnimatorValue;
        canvas.drawBitmap(mCanvasBitmap, 0 , 0 , mPaint);
        mCanvas.restore();
        if(isFinished==true){
            initSun(canvas);
        }
    }


}