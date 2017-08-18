package com.breezehan.ipc;

import android.app.Service;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author  hands
 * Description
 * Date    2017/8/10 14:29
 * Version
 */

public class BookService extends Service {

    private static final String TAG = "BookService";
    //    private List<Book> mBookList = new ArrayList<>();
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mArrivedListeners = new RemoteCallbackList<>();
    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mArrivedListeners.contains(listener)) {
//                mArrivedListeners.add(listener);
//            } else {
//                Log.d(TAG, "already exists.");
//            }
            mArrivedListeners.register(listener);
//            Log.d(TAG, "registerListener,size: " + mArrivedListeners.size());
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mArrivedListeners.contains(listener)) {
//                mArrivedListeners.remove(listener);
//                Log.d(TAG, "unRegisterListener succeed. ");
//            } else {
//                Log.d(TAG, "not found,can not unregister.");
//            }
//            Log.d(TAG, "unRegisterListener,current size:"+mArrivedListeners.size());
            mArrivedListeners.unregister(listener);
        }
    };
    private IBinder selfBinder = new UserManagerImpl();//手动实现的binder

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "三体"));
        mBookList.add(new Book(2, "Android"));
        new Thread(new ServiceWoker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    private class ServiceWoker implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId, "new Book #" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        mBookList.add(newBook);
//        Log.d(TAG, "onNewBookArrived，notify listeners:"+mArrivedListeners.size());
//        for (int i=0;i<mArrivedListeners.size();i++) {
//            IOnNewBookArrivedListener listener = mArrivedListeners.get(i);
//            Log.d(TAG, "onNewBookArrived,notify listener:"+listener);
//            listener.onNewBookArrived(newBook);
//        }
        int N = mArrivedListeners.beginBroadcast();
        for(int i=0;i<N;i++) {
            IOnNewBookArrivedListener broadcastItem = mArrivedListeners.getBroadcastItem(i);
            if (broadcastItem != null) {
                broadcastItem.onNewBookArrived(newBook);//耗时时非UI线程
            }
        }
        mArrivedListeners.finishBroadcast();
    }
}
