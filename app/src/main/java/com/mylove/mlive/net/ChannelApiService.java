package com.mylove.mlive.net;

import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.StreamApi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/2.
 */

public interface ChannelApiService {
    @GET("channel.json")
    Observable<StreamApi> getChannel();

    @GET
    Observable<List<ChannelBean>> getStream(@Url String url);
}
