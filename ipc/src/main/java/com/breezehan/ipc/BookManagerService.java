package com.breezehan.ipc;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private AtomicBoolean mIsServerDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();
    private Binder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
            int itemsSize = listeners.beginBroadcast();
            Log.i(TAG, "registerListener: listeners size "+itemsSize);
            listeners.finishBroadcast();
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
            int itemsSize = listeners.beginBroadcast();
            Log.i(TAG, "registerListener: listeners size "+itemsSize);
            listeners.finishBroadcast();
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.breezehan.ipc.permission.BookManagerService");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "Android"));
        bookList.add(new Book(2, "Java"));
        new Thread(new ServiceWorker()).start();


    }

    @Override
    public void onDestroy() {
        mIsServerDestroyed.set(true);
        super.onDestroy();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsServerDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = bookList.size() + 1;
                Book book = new Book(bookId, "new Book#" + bookId);
                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        bookList.add(book);
        int N = listeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = listeners.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listeners.finishBroadcast();
    }
}

