package com.chunsoft.baseframework.utils.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Developer：chunsoft on 2016/10/31 10:25
 * Email：chun_soft@qq.com
 * Content：自定义RecycleView，增加头部和尾部的功能
 */

public class XRecycleView extends RecyclerView{
    private ArrayList<View> mHeaderViews = new ArrayList<View>();
    private ArrayList<View> mFooterViews = new ArrayList<View>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrapAdapter;

    private static final int TYPE_HEADER = -101;
    private static final int TYPE_FOOTER = -102;
    private static final int TYPE_LIST_ITEM = -103;

    public XRecycleView(Context context) {
        super(context);
    }

    public XRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){

    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews,mFooterViews,adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    public void addHeaderView(View view){
        mHeaderViews.clear();
        mHeaderViews.add(view);
    }

    public void addFooterView(View view){
        mFooterViews.clear();
        mFooterViews.add(view);
    }

    private final RecyclerView.AdapterDataObserver mDataObserver =
            new RecyclerView.AdapterDataObserver(){
                @Override
                public void onChanged() {
                    mWrapAdapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mWrapAdapter.notifyItemRangeChanged(positionStart,itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    mWrapAdapter.notifyItemRangeChanged(positionStart,itemCount,payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mWrapAdapter.notifyItemRangeInserted(positionStart,itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mWrapAdapter.notifyItemRangeRemoved(positionStart,itemCount);
                }
            };


    private class WrapAdapter extends android.support.v7.widget.RecyclerView.Adapter<ViewHolder>{
        private Adapter mAdapetr;
        private List<View> mHeaderViews;
        private List<View> mFooterViews;

        public WrapAdapter(List<View> mHeaderViews,List<View> mFooterViews,Adapter adapter){
            this.mAdapetr = adapter;
            this.mHeaderViews = mHeaderViews;
            this.mFooterViews = mFooterViews;
        }

        public int getHeaderCount(){return this.mHeaderViews.size();};
        public int getFooterCount(){return this.mFooterViews.size();};

        public boolean isHeader(int position){
            return position >= 0 && position < this.mHeaderViews.size();
        }
        public boolean isFooter(int position){
            return position < getItemCount() && position >= getItemCount()-this.getFooterCount();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER){
                return new CustomViewHolder(this.mHeaderViews.get(0));
            }else if (viewType == TYPE_FOOTER){
                return new CustomViewHolder(this.mFooterViews.get(0));
            }else {
                return this.mAdapetr.onCreateViewHolder(parent,viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(isHeader(position)) return;
            if(isFooter(position)) return;

            int rePostion = position - getHeaderCount();
            int itemCount = this.mAdapetr.getItemCount();

            if (this.mAdapetr != null){
                if (rePostion < itemCount){
                    this.mAdapetr.onBindViewHolder(holder,rePostion);
                    return;
                }
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)){
                return TYPE_HEADER;
            }else if(isFooter(position)){
                return TYPE_FOOTER;
            }
            int rePostion = position - getHeaderCount();
            int itemCount = this.mAdapetr.getItemCount();
            if (rePostion < itemCount){
                return this.mAdapetr.getItemViewType(position-getHeaderCount());
            }
            return TYPE_LIST_ITEM;
        }

        @Override
        public int getItemCount() {
            if (this.mAdapetr != null){
                return getHeaderCount()+getFooterCount()+this.mAdapetr.getItemCount();
            }else {
                return getHeaderCount()+getFooterCount();
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (this.mAdapetr != null){
                this.mAdapetr.registerAdapterDataObserver(observer);
            }
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (this.mAdapetr != null){
                this.mAdapetr.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            if (holder.getItemViewType() == TYPE_HEADER){
                super.onViewDetachedFromWindow(holder);
            }else if(holder.getItemViewType() == TYPE_FOOTER){
                super.onViewDetachedFromWindow(holder);
            }else {
                this.mAdapetr.onViewDetachedFromWindow(holder);
            }
        }

        private class CustomViewHolder extends ViewHolder{
            public CustomViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
