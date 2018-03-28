package com.mylove.mlive.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.mylove.mlive.R;
import com.mylove.mlive.adapter.CommonAdapter;
import com.mylove.mlive.adapter.OneAdapter;
import com.mylove.mlive.adapter.TwoAdapter;
import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.ListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ChannelMenu extends FrameLayout implements CommonAdapter.CommonAdapterListener{

    private Context mContext;
    private CascadeMenu mMenuOne,mMenuTwo;
    private ChannelMenuLinstener menuLinstener;

    private OneAdapter oneAdapter;
    private TwoAdapter twoAdapter;

    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;

    public ChannelMenu(@NonNull Context context) {
        this(context,null);
    }

    public ChannelMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChannelMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_menu_layout,this);
        mMenuOne = (CascadeMenu) v.findViewById(R.id.main_menu_one);
        mMenuTwo = (CascadeMenu) v.findViewById(R.id.main_menu_two);

        mMenuOne.setLayoutManager(new LinearLayoutManager(mContext));
        oneAdapter = new OneAdapter(context,null,R.layout.main_one_item);
        mMenuOne.setAdapter(oneAdapter);
        oneAdapter.setCommonAdapterListener(this);

        mMenuTwo.setLayoutManager(new LinearLayoutManager(mContext));
        twoAdapter = new TwoAdapter(context,null,R.layout.main_two_item);
        mMenuTwo.setAdapter(twoAdapter);
        twoAdapter.setCommonAdapterListener(this);
    }

    public <T> void  setDatas(List<T> mDatas,int type){
        switch (type){
            case TYPE_ONE:
                oneAdapter.setDatas((List<ChannelBean>)mDatas);
                break;
            case TYPE_TWO:
                twoAdapter.setDatas((List<ListBean>)mDatas);
                break;
            default:
                break;
        }
    }

    public void requestFocusType(int type,int position){
        switch (type){
            case TYPE_ONE:
                mMenuOne.requestFocus();
                mMenuOne.smoothScrollToPosition(position);
                break;
            case TYPE_TWO:
                mMenuTwo.requestFocus();
                break;
            default:
                break;
        }

    }

    public void setMenuLinstener(ChannelMenuLinstener menuLinstener) {
        this.menuLinstener = menuLinstener;
    }

    @Override
    public void onItemClick(int position, Object o) {
        if (menuLinstener != null){
            menuLinstener.onItemClick(position,o);
        }
    }

    public interface ChannelMenuLinstener{
        public void onItemSelected(int position, Object o);
        public void onItemClick(int position, Object o);
    }
}
