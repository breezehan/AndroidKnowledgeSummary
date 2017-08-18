package com.breezehan.ipc.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.breezehan.ipc.R;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
//        ISecurityCenter iSecurityCenter = ISecurityCenter.Stub.asInterface(securityBinder);
        ISecurityCenter iSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        try {
            String encrypt = iSecurityCenter.encrypt(msg);
            Log.d(TAG, "doWork,encrypt:"+encrypt);
            Log.d(TAG, "doWork,decrypt:"+iSecurityCenter.decrypt(encrypt));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute iCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.d(TAG, "doWork,compute: "+iCompute.add(1,2));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
