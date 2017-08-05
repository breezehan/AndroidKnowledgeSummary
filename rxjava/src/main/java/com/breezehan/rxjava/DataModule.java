package com.breezehan.rxjava;

import com.orhanobut.logger.Logger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Author  hands
 * Description
 * Date    2017/7/28 15:08
 * Version
 */

@Module
public class DataModule {
    @Provides
    public User provideUser() {
        return new User();
    }

    @Named("meizi")
    @UserScope
    @Provides
    public User provideUserMeizi() {
        Logger.d("initialize +11");
        System.out.println("meizi");
        return new User("meizi",18);
    }

    @UserQualifier
    @Provides
    public User provideUserHa() {
        return new User("我是大叔", 38);
    }

}
