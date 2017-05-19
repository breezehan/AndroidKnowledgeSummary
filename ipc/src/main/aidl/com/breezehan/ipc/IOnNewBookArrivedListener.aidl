// IOnNewBookArrivedListener.aidl
package com.breezehan.ipc;

// Declare any non-default types here with import statements
import com.breezehan.ipc.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
