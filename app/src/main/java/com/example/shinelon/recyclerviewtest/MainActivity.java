package com.example.shinelon.recyclerviewtest;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemTouchHelper mItemTouchHelper;
    List<String> mList;
    RecyclerView mRecyclerView;
    public interface ItemTouchListener{
        void itemMove(int from,int to);
        void itemRemove(int position);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        mList = new ArrayList<>();
        mList.add("test1");
        mList.add("test2");
        mList.add("test3");
        mList.add("test4");
        mList.add("test5");
        mList.add("test6");
        mList.add("test7");
        mList.add("test8");
        mList.add("test9");
        mList.add("test10");
        cusAdapter adapter =  new cusAdapter(mList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(adapter);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallBack(adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerViewLongClickListener(mRecyclerView));
        //mRecyclerView.addItemDecoration(new cusDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private class RecyclerViewLongClickListener implements RecyclerView.OnItemTouchListener{

        private GestureDetectorCompat mGestureDetector;
        private RecyclerView mRecyclerView;

        public RecyclerViewLongClickListener(RecyclerView recyclerView){
            this.mRecyclerView = recyclerView;
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),new ItemPress());
        }
        //触摸拦截事件
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }
        //触摸事件
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.e("TOUCHEVENT","  不为空");
        }
        //触摸冲突事件
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        /**
         * ViewGroup里的onInterceptTouchEvent默认值是false这样才能把事件传给View里的onTouchEvent.
         * ViewGroup里的onTouchEvent默认值是false。
         * View里的onTouchEvent返回默认值是true.这样才能执行多次touch事件
         *
         * SimpleOnGestureListener实现了两个接口中的八个方法（空方法）
         */
        private class ItemPress extends GestureDetector.SimpleOnGestureListener{
            //长按
            cusViewholder holder;
            @Override
            public void onLongPress(MotionEvent e) {
                View view = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
                if(view!=null){
                    holder = (cusViewholder) mRecyclerView.getChildViewHolder(view);
                    Log.e("长按","  不为空");
                }
                if(holder.getAdapterPosition()>0&&holder.getAdapterPosition()<mList.size()-1){
                    mItemTouchHelper.startDrag(holder);
                }
            }
            //点击
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("点击","  不为空");
                Toast.makeText(mRecyclerView.getContext(),"点击事件",Toast.LENGTH_SHORT).show();
                return true;
            }
        }

    }


    private class cusViewholder extends  RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView mTextView;
        public cusViewholder(View itemView){
            super(itemView);
            //itemView.setOnLongClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }

        /**
         * 部分item长按监听事件实现方法1
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v) {
            cusViewholder holder = (cusViewholder) mRecyclerView.getChildViewHolder(v);
            if(holder.getAdapterPosition()>0&&holder.getAdapterPosition()<mList.size()-1){
                mItemTouchHelper.startDrag(holder);
            }
            return true;
        }
    }

    private class cusAdapter extends  RecyclerView.Adapter<cusViewholder> implements MainActivity.ItemTouchListener {
        List<String> mList;
        public cusAdapter(List<String> list){
            mList = list;
        }

        @Override
        public cusViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item,parent,false);
            return new cusViewholder(view);
        }

        @Override
        public void onBindViewHolder(cusViewholder holder, int position) {
            holder.mTextView.setText( mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void itemMove(int from,int to) {
            Collections.swap(mList,from,to);
            notifyItemMoved(from,to);
        }

        @Override
        public void itemRemove(int position) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

}
