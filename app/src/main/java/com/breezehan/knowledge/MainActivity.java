package com.breezehan.knowledge;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Description 对应博客中的文章http://blog.csdn.net/hds2011/article/details/71215298
 * Author  breezehan
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        if (savedInstanceState != null) {
            Log.i(TAG, "savedInstanceState: "+savedInstanceState.getString("saveState"));
        }
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.breezehan.knowledge.second");
                intent.setDataAndType(Uri.parse("http://www.baidu.com"), "image/*");
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                intent.setType("image/*");
//                intent.addCategory("com.breezehan.knowledge.mycategory");
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.setDataAndType(Uri.parse("http:"),"text/plain");
//                intent.setDataAndType(Uri.parse("content:"),"text/plain");
//                intent.setAction(intent.ACTION_SEND_MULTIPLE);
//                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    public void StartDialogActivity(View view) {
        startActivity(new Intent(this,DialogActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something is saved.";
        outState.putString("saveState", tempData);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }
}
