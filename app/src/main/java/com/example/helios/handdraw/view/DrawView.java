package com.example.helios.handdraw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by helios on 12/19/15.
 * 实现bitmap双缓冲绘图
 */
public class DrawView extends View{

    private static final boolean DEBUG = true;
    private Path mPath;//记录绘制路线
    private float preX, preY;
    private Paint mPaint = null;
    private int mWidth ;
    private int mHeight;
    private Bitmap cacheBitmap;//作为缓冲区
    private Canvas mCanvas;//bitmap的画布
    private int mStrokeWidth;

    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWidth = 800;
        mHeight = 1000;
        cacheBitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mPath = new Path();
        mCanvas.setBitmap(cacheBitmap);
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth != 0 ? mStrokeWidth : 1);
        mPaint.setDither(true);
    }

    public void setDrawViewWidth(int width){
        this.mWidth = width;
    }

    public void setDrawViewHeight(int height){
        this.mHeight = height;
    }

    public void setStrokeWidth(int strokeWidth){
        this.mStrokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint drawPaint = new Paint();
        canvas.drawBitmap(cacheBitmap,0,0,drawPaint);
//        canvas.drawPath(mPath,drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath,mPaint);
                mPath.reset();
                break;
        }
        invalidate();
        return true;
    }
}
