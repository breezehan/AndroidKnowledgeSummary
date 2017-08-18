package com.breezehan.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Author  hands
 * Description
 * Date    2017/8/10 14:29
 * Version
 */

public class BookService extends Service{
    //    private List<Book> mBookList = new ArrayList<>();
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };
    private IBinder selfBinder = new UserManagerImpl();//手动实现的binder

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "三体"));
        mBookList.add(new Book(2, "Android"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
