package com.breezehan.knowledge;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Description 对应博客中的文章http://blog.csdn.net/hds2011/article/details/71215298
 * Author  breezehan
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
