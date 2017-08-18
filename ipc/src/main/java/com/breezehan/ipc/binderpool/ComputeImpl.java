package com.breezehan.ipc.binderpool;

import android.os.RemoteException;

/**
 * Author  hands
 * Description
 * Date    2017/8/18 16:36
 * Version
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
