package com.mylove.mlive.component;

import android.content.Context;


import com.mylove.mlive.MyApp;
import com.mylove.mlive.module.ApplicationModule;
import com.mylove.mlive.module.HttpModule;
import com.mylove.mlive.net.ChannelApi;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {

    MyApp getApplication();

    ChannelApi getChannelApi();

    Context getContext();
}
