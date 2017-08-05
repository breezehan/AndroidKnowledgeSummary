package com.breezehan.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.breezehan.rxjava.demo.ChapterOne;
import com.breezehan.rxjava.demo.ChapterTwo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {
//    @Inject
//    User user;

    @Inject
    @Named("meizi")
    User meizi;

    @Inject
    @Named("meizi")
    User xiameimei;

    //    @Inject
//    @UserQualifier
//    User uncle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        DaggerSimpleComponent.builder()
//                .dataModule(new DataModule())
//                .build()
//                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LApplication.getRefWatcher().watch(this);
    }

    public void daggerStar(View view) {

        ChapterTwo.demo2(MainActivity.this);
//        user.setAge(10);
//        user.setName("breezehan");
//        Logger.d(user);
//        Logger.d(meizi);
//        Logger.d(uncle);
    }
}
