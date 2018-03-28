package com.mylove.mlive.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mylove.mlive.bean.CoreEvent;

import org.simple.eventbus.EventBus;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class CoreService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_INIT_CORE = "com.mylove.mlive.service.action.CoreService";


    /**
     * 启动调用
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, CoreService.class);
        intent.setAction(ACTION_INIT_CORE);
        context.startService(intent);
    }

    public CoreService() {
        super("CoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_CORE.equals(action)) {
                handleActionCore();
            }
        }
    }

    private void handleActionCore() {
        long start = System.currentTimeMillis();
//      初始化插件，动态dex等信息

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("zhou","duration ==>"+(System.currentTimeMillis() - start));
        EventBus.getDefault().post(new CoreEvent());

    }

}
