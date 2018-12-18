package com.jbh.librarymanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.distributiongoods.jbh.verticalscrollviewlibrary.CycleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> list=new ArrayList<>();

    private MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(adapter=new MyAdapter(){
            @Override
            public void content(MyViewHolder holder, final int position) {
                super.content(holder, position);
                holder.textView.setText(list.get(position));
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.notifyDataSetChanged(position);
                    }
                });
            }
        });


        CycleView mCycleView =   findViewById(R.id.mCycleView);

//        List<String> strings=new ArrayList<>();
//        strings.add("dajk fklad flasmdl fsladm fasdm");
//        strings.add("昆明公司的卢浮宫快乐删掉了开发告诉对方 收到了吗感受到");
//        strings.add("的空间功能水电费 ");
//        mCycleView.setDataString(strings);

        for (int i=0;i<5 ; i++){
            View view=LayoutInflater.from(this).inflate(R.layout.text_layout,null);
            mCycleView.addView(view);
        }


        mCycleView.setAutoStart(true);
    }



    public void addItem(View view){
        list.addAll(getList());
        notifyDataSetChanged();
    }



    public void notifyDataSetChanged(){
        if (adapter!=null){
            adapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(list.size() - getList().size());
        }
    }



    public void notifyDataSetChanged(int position){
        if (adapter!=null){
            list.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position , list.size());
        }
    }


    public List<String> getList(){
        List<String> strings=new ArrayList<>();
        for (int i=0 ; i<10;i++){
            strings.add("标题"+i);
        }
        return strings;
    }




    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return  new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1,parent,false));

        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            content(holder,position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }


        /**
         * @param holder
         * @param position
         */
        public void content(MyAdapter.MyViewHolder holder, int position){

        }


    }
}
