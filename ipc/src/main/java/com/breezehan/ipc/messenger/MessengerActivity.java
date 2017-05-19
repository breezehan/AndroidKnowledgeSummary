package com.breezehan.ipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.breezehan.ipc.R;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MsgConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "handleMessage: receive from service "+msg.getData().getString("reply"));
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler());
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger mService = new Messenger(service);
            Message msg = Message.obtain(null, MsgConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello ,this is client");
            msg.setData(data);
            msg.replyTo = messenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
