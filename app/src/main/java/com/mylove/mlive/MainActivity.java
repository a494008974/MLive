package com.mylove.mlive;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.VideoView;

import com.mylove.mlive.base.BaseActivity;
import com.mylove.mlive.bean.ChannelBean;
import com.mylove.mlive.bean.CoreEvent;
import com.mylove.mlive.bean.ListBean;
import com.mylove.mlive.component.ApplicationComponent;
import com.mylove.mlive.component.DaggerHttpComponent;
import com.mylove.mlive.net.ApiConstants;
import com.mylove.mlive.service.CoreService;
import com.mylove.mlive.widget.ChannelMenu;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private Handler mHandler = new Handler();
    private SplashFragment splashFragment;

    @BindView(R.id.content_viewstub)
    ViewStub viewStub;

    private ChannelMenu channelMenu;

    private VideoView mVideoView;

    private long startTime = 0,endTime = 0;
    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);
    }

    public void initView(){
        //初始化界面控件
        mVideoView = (VideoView) findViewById(R.id.videoview);
        channelMenu = (ChannelMenu)findViewById(R.id.channel_menu);
        channelMenu.setMenuLinstener(new ChannelMenu.ChannelMenuLinstener() {
            @Override
            public void onItemSelected(int position, Object o) {
                Log.d("aaaaaa","onItemSelected position = " + position);
            }

            @Override
            public void onItemClick(int position, Object o) {
                Log.d("aaaaaa","onItemClick position = " + position);
            }
        });
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        startTime = System.currentTimeMillis();
        EventBus.getDefault().register(this);

        splashFragment = SplashFragment.newInstance(ApiConstants.sSplashApi);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, splashFragment);
        transaction.commit();

        //1.判断当窗体加载完毕的时候,立马再加载真正的布局进来
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                // 开启延迟加载
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewStub.inflate();//将viewstub加载进来,记载完毕后控件使用正常
                        initView();
                        //3.异步初始化插件等数据
                        CoreService.start(MainActivity.this);
                    }
                });
            }
        });

    }

    @Subscriber
    private void updateCore(CoreEvent coreEvent){
        //频道数据加载
        mPresenter.getChannel();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void loadChannelData(List<ChannelBean> channelBeans) {
        Log.d("aaaaaa","channelBeans.size = " + channelBeans.size());
        //获取数据并加载界面
        channelMenu.setDatas(channelBeans,ChannelMenu.TYPE_ONE);
        channelMenu.setDatas(channelBeans.get(0).getList(),ChannelMenu.TYPE_TWO);

        endTime = System.currentTimeMillis();
        final long n = endTime - startTime > 3000 ? 0 : 3000 - (endTime - startTime);

        Log.d("aaaaaa",""+(endTime - startTime));
        //2.判断当窗体加载完毕的时候执行,延迟一段时间做动画。
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
//               开启延迟加载,实现fragment里面的动画效果
                mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment) ,n);
//                mHandler.post(new DelayRunnable(MainActivity.this, splashFragment));//不开启延迟加载
            }
        });

        //开播
        if(mVideoView != null && !mVideoView.isPlaying()){
            mVideoView.setVideoURI(Uri.parse("http://ecdn.dynsite.tm/802AAD2/142194e/Live/gmapinoy/tracks-v1a1/index.m3u8?token=0558356b27398f74c6bf8c38ec01782cfcdc973e5c0e8f07c2452e1f9d2d656448c1f5fd8473e00d9b02969abac0cdb8656b1fbcf3a1b598554362c09c3a80ce7be258d1136bd2cea2f3324d918641966466f9928059a92d942b14a37ab03c1af6cb903449fc72e2085bdffb2b96ec9406c4b219&mac=00:1A:79:88:77:66&ip=159.65.49.66&magic_server=MAIN_VD_SP1"));
            mVideoView.start();
        }

        channelMenu.requestFocusType(ChannelMenu.TYPE_ONE,5);
    }

    @Override
    public void loadListData(List<ListBean> listBeans) {
//        for (ListBean listBean : listBeans){
//            Log.d("list",listBean.getName());
//        }
    }

    static class DelayRunnable implements Runnable{
        private WeakReference<Context> contextRef;
        private WeakReference<SplashFragment> fragmentRef;

        public DelayRunnable(Context context, SplashFragment f) {
            contextRef = new WeakReference<Context>(context);
            fragmentRef = new WeakReference<SplashFragment>(f);
        }

        @Override
        public void run() {
            // 移除splash页面
            if(contextRef!=null){
                SplashFragment splashFragment = fragmentRef.get();
                if(splashFragment==null){
                    return;
                }
                FragmentActivity activity = (FragmentActivity) contextRef.get();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.remove(splashFragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

}
