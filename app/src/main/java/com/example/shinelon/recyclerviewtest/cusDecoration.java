package com.example.shinelon.recyclerviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Shinelon on 2017/8/3.
 */

public class cusDecoration extends RecyclerView.ItemDecoration{
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private int currentOrien;
    private int [] ATTR = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;

    public  cusDecoration(Context context,int orientation){
        TypedArray array = context.obtainStyledAttributes(ATTR);
        mDrawable = array.getDrawable(0);
        array.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation){
        this.currentOrien = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(currentOrien == VERTICAL){
            drawVertical(c,parent);
        }else if(currentOrien == HORIZONTAL){
            drawableHorizontal(c,parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(currentOrien == VERTICAL){
            outRect.set(0,0,0,mDrawable.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mDrawable.getIntrinsicHeight(),0);
        }
    }

    public void drawVertical(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i <= parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }
    public void drawableHorizontal(Canvas canvas,RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getBottom();
        for (int i = 0; i <= parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getWidth() + params.rightMargin;
            int right = left + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }
}
