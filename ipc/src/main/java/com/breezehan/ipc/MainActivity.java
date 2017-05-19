package com.breezehan.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.i(TAG, "onServiceConnected: query book list,list type:" + bookList.getClass().getCanonicalName());
                Log.i(TAG, "onServiceConnected: "+bookList.toString());
                Book book = new Book(3,"Android开发艺术搜索");
                bookManager.addBook(book);
                List<Book> books = bookManager.getBookList();
                Log.i(TAG, "onServiceConnected: "+books.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("test", new People(101, "username", true));
                startActivity(intent);
            }
        });
        Intent intent = new Intent(MainActivity.this,BookService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            User user = new User(1,"breezehan",true);
//            File file = new File(Environment.getExternalStorageDirectory()+"/.cache.txt");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
//            outputStream.writeObject(user);
//            outputStream.close();
//
//            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
//            User userRead = (User)inputStream.readObject();
//            Log.i(TAG, "serializable_test: "+userRead.getUserName()+"\t"+(user==userRead));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
