package com.breezehan.knowledge.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.breezehan.knowledge.MainActivity;
import com.breezehan.knowledge.R;
import com.breezehan.knowledge.SecondActivity;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: "+this.toString()+"\ttaskId:"+getTaskId()+"packageName;"+ getPackageName());
        setContentView(R.layout.activity_first);
        ((TextView)findViewById(R.id.textView)).setText(this.toString());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(FirstActivity.this,OtherActivity.class);
                Intent intent = new Intent();
                intent.setAction("com.breezehan.taskaffinity.reparent");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

}
