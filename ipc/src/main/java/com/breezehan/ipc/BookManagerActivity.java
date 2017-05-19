package com.breezehan.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    private static final int MSG_NEW_BOOK_ADD = 1;
    private IBookManager mRemoteBookManager;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_NEW_BOOK_ADD:
                    Log.i(TAG, "handleMessage: I have know new book added."+(Book)msg.obj);
                    break;
            }
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            mRemoteBookManager = iBookManager;
            try {
                List<Book> bookList = iBookManager.getBookList();
                Log.i(TAG, "onServiceConnected: query book list,list type:" + bookList.getClass().getCanonicalName());
                Log.i(TAG, "onServiceConnected: " + bookList.toString());
                Book book = new Book(3, "Android开发艺术搜索");
                iBookManager.addBook(book);
                List<Book> books = iBookManager.getBookList();
                Log.i(TAG, "query book list: " + books.toString());
                mRemoteBookManager.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.i(TAG, "binder died");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, "onDestroy: unRegisterListener "+onNewBookArrivedListener);
                mRemoteBookManager.unRegisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            myHandler.obtainMessage(MSG_NEW_BOOK_ADD,newBook).sendToTarget();
        }
    };
}
