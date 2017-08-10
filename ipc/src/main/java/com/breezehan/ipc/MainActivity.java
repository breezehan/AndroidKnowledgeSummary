package com.breezehan.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.breezehan.ipc.bundle.SecondActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IBookManager iBookManager;
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iBookManager != null) {
                iBookManager.asBinder().unlinkToDeath(deathRecipient, 0);
                iBookManager = null;
                //重新连接或者其他操作
            }
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient, 0);
                iBookManager.addBook(new Book(1, "Java编程思想"));
                iBookManager.addBook(new Book(2, "Android开发艺术探索"));
                List<Book> bookList = iBookManager.getBookList();
                Log.d(TAG, "onServiceConnected:" + bookList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection selfServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IUserManager iUserManager = UserManagerImpl.asInterface(service);
            try {
                iUserManager.addUser(new User(18, "徐医生"));
                iUserManager.addUser(new User(22, "任玉刚"));
                List<User> userList = iUserManager.getUserList();
                Log.d(TAG, "onServiceConnected self:" + userList.toString());
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
        bindService(new Intent(this, BookService.class), selfServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void bundle(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", "lily");
        intent.putExtras(bundle);
        intent.putExtra("info", "secret");
        startActivity(intent);
    }
}
