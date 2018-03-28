package com.mylove.mlive.net;

import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.StreamApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/3/2.
 */

public class ChannelApi {
    public static ChannelApi sInstance;
    private ChannelApiService mService;

    public ChannelApi(ChannelApiService mService) {
        this.mService = mService;
    }

    public static ChannelApi getInstance(ChannelApiService channelApiService) {
        if (sInstance == null)
            sInstance = new ChannelApi(channelApiService);
        return sInstance;
    }

    public Observable<List<ChannelBean>> getChannelDetail(String url) {
        return mService.getStream(url);
    }

    public Observable<StreamApi> getStream() {
        return mService.getChannel();
    }
}
