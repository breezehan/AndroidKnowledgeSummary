package com.breezehan.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MsgConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "handleMessage: receive msg from client:"+msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replayMsg = Message.obtain(null, MsgConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","恩，你的消息我已收到，稍后回复你。。。");
                    replayMsg.setData(bundle);
                    try {
                        client.send(replayMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler());
    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
