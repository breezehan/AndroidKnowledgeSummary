package com.breezehan.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        textView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void toSencond(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        String msg = event.getMessenge();
        textView.setText(msg);
    }
}
