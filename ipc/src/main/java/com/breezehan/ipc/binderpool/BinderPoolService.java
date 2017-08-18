package com.breezehan.ipc.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BinderPoolService extends Service {
    private static final String TAG = "BinderPoolService";
    private IBinder binderPool = new BinderPool.BinderPoolImpl();
    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
