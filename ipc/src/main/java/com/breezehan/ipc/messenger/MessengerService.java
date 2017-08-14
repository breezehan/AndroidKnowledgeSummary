package com.breezehan.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.breezehan.ipc.MyConstants;

/**
 * Author  hands
 * Description
 * Date    2017/8/14 16:35
 * Version
 */

public class MessengerService extends Service {
    private final static String TAG = "MessengerService";
    private Handler serverHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.d(TAG, "handleMessage: receive msg  "+msg.getData().getString("msg"));
                    Messenger clientMessenger = msg.replyTo;
                    Message obtain = Message.obtain(null, MyConstants.MSG_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","Hi,This is from server.");
                    obtain.setData(bundle);
                    try {
                        clientMessenger.send(obtain);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private Messenger serverMessenger = new Messenger(serverHanlder);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serverMessenger.getBinder();
    }
}
