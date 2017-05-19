package com.breezehan.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
//    private IBinder mBinder = new IBookManager.Stub() {
//        @Override
//        public List<Book> getBookList() throws RemoteException {
//            return mBookList;
//        }
//
//        @Override
//        public void addBook(Book book) throws RemoteException {
//            mBookList.add(book);
//        }
//    };
    public BookService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
//        return mBinder;
        return null;
    }
}
