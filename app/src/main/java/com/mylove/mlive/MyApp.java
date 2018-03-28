package com.mylove.mlive;

import android.app.Application;
import android.content.Context;

import com.mylove.mlive.bean.DaoMaster;
import com.mylove.mlive.bean.DaoSession;
import com.mylove.mlive.component.ApplicationComponent;
import com.mylove.mlive.component.DaggerApplicationComponent;
import com.mylove.mlive.module.ApplicationModule;
import com.mylove.mlive.module.HttpModule;
import com.mylove.mlive.utils.ContextUtils;

import org.greenrobot.greendao.database.Database;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MyApp extends Application {
    private static MyApp sMyApp;
    private ApplicationComponent mApplicationComponent;
    public static int width = 0;
    public static int height = 0;

    public static final String dbName = "channels-db";
    public static final boolean ENCRYPTED = false;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sMyApp = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "channels-db-encrypted" : "channels-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        width = ContextUtils.getSreenWidth(MyApp.getInstance());
        height = ContextUtils.getSreenHeight(MyApp.getInstance());

        InitializeService.start(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public static MyApp getInstance() {
        return sMyApp;
    }

    public static Context getContext() {
        return sMyApp.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
