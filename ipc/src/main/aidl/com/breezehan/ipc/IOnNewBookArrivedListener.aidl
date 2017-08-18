// IOnNewBookArrivedListener.aidl
package com.breezehan.ipc;
import com.breezehan.ipc.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
