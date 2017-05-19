package com.breezehan.ipc.manual;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private BookManagerImpl bookManagerImpl = new BookManagerImpl() {
        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }
    };
    public BookService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bookManagerImpl;
    }
}
