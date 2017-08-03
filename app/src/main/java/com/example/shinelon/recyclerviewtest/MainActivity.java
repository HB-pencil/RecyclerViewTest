package com.example.shinelon.recyclerviewtest;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemTouchHelper mItemTouchHelper;

    public interface ItemTouchListener{
        void itemMove(int from,int to);
        void itemRemove(int position);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        List<String> mList = new ArrayList<>();
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
        //mRecyclerView.addItemDecoration(new cusDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private class cusViewholder extends  RecyclerView.ViewHolder{
        private TextView mTextView;
        public cusViewholder(View itemView){
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
                    vibrator.vibrate(70);
                    return true;
                }
            });
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    private class cusAdapter extends  RecyclerView.Adapter<cusViewholder> implements ItemTouchListener{
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
