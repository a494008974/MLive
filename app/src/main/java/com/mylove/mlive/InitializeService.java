package com.mylove.mlive;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class InitializeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    public static final String ACTION_INIT_WHEN_APP_CREATE = "com.mylove.mlive.action.app.create";
    public static final String EXTRA_PARAM = "com.mylove.mlive.extra.PARAM";

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("InitializeService ------- onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("InitializeService ------- onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("InitializeService ------- onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("InitializeService ------- onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("InitializeService ------- onBind");
        return super.onBind(intent);
    }

    /**
     * 启动调用
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("InitializeService ------- onHandleIntent"+Thread.currentThread());
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit(EXTRA_PARAM);
            }
        }
    }

    private void performInit(String extraParam) {
        //第三方SDK初始化,如当贝广告，友盟统计等
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
