package com.mylove.mlive;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mylove.mlive.base.BaseFragment;
import com.mylove.mlive.component.ApplicationComponent;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/28.
 */

public class SplashFragment extends BaseFragment {

    @BindView(R.id.splash_img)
    ImageView splashImg;

    public static SplashFragment newInstance(String splashUrl){
        Bundle args = new Bundle();
        args.putString("splashUrl", splashUrl);
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        splashImg.setImageResource(R.drawable.ic_player);
    }

    @Override
    public void initData() {

    }
}
