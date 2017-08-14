package com.breezehan.ipc;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Author  hands
 * Description
 * Date    2017/8/10 15:17
 * Version
 */

public interface IUserManager extends IInterface {
    public static final String DESCRIPTOR = "com.breezehan.ipc.IUserManager";

    public final static int TRANSACTION_getUserList = IBinder.FIRST_CALL_TRANSACTION + 0;
    public final static int TRANSACTION_addUser = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<User> getUserList() throws RemoteException;

    public void addUser(User user) throws RemoteException;

}
