package com.breezehan.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.breezehan.ipc.bundle.SecondActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBookManager iBookManager;
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iBookManager != null) {
                iBookManager.asBinder().unlinkToDeath(deathRecipient, 0);
                iBookManager = null;
                //重新连接或者其他操作
                Log.e(TAG, "binderDied: ");
            }
        }
    };
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "handleMessage: received new book "+msg.obj);
                    break;
            }
        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            myHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,book).sendToTarget();
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient, 0);
//                iBookManager.addBook(new Book(1, "Java编程思想"));
//                iBookManager.addBook(new Book(2, "Android开发艺术探索"));
                List<Book> bookList = iBookManager.getBookList();
                Log.d(TAG, "onServiceConnected: query book list,list type:"+bookList.getClass().getCanonicalName());
                Log.d(TAG, "onServiceConnected: query book list " + bookList.toString());
                Book book = new Book(3, "Android进阶");
                iBookManager.addBook(book);
                Log.d(TAG, "onServiceConnected: add new book "+book);
                List<Book> newBookList = iBookManager.getBookList();
                Log.d(TAG, "onServiceConnected: query book list " + newBookList.toString());
                iBookManager.registerListener(mOnNewBookArrivedListener);
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
        bindService(new Intent(this, BookService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()) {
            Log.d(TAG, "onDestroy: unregister listener "+mOnNewBookArrivedListener);
            try {
                iBookManager.unRegisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
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
