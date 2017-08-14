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

import com.breezehan.ipc.MyConstants;
import com.breezehan.ipc.R;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    private Handler clientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVER:
                    Log.d(TAG, "handleMessage: receive msg "+msg.getData().getString("msg"));
                    break;
            }
        }
    };
    private Messenger clientMessenger = new Messenger(clientHandler);

    private Messenger serviceMessenger;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);
            Message message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "Hello,This is client.");
            message.setData(bundle);
            message.replyTo = clientMessenger;
            try {
                serviceMessenger.send(message);
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
        bindService(new Intent(this, MessengerService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}
