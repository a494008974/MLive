package com.mylove.mlive.component;

import com.mylove.mlive.MainActivity;
import com.mylove.mlive.SplashFragment;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(MainActivity mainActivity);

}
