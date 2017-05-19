package com.breezehan.ipc.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.breezehan.ipc.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Description
 * Author  Administrator
 * Date    2017/5/18 20:09
 * Version
 */

public class MyBinderPool {

    public static final int BINDER_SECURETY_CENTER = 1;
    public static final int BINDER_COMPUTE = 2;
    private static MyBinderPool mInstance;
    private Context mContext;
    private static volatile IBinderPool mBinderPool;
    private CountDownLatch mConnectBinderPoolCountDownLatch;
    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mRecipient,0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static MyBinderPool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MyBinderPool.class) {
                if (mInstance == null) {
                    mInstance = new MyBinderPool(context);
                }
            }
        }
        return mInstance;
    }

    public MyBinderPool(Context context) {
        mContext = context;
        connectBinderPoolService();
    }

    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder getQueryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }
    public static class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURETY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
            }
            return binder;
        }
    }
}
