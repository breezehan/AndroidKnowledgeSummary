package com.breezehan.ipc.binderpool;

import android.os.RemoteException;

import com.breezehan.ipc.ICompute;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
