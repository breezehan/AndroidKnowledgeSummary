package com.breezehan.rxjava;

import dagger.Component;

/**
 * Author  hands
 * Description
 * Date    2017/7/28 14:49
 * Version
 */
@UserScope
@Component(modules = DataModule.class)
public interface SimpleComponent {
    void inject(MainActivity mainActivity);
}
