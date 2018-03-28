package com.mylove.mlive;

import com.mylove.mlive.base.BaseContract;
import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.ListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface MainContract {
    interface View extends BaseContract.BaseView{
        void loadChannelData(List<ChannelBean> channelBeans);
        void loadListData(List<ListBean> listBeans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getChannel();
    }
}
