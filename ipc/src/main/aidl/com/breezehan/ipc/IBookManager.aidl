// IBookManager.aidl
package com.breezehan.ipc;

import com.breezehan.ipc.Book;
import com.breezehan.ipc.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);

}
