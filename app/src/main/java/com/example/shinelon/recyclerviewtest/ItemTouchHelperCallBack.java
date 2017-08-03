package com.example.shinelon.recyclerviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by Shinelon on 2017/8/3.
 */

public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private MainActivity.ItemTouchListener mListener;

    public ItemTouchHelperCallBack(MainActivity.ItemTouchListener listener){
        mListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            int drag = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            int swipe = 0;
            return  makeMovementFlags(drag,swipe);
        }else{
            int drag = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            int swipe = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            Log.d("布局","List布局");
            return makeMovementFlags(drag,swipe);
        }

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
         mListener.itemMove(viewHolder.getLayoutPosition(),target.getLayoutPosition());
         return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
         mListener.itemRemove(viewHolder.getLayoutPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return  true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            Context context = viewHolder.itemView.getContext();
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.white));
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleY(1);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //这句代码就是item拖拽和滑动效果的实现，所以这句不能省略
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            viewHolder.itemView.setAlpha(1- Math.abs(dX)/viewHolder.itemView.getWidth());
            viewHolder.itemView.setScaleY(1-Math.abs(dX)/viewHolder.itemView.getWidth());
        }
    }
}
