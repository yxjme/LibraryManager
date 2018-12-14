package com.distributiongoods.jbh.melibrary.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbsdata on 2018/9/15.
 */

public  class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /*头*/
    public static final int HEADER_TYPE=0;
    /*内容*/
    public static final int CONTENT_TYPE=1;
    /*脚*/
    public static final int FOOTER_TYPE=2;
    /*上下文*/
    private Context context;
    /*内容视图*/
    private int itemContentView;
    /*内容数量*/
    private int contentSize;
    private View headView;
    private View footerView;
    private List<View>  listHead=new ArrayList<>();
    private List<View>  listFooter=new ArrayList<>();


    public BaseRecyclerViewAdapter(Context context, int itemContentView, int contentSize){
        this.context=context;
        this.itemContentView=itemContentView;
        this.contentSize=contentSize;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (headView!=null &&  position < headViewSize()){
            head(holder,position);
        }
        if (footerView != null  &&  listFooter != null && (position + 1) > headViewSize() + contentSize){
            footer(holder,position- headViewSize()-contentSize);
        }
        if ((position+1) > headViewSize() &&  position < headViewSize() + contentSize){
            content(holder,position - headViewSize());
        }
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (headView!=null &&  viewType < headViewSize()){
            return new HeaderViewHolder(listHead.get(viewType));
        }else if (footerView != null  &&  listFooter != null && (viewType + 1) > headViewSize() + contentSize){
            return new FooterViewHolder(listFooter.get(viewType-(headViewSize() + contentSize)));
        }else {
            return new ContentViewHolder(LayoutInflater.from(context).inflate(itemContentView,parent,false));
        }
    }


    @Override
    public int getItemCount() {
        return headViewSize()+contentSize+footerViewSize();
    }


    /**
     *
     * @param head
     */
    public void addHeadView(View head){
        listHead.add(head);
        setHeadView(head);
    }



    /**
     * @param footer
     */
    public void addFooterView(View footer){
        listFooter.add(footer);
        setFooterView(footer);
    }


    /**
     * @param headView
     */
    public void setHeadView(View headView) {
        this.headView = headView;
    }


    /**
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }


    /**
     * 底部view的数量
     *
     * @return
     */
    private int footerViewSize() {
        return listFooter != null ? listFooter.size() : 0;
    }


    /**
     * 头部view的数量
     *
     * @return
     */
    private int headViewSize() {
        return listHead != null ? listHead.size() : 0;
    }



    /**
     *
     * @param holder
     * @param position
     */
    public void head(RecyclerView.ViewHolder holder, int position) {


    }



    /**
     * @param holder
     * @param position
     */
    public void footer(RecyclerView.ViewHolder holder, int position) {


    }


    /**
     * @param holder
     * @param position
     */
    public void content(RecyclerView.ViewHolder holder, int position) {


    }


    /**
     * 内容 holder
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder{

        public ContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(v,getAdapterPosition() - headViewSize());
                }
            });
        }
    }



    /**
     * 添加 footer
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }



    /**
     * 添加头 header
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }



    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void OnItemClick(View view, int position);
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
