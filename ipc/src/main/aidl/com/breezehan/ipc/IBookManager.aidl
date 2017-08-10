// IBookManager.aidl
package com.breezehan.ipc;

import com.breezehan.ipc.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
