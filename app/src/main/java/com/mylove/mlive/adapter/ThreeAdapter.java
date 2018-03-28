package com.mylove.mlive.adapter;

import android.content.Context;

import com.mylove.mlive.R;
import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.ListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ThreeAdapter extends CommonAdapter<ChannelBean>{
    public ThreeAdapter(Context mContext, List<ChannelBean> mDatas, int mLayout) {
        super(mContext,mDatas, mLayout);
    }

    @Override
    public void convert(CViewHolder holder, ChannelBean item) {
        holder.setText(R.id.main_item_tv_three,item.getName());
    }
}
