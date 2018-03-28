package com.mylove.mlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CViewHolder> {
    private List<T> mDatas;
    private Context mContext;
    private int mLayout;
    private CommonAdapterListener commonAdapterListener;

    public CommonAdapter(Context mContext,List<T> mDatas, int mLayout) {
        this.mDatas = mDatas != null ? mDatas : new ArrayList<T>();
        this.mContext = mContext;
        this.mLayout = mLayout;
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CViewHolder viewHolder = CViewHolder.get(mContext,null,parent,mLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, final int position) {
        convert(holder,mDatas.get(position));
        holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonAdapterListener != null){
                    commonAdapterListener.onItemClick(position,mDatas.get(position));
                }
            }
        });
    }

    public abstract void convert(CViewHolder holder,T item);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setCommonAdapterListener(CommonAdapterListener commonAdapterListener) {
        this.commonAdapterListener = commonAdapterListener;
    }

    public interface CommonAdapterListener{
        public void onItemClick(int position, Object o);
    }
}
