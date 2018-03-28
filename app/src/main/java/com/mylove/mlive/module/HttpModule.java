package com.mylove.mlive.module;

import com.mylove.mlive.MyApp;
import com.mylove.mlive.net.ApiConstants;
import com.mylove.mlive.net.ChannelApi;
import com.mylove.mlive.net.ChannelApiService;
import com.mylove.mlive.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */
@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        // 指定缓存路径,缓存大小50Mb
        Cache cache = new Cache(new File(MyApp.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 50);
        return new OkHttpClient().newBuilder()
//                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
//                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
//                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

//    @Provides
//    Retrofit.Builder provideBuilder(OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient);
//    }

    @Provides
    ChannelApi provideChannelApi(OkHttpClient.Builder builder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return ChannelApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sChannelApi)
                .build().create(ChannelApiService.class));
    }
//
//    @Provides
//    JanDanApi provideJanDanApis(OkHttpClient.Builder builder) {
//
//        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(builder.build());
//
//        return JanDanApi.getInstance(retrofitBuilder
//                .baseUrl(ApiConstants.sJanDanApi)
//                .build().create(JanDanApiService.class));
//    }

}
