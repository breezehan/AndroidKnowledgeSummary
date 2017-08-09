package com.breezehan.serializable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void display(View view) {
        File file = new File(getExternalFilesDir(""), "test.txt");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            User user = (User)objectInputStream.readObject();
            textView.setText(user.getName()+"\t"+user.getAge());
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
