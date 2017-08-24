package com.breezehan.remoteviews;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
/**
 * Author  hands
 * Description  解析原理使用，关于Notification更多请看其他:
 *              如1.http://iluhcm.com/2017/03/12/experience-of-adapting-to-android-notifications/
 *              2.http://www.jianshu.com/p/22e27a639787
 * Date    2017/8/24 17:18
 * Version
 */
public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fireNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("通知")
                .setContentText("Hello world!");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        builder.setContent(remoteViews);
        remoteViews.setTextViewText(R.id.msg,"通知通知...");
        remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2,pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}
